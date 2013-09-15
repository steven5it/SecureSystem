import java.util.HashMap;
import java.util.Map;

/*
 * ReferenceMonitor maintains labels and validates subjects and objects and reads/writes
 */
public class ReferenceMonitor {
	private Map<SecuritySubject, SecurityLevel> subjectMap = new HashMap<SecuritySubject, SecurityLevel>();
	private Map<SecurityObject, SecurityLevel> objectMap= new HashMap<SecurityObject, SecurityLevel>();
	ObjectManager om = new ObjectManager();
	/*
	 * ObjectManager class performs simple accesses (reads and writes objects by name)
	 */
	private class ObjectManager {
		public void objectRead (SecuritySubject s, int temp) {
			s.setTEMP(temp);
		}
		public void objectWrite(SecurityObject o, int value) {
			o.setValue(value);
		}
	}
	
	public void createNewObject(String name, SecurityLevel sl) {
		//check for the existence of this object
		SecurityObject object = new SecurityObject(name);
		if (objectMap.containsKey(object)) {
			System.err.println("Error: object already exists");
			return;
		}
		
		//if object key does not yet exist, add it to mapping
		objectMap.put(object, sl);
	}
	
	public void createNewSubject(SecuritySubject subject, SecurityLevel sl) {
		//check for the existence of this subject
		if (subjectMap.containsKey(subject))
		{
			System.err.println("Error: subject already exists");
			return;
		}
		subjectMap.put(subject, sl);	
	}
	
	/*
	 * Validate if SecurityLevel of subject is >= than SecurityLevel of object
	 */
	public void executeRead(String s, String o) {
		int temp = 0;
		for (SecuritySubject sKey: subjectMap.keySet())
		{
			if (sKey.getName().equalsIgnoreCase(s))
			{
				for (SecurityObject oKey: objectMap.keySet())
				{
					if (oKey.getName().equalsIgnoreCase(o))
					{
						//both the subject and object exist in our mapping, now check security
						if (subjectMap.get(sKey).compareTo(objectMap.get(oKey)) >= 0)
						{
							//subject securitylevel > object securitylevel so execute
							temp = oKey.getValue();
							System.out.println(s + " reads " + o);
							om.objectRead(sKey, temp);
							return;
						}
						else {
							//System.err.println("Access denied: security level does not match.");
							System.out.println(s + " reads " + o);
							om.objectRead(sKey, temp);
							return;
						}
					}
				}
				//System.err.println("Not a valid object entry."); don't output anything to avoid covert channel
				return;
			}
		}
		//System.err.println("Not a valid subject entry.");
		return;
	}
	
	
	//test hashcode

	
	/*
	 * Validate if SecurityLevel of subject is <= SecurityLevel of object
	 */
	public void executeWrite(String s, String o, int value) {
		for (SecuritySubject sKey: subjectMap.keySet())
		{
			if (sKey.getName().equalsIgnoreCase(s))
			{
				for (SecurityObject oKey: objectMap.keySet())
				{
					if (oKey.getName().equalsIgnoreCase(o))
					{
						//both the subject and object exist in our mapping, now check security
						if (subjectMap.get(sKey).compareTo(objectMap.get(oKey)) <= 0)
						{
							//subject securitylevel < object securitylevel so execute
							System.out.println(s + " writes value " + value + " to " + o);
							om.objectWrite(oKey, value);
							return;
						}
						else{
							//System.err.println("Access denied: security level does not match.");
							System.out.println(s + " writes value " + value + " to " + o);
							return;
						}
					}
				}
				//System.err.println("Not a valid object entry.");
				return;
			}
		}
		//System.err.println("Not a valid subject entry.");
		return;
	}

	public void validateInstruction(InstructionObject instruction) {
		if (instruction.getInstruction().equals("BAD")) {
			System.out.println("Bad Instruction");
		}
		else {
			if (instruction.getInstruction().equalsIgnoreCase("read")) {
				executeRead(instruction.getSubject(), instruction.getObject());
			} 
			if (instruction.getInstruction().equalsIgnoreCase("write")) {
				executeWrite(instruction.getSubject(), instruction.getObject(), instruction.getValue());
			}
					
		}
		
	}
	
	//iterates through all objects, then subjects, printing their values
	public void printState() {
		System.out.println("The current state is:");
		for (SecurityObject oKey: objectMap.keySet())
		{
			System.out.println("\t" + oKey.getName() + " has value: " + oKey.getValue());
		}
		for (SecuritySubject sKey: subjectMap.keySet())
		{
			System.out.println("\t" + sKey.getName() + " has recently read: " + sKey.getTEMP());
		}	
		System.out.println();
	}
}

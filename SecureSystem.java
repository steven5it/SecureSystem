/*Name: Steven Lee
 * UTEID: SCL346
 * Class: CS361
 * Assignment: HW1
 * Purpose: create a simple implementation of the BLP security system
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SecureSystem {
	
	static ReferenceMonitor rm = new ReferenceMonitor();
	public static void main (String [] args) throws IOException {
		
		SecureSystem sys = new SecureSystem();
		
		//HIGH dominates LOW as defined by Bell LaPadula Model
		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;
		
		//add two subjects, one high and one low.
		sys.createSubject("Lyle", low);
		sys.createSubject("Hal", high);
		
		//add two objects, one high and one low.
		rm.createNewObject("LObj", low);
		rm.createNewObject("HObj", high);
		
		//read in instruction list
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(args[0]));// "+ .txt"));
			System.out.println("Reading from file: " + args[0]);
			String line;
			InstructionObject instruction = null;
			while ((line = br.readLine()) != null) {
				//split each instruction line into an array of strings
				String [] lineArray = line.split(" ");
				
				//validate input for the format "write S O value" or "read S O"
				//if instruction set contains no arguments
				if (lineArray.length == 0)
				{
					instruction = new InstructionObject("BAD");
				}
				else
				{
					//get the instruction from the line
					String action = lineArray[0];
					
					//check if instruction is a valid one
					if (!action.equalsIgnoreCase("read") && !action.equalsIgnoreCase("write"))
					{
						instruction = new InstructionObject("BAD");
					}
					
					//if action is valid
					else if (action.equalsIgnoreCase("read"))
					{
						//invalid number of arguments
						if (lineArray.length != 3) {
							instruction = new InstructionObject("BAD");
						}
						else {	
							//ordered as such: action, subject, object
							instruction = new InstructionObject(action, lineArray[1], lineArray[2]);
						}
					}
					else if (action.equalsIgnoreCase("write"))
					{
						//invalid number of arguments
						if (lineArray.length!= 4 || !(sys.isInteger(lineArray[3]))) {
							instruction = new InstructionObject("BAD");
						}
						else {
							//ordered as such, action, subject, object, value
							instruction = new InstructionObject(action, lineArray[1], lineArray[2], lineArray[3]);
						}
					}
					
					//pass instruction to referenceMonitor for security validation
					if (instruction != null)
					{
						rm.validateInstruction(instruction);
					}
				}
				sys.printState();
			}
			
		}
		catch (IOException e) {
			System.err.print("The file was not found.");
		}
		finally {
			br.close();
		}
		
		
	}

	
	/*
	 * Instantiate subject and place in reference monitor
	 */
	private void createSubject(String name, SecurityLevel sl) {
		SecuritySubject subject = new SecuritySubject(name);
		rm.createNewSubject(subject, sl);
	}
	
	/* pre: none
	 * post: output values of LObj, HObj, and TEMP variable for Lyle and Hal
	 * debugging method that prints out current values from the state
	 */
	private void printState() {
		//implement iteration over maps eventually to print out all subjects/objects
		rm.printState();
	}
	
	/*
	 * Pass in string and check if it is an integer value
	 */
	private boolean isInteger (String s) {
		int size = s.length();
		for (int i = 0; i < size; i++)
		{
			if (!Character.isDigit(s.charAt(i)))
			{
				return false;
			}
		}
		return size > 0;
	}
}

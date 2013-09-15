//class for each instruction in the instruction list
public class InstructionObject {
	private String instruction;
	private String subjectName;
	private String objectName;
	private int value;
	
	public InstructionObject(String instruction) {
		this.instruction = instruction;
		
	}
	
	public InstructionObject(String instruction, String subjName, String objName) {
		this.instruction = instruction;
		subjectName = subjName;
		objectName = objName;
	}

	public InstructionObject(String instruction, String subjName, String objName, String value) {
		this.instruction = instruction;
		subjectName = subjName;
		objectName = objName;
		this.value = Integer.parseInt(value);
	}

	//accessor methods
	public String getInstruction()
	{
		return instruction;
	}
	public String getSubject()
	{
		return subjectName;
	}
	public String getObject()
	{
		return objectName;
	}
	public int getValue()
	{
		return value;
	}
	
}

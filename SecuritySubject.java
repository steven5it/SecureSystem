
public class SecuritySubject {
	//instance vars
	private String name;
	private int TEMP;  //variable contains most recently read value
	
	public SecuritySubject () {
		TEMP = 0;
	}
	
	public SecuritySubject (String name) {
		this();
		this.name = name;
		
	}
	
	//accessor methods
	public String getName()
	{
		return name;
	}
	public int getTEMP ()
	{
		return TEMP;
	}
	
	//mutator methods
	public void setTEMP (int temp) 
	{
		TEMP = temp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecuritySubject other = (SecuritySubject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}
	
}

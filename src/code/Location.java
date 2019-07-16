package code;

public class Location {
	
	/*not sure if should be private w setter methods or leave public
	 * 
	 */
	private String codename; //codename of character at location
	private String person;	//character on board
	private boolean isRevealed; //true if character revealed, false otherwise
	
	
	//default constructor (MAY BE REMOVED)
	public Location(){
		
	}
	
	
	/**
	 * constructor sets codeName, Person, and isRevealed
	 * @param codename
	 * @param person
	 * @param isRevealed
	 */
	public Location(String codename, String person, boolean isRevealed){
		this.codename = codename;
		this.setPerson(person);
		this.isRevealed = isRevealed;
	}
	
	/**
	 * simple toString for printing out location
	 */
	public String toString() {
		return codename + " " + isRevealed;
	}


	/**
	 * Generic getter and setter methods
	 */
	
	public String getPerson() {
		return person;
	}


	public void setPerson(String person) {
		this.person = person;
	}
	
	
	public String getCodename(){
		return codename;
	}
	
	public void setCodename(String codename)
	{
		this.codename = codename;
	}
	
	public boolean getRevealed()
	{
		return isRevealed;
	}
	
	public void setRevealed(boolean isRevealed)
	{
		this.isRevealed = isRevealed;
		
	}
	
	

}

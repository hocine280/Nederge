package TareServer.Commands;

public class Customer {
	
	private String name;
	private String surname;
	private String email;
	private String companyName;

	public Customer(String name, String surname, String email, String companyName){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = companyName;
	}

	public Customer(String name, String surname, String email){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = null;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getCompanyName() {
		return companyName;
	}

}

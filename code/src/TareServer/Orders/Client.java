package TareServer.Orders;

import org.json.JSONObject;

public class Client {
	
	private String name;
	private String surname;
	private String email;
	private String companyName;
	private int phoneNumber;

	public Client(String name, String surname, String email, String companyName, int phoneNumber){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
	}

	public Client(String name, String surname, String email, int phoneNumber){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = null;
		this.phoneNumber = phoneNumber;
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

	public int getPhoneNumber(){
		return this.phoneNumber;
	}

	public JSONObject toJson(){
		JSONObject ret = new JSONObject();

		ret.put("name", this.name);
		ret.put("surname", this.surname);
		ret.put("email", this.email);
		ret.put("companyName", this.companyName);
		ret.put("phoneNumber", this.phoneNumber);

		return ret;
	}

}

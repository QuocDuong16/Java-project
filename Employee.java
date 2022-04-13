package BookStore;

public class Employee extends Person {
	private String username;
	private String password;
	public Employee() {
		super();
	}
	public Employee(String id, String name, String address, String phone, String username, String password) {
		super(id,name,address,phone);
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}

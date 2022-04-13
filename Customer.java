package BookStore;

public class Customer extends Person {
	private String cClass;//Hạng khách hàng (thể loại khách hàng)
	public Customer() {
		super();
	}
	public Customer(String id, String name, String address, String phone, String cClass) {
		super(id,name,address,phone);
		this.cClass = cClass;
	}
	public String getcClass() {
		return cClass;
	}
}

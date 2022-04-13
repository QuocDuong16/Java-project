package BookStore;

public class BookCategory {
	private int id;
	private String name;
	private String describe;
	public BookCategory() {
		
	}
	public BookCategory(int id, String name, String describe) {
		this.id = id;
		this.name = name;
		this.describe = describe;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescribe() {
		return describe;
	}
}

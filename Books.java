package BookStore;

public class Books {
	private int id;
	private String name;
	private String publisher;
	private String author;
	private BookCategory categories;
	private int quantity;
	private int price;
	public Books() {
		
	}
	public Books(int id, String name, String publisher, String author, BookCategory categories, int quantity, int price) {
		this.id = id;
		this.name = name;
		this.publisher = publisher;
		this.author = author;
		this.categories = categories;
		this.quantity = quantity;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getAuthor() {
		return author;
	}
	public BookCategory getCategories() {
		return categories;
	}
	public int getQuantity() {
		return quantity;
	}
	public int getPrice() {
		return price;
	}
}

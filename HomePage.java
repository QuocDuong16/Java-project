package BookStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
public class HomePage extends JFrame implements ActionListener {
	Vector<Books> bookList = new Vector<>();
	Vector<BookCategory> bookCategoryList = new Vector<>();
	Vector<Employee> employeeList = new Vector<>();
	Vector<Customer> customerList = new Vector<>();
	JPanel mainPanel = new JPanel();
	JComboBox<String> cb;
	Connection connection;
	public HomePage() {
		setTitle("Home Page");
		importDatabase();
		String select[] = {"Books","Book Category","Employee","Customer","Import Bill","Export Bill","Statistic"};
		cb = new JComboBox<String>(select);
		cb.setBounds(20,10,850,28);
		JButton backButton = new JButton("BACK");
		backButton.setBounds(880,10,80,28);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		booksGUI();
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cb.getSelectedItem().equals("Books")) {
					booksGUI();
				}
				if (cb.getSelectedItem().equals("Book Category")) {
					bookCategoryGUI();
				}
				if (cb.getSelectedItem().equals("Employee")) {
					employeeGUI();
				}
				if (cb.getSelectedItem().equals("Customer")) {
					customerGUI();
				}
				if (cb.getSelectedItem().equals("Order")) {
					orderGUI();
				}
				if (cb.getSelectedItem().equals("Statistic")) {
					statisticGUI();
				}
			}
		});
		mainPanel.setBounds(40,55,900,580);
		mainPanel.setLayout(null);
		add(cb);add(backButton);
		add(mainPanel);
		setSize(1000,700);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	private int checkInteger(String s) {
		int num = 0;
		try {
			num = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			num = -1;
		}
		return num;
	}
	private void importDatabase() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=BookStore;encrypt=true;trustServerCertificate=true";
			String username = "sa"; String password= "16052002";
			connection = DriverManager.getConnection(dbUrl, username, password);
			Statement st = connection.createStatement();
			// Lấy dữ liệu thể loại sách
			ResultSet rs = st.executeQuery("Select * from TheLoaiSach");
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String describe = rs.getString(3);
				BookCategory bookCategory = new BookCategory(id, name, describe);
				bookCategoryList.add(bookCategory);
			}
			// Lấy dữ liệu sách
			rs = st.executeQuery("Select * from Sach");
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String publisher = rs.getString(3);
				String author = rs.getString(4);
				int categoriesId = rs.getInt(5);
				int quantity = rs.getInt(6);
				int price = rs.getInt(7);
				Books newBook = new Books(id, name, publisher, author, bookCategoryList.get(categoriesId - 1), quantity, price);
				bookList.add(newBook);
			}
			// Lấy dữ liệu nhân viên
			rs = st.executeQuery("Select * from NhanVien");
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String address = rs.getString(3);
				String phone = rs.getString(4);
				String username1 = rs.getString(5);
				String password1 = rs.getString(6);
				Employee employee = new Employee(id, name, address, phone, username1, password1);
				employeeList.add(employee);
			}
			// Lấy dữ liệu khách hàng
			rs = st.executeQuery("Select * from KhachHang");
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String address = rs.getString(3);
				String phone = rs.getString(4);
				String cClass = rs.getString(5);
				Customer employee = new Customer(id, name, address, phone, cClass);
				customerList.add(employee);
			}
			// Lấy dữ liệu hóa đơn nhập
			
			// Lấy dữ liệu hóa đơn xuất
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void booksGUI() {
		mainPanel.removeAll();
		DefaultTableModel model = new DefaultTableModel();
		JTable booksListTable = new JTable();
		model.setRowCount(0);
		// Tao danh sach the loai
		Vector<String> categoriesList = new Vector<>();
		for (BookCategory i : bookCategoryList) {
			categoriesList.add(i.getName());
		}
		// Tao panel
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel searchPanel = new JPanel();
		JPanel tablePanel = new JPanel();
		// Tao label
		JLabel idLabel = new JLabel("Id:");
		JTextField idField;
		if (bookList.isEmpty()) idField = new JTextField("1");
		else idField = new JTextField(String.valueOf(bookList.lastElement().getId() + 1));
		idLabel.setBounds(20,25,60,20);
		idField.setBounds(100,25,120,20);
		idField.setEditable(false);
		
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField();
		nameLabel.setBounds(20,65,60,20);
		nameField.setBounds(100,65,120,20);
		
		JLabel publisherLabel = new JLabel("Publisher:");
		JTextField publisherField = new JTextField();
		publisherLabel.setBounds(20,105,60,20);
		publisherField.setBounds(100,105,120,20);
		
		JLabel authorLabel = new JLabel("Author:");
		JTextField authorField = new JTextField();
		authorLabel.setBounds(20,145,60,20);
		authorField.setBounds(100,145,120,20);
		
		JLabel categoriesLabel = new JLabel("Categories:");
		JComboBox<String> categoriesBox = new JComboBox<String>(categoriesList);
		categoriesLabel.setBounds(240,25,60,20);
		categoriesBox.setBounds(320,25,120,20);
		
		JLabel quantityLabel = new JLabel("Quantity:");
		JTextField quantityField = new JTextField();
		quantityLabel.setBounds(240,65,60,20);
		quantityField.setBounds(320,65,120,20);
		
		JLabel priceLabel = new JLabel("Price:");
		JTextField priceField = new JTextField();
		priceLabel.setBounds(240,105,60,20);
		priceField.setBounds(320,105,120,20);
		// Tao nut
		JButton addButton = new JButton("ADD");
		addButton.setBounds(25,35,80,30);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.valueOf(idField.getText());
				String name = nameField.getText();
				String publisher = publisherField.getText();
				String author = authorField.getText();
				String categoriesName = categoriesBox.getSelectedItem().toString();
				int quantity = checkInteger(quantityField.getText());
				int price = checkInteger(priceField.getText());
				if (name.isBlank()  || author.isBlank() || publisher.isBlank() || categoriesName.isBlank() || quantity == -1 || price == -1) {
					JOptionPane.showMessageDialog(addButton, "Wrong data type or empty information");
					return;
				}
				BookCategory categories = new BookCategory();
				for (BookCategory i : bookCategoryList) {
					if (categoriesName.equals(i.getName())) {
						categories = i;
						break;
					}
				}
				Books newBook = new Books(id,name,publisher,author,categories,quantity,price);
				bookList.add(newBook);
				model.addRow(new Object[] {newBook.getId(),newBook.getName(),newBook.getPublisher(),newBook.getAuthor(),
						newBook.getCategories().getName(),newBook.getQuantity(),newBook.getPrice()});
				idField.setText(String.valueOf(bookList.lastElement().getId() + 1));
				String insertQuery = "Insert into Sach values(?,?,?,?,?,?,?)";
				PreparedStatement prest;
				try {
					prest = connection.prepareStatement(insertQuery);
					prest.setInt(1, id);
					prest.setString(2, name);
					prest.setString(3, publisher);
					prest.setString(4, author);
					prest.setInt(5, categories.getId());
					prest.setInt(6, quantity);
					prest.setInt(7, price);
					prest.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.setBounds(120,35,80,30);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = booksListTable.getSelectedRow();
				if (i >= 0) {
					int id = bookList.get(i).getId();
					String deleteQuery = "Delete from Sach where MaSach = ?";
					try {
						PreparedStatement prest = connection.prepareStatement(deleteQuery);
						prest.setInt(1, id);
						prest.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainPanel, "Không thể xóa do bị khóa ngoại");
						return;
					}
					model.removeRow(i);
					bookList.remove(i);
				}
			}
		});
		
		JButton updateButton = new JButton("UPDATE");
		updateButton.setBounds(215,35,80,30);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = checkInteger(idField.getText());
				String name = nameField.getText();
				String publisher = publisherField.getText();
				String author = authorField.getText();
				String categoriesName = categoriesBox.getSelectedItem().toString();
				int quantity = checkInteger(quantityField.getText());
				int price = checkInteger(priceField.getText());
				if (id == -1 || name.isBlank()  || author.isBlank() || publisher.isBlank() || categoriesName.isBlank() || quantity == -1 || price == -1) {
					JOptionPane.showMessageDialog(addButton, "Wrong data type or empty information");
					return;
				}
				BookCategory categories = new BookCategory();
				for (BookCategory i : bookCategoryList) {
					if (categoriesName.equals(i.getName())) {
						categories = i;
						break;
					}
				}
				Books newBook = new Books(id,name,publisher,author,categories,quantity,price);
				int i = booksListTable.getSelectedRow();
				if (i >= 0) {
					bookList.setElementAt(newBook, i);
					model.setValueAt(newBook.getId(), i, 0);
					model.setValueAt(newBook.getName(), i, 1);
					model.setValueAt(newBook.getPublisher(), i, 2);
					model.setValueAt(newBook.getAuthor(), i, 3);
					model.setValueAt(newBook.getCategories().getName(), i, 4);
					model.setValueAt(newBook.getQuantity(), i, 5);
					model.setValueAt(newBook.getPrice(), i, 6);
					String updateQuery = "Update Sach SET TenSach = ?, NhaXuatBan = ?, TacGia = ?, MaLoai = ?, SoLuong = ?, Gia = ? where MaSach = ?";
					try {
						PreparedStatement prest = connection.prepareStatement(updateQuery);
						prest.setString(1, name);
						prest.setString(2, publisher);
						prest.setString(3, author);
						prest.setInt(4, categories.getId());
						prest.setInt(5, quantity);
						prest.setInt(6, price);
						prest.setInt(7, id);
						prest.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		JTextField searchField = new JTextField(12);
		JButton resetButton = new JButton("RESET");
		resetButton.setBounds(310,35,80,30);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idField.setText(String.valueOf(bookList.lastElement().getId() + 1));
				nameField.setText(null);
				publisherField.setText(null);
				authorField.setText(null);
				categoriesBox.setSelectedItem("");
				quantityField.setText(null);
				priceField.setText(null);
				searchField.setText(null);
			}
		});
		
		JButton searchButton = new JButton("SEARCH");
		searchField.setBounds(30,30,200,25);
		searchButton.setBounds(240,30,80,25);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booksListTable.removeRowSelectionInterval(0, bookList.size() - 1);
				String tempString = searchField.getText();
				for (Books i : bookList) {
					if (i.getName().equals(tempString)) {
						booksListTable.addRowSelectionInterval(bookList.indexOf(i), bookList.indexOf(i));
					}
				}
			}
		});
		// Tao bang
		booksListTable.setModel(model);
		JScrollPane scrollPane = new JScrollPane(booksListTable);
		booksListTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				int i = booksListTable.getSelectedRow();
				if (i >= 0) {
					idField.setText(model.getValueAt(i, 0).toString());
					nameField.setText(model.getValueAt(i, 1).toString());
					publisherField.setText(model.getValueAt(i, 2).toString());
					authorField.setText(model.getValueAt(i, 3).toString());
					categoriesBox.setSelectedItem(model.getValueAt(i, 4).toString());
					quantityField.setText(model.getValueAt(i, 5).toString());
					priceField.setText(model.getValueAt(i, 6).toString());
				}
			}
		});
		scrollPane.setBounds(10,15,860,330);
		model.addColumn("ID");
		model.addColumn("NAME");
		model.addColumn("PUBLISHER");
		model.addColumn("AUTHOR");
		model.addColumn("CATEGORIES");
		model.addColumn("QUANTITY");
		model.addColumn("PRICE");
		for (Books i : bookList) {
			model.addRow(new Object[] {i.getId(),i.getName(),i.getPublisher(),i.getAuthor(),i.getCategories(),i.getQuantity(),i.getPrice()});
		}
		
		inputPanel.add(idLabel);inputPanel.add(idField);
		inputPanel.add(nameLabel);inputPanel.add(nameField);
		inputPanel.add(publisherLabel);inputPanel.add(publisherField);
		inputPanel.add(authorLabel);inputPanel.add(authorField);
		inputPanel.add(categoriesLabel);inputPanel.add(categoriesBox);
		inputPanel.add(quantityLabel);inputPanel.add(quantityField);
		inputPanel.add(priceLabel);inputPanel.add(priceField);
		
		searchPanel.add(searchField);searchPanel.add(searchButton);
		searchPanel.setLayout(null);
		searchPanel.setBounds(35,100,350,80);
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search by name or publisher"));
		buttonPanel.add(addButton);buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);buttonPanel.add(resetButton);
		buttonPanel.add(searchPanel);
		
		tablePanel.add(scrollPane);
		
		inputPanel.setLayout(null);
		inputPanel.setBounds(10,10,460,200);
		inputPanel.setBorder(BorderFactory.createTitledBorder("Book information"));
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(470,10,420,200);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		tablePanel.setLayout(null);
		tablePanel.setBounds(10,215,880,355);
		tablePanel.setBorder(BorderFactory.createTitledBorder("List of books"));
		mainPanel.add(inputPanel);
		mainPanel.add(buttonPanel);
		mainPanel.add(tablePanel);
	}
	private void bookCategoryGUI() {
		mainPanel.removeAll();
		DefaultTableModel model = new DefaultTableModel();
		JTable bookCategoryListTable = new JTable();
		model.setRowCount(0);
		// Tao panel
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel searchPanel = new JPanel();
		JPanel tablePanel = new JPanel();
		// Tao label
		JLabel idLabel = new JLabel("Id:");
		JTextField idField;
		if (bookCategoryList.isEmpty()) idField = new JTextField("1");
		else idField = new JTextField(String.valueOf(bookCategoryList.lastElement().getId() + 1));
		idLabel.setBounds(20,25,100,20);
		idField.setBounds(140,25,200,20);
		idField.setEditable(false);
		
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField();
		nameLabel.setBounds(20,65,100,20);
		nameField.setBounds(140,65,200,20);
		
		JLabel describeLabel = new JLabel("Describe:");
		JTextField describeField = new JTextField();
		describeLabel.setBounds(20,105,100,20);
		describeField.setBounds(140,105,200,20);
		
		// Tao nut
		JButton addButton = new JButton("ADD");
		addButton.setBounds(25,35,80,30);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.valueOf(idField.getText());
				String name = nameField.getText();
				String describe = describeField.getText();
				if (id == -1 || name.isBlank()  || describe.isBlank()) {
					JOptionPane.showMessageDialog(addButton, "Wrong data type or empty information");
					return;
				}
				BookCategory newBookCategory = new BookCategory(id,name,describe);
				bookCategoryList.add(newBookCategory);
				model.addRow(new Object[] {newBookCategory.getId(),newBookCategory.getName(),newBookCategory.getDescribe()});
				idField.setText(String.valueOf(bookCategoryList.lastElement().getId() + 1));
				String insertQuery = "Insert into TheLoaiSach values(?,?,?)";
				PreparedStatement prest;
				try {
					prest = connection.prepareStatement(insertQuery);
					prest.setInt(1, id);
					prest.setString(2, name);
					prest.setString(3, describe);
					prest.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.setBounds(120,35,80,30);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = bookCategoryListTable.getSelectedRow();
				if (i >= 0) {
					int id = bookCategoryList.get(i).getId();
					String deleteQuery = "Delete from TheLoaiSach where MaLoai = ?";
					try {
						PreparedStatement prest = connection.prepareStatement(deleteQuery);
						prest.setInt(1, id);
						prest.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainPanel, "Không thể xóa do bị khóa ngoại");
						return;
					}
					model.removeRow(i);
					bookCategoryList.remove(i);
				}
			}
		});
		
		JButton updateButton = new JButton("UPDATE");
		updateButton.setBounds(215,35,80,30);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = checkInteger(idField.getText());
				String name = nameField.getText();
				String describe = describeField.getText();
				if (id == -1 || name.isBlank()  || describe.isBlank()) {
					JOptionPane.showMessageDialog(addButton, "Wrong data type or empty information");
					return;
				}
				BookCategory newBookCategory = new BookCategory(id,name,describe);
				int i = bookCategoryListTable.getSelectedRow();
				if (i >= 0) {
					bookCategoryList.setElementAt(newBookCategory, i);
					model.setValueAt(newBookCategory.getId(), i, 0);
					model.setValueAt(newBookCategory.getName(), i, 1);
					model.setValueAt(newBookCategory.getDescribe(), i, 2);
					String updateQuery = "Update TheLoaiSach SET TenLoai = ?, MoTa = ? where MaLoai = ?";
					try {
						PreparedStatement prest = connection.prepareStatement(updateQuery);
						prest.setString(1, name);
						prest.setString(2, describe);
						prest.setInt(3, id);
						prest.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		JTextField searchField = new JTextField(12);
		JButton resetButton = new JButton("RESET");
		resetButton.setBounds(310,35,80,30);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idField.setText(String.valueOf(bookCategoryList.lastElement().getId() + 1));
				nameField.setText(null);
				describeField.setText(null);
				searchField.setText(null);
			}
		});
		
		JButton searchButton = new JButton("SEARCH");
		searchField.setBounds(30,30,200,25);
		searchButton.setBounds(240,30,80,25);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookCategoryListTable.removeRowSelectionInterval(0, bookCategoryList.size() - 1);
				String tempString = searchField.getText();
				for (BookCategory i : bookCategoryList) {
					if (i.getName().equals(tempString)) {
						bookCategoryListTable.addRowSelectionInterval(bookCategoryList.indexOf(i), bookCategoryList.indexOf(i));
					}
				}
			}
		});
		// Tao bang
		bookCategoryListTable.setModel(model);
		JScrollPane scrollPane = new JScrollPane(bookCategoryListTable);
		bookCategoryListTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				int i = bookCategoryListTable.getSelectedRow();
				if (i >= 0) {
					idField.setText(model.getValueAt(i, 0).toString());
					nameField.setText(model.getValueAt(i, 1).toString());
					describeField.setText(model.getValueAt(i, 2).toString());
				}
			}
		});
		scrollPane.setBounds(10,15,860,330);
		model.addColumn("ID");
		model.addColumn("NAME");
		model.addColumn("DESCRIBE");
		for (BookCategory i : bookCategoryList) {
			model.addRow(new Object[] {i.getId(),i.getName(),i.getDescribe()});
		}
		
		inputPanel.add(idLabel);inputPanel.add(idField);
		inputPanel.add(nameLabel);inputPanel.add(nameField);
		inputPanel.add(describeLabel);inputPanel.add(describeField);
		
		searchPanel.add(searchField);searchPanel.add(searchButton);
		searchPanel.setLayout(null);
		searchPanel.setBounds(35,100,350,80);
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search by name"));
		buttonPanel.add(addButton);buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);buttonPanel.add(resetButton);
		buttonPanel.add(searchPanel);
		
		tablePanel.add(scrollPane);
		
		inputPanel.setLayout(null);
		inputPanel.setBounds(10,10,460,200);
		inputPanel.setBorder(BorderFactory.createTitledBorder("Book Category information"));
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(470,10,420,200);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		tablePanel.setLayout(null);
		tablePanel.setBounds(10,215,880,355);
		tablePanel.setBorder(BorderFactory.createTitledBorder("List of book category"));
		mainPanel.add(inputPanel);
		mainPanel.add(buttonPanel);
		mainPanel.add(tablePanel);
		
	}
	private void employeeGUI() {
		mainPanel.removeAll();
	}
	private void customerGUI() {
		mainPanel.removeAll();
	}
	private void orderGUI() {
		mainPanel.removeAll();
	}
	private void statisticGUI() {
		mainPanel.removeAll();
	}
}
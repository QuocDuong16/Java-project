package BookStore;

import javax.swing.UIManager;

public class BookStore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new HomePage();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

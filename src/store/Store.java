package store;

import java.io.IOException;

import gui.LoginFrame;
import mgr.Factory;
import mgr.Manager;

public class Store {
	public static Manager<User> muserMgr = new Manager<>();
	public static Manager<User> userMgr = new Manager<>();
	public static Manager<Shoe> shoeMgr = new Manager<>();
	public static User myUser= new User();

	public static void main(String[] args) throws IOException {
		Store store = new Store();
		store.run();
	}

	private void run() throws IOException {
		userMgr.readAll("users.txt", new Factory<User>() {
			public User create() {
				return new User();
			}
		});
		
		muserMgr.readAll("Musers.txt", new Factory<User>() {
			public User create() {
				return new User();
			}
		});
		
		shoeMgr.readAll("shoes.txt", new Factory<Shoe>() {
			public Shoe create() {
				return new Shoe();
			}
		});
		
		LoginFrame frame =new LoginFrame();
		frame.setVisible(true);
	}
}
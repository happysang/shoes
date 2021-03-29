package member;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import store.Client;
import store.Store;
import store.Supervisor;
import store.User;

public class Account {
	Scanner scan = new Scanner(System.in);
	String checkId;
	String checkPwd;
	float myLength;
	char myWidth;
	User user;

	public User loginPage() {
		while (true) {
			System.out.println("---로그인 화면---");
			System.out.print("아이디를 입력하세요 >>");
			checkId = scan.next();
			System.out.print("비밀번호를 입력하세요 >>");
			checkPwd = scan.next();

			user = Store.userMgr.find(checkId);
			if(checkPwd.contains("manager"))
				user = Store.muserMgr.find(checkId);
			if (user == null) {
				System.out.println("존재하지 않는 아이디입니다.");
				continue;
			}
			if (user.matches(checkPwd)) {
				System.out.println("로그인 성공!");
				return user;
			}
			System.out.println("비밀번호가 일치하지 않습니다.");
		}
	}
	//오버로딩
	public User loginPage(String checkId, String checkPwd) {
		User user = null;
		user = Store.userMgr.find(checkId);
		if(checkPwd.contains("manager")) {
			user = Store.muserMgr.find(checkId);
		}
		if (user == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return null;
		}
		if (user.matches(checkPwd)) {
			System.out.println("로그인 성공!");
			return user;
		}
		return null;
	}

	public User signUp(String id, String pwd, String length, String width) throws IOException {
		User newUser = new User();
		
		newUser.id = id;
		newUser.pwd = pwd;

		if (newUser.pwd.contains("manager")) {
			newUser.managerAuth = true;
			Store.userMgr.mList.add(newUser);
			addUser(newUser);
			System.out.println("관리자 회원가입 완료");
			return new Supervisor(newUser.id, newUser.pwd, 0.0f, 'M', true);
		}
		else {
			myWidth = width.charAt(0);
			myLength = Float.parseFloat(length);
			newUser.myLength = myLength;
			newUser.myWidth = myWidth;

			Store.userMgr.mList.add(newUser);	
			addUser(newUser);
			System.out.println("회원가입 완료");
			return new Client(newUser.id, newUser.pwd, newUser.myLength, newUser.myWidth, newUser.managerAuth);
		}
	}
	
	public void addUser(User user) throws IOException {
		String textFileName = null;
		String contentToBeSaved = null;
		
		textFileName = "users.txt";
		
		if(user.managerAuth) {
			textFileName = "Musers.txt";
			user.myLength = 0;
			user.myWidth = 'X';
		}
		
		contentToBeSaved = user.id + " " + user.pwd + " " + user.myLength + " " + user.myWidth;
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(textFileName, true));
		bufferedWriter.write("\n" + contentToBeSaved);
		bufferedWriter.close();

		System.out.println("저장이 완료되었습니다.");
	}
	
}



//원래 Signup
//public User signUp() throws IOException {
//	User newUser = new User();
//
//	while (true) {
//		System.out.println("---회원가입---");
//		System.out.printf("가입할아이디를 입력하세요 (5자 미만으로 입력할 것) >>");
//		checkId = scan.next();
//		User checkUser = Store.userMgr.find(checkId);
//		if (checkId.length() >= 5) {
//			System.out.println("5자 미만으로 입력하세요!");
//			continue;
//		}
//		if (checkUser != null) {
//			System.out.println("이미 가입된 아이디입니다.");
//			continue;
//		}
//		System.out.printf("비밀번호를 입력하세요 (5자 이상으로 입력할 것) >>");
//		checkPwd = scan.next();
//		if (checkPwd.length() < 5) {
//			System.out.println("5자 이상으로 입력하세요!");
//			continue;
//		}
//		break;
//	}
//	newUser.id = checkId;
//	newUser.pwd = checkPwd;
//
//	if (newUser.pwd.contains("manager")) {
//		newUser.managerAuth = true;
//		Store.userMgr.mList.add(newUser);
//		addUser(newUser);
//		System.out.println("관리자 회원가입 완료");
//		return new Supervisor(newUser.id, newUser.pwd, newUser.myLength, newUser.myWidth, newUser.managerAuth);
//	} 
//	else {
//		System.out.printf("계정이 생성되었습니다.\n발 실측 길이를 입력하세요 >>");
//		myLength = scan.nextFloat();
//		System.out.printf("발 볼 사이즈를 입력하세요(L,M,S 중 하나 입력) >>");
//		myWidth = scan.next().charAt(0);
//
//		newUser.myLength = myLength;
//		newUser.myWidth = myWidth;
//
//		Store.userMgr.mList.add(newUser);
//		addUser(newUser);
//		System.out.println("회원가입 완료");
//		return new Client(newUser.id, newUser.pwd, newUser.myLength, newUser.myWidth, newUser.managerAuth);
//	}
//}
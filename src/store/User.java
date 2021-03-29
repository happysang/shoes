package store;

import java.util.Scanner;

import mgr.Manageable;

public class User implements Manageable {
	public String id;
	public String pwd;
	public boolean managerAuth = false;
	public float myLength;
	public char myWidth;
	
	public User() {
	}
	//생성자 추가
	public User(String[] add) {
		id = add[0];
		pwd = add[1];
		myLength = Float.parseFloat(add[2]);
		myWidth = add[3].charAt(0);
	}
	public User(String id, String pwd,float myLength,char myWidth,boolean managerAuth) {
		this.id = id;
		this.pwd = pwd;
		this.myLength = myLength;
		this.myWidth = myWidth;
		this.managerAuth = managerAuth;
	}
	
	@Override
	public User read(Scanner scan) {
		id = scan.next();
		pwd = scan.next();
		myLength = scan.nextFloat();
		myWidth = scan.next().charAt(0);
		if(pwd.contains("manager")) {
			managerAuth = true;
			return new Supervisor(id, pwd, myLength, myWidth, managerAuth);
		}
		else 
			return new Client(id, pwd, myLength, myWidth, managerAuth);
	}
	
	@Override
	public void print() {
		System.out.printf("아이디: %s \n", id);
	}
	
	public String[] getTexts() {
		return new String[] { id, pwd, ""+myLength, ""+myWidth}; 
	}
	//matches 함수 수정
	@Override
	public boolean matches(String kwd) {
		if(id.contains(kwd))
			return true;
		if(pwd.contains(kwd))
			return true;
		if(isStringFloat(kwd)) {
	    	  if(myLength == Float.valueOf(kwd)) {
	    		  return true;
	    	  }
	      }
		if(kwd.matches(""+myWidth))
			return true;
		return false;
	}
	//문자열이 실수인지 판단하고 참/거짓 반환
	public static boolean isStringFloat(String s) {
		try {
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
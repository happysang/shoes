package store;

import java.util.ArrayList;
import java.util.Scanner;

import store.Store;
import store.User;
import store.Client;
import store.Shoe;

public class Supervisor extends User {
	Scanner scan = new Scanner(System.in);
	
	public Supervisor(String id, String pwd, float myLength, char myWidth, boolean managerAuth) {
		super(id, pwd, myLength, myWidth, managerAuth);
	}
	
	public void userUpdate() { // 유저 데이터 수정 메소드
		Client client = null;
		Store.userMgr.printAll();
		System.out.print("수정할 고객의 아이디를 입력하시오 >>");
		String kwd = scan.next();
		
		try{
			client = (Client) Store.userMgr.find(kwd);
		}catch (Exception e) {
			System.out.println("매니저 계정은 변경할 수 없습니다.");
			return;
		}
		if (client == null) {
			System.out.println("해당 아이디를 가진 고객이 없습니다.");
			return;
		}
		if (client != null) {
			client.print();
			System.out.print("수정하고자 하는 번호를 선택하세요  1)아이디 2)비번 3)발길이 4)발볼  >>");
			int ch = scan.nextInt();
			switch (ch) {
			case 1:
				System.out.print("아이디를 새로 입력하세요 >>");
				client.id = scan.next();
				break;
			case 2:
				System.out.print("비밀번호를 새로 입력하세요 >>");
				client.pwd = scan.next();
				break;
			case 3:
				System.out.print("발길이를 새로 입력하세요 >>");
				client.myLength = scan.nextFloat();
				break;
			case 4:
				System.out.print("발볼을 새로 입력하세요 >>");
				client.myWidth = scan.next().charAt(0);
				break;
			default:
				System.out.println("[수정 실패] 1~4 사이의 숫자를 입력해주세요.");
				return;
			}
		}
		System.out.println("[수정 완료 !]");
//		client.print();
		Store.userMgr.printAll();
	}

	public void userDelete() { // 유저 데이터 삭제 메소드
		Client client = null;
		Store.userMgr.printAll();
		System.out.print("삭제할 고객의 아이디를 입력하시오. >>");
		String kwd = scan.next();
		try{
			client = (Client) Store.userMgr.find(kwd);
		}catch (Exception e) {
			System.out.println("매니저 계정은 삭제할 수 없습니다.");
			return;
		}
		
		if (client != null) {
			client.print();
			Store.userMgr.mList.remove(client);
			System.out.println("정상적으로 삭제완료");
			Store.userMgr.printAll();
			return;
		} else
			System.out.println("삭제 실패");
	}

	public void shoeRegister() { // 신발 데이터 등록 메소드
		Shoe shoe = new Shoe();
		//데이터 입력
		System.out.println("신발을 새로 등록합니다.");
		System.out.print("고유코드를 새로 입력하세요 >>");
		shoe.code = scan.next();
		
		System.out.print("브랜드를 새로 입력하세요 >>");
		shoe.brand = scan.next();
		
		System.out.print("종류를 새로 입력하세요 >>");
		shoe.category = scan.next();
		
		System.out.print("이름을 새로 입력하세요 >>");
		shoe.shoeName = scan.next();
		
		System.out.print("가격을 새로 입력하세요 >>");
		shoe.price = scan.nextInt();
		
		System.out.print("실측길이를 새로 입력하세요 >>");
		shoe.length = scan.nextFloat();
		
		System.out.print("발볼을 새로 입력하세요 >>");
		shoe.width = scan.next().charAt(0);
		
		Store.shoeMgr.mList.add(shoe);
		System.out.println("--신발이 정상 등록 되었습니다--");
		Store.shoeMgr.printAll();
	}

	public void shoeUpdate() { // 신발 데이터 수정 메소드
		Shoe shoe = null;
		Store.shoeMgr.printAll();
		System.out.print("수정하려는 신발의 키워드를 입력하시오. >>");
		String kwd = scan.next();
		
		ArrayList<Shoe> searchList = Store.shoeMgr.findList(kwd);
		if(searchList.size() == 0) {
		     System.out.println("검색된 상품이 없습니다.");
		     return;
		}
	      
	    for(Shoe s : searchList) { // 검색된 신발 출력
	         System.out.printf("[%d] ", searchList.indexOf(s)+1);
	         s.print();
	    }
	    
	    System.out.print("수정하려는 신발의 번호를 입력해주세요 >>");
	    int num = scan.nextInt();
	    try{
			shoe = searchList.get(num-1);
		}catch(Exception e) {
			System.out.println("검색목록에 입력한 번호에 해당하는 신발이 없습니다.");
			return;
		}
	    System.out.print("선택한 신발 - ");
	    shoe.print();
	    
		if (shoe != null) {
			System.out.print("\t 수정하고자 하는 번호를 선택 1)고유코드 2)브랜드 3)종류 4)이름 5)가격 6)실측길이 7)발볼 ");
			int ch = scan.nextInt();
			switch (ch) {
			case 1:
				System.out.print("고유코드를 새로 입력하세요 >>");
				shoe.code = scan.next();
				break;
			case 2:
				System.out.print("브랜드를 새로 입력하세요 >>");
				shoe.brand = scan.next();
				break;
			case 3:
				System.out.print("종류를 새로 입력하세요 >>");
				shoe.category = scan.next();
				break;
			case 4:
				System.out.print("이름을 새로 입력하세요 >>");
				shoe.shoeName = scan.next();
				break;
			case 5:
				System.out.print("가격을 새로 입력하세요 >>");
				shoe.price = scan.nextInt();
				break;
			case 6:
				System.out.print("실측길이를 새로 입력하세요 >>");
				shoe.length = scan.nextFloat();
				break;
			case 7:
				System.out.print("발볼을 새로 입력하세요 >>");
				shoe.width = scan.next().charAt(0);
				break;
			default:
				System.out.println("[수정 실패]1~7 숫자를 입력해주세요");
				return;
			}
		}
		System.out.println("신발 정보 수정 완료!");
		shoe.print();

	}

	public void shoeDelete() { // 신발 데이터 삭제 메소드
		Shoe shoe = null;
		System.out.print("삭제할 신발의 키워드를 입력하시오 >>");
		String kwd = scan.next();
		ArrayList<Shoe> searchList = Store.shoeMgr.findList(kwd);
		
		if(searchList.size() == 0) {
		   System.out.println("검색된 상품이 없습니다.");
		   return;
		}
		
		for(Shoe s : searchList) { // 검색된 신발 출력
	         System.out.printf("[%d] ", searchList.indexOf(s)+1);
	         s.print();
	    }
		
	    System.out.print("삭제하려는 신발의 번호를 입력해주세요 >>");
		int num = scan.nextInt();
		try{
			shoe = searchList.get(num-1);
		}catch(Exception e) {
			System.out.println("검색목록에 입력한 번호에 해당하는 신발이 없습니다.");
			return;
		}
		
		System.out.print("선택한 신발 - ");
		shoe.print();
		Store.shoeMgr.mList.remove(shoe);
		System.out.println("정상적으로 삭제 완료!");
	}
}

package store;


import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import gui.DetailInfo;

public class Client extends User {
   Scanner scan = new Scanner(System.in);
   Store store;
   public ArrayList<MyShoe> myShoeList = new ArrayList<>();
   int orderId;
   
   public Client(String id, String pwd, float myLength, char myWidth, boolean managerAuth) {
      super(id, pwd, myLength, myWidth, managerAuth);
   }

   
   @Override
   public void print() {
	   print(true);
   }
   
   void print(boolean idOnly) {
	  super.print();
	  
	  if(!idOnly) {
		  System.out.printf("나의 실측 길이 : %f \n", myLength);
		  System.out.printf("나의 실측 너비 : %s \n", myWidth);
	  }
   }

   public void myPage() throws IOException { // 등록되어 있는 발의 실측 길이와 너비 수정 메소드
      System.out.println("--나의 신발 구매 리스트--");
      printMylist();
      int input;
      System.out.print("발의 실측 길이와 너비를 수정하시겠습니까? (수정 : 1, 그 외의 버튼 입력시 종료) >>");
      input = scan.nextInt();
      if (input == 1) {
         System.out.print("발의 실측 길이를 입력해주세요 (소숫점까지입력가능) >>");
         myLength = scan.nextFloat();
         System.out.print("발의 너비를 입력해주세요(S, M, L) >>");
         myWidth = scan.next().charAt(0);
         System.out.println("수정되었습니다.");
      } else
         return;
   }
   
   public boolean buyShoes(Shoe selectedShoe) throws IOException{ //신발 구매 메소드(gui용)
	   MyShoe ms = new MyShoe(selectedShoe);
	   myShoeList.add(ms);
	   addOrder(ms, this);
	   System.out.println("--정상 구매되었습니다--");
	   printMylist();
	   return true;
   }

   public void buyShoes() throws IOException {   //신발 구매 메소드(콘솔)
      System.out.println("---구매 가능한 신발목록---");
      Store.shoeMgr.printAll();
      System.out.print("검색 키워드를 입력하세요. >>");
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
      System.out.print("구매하려는 신발의 번호를 입력해주세요. >>");
      int num = scan.nextInt();
      Shoe selectedShoe = null;
      try{
    	  selectedShoe = searchList.get(num-1);
		}catch(Exception e) {
			System.out.println("검색목록에 입력한 번호에 해당하는 신발이 없습니다.");
			return;
		}
      DetailInfo di = new DetailInfo(this, selectedShoe);// detailInfo 확인용
      selectedShoe.print();
      System.out.println("내 발에 맞는 사이즈 추천 : "+ selectedShoe.recommendSize(this)); //사이즈 추천
      selectedShoe.size = selectedShoe.chooseSize(scan);

      System.out.print("해당 신발을 구매하시려면  1을 입력하세요. (취소: 그 외 숫자) >>");
      int input = scan.nextInt();
      if (input == 1) {
         MyShoe ms = new MyShoe(selectedShoe);
         myShoeList.add(ms);
         System.out.println("--정상 구매되었습니다--");
         addOrder(ms, this);
         System.out.println("--나의 신발 구매 리스트--");
         printMylist();
      } else {
    	  System.out.println("구매를 취소합니다.");
    	  return;
      }
   }
   
   public void printMylist() {
	      for (MyShoe ms : myShoeList) {
	         ms.print();
	      }
	   }


   public void createReview() throws IOException{ // 리뷰 작성 메소드
      if (myShoeList.isEmpty()) {
         System.out.println("구매한 신발이 없습니다. 신발 구매 후 리뷰 메뉴를 이용해 주세요.");
         return;
      }
      displayReview();
      
      System.out.print("리뷰할 신발의 번호를 입력해 주세요 >>");
      int num = scan.nextInt();
      MyShoe reviewShoe = myShoeList.get(num-1); // 리뷰할 shoe 객체 받아옴
      System.out.print("리뷰를 입력하세요 >>");
      scan.nextLine();
      String rv = scan.nextLine();
      reviewShoe.setReview(rv);
      addReview(reviewShoe, this);
      System.out.println("--리뷰 작성 종료--");
   }
   
   public void displayReview() {
      System.out.println("--내가 작성 한 리뷰--");
      for (MyShoe ms : myShoeList) {
         System.out.printf("[%d] ", myShoeList.indexOf(ms)+1);
         if (!ms.review.equals("")) {
            System.out.println(ms.getName() + "(" + ms.getSize() + ")" + " 의 리뷰 : "+ ms.review);
         }
         else {
            System.out.println(ms.getName() + "(" + ms.getSize() + ")" + " 의 리뷰 : 리뷰 없음");
         }
      }
   }
   
   public void roadOrder() throws IOException { //주문 불러오기 메소드, 파일을 읽어 사용자의
	   File file = new File("order.txt");		//구매 목록을 불러온다
	   FileReader filereader = new FileReader(file);
	   BufferedReader bufReader = new BufferedReader(filereader);
	   String line = "";
	   String kwd;
	   while((line = bufReader.readLine()) != null) {
		    int check = 0;
		   	StringTokenizer token = new StringTokenizer(line," ", false);
		   	kwd = token.nextToken();
		   	if(this.id.equals(kwd)) {
		   		kwd = token.nextToken();
		   		MyShoe ms = new MyShoe((Shoe)Store.shoeMgr.find(kwd));
		   		ms.shoe.size = Integer.parseInt(token.nextToken());
		   		for(MyShoe m: myShoeList) {
		   			if(m.shoe.matches(ms.shoe.code)) {
		   				System.out.println("중복입니다.-"+ms.shoe.shoeName);
		   				check = 1;
		   			}
		   		}
		   		if(check == 0) {
		   			System.out.println("추가함-"+ms.shoe.shoeName);
		   			myShoeList.add(ms);
		   		}
		   	}
	   }
   }
   
   public void roadReview() throws IOException { //리뷰 불러오기 메소드, 위와 동일
	   File file = new File("review.txt");
	   FileReader filereader = new FileReader(file);
	   BufferedReader bufReader = new BufferedReader(filereader);
	   String line = "";
	   String kwd;
	   while((line = bufReader.readLine()) != null) {
		   	StringTokenizer token = new StringTokenizer(line, " ", false);
		   	kwd = token.nextToken();
		   	if(this.id.equals(kwd)) {
		   		kwd = token.nextToken();
		   		for(MyShoe s: myShoeList) {
		   			if(s.shoe.shoeName.equals(kwd)) {
		   				kwd = token.nextToken();
		   				while(token.hasMoreTokens()) {
		   					kwd = kwd.concat(" "+token.nextToken());
		   				}
		   					s.setReview(kwd);
		   				
		   			}
		   		}
		   	}
	   }
   }
   // order.txt에 주문을 추가하는 메소드, 주문 번호는 삭제하고 아이디와 신발이름, 사이즈를 저장한다.
   public void addOrder(MyShoe orderedShoe, User user) throws IOException {
	   String order = null;
	   order = user.id + " " + orderedShoe.shoe.shoeName + " " + orderedShoe.shoe.size;
	   BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("order.txt", true));
	   bufferedWriter.write(order + "\n");
	   bufferedWriter.close();
   }
   // review.txt에 리뷰를 추가하는 메소드, 유저아이디와 신발이름, 리뷰를 저장한다. 
   public void addReview(MyShoe shoe, User user) throws IOException {
	   String reviewList = null;
	   reviewList = user.id + " " + shoe.shoe.shoeName + " " +shoe.review;
	   BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("review.txt", true));
	   bufferedWriter.write(reviewList + "\n");
	   bufferedWriter.close();
   }
	public class MyShoe{ //내부클래스
	    Shoe shoe;
	    public String review;
	    
	    MyShoe(Shoe shoe) {
	       this.shoe = shoe;
	       this.review = "없음";
	    }
	    
	    void print() {
	       shoe.print(false);
	    }
	    
	    void setReview(String review) {
	       this.review = review;
	    }
	    
	    String getName() {
	       return shoe.shoeName;
	    }
	    
	    int getSize() {
	    	return shoe.size;
	    }
	    
	   @Override
       public String toString() {
         return shoe.brand + " " + shoe.shoeName + " " + shoe.size + " - " + this.review;
       }
    }

}
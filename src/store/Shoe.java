package store;

import java.util.Scanner;
import mgr.Manageable;

public class Shoe implements Manageable {
   String code; //고유코드
   String brand; //브랜드
   String category; //종류
   String shoeName; //신발이름
   int price; //가격
   int size=280; //사이즈
   float length; //실측길이(280사이즈기준)
   char width; //발볼 (S / M / L) 3가지
   float diff;
   
   public Shoe() {
      
   }
   //생성자 추가
   public Shoe(String[] add) {
      code = add[0];
      brand = add[1];
      category = add[2];
      shoeName = add[3];
      price = Integer.parseInt(add[4]);
      length = Float.parseFloat(add[5]);
      width = add[6].charAt(0);
   }
   @Override
   public Shoe read(Scanner scan) {
     code = scan.next();
      brand = scan.next();
      category = scan.next();
      shoeName = scan.next();
      price = scan.nextInt();
      length = scan.nextFloat();
      width = scan.next().charAt(0);
      
      diff = length-280;
      
      return this;
   }
   @Override
   public void print() { //일반출력과 내 구매한 신발 출력을 다르게 해주기 위함. (코드 -> 사이즈)
         print(true);
      }
   void print(boolean sizeDetail) {
         if (sizeDetail) {
            System.out.printf("[%s] %s %s-%s (%d)원 \n", code, category, brand, shoeName, price);
         }
         if (!sizeDetail) {
            System.out.printf("(%d) %s-%s (%d)원 \n", size, brand, shoeName, price);
         }
      }

   public String getCode() {
      return this.code;
   }
   public String getShoeName() {
      return this.shoeName;
   }
   
   public void setSize(int size) {
      this.size = size;
   }
   public int getPrice(){
      return this.price;
   }
   @Override
   public boolean matches(String kwd) {
      if (shoeName.contains(kwd) && kwd.length() >= 2)
          return true;
      if (brand.contains(kwd))
         return true;
      if (category.contains(kwd))
         return true;
      if (code.contains(kwd) && kwd.length() > 2)
         return true;
      if(kwd.matches("[0-9]+")) {
         int num = Integer.valueOf(kwd);
         if(price == num) return true;
      }
      if(isStringFloat(kwd)) {
         if(length == Float.valueOf(kwd)) {
            return true;
         }
      }
      if(kwd.matches(""+width))
         return true;
       return false;
   }
   
   public boolean matches (String[] kwdArr) {
      for (String kwd: kwdArr) {
         if (!matches(kwd))
            return false;
      }
      return true;
   }
    
   public int recommendSize (Client client) { //user발길이, 실측길이
      int size = 0;
      float myLength = client.myLength;
      char myWidth = client.myWidth;
      
      size += ((int)myLength / 10) * 10;
      
      if (myLength % 10 >= 3 && myLength % 10 < 7) {
         size+=5;
      }
      if (myLength%10>=7) {
         size+=10;
      }
      
      switch (myWidth-width) { // L : 76 M : 77 S : 83 ASCII
      case -1: //L-M
      case -6: //M-S
      case -7: //L-S
         size +=15;
         break;
      
      case 1: //M-L
      case 6: //S-M
      case 7: //S-L
         size+=5;
         break;

      case 0: //same
         size +=10;
         break;
      }
      //추천사이즈 발볼까지 처리완료
      
      //실측차이에 따른 사이즈 추천 변화 (마무리)
      if ((int)diff >= 2) {
         size -= 5;
      }
      else if ((int)diff > -2) {
         size += 0;
      }
      else if ((int)diff <-2) {
         size += 5;
      }
      return size;
   }
   
   public int chooseSize(Scanner scan) {
       while(true) {
          System.out.print("사이즈를 입력하세요 : [220 ~ 300] 5단위로 선택가능 >>");
         int size = scan.nextInt();
          if (size % 5 != 0) {
             System.out.println("[재입력바람] 5단위로만 구매 가능합니다.");
             continue;
          }
          return size;
       }
   }
   //JTable의 row 반환
   public String[] getTexts() {
      return new String[] { code, brand, category, shoeName, ""+price};
   }
   
   public String[] getAllTexts() {
      return new String[] { code, brand, category, shoeName, ""+price, ""+length, ""+width};
   }
   //실수인지 확인하는 메소드
   public static boolean isStringFloat(String s) {
      try {
         Float.parseFloat(s);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }
   
}
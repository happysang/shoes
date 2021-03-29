package mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import store.Shoe;

public class Manager<T extends Manageable> {
   public ArrayList<T> mList = new ArrayList<>();
   
   public T find(String kwd) {
       for (T m: mList)
          if (m.matches(kwd))
             return m;
       return null;
   }
   
   public ArrayList<T> findList(String kwd){
      ArrayList<T> matchedList = new ArrayList<>();
      for (T m: mList)
          if (m.matches(kwd))
             matchedList.add(m);
      return matchedList;
   }
   
   public void readAll(String filename, Factory<T> fac) {
      Scanner filein = openFile(filename);
      T m = null;
      while (filein.hasNext()) {
         m = fac.create();
         m = (T) m.read(filein);
         mList.add(m);
      }
      filein.close();
   }

   public void printAll() {
      for (T m : mList) {
         m.print();
      }
   }
   
   public void search(Scanner keyScanner) {
      String kwd = null;
      while (true) {
         System.out.print(">> ");
         kwd = keyScanner.next();
         if (kwd.equals("end"))
            break;
         for (T m : mList) {
            if (m.matches(kwd))
               m.print();
         }
      }
   }
   //리스트를 반환
   public ArrayList<T> getList(){
      return mList;
   }
   //문자열을 받아서 검색
   public ArrayList<T> search(ArrayList<String> kwds) {   
      boolean success = true;
      ArrayList<T> search = new ArrayList<>();
      //널문자는 검색어에 포함하지 않음
      for(int i=0; i<kwds.size() ; i++) {
         String temp = kwds.get(i);
         if(temp==null) {
            kwds.remove(temp);
            i--;
         }
      }
      if(kwds.size()==0) return (ArrayList<T>) search;
      //
//      for(Manageable<T> m : mList) {
//         T s = (T) m;
//         success = true;
//         for(String kwd : kwds) {
//            if(s.matches(kwd)) search.add(s);
//         }
//         
//      }
      for(Manageable<T> m : mList) {
         T s = (T) m;
         success = true;
         for(String kwd : kwds) {
            if(!s.matches(kwd)) success=false;   
         }
         if(success == true) search.add(s);
      }
      return search;
   }   
   
   public Scanner openFile(String filename) {
      Scanner filein = null;
      try {
         filein = new Scanner(new File(filename));
      } catch (Exception e) {
         System.out.println(filename + ":  open failed");
         System.exit(0);
      }
      return filein;
   }
}
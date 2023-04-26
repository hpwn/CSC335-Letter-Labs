package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CreateAccountFile {
   public static void main(String[] args) {
      String fileName = "accounts.ser";
    //  ArrayList<String> list = new ArrayList<String>();
    //  list.add("test");
   //   list.add("testtwo");
      
      try {
         FileOutputStream fos = new FileOutputStream(fileName);
         ObjectOutputStream oos = new ObjectOutputStream(fos);
     //    oos.writeObject(list);
         oos.close();
         System.out.println("File created: " + fileName);
      } catch (IOException e) {
         System.out.println("Failed to create file: " + e.getMessage());
      }
   }
}
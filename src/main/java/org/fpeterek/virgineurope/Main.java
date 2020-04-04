package org.fpeterek.virgineurope;

import org.fpeterek.virgineurope.orm.Database;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello, World!");

    try {
      Database db = new Database();
      var res = db.select(Airport.class);
      res.forEach(System.out::println);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      System.out.println("Zpíčená java kurva jasdffioasdfjop");
    }

  }

}

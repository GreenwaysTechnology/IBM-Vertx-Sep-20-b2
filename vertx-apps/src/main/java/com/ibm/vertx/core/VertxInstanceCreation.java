package com.ibm.vertx.core;

import io.vertx.core.Vertx;

public class VertxInstanceCreation {
  public static void main(String[] args) {
    //create vertx instance
    Vertx vertxInstance = Vertx.vertx();
    System.out.println(vertxInstance.getClass().getName());

  }
}

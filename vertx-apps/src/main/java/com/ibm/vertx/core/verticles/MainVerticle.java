package com.ibm.vertx.core.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;


public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(MainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Main Verticle");
    vertx.deployVerticle(UserVerticle.class.getName());
  }
}

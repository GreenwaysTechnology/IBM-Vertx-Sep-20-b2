package com.ibm.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.example.util.Runner;

public class GreeterVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    //Get Vertx(container)
    Vertx vertxIns = Vertx.vertx();
    //you have method called deploy
    //way -1 - create verticle object new
    vertxIns.deployVerticle(new GreeterVerticle());
    //way -2 : passing class name
    vertxIns.deployVerticle(GreeterVerticle.class.getName());
    //way 3; string class name
    vertxIns.deployVerticle("com.ibm.vertx.core.GreeterVerticle");
    //way 4 : via utility class
    Runner.runExample(GreeterVerticle.class);

  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter start");
  }
}

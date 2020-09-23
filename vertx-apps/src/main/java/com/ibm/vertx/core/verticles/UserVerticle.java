package com.ibm.vertx.core.verticles;

import io.vertx.core.AbstractVerticle;

public class UserVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("User Verticle");
  }
}

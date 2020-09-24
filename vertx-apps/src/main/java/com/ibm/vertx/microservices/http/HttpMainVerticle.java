package com.ibm.vertx.microservices.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class HttpMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HttpMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ProviderServiceVerticle());
    vertx.deployVerticle(new ConsumerServiceVerticle());
  }
}

package com.ibm.vertx.microservices.distributed;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;

public class HighAvailabilityVerticle extends AbstractVerticle {
  public HighAvailabilityVerticle() {
    System.out.println("Constrctor is called");
  }

  @Override
  public void start() throws Exception {
    super.start();
    HttpServer server = vertx.createHttpServer();

    server.requestHandler(req -> {
      long id = Thread.currentThread().getId();
      long instanceName = this.getClass().hashCode();

      req.response().end("<h1> I am coming from " + id + " Instance " + instanceName);
    });

    server.listen(8888, "localhost", handler -> {
      long instanceName = this.getClass().hashCode();

      if (handler.succeeded()) {
        System.out.println("Server is Ready! " + Thread.currentThread().getId() + " " + instanceName);
      } else {
        System.out.println("Server failed to Start");
      }
    });
  }
}

package com.ibm.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class SendHtml extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(SendHtml.class);
  }


  public void createFluentServer() {
    vertx.createHttpServer()
      .requestHandler(request -> request
        .response()
        .putHeader("content-type", "text/html")
        .end("<html><body><h1>Hello from vert.x!</h1></body></html>"))
      .listen(3001, serverHandler -> {
        if (serverHandler.succeeded()) {
          System.out.println("Server is ready at " + serverHandler.result().actualPort());
        } else {
          System.out.println(serverHandler.cause());
        }
      });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //createServer();
    createFluentServer();
  }
}

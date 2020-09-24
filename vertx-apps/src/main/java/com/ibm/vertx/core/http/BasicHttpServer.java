package com.ibm.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class BasicHttpServer extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicHttpServer.class);
  }

  public void createServer() {
    //create server object
    HttpServer httpServer = vertx.createHttpServer();

    //request handling
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      //send a response
      response.end("Hello Vertx Web Server");
    });


    //start the server
    httpServer.listen(3000, serverHandler -> {
      if (serverHandler.succeeded()) {
        System.out.println("Server is ready at " + serverHandler.result().actualPort());
      } else {
        System.out.println(serverHandler.cause());
      }
    });
  }

  public void createFluentServer() {
    vertx.createHttpServer()
      .requestHandler(request -> request.response().end("Hello Vertx Web Server"))
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

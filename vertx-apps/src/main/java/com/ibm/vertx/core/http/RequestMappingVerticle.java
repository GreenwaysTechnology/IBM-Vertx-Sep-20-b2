package com.ibm.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class RequestMappingVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(RequestMappingVerticle.class);
  }

  public void simpleRouter() {
    //create server object
    HttpServer httpServer = vertx.createHttpServer();
    //request handling
    httpServer.requestHandler(request -> {
      System.out.println(request.uri() + " " + request.method());
      HttpServerResponse response = request.response();

      if (request.uri().equals("/") && request.method() == HttpMethod.GET) {
        //send a response
        response.putHeader("content-type", "text/html").end("<h1>Home</h1>");
      }

      if (request.uri().equals("/api/hello") && request.method() == HttpMethod.GET) {
        //send a response
        response.putHeader("content-type", "text/html").end("<h1>Hello Vertx Web Server</h1>");
      }
      if (request.uri().equals("/api/hai") && request.method() == HttpMethod.GET) {
        //send a response
        response.putHeader("content-type", "text/html").end("<h1>Hai Vertx Web Server</h1>");
      }

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


  @Override
  public void start() throws Exception {
    super.start();
    simpleRouter();
  }
}

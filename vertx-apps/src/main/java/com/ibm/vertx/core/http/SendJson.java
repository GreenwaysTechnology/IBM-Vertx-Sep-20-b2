package com.ibm.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class SendJson extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(SendJson.class);
  }
  public void sendJson() {
    //create server
    HttpServer httpServer = vertx.createHttpServer();
    //start handling request-response in non blocking
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.putHeader("content-type", "application/json");
      JsonObject message = new JsonObject()
        .put("name", "Subramanian")
        .put("message", "welcome");
      response.end(message.encodePrettily());
    });
    //start the server
    httpServer.listen(3000, handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is up " + handler.result().actualPort());
      } else {
        System.out.println("Server is down!!!" + handler.cause());
      }
    });
  }
  @Override
  public void start() throws Exception {
    super.start();
    sendJson();
  }
}

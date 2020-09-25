package com.ibm.vertx.rx;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.http.HttpServerResponse;

class ReactiveHttpServer extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    HttpServer httpServer = vertx.createHttpServer();
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.end("Hello Reactive World");
    });

    httpServer.rxListen(3000).subscribe(ar -> {
      System.out.println("Server is Listening " + ar.actualPort());
    }, err -> {
      System.out.println(err.getMessage());
    });
  }
}


public class ReactiveHelloWorldVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ReactiveHelloWorldVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //future / functional style
//    vertx.deployVerticle(new ReactiveHttpServer(), asyncResult -> {
//      if (asyncResult.succeeded()) {
//        System.out.println(asyncResult.result());
//      } else {
//        System.out.println(asyncResult.cause());
//      }
//    });
    //rx style
    vertx.rxDeployVerticle(new ReactiveHttpServer())
      .subscribe(System.out::println, System.out::println);
  }
}

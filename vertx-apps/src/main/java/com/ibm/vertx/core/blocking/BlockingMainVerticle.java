package com.ibm.vertx.core.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class ServerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Ensure server verticle deployement when httpserver succfully starts
    vertx.createHttpServer()
      .requestHandler(request -> request.response()
        .end("test"))
      .listen(3000, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          //ensure start method is finished
          startPromise.complete();
        } else {
          startPromise.fail("Failed ");
        }
      });
  }
}

class MyVerticle extends AbstractVerticle {
  public void blockMe() {
    try {
      Thread.sleep(10000);
    } catch (InterruptedException exception) {
      System.out.println(exception);
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    blockMe();
    System.out.println(Thread.currentThread().getName());
  }
}

public class BlockingMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BlockingMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(Thread.currentThread().getName());
    // for (int i = 0; i < 25; i++)
    DeploymentOptions options = new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolSize(10);
    vertx.deployVerticle(new MyVerticle(), options);
    // vertx.deployVerticle(new MyVerticle(), new DeploymentOptions().setWorker(true));
    vertx.deployVerticle(new MyVerticle(), options, completionHandler -> {
      if (completionHandler.succeeded()) {
        System.out.println("Verticle Deployed : " + completionHandler.result());
      } else {
        System.out.println("Verticle not deployed " + completionHandler.cause());
      }
    });

    //
    vertx.deployVerticle(new ServerVerticle(), completionHandler -> {
      if (completionHandler.succeeded()) {
        System.out.println("Verticle Deployed : " + completionHandler.result());
      } else {
        System.out.println("Verticle not deployed " + completionHandler.cause());
      }
    });
  }
}

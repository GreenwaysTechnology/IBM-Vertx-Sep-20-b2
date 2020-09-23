package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;

class FutureVerticle extends AbstractVerticle {

  //create empty future; no response return
  public Future<Void> getEmptyFuture() {
    //create Future object
    Future<Void> future = Future.future();
    //empty response injection
    future.complete();
    return future;
  }

  //how to send success results : can be any type
  public Future<String> getSuccessFuture() {
    //create Future object
    Future<String> future = Future.future();
    //empty response injection
    future.complete("Hello I am fututure result!!");
    return future;
  }

  //how to send failure reponse;
  public Future<String> getFailedFuture() {
    //create Future object
    Future<String> future = Future.future();
    //empty response injection
    future.fail("Some thing went wrong");
    return future;
  }

  //how to send success or failure based on biz logic
  public Future<String> validate(String userName, String password) {
    //create Future object
    Future<String> future = Future.future();

    //biz logic
    if (userName.equals("admin") && password.equals("admin")) {
      future.complete("Login Success");
    } else {
      future.fail("Login Failed");
    }

    return future;
  }

  //facotry api
  public Future<String> validateUsingFactory(String userName, String password) {
    //biz logic
    if (userName.equals("admin") && password.equals("admin")) {
      return Future.succeededFuture("Login success");
    } else {
      return Future.failedFuture("Login Failed");
    }
  }

  //function as parameter pattern
  public void validateWithFunction(Handler<AsyncResult<String>> aHandler) {
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      aHandler.handle(Future.succeededFuture("login success"));
    } else {
      aHandler.handle(Future.failedFuture("Login Failed"));
    }

  }


  @Override
  public void start() throws Exception {
    super.start();
    //handle empty the response
    if (getEmptyFuture().succeeded()) {
      System.out.println("got empty response");
    }
    //set handler; using anonmous inner class
    getSuccessFuture().setHandler(new Handler<AsyncResult<String>>() {
      @Override
      public void handle(AsyncResult<String> asyncResult) {
        //value access
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result());
        } else {
          System.out.println(asyncResult.cause());
        }
      }
    });
    //set handler; using lambda expression
    getSuccessFuture().setHandler(asyncResult -> {
      //value access
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    //onComplete handler
    getSuccessFuture().onComplete(asyncResult -> {
      //value access
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    //onSuccess will give direct result
    getSuccessFuture().onSuccess(response -> {
      System.out.println(response);
    });
    getSuccessFuture().onSuccess(System.out::println);

    //handle failures
    getFailedFuture().onComplete(asyncResult -> {
      if (asyncResult.failed()) {
        System.out.println(asyncResult.cause());
      }
    });
    getFailedFuture().onFailure(System.out::println);

    //login
    validate("admin", "admin").onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    validate("foo", "foo")
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

    validateUsingFactory("admin", "admin")
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

    //funciton as parameter ; handler as a parameter
    validateWithFunction(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });

  }
}


public class FutureMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FutureMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FutureVerticle());
  }
}

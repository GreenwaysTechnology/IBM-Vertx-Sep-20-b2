package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class CallbackVerticle extends AbstractVerticle {

  //getUser--->login--->other---untill you get final

  public Future<String> getUser() {
    Promise promise = Promise.promise();
    String userName = "admin";
    if (userName != null) {
      //output
      promise.complete(userName);
    } else {
      promise.fail(new RuntimeException("User not found"));
    }
    return promise.future();
  }

  public Future<String> login(String userName) {
    Promise promise = Promise.promise();
    if (userName.equals("admin")) {
      //output
      promise.complete("login success");
    } else {
      promise.fail(new RuntimeException("login failed"));
    }
    return promise.future();
  }

  public Future<String> showPage(String status) {
    Promise promise = Promise.promise();
    if (status.equals("login success")) {
      //output
      promise.complete("Welcome to Admin page");
    } else {
      promise.fail(new RuntimeException("You are inside Guest page"));
    }
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getUser().onComplete(userasync -> {
      if (userasync.succeeded()) {
        System.out.println("getUser is called");
        login(userasync.result()).onComplete(loginasync -> {
          if (loginasync.succeeded()) {
            System.out.println("login is called");
            showPage(loginasync.result()).onComplete(pageasync -> {
              System.out.println("get page is called");
              if (pageasync.succeeded()) {
                System.out.println(pageasync.result());
              } else {
                System.out.println(pageasync.cause());
              }
            });
          } else {
            System.out.println(loginasync.cause());
          }
        });
      } else {
        System.out.println(userasync.cause());
      }
    });
  }
}

public class CallbackChainingVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackChainingVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new CallbackVerticle());
  }
}

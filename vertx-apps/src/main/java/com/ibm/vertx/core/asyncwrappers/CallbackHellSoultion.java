package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class CallbackHellVerticle extends AbstractVerticle {

  public Future<String> getUser() {
    Promise<String> promise = Promise.promise();
    //biz
    String userName = "admin";
    if (userName != null) {
      promise.complete(userName);
    } else {
      promise.fail("User not found");
    }

    return promise.future();
  }

  public Future<String> login(String userName) {
    Promise<String> promise = Promise.promise();
    //biz
    if (userName.equals("admin")) {
      promise.complete("login success");
    } else {
      promise.fail("login failed");
    }
    return promise.future();
  }

  public Future<String> page(String status) {
    Promise<String> promise = Promise.promise();
    //biz
    if (status.equals("login success")) {
      promise.complete("Admin Page");
    } else {
      promise.fail("Guest Page");
    }
    return promise.future();
  }

  public void callbackHellCode() {
    getUser().onComplete(event -> {
      if (event.succeeded()) {
        System.out.println("getUser is called ");
        login(event.result()).onComplete(loginevent -> {
          if (loginevent.succeeded()) {
            System.out.println("login is called");
            page(loginevent.result()).onComplete(pageevent -> {
              System.out.println("Page is called");
              if (pageevent.succeeded()) {
                System.out.println(pageevent.result());
              } else {
                System.out.println(pageevent.cause());
              }
            });
          } else {
            System.out.println(loginevent.cause());
          }
        });
      } else {
        System.out.println(event.cause());
      }
    });
  }

  //compose method of Future / Promise eleminate the complexity of callback nesting
  public void compose() {
    getUser()
      .compose(userName -> {
        return login(userName);
      })
      .compose(status -> {
        return page(status);
      })
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

    getUser()
      .compose(userName -> login(userName))
      .compose(status -> page(status))
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    getUser()
      .compose(this::login)
      .compose(this::page)
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

  }


  @Override
  public void start() throws Exception {
    super.start();
    //callbackHellCode();
    compose();
  }
}


public class CallbackHellSoultion extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackHellSoultion.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new CallbackHellVerticle());
  }
}

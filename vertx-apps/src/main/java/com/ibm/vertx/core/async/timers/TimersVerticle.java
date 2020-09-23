package com.ibm.vertx.core.async.timers;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import java.util.Date;

public class TimersVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(TimersVerticle.class);
  }

  //delay
  public Future<JsonObject> delay(long duration) {
    Promise<JsonObject> promise = Promise.promise();
    //trigger nonblocking api, wait for result,
    vertx.setTimer(duration, asyncResult -> {
      //do something once timer has fired
      promise.complete(new JsonObject().put("message", "Hello i am delayed Message"));
    });
    return promise.future();
  }

  //heart beat
  public void heartBeat(Handler<AsyncResult<String>> aHandler) {
    long timerId = vertx.setPeriodic(1000, heartBeatValue -> {
      //send something every 1000ms
      aHandler.handle(Future.succeededFuture(new Date().toString()));
    });
    //stop the timer based on timerId;
    vertx.setTimer(8000, stopvalue -> {
      System.out.println("Stopping timer");
      vertx.cancelTimer(timerId);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //blocking coding
    System.out.println("start");
    delay(5000).onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result().encodePrettily());
      }
    });
    heartBeat(now -> {
      System.out.println(now.result());
    });

    System.out.println("end");
  }
}

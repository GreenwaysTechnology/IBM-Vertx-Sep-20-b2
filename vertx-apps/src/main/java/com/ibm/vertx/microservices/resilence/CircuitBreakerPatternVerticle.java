package com.ibm.vertx.microservices.resilence;

import com.ibm.vertx.microservices.common.BaseMicroServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

class SomeServiceVerticle extends AbstractVerticle {

  public void slowService() {
    vertx.createHttpServer().requestHandler(request -> {
      vertx.<String>executeBlocking(promise -> {
        // Do the blocking operation in here
        // Imagine this was a call to a blocking API to get the result
        try {
          Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        String result = "hello , i am coming late , can you wait for me?";
        promise.complete(result);

      }, res -> {

        if (res.succeeded()) {
          request.response().putHeader("content-type", "text/plain").end(res.result());

        } else {
          res.cause().printStackTrace();
        }
      });

    }).listen(3000);
  }

  @Override
  public void start() throws Exception {
    super.start();
    slowService();
  }
}


public class CircuitBreakerPatternVerticle extends BaseMicroServiceVerticle {
  public static void main(String[] args) {
    Runner.runExample(CircuitBreakerPatternVerticle.class);
  }

  public void testCircuitBreaker() {
    circuitBreaker.executeWithFallback(future -> {
      //risky code
      WebClient client = WebClient.create(vertx);
      client.get(3000, "localhost", "/").send(ar -> {
        // Obtain response
        HttpResponse<Buffer> response = ar.result();
        if (response.statusCode() != 200) {
          future.fail("HTTP error");
        } else {
          future.complete(response.bodyAsString());
        }
      });
    }, v -> {
      // Executed when the circuit is opened
      return "Hello, I am fallback";
    }).onComplete(ar -> {
      // Do something with the result
      if (ar.succeeded()) {
        System.out.println(ar.result());
      } else {
        System.out.println(ar.cause());

      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new SomeServiceVerticle());
    testCircuitBreaker();
  }
}

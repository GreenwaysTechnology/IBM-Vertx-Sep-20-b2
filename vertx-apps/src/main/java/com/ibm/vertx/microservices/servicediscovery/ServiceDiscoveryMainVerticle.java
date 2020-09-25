package com.ibm.vertx.microservices.servicediscovery;
import com.ibm.vertx.microservices.common.BaseMicroServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

class HttpServerVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    vertx.createHttpServer().requestHandler(request -> {
      if (request.path().equals("/api/hello")) {
        request.response().end("Hello");
      }
    }).listen(3000);
  }
}
class PublisherVerticle extends BaseMicroServiceVerticle {
  public void publish() {
    publishRecord("hello-service", "localhost", false, 3000, "/api/hello");
  }

  @Override
  public void start() throws Exception {
    super.start();
    publish();
  }
}

class ConsumerVerticle extends BaseMicroServiceVerticle {
  public void getRecord() {
    vertx.setTimer(2000, ar -> {
      //Get The Service Instance from the Service
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "hello-service"), sar -> {
        WebClient client = sar.result();
        client.get("/api/hello").send(res -> {
          System.out.println("Response is ready!");
          System.out.println(res.result().bodyAsString());
          //remove /release discovery record
          ServiceDiscovery.releaseServiceObject(discovery, client);
        });
      });

    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    getRecord();
  }
}


public class ServiceDiscoveryMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceDiscoveryMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new HttpServerVerticle());
    vertx.deployVerticle(new ConsumerVerticle());
    vertx.deployVerticle(new PublisherVerticle());

  }
}

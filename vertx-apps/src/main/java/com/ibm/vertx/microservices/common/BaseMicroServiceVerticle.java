package com.ibm.vertx.microservices.common;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class BaseMicroServiceVerticle extends AbstractVerticle {
  //ServiceDiscovery Options which helps configure zoo keeper information
  protected ServiceDiscoveryOptions discoveryOptions;
  //Service Discovery Instance creation which helps publish , discover and unpublish
  protected ServiceDiscovery discovery;
  protected CircuitBreakerOptions options;
  protected CircuitBreaker circuitBreaker;

  private void initCb() {
    options = new CircuitBreakerOptions();
    options.setMaxFailures(2);// no of failures will be allowed , after that , ciruit will open
    options.setTimeout(6000); // consider a failure if the operation deos not succeed in time
    options.setFallbackOnFailure(true); // if any failure, should i handle fallback or not
    options.setResetTimeout(5000); // time spent in open state before attempting to retry.
    circuitBreaker = CircuitBreaker.create("my-circuit-breaker", vertx, options);

  }

  @Override
  public void start() throws Exception {
    super.start();
    initCb();
    discoveryOptions = new ServiceDiscoveryOptions();
    //enable discovery server : apache zoo keeper
    discoveryOptions.setBackendConfiguration(new JsonObject()
      .put("connection", "127.0.0.1:2181")
      .put("ephemeral", true)
      .put("guaranteed", true)
      .put("basePath", "/services/my-backend")
    );
    discovery = ServiceDiscovery.create(vertx, discoveryOptions);
  }

  protected void publishRecord(String name, String host, boolean ssl, int port, String root) {
    Record httpEndPointRecord = HttpEndpoint.createRecord(name,
      ssl, host, port, root, new JsonObject());
    //publish HttpEndpoint
    discovery.publish(httpEndPointRecord, ar -> {
      if (ar.succeeded()) {
        System.out.println("Successfully published to Zookeeper...>>>>" + ar.result().toJson());
      } else {
        System.out.println(" Not Published " + ar.cause());
      }
    });
  }
}

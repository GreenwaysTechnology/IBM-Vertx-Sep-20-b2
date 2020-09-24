package com.ibm.vertx.microservices.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class ExternalizeConfigVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(config().encodePrettily());
  }
}


class FileSystemConfigVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "conf/config.json"));
    ConfigRetriever retriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(options));

    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.getString("appname"));
        System.out.println(configRes.getString("version"));

      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });


  }
}

//
class ApplicationConfigVerticle extends AbstractVerticle {


  @Override
  public void start() throws Exception {
    super.start();
    //read config
    JsonObject config = config();
    System.out.println(config.encodePrettily());
    System.out.println(config().getString("name"));
    System.out.println(config().getString("message", "default Message"));

    vertx.createHttpServer()
      .requestHandler(request -> {
        request.response().end(config().getString("message", "You are lucky!!"));
      })
      .listen(config().getInteger("http.port", 3000));

    //env and system
    System.out.println(System.getProperty("java.home"));
    System.out.println(System.getProperty("path.separator"));
    System.out.println(System.getenv("path"));

  }
}


public class VerticleConfiguration extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(VerticleConfiguration.class);
  }

  public Future<JsonObject> getConfig() {
    Promise<JsonObject> promise = Promise.promise();
    //store options
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "conf/config.json"));
    //config reteriver
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));
    //read configuration
    retriever.getConfig(config -> {
      if (config.succeeded()) {
        promise.complete(config.result());
      } else {
        promise.fail("Config Error : " + config.cause());
      }
    });
    return promise.future();

  }

  @Override
  public void start() throws Exception {
    super.start();
//    JsonObject serverConfig = new JsonObject()
//      .put("http.port", 3000)
//      .put("http.host", "locahost")
//      .put("http.ssl", false);
//
//    JsonObject applicationConfig = new JsonObject()
//      .put("name", "Subramanian")
//      .put("city", "Coimbatore")
//      .put("state", "TN")
//      .mergeIn(serverConfig);
//    DeploymentOptions options = new DeploymentOptions();
//    options.setConfig(applicationConfig);
//    vertx.deployVerticle(new ApplicationConfigVerticle(), options, dep -> {
//      if (dep.succeeded()) {
//        System.out.println(dep.result());
//      }
//    });
//
//    vertx.deployVerticle(new FileSystemConfigVerticle());

    //////////////////////////////////////////////////////////////////////
    getConfig().onSuccess(myconfig -> {
      //  System.out.println(myconfig.encodePrettily());
      JsonObject config = new JsonObject()
        .put("name", "Subramnaian")
        .put("message", "Hello!!")
        .put("port", 8082)
        .mergeIn(myconfig);
      DeploymentOptions myoptions = new DeploymentOptions().setConfig(config);
      vertx.deployVerticle(new ExternalizeConfigVerticle(), myoptions);
    });

  }
}

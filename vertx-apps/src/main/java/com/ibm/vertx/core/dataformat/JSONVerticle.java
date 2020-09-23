package com.ibm.vertx.core.dataformat;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class JSONVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(JSONVerticle.class);
  }

  public void createSimpleJSON() {
    JsonObject profile = new JsonObject();
    //create json
    profile.put("id", 1);
    profile.put("name", "Subramanian");
    profile.put("status", true);
    //read data
    System.out.println(profile.getInteger("id"));
    System.out.println(profile.getString("name"));
    System.out.println(profile.getBoolean("status"));
    //print full
    System.out.println(profile.encodePrettily());


  }

  public void createfluentJSON() {
    JsonObject profile = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true);
    //read data
    System.out.println(profile.getInteger("id"));
    System.out.println(profile.getString("name"));
    System.out.println(profile.getBoolean("status"));
    //print full
    System.out.println(profile.encodePrettily());

  }

  public void createNestedJSON() {
    JsonObject profile = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject()
        .put("city", "Coimbatore")
        .put("state", "Tamil Nadue"));
    //read data
    System.out.println(profile.getInteger("id"));
    System.out.println(profile.getString("name"));
    System.out.println(profile.getBoolean("status"));
    //print full
    System.out.println(profile.encodePrettily());
  }

  public void createJSONArray() {
    JsonObject profile = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject()
        .put("city", "Coimbatore")
        .put("state", "Tamil Nadu"));

    JsonArray profiles = new JsonArray()
      .add(profile)
      .add(new JsonObject()
        .put("id", 2)
        .put("name", "Ram")
        .put("status", true)
        .put("address", new JsonObject()
          .put("city", "Chennai")
          .put("state", "Tamil Nadu")));

    //print full
    System.out.println(profiles.encodePrettily());
  }

  public void mergeJson(JsonObject newConfig) {
    JsonObject config = new JsonObject()
      .put("http.port", 8080)
      .mergeIn(newConfig);
    System.out.println(config.encodePrettily());
  }

  //how to encapulate json / json array into promise/future
  public Future<JsonArray> getList() {
    Promise<JsonArray> profileList = Promise.promise();
    JsonObject profile = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject()
        .put("city", "Coimbatore")
        .put("state", "Tamil Nadu"));

    JsonArray profiles = new JsonArray()
      .add(profile)
      .add(new JsonObject()
        .put("id", 2)
        .put("name", "Ram")
        .put("status", true)
        .put("address", new JsonObject()
          .put("city", "Chennai")
          .put("state", "Tamil Nadu")));

    profileList.complete(profiles);

    return profileList.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
//    createSimpleJSON();
//    createfluentJSON();
//    createNestedJSON();
//    createJSONArray();
    mergeJson(new JsonObject().put("http.host", "example.com").put("http.ssl", true));
    getList().onSuccess(profiles -> {
      System.out.println(profiles.encodePrettily());
    });
  }
}

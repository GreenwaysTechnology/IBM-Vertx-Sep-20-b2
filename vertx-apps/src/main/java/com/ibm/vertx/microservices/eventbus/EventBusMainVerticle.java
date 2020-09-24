package com.ibm.vertx.microservices.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;

class Address {
  public static String PUB_SUB_ADDRESS = "news.in.covid";
  public static String POINT_TO_POINT = "fund.in.covid.request";
  public static String REQUEST_REPLY = "report.in.covid";
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
//pub-sub ; one provider many consumer
class NewsSevenVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUB_SUB_ADDRESS);
    messageConsumer.handler(news -> {
      System.out.println(this.getClass().getName() + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class BBCVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUB_SUB_ADDRESS);
    messageConsumer.handler(news -> {
      System.out.println(this.getClass().getName() + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class NDTVVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUB_SUB_ADDRESS);
    messageConsumer.handler(news -> {
      System.out.println(this.getClass().getName() + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}


//publisher publish news to all news tv channels
class PublisherVerticle extends AbstractVerticle {

  public void publish() {
    //Get EventBus Object reference
    EventBus eventBus = vertx.eventBus();
    String message = "Last 24 Hrs covid count is 80000";
    //send message with timer
    vertx.setTimer(1000, ar -> {
      eventBus.publish(Address.PUB_SUB_ADDRESS, message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    publish();
  }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////
//point to point
class CenertalFinanceVerticle extends AbstractVerticle {

  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.POINT_TO_POINT);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request 1 -  : " + news.body());
    });
  }

  public void consume2() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.POINT_TO_POINT);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request 2 -  : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
    consume2();
  }
}


//point to point publisher
class FinanceRequestVerticle extends AbstractVerticle {

  public void requestFinance() {
    System.out.println("Finance Request started....");
    vertx.setTimer(5000, ar -> {
      //point to point : send method
      String message = "Dear Team, We request that we want 1 Billion Money for Covid";
      //point to point ; send
      vertx.eventBus().send(Address.POINT_TO_POINT, message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    requestFinance();
  }
}
////////////////////////////////////////////////////////////////////////////////////////////////////////
//Request-Reply with ack.


class LabVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.REQUEST_REPLY);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request -  : " + news.body());
      //sending reply /ack
      news.reply("Patient is Crictal, Need More attention");
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class ReportVerticle extends AbstractVerticle {

  public void sendReport() {
    vertx.setTimer(5000, ar -> {
      String message = "Report of Mr.x";
      vertx.eventBus().request(Address.REQUEST_REPLY, message, asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result().body());
        } else {
          System.out.println(asyncResult.cause());
        }
      });
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    sendReport();
  }
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////

public class EventBusMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusMainVerticle.class);
  }

  public void pubsub() {
    vertx.deployVerticle(new PublisherVerticle());
    vertx.deployVerticle(new NDTVVerticle());
    vertx.deployVerticle(new BBCVerticle());
    vertx.deployVerticle(new NewsSevenVerticle());
  }

  public void pointToPoint() {
    vertx.deployVerticle(new CenertalFinanceVerticle());
    vertx.deployVerticle(new FinanceRequestVerticle());
  }

  public void requestreply() {
    vertx.deployVerticle(new ReportVerticle());
    vertx.deployVerticle(new LabVerticle());
  }

  @Override
  public void start() throws Exception {
    super.start();
    // pubsub();
    //pointToPoint();
    requestreply();
  }
}

package com.ibm.vertx.core.fs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.FileSystem;
import io.vertx.example.util.Runner;

public class FileSystemVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FileSystemVerticle.class);
  }

  public void readFile() {
    FileSystem fileSystem = vertx.fileSystem();
    fileSystem.readFile("assets/info.txt", fileHandler -> {
      if (fileHandler.succeeded()) {
        System.out.println(fileHandler.result().toString());
      } else {
        System.out.println(fileHandler.cause().getCause());
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start");
    readFile();
    System.out.println("end");
  }
}

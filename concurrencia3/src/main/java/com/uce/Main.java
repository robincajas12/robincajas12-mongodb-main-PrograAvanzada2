package com.uce;

import com.uce.web.BookWeb;
import io.helidon.config.Config;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import jakarta.persistence.Persistence;

public class Main {
  public static void main(String[] args) {
    var emf = Persistence.createEntityManagerFactory("default");
    Config config = Config.create();
    var server = WebServer.builder()
            .port(8080)
                    .

            addRouting(HttpRouting.builder()
                    .register(new BookWeb(emf)))
            //.routing(it -> it.register(new BookWeb()))
            .build();
    server.start();



  }
}

package com.uce.web;

import com.uce.models.Book;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.persistence.EntityManagerFactory;

public class BookWeb implements HttpService {
  private final EntityManagerFactory entityManagerFactory;
  public BookWeb(EntityManagerFactory emf) {
    this.entityManagerFactory = emf;
  }

  void frinBooks (ServerRequest serverRequest, ServerResponse serverResponse)
  {

    var res = entityManagerFactory.callInTransaction(em -> {
      return em.createQuery("select o from Book o", Book.class).getResultList();
    });
    serverResponse.send(res.toArray());
  }

  void booksByIsbn(ServerRequest serverRequest, ServerResponse serverResponse)
  {
    serverResponse.send("pe causa");
  }
  @Override
  public void routing(HttpRules httpRules) {
     httpRules.
            get("book",this::frinBooks)
             .get("/book/{isbn}",this::booksByIsbn);
  }
}

package br.com.filmoteca.testcontainer.support;

import br.com.filmoteca.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public abstract class TestContainerSupport {

  public static final MongoDBContainerConfiguration mongoContainer;

  static {
    mongoContainer = MongoDBContainerConfiguration.getInstance();
    mongoContainer.start();
  }
}
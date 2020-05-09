package br.com.filmoteca.support;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import br.com.filmoteca.Application;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = RANDOM_PORT)
public abstract class IntegrationTestSupport {

  @ClassRule
  public static MongoDBContainerConfiguration mongoDBContainer =
      MongoDBContainerConfiguration.getInstance();

  @Autowired
  private MongoTemplate mongoTemplate;

  @BeforeClass
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.filmoteca.templates");
  }
}
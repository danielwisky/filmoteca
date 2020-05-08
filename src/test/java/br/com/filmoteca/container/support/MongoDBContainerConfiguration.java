package br.com.filmoteca.container.support;

public class MongoDBContainerConfiguration extends MongoContainer {

  private static final String DOCKER_IMAGE = "3.6.1";
  private static final String MONGO_DB_NAME = "filmoteca";
  private static MongoDBContainerConfiguration container;

  public MongoDBContainerConfiguration() {
    super(DOCKER_IMAGE, MONGO_DB_NAME);
  }

  public static MongoDBContainerConfiguration getInstance() {
    if (container == null) {
      container = new MongoDBContainerConfiguration();
    }

    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("MONGODB_URI", container.getMongoDbUri());
  }
}
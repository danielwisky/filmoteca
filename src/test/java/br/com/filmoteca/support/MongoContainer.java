package br.com.filmoteca.support;

import static java.lang.String.format;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.Base58;

public class MongoContainer extends GenericContainer<MongoContainer> {

  private static final int MONGODB_PORT = 27017;
  private static final String MONGODB_URL_PATTERN = "mongodb://%s:%s/%s";

  private final String mongoDBName;

  public MongoContainer(final String mongoDBVersion, final String mongoDBName) {
    super("mongo:" + mongoDBVersion);
    this.mongoDBName = mongoDBName;
    withNetworkAliases("mongodb-" + Base58.randomString(6));
    withExposedPorts(MONGODB_PORT);
    waitingFor(Wait.forListeningPort());
  }

  public String getMongoDbUri() {
    final String mongoDbUri =
        format(MONGODB_URL_PATTERN, getContainerIpAddress(), getFirstMappedPort(), mongoDBName);
    return mongoDbUri;
  }
}
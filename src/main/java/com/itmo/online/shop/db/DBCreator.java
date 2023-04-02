package com.itmo.online.shop.db;

import com.itmo.online.shop.OnlineShopApplication;
import com.itmo.online.shop.util.PropertiesUtils;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class DBCreator {

  public static void install() {

    String schema = "jdbc:postgresql://";

    Optional<String> urlOpt = PropertiesUtils.determinePropertyValue(OnlineShopApplication.class,
        "spring.datasource.url");
    if (!urlOpt.isPresent()) {
      throw new IllegalStateException("spring.datasource.url is empty");
    }
    String url = urlOpt.get();
    if (!StringUtils.hasText(url)) {
      throw new IllegalStateException("spring.datasource.url is empty");
    }

    Optional<String> usernameOpt = PropertiesUtils.determinePropertyValue(
        OnlineShopApplication.class, "spring.datasource.username");
    if (!usernameOpt.isPresent()) {
      throw new IllegalStateException("spring.datasource.username is empty");
    }
    String username = usernameOpt.get();
    if (!StringUtils.hasText(username)) {
      throw new IllegalStateException("spring.datasource.username is empty");
    }

    Optional<String> passwordOpt = PropertiesUtils.determinePropertyValue(
        OnlineShopApplication.class, "spring.datasource.password");
    if (!passwordOpt.isPresent()) {
      throw new IllegalStateException("spring.datasource.password is empty");
    }
    String password = passwordOpt.get();

    String cleanURI = url.substring(5);
    URI uri = URI.create(cleanURI);
    String server = uri.getHost();
    if (!StringUtils.hasText(server)) {
      throw new IllegalStateException("spring.datasource.url has empty host name of the server");
    }

    String port = String.valueOf(uri.getPort());
    String[] str = url.split(port + "/");
    String dbName = str[1];
    if (!StringUtils.hasText(server)) {
      throw new IllegalStateException("spring.datasource.url has empty database name");
    }

    log.info("Flyway initialized with params: url={} username={}", url, username);
    log.info("Connecting to {}...", dbName);
    if (dbExists(schema, server, dbName, username, password)) {
      log.info("Database {} exists", dbName);
      return;
    }

    if (createDb(schema, server, dbName, username, password)) {
      log.info("Database installation complete");
    }
  }

  public static boolean dbExists(String schema,
      String server,
      String dbName,
      String username,
      String password) {
    boolean exists = false;
    try (Connection connection = DriverManager.getConnection(schema + server + "/", username,
        password);
        PreparedStatement databaseExists = connection
            .prepareStatement("SELECT 1 FROM pg_database WHERE datname = ?")) {

      databaseExists.setString(1, dbName);
      try (ResultSet rs = databaseExists.executeQuery()) {
        exists = rs.next();
      }
    } catch (SQLException e) {
      log.error("An error occurred while connecting to database", e);
      System.exit(1);
    }
    return exists;
  }

  private static boolean createDb(String schema,
      String server,
      String dbName,
      String username,
      String password) {
    log.info("Creating database " + dbName + "...");
    try (Connection connection = DriverManager.getConnection(schema + server + "/", username,
        password);
        Statement createDatabase = connection.createStatement()) {
      boolean transactionsEnabled = !connection.getAutoCommit();
      if (transactionsEnabled) {
        connection.setAutoCommit(true);
      }

      createDatabase.executeUpdate("CREATE DATABASE \"" + dbName + "\"");

      if (transactionsEnabled) {
        connection.setAutoCommit(false);
      }

      log.info("Created complete");
      return true;
    } catch (SQLException e) {
      log.error("An error occurred while checking database {}", dbName, e);
      System.exit(1);
    }
    return false;
  }
}

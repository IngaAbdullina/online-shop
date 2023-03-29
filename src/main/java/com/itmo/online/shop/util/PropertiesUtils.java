package com.itmo.online.shop.util;

import java.util.Optional;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesUtils {

  public static Optional<String> determinePropertyValue(Class<?> mainAppClass, String property) {
    Optional<String> fromVMArgs = Optional.ofNullable(System.getenv(property));
    if (fromVMArgs.isPresent()) {
      return fromVMArgs;
    }

    String activeProfile = System.getProperty("spring.profiles.active");
    String propertiesFilename = (activeProfile == null || activeProfile.equals("stdout"))
        ? "application.properties"
        : "application-" + activeProfile + ".properties";

    try {
      Properties prop = new Properties();
      prop.load(mainAppClass.getClassLoader().getResourceAsStream(propertiesFilename));
      return Optional.ofNullable(prop.getProperty(property));
    } catch (Exception ignored) {
    }

    return Optional.empty();
  }
}

package org.kirsch.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties extends Properties {

  private static ApplicationProperties applicationProperties;
  private static final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

  private ApplicationProperties() {
    try {
      this.load(new FileInputStream("src/main/resources/application.properties"));
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
  }

  public static ApplicationProperties getInstance() {
    if(applicationProperties == null) {
      applicationProperties = new ApplicationProperties();
    }
    return applicationProperties;
  }

  public String getGoogleJavaApiKey() {
    return applicationProperties.getProperty("com.google.api.key.java", System.getenv("GOOGLE_API_KEY_JAVA"));
  }

  public String getGoogleJsApiKey() {
    return applicationProperties.getProperty("com.google.api.key.js", System.getenv("GOOGLE_API_KEY_JS"));
  }

  public Integer getPlacesMaxResultCount() {
    int maxResultCount = 0;
    try {
      String str = applicationProperties.getProperty("com.google.maps.places.max_result_count", "10");
      maxResultCount = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      log.warn(e.getMessage());
      maxResultCount = 10;
    }
    return maxResultCount;
  }

}
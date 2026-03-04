package io.coffeeride.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  private final FileCleanupInterceptor fileCleanupInterceptor;

  public InterceptorConfig(FileCleanupInterceptor fileCleanupInterceptor) {
    this.fileCleanupInterceptor = fileCleanupInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(fileCleanupInterceptor)
      .addPathPatterns("/gpx");
  }

}
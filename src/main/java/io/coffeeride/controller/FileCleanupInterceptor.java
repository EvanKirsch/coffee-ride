package io.coffeeride.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.java.Log;


@Log
@Component
class FileCleanupInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    String contentDisposition = response.getHeader(HttpHeaders.CONTENT_DISPOSITION);
    String key  = "filename=";
    int index = contentDisposition.indexOf(key, 0) + key.length();
    String filename = contentDisposition.substring(index, contentDisposition.length());
    File file = new File(filename);
    file.delete();
  }

}
package com.webflux.demowebflux.exceptions;

public class PostErrorServerException extends Exception {

  public PostErrorServerException(String message) {
      super(message);
      this.showError(message);
  }

  private void showError(String message){
      System.out.println("PostErrorServerException "+ message);
  }

}

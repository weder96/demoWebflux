package com.webflux.demowebflux.exceptions;

public class PostNotFoundException  extends Exception {

  public PostNotFoundException(String message) {
            super(message);
            this.showError(message);
  }

    private void showError(String message){
        System.out.println("PostNotFoundException  "+ message);
    }
}

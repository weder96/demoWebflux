package com.webflux.demowebflux.model;

import lombok.Data;

@Data
public class Posts {

    int id;
    String title;
    String body;
    int userId;

}

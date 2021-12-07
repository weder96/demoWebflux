package com.webflux.demowebflux.service;

import com.webflux.demowebflux.exceptions.PostErrorServerException;
import com.webflux.demowebflux.exceptions.PostNotFoundException;
import com.webflux.demowebflux.model.Posts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientService {

    final static String URL_BASE = "https://jsonplaceholder.typicode.com";
    final static String POSTS = "Posts ";
    final static String NOT_FOUND = " Not Found ";

    protected  WebClient webClient;

    public WebClientService() {
        this.webClient = WebClient.create(URL_BASE);
    }

    public Mono<Posts>  closeSessionByCode(final int code){
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/todos/"+code)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new PostNotFoundException("Posts Not Found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new PostErrorServerException("Posts  Not Found")))
                .bodyToMono(Posts.class);
    }

    public Mono<Posts> openSessionByCode(final int code){
        Posts post = new Posts();
        post.setBody("body");
        post.setTitle("title");
        post.setUserId(code);

        return this.webClient.post()
                .uri("/posts")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(post), Posts.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new PostNotFoundException(POSTS +post.getUserId() + NOT_FOUND)))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new PostErrorServerException(POSTS +post.getUserId()+ NOT_FOUND)))
                .bodyToMono(Posts.class);
    }

    public Mono<Posts> sendLineBySessionOpen(final int code, final String line){
        Posts post = new Posts();
        post.setId(1);
        post.setTitle("Foo");
        post.setBody(line);
        post.setUserId(code);

        return this.webClient.put()
                .uri("/posts/"+post.getId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(post), Posts.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new PostNotFoundException(POSTS +post.getId() + NOT_FOUND)))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new PostErrorServerException(POSTS +post.getId() + NOT_FOUND)))
                .bodyToMono(Posts.class);
    }
}

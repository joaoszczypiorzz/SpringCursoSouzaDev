package br.com.joaoszczypior.spring_boot_essentials.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api")
public class HelloWordController {

    @GetMapping
    public ResponseEntity<String> helloWorld () {
       return ResponseEntity.ok("Hello World");
    }
}

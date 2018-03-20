package info.matsumana.sample.webfiltersample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    String index() {
        return "Hello, World!";
    }

    @GetMapping("/fail")
    String fail() {
        throw new RuntimeException("failed!");
    }
}

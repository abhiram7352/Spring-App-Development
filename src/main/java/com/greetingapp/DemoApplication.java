package com.greetingapp;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

//import javax.persistence.*;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@RestController
@RequestMapping("/greet")
class GreetingController {

    private final GreetingRepository greetingRepository;

    public GreetingController(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/hello/query")
    public String sayHelloWithQuery(@RequestParam String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/hello/{name}")
    public String sayHelloWithPath(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/hello/body")
    public String sayHelloWithBody(@RequestBody GreetingRequest request) {
        return "Hello, " + request.getName() + "!";
    }

    @PostMapping("/save")
    public Greeting saveGreeting(@RequestBody Greeting greeting) {
        return greetingRepository.save(greeting);
    }

    @GetMapping("/get/{id}")
    public Optional<Greeting> getGreeting(@PathVariable Long id) {
        return greetingRepository.findById(id);
    }
}

@Entity
class Greeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

interface GreetingRepository extends org.springframework.data.jpa.repository.JpaRepository<Greeting, Long> {}

class GreetingRequest {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

package com.example.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@Controller
public class HelloWorldApplication {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    @GetMapping("/")
    public String welcome(Model model) {
        List<Wallpaper> wallpapers = Arrays.asList(
            new Wallpaper("naruto1.jpg", "Naruto in Action", 9.99),
            new Wallpaper("naruto2.jpg", "Naruto and Hinata", 12.99),
            new Wallpaper("naruto3.jpg", "Team 7", 14.99)
        );
        model.addAttribute("wallpapers", wallpapers);
        
        for (Wallpaper wallpaper : wallpapers) {
            logger.info("Wallpaper: {}", wallpaper);
        }
        
        return "welcome";
    }

    @Bean
    public CommandLineRunner checkResources() {
        return args -> {
            logger.info("Checking resources at startup:");
            String[] imageNames = {"naruto1.jpg", "naruto2.jpg", "naruto3.jpg"};
            for (String imageName : imageNames) {
                Resource resource = new ClassPathResource("static/images/" + imageName);
                if (resource.exists()) {
                    logger.info("Resource found: {}", imageName);
                    logger.info("File size: {} bytes", resource.contentLength());
                } else {
                    logger.warn("Resource not found: {}", imageName);
                }
            }
            
            // List contents of the static/images directory
            Path imagesDir = Paths.get("/app/static/images");
            logger.info("Contents of {}: {}", imagesDir, Files.list(imagesDir).map(Path::getFileName).toArray());
        };
    }
}

class Wallpaper {
    private String imageUrl;
    private String name;
    private double price;

    public Wallpaper(String imageUrl, String name, double price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getImageUrl() { return imageUrl; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "Wallpaper{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

package com.example.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/images/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String fileName) throws IOException {
        logger.info("Attempting to serve image: {}", fileName);
        String resourcePath = "static/images/" + fileName;
        logger.info("Looking for resource at: {}", resourcePath);
        Resource resource = new ClassPathResource(resourcePath);
        
        if (resource.exists()) {
            logger.info("Image found: {}", fileName);
            logger.info("File size: {} bytes", resource.contentLength());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            logger.warn("Image not found: {}", fileName);
            logger.warn("Absolute path attempted: {}", resource.getFile().getAbsolutePath());
            
            // List contents of the static/images directory
            Path imagesDir = Paths.get("src/main/resources/static/images");
            logger.info("Contents of {}: {}", imagesDir, Files.list(imagesDir).map(Path::getFileName).toArray());
            
            return ResponseEntity.notFound().build();
        }
    }
}

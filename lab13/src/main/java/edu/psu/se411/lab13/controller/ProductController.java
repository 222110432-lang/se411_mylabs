package edu.psu.se411.lab13.controller;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import edu.psu.se411.lab13.model.Product;
import edu.psu.se411.lab13.service.ProductService;
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
 private final ProductService service;
 public ProductController(ProductService service) { this.service=service; }
 @GetMapping public Iterable<Product> findAll() { return service.findAll(); }
 @GetMapping("/{id}") public Product findById(@PathVariable @Positive Long id) { return service.findById(id); }
 
}

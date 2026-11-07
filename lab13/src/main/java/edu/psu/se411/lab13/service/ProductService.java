package edu.psu.se411.lab13.service;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import edu.psu.se411.lab13.model.Product;
import edu.psu.se411.lab13.repository.ProductRepository;
@Service
public class ProductService {
 private final ProductRepository repository;
 
 public ProductService(ProductRepository repository) { this.repository=repository;  }
 public Iterable<Product> findAll() { return repository.findAll(); }
 public Product findById(Long id) {
  return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
 }
 
}

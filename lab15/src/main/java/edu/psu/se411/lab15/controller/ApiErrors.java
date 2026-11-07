package edu.psu.se411.lab15.controller;
import java.util.Map;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class ApiErrors {
 @ExceptionHandler(DuplicateKeyException.class)
 public ResponseEntity<Map<String,String>> duplicate(DuplicateKeyException exception) {
  return ResponseEntity.status(422).body(Map.of("error","Id already exists"));
 }
}

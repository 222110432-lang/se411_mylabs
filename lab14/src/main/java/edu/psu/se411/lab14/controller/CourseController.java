package edu.psu.se411.lab14.controller;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import edu.psu.se411.lab14.model.Course;
import edu.psu.se411.lab14.service.CourseService;
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
 private final CourseService service;
 public CourseController(CourseService service) { this.service=service; }
 @GetMapping public Iterable<Course> findAll() { return service.findAll(); }
 @GetMapping("/{id}") public Course findById(@PathVariable @PositiveOrZero Long id) { return service.findById(id); }
 @PostMapping public ResponseEntity<Course> create(@Valid @RequestBody Course value) {
  Course created=service.create(value);
  URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();
  return ResponseEntity.created(location).body(created);
 }
 @PutMapping("/{id}") public ResponseEntity<Void> update(@PathVariable @PositiveOrZero Long id,@Valid @RequestBody Course value) {
  service.update(id,value); return ResponseEntity.noContent().build();
 }
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable @PositiveOrZero Long id) {
  service.delete(id); return ResponseEntity.noContent().build();
 }
}

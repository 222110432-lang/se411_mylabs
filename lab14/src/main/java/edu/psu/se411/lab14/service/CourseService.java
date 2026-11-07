package edu.psu.se411.lab14.service;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import edu.psu.se411.lab14.model.Course;
import edu.psu.se411.lab14.repository.CourseRepository;
@Service
public class CourseService {
 private final CourseRepository repository;
 private final JdbcAggregateTemplate template;
 public CourseService(CourseRepository repository,JdbcAggregateTemplate template) { this.repository=repository; this.template=template; }
 public Iterable<Course> findAll() { return repository.findAll(); }
 public Course findById(Long id) {
  return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found"));
 }
 @Transactional
 public Course create(Course value) {
  if(value.id()!=null && repository.existsById(value.id())) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Id already exists");
  return template.insert(value);
 }
 @Transactional
 public void update(Long id,Course value) {
  findById(id);
  if(value.id()!=null && !id.equals(value.id())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Body id must match URL id");
  repository.save(new Course(id,value.code(),value.name(),value.description(),value.credits()));
 }
 @Transactional
 public void delete(Long id) { findById(id); repository.deleteById(id); }
}

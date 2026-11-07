package edu.psu.se411.lab14;
import java.net.*;
import java.net.http.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)

class ApiTest {
 @Autowired Environment environment;
 private final HttpClient client=HttpClient.newHttpClient();
 private String base() { return "http://localhost:"+environment.getProperty("local.server.port")+"/api/v1/courses"; }
 private HttpResponse<String> call(String method,String path,String body) throws Exception {
  return client.send(HttpRequest.newBuilder(URI.create(path)).header("Content-Type","application/json")
   .method(method,body==null?HttpRequest.BodyPublishers.noBody():HttpRequest.BodyPublishers.ofString(body)).build(),HttpResponse.BodyHandlers.ofString());
 }
 @Test void readsAndValidation() throws Exception {
  var all=call("GET",base(),null); assertEquals(200,all.statusCode()); assertTrue(all.body().contains("SE201"));
  assertEquals(200,call("GET",base()+"/1",null).statusCode());
  assertEquals(404,call("GET",base()+"/999999",null).statusCode());
  assertEquals(400,call("GET",base()+"/-1",null).statusCode());
  assertEquals(400,call("GET",base()+"/not-a-number",null).statusCode());
 }
 @Test void completeCrudLifecycle() throws Exception {
  String body="{\"code\":\"SE411\",\"name\":\"Software Construction\",\"description\":\"Lab test\",\"credits\":3}";
  var created=call("POST",base(),body); assertEquals(201,created.statusCode(),created.body());
  String location=created.headers().firstValue("Location").orElseThrow();
  assertTrue(location.contains("/api/v1/courses/"));
  assertEquals(200,call("GET",location,null).statusCode());
  String changed=body.replace("Software Construction","Updated Course");
  assertEquals(204,call("PUT",location,changed).statusCode());
  assertTrue(call("GET",location,null).body().contains("Updated Course"));
  assertEquals(204,call("DELETE",location,null).statusCode());
  assertEquals(404,call("GET",location,null).statusCode());
  assertEquals(404,call("DELETE",location,null).statusCode());
  assertEquals(404,call("PUT",location,changed).statusCode());
 }
 @Test void rejectsInvalidBodiesAndDuplicateIds() throws Exception {
  String valid="{\"id\":900,\"code\":\"SE411\",\"name\":\"Construction\",\"credits\":3}";
  try {
   var first=call("POST",base(),valid); assertEquals(201,first.statusCode(),first.body());
   assertEquals(422,call("POST",base(),valid).statusCode());
   assertEquals(400,call("PUT",base()+"/900",valid.replace("900","901")).statusCode());
  } finally { call("DELETE",base()+"/900",null); }
  for(String body:List.of(
    valid.replace("\"id\":900","\"id\":-1"),
    valid.replace("\"SE411\"","\"\""),
    valid.replace("\"Construction\"","\"X\""),
    valid.replace("\"credits\":3","\"credits\":0"),
    valid.replace("\"credits\":3","\"credits\":11"),
    valid.replace("\"credits\":3","\"credits\":null"),
    "{}","{broken")) {
   assertEquals(400,call("POST",base(),body).statusCode(),body);
  }
 }
}

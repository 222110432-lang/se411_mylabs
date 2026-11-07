package edu.psu.se411.lab13;
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
 private String base() { return "http://localhost:"+environment.getProperty("local.server.port")+"/api/v1/products"; }
 private HttpResponse<String> call(String method,String path,String body) throws Exception {
  return client.send(HttpRequest.newBuilder(URI.create(path)).header("Content-Type","application/json")
   .method(method,body==null?HttpRequest.BodyPublishers.noBody():HttpRequest.BodyPublishers.ofString(body)).build(),HttpResponse.BodyHandlers.ofString());
 }
 @Test void readsAndValidation() throws Exception {
  var all=call("GET",base(),null); assertEquals(200,all.statusCode()); assertTrue(all.body().contains("3d printer"));
  assertEquals(200,call("GET",base()+"/10",null).statusCode());
  assertEquals(404,call("GET",base()+"/999999",null).statusCode());
  assertEquals(400,call("GET",base()+"/-1",null).statusCode());
  assertEquals(400,call("GET",base()+"/not-a-number",null).statusCode());
 }
 
}

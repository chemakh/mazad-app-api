package ch.com.mazad.restclients;


import net.minidev.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mazad-auth-server", url = "${mazad.feign.auth}")
public interface UserClient
{

    @RequestMapping(method = RequestMethod.POST, value = "oauth/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<JSONObject> authenticate(@RequestParam("client_secret") String secret, @RequestParam("client_id") String clientId,
                                            @RequestParam("grant_type") String grantType, @RequestParam("scope") String scope,
                                            @RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(method = RequestMethod.POST, value = "oauth/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<JSONObject> refreshToken(@RequestParam("client_secret") String secret, @RequestParam("client_id") String clientId,
                                            @RequestParam("grant_type") String grantType,
                                            @RequestParam("refresh_token") String token);
}

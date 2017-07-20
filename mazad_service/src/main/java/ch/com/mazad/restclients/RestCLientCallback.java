package ch.com.mazad.restclients;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class RestCLientCallback implements UserClient {

    private static Logger logger = LoggerFactory.getLogger(RestCLientCallback.class);

    @Inject
    private UserClient userClient;

    @Override
    @HystrixCommand
    public ResponseEntity<JSONObject> authenticate(String secret, String clientId, String grantType, String scope, String username, String password) {
        return userClient.authenticate(secret, clientId, grantType, scope, username, password);
    }

    @Override
    @HystrixCommand
    public ResponseEntity<JSONObject> refreshToken(String secret, String clientId, String grantType, String token) {
        return userClient.refreshToken(secret, clientId, grantType, token);
    }



}

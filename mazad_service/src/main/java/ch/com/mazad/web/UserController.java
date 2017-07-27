package ch.com.mazad.web;


import ch.com.mazad.exception.FieldErrorDTO;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.restclients.RestCLientCallback;
import ch.com.mazad.service.UserService;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.*;
import java.io.IOException;
import java.util.Set;

@Api(value = "user", description = "Operations with user", produces = "application/json")
@RestController
@RequestMapping("/ws/users")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private ObjectMapper objectMapper;

    @Value("${mazad.security.authentication.oauth.clientid}")
    private String oauthClientId;

    @Value("${mazad.security.authentication.oauth.secret}")
    private String oauthClientSecret;

    @Value("${mazad.security.authentication.oauth.grant_type}")
    private String grantType;

    @Value("${mazad.security.authentication.oauth.scope}")
    private String scope;

    @Inject
    private UserService userService;

    @Inject
    private RestCLientCallback restCLientCallback;

    @RequestMapping(value = "authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Authentication Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 400, message = "Bad credentials")
    })
    public JSONObject authenticate(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "password") String password) {
        return restCLientCallback.authenticate(oauthClientSecret, oauthClientId, grantType, scope, username, password).getBody();
    }


    @RequestMapping(value = "token/refresh",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Refresh Token Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 401, message = "Bad Token")
    })
    public JSONObject refreshToken(@RequestParam(value = "token") String token) {
        ResponseEntity<JSONObject> responseEntity = restCLientCallback.refreshToken(oauthClientSecret, oauthClientId, "refresh_token", token);
        return responseEntity.getBody();
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create User Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public UserDTO createUser(@Valid @RequestPart("user") String user, @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws MazadException, IOException {
        
    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
        final Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        
        if (violations.size() > 0) {
            violations.forEach(u -> System.out.println("  \"" + u.getPropertyPath().toString() + "\"" + " " + u.getMessage()));
            
            ConstraintViolation<UserDTO> violation = violations.stream().findFirst().get();
            
            throw MazadException.validationErrorBuilder(new FieldErrorDTO("User", violation.getPropertyPath().toString(),violation.getMessage()));
            
            
        } 
                
    	return userService.createUser(userDTO, avatar);
    }

    @RequestMapping(value = "",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update User Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 404, message = "Object with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")

    })
    public UserDTO updateUser(@Valid @RequestBody UserDTO userDTO, @RequestParam("reference") String reference) throws MazadException {

        return userService.updateUser(userDTO, reference);
    }

    @RequestMapping(value = "",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete User Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 404, message = "User with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public JSONObject deleteUser(@RequestParam("reference") String reference) throws MazadException {

        return userService.deleteUser(reference);
    }

    @PutMapping(value = "avatar",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update User Avatar Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 404, message = "Object with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")

    })
    public UserDTO updateUserAvatar(@RequestParam("user_ref") String userRef, @RequestPart(value = "photo") MultipartFile photo) throws MazadException, IOException {

        return userService.updateUserAvatar(userRef, photo);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "activate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Activate Registration Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Invalid Key Value"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public UserDTO activateRegistration(@RequestParam(value = "key") String key) throws MazadException {
        return userService.activateRegistration(key);
    }

    @RequestMapping(value = "email/activate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Activate Registration Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Invalid Key Value"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public UserDTO activateRegistrationByEmail(@RequestParam(value = "key") String key,
                                               @RequestParam(value = "email") String email) throws MazadException {
        return userService.activateRegistration(email, key);
    }

    @RequestMapping(value = "password/reset",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Reset Password Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 404, message = "User not Found")
    })
    public JSONObject requestResetPassword(@RequestParam("email") String email) throws MazadException {

        userService.requestResetPassword(email);

        JSONObject result = new JSONObject();
        result.put("result", "email-sent");
        result.put("info", "a mail has been sent to your mailbox");
        return result;
    }

    @RequestMapping(value = "password/reset/finish",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Complete Reset Password Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 404, message = "User not Found")
    })
    public JSONObject completePasswordReset(@RequestParam("password") String password, @RequestParam("key") String key) throws MazadException {
        JSONObject result = new JSONObject();
        userService.completePasswordReset(password, key);
        result.put("result", "reset-success");
        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "password/change",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Change Password Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 400, message = "Password Don't Match"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public JSONObject changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) throws MazadException {
        JSONObject result = new JSONObject();
        userService.changePassword(oldPassword, newPassword);
        result.put("result", "reset-success");
        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "email/verify",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Verify Email Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Invalid Key Value"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public UserDTO verifyEmail(@RequestParam("code") String code) throws MazadException {

        logger.debug("Call rest to verify email  ");
        return userService.verifyEmail(code);

    }

    @RequestMapping(value = "email/request/code",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Request Email Code Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public JSONObject requestEmailCode(String email) throws MazadException {

        JSONObject result = new JSONObject();
        userService.requestEmailCode(email);
        result.put("result", "sent-success");
        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "email/send",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Request Email Code Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    public JSONObject requestEmailCode() throws MazadException {

        JSONObject result = new JSONObject();
        userService.requestEmailCode();
        result.put("result", "sent-success");
        return result;
    }

    @RequestMapping(value = "current",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Current User Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 404, message = "User with Ref not Found")
    })
    public UserDTO getCurrentUser() throws MazadException {
        return userService.getLoggedUser();
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get User Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = UserDTO.class),
            @ApiResponse(code = 404, message = "User with Ref not Found")
    })
    public UserDTO getUserDetails(@RequestParam("reference") String reference) throws MazadException {
        return userService.getUserDetails(reference);
    }

//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(value = "/account/disable",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Disable Account Service")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Operation Executed Successfully"),
//            @ApiResponse(code = 401, message = "Unauthorized")
//    })
//    public JSONObject disableAccount() throws MazadException {
//        logger.debug("Call rest to disable account ");
//
//        JSONObject result = new JSONObject();
//        userService.disableAccount();
//        result.put("result", "account disabled");
//        return result;
//
//    }
}

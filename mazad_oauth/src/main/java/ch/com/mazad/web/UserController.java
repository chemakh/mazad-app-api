package ch.com.mazad.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController
{


    @PreAuthorize("#oauth2.hasScope('openid')")
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal)
    {
        return principal;
    }
}

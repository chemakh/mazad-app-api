package ch.com.mazad.service;

import ch.com.mazad.exception.UserNotActivatedException;
import ch.com.mazad.repository.UserRepository;
import ch.com.mazad.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

    private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {

        logger.info("Authenticating the user {}", email);
        Optional<User> userFound = repository.findOneByEmail(email);
        return userFound.map(user -> {
            if (!user.isActivated() || !user.isMailVerified())
            {
                    throw new UserNotActivatedException("User " + email + " has not been activated yet");
            }
            return user;
        }).orElseThrow(() -> new UsernameNotFoundException("user " + email + " Not found in the database"));
    }
}

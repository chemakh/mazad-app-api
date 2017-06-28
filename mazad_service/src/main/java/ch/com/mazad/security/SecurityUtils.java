package ch.com.mazad.security;

import ch.com.mazad.domain.Authority;
import ch.com.mazad.domain.User;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.inject.Inject;


public final class SecurityUtils {

    private static UserRepository userRepository;

    @Inject
    public SecurityUtils(UserRepository userRepository) {
        SecurityUtils.userRepository = userRepository;
    }

    public static boolean checkIfThereIsUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public static User getCurrentUser() throws MazadException {

        if (!SecurityUtils.checkIfThereIsUserLogged())
            throw MazadException.actionUnauthorizedErrorBuilder();

        return userRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin()).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, getCurrentUserLogin(), MazadException.WITH_EMAIL));
    }

    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    public static boolean isCurrentUserInRole(User user, String authority) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        return authentication != null && user.getAuthorities().contains(new Authority(authority));

    }

    public static boolean isCurrentUserAdmin() throws MazadException {
        return SecurityUtils.isCurrentUserInRole(getCurrentUser(), AuthoritiesConstants.ADMIN);
    }
}

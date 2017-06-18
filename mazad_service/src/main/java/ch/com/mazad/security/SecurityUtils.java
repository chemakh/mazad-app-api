package ch.com.mazad.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public final class SecurityUtils {

    private SecurityUtils() {

    }

    public static boolean checkIfThereIsUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
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


//    public static boolean isCurrentUserInRole(User user, String authority) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//
//        return authentication != null && user.getAuthorities().contains(new Authority(authority));
//
//    }

//    public static boolean isDoctor(User user) {
//        return SecurityUtils.isCurrentUserInRole(user, AuthoritiesConstants.DOCTOR);
//    }
//
//    public static boolean isAdmin(User user) {
//        return SecurityUtils.isCurrentUserInRole(user, AuthoritiesConstants.ADMIN);
//    }
//
//    public static boolean isAssistantDoctor(User user) {
//        return SecurityUtils.isCurrentUserInRole(user, AuthoritiesConstants.ASSISTANT_DOCTOR);
//    }
//
//    public static boolean isAssistantAdmin(User user) {
//        return SecurityUtils.isCurrentUserInRole(user, AuthoritiesConstants.ASSISTANT_ADMIN);
//    }
}

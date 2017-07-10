package ch.com.mazad.service;

import ch.com.mazad.domain.User;
import ch.com.mazad.exception.FieldErrorDTO;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.UserRepository;
import ch.com.mazad.security.SecurityUtils;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.utils.TokenUtil;
import ch.com.mazad.web.dto.UserDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 22/06/2017.
 */

@Service
public class UserService
{

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private PhotoService photoService;

    @Inject
    private DTOMapper mapper;


    public UserDTO createUser(UserDTO userDTO) throws MazadException
    {

        checkIfEmailIsUsed(userDTO.getEmail(), userDTO.getReference());

        userDTO.setReference(TokenUtil.generateReference());

        if (userDTO.getAddress() != null)
            userDTO.getAddress().setReference(TokenUtil.generateReference());

        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);
        userDTO.setReference(TokenUtil.generateReference());
        User user = userRepository.save(mapper.fromDTOToUser(userDTO));
        return mapper.fromUserToDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, String reference) throws MazadException
    {

        User user = userRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, userDTO.getReference()));

        if (userDTO.getAddress() == null)
            userDTO.setAddress(mapper.fromAddressToDTO(user.getAddress()));
        mapper.updateUserFromDto(userDTO, user);
        Optional.ofNullable(userDTO.getPassword()).ifPresent(pass -> user.setPassword(passwordEncoder.encode(pass)));

        return mapper.fromUserToDTO(userRepository.save(user));
    }

    public UserDTO updateUserAvatar(String userRef, MultipartFile photo) throws IOException, MazadException
    {
        return photoService.createUserAvatar(photo, userRef);
    }

    public List<UserDTO> getUser(String reference) throws MazadException
    {

        if (reference != null)

            try
            {
                return Collections.singletonList(userRepository.findOneByReference(reference).map(mapper::fromUserToDTO)
                        .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, reference)));
            }
            catch (MazadException e)
            {
                return Collections.singletonList(userRepository.findOneByReference(reference).map(mapper::fromUserToDTO)
                        .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, reference)));
            }
        else
            return userRepository.findAll().stream().map(mapper::fromUserToDTO)
                    .collect(Collectors.toList());
    }

    public JSONObject deleteUser(String reference) throws MazadException
    {

        User user = userRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, reference));

        user.setDeletionDate(LocalDateTime.now());
        user.setDeleted(true);

        userRepository.save(user);

        JSONObject result = new JSONObject();
        result.put("result", "delete-success");
        return result;
    }


    public UserDTO getCurrentUser(Principal principal)
    {
        logger.info("Getting current user");

        if (principal instanceof User)
        {
            User user = (User) principal;
            return mapper.fromUserToDTO(user);

        }
        return null;
    }

    public UserDTO activateRegistration(String key) throws MazadException
    {

        logger.debug("Activating user for activation key {}", key);

        User currentUser = getCurrentUser();

        return userRepository.findOneByEmailKey(key)
                .filter(user -> user.equals(currentUser))
                .map(user ->
                {
                    user.setActivated(true);
                    user.setEmailKey(null);
                    userRepository.save(user);
                    logger.debug("Activated user: {}", user);

                    return mapper.fromUserToDTO(user);
                }).orElseThrow(() -> MazadException.invalidCodeExceptionBuilder("Email Key"));
    }

    public UserDTO requestResetPassword(String mail) throws MazadException
    {
        return userRepository.findOneByEmail(mail)
                .filter(User::isActivated)
                .map(user ->
                {
                    user.setResetPasswordKey(TokenUtil.generateCode());
                    userRepository.save(user);
                    return mapper.fromUserToDTO(user);
                }).orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, mail, MazadException.WITH_EMAIL));
    }

    public UserDTO completePasswordReset(String newPassword, String key) throws MazadException
    {
        logger.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetPasswordKey(key)
                .filter(User::isActivated)
                .map(user ->
                {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetPasswordKey(null);
                    userRepository.save(user);
                    return mapper.fromUserToDTO(user);
                })
                .orElseThrow(() -> MazadException.invalidCodeExceptionBuilder("Password Key"));
    }

    public boolean changePassword(String oldPassword, String newPassword) throws MazadException
    {
        logger.debug("Reset user password for reset");

        User user = getCurrentUser();
        if (passwordEncoder.matches(oldPassword, user.getPassword()))
        {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        else
        {
            throw MazadException.validationErrorBuilder(new FieldErrorDTO("User", "Password", "must_match"));
        }

        return true;

    }

    public boolean requestEmailCode() throws MazadException
    {
        logger.debug("Reset user email ");
        User user = getCurrentUser();
        user.setEmailKey(TokenUtil.generateCode());
        userRepository.save(user);

        //TODO sendActivationEmailKey

        return true;

    }

    public UserDTO verifyEmail(String code) throws MazadException
    {
        logger.debug("Verify user email with  key {}", code);
        User currentUser = getCurrentUser();
        return userRepository.findOneByEmailKey(code)
                .filter(user -> user.equals(currentUser))
                .map(user ->
                {
                    user.setEmailKey(null);
                    user.setMailVerified(true);
                    user = userRepository.save(user);
                    logger.debug("verify  email : {}", user.getEmail());
                    return mapper.fromUserToDTO(user);
                })
                .orElseThrow(() -> MazadException.invalidCodeExceptionBuilder("Email Key"));
    }


    public UserDTO getUserDetails(String reference)
    {
        User us = userRepository.findOneByReference(reference).orElse(null);
        return mapper.fromUserToDTO(us);
    }

    public User getCurrentUser() throws MazadException
    {

        if (!SecurityUtils.checkIfThereIsUserLogged())
            throw MazadException.actionUnauthorizedErrorBuilder();

        return userRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin()).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, "currentUserName", MazadException.WITH_EMAIL));
    }

    public void checkIfEmailIsUsed(String email, String reference) throws MazadException
    {
        if (email != null && !email.isEmpty())
        {
            Optional<String> find = userRepository.getUserReferenceByEmail(email).
                    filter(usRef -> reference == null || reference.isEmpty() || !usRef.equals(reference));
            if (find.isPresent())
            {
                throw MazadException.identifierAlreadyInUseExceptionBuilderBuilder("Email", email, new FieldErrorDTO("User", "Email", "already_in_use"));
            }

        }

    }
}

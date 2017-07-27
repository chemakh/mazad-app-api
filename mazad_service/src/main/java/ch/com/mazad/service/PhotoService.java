package ch.com.mazad.service;

import ch.com.mazad.config.MazadProperties;
import ch.com.mazad.domain.Article;
import ch.com.mazad.domain.Photo;
import ch.com.mazad.domain.User;
import ch.com.mazad.exception.FieldErrorDTO;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.ArticleRepository;
import ch.com.mazad.repository.PhotoRepository;
import ch.com.mazad.repository.UserRepository;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.utils.TokenUtil;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.PhotoDTO;
import ch.com.mazad.web.dto.UserDTO;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 05/07/2017.
 */

@Service
public class PhotoService {

    private final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    @Inject
    private DTOMapper mapper;

    @Inject
    private MazadProperties mazadProperties;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private PhotoRepository photoRepository;

    public UserDTO createUserAvatar(MultipartFile avatar, String userRef) throws IOException, MazadException {

        if (avatar != null) {
            User user = userRepository.findOneByReference(userRef).
                    orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(User.class, userRef));

            String referenceOldAvatar = user.getAvatar() != null ? user.getAvatar().getReference() : null;
            user.setAvatar(createPhoto(avatar, null));
            UserDTO userDTO = mapper.fromUserToDTO(userRepository.save(user));

            if (referenceOldAvatar != null)
                removePhoto(photoRepository.findOneByReference(referenceOldAvatar).orElse(null));

            return userDTO;
        } else
            throw MazadException.validationErrorBuilder(new FieldErrorDTO("Photo", "Photo", "must_be_not_null"));
    }

    public ArticleDTO createArticleAvatar(MultipartFile avatar, String articleRef) throws IOException, MazadException {
        if (avatar != null) {

            Article article = articleRepository.findOneByReference(articleRef).
                    orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, articleRef));

            if (article.getAvatar() != null)
                removePhoto(article.getAvatar());

            String referenceOldAvatar = article.getAvatar() != null ? article.getAvatar().getReference() : null;
            article.setAvatar(createPhoto(avatar, null));
            ArticleDTO articleDTO = mapper.fromArticleToDTO(articleRepository.save(article));

            if (referenceOldAvatar != null)
                removePhoto(photoRepository.findOneByReference(referenceOldAvatar).orElse(null));

            return articleDTO;
        } else
            throw MazadException.validationErrorBuilder(new FieldErrorDTO("Photo", "Photo", "must_be_not_null"));
    }

    public Photo createPhoto(MultipartFile avatar, String articleRef) throws MazadException, IOException {

        if (avatar != null) {

            Photo photo = new Photo();
            photo.setReference(TokenUtil.generateReference());
            photo.setLabel(avatar.getName());
            photo.setName(photo.getReference() + "." + FilenameUtils.getExtension(avatar.getOriginalFilename()));
            photo.setArticle(articleRef != null ? articleRepository.findOneByReference(articleRef).
                    orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, articleRef)) : null);
            avatar.transferTo(new File(mazadProperties.getAvatar().getPath() + photo.getName()));

            photo = photoRepository.save(photo);

            if (articleRef != null) {
                Article article = articleRepository.findOneByReference(articleRef).
                        orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, articleRef));

                if (article.getAvatar() == null) {
                    article.setAvatar(photo);
                    articleRepository.save(article);
                }
            }
            return photo;

        } else
            throw MazadException.validationErrorBuilder(new FieldErrorDTO("Photo", "Photo", "must_be_not_null"));
    }

    public Photo getPhotoByArticle(String reference, String articleRef) throws MazadException {
        return photoRepository.findOneByReferenceAndArticleReference(reference, articleRef).orElseThrow(() ->
                MazadException.resourceNotFoundExceptionBuilder(Photo.class, reference));
    }

    public List<PhotoDTO> getArticlePhotos(String articleRef) {
        return photoRepository.findByArticleReference(articleRef).stream().map(mapper::fromPhotoToDTO)
                .collect(Collectors.toList());
    }

    public Photo getPhoto(String reference) throws MazadException {
        return photoRepository.findOneByReference(reference).orElseThrow(() ->
                MazadException.resourceNotFoundExceptionBuilder(Photo.class, reference));
    }

    public void removePhoto(Photo photo) throws MazadException, IOException {

        if (photo != null) {
            File file = new File(mazadProperties.getAvatar().getPath() + photo.getName());
            file.delete();
            photoRepository.delete(photo);
        }

    }
}

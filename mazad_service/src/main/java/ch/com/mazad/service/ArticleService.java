package ch.com.mazad.service;

import ch.com.mazad.domain.*;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.ArticleRepository;
import ch.com.mazad.repository.BidRepository;
import ch.com.mazad.security.SecurityUtils;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.utils.TokenUtil;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.PhotoDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 27/06/2017.
 */
@Service
public class ArticleService
{
    private static Integer DEFAULT_VALIDITY_DURATION = 5;
    private final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private BidRepository bidRepository;
    @Inject
    private PhotoService photoService;
    @Inject
    private DTOMapper mapper;

    public ArticleDTO getArticleByReference(String reference) throws MazadException
    {
        return checkValidity(reference, false);
    }

    public List<ArticleDTO> getArticles(String reference, String categoryRef, String userRef, boolean byBid, boolean sold) throws MazadException
    {

        if(reference != null)
            return Collections.singletonList(getArticleByReference(reference));
        else if(categoryRef != null && userRef != null)
            if(!byBid)
                return articleRepository.findByCategoryReferenceAndCreatedByReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(categoryRef, userRef, sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
            else
                return bidRepository.findByArticleCategoryReferenceAndUserReferenceAndArticleDeletedIsFalseAndArticleSoldAndArticleValidityDurationGreaterThan(categoryRef, userRef, sold, 0).stream().map(Bid::getArticle).distinct().map(mapper::fromArticleToDTO)
                        .collect(Collectors.toList());
        else if(categoryRef != null)
            return articleRepository.findByCategoryReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(categoryRef, sold, 0).stream().
                    map(mapper::fromArticleToDTO).collect(Collectors.toList());
        else if(userRef != null)
            if(!byBid)
                return articleRepository.findByCreatedByReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(userRef, sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
            else
                return bidRepository.findByUserReferenceAndArticleSold(userRef, sold).stream().map(Bid::getArticle).distinct().map(mapper::fromArticleToDTO)
                        .collect(Collectors.toList());
        else
            return articleRepository.findByDeletedIsFalseAndSoldAndValidityDurationGreaterThan(sold, 0).stream().map(mapper::fromArticleToDTO)
                    .collect(Collectors.toList());
    }

    public List<ArticleDTO> getArticles(String categoryRef, String userRef, String label, boolean sold) throws MazadException
    {

        if(categoryRef != null && userRef != null)
            if(label == null || label.isEmpty())
                return articleRepository.findByCategoryReferenceAndCreatedByReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan
                        (categoryRef, userRef, sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
            else
                return articleRepository.findByCategoryReferenceAndCreatedByReferenceAndLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan
                        (categoryRef, userRef, label.toUpperCase(), sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
        else if(categoryRef != null)
            if(label == null || label.isEmpty())
                return articleRepository.findByCategoryReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(categoryRef, sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
            else
                return articleRepository.findByCategoryReferenceAndLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan
                        (categoryRef, label.toUpperCase(), sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());

        else if(userRef != null)
            if(label == null || label.isEmpty())
                return articleRepository.findByCreatedByReferenceAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(userRef, sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
            else
                return articleRepository.findByCreatedByReferenceAndLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan
                        (userRef, label.toUpperCase(), sold, 0).stream().
                        map(mapper::fromArticleToDTO).collect(Collectors.toList());
        else if(label == null || label.isEmpty())
            return articleRepository.findByDeletedIsFalseAndSoldAndValidityDurationGreaterThan(sold, 0).stream().map(mapper::fromArticleToDTO)
                    .collect(Collectors.toList());
        else
            return articleRepository.findByLabelIgnoreCaseContainingAndDeletedIsFalseAndSoldAndValidityDurationGreaterThan(label.toUpperCase(), sold, 0).stream().map(mapper::fromArticleToDTO)
                    .collect(Collectors.toList());
    }

    public ArticleDTO updateArticleAvatar(String articleRef, MultipartFile avatar) throws MazadException, IOException
    {
        return photoService.createArticleAvatar(avatar, articleRef);
    }

    public ArticleDTO createArticle(ArticleDTO article, List<MultipartFile> avatars) throws MazadException, IOException
    {

        article.setReference(TokenUtil.generateReference());
        article.setCurrentPrice(article.getStartingPrice());
        article.setCreationDate(LocalDateTime.now());
        article.setValidityDuration(article.getValidityDuration() != null ? article.getValidityDuration() : DEFAULT_VALIDITY_DURATION);

        Article ar = articleRepository.save(mapper.fromDTOToArticle(article));
        if(avatars != null && !avatars.isEmpty())
        {

            String reference = ar.getReference();
            avatars.forEach(av -> {
                try
                {
                    photoService.createPhoto(av, reference);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            });
        }
        return mapper.fromArticleToDTO(articleRepository.findOneByReference(ar.getReference()).orElse(new Article()));
    }

    public ArticleDTO updateArticle(ArticleDTO articleDTO) throws MazadException
    {

        Article article = articleRepository.findOneByReference(articleDTO.getReference()).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, articleDTO.getReference() + "", MazadException.WITH_REF));

        Integer validity = article.getValidityDuration();

        mapper.updateArticleFromDto(articleDTO, article);

        if(article.getValidityDuration() < validity)
            article.setValidityDuration(validity);

        return mapper.fromArticleToDTO(articleRepository.save(article));
    }

    public ArticleDTO checkValidity(String reference, boolean addCounter) throws MazadException
    {
        Article article = articleRepository.findOneByReference(reference).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, reference + "", MazadException.WITH_REF));

        if(article.getCreationDate() == null)
            article.setCreationDate(LocalDateTime.now());
        if(((Long)Duration.between(article.getCreationDate(), LocalDateTime.now()).toMinutes()).compareTo(article.getValidityDuration().longValue()) > 0)
        {
            article.setValidityDuration(-1);
        }

        if(addCounter)
        {
            if(article.getValidityDuration().compareTo(-1) == 0)
            {
                articleRepository.save(article);
                throw MazadException.unprocessableEntityExceptionBuilder("article.invalide", new String[]{reference});
            }
            else if(new Long(article.getValidityDuration().longValue()).compareTo(Duration.between(article.getCreationDate(), LocalDateTime.now()).toMinutes()) < 4)
            {
                article.setValidityDuration(article.getValidityDuration() + 4);
            }
        }

        return mapper.fromArticleToDTO(articleRepository.save(article));
    }

    public JSONObject deleteArticle(String reference) throws MazadException
    {

        Article article = articleRepository.findOneByReference(reference).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, reference + "", MazadException.WITH_REF));

        article.setDeletionDate(LocalDateTime.now());
        article.setDeleted(true);

        articleRepository.save(article);
        JSONObject result = new JSONObject();
        result.put("result", "delete-success");
        return result;
    }

    public ArticleDTO addPhoto(String articleRef, MultipartFile avatar) throws MazadException, IOException
    {

        Photo photo = photoService.createPhoto(avatar, articleRef);
        ArticleDTO articleDTO = getArticleByReference(articleRef);
        articleDTO.getPhotos().add(mapper.fromPhotoToDTO(photo));
        return articleDTO;
    }

    public List<PhotoDTO> getPhotos(String articleRef) throws MazadException
    {
        return getArticleByReference(articleRef).getPhotos().stream().collect(Collectors.toList());
    }

    public ArticleDTO removePhoto(String articleRef, String reference) throws MazadException, IOException
    {

        photoService.removePhoto(photoService.getPhotoByArticle(reference, articleRef));
        return getArticleByReference(articleRef);
    }
}

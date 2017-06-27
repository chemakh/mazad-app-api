package ch.com.mazad.service;

import ch.com.mazad.domain.Article;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.ArticleRepository;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.utils.TokenUtil;
import ch.com.mazad.web.dto.ArticleDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 27/06/2017.
 */

@Service
public class ArticleService {

    private final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private DTOMapper mapper;

    public ArticleDTO getArticleByReference(String reference) throws MazadException {
        return articleRepository.findOneByReference(reference).map(mapper::fromArticleToDTO).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, reference + "", MazadException.WITH_REF));
    }

    public List<ArticleDTO> getArticleByCategory(String category) throws MazadException {
        return articleRepository.findByCategoryNameAndDeletedIsFalse(category).
                stream().map(mapper::fromArticleToDTO).collect(Collectors.toList());
    }

    public ArticleDTO createArticle(ArticleDTO article) {

        article.setReference(TokenUtil.generateReference());
        return mapper.fromArticleToDTO(articleRepository.save(mapper.fromDTOToArticle(article)));

    }

    public ArticleDTO updateArticle(ArticleDTO articleDTO) throws MazadException {

        Article article = articleRepository.findOneByReference(articleDTO.getReference()).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, articleDTO.getReference() + "", MazadException.WITH_REF));

        mapper.updateArticleFromDto(articleDTO, article);

        return mapper.fromArticleToDTO(articleRepository.save(article));

    }

    public JSONObject deleteArticle(String reference) throws MazadException {

        Article article = articleRepository.findOneByReference(reference).
                orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, reference + "", MazadException.WITH_REF));

        article.setDeletionDate(LocalDateTime.now());
        article.setDeleted(true);

        articleRepository.save(article);
        JSONObject result = new JSONObject();
        result.put("result", "delete-success");
        return result;


    }


}
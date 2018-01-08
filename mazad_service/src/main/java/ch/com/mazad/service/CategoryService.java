package ch.com.mazad.service;

import ch.com.mazad.domain.Category;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.ArticleRepository;
import ch.com.mazad.repository.CategoryRepository;
import ch.com.mazad.utils.TokenUtil;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 27/06/2017.
 */

@Service
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private PhotoService photoService;

    public Category createCategory(Category category, MultipartFile avatar) throws MazadException, IOException {

        category.setReference(TokenUtil.generateReference());
        if (avatar != null)
            category.setAvatar(photoService.createPhoto("Category", avatar, null));
        return categoryRepository.save(category);
    }

    public Category updateCategoryAvatar(String categoryRef, MultipartFile avatar) throws MazadException, IOException {
        return photoService.createCategoryAvatar(avatar, categoryRef);
    }

    public Category updateCategory(Category category, String reference) throws MazadException {

        categoryRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Category.class, reference));

        return categoryRepository.save(category);
    }

    public List<Category> getCategory(String name) throws MazadException {

        if (name != null)
            return Collections.singletonList(categoryRepository.findOneByName(name)
                    .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Category.class, name)));

        else
            return categoryRepository.findAll();
    }

    public JSONObject deleteCategory(String reference) throws MazadException {

        Category category = categoryRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Category.class, reference));

        articleRepository.save(articleRepository.findByCategoryReference(reference).stream().map(article ->
        {
            article.setCategory(null);
            return article;
        }).collect(Collectors.toList()));

        categoryRepository.delete(category);

        JSONObject result = new JSONObject();
        result.put("result", "delete-success");
        return result;
    }

}

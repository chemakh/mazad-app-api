package ch.com.mazad.service;

import ch.com.mazad.domain.Category;
import ch.com.mazad.domain.Photo;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.PhotoRepository;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.PhotoDTO;
import ch.com.mazad.web.dto.UserDTO;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 22/09/2017.
 */
@Service
public class TestService
{
    private static Faker faker = new Faker();
    private static Integer AVATAR_NUMBER = 24;
    @Inject
    private ArticleService articleService;
    @Inject
    private PhotoRepository photoRepository;
    @Inject
    private UserService userService;
    @Inject
    private DTOMapper mapper;
    @Inject
    private CategoryService categoryService;

    public List<ArticleDTO> generateFakerArticles(Integer number) throws MazadException, IOException
    {
        UserDTO user = userService.getLoggedUser();
        List<Category> categories = categoryService.getCategory(null);
        List<PhotoDTO> photos = generateFakerAvatar();

        List<ArticleDTO> articles = new ArrayList<>();

        for (Integer i = 0; i < number; i++)
        {
            ArticleDTO article = createFakerArticle(user, photos.get(i % photos.size()), categories.get(i % categories.size()).getName());
            articles.add(article);
        }

        return articles;
    }

    private List<PhotoDTO> generateFakerAvatar()
    {
        List<Photo> photos = new ArrayList<>();
        for (Integer i = 1; i < AVATAR_NUMBER + 1; i++)
        {

            if(!photoRepository.findOneByReference("avatar" + i).isPresent())
            {
                Photo photo = new Photo();
                photo.setReference("avatar" + i);
                photo.setLabel("avatar : " + i);
                photo.setName(i + ".jpg");

                photoRepository.save(photo);
            }
            photos.add(photoRepository.findOneByReference("avatar" + i).get());
        }
        return photos.stream().map(mapper::fromPhotoToDTO).collect(Collectors.toList());
    }

    private ArticleDTO createFakerArticle(UserDTO createdBy, PhotoDTO photoDTO, String cat) throws MazadException, IOException
    {

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setCreatedByUserReference(createdBy.getReference());
        articleDTO.setValidityDuration(100);
        articleDTO.setLabel(faker.name().title());
        articleDTO.setCreationDate(LocalDateTime.now());
        articleDTO.setAvatar(photoDTO);
        articleDTO.setBuyItNowPrice(new BigDecimal(faker.number().randomNumber(4, true)));
        articleDTO.setCategory(cat);
        articleDTO.setDescription(faker.lorem().paragraph());
        articleDTO.setStartingPrice(new BigDecimal(faker.number().randomNumber(3, true)));

        return articleService.createArticle(articleDTO, null);
    }
}

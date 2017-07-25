package ch.com.mazad.web;

import ch.com.mazad.domain.Bid;
import ch.com.mazad.exception.FieldErrorDTO;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.service.ArticleService;
import ch.com.mazad.service.BidService;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.BidDTO;
import ch.com.mazad.web.dto.PhotoDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Chemakh on 27/06/2017.
 */

@RestController
@RequestMapping("/ws/articles")
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Inject
    private ArticleService articleService;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private BidService bidService;

    @PostMapping(value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create Article Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ArticleDTO createArticle(@RequestPart("article") String article, @RequestPart(value = "photos", required = false) List<MultipartFile> photos) throws MazadException, IOException {

    	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        ArticleDTO articleDTO = objectMapper.readValue(article, ArticleDTO.class);
        final Set<ConstraintViolation<ArticleDTO>> violations = validator.validate(articleDTO);
        
        if (violations.size() > 0) {
            violations.forEach(u -> System.out.println("  \"" + u.getPropertyPath().toString() + "\"" + " " + u.getMessage()));
            
            ConstraintViolation<ArticleDTO> violation = violations.stream().findFirst().get();
            
            throw MazadException.validationErrorBuilder(new FieldErrorDTO("Article", 
            		violation.getPropertyPath().toString(),violation.getMessage()));
            
            
        } 
    	
    	return articleService.createArticle(articleDTO, photos);
    }

    @PutMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Article Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 404, message = "Object with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")

    })
    public ArticleDTO updateArticle(@Valid @RequestBody ArticleDTO articleDTO, @RequestParam("reference") String reference) throws MazadException {

        articleDTO.setReference(reference);
        return articleService.updateArticle(articleDTO);
    }

    @PutMapping(value = "avatar",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Article Avatar Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 404, message = "Object with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")

    })
    public ArticleDTO updateArticleAvatar(@RequestParam("article_ref") String articleRef, @RequestPart(value = "photo") MultipartFile photo) throws MazadException, IOException {

        return articleService.updateArticleAvatar(articleRef, photo);
    }

    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Article Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 404, message = "Article with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public List<ArticleDTO> getArticles(@RequestParam(value = "reference", required = false) String reference,
                                        @RequestParam(value = "category_ref", required = false) String categoryRef,
                                        @RequestParam(value = "user_ref", required = false) String userRef,
                                        @RequestParam(value = "by_bid", required = false) boolean byBid) throws MazadException {
        return articleService.getArticles(reference, categoryRef, userRef, byBid);
    }

    @DeleteMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Article Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 404, message = "Article with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public JSONObject deleteArticle(@RequestParam("reference") String reference) throws MazadException {

        return articleService.deleteArticle(reference);
    }

    @PostMapping(value = "bids",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create Bid Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Bid.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public BidDTO createBid(@Valid @RequestBody BidDTO bidDTO, @RequestParam("reference_article") String referenceArticle
            , @RequestParam("reference_user") String referenceUser) throws MazadException {

        return bidService.createBid(bidDTO, referenceArticle, referenceUser);
    }

    @GetMapping(value = "bids",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Bid Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Bid.class),
            @ApiResponse(code = 404, message = "Bid with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public List<BidDTO> getBid(@RequestParam(value = "reference", required = false) String reference,
                               @RequestParam("reference_article") String referenceArticle
            , @RequestParam("reference_user") String referenceUser) throws MazadException {

        return bidService.getBid(reference, referenceArticle, referenceUser);
    }

    @DeleteMapping(value = "bids",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Bid Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Bid.class),
            @ApiResponse(code = 404, message = "Bid with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public JSONObject cancelBid(@RequestParam("reference") String reference,
                                @RequestParam("reference_article") String referenceArticle
            , @RequestParam(value = "reference_user", required = false) String referenceUser) throws MazadException {

        return bidService.cancelBid(reference, referenceArticle, referenceUser);
    }


    @PostMapping(value = "photos",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add Photo Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ArticleDTO addPhoto(@RequestParam("article_ref") String articleRef, @RequestPart(value = "photo") MultipartFile photo) throws MazadException, IOException {

        return articleService.addPhoto(articleRef, photo);
    }

    @DeleteMapping(value = "photos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Remove Photo Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 404, message = "Object with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")

    })
    public ArticleDTO removePhoto(@RequestParam("article_ref") String articleRef, @RequestParam("reference") String reference) throws MazadException, IOException {

        return articleService.removePhoto(articleRef, reference);
    }

    @GetMapping(value = "photos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Article Photos Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 404, message = "Article with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public List<PhotoDTO> getPhotos(@RequestParam("article_ref") String articleRef) throws MazadException {
        return articleService.getPhotos(articleRef);
    }


}

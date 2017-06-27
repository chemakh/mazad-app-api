package ch.com.mazad.web;

import ch.com.mazad.exception.MazadException;
import ch.com.mazad.service.ArticleService;
import ch.com.mazad.web.dto.ArticleDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Chemakh on 27/06/2017.
 */

@RestController
@RequestMapping("/ws/article")
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);  

    @Inject
    private ArticleService articleService;

    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create Article Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ArticleDTO createArticle(@Valid @RequestBody ArticleDTO articleDTO) {

        return articleService.createArticle(articleDTO);
    }

    @RequestMapping(value = "/",
            method = RequestMethod.PUT,
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

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Article Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = ArticleDTO.class),
            @ApiResponse(code = 404, message = "Article with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public ArticleDTO getArticle(@RequestParam(value = "reference", required = false) String reference) throws MazadException {

        return articleService.getArticleByReference(reference);
    }

    @RequestMapping(value = "/",
            method = RequestMethod.DELETE,
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



}

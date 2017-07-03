package ch.com.mazad.web;

import ch.com.mazad.domain.Bid;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.service.ArticleService;
import ch.com.mazad.service.BidService;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.BidDTO;
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
import java.util.List;

/**
 * Created by Chemakh on 27/06/2017.
 */

@RestController
@RequestMapping("/ws/article")
public class ArticleController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Inject
    private ArticleService articleService;

    @Inject
    private BidService bidService;

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
    public List<ArticleDTO> getArticles(@RequestParam(value = "reference", required = false) String reference,
                                        @RequestParam(value = "category_ref", required = false) String categoryRef,
                                        @RequestParam(value = "user_ref", required = false) String userRef,
                                        @RequestParam(value = "user_ref", required = false) boolean byBid) throws MazadException {
        return articleService.getArticles(reference, categoryRef, userRef, byBid);
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

    @RequestMapping(value = "/bids",
            method = RequestMethod.POST,
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

    @RequestMapping(value = "/bids",
            method = RequestMethod.GET,
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

    @RequestMapping(value = "/bids",
            method = RequestMethod.DELETE,
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


}

package ch.com.mazad.web;

import ch.com.mazad.domain.Sale;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.service.SaleService;
import ch.com.mazad.web.dto.SaleDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Chemakh on 21/08/2017.
 */

@RestController
@RequestMapping("/ws/sales")
public class SaleController
{

    @Inject
    private SaleService saleService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create Sale Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = SaleDTO.class),
            @ApiResponse(code = 400, message = "Validation Error, Database conflict"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public SaleDTO createSale(@RequestParam("Bid_reference") String bidReference,
                           @RequestParam("article_reference") String articleReference) throws MazadException
    {
        return saleService.createSale(bidReference,articleReference);
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Sale Details Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Sale.class),
            @ApiResponse(code = 404, message = "Sale with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public List<SaleDTO> getSale(@RequestParam(value = "reference", required = false) String reference) throws MazadException {

        return saleService.getSale(reference);
    }

    @RequestMapping(value = "",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Sale Service")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Executed Successfully", response = Sale.class),
            @ApiResponse(code = 404, message = "Sale with Ref not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public JSONObject deleteSale(@RequestParam("reference") String reference) throws MazadException {

        return saleService.deleteSale(reference);
    }
}

package ch.com.mazad.service;

import ch.com.mazad.domain.Article;
import ch.com.mazad.domain.Bid;
import ch.com.mazad.domain.Sale;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.ArticleRepository;
import ch.com.mazad.repository.BidRepository;
import ch.com.mazad.repository.SaleRepository;
import ch.com.mazad.web.dto.SaleDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 21/08/2017.
 */
@Service
public class SaleService
{
    private final Logger logger = LoggerFactory.getLogger(SaleService.class);
    @Inject
    private SaleRepository saleRepository;
    @Inject
    private BidRepository bidRepository;
    @Inject
    private ArticleRepository articleRepository;

    public SaleDTO createSale(String bidReference, String articleReference) throws MazadException
    {
        Bid bid = bidRepository.findOneByReference(bidReference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Bid.class, bidReference));

        Article article = articleRepository.findOneByReference(articleReference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Article.class, articleReference));

        if(!article.equals(bid.getArticle()))
            throw MazadException.resourceNotFoundExceptionBuilder(Article.class, articleReference);

        if(article.isSold())
            throw MazadException.unprocessableEntityExceptionBuilder("article.sold", null);

        article.setSold(true);

        Sale sale = new Sale();
        sale.setReference(bidReference);
        sale.setBid(bid);
        sale.setArticle(article);
        sale.setSoldPrice(bid.getFinalPrice());
        sale.setSoldDate(LocalDateTime.now());

        articleRepository.save(article);
        return fromSaleToDTO(saleRepository.save(sale));
    }

    public List<SaleDTO> getSale(String reference) throws MazadException
    {

        if(reference != null)
            return Collections.singletonList(saleRepository.findOneByReference(reference).map(this::fromSaleToDTO)
                    .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Sale.class, reference)));

        else
            return saleRepository.findAll().stream().map(this::fromSaleToDTO).collect(Collectors.toList());
    }

    public JSONObject deleteSale(String reference) throws MazadException
    {

        Sale sale = saleRepository.findOneByReference(reference)
                .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Sale.class, reference));

        articleRepository.save(articleRepository.findOneByReference(sale.getBid().getArticle().getReference()).map(article ->
        {
            article.setSold(false);
            return article;
        }).orElse(null));

        saleRepository.delete(sale);

        JSONObject result = new JSONObject();
        result.put("result", "delete-success");
        return result;
    }

    public SaleDTO fromSaleToDTO(Sale sale)
    {
        if(sale == null)
        {
            return null;
        }

        SaleDTO saleDTO = new SaleDTO();

        saleDTO.setReference(sale.getReference());
        saleDTO.setSoldDate(sale.getSoldDate());
        saleDTO.setSoldPrice(sale.getSoldPrice());
        saleDTO.setArticleReference(sale.getArticle().getReference());
        saleDTO.setBidReference(sale.getBid().getReference());
        saleDTO.setSoldByUserReference(sale.getBid().getUser().getReference());

        return saleDTO;
    }
}

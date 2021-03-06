package ch.com.mazad.service;

import ch.com.mazad.domain.Bid;
import ch.com.mazad.exception.MazadException;
import ch.com.mazad.repository.BidRepository;
import ch.com.mazad.security.SecurityUtils;
import ch.com.mazad.utils.DTOMapper;
import ch.com.mazad.utils.TokenUtil;
import ch.com.mazad.web.dto.ArticleDTO;
import ch.com.mazad.web.dto.BidDTO;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chemakh on 28/06/2017.
 */

@Service
public class BidService {

    @Inject
    private BidRepository bidRepository;

    @Inject
    private DTOMapper mapper;

    @Inject
    private ArticleService articleService;
    @Inject
    private PushNotificationsService pushNotificationsService;

    public BidDTO createBid(BidDTO bidDTO, String articleRef, String userRef, boolean buyItNow) throws MazadException
    {

        if(bidDTO.getBidAmount().compareTo(BigDecimal.ZERO) < 0)
            throw MazadException.unprocessableEntityExceptionBuilder("article.bid_negative", null);

        ArticleDTO articleDTO = articleService.checkValidity(articleRef,true);
        if (!articleDTO.isSold()) {

            if(articleDTO.getBidAmount() != null && articleDTO.getBidAmount().compareTo(BigDecimal.ZERO) > 0 && (articleDTO.getBidAmount().compareTo(bidDTO.getBidAmount()) <= 0 && !buyItNow))
                throw MazadException.unprocessableEntityExceptionBuilder("article.bid_amount", new String[]{articleRef,articleDTO.getBidAmount()+""});

            bidDTO.setInitialPrice(articleDTO.getCurrentPrice());
            articleDTO.setCurrentPrice(articleDTO.getCurrentPrice().add(bidDTO.getBidAmount()));
            bidDTO.setFinalPrice(articleDTO.getCurrentPrice());
            bidDTO.setArticleReference(articleRef);
            bidDTO.setUserReference(userRef);
            bidDTO.setReference(TokenUtil.generateReference());
            bidDTO.setCreationDate(LocalDateTime.now());
            articleDTO = articleService.updateArticle(articleDTO);
            if(!buyItNow)
                pushNotificationsService.notify(articleDTO, userRef, PushNotificationsService.BID_CREATION);
            return mapper.fromBidToDTO(bidRepository.save(mapper.fromDTOToBid(bidDTO)));
        }

        throw MazadException.unprocessableEntityExceptionBuilder("article.sold", null);

    }


    public List<BidDTO> getBid(String reference, String articleRef, String userRef) throws MazadException {

        if (reference != null)
            return Collections.singletonList(bidRepository.findOneByReferenceAndArticleReference(reference, articleRef).map(mapper::fromBidToDTO)
                    .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Bid.class, reference)));
        else if (articleRef != null && userRef != null)
            return bidRepository.findByArticleReferenceAndUserReference(articleRef, userRef).stream().map(mapper::fromBidToDTO)
                    .collect(Collectors.toList());
        else if (articleRef != null)
            return bidRepository.findByArticleReference(articleRef).stream().map(mapper::fromBidToDTO)
                    .collect(Collectors.toList());
        else if (userRef != null)
            return bidRepository.findByUserReference(userRef).stream().map(mapper::fromBidToDTO)
                    .collect(Collectors.toList());
        else
            return Collections.emptyList();
    }

    public JSONObject cancelBid(String reference, String referenceArticle, String refUser) throws MazadException {

        if (SecurityUtils.isCurrentUserAdmin()) {
            Bid bid = bidRepository.findOneByReferenceAndArticleReference(reference, referenceArticle)
                    .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Bid.class, reference));

            bidRepository.delete(bid);

            JSONObject result = new JSONObject();
            result.put("result", "delete-success");
            return result;
        } else {
            Bid bid = bidRepository.findOneByReferenceAndArticleReferenceAndUserReference(reference, referenceArticle, refUser)
                    .orElseThrow(() -> MazadException.resourceNotFoundExceptionBuilder(Bid.class, reference));

            bidRepository.delete(bid);

            JSONObject result = new JSONObject();
            result.put("result", "delete-success");
            return result;
        }

    }


}

package ch.com.mazad.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Chemakh on 18/06/2017.
 */

public class BidDTO implements Serializable
{

    private String reference;

    private String articleReference;

    private BigDecimal initialPrice;

    private BigDecimal bidAmount;

    private BigDecimal finalPrice;

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getArticleReference() {
        return articleReference;
    }

    public void setArticleReference(String articleReference) {
        this.articleReference = articleReference;
    }

    public BigDecimal getInitialPrice()
    {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice)
    {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getBidAmount()
    {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount)
    {
        this.bidAmount = bidAmount;
    }

    public BigDecimal getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice)
    {
        this.finalPrice = finalPrice;
    }
}

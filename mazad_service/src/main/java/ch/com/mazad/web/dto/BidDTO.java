package ch.com.mazad.web.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Chemakh on 18/06/2017.
 */

public class BidDTO implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4205844204687786799L;

	private String reference;

    private String articleReference;

    private String userReference;

    private LocalDateTime creationDate;

    private BigDecimal initialPrice;

    @NotNull
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

    public String getUserReference() {
        return userReference;
    }

    public void setUserReference(String userReference) {
        this.userReference = userReference;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

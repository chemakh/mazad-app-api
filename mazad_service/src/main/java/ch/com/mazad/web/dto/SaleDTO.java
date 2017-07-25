package ch.com.mazad.web.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Chemakh on 18/06/2017.
 */

public class SaleDTO implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7596633276412393609L;

	private String reference;

    private String articleReference;

    private String bidReference;

    private LocalDateTime soldDate;

    private String soldByUserReference;

    private BigDecimal soldPrice;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getArticleReference() {
        return articleReference;
    }

    public void setArticleReference(String articleReference) {
        this.articleReference = articleReference;
    }

    public String getBidReference() {
        return bidReference;
    }

    public void setBidReference(String bidReference) {
        this.bidReference = bidReference;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDateTime soldDate) {
        this.soldDate = soldDate;
    }

    public String getSoldByUserReference() {
        return soldByUserReference;
    }

    public void setSoldByUserReference(String soldByUserReference) {
        this.soldByUserReference = soldByUserReference;
    }

    public BigDecimal getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice) {
        this.soldPrice = soldPrice;
    }
}

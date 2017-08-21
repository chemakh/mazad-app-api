package ch.com.mazad.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Chemakh on 18/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "index_bid_reference", columnList = "reference", unique = true)})
public class Bid implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6711288240952602931L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String reference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime creationDate;

    private BigDecimal oldBid;

    private BigDecimal bidAmount;

    private BigDecimal actualBid;

    private BigDecimal initialPrice;

    private BigDecimal finalPrice;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public Article getArticle()
    {
        return article;
    }

    public void setArticle(Article article)
    {
        this.article = article;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBidAmount()
    {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount)
    {
        this.bidAmount = bidAmount;
    }

    public BigDecimal getOldBid() {
        return oldBid;
    }

    public void setOldBid(BigDecimal oldBid) {
        this.oldBid = oldBid;
    }

    public BigDecimal getActualBid() {
        return actualBid;
    }

    public void setActualBid(BigDecimal actualBid) {
        this.actualBid = actualBid;
    }

    public BigDecimal getInitialPrice()
    {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice)
    {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice)
    {
        this.finalPrice = finalPrice;
    }

    @Override
    public boolean equals(Object object) {
        return Optional.ofNullable(object).filter(obj -> obj instanceof Bid).map(obj -> (Bid) obj).
                filter(ts -> this.reference == null || Objects.equals(ts.getReference(), this.reference)).
                filter(ts -> this.reference != null || Objects.equals(ts, this)).
                isPresent();
    }

    @Override
    public int hashCode() {
        if (this.getReference() != null)
            return this.getReference().hashCode();
        else
            return super.hashCode();
    }
}

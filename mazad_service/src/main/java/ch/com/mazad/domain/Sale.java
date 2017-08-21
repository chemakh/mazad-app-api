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
@Table(indexes = {@Index(name = "index_sale_reference", columnList = "reference", unique = true)})
public class Sale implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 6744219298748196922L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String reference;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bid_id")
    private Bid bid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;

    private LocalDateTime soldDate;

    private BigDecimal soldPrice;

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

    public Bid getBid()
    {
        return bid;
    }

    public void setBid(Bid bid)
    {
        this.bid = bid;
    }

    public LocalDateTime getSoldDate()
    {
        return soldDate;
    }

    public void setSoldDate(LocalDateTime soldDate)
    {
        this.soldDate = soldDate;
    }

    public BigDecimal getSoldPrice()
    {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice)
    {
        this.soldPrice = soldPrice;
    }

    public Article getArticle()
    {
        return article;
    }

    public void setArticle(Article article)
    {
        this.article = article;
    }

    @Override
    public boolean equals(Object object)
    {
        return Optional.ofNullable(object).filter(obj -> obj instanceof Sale).map(obj -> (Sale)obj).
                filter(ts -> this.reference == null || Objects.equals(ts.getReference(), this.reference)).
                filter(ts -> this.reference != null || Objects.equals(ts, this)).
                isPresent();
    }

    @Override
    public int hashCode()
    {
        if(this.getReference() != null)
            return this.getReference().hashCode();
        else
            return super.hashCode();
    }
}

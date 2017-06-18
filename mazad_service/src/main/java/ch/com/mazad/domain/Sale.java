package ch.com.mazad.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Chemakh on 18/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "index_sale_reference", columnList = "reference", unique = true)})
public class Sale implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String reference;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;

    private Bid bid;
    
    private LocalDateTime soldDate;

    private User soldBy;

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

    public Article getArticle()
    {
        return article;
    }

    public void setArticle(Article article)
    {
        this.article = article;
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

    public User getSoldBy()
    {
        return soldBy;
    }

    public void setSoldBy(User soldBy)
    {
        this.soldBy = soldBy;
    }

    public BigDecimal getSoldPrice()
    {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice)
    {
        this.soldPrice = soldPrice;
    }
}

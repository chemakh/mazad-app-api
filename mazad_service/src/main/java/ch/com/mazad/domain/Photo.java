package ch.com.mazad.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Chemakh on 18/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "index_photo_reference", columnList = "reference", unique = true)})
public class Photo implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String reference;

    private String label;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

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

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
    public boolean equals(Object object) {
        return Optional.ofNullable(object).filter(obj -> obj instanceof Photo).map(obj -> (Photo) obj).
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

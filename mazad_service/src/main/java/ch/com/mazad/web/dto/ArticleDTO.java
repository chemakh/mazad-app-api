package ch.com.mazad.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chemakh on 18/06/2017.
 */
public class ArticleDTO implements Serializable
{
    private String reference;

    @NotNull
    @Size(min = 2,max = 255)
    private String label;

    @NotNull
    @Size(min = 2,max = 255)
    private String description;

    private String createdByUserReference;

    private LocalDateTime creationDate;

    private BigDecimal initialPrice;

    private BigDecimal currentPrice;

    private BigDecimal finalPrice;

    private boolean sold;

    private PhotoDTO avatar;

    private Set<PhotoDTO> photos = new HashSet<>();

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCreatedByUserReference() {
        return createdByUserReference;
    }

    public void setCreatedByUserReference(String createdByUserReference) {
        this.createdByUserReference = createdByUserReference;
    }

    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate)
    {
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

    public BigDecimal getCurrentPrice()
    {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice)
    {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getFinalPrice()
    {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice)
    {
        this.finalPrice = finalPrice;
    }

    public boolean isSold()
    {
        return sold;
    }

    public void setSold(boolean sold)
    {
        this.sold = sold;
    }

    public PhotoDTO getAvatar()
    {
        return avatar;
    }

    public void setAvatar(PhotoDTO avatar)
    {
        this.avatar = avatar;
    }

    public Set<PhotoDTO> getPhotos()
    {
        return photos;
    }

    public void setPhotos(Set<PhotoDTO> photos)
    {
        this.photos = photos;
    }
}

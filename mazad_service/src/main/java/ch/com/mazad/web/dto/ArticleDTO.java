package ch.com.mazad.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chemakh on 18/06/2017.
 */
public class ArticleDTO implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -6042860173316095067L;
    private String reference;
    @NotNull
    @Size(min = 2, max = 255)
    private String label;
    @NotNull
    @Size(min = 2, max = 255)
    private String description;
    private String createdByUserReference;
    private LocalDateTime creationDate;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal buyItNowPrice;
    private BigDecimal bidAmount;
    private boolean sold;
    private PhotoDTO avatar;
    private String category;
    private Integer validityDuration;
    private List<PhotoDTO> photos = new ArrayList<>();

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

    public String getCreatedByUserReference()
    {
        return createdByUserReference;
    }

    public void setCreatedByUserReference(String createdByUserReference)
    {
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

    public BigDecimal getStartingPrice()
    {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice)
    {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getCurrentPrice()
    {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice)
    {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getBuyItNowPrice()
    {
        return buyItNowPrice;
    }

    public void setBuyItNowPrice(BigDecimal buyItNowPrice)
    {
        this.buyItNowPrice = buyItNowPrice;
    }

    public BigDecimal getBidAmount()
    {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount)
    {
        this.bidAmount = bidAmount;
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

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public List<PhotoDTO> getPhotos()
    {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos)
    {
        this.photos = photos;
    }

    public Integer getValidityDuration()
    {
        return validityDuration;
    }

    public void setValidityDuration(Integer validityDuration)
    {
        this.validityDuration = validityDuration;
    }

    public String getTimeLeft()
    {
        Duration duration= Duration.between(LocalDateTime.now(), this.getCreationDate().plusDays(this.getValidityDuration()));
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "00:00:00" : positive;    }
}

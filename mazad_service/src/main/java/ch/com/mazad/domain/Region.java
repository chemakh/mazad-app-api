package ch.com.mazad.domain;

import javax.persistence.*;

/**
 * Created by Chemakh on 25/10/2017.
 */
@Entity
@Table(indexes = {@Index(name = "index_region_reference", columnList = "reference", unique = true)})
public class Region
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String reference;
    private String labelEn;
    private String labelAr;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabelAr()
    {
        return labelAr;
    }

    public void setLabelAr(String labelAr)
    {
        this.labelAr = labelAr;
    }

    public String getLabelEn()
    {
        return labelEn;
    }

    public void setLabelEn(String labelEn)
    {
        this.labelEn = labelEn;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }
}

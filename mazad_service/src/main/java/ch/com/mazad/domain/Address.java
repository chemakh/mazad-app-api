package ch.com.mazad.domain;

import javax.persistence.*;

/**
 * Created by Chemakh on 24/06/2017.
 */
@Entity
@Table(indexes = {@Index(name = "index_address_reference", columnList = "reference", unique = true)})
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String reference;

    private String country;

    private String streetAddress1;

    private String streetAddress2;

    private String city;

    private String region;

    private String postalCode;

    public Address()
    {
    }

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

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getStreetAddress1()
    {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1)
    {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2()
    {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2)
    {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
}

package ch.com.mazad.web.dto;

import ch.com.mazad.domain.GeoPoint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Chemakh on 24/06/2017.
 */
public class AddressDTO
{

    private String reference;

    @NotNull
    @Size(min = 2, max = 255)
    private String country;

    @NotNull
    @Size(min = 2, max = 255)
    private String streetAddress1;

    private String streetAddress2;

    @NotNull
    @Size(min = 2, max = 255)
    private String city;

    private String region;

    @NotNull
    @Size(min = 2, max = 255)
    private String postalCode;

    @NotNull
    private GeoPoint location;

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

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}

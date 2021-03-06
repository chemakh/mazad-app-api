package ch.com.mazad.web.dto;

import ch.com.mazad.domain.Sex;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Chemakh on 18/06/2017.
 */


public class UserDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 558348605877706510L;

	@NotNull
    @Size(min = 2,max = 255)
    private String firstname;

    @NotNull
    @Size(min = 2,max = 255)
    private String lastname;

    private String reference;

    @Size(min = 2,max = 255)
    @Email
    private String email;

    private String mobileNumber;

    @Size(min = 2,max = 255)
    private String password;

    private String langKey;

    private Sex sex;

    private PhotoDTO avatar;

    private AddressDTO address;

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getLangKey()
    {
        return langKey;
    }

    public void setLangKey(String langKey)
    {
        this.langKey = langKey;
    }


    public Sex getSex()
    {
        return sex;
    }

    public void setSex(Sex sex)
    {
        this.sex = sex;
    }

    public PhotoDTO getAvatar()
    {
        return avatar;
    }

    public void setAvatar(PhotoDTO avatar)
    {
        this.avatar = avatar;
    }

    public AddressDTO getAddress()
    {
        return address;
    }

    public void setAddress(AddressDTO address)
    {
        this.address = address;
    }
}

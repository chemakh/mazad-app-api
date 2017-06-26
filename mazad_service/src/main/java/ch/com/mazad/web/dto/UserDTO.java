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
    @NotNull
    @Size(min = 2,max = 255)
    private String firstname;

    @NotNull
    @Size(min = 2,max = 255)
    private String lastname;

    private String reference;

    @NotNull
    @Size(min = 2,max = 255)
    @Email
    private String email;

    @NotNull
    @Size(min = 2,max = 255)
    private String password;

    private String langKey;

    private Sex sex;

    private PhotoDTO avatar;

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
}

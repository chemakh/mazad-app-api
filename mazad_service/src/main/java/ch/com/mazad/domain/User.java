package ch.com.mazad.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Chemakh on 18/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "index_user_reference", columnList = "reference", unique = true)})
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String reference;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", length = 60, nullable = true)
    private String password;

    @Column(name = "activated", nullable = false)
    private boolean activated = true;

    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Column(name = "email_key", length = 20)
    private String emailKey;

    @Column(name = "reset_password_key", length = 20)
    private String resetPasswordKey;

    @Column(name = "is_mail_verified", columnDefinition = "tinyint(1) default 0")
    private boolean isMailVerified = false;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id")
    private Photo avatar;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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

    public boolean isActivated()
    {
        return activated;
    }

    public void setActivated(boolean activated)
    {
        this.activated = activated;
    }

    public String getLangKey()
    {
        return langKey;
    }

    public void setLangKey(String langKey)
    {
        this.langKey = langKey;
    }

    public String getEmailKey()
    {
        return emailKey;
    }

    public void setEmailKey(String emailKey)
    {
        this.emailKey = emailKey;
    }

    public String getResetPasswordKey()
    {
        return resetPasswordKey;
    }

    public void setResetPasswordKey(String resetPasswordKey)
    {
        this.resetPasswordKey = resetPasswordKey;
    }

    public boolean isMailVerified()
    {
        return isMailVerified;
    }

    public void setMailVerified(boolean mailVerified)
    {
        isMailVerified = mailVerified;
    }

    public Sex getSex()
    {
        return sex;
    }

    public void setSex(Sex sex)
    {
        this.sex = sex;
    }

    public Photo getAvatar()
    {
        return avatar;
    }

    public void setAvatar(Photo avatar)
    {
        this.avatar = avatar;
    }
}

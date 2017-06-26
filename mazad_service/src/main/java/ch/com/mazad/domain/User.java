package ch.com.mazad.domain;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    private String firstname;

    private String lastname;

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
    private boolean mailVerified = false;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id")
    private Photo avatar;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    private boolean deleted;

    private LocalDateTime deletionDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> authorities = new HashSet<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

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

    public boolean isMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(boolean mailVerified) {
        this.mailVerified = mailVerified;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public LocalDateTime getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(LocalDateTime deletionDate) {
        this.deletionDate = deletionDate;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object object) {
        return Optional.ofNullable(object).filter(obj -> obj instanceof User).map(obj -> (User) obj).
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

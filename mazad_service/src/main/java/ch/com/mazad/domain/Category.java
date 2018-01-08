package ch.com.mazad.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Chemakh on 27/06/2017.
 */

@Entity
@Table(indexes = {@Index(name = "index_category_reference", columnList = "reference", unique = true)})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 50)
    private String name;

    @Column(unique = true)
    private String reference;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id")
    private Photo avatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Photo getAvatar() {
        return avatar;
    }

    public void setAvatar(Photo avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        return Optional.ofNullable(o).filter(obj -> obj instanceof Category).
                map(obj -> (Category) obj).filter(category -> Objects.equals(category.getName(), this.name))
                .isPresent();
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

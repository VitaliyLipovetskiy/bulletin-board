package com.lvv.bulletinboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lvv.bulletinboard.util.validation.NoHtml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Vitalii Lypovetskyi
 */
@Entity
@Table(name = "ads")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ad extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    @NoHtml
    private String name;

    @NotBlank
    @Size(min = 2, max = 250)
    @Column(name = "description",nullable = false)
    @NoHtml
    private String description;

    @NotBlank
    @Size(min = 2, max = 250)
    @Column(name = "contact", nullable = false)
    @NoHtml
    private String contact;

    @Column(name = "image")
    @NoHtml
    private String image;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default false")
    private Boolean enabled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    public Ad(Integer id, String name, String description, String contact, String image, Boolean enabled) {
        super(id);
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.image = image;
        this.enabled = enabled;
    }

    public Ad(Integer id, String name, String description, String contact, String image) {
        this(id, name, description, contact, image, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ad ad = (Ad) o;
        return Objects.equals(name, ad.name) && Objects.equals(description, ad.description) && Objects.equals(contact, ad.contact) && Objects.equals(image, ad.image) && Objects.equals(enabled, ad.enabled) && Objects.equals(user, ad.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, contact, image, enabled, user);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", image='" + image + '\'' +
                ", enabled='" + enabled + '\'' +
                ", user=" + user +
                '}';
    }
}

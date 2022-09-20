package com.lvv.bulletinboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.io.Serializable;

/**
 * @author Vitalii Lypovetskyi
 */
@Entity
@Table(name = "ads")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ad extends BaseEntity implements Serializable {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    public Ad(Integer id, String name, String description, String contact, String image) {
        super(id);
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.image = image;

    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", image='" + image + '\'' +
                ", user=" + user +
                '}';
    }
}

package com.lvv.bulletinboard.model;

import com.fasterxml.jackson.annotation.*;
import com.lvv.bulletinboard.HasId;
import com.lvv.bulletinboard.util.validation.NoHtml;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Vitalii Lypovetskyi
 */
@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity implements HasId, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Ad ad;

    @Column(name = "date_time", nullable = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Column(name = "incoming", nullable = false)
    private Boolean incoming;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 250)
    @NoHtml
    private String description;

    public Board(Integer id, User user, Ad ad, Boolean incoming, String description) {
        super(id);
        this.user = user;
        this.ad = ad;
        this.incoming = incoming;
        this.dateTime = LocalDateTime.now();
        this.description = description;
    }

    public Board(User user, Ad ad, Boolean incoming, String description) {
        this(null, user, ad, incoming, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Board board = (Board) o;
        return Objects.equals(user, board.user) && Objects.equals(ad, board.ad) && Objects.equals(dateTime, board.dateTime) && Objects.equals(incoming, board.incoming) && Objects.equals(description, board.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, ad, dateTime, incoming, description);
    }
}

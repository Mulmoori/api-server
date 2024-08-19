package org.dongguk.vsa.mulmoori.narooteo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "narootos")
public class Narooteo {
    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -------------------------------------------- */
    /* Information Attribute ---------------------- */
    /* -------------------------------------------- */
    @Column(name = "title", length = 20, nullable = false)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name="participation_code", length = 10, nullable = false, updatable = false)
    private String participationCode;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* One to Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "narooteo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dialogue> dialogues = new ArrayList<>();

    @OneToMany(mappedBy = "narooteo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserNarooteo> users = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Narooteo(
            String title,
            String participationCode
    ) {
        this.title = title;
        this.participationCode = participationCode;

        createdAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}

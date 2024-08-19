package org.dongguk.vsa.mulmoori.dialogue.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.vsa.mulmoori.dialogue.domain.type.EDialogueStatus;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dialogues")
public class Dialogue {

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
    @Lob
    @Column(name="question", length = 1000, nullable = false)
    private String question;

    @Lob
    @Column(name = "answer", length = 1000)
    private String answer;

    @Column(name = "is_answered_by_llm")
    private Boolean isAnsweredByLlm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EDialogueStatus status;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "asked_at", nullable = false, updatable = false)
    private LocalDateTime askedAt;

    @Column(name = "replied_at", updatable = false)
    private LocalDateTime repliedAt;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "narooto_id", nullable = false, updatable = false)
    private Narooteo narooteo;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Dialogue(
            String question,
            User user,
            Narooteo narooteo
    ) {
        this.question = question;
        this.status = EDialogueStatus.PENDING;

        this.user = user;
        this.narooteo = narooteo;

        this.askedAt = LocalDateTime.now();
    }

    public void updateAnswer(
            String answer,
            Boolean isAnsweredByLlm,
            EDialogueStatus status
    ) {
        this.answer = answer;
        this.isAnsweredByLlm = isAnsweredByLlm;
        this.status = status;

        this.repliedAt = LocalDateTime.now();
    }

    public void updateStatus(EDialogueStatus status) {
        this.status = status;
    }
}

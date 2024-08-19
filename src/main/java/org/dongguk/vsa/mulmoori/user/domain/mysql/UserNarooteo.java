package org.dongguk.vsa.mulmoori.user.domain.mysql;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "user_narooteo",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_narooteo",
                        columnNames = {"modeullak_id", "user_id"}
                )
        }
)
public class UserNarooteo {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private ENarooteoRole role;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "narooteo_id", nullable = false, updatable = false)
    private Narooteo narooteo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserNarooteo(
            ENarooteoRole role,
            User user,
            Narooteo narooteo
    ) {
        this.role = role;
        this.user = user;
        this.narooteo = narooteo;
    }
}

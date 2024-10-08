package org.dongguk.vsa.mulmoori.user.domain.mysql;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityProvider;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityRole;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_user_account")
)
@DiscriminatorValue("USER")
@DynamicUpdate
public class User extends Account {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "nickname", length = 12, nullable = false)
    private String nickname;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserNarooteo> narootoes = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Dialogue> dialogues = new ArrayList<>();

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public User(
            ESecurityProvider provider,
            String serialId,
            String password,
            String nickname
    ) {
        super(
                provider,
                serialId,
                password
        );

        this.nickname = nickname;
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }
}

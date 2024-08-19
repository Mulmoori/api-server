package org.dongguk.vsa.mulmoori.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityProvider;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityRole;
import org.hibernate.annotations.DynamicUpdate;

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

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}

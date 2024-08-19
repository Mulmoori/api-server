package org.dongguk.vsa.mulmoori.security.domain.redis;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "authentication_code", timeToLive = 60 * 3)
public class AuthenticationCode {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "value")
    private String value;

    @Builder
    public AuthenticationCode(String email, String value) {
        this.email = email;
        this.value = value;
    }
}

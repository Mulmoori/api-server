package org.dongguk.vsa.mulmoori.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ENarooteoRole {

    HOST("관리자", "HOST"),
    PARTICIPANT("참가자", "PARTICIPANT")

    ;

    private final String koName;
    private final String enName;
}

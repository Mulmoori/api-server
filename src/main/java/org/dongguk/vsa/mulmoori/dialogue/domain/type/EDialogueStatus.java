package org.dongguk.vsa.mulmoori.dialogue.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDialogueStatus {
    PENDING("대기 중", "PENDING"),
    PROGRESS("진행 중", "PROGRESS"),
    COMPLETED("완료", "COMPLETED");

    private final String koName;
    private final String enName;
}

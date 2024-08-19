package org.dongguk.vsa.mulmoori.security.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface WithdrawalUseCase {
    /**
     * 회원탈퇴 요청을 처리하는 UseCase
     * @param accountId 회원탈퇴 요청을 하는 계정의 ID
     */
    void execute(UUID accountId);
}

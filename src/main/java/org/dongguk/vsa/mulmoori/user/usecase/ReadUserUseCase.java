package org.dongguk.vsa.mulmoori.user.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;
import org.dongguk.vsa.mulmoori.user.dto.response.UserDetailDto;

import java.util.UUID;

@UseCase
public interface ReadUserUseCase {

    /**
     * 사용자 조회 유스케이스
     *
     * @param accountId 사용자 식별자
     *
     * @return UserDetailDto
     */
    UserDetailDto execute(UUID accountId);
}

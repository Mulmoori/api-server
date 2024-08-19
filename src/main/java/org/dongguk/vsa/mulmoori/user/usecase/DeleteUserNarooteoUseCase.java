package org.dongguk.vsa.mulmoori.user.usecase;

import org.dongguk.vsa.mulmoori.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteUserNarooteoUseCase {

    void execute(UUID accountId, Long narooteoId);
}

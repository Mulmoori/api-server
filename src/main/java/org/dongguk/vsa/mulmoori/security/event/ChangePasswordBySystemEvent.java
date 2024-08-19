package org.dongguk.vsa.mulmoori.security.event;

import lombok.Builder;

@Builder
public record ChangePasswordBySystemEvent(
        String receiverAddress,
        String temporaryPassword
) {
}

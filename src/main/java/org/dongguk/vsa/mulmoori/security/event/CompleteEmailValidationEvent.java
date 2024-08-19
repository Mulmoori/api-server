package org.dongguk.vsa.mulmoori.security.event;

import lombok.Builder;

@Builder
public record CompleteEmailValidationEvent(
        String receiverAddress,
        String authenticationCode
) {
}


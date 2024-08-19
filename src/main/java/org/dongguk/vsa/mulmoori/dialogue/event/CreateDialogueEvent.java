package org.dongguk.vsa.mulmoori.dialogue.event;

import lombok.Builder;

@Builder
public record CreateDialogueEvent(
        Long id,
        String question
) {
}

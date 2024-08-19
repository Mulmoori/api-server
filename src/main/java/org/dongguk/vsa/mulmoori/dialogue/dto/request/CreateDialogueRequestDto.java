package org.dongguk.vsa.mulmoori.dialogue.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDialogueRequestDto(
        @JsonProperty("narooteo_id")
        @NotNull
        Long narooteoId,

        @JsonProperty("question")
        @Size(min = 1, max = 500)
        String question
) {
}

package org.dongguk.vsa.mulmoori.dialogue.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAnswerInDialogueRequestDto(
        @JsonProperty("answer")
        @Size(min = 1, max = 1000)
        String answer,

        @JsonProperty("is_using_stt")
        @NotNull
        Boolean isUsingStt
) {
}

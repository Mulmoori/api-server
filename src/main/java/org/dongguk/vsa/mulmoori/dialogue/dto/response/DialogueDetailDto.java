package org.dongguk.vsa.mulmoori.dialogue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.annotation.validation.DateTimeValue;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.core.utility.DateTimeUtil;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;

@Getter
public class DialogueDetailDto extends SelfValidating<DialogueDetailDto> {
    @JsonProperty("id")
    @NotNull
    private final Long id;

    @JsonProperty("question")
    @NotNull
    private final String question;

    @JsonProperty("asked_at")
    @DateTimeValue
    private final String askedAt;

    @JsonProperty("answer")
    private final String answer;

    @JsonProperty("replied_at")
    private final String repliedAt;

    @JsonProperty("is_answered_by_llm")
    private final Boolean isAnsweredByLlm;

    @Builder
    public DialogueDetailDto(
            Long id,
            String question,
            String askedAt,
            String answer,
            String repliedAt,
            Boolean isAnsweredByLlm
    ) {
        this.id = id;
        this.question = question;
        this.askedAt = askedAt;

        this.answer = answer;
        this.repliedAt = repliedAt;
        this.isAnsweredByLlm = isAnsweredByLlm;
    }

    public static DialogueDetailDto fromEntity(Dialogue entity) {
        String askedAt = DateTimeUtil.convertLocalDateTimeToString(entity.getAskedAt());
        String repliedAt = entity.getRepliedAt() == null ? null : DateTimeUtil.convertLocalDateTimeToString(entity.getRepliedAt());

        return DialogueDetailDto.builder()
                .id(entity.getId())
                .question(entity.getQuestion())
                .askedAt(askedAt)
                .answer(entity.getAnswer())
                .repliedAt(repliedAt)
                .isAnsweredByLlm(entity.getIsAnsweredByLlm())
                .build();
    }
}

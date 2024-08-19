package org.dongguk.vsa.mulmoori.dialogue.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.annotation.validation.DateTimeValue;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.core.utility.DateTimeUtil;
import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;

import java.util.List;
import java.util.Random;

@Getter
public class DialogueOverviewListDto extends SelfValidating<DialogueOverviewListDto> {

    @JsonProperty("overviews")
    @NotNull
    private final List<DialogueOverviewDto> overviews;

    @Builder
    public DialogueOverviewListDto(List<DialogueOverviewDto> overviews) {
        this.overviews = overviews;

        this.validateSelf();
    }

    public static DialogueOverviewListDto fromEntities(List<Dialogue> entities) {
        return DialogueOverviewListDto.builder()
                .overviews(entities.stream()
                        .map(DialogueOverviewDto::fromEntity)
                        .toList())
                .build();
    }

    @Getter
    public static class DialogueOverviewDto extends SelfValidating<DialogueOverviewDto> {
        @JsonProperty("id")
        @NotNull
        private final Long id;

        @JsonProperty("user_random_name")
        @NotNull
        private final String userRandomName;

        @JsonProperty("status")
        @NotNull
        private final String status;

        @JsonProperty("question")
        @NotNull
        private final String question;

        @JsonProperty("asked_at")
        @DateTimeValue
        private final String updateAt;

        @Builder
        public DialogueOverviewDto(
                Long id,
                String userRandomName,
                String status,
                String question,
                String updateAt
        ) {
            this.id = id;
            this.userRandomName = userRandomName;
            this.status = status;
            this.question = question;
            this.updateAt = updateAt;
        }

        public static DialogueOverviewDto fromEntity(Dialogue entity) {
            String status;

            if (entity.getAnswer() == null) {
                status = "답변 전";
            } else {
                status = entity.getIsAnsweredByLlm() ? "AI 답변" : "관리자 답변";
            }

            int randomAdjectiveIndex = new Random().nextInt(Constants.ADJECTIVES.size());
            int randomAnimalIndex = new Random().nextInt(Constants.ANIMALS.size());

            return DialogueOverviewDto.builder()
                    .id(entity.getId())
                    .userRandomName(Constants.ADJECTIVES.get(randomAdjectiveIndex) + " " + Constants.ANIMALS.get(randomAnimalIndex))
                    .status(status)
                    .question(entity.getQuestion())
                    .updateAt(DateTimeUtil.convertLocalDateTimeToString(entity.getAskedAt()))
                    .build();
        }
    }
}

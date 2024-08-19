package org.dongguk.vsa.mulmoori.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;

@Getter
public class UserDetailDto extends SelfValidating<UserDetailDto> {

    @JsonProperty("nickname")
    @NotBlank
    private final String nickname;


    @JsonProperty("participant_narooteo_count")
    @NotNull
    private final Integer participantNarooteoCount;


    @JsonProperty("participant_dialogue_count")
    @NotNull
    private final Integer participantDialogueCount;

    @Builder
    public UserDetailDto(
            String nickname,
            Integer participantNarooteoCount,
            Integer participantDialogueCount
    ) {
        this.nickname = nickname;
        this.participantNarooteoCount = participantNarooteoCount;
        this.participantDialogueCount = participantDialogueCount;
    }
}

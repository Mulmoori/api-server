package org.dongguk.vsa.mulmoori.narooteo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;

@Getter
public class NarooteoSummaryDto extends SelfValidating<NarooteoSummaryDto> {

    @JsonProperty("id")
    @NotNull
    private final Long id;

    @JsonProperty("host_nickname")
    @NotNull
    private final String hostNickname;

    @JsonProperty("title")
    @NotNull
    private final String title;

    @JsonProperty("participation_code")
    @NotNull
    private final String participationCode;

    @JsonProperty("is_host")
    @NotNull
    private final Boolean isHost;

    @Builder
    public NarooteoSummaryDto(
            Long id,
            String hostNickname,
            String title,
            String participationCode,
            Boolean isHost
    ) {
        this.id = id;
        this.hostNickname = hostNickname;
        this.title = title;
        this.participationCode = participationCode;
        this.isHost = isHost;
    }

    public static NarooteoSummaryDto fromEntity(Narooteo narooteo, User hostUser, User requestUser) {
        return NarooteoSummaryDto.builder()
                .id(narooteo.getId())
                .hostNickname(hostUser.getNickname())
                .title(narooteo.getTitle())
                .participationCode(narooteo.getParticipationCode())
                .isHost(hostUser.getId().equals(requestUser.getId()))
                .build();
    }
}

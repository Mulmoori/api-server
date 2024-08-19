package org.dongguk.vsa.mulmoori.narooteo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;

@Getter
public class NarooteoBriefDto extends SelfValidating<NarooteoBriefDto> {
    @JsonProperty("id")
    @NotNull
    private final Long id;

    @JsonProperty("title")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,20}$", message = "제목은 1자 이상 20자 이하의 한글, 영문, 숫자로 입력해주세요.")
    private final String title;

    @JsonProperty("host_nickname")
    @NotNull
    private final String hostNickname;

    @Builder
    public NarooteoBriefDto(
            Long id,
            String title,
            String hostNickname
    ) {
        this.id = id;
        this.title = title;
        this.hostNickname = hostNickname;

        this.validateSelf();
    }

    public static NarooteoBriefDto fromEntity(Narooteo entity, User host) {
        return NarooteoBriefDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .hostNickname(host.getNickname())
                .build();
    }
}

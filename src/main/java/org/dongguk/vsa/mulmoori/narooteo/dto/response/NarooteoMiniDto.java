package org.dongguk.vsa.mulmoori.narooteo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;

@Getter
public class NarooteoMiniDto extends SelfValidating<NarooteoMiniDto> {

    @JsonProperty("id")
    private final Long id;

    @Builder
    public NarooteoMiniDto(Long id) {
        this.id = id;
    }

    public static NarooteoMiniDto fromEntity(Narooteo entity) {
        return NarooteoMiniDto.builder()
                .id(entity.getId())
                .build();
    }
}

package org.dongguk.vsa.mulmoori.narooteo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;

import java.util.List;
import java.util.Map;

@Getter
public class NarooteoOverviewListDto extends SelfValidating<NarooteoOverviewListDto> {

    @JsonProperty("overviews")
    private final List<NarooteoOverviewDto> overviews;

    @Builder
    public NarooteoOverviewListDto(List<NarooteoOverviewDto> overviews) {
        this.overviews = overviews;

        this.validateSelf();
    }

    public static NarooteoOverviewListDto fromEntities(
            List<Narooteo> narooteos,
            Map<Long, Boolean> isConnectionHostMap,
            Map<Long, Integer> participantCountMap
    ) {
        List<NarooteoOverviewDto> overviews = narooteos.stream()
                .map(narooteo -> NarooteoOverviewDto.fromEntity(
                        narooteo,
                        isConnectionHostMap.getOrDefault(narooteo.getId(), false),
                        participantCountMap.getOrDefault(narooteo.getId(), 0)
                ))
                .toList();

        return NarooteoOverviewListDto.builder()
                .overviews(overviews)
                .build();
    }


    public static class NarooteoOverviewDto extends SelfValidating<NarooteoOverviewDto> {

        @JsonProperty("id")
        @NotNull
        private final Long id;

        @JsonProperty("title")
        @NotNull
        private final String title;

        @JsonProperty("is_connection_host")
        @NotNull
        private final Boolean isConnectionHost;

        @JsonProperty("participant_count")
        @NotNull
        private final Integer participantCount;

        @Builder
        public NarooteoOverviewDto(
                Long id,
                String title,
                Boolean isConnectionHost,
                Integer participantCount
        ) {
            this.id = id;
            this.title = title;
            this.isConnectionHost = isConnectionHost;
            this.participantCount = participantCount;
        }

        public static NarooteoOverviewDto fromEntity(
                Narooteo narooteo,
                Boolean isConnectionHost,
                Integer participantCount
        ) {
            return NarooteoOverviewDto.builder()
                    .id(narooteo.getId())
                    .title(narooteo.getTitle())
                    .isConnectionHost(isConnectionHost)
                    .participantCount(participantCount)
                    .build();
        }
    }
}

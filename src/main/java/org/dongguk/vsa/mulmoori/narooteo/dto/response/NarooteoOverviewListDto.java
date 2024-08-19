package org.dongguk.vsa.mulmoori.narooteo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.vsa.mulmoori.core.annotation.validation.DateValue;
import org.dongguk.vsa.mulmoori.core.dto.SelfValidating;
import org.dongguk.vsa.mulmoori.core.utility.DateTimeUtil;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;

import java.time.format.DateTimeFormatter;
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


    @Getter
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

        @JsonProperty("participated_at")
        @DateValue
        private final String participatedAt;

        @Builder
        public NarooteoOverviewDto(
                Long id,
                String title,
                Boolean isConnectionHost,
                Integer participantCount,
                String participatedAt
        ) {
            this.id = id;
            this.title = title;
            this.isConnectionHost = isConnectionHost;
            this.participantCount = participantCount;
            this.participatedAt = participatedAt;
        }

        public static NarooteoOverviewDto fromEntity(
                Narooteo narooteo,
                Boolean isConnectionHost,
                Integer participantCount
        ) {
            String dateFormatted = "yyyy-MM-dd";

            String participatedAt = DateTimeFormatter.ofPattern(dateFormatted).format(narooteo.getCreatedAt());

            return NarooteoOverviewDto.builder()
                    .id(narooteo.getId())
                    .title(narooteo.getTitle())
                    .isConnectionHost(isConnectionHost)
                    .participantCount(participantCount)
                    .participatedAt(participatedAt)
                    .build();
        }
    }
}

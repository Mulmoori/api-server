package org.dongguk.vsa.mulmoori.narooteo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public record CreateNarooteoRequestDto(
        @JsonProperty("title")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,20}$", message = "제목은 1자 이상 20자 이하의 한글, 영문, 숫자로 입력해주세요.")
        String title
) {
}

package org.dongguk.vsa.mulmoori.core.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AnalysisUtil implements InitializingBean {

    @Value("${analysis-server.url}")
    private String analysisServerUrl;

    private static RestClient REST_CLIENT;

    private final ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() {
        REST_CLIENT = RestClient.create();
    }

    public String preProcessSttText(
            String question,
            String sttText
    ) throws JsonProcessingException {
        Map<String, String> requestMap = Map.of(
                "user_question", question,
                "stt_text", sttText
        );

        String requestJson = objectMapper.writeValueAsString(requestMap);

        Map<String, Object> responseMap = REST_CLIENT.post().uri(analysisServerUrl + "/process_stt")
                .headers(headers -> headers.set("Content-Type", "application/json; charset=utf-8"))
                .body(requestJson)
                .retrieve()
                .toEntity(Map.class)
                .getBody();

        return Objects.requireNonNull(responseMap).get("answer").toString();
    }

    public String createAnswer(
            Long dialogueId,
            String question
    ) throws JsonProcessingException {
        Map<String, Object> requestMap = Map.of(
                "id", dialogueId,
                "question", question
        );

        String requestJson = objectMapper.writeValueAsString(requestMap);

        Map<String, Object> responseMap = REST_CLIENT.post().uri(analysisServerUrl + "/query")
                .headers(headers -> headers.set("Content-Type", "application/json; charset=utf-8"))
                .body(requestJson)
                .retrieve()
                .toEntity(Map.class)
                .getBody();

        return Objects.requireNonNull(responseMap).get("answer").toString();
    }

    public void updateVectorStore(
            Long dialogueId,
            String question,
            String answer
    ) throws JsonProcessingException {
        Map<String, Object> requestMap = Map.of(
                "id", dialogueId,
                "question", question,
                "answer", answer
        );

        String requestJson = objectMapper.writeValueAsString(requestMap);

        REST_CLIENT.post().uri(analysisServerUrl + "/update_vectorstore")
                .headers(headers -> headers.set("Content-Type", "application/json; charset=utf-8"))
                .body(requestJson)
                .retrieve()
                .toBodilessEntity();
    }
}

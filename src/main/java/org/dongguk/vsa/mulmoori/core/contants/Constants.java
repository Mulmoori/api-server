package org.dongguk.vsa.mulmoori.core.contants;

import java.util.List;

public class Constants {

    // JWT
    public static String ACCOUNT_ID_ATTRIBUTE_NAME = "ACCOUNT_ID";
    public static String ACCOUNT_ID_CLAIM_NAME = "aid";
    public static String ACCOUNT_ROLE_CLAIM_NAME = "rol";

    // HEADER
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";

    // COOKIE
    public static String ACCESS_TOKEN = "access_token";
    public static String REFRESH_TOKEN = "refresh_token";

    // RABBIT MQ
    public static final String MULMOORI_EXCHANGE_NAME = "mulmoori.exchange";

    public static final String MULMOORI_DIALOGUE_QUEUE_NAME = "dialogue.queue";

    public static final String NAROOTEO_DIALOGUE_ROUTING_KEY = "narooteos.*.dialogues";
    public static final String NAROOTEO_USER_DIALOGUE_ROUTING_KEY = "narooteos.*.users.*.dialogues";

    // 형용사 List
    public static List<String> ADJECTIVES = List.of(
            "행복한",
            "슬픈",
            "화난",
            "무서운",
            "즐거운",
            "힘든",
            "편안한",
            "불안한",
            "놀라운",
            "지루한",
            "신나는",
            "짜증나는",
            "피곤한"
    );

    // 동물 명사 List
    public static List<String> ANIMALS = List.of(
            "사자",
            "호랑이",
            "곰",
            "늑대",
            "여우",
            "너구리",
            "쥐",
            "토끼",
            "고양이",
            "강아지",
            "말",
            "돼지",
            "소",
            "양",
            "닭",
            "오리",
            "참새",
            "독수리",
            "까마귀",
            "비둘기",
            "참치",
            "고래",
            "상어",
            "문어",
            "오징어",
            "랍스터",
            "게",
            "새우",
            "바다표범",
            "코끼리",
            "코뿔소",
            "기린",
            "낙타",
            "캥거루",
            "코알라",
            "판다",
            "고릴라",
            "오랑우탄",
            "침팬지",
            "표범",
            "치타",
            "악어"
    );

    /**
     * 인증이 필요 없는 URL
     */
    public static List<String> NO_NEED_AUTH_URLS = List.of(
            // Health Check
            "/ws-stomp",

            // Stomp
            "/pub",
            "/exchange",

            // Authentication/Authorization
            "/api/auth/validations/email",
            "/api/auth/validations/authentication-code",
            "/api/auth/reissue/token",
            "/api/auth/reissue/password",
            "/api/auth/sign-up",
            "/api/auth/login",

            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**"
    );

    /**
     * Swagger 에서 사용하는 URL
     */
    public static List<String> SWAGGER_URLS = List.of(
            "/api-docs.html",
            "/api-docs",
            "/swagger-ui",
            "/v3"
    );

    /**
     * 사용자 URL
     */
    public static List<String> USER_URLS = List.of(
            "/api/**"
    );
}


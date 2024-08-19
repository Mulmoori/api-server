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


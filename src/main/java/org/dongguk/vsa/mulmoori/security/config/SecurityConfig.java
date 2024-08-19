package org.dongguk.vsa.mulmoori.security.config;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.contants.Constants;
import org.dongguk.vsa.mulmoori.core.utility.JsonWebTokenUtil;
import org.dongguk.vsa.mulmoori.security.filter.ExceptionFilter;
import org.dongguk.vsa.mulmoori.security.filter.GlobalLoggerFilter;
import org.dongguk.vsa.mulmoori.security.filter.JsonWebTokenAuthenticationFilter;
import org.dongguk.vsa.mulmoori.security.handler.common.DefaultAccessDeniedHandler;
import org.dongguk.vsa.mulmoori.security.handler.common.DefaultAuthenticationEntryPoint;
import org.dongguk.vsa.mulmoori.security.handler.login.DefaultLoginFailureHandler;
import org.dongguk.vsa.mulmoori.security.handler.login.DefaultLoginSuccessHandler;
import org.dongguk.vsa.mulmoori.security.handler.logout.DefaultLogoutProcessHandler;
import org.dongguk.vsa.mulmoori.security.handler.logout.DefaultLogoutSuccessHandler;
import org.dongguk.vsa.mulmoori.security.usecase.AuthenticateJsonWebTokenUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultLoginSuccessHandler defaultLoginSuccessHandler;
    private final DefaultLoginFailureHandler defaultLoginFailureHandler;

    private final DefaultLogoutProcessHandler defaultLogoutProcessHandler;
    private final DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                        .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasRole("USER")
                        .anyRequest().authenticated()
                )

                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("serial_id")
                        .passwordParameter("password")
                        .successHandler(defaultLoginSuccessHandler)
                        .failureHandler(defaultLoginFailureHandler)
                )

                .logout(configurer -> configurer
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(defaultLogoutProcessHandler)
                        .logoutSuccessHandler(defaultLogoutSuccessHandler)
                )

                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                )

                .addFilterBefore(
                        new JsonWebTokenAuthenticationFilter(
                                authenticateJsonWebTokenUseCase,
                                jsonWebTokenUtil
                        ),
                        LogoutFilter.class
                )

                .addFilterBefore(
                        new ExceptionFilter(),
                        JsonWebTokenAuthenticationFilter.class
                )

                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        ExceptionFilter.class
                )

                .getOrBuild();
    }
}
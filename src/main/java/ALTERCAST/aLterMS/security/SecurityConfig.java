package ALTERCAST.aLterMS.security;

import ALTERCAST.aLterMS.repository.UserRepository;
import ALTERCAST.aLterMS.repository.UserSectionRepository;
import ALTERCAST.aLterMS.service.UserService;
import ALTERCAST.aLterMS.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Spring Security 설정 클래스
 */
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserSectionRepository userSectionRepository;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**CORS 설정*/
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 허용할 도메인 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드 설정
        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 헤더 설정
        configuration.setAllowCredentials(true); // 자격 증명 허용 여부 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 필터 체인에 대한 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), jwtUtil);
        loginFilter.setFilterProcessesUrl("/users/login");
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userRepository, userService, userSectionRepository);

        // 접근 권한 설정
//        http.authorizeHttpRequests((authorizedHttpRequests) -> authorizedHttpRequests
//                .anyRequest().permitAll()
//        );
        http
                .cors(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/users/join","/users/login","users/set/**","/auth/**", "/oauth2/**")
                        .permitAll()
                        .requestMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js")
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        // 필터 등록
        http
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 자리에 LoginFilter 등록
                .addFilterBefore(jwtAuthenticationFilter, LoginFilter.class) // LoginFilter 앞에 JwtAuthenticationFilter 등록
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세선 정책 설정 (rest api에 필요하다고 함..)
        ;
        // 기타 설정
        http.formLogin((auth) -> auth.disable()) // form 로그인 방식 해제

                .httpBasic((auth) -> auth.disable()) // http basic 해제
                .csrf((auth) -> auth.disable()) // csrf 해제
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource())) // cors 해제
                .headers((headers) -> headers // 프레임 관련 (h2)
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                        ))
                )

        ;
        return http.build();
    }
}

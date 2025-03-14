package backend.projects.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Spring 설정 클래스임을 선언
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer 인터페이스 구현

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 설정 적용 (/** : 모든 경로 패턴)
                .allowedOrigins("http://localhost:5173") // 허용할 Origin (프론트엔드 개발 서버 주소)
                .allowedMethods("*") // 허용할 HTTP 메소드 (* : 모든 메소드 허용 - GET, POST, PUT, DELETE 등)
                .allowedHeaders("*") // 허용할 Header (* : 모든 Header 허용)
                .allowCredentials(true); // 인증 정보 (쿠키, Authorization Header) 허용 여부 (true : 허용)
    } // addCorsMappings end
} // class end

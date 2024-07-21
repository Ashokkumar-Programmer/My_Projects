package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import backend.utils.StringUtils;

@Configuration
public class ThymeleafConfig {
    @Bean
    public StringUtils stringUtils() {
        return new StringUtils();
    }
}

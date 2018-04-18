package pl.altkom.asc.wl.claim.app.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

/**
 * @author tdorosz
 */
@Configuration
public class WebConfiguration {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        objectMapper.registerModule(new JavaTimeModule());
        jsonConverter.setObjectMapper(objectMapper);

        return jsonConverter;
    }
}

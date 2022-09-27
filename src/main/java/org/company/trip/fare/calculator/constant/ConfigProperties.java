package org.company.trip.fare.calculator.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "trip.fare.file")
@Configuration
@Setter
@Getter
public class ConfigProperties {
    private String output;
}

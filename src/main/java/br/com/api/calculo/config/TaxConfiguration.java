package br.com.api.calculo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "tax")
@Data
public class TaxConfiguration {
    private Map<String, BigDecimal[]> rates;
}
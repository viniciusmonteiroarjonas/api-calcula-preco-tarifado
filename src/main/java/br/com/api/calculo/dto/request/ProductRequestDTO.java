package br.com.api.calculo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    @JsonProperty("nome")
    private String name;

    @JsonProperty("categoria")
    private String category;

    @JsonProperty("preco_base")
    private BigDecimal basePrice;
}
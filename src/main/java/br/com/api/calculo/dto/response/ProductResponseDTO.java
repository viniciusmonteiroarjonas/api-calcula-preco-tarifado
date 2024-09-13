package br.com.api.calculo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;

    private String name;

    private String category;

    private BigDecimal basePrice;

    private BigDecimal tariffedPrice;
}

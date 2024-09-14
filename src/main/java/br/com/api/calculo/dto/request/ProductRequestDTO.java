package br.com.api.calculo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "{product.name.notblank}")
    private String name;

    @JsonProperty("categoria")
    @NotBlank(message = "{product.category.notblank}")
    private String category;

    @JsonProperty("preco_base")
    @NotNull(message = "{product.baseprice.notnull}")
    private BigDecimal basePrice;
}
package br.com.api.calculo.mapper;

import br.com.api.calculo.dto.response.ProductResponseDTO;
import br.com.api.calculo.model.ProductDomain;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDTO(ProductDomain product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .basePrice(product.getBasePrice())
                .tariffedPrice(product.getTariffedPrice())
                .build();
    }
}
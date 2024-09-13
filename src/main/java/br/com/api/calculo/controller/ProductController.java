package br.com.api.calculo.controller;

import br.com.api.calculo.dto.request.ProductRequestDTO;
import br.com.api.calculo.dto.response.ProductResponseDTO;
import br.com.api.calculo.model.ProductDomain;
import br.com.api.calculo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/v1")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/preco/tarifado")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductDomain product = productService.calculateAndSaveProduct(productRequestDTO);

        ProductResponseDTO responseDTO = convertToResponseDTO(product);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    private ProductResponseDTO convertToResponseDTO(ProductDomain product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .basePrice(product.getBasePrice())
                .tariffedPrice(product.getTariffedPrice())
                .build();
    }
}

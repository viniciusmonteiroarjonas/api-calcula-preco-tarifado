package br.com.api.calculo.controller;

import br.com.api.calculo.dto.request.ProductRequestDTO;
import br.com.api.calculo.dto.response.ProductResponseDTO;
import br.com.api.calculo.mapper.ProductMapper;
import br.com.api.calculo.model.ProductDomain;
import br.com.api.calculo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/v1")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/preco/tarifado")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Validated ProductRequestDTO productRequestDTO) {
        ProductDomain product = productService.calculateAndSaveProduct(productRequestDTO);
        ProductResponseDTO responseDTO = productMapper.toResponseDTO(product);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}

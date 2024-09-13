package br.com.api.calculo.service;

import br.com.api.calculo.config.TaxConfiguration;
import br.com.api.calculo.dto.request.ProductRequestDTO;
import br.com.api.calculo.model.ProductDomain;
import br.com.api.calculo.repository.ProductRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TaxConfiguration taxConfiguration;

    public ProductDomain calculateAndSaveProduct(ProductRequestDTO dto) {
        String category = dto.getCategory().toUpperCase();
        BigDecimal[] taxes = taxConfiguration.getRates().get(category);

        log.info("Categoria Informada: {}", category);
        log.info("Tax rates: {}", new Gson().toJson(taxes));

        if (taxes == null) {
            throw new IllegalArgumentException("Categoria inválida: " + category);
        }

        BigDecimal tariffedPrice = dto.getBasePrice()
                .add(dto.getBasePrice().multiply(taxes[0]))
                .add(dto.getBasePrice().multiply(taxes[1]))
                .add(dto.getBasePrice().multiply(taxes[2]));

        ProductDomain product = ProductDomain.builder()
                .name(dto.getName())
                .category(category)
                .basePrice(dto.getBasePrice())
                .tariffedPrice(tariffedPrice)
                .build();

        return productRepository.save(product);
    }
}
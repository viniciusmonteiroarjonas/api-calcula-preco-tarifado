package br.com.api.calculo.service;

import br.com.api.calculo.config.TaxConfiguration;
import br.com.api.calculo.dto.request.ProductRequestDTO;
import br.com.api.calculo.model.ProductDomain;
import br.com.api.calculo.repository.ProductRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TaxConfiguration taxConfiguration;

    public ProductDomain calculateAndSaveProduct(ProductRequestDTO dto) {
        log.info("API CALCULO - Iniciando cálculo do produto.");
        try {
            String category = dto.getCategory().toUpperCase();
            BigDecimal[] taxes = taxConfiguration.getRates().get(category);

            log.info("API CALCULO - Categoria Informada: {}", category);
            log.info("API CALCULO - Taxas encontradas para a categoria '{}': {}", category, new Gson().toJson(taxes));

            if (taxes == null) {
                String errorMessage = String.format("API CALCULO - Categoria inválida fornecida: %s", category);
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            if (taxes.length < 3) {
                String errorMessage = String.format("API CALCULO - Número insuficiente de taxas para a categoria '%s'. Taxas encontradas: %d", category, taxes.length);
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            BigDecimal basePrice = dto.getBasePrice().setScale(2, RoundingMode.HALF_UP);
            BigDecimal tariffedPrice = basePrice
                    .add(basePrice.multiply(taxes[0]))
                    .add(basePrice.multiply(taxes[1]))
                    .add(basePrice.multiply(taxes[2]))
                    .setScale(2, RoundingMode.HALF_UP);


            log.info("API CALCULO - Preço base do produto: {}", basePrice);
            log.info("API CALCULO - Preço tarifado calculado: {}", tariffedPrice);


            ProductDomain product = ProductDomain.builder()
                    .name(dto.getName())
                    .category(category)
                    .basePrice(dto.getBasePrice())
                    .tariffedPrice(tariffedPrice)
                    .build();

            ProductDomain savedProduct = productRepository.save(product);
            log.info("API CALCULO - Produto salvo com sucesso. ID do produto salvo: {}", savedProduct.getId());
            return savedProduct;

        } catch (IllegalArgumentException e) {
            log.error("API CALCULO - Erro ao calcular e salvar o produto: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("API CALCULO - Erro inesperado ao processar o produto: {}", e.getMessage(), e);
            throw new RuntimeException("Erro inesperado ao processar o produto", e);
        }
    }
}

package br.com.api.calculo.service;

import br.com.api.calculo.config.TaxConfiguration;
import br.com.api.calculo.dto.request.ProductRequestDTO;
import br.com.api.calculo.model.ProductDomain;
import br.com.api.calculo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TaxConfiguration taxConfiguration;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateAndSaveProductSuccess() {
        // Arrange
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Product");
        requestDTO.setCategory("ELECTRONICS");
        requestDTO.setBasePrice(new BigDecimal("100"));

        BigDecimal[] taxes = { new BigDecimal("0.10"), new BigDecimal("0.05"), new BigDecimal("0.02") };

        Map<String, BigDecimal[]> taxRates = new HashMap<>();
        taxRates.put("ELECTRONICS", taxes);

        when(taxConfiguration.getRates()).thenReturn(taxRates);

        ProductDomain expectedProductDomain = ProductDomain.builder()
                .id(1L)
                .name("Product")
                .category("ELECTRONICS")
                .basePrice(new BigDecimal("100"))
                .tariffedPrice(new BigDecimal("117")) // 100 + (100*0.10) + (100*0.05) + (100*0.02)
                .build();

        when(productRepository.save(any(ProductDomain.class))).thenReturn(expectedProductDomain);

        ProductDomain result = productService.calculateAndSaveProduct(requestDTO);

        assertEquals("Product", result.getName(), "Name should be 'Product'");
        assertEquals("ELECTRONICS", result.getCategory(), "Category should be 'ELECTRONICS'");
        assertEquals(new BigDecimal("100"), result.getBasePrice(), "Base price should be 100");
        assertEquals(new BigDecimal("117"), result.getTariffedPrice(), "Tariffed price should be 117");
    }

    @Test
    public void testCalculateAndSaveProductWithInvalidCategory() {
        // Arrange
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Product");
        requestDTO.setCategory("INVALID");
        requestDTO.setBasePrice(new BigDecimal("100"));

        when(taxConfiguration.getRates()).thenReturn(new HashMap<>());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.calculateAndSaveProduct(requestDTO);
        });
    }

    @Test
    public void testCalculateAndSaveProductWithNullBasePrice() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Product");
        requestDTO.setCategory("ELECTRONICS");
        requestDTO.setBasePrice(null);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.calculateAndSaveProduct(requestDTO);
        });
    }

}

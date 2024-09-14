package br.com.api.calculo.repository;

import br.com.api.calculo.model.ProductDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveAndFindById() {
        ProductDomain product = ProductDomain.builder()
                .name("Product")
                .category("ELECTRONICS")
                .basePrice(new BigDecimal("100"))
                .tariffedPrice(new BigDecimal("117"))
                .build();

        ProductDomain savedProduct = productRepository.save(product);

        Optional<ProductDomain> foundProduct = productRepository.findById(savedProduct.getId());

        assertTrue(foundProduct.isPresent(), "Product should be found");
        assertEquals("Product", foundProduct.get().getName(), "Name should be 'Product'");
        assertEquals("ELECTRONICS", foundProduct.get().getCategory(), "Category should be 'ELECTRONICS'");
        assertEquals(new BigDecimal("100"), foundProduct.get().getBasePrice(), "Base price should be 100");
        assertEquals(new BigDecimal("117"), foundProduct.get().getTariffedPrice(), "Tariffed price should be 117");
    }

    @Test
    public void testDeleteById() {
        ProductDomain product = ProductDomain.builder()
                .name("Product")
                .category("ELECTRONICS")
                .basePrice(new BigDecimal("100"))
                .tariffedPrice(new BigDecimal("117"))
                .build();
        ProductDomain savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());

        Optional<ProductDomain> deletedProduct = productRepository.findById(savedProduct.getId());
        assertTrue(deletedProduct.isEmpty(), "Product should be deleted");
    }
}

package br.com.api.calculo.controller;

import br.com.api.calculo.dto.request.ProductRequestDTO;
import br.com.api.calculo.dto.response.ProductResponseDTO;
import br.com.api.calculo.mapper.ProductMapper;
import br.com.api.calculo.model.ProductDomain;
import br.com.api.calculo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Test
    public void testCreateProduct() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Product");
        requestDTO.setCategory("ELECTRONICS");
        requestDTO.setBasePrice(new BigDecimal("100"));

        ProductDomain productDomain = ProductDomain.builder()
                .id(1L)
                .name("Product")
                .category("ELECTRONICS")
                .basePrice(new BigDecimal("100"))
                .tariffedPrice(new BigDecimal("120"))
                .build();

        when(productService.calculateAndSaveProduct(any(ProductRequestDTO.class)))
                .thenReturn(productDomain);

        when(productMapper.toResponseDTO(any(ProductDomain.class)))
                .thenReturn(ProductResponseDTO.builder()
                        .id(1L)
                        .name("Product")
                        .category("ELECTRONICS")
                        .basePrice(new BigDecimal("100"))
                        .tariffedPrice(new BigDecimal("120"))
                        .build());

        mockMvc.perform(post("/products/v1/preco/tarifado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Product")))
                .andExpect(jsonPath("$.category", is("ELECTRONICS")))
                .andExpect(jsonPath("$.basePrice", is(100)))
                .andExpect(jsonPath("$.tariffedPrice").exists());
    }
}

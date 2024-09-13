package br.com.api.calculo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "categoria")
    private String category;

    @Column(name = "preco_base")
    private BigDecimal basePrice;

    @Column(name = "preco_tarifado")
    private BigDecimal tariffedPrice;
}


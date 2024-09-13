package br.com.api.calculo.repository;

import br.com.api.calculo.model.ProductDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDomain, Long> {
}

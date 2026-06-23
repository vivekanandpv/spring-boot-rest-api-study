package dev.vivekanand.massmutualspringday2.repositories;

import dev.vivekanand.massmutualspringday2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

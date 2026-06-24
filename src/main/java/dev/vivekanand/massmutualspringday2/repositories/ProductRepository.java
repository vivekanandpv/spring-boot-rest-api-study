package dev.vivekanand.massmutualspringday2.repositories;

import dev.vivekanand.massmutualspringday2.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //  derived query or query method
    List<Product> findProductByNameStartingWith(String name);

    //  JPQL query
    @Query("select p from Product p where p.name like :prefix%")
    List<Product> foo(@Param("prefix") String prefix);

    //  Native (=SQL) query
    @Query(value = "SELECT * FROM PRODUCT WHERE NAME LIKE CONCAT(:prefix, '%')", nativeQuery = true)
    List<Product> bar(@Param("prefix") String prefix);
}

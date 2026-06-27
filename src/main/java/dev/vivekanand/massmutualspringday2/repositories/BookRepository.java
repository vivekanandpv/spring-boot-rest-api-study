package dev.vivekanand.massmutualspringday2.repositories;

import dev.vivekanand.massmutualspringday2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

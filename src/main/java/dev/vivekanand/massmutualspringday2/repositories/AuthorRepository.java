package dev.vivekanand.massmutualspringday2.repositories;

import dev.vivekanand.massmutualspringday2.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}

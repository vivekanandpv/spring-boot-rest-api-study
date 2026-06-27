package dev.vivekanand.massmutualspringday2.repositories;

import dev.vivekanand.massmutualspringday2.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}

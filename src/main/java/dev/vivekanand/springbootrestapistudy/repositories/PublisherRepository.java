package dev.vivekanand.springbootrestapistudy.repositories;

import dev.vivekanand.springbootrestapistudy.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}

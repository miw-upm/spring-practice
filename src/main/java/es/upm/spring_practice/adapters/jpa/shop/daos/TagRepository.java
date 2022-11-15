package es.upm.spring_practice.adapters.jpa.shop.daos;

import es.upm.spring_practice.adapters.jpa.shop.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, String> {
    Optional<TagEntity> findByName(String name);

    int deleteByName(String name);
}

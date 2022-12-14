package es.upm.spring_practice.domain.ports.shop;

import es.upm.spring_practice.domain.models.shop.Tag;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TagPersistence {
    Tag readByName(String name);

    void delete(String name);

    Stream<Tag> readAll();
}

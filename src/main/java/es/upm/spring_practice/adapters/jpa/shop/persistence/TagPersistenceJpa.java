package es.upm.spring_practice.adapters.jpa.shop.persistence;

import es.upm.spring_practice.adapters.jpa.shop.daos.TagRepository;
import es.upm.spring_practice.adapters.jpa.shop.entities.TagEntity;
import es.upm.spring_practice.domain.exceptions.NotFoundException;
import es.upm.spring_practice.domain.models.shop.Tag;
import es.upm.spring_practice.domain.ports.shop.TagPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository("tagPersistence")
public class TagPersistenceJpa implements TagPersistence {

    private final TagRepository tagRepository;

    @Autowired
    public TagPersistenceJpa(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Stream<Tag> readAll() {
        return this.tagRepository.findAll().stream()
                .map(TagEntity::toTag);
    }

    @Override
    public Tag readByName(String name) {
        return this.tagRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(" Tag name: " + name))
                .toTag();
    }

    @Override
    public void delete(String name) {
        this.tagRepository.deleteByName(name);
    }
}

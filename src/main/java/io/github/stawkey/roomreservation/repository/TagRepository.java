package io.github.stawkey.roomreservation.repository;

import io.github.stawkey.roomreservation.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}

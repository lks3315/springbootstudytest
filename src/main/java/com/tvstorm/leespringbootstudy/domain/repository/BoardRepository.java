package com.tvstorm.leespringbootstudy.domain.repository;

import com.tvstorm.leespringbootstudy.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}

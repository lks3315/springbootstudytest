package com.tvstorm.leespringbootstudy.domain.repository;

import com.tvstorm.leespringbootstudy.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // JPA가 MemberEntity 중 user의 id 정보를 WHERE 조건절로 지정하도록 findById() 정의
    Optional<MemberEntity> findByEmail(String userEmail);
}

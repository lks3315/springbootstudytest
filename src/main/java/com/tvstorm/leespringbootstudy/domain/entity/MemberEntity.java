package com.tvstorm.leespringbootstudy.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Builder
    public MemberEntity(Long userNo, String email, String password) {
        this.userNo = userNo;
        this.email = email;
        this.password = password;
    }
}

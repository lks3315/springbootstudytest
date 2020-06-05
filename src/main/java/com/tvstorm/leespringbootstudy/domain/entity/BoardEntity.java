package com.tvstorm.leespringbootstudy.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table
public class BoardEntity extends TimeEntity{

    @Id
    @GeneratedValue// 기본키로 대체키를 사용할 때 기본키 생성 전략
    private Long id;

    @Column(length = 30, nullable = false)
    private String writer;

    @Column(length = 300, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public BoardEntity(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}

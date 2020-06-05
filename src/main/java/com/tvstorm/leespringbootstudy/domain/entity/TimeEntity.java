package com.tvstorm.leespringbootstudy.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // table로 매핑하지 않고 자식 클래스(여기선 BoardEntity)에게 매핑 정보를 상속하기 위한 애노테이션
@EntityListeners(AuditingEntityListener.class) // JPA에게 해당 Entity는 Auditing 기능을 사용한다는 것을 알리는 애노테이션
public abstract class TimeEntity {

    @CreatedDate // Entity가 처음 저장될 때 생성일을 주입하는 애노테이션
    @Column(updatable = false) // 생성일은 업뎃할 필요가 없으므로 false
    private LocalDateTime createDate;

    @LastModifiedDate // Entity 수정 일자를 주입하는 @
    private LocalDateTime modifiedDate;
}

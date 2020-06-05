package com.tvstorm.leespringbootstudy.dto;

import com.tvstorm.leespringbootstudy.domain.entity.BoardEntity;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;

    @NotEmpty(message = "작성자는 필수 입력 값")
    private String writer;

    @NotEmpty(message = "제목은 필수 입력 값")
    private String title;

    @NotEmpty(message = "내용은 필수 입력 값")
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // DTO에서 필요한 부분을 builder 패턴을 통해 to entity , lombok의 @Builder끼리 가능
    public BoardEntity toEntity() {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}

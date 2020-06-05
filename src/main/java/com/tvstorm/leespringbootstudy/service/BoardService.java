package com.tvstorm.leespringbootstudy.service;

import com.tvstorm.leespringbootstudy.domain.entity.BoardEntity;
import com.tvstorm.leespringbootstudy.domain.repository.BoardRepository;
import com.tvstorm.leespringbootstudy.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.*;

@AllArgsConstructor
@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Transactional // 트랜잭션을 적용하는 애노테이션
    public Long savePost(BoardDto boardDto) {
        //save메소드는 JpaRepository에 정의된 메소드로 DB에 INSERT, UPDATE를 담당, 매개변수로 Entity 전달
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardDto> getBoardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        //boardRepository에서 BoardEntity를 가져온 후에 DTO로 변환하는 과정
        for (BoardEntity boardEntity : boardEntities) {
            BoardDto boardDTO = BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .createdDate(boardEntity.getCreateDate())
                    .build();
            boardDtoList.add(boardDTO);
        }
        return boardDtoList;
    }

    @Transactional
    public BoardDto getPost(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        BoardEntity boardEntity = optionalBoardEntity.get(); // Optional 타입의 boardEntity의 Entity만을 가져오기 위해 선언

        BoardDto boardDTO = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .createdDate(boardEntity.getCreateDate())
                .build();

        return boardDTO;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorBoardResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("vaild_%s", error.getField());
            validatorBoardResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorBoardResult;
    }
}

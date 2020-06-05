package com.tvstorm.leespringbootstudy.controller;

import com.tvstorm.leespringbootstudy.dto.BoardDto;
import com.tvstorm.leespringbootstudy.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor // 생성자를 만들 때 자동으로 bean 주입도 해줌
public class BoardController {
    private BoardService boardService;

//    @GetMapping("/")
//    public String boardList() {
//        return "board/list";
//    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);
        model.addAttribute("boardDto", boardDTO);
        return "board/detail";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);
        model.addAttribute("boardDto", boardDTO);
        return "board/update";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);
        return "redirect:/board";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);
        return "redirect:/board";
    }

    @GetMapping("/post/write")
    public String boardWrite() {
        return "board/write";
    }

    @PostMapping("/post")
    public String boardWrite(@Valid BoardDto boardDto, Errors errors, Model model) { // dto는 Controller와 Service 사이에서 데이터를 주고받는 객체로 구현
        log.info("boardDto {}" , boardDto);
        log.info("errors {}" , errors);
        log.info("model {}" , model);

        if (errors.hasErrors()) {
            model.addAttribute("boardDto", boardDto);
            Map<String, String> validatorResult = boardService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "board/write";
        }

        boardService.savePost(boardDto);
        return "redirect:/board"; // redirect:는 뒤에 오는 url 요청을 다시 하는 개념, 글쓰기 완료 후 리스트 화면으로 감
    }

    // 게시글 목록
    @GetMapping("/board")
    public String list(Model model) {
        List<BoardDto> boardList = boardService.getBoardlist();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }
}

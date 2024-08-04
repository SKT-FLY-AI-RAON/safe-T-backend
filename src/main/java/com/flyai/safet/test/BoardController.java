package com.flyai.safet.test;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;

    //게시물 하나 조회
    @GetMapping("/{id}")
    public Board getBoardDetail(@PathVariable(value = "id") Long id){
        return boardRepository.findById(id).get();
    }
}

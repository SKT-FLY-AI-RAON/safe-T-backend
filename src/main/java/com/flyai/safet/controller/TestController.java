package com.flyai.safet.controller;


import com.flyai.safet.entity.ApiResponse;
import com.flyai.safet.exception.BadRequestException;
import com.flyai.safet.test.Board;
import com.flyai.safet.test.BoardRepository;
import com.flyai.safet.test.BoardReq;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final BoardRepository boardRepository;

    @GetMapping("")
    public ApiResponse<String> test(){
        return new ApiResponse<>("test");
    }


    @ApiOperation(value = "게시물 전체 조회", notes = "게시물 전체 조회")
    @GetMapping("/board")
    public ApiResponse<List<Board>> getBoards() {

        List<Board> boardList = boardRepository.findAll();

        if (boardList == null){
            throw new BadRequestException("게시글이 존재하지 않습니다.");
        }

        return new ApiResponse<>(boardList);
    }




    @ApiOperation(value = "게시물 1개 조회", notes = "게시물 1개 조회")
    @GetMapping("/board/{id}")
    public ApiResponse<Board> getBoardDetail(@PathVariable(value = "id") Long id) {

        Board board = boardRepository.findBoardById(id).orElse(null);

        if (board == null){
            throw new BadRequestException("존재하지 않는 게시글입니다.");
        }

        return new ApiResponse<>(board);
    }


    @ApiOperation(value = "게시물 생성", notes = "게시물 생성")
    @PostMapping("/board")
    public ApiResponse<Long> addBoard(@RequestBody BoardReq boardReq) {

        Board board = Board.builder()
                .title(boardReq.getTitle())
                .content(boardReq.getContent())
                .build();

        Long id = boardRepository.save(board).getId();

        return new ApiResponse<>(id);
    }




}

package com.flyai.safet.controller;


import com.flyai.safet.entity.ApiResponse;
import com.flyai.safet.exception.BadRequestException;
import com.flyai.safet.service.S3Service;
import com.flyai.safet.service.UdpService;
import com.flyai.safet.test.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final UdpService udpService;


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


    @ApiOperation(value = "test 업로드", notes = "test 업로드")
    @PostMapping("/test")
    public ApiResponse<Image> uploadImageTest(@RequestPart(name = "images") MultipartFile multipartFile){

        String url = s3Service.uploadImage("test", multipartFile.getOriginalFilename(), multipartFile);

        Image image = Image.builder()
                .url(url)
                .build();

        imageRepository.save(image);

        return new ApiResponse<>(image);
    }



    @ApiOperation(value = "이미지 업로드", notes = "이미지 업로드")
    @PostMapping("/image")
    public ApiResponse<Image> uploadImage(@RequestPart(name = "images") MultipartFile multipartFile){

        String url = s3Service.uploadImage("image", multipartFile.getOriginalFilename(), multipartFile);

        Image image = Image.builder()
                .url(url)
                .build();

        imageRepository.save(image);

        return new ApiResponse<>(image);
    }




    @ApiOperation(value = "파일 업로드", notes = "파일 업로드")
    @PostMapping("/file")
    public ApiResponse<Image> uploadFile(@RequestPart(name = "files") MultipartFile multipartFile){

        String url = s3Service.uploadImage("file", multipartFile.getOriginalFilename(), multipartFile);

        Image image = Image.builder()
                .url(url)
                .build();

        imageRepository.save(image);

        return new ApiResponse<>(image);
    }


    @ApiOperation(value = "비디오 업로드", notes = "비디오 업로드")
    @PostMapping("/video")
    public ApiResponse<Image> uploadVideo(@RequestPart(name = "videos") MultipartFile multipartFile){

        String url = s3Service.uploadImage("video", multipartFile.getOriginalFilename(), multipartFile);

        Image image = Image.builder()
                .url(url)
                .build();

        imageRepository.save(image);

        return new ApiResponse<>(image);
    }










    @PostMapping("/sendVideo")
    public String uploadVideo(@RequestPart(name = "file") MultipartFile file,
                              @RequestParam String serverAddress,
                              @RequestParam int port) {
        try {
            udpService.sendVideo(file, serverAddress, port);
            return "Video sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send video: " + e.getMessage();
        }
    }
//    public String sendVideo(@RequestParam String filePath, @RequestParam String serverAddress, @RequestParam int port) {
//        try {
//            udpService.sendVideo(filePath, serverAddress, port);
//            return "Video sent successfully";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to send video: " + e.getMessage();
//        }
//    }



}

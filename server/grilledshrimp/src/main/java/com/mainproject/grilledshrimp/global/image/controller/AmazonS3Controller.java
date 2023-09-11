//package com.mainproject.grilledshrimp.global.image.controller;
//
//import com.mainproject.grilledshrimp.global.image.service.AwsS3Service;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.MalformedURLException;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/s3")
//public class AmazonS3Controller {
//
//    private final AwsS3Service awsS3Service;
//
//    /**
//     * Amazon S3에 파일 업로드
//     * @return 성공 시 201 CREATED 와 함께 업로드 된 파일의 파일명 리스트 반환
//     */
//    @PostMapping("/file")
//    public ResponseEntity<List<String>> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
//        return new ResponseEntity<>(awsS3Service.uploadImage(multipartFile), HttpStatus.CREATED);
//    }
//
//    /**
//     * Amazon S3에 업로드 된 파일을 삭제
//     * @return 성공 시 204 NO_CONTENT 반환
//     */
//    @DeleteMapping("/file")
//    public ResponseEntity<Void> deleteFile(@RequestParam String fileName) {
//        awsS3Service.deleteImage(fileName);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
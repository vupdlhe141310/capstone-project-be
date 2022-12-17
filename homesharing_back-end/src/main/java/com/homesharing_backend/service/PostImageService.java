package com.homesharing_backend.service;

import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.MessageResponse;
import com.homesharing_backend.presentation.payload.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostImageService {

    public ResponseEntity<ResponseObject> getAllPostImageByPostID(Long postID);

    public ResponseEntity<MessageResponse> insertPostImage(List<MultipartFile> multipartFile, Long postID);

    public ResponseEntity<MessageResponse> insertPostImageOneByPostID(MultipartFile multipartFile, Long postID);

    public ResponseEntity<MessageResponse> editPostImageByPostImageID(Long postID, List<MultipartFile> multipartFile);

    public ResponseEntity<JwtResponse> downloadImage(Long postID);

}

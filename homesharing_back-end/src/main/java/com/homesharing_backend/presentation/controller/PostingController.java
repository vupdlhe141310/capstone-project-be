package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.data.entity.Post;
import com.homesharing_backend.data.repository.PostRepository;
import com.homesharing_backend.presentation.payload.JwtResponse;
import com.homesharing_backend.presentation.payload.request.PostRequest;
import com.homesharing_backend.presentation.payload.request.PostVoucherRequest;
import com.homesharing_backend.service.PostImageService;
import com.homesharing_backend.service.PostService;
import com.homesharing_backend.service.PostVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posting")
public class PostingController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostImageService postImageService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostVoucherService postVoucherService;

    @PostMapping(value = "/create-posting")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> createPosting(@RequestBody PostRequest postRequest) {
        return postService.createPosting(postRequest);
    }

    @PostMapping(value = "/insert-post-image")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> insertPostImage(@RequestParam("file") List<MultipartFile> multipartFiles,
                                             @RequestParam("post-id") Long postID) {
        return postImageService.insertPostImage(multipartFiles, postID);
    }

    @PostMapping(value = "/insert-post-image-one")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> insertPostImageOne(@RequestParam("file") MultipartFile multipartFiles,
                                                @RequestParam("post-id") Long postID) {
        return postImageService.insertPostImageOneByPostID(multipartFiles, postID);
    }

    @GetMapping(value = "/all-post-host")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> getAllPostByHost() {
        return postService.getAllPostByHost();
    }

    @GetMapping("/page")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<JwtResponse> page(@RequestParam("page") int page) {
        Page<Post> posts = postRepository.findAll(PageRequest.of(page, 2));
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.name(), posts.getTotalPages()));
    }

    @PutMapping("/edit-post")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> editPost(@RequestParam("post-id") Long postID,
                                      @RequestBody PostRequest postRequest) {
        return postService.editPost(postID, postRequest);
    }

    @GetMapping("/download-image")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> downloadImage(@RequestParam("post-id") Long postID) {
        return postImageService.downloadImage(postID);
    }

    @PutMapping("/edit-image")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> editImage(@RequestParam("post-id") Long postID,
                                       @RequestParam("file") List<MultipartFile> multipartFiles) {
        return postImageService.editPostImageByPostImageID(postID, multipartFiles);
    }

    @PostMapping("/insert-post-voucher")
    @PreAuthorize("hasRole('ROLE_HOST')")
    public ResponseEntity<?> insertPostVoucherByHost(@RequestParam("post-id") Long postID,
                                                     @RequestBody PostVoucherRequest postVoucherRequest) {
        return postVoucherService.insertPostVoucher(postID, postVoucherRequest);
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateStatusPostByAdmin(@RequestParam("post-id") Long postID,
                                                     @RequestParam("status") int status) {
        return postService.updateStatus(postID, status);
    }
}

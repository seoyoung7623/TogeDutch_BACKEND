package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Post;
import com.proj.togedutch.entity.User;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostService postService;
    @Autowired
    AWSS3Service awsS3Service;

    @Value("${cloud.aws.url}")
    private String url;

    // 공고 등록
    @PostMapping("")
    public BaseResponse<Post> createPost(@RequestPart Post post, @RequestParam int user, @RequestPart MultipartFile file) throws IOException{
        if (post.getTitle() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TITLE);
        }
        if (post.getUrl() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_URL);
        }
        if (Integer.valueOf(post.getDelivery_tips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TIP);
        }
        if (Integer.valueOf(post.getMinimum()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_MINIMUM);
        }
        if (Integer.valueOf(post.getNum_of_recruits()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_RECRUIT);
        }
        if (post.getLatitude() == null || post.getLongitude() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_LOCATION);
        }

        String fileUrl = null;
        if(file != null)
            fileUrl = url + awsS3Service.uploadFile(file, post, user);

        try {
            Post newPost = postService.createPost(post, user, fileUrl);
            return new BaseResponse<>(newPost);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 공고 전체 조회
    @GetMapping("")
    public BaseResponse<List<Post>> getAllPosts() throws BaseException {
        try{
            List<Post> getPostsRes = postService.getAllPosts();
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 공고 전체 조회 (최신순 / 주문임박)
    @GetMapping("/")
    public BaseResponse<List<Post>> getSortingPosts(@RequestParam String sort) throws BaseException {
        try{
            List<Post> getPostsRes = postService.getSortingPosts(sort);
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 공고 수정
    @PutMapping("/{postIdx}")
    public BaseResponse<Post> modifyPost(@PathVariable("postIdx") int postIdx, @RequestPart Post post,
                                         @RequestParam int user, @RequestPart MultipartFile file) throws Exception {

        /* 23.01.16 로그인한 유저와 Post_User_id가 같은 경우만 수정 가능하도록 검사하는 코드 추가 필요*/

        if (post.getTitle() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TITLE);
        }
        if (post.getUrl() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_URL);
        }
        if (Integer.valueOf(post.getDelivery_tips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TIP);
        }
        if (Integer.valueOf(post.getMinimum()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_MINIMUM);
        }
        if (Integer.valueOf(post.getNum_of_recruits()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_RECRUIT);
        }
        if (post.getLatitude() == null || post.getLongitude() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_LOCATION);
        }

        String fileUrl = postService.getImageUrl(postIdx);
        if(fileUrl != null) {           // 기존에 서버에 등록된 이미지 삭제
            String[] url = fileUrl.split("/");
            logger.info("Delete Image start");
            awsS3Service.deleteImage(url[3]); // https:~ 경로 빼고 파일명으로 삭제
        }
        
        // 이미지 파일이 있으면 서버에 등록
        if(file != null)
            fileUrl = url + awsS3Service.uploadFile(file, post, user);

        // 공고 내용 수정
        try {
            Post modifyPost = postService.modifyPost(postIdx, post, user, fileUrl);
            logger.info("Modify success");
            return new BaseResponse<>(modifyPost);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 공고 특정 조회 (로그인 전과 로그인 후의 화면 버튼 달라짐 -> 근데 굳이 11번과 안나눠도 될 것 같음)
    @GetMapping("/{postIdx}")
    public BaseResponse<Post> getPostByUserId(@PathVariable("postIdx") int postIdx, @RequestParam int user) throws BaseException {
        try {
            Post getPost = postService.getPostByUserId(postIdx, user);
            return new BaseResponse<>(getPost);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }















}
package com.ssafysns.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafysns.model.dto.Likes;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostLikes;
import com.ssafysns.model.service.CommentService;
import com.ssafysns.model.service.JwtService;
import com.ssafysns.model.service.LikesService;
import com.ssafysns.model.service.PostService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/likes")
@EnableAutoConfiguration
public class LikesController {
	
	@Autowired
	LikesService likesService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	JwtService jwtService;
	
	String jwtId = "forB@gmail.com";
	
	/**
	 * Likes CRUD
	 */
	// 게시글 좋아요 누르기
	@ApiOperation(value = "게시글 좋아요 누르기")
	@PostMapping("/add/{pno}")
	public ResponseEntity<Map<String, Object>> insert(@PathVariable int pno) throws Exception {
		/**
		 * JWT 토큰 받아오기
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		System.out.println("게시글 좋아요 누르기_ id: "+jwtId+", pno: "+pno);
		PostLikes post_likes = new PostLikes(jwtId, pno);
		System.out.println("#_" + post_likes);
		likesService.insert(post_likes);
		return handleSuccess("게시글 좋아요를 눌렀습니다");
	}
	
	// 댓글 좋아요 누르기
	@ApiOperation(value = "댓글 좋아요 누르기")
	@PostMapping("/add/{pno}/{cno}")
	public ResponseEntity<Map<String, Object>> insert(@PathVariable int pno, @PathVariable int cno) throws Exception {
		/**
		 * JWT 토큰 받아오기
		 */
//		Map<String, Object> jwt = jwtService.get("userid");
//		String jwtId = jwt.get("userid").toString();
		
		Likes likes = new Likes(jwtId, pno, cno);
		likesService.insert(likes);
		return handleSuccess("댓글 좋아요를 눌렀습니다");
	}
	
	
	// (하나의 게시글)해당 게시글의 좋아요 목록 가져오기 - PostController
	// (뉴스피드) 여러개의 게시글 좋아요 목록 가져오기 - PostController
	
	
	/**
	 * Exception Handler
	 */
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	private ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state",  "fail");
		resultMap.put("data",  data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}

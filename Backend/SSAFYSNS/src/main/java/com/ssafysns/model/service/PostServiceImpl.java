package com.ssafysns.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafysns.model.dto.NoticeException;
import com.ssafysns.model.dto.Post;
import com.ssafysns.model.dto.PostException;
import com.ssafysns.repository.PostRepository;
import com.ssafysns.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void insert(String id, Post post) {
		String nickname = userRepository.findById(id).get().getNickname();
		post.setNickname(nickname);
		try {
			postRepository.save(post);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 저장 중 오류가 발생했습니다.");
		}
	}

	@Override
	@Transactional
	public Boolean delete(String id, int pno) {
		Post post = null;
		
		try {
			post = postRepository.findById(pno).get();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("pno에 해당하는 게시글 정보를 불러올 수 없습니다.");
		}
		
		if(post.getUser().getId() == id) {
			try {
				postRepository.updateDeleted(pno);
			} catch (Exception e) {
				e.printStackTrace();
				throw new PostException("게시물 삭제 중 오류가 발생했습니다.");
			}
		} else { // 작성자와 로그인 유저정보가 다를 경우
			return false;
		}
		
		return true;
	}

	//게시글 수정버튼	
	@Override
	public Boolean update(String id, Post post) {
		
		if(post.getUser().getId() == id) {
			try {
				postRepository.save(post);
			} catch (Exception e) {
				e.printStackTrace();
				throw new PostException("게시물 수정 중 오류가 발생했습니다.");
			}
		} else {
			System.out.println("게시글 수정 권한이 없습니다.");
			return false;
		}
		
		return true;
	}

	// 모든 Post 조회
	@Override
	public List<Post> searchAll() {
		try {
			return postRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 목록 검색 중 오류가 발생했습니다.");
		}
	}
	
	// 최근 한 달 Post 조회
	@Override
	public List<Post> searchAMonth() {
		try {
			return postRepository.findAMonth();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("최근 한달 간 게시물 목록 검색 중 오류가 발생했습니다.");
		}
	}
	// 최근 한 달 Post Pno 조회
	@Override
	public List<Integer> searchPnoAMonth() {
		try {
			return postRepository.findPnoAMonth();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("최근 한달 간 게시물 번호 목록 검색 중 오류가 발생했습니다.");
		}
	}

	// pno로 Post 조회
	@Override
	public Optional<Post> search(int pno) {
		try {
			return postRepository.findById(pno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 검색 중 오류가 발생했습니다.");
		}
	}
	
	
	@Override
	public int count() {
		try {
			return (int) postRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PostException("게시물 개수 조회 중 오류가 발생했습니다.");
		}
	}

	/**
	 * ID 체크***
	 * 뉴스피드
	 */
	// Follow 하는 Hashtag를 포함한 게시글 리스트 가져오기
	@Override
	public List<Integer> followHashPno(String id) {
		List<Integer> all_hash_pno_list = null;
		try {
			all_hash_pno_list  = postRepository.followHashPnoById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all_hash_pno_list;
	}
	
	// [뉴스피드]
	// Follow하는 태그 리스트의 모든 글 가져오기
	@Override
	public List<Post> searchAllFollowList(List<Integer> pno_list) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.findByPnoList(pno_list);
		} catch(Exception e) {
			e.printStackTrace();
			throw new PostException("팔로우하는 모든 게시글 조회 중 오류가 발생했습니다.");
		}
		
		return posts;
	}
	
	@Override
	public List<Integer> searchPnoByHash(String hashtag) {
		System.out.println("==============Pno List 출력==============");
		List<Integer> pno_list = null;		
		try {
			pno_list = postRepository.findPnoByHashtag(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pno_list;
	}
	
	// [Tab] 탭 해시태그로 게시글 리스트 가져오기
	@Override
	public List<Post> search(String hashtag) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.findByHashtag(hashtag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return posts;
	}

	// Likes가 베스트 20인 애들만 출력
	@Override
	public List<Post> searchBest20() {
		List<Post> posts = null;
		try {
			posts = postRepository.findAll();
			posts = posts.subList(0, Integer.min(posts.size(), 20));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

}

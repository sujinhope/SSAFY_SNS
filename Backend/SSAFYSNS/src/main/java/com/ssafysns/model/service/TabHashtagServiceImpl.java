package com.ssafysns.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafysns.model.dto.TabHashtag;
import com.ssafysns.model.dto.TabHashtagException;
import com.ssafysns.repository.TabHashtagRepository;

@Service
public class TabHashtagServiceImpl implements TabHashtagService {

	@Autowired
	TabHashtagRepository tabHashtagRepository;

	@Override
	public void update(TabHashtag tabHashtag) {
		try {
			// 만약, hashtag의 길이가 0이면("") id에 해당하는 tabHashtag record를 삭제
			if (tabHashtag.getHashtag().length() == 0) {
				tabHashtagRepository.deleteByUserId(tabHashtag.getId());
			}
			// tabHashtag의 insert, delete, update가 모두 이루어짐.
			else {
				String id = tabHashtag.getId();
				Optional<TabHashtag> find = tabHashtagRepository.findByUserId(id);
				if (find.isPresent()) {
					tabHashtagRepository.updateByUserId(id, tabHashtag.getHashtag());
				} else {
					tabHashtagRepository.save(tabHashtag);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 수정 중 오류가 발생했습니다.");
		}
	}

	@Override
	public Optional<TabHashtag> searchById(String id) {
		try {
			return tabHashtagRepository.findByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TabHashtagException("TabHashtag 조회 중 오류가 발생했습니다.");
		}
	}

//	@Override
//	public void insert(TabHashtag tabHashtag) {
//		try {
//			Optional<TabHashtag> find = tabHashtagRepository.findByUserIdAndHashtag(tabHashtag.getId(),
//					tabHashtag.getHashtag());
//			if (find.isPresent()) {
//				throw new TabHashtagException("TabHashtag 등록 중 오류가 발생했습니다. : 이미 존재하는 hashtag입니다.");
//			} else {
//				tabHashtagRepository.save(tabHashtag);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException("TabHashtag 등록 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public void delete(int no) {
//		try {
//			tabHashtagRepository.deleteById(no);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException("TabHashtag 삭제 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public void deleteById(String id) {
//		try {
//			tabHashtagRepository.deleteByUserId(id);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException("TabHashtag 삭제 중 오류가 발생했습니다.");
//		}
//	}

//	public void deleteByIdAndHashtag(String id, String hashtag) {
//		try {
//			tabHashtagRepository.deleteByUserIdAndHashtag(id, hashtag);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException("TabHashtag 삭제 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public Optional<TabHashtag> search(int no) {
//		try {
//			return tabHashtagRepository.findById(no);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException("TabHashtag 조회 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public List<TabHashtag> searchAll() {
//		try {
//			return tabHashtagRepository.findAll();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException("TabHashtag 목록 조회 중 오류가 발생했습니다.");
//		}
//	}

//	@Override
//	public int count() {
//		try {
//			return (int) tabHashtagRepository.count();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TabHashtagException();
//		}
//	}

}

package com.scsa.memo.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.scsa.memo.dto.Memo;


@Service
public class MemoServiceImpl implements MemoService {

	List<Memo> list = new ArrayList<>();
	int index;
	
	@PostConstruct
	private void init() {
        list.add(new Memo(index++, "부서회의", "전체미팅 건입니다.", new Date((2023-1900),11,12).getTime()));
        list.add(new Memo(index++, "개발미팅", "과정 개발 미팅입니다.", new Date((2023-1900),10,11).getTime()));
        list.add(new Memo(index++, "소개팅", "미팅.", new Date((2023-1900),11,10).getTime()));

	}

    @Override
	public List<Memo> retrieveMemo() {
		return list;
	}
    
  	@Override
	public boolean writeMemo(Memo memo) {
  		memo.setId(index++);
		return list.add(memo);
	}

	@Override
	public Memo detailMemo(int id) {
		for (Memo memo : list) {
			if(memo.getId() == id) {
				return memo;
			}
		}
		return null;
	}

	@Override
	public boolean updateMemo(Memo memo) {
		int index = memo.getId();
		try {
			list.set(index,  memo);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteMemo(int id) {
		boolean deleted = false;
		for (Memo memo : list) {
			if(memo.getId() == id) {
				list.remove(id);
				deleted = true;
				break;
			}
		}
	
		return deleted;
	}

}
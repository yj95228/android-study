package com.scsa.memo.service;

import java.util.List;

import com.scsa.memo.dto.Memo;

public interface MemoService {
	public List<Memo> retrieveMemo();
	public Memo detailMemo(int no);
	public boolean writeMemo(Memo Memo);
	public boolean updateMemo(Memo Memo);
	public boolean deleteMemo(int no);
}

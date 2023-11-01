package com.ezen.myProject.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezen.myProject.domain.BoardDTO;
import com.ezen.myProject.domain.BoardVO;
import com.ezen.myProject.domain.FileVO;
import com.ezen.myProject.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(int bno);

	void readCount(@Param("bno")int bno, @Param("cnt")int cnt);

	int update(BoardVO bvo);

	int remove(int bno);

	int getTotalCount(PagingVO pgvo);

	int selectBno();


	int getFileCount(BoardVO bvo);

	int countComment(BoardVO bvo);

	void fileCount(BoardVO bvo1);




}

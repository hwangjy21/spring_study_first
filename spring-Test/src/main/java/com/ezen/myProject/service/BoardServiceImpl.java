package com.ezen.myProject.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.myProject.domain.BoardDTO;
import com.ezen.myProject.domain.BoardVO;
import com.ezen.myProject.domain.FileVO;
import com.ezen.myProject.domain.PagingVO;
import com.ezen.myProject.repository.BoardDAO;
import com.ezen.myProject.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO bdao;
	
	@Inject
	private FileDAO fdao;

	@Override
	public int register(BoardDTO bdto) {
		log.info("register check 2");
		//기존 게시글에 대한 내용을 DB에 저장 같은 내용을 저장
		int isOk = bdao.insert(bdto.getBvo());
		
		//-----파일 저장 라인
		
		
		if(bdto.getFlist()==null) {
			//파일의 값이 null이면 저장 없음
			isOk *=1;//그냥 성공한 걸루...
		}
		
		else {
			//bvo의 값이 들어가고, 파일의 개수가 있다면...
			if(isOk>0 && bdto.getFlist().size()>0) {
				//fvo의 bno는 아직 설정되기 전...
				
				//현재 시점에서 bno는 아직 결정되지 않았음=> db insert ai에 의해 자동 생성
				
				int bno = bdao.selectBno();// 방긍 저장된 bno가져오기
				
				log.info("제발ㅎㅎ2");
				//flist 의 모든 fileVO에 방금 가져온 = bno set
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					log.info(">>insert fvo"+fvo);
					
					//파일 저장ㄴ
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
		
		return isOk;
	
	}

//	@Override
//	public List<BoardVO> getList() {
//		log.info("list check 2");
//		return bdao.getList();
//	}

	@Override
	public BoardVO getDetail(int bno) {
		//read_count + 1
		//int cnt = 1;
		bdao.readCount(bno, 1);
		return bdao.getDetail(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		//수정할 때 들어가는 부당 read_count 2개 빼주기
		//read_count - 2
		//int cnt = -2;
		bdao.readCount(bvo.getBno(), -2);
		return bdao.update(bvo);
	}

	@Override
	public int remove(int bno) {
		// TODO Auto-generated method stub
		return bdao.remove(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
	
		List<BoardVO> list = bdao.getList(pgvo);

	    // 파일 개수를 가져와서 각 게시물에 설정
	    for (BoardVO bvo1 : list) {
	      bdao.countComment(bvo1);
	      bdao.fileCount(bvo1);
	    }

	
		return bdao.getList(pgvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public int fileCount(BoardVO bvo) {
		// TODO Auto-generated method stub
		return bdao.getFileCount(bvo);
	}

	@Override
	public int countComment(BoardVO bvo) {
		// TODO Auto-generated method stub
		return bdao.countComment(bvo);
	}

	@Override
	public BoardDTO getDetailFile(int bno) {
		//detail bvo, file 같이 가져오기
		bdao.readCount(bno, 1); //리드 카운트 올리기
		
		BoardDTO bdto = new BoardDTO();
		bdto.setBvo(bdao.getDetail(bno)); //bdao bvo 호출
		bdto.setFlist(fdao.getFileList(bno));
		
		return bdto;
	}



	@Override
	public BoardDTO getDetailFile_d(String uuid) {
		// TODO Auto-generated method stub
		return fdao.getDetailFile(uuid);
	}

	@Override
	public int delete(String uuid) {
		// TODO Auto-generated method stub
		return fdao.delete_f(uuid);
	}

	@Override
	public int modifyFiles(BoardDTO bdto) {
		
		bdao.readCount(bdto.getBvo().getBno(), -2);
		int isOk = bdao.update(bdto.getBvo()); //기존 bvo update 
		if(bdto.getFlist()==null) {
			isOk*=1;
		}else {
			if(isOk>0 && bdto.getFlist().size()>0) {
				int bno = bdto.getBvo().getBno();
				
				//모든 fvo에 bno set
				for(FileVO fvo: bdto.getFlist()) {
					fvo.setBno(bno);
					isOk*=fdao.insertFile(fvo);
				}
			}
		}
		return isOk;
	}

	/*
	 * @Override public int register(BoardVO bvo) { // TODO Auto-generated method
	 * stub return 0; }
	 */
}

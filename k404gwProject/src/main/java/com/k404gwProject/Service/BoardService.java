package com.k404gwProject.Service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.k404gwProject.Dto.BoardDto;
import com.k404gwProject.Entity.Board;

public interface BoardService {
	
	
//	public Long createQnaBoard(BoardDto boardDto, String email)throws Exception;  //게시글 작성
//	public void insert(Board qnaBoard, Member member);
//	public void insert(Board board);
	public void createQboard(String title, String content, String name, HttpSession session, BoardDto boardDto);
	public List<BoardDto> findAll();
	public BoardDto boardDtl(Long id);
	public Long updateQboard(BoardDto boardDto);
	public void deleteQboard(BoardDto boardDto);
}

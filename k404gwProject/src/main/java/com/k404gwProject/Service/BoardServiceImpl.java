package com.k404gwProject.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.k404gwProject.Dto.BoardDto;
import com.k404gwProject.Entity.Board;
import com.k404gwProject.Entity.Member;
import com.k404gwProject.Repository.BoardRepository;
import com.k404gwProject.Repository.MemberRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private MemberRepository memberRepository;	
//	
//	public void insert(Board board) {
//		System.out.println("여기까진 왔니? setMember 전");
////		qnaBoard.setMember(member);
//		System.out.println("여기까진 왔니? setMember 후");
////		System.out.println("qnaBoard에는 뭐가 있을까? :"+ qnaBoard.getMember());
////		System.out.println("qnaBoard에는 뭐가 있을까? :"+ qnaBoard.getMember().getId());
//		System.out.println("qnaBoard에는 뭐가 있을까? :"+ board.getId());
//		System.out.println("qnaBoard에는 뭐가 있을까? :"+ board.getContent());
//		System.out.println("qnaBoard에는 뭐가 있을까? :"+ board.getTitle());
//		System.out.println("qnaBoard에는 뭐가 있을까? :"+ board.getName());
//		
//		boardRepository.save(board);
//	}
	public void createQboard(String title, String content, String name, HttpSession session) {
		Member sessionMember = (Member) session.getAttribute("email");
		String email = sessionMember.getEmail();
		Member LoginMember = memberRepository.findByEmail(email);
		System.out.println("LoginMember 서비스 :" + LoginMember);
		Board newBoard = new Board(LoginMember, title, content, name);
		 System.out.println("newBoard에는 뭐가 들어있을까?"+newBoard);
		boardRepository.save(newBoard);
	}
	
	public List<BoardDto> findAll() {
		List<Board> boardList = boardRepository.findAll();
		System.out.println("boardList에는 뭐가 들어있니?"+boardList);
		List<BoardDto> boardDtoList = new ArrayList();
		
		for (Board board : boardList) {
			boardDtoList.add(BoardDto.toBoardDto(board));
		}
		return boardDtoList;
	}

	@Override
	public BoardDto boardDtl(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		BoardDto boardDtl = BoardDto.toBoardDto(board);
		return boardDtl;
	}

	@Override
	@Transactional
	public Long updateQboard(BoardDto boardDto) {
		Board board = boardRepository.findById(boardDto.getId()).orElseThrow(EntityNotFoundException::new);	
		board.updateBoard(boardDto);
//		boardRepository.save(board2);
		
		return board.getId();
	}

	@Override
	public void deleteQboard(BoardDto boardDto) {
		boardRepository.deleteById(boardDto.getId());
		
	}
	
	
	
	
	
	
	
	
	
//	@Override
//	public Long createQnaBoard(BoardDto boardDto, String email) throws Exception {
//			System.out.println("메서드 실행은 했니?");
//			QnaBoard qnaBoard = new QnaBoard();
//			qnaBoard.createQboard(boardDto);
////			
////		Long test1 = qnaBoard.getMember().getId();
////			QnaBoard test2 = boardRepository.findById(test1).orElseThrow(EntityNotFoundException::new);
////			boardRepository.save(qnaBoard);
//		System.out.println("엔티티맵핑전 qndBoard: ");
//		System.out.println("멤버추가전 qndBoard: "+qnaBoard);
//		Member member = memberRepository.findByEmail(email);
//		System.out.println("member: "+ member);
//		qnaBoard.setMember(member);
//		System.out.println("멤버추가후 qndBoard: "+qnaBoard);
//		boardRepository.save(qnaBoard);	;
//		
//		return qnaBoard.getMember().getId();
//	}
//
//	
	
}

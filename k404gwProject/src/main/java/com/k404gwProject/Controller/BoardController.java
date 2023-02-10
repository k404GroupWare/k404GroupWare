package com.k404gwProject.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.k404gwProject.Dto.BoardDto;
import com.k404gwProject.Repository.BoardRepository;
import com.k404gwProject.Repository.MemberRepository;
import com.k404gwProject.Service.BoardService;

@RequestMapping("/board")
@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@GetMapping (value="/list")
	public String boardList(Model model) {
		List<BoardDto> boardDtoList = boardService.findAll();
		model.addAttribute("boardList",boardDtoList);
		return "board/boardList";
	}
	@GetMapping(value="/write")
	public String boardWriteForm() {
		
		return "board/boardWrite";
	}
//	@PostMapping(value="/write") 
//	public String boardWrite(HttpServletRequest req, Board board){     
////	     if(bindingResult.hasErrors()){
////	        	System.out.println("오류발생");
////	        	System.out.println(bindingResult);
////	            return "board/boardWrite";
////	        }
////            try {
////            	System.out.println("createQnaBoard작업전");
////				Long test = boardService.createQnaBoard(boardDto, email);
////				System.out.println("리턴값 설정" + test);
////			} catch (Exception e) {
////				System.out.println("여기니?");
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		HttpSession session = req.getSession();
//		Member memT = (Member) session.getAttribute("email");
//		System.out.println("세션 부름 : " + memT);
//		board.setId(1L);
////		qnaBoard.setMember(memT);
//		// qnaBoard 글쓰기 폼에서 입력된 일반글 + 세션에서 가져온 멤버 정보 객체
//		System.out.println("board 값 : " +board);
////		System.out.println("qnaBoard.getMember() :" + qnaBoard.getMember());
////		System.out.println("qnaBoard.getMember() :" + qnaBoard.getMember().getEmail());
////	     Member member = memberRepository.findByEmail(qnaBoard.getMember().getEmail());
////	     System.out.println("qnaboard에 담긴 member정보 있니? :" + member);
////	     boardService.insert(qnaBoard, member);
//	     boardService.insert(board);
//	     
//	     Member memT2 = (Member) session.getAttribute("email");
//	     String memT3 = "Member(Iid="+memT2.getId()+")";
//	     
//		return "board/boardList";
//	}
	
	@PostMapping (value = "/write")
	public String create(String title, String content, String name, HttpSession session) {
		boardService.createQboard(title,content,name,session);
//		Member LoginMember = (Member) session.getAttribute("email");
//		System.out.println("LoginMember 컨트롤러 : " + LoginMember);
//		String email = LoginMember.getEmail();
//		Member test = memberRepository.findByEmail(email);
//		System.out.println("email 컨트롤러 :" + email);
//		System.out.println("test 컨트롤러 :" + test);
//		
//		Board newBoard = new Board(LoginMember, title, content, name);
//		boardRepository.save(newBoard);
		return "redirect:/board/list";
	}
	
	@GetMapping(value="/boardDtl/{id}")
	public String boardDtl(@PathVariable("id") Long id, Model model) {
		System.out.println("너 /boardDtl/{title} 실행했니?");
		BoardDto boardDtl = boardService.boardDtl(id);
		System.out.println("boardDtl의 값 확인!!!!"+boardDtl);
		model.addAttribute("board", boardDtl);	
		return "board/boardDtl";
	}
	
	@GetMapping(value="/mdForm/{id}")
	public String mdForm(@PathVariable("id") Long id, Model model) {
		BoardDto boardDtl = boardService.boardDtl(id);
		System.out.println("boardDtl 안에는 뭐가 들어있을까?: " +boardDtl);
		model.addAttribute("board", boardDtl);	
		return "board/mdWrite";
	}
	
	// 수정하기
	@PostMapping(value="/mdForm/{id}")
	public String MdBoard(@PathVariable("id") Long id, BoardDto boardDto, Model model) {
		BoardDto boardDtl2 = boardService.boardDtl(id);
		System.out.println("수정전  boardDtl2.getTitle()에는 뭐가 들어있을까요?"+boardDtl2.getTitle());		
		
		boardService.updateQboard(boardDto);		
		BoardDto boardDtl = boardService.boardDtl(id);
		
		System.out.println("title 모 야?"+id);
		System.out.println("수정한후 boardDtl.getTitle()에는 뭐가 들어있을까요?"+boardDtl.getTitle());
		
		model.addAttribute("board", boardDtl);
		return "board/boardDtl";
	}
	// 삭제하기
	@GetMapping(value="/delete/{id}")
	public String DelBoard(@PathVariable("id")Long id  , BoardDto boardDto) {
		boardService.deleteQboard(boardDto);
		return "redirect:/board/list"; 
	}
}

package com.k404gwProject.Controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.k404gwProject.Dto.BoardDto;
import com.k404gwProject.Dto.QnaBoardFileDto;
import com.k404gwProject.Entity.Board;
import com.k404gwProject.Repository.BoardRepository;
import com.k404gwProject.Repository.MemberRepository;
import com.k404gwProject.Service.BoardFileService;
import com.k404gwProject.Service.BoardService;
import com.k404gwProject.Service.FileService;

@RequestMapping("/board")
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private BoardFileService boardFileService;
	
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
////	        	System.out.println("????????????");
////	        	System.out.println(bindingResult);
////	            return "board/boardWrite";
////	        }
////            try {
////            	System.out.println("createQnaBoard?????????");
////				Long test = boardService.createQnaBoard(boardDto, email);
////				System.out.println("????????? ??????" + test);
////			} catch (Exception e) {
////				System.out.println("??????????");
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		HttpSession session = req.getSession();
//		Member memT = (Member) session.getAttribute("email");
//		System.out.println("?????? ?????? : " + memT);
//		board.setId(1L);
////		qnaBoard.setMember(memT);
//		// qnaBoard ????????? ????????? ????????? ????????? + ???????????? ????????? ?????? ?????? ??????
//		System.out.println("board ??? : " +board);
////		System.out.println("qnaBoard.getMember() :" + qnaBoard.getMember());
////		System.out.println("qnaBoard.getMember() :" + qnaBoard.getMember().getEmail());
////	     Member member = memberRepository.findByEmail(qnaBoard.getMember().getEmail());
////	     System.out.println("qnaboard??? ?????? member?????? ??????? :" + member);
////	     boardService.insert(qnaBoard, member);
//	     boardService.insert(board);
//	     
//	     Member memT2 = (Member) session.getAttribute("email");
//	     String memT3 = "Member(Iid="+memT2.getId()+")";
//	     
//		return "board/boardList";
//	}
	
	@PostMapping (value = "/write")
	public String create(String title, String content, String name, HttpSession session, BoardDto boardDto, MultipartFile file, HttpServletRequest request) {
		boardService.createQboard(title,content,name,session, boardDto, file, request);
		return "redirect:/board/list";
//		return "redirect:/board/list";
	}
	
    // ????????? ?????? ?????????
    @PostMapping(value = "/upload",  produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) {
    	System.out.println("upload ?????????????");
        ResponseEntity<String> entity = null; 
        try {
            String savedFilePath = fileService.uploadFile(file, request);
            entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);            
        }
        return entity;
    }
    
    // ????????? ???????????? ??????
    @GetMapping(value = "/display")
    public  ResponseEntity<byte[]> displayFile( String fileName, HttpServletRequest request) throws Exception {

        HttpHeaders httpHeaders = fileService.getHttpHeaders(fileName); // Http ?????? ?????? ????????????

        String rootPath = fileService.getRootPath(fileName, request); // ????????? ???????????? ??????

        ResponseEntity<byte[]> entity = null;

        // ???????????????, HttpHeader ??????
        try (InputStream inputStream = new FileInputStream(rootPath + fileName)) {
            entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            
        }
        return entity;
    }
    
    // ????????? ?????? ?????? : ????????? ?????? ????????? , deleteMapping ????????? ????????? ???
    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteFile(String fileName, HttpServletRequest request) {
        ResponseEntity<String> entity = null;
        
        try {
        	System.out.println("????????? ????????? fileName: "+fileName);
        	
            fileService.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
    @GetMapping(value="/fileList/{qnaNo}")
    	public ResponseEntity<List<QnaBoardFileDto>> getFiles(@PathVariable("qnaNo") Board qnaNo) {
    	 ResponseEntity<List<QnaBoardFileDto>> entity = null;
    	 try {
    		 List<QnaBoardFileDto> fileList = boardFileService.getBoardFile(qnaNo);
    		 System.out.println("???????????? fileList :" + fileList);
    		 entity = new ResponseEntity<>(fileList,HttpStatus.OK);
    		 
    	 }catch(Exception e) {
    		 e.printStackTrace();
    		 entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	 }
    	 
    	 return entity;
    }
    // ????????? ?????? ?????? ??????
    @PostMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteAllFiles(@RequestParam("files[]") String[] files, HttpServletRequest request) {

        if (files == null || files.length == 0)
            return new ResponseEntity<>("DELETED", HttpStatus.OK);

        ResponseEntity<String> entity = null;

        try {
            for (String fileName : files)
                fileService.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
	
	@GetMapping(value="/boardDtl/{id}")
	public String boardDtl(@PathVariable("id") Long id, Model model) {
		System.out.println("??? /boardDtl/{title} ?????????????");
		BoardDto boardDtl = boardService.boardDtl(id);
		Long idTest = boardDtl.getId();
		System.out.println("boardDtl??? ??? ??????!!!!"+boardDtl);
		model.addAttribute("board", boardDtl);
		model.addAttribute("boardFile",idTest);
		System.out.println("idTest??? ??? : "+ idTest);
		return "board/boardDtl";
	}
	
	@GetMapping(value="/mdForm/{id}")
	public String mdForm(@PathVariable("id") Long id, Model model) {
		BoardDto boardDtl = boardService.boardDtl(id);
		System.out.println("boardDtl ????????? ?????? ????????????????: " +boardDtl);
		model.addAttribute("board", boardDtl);	
		return "board/mdWrite";
	}
	
	// ????????????
	@PostMapping(value="/mdForm/{id}")
	public String MdBoard(@PathVariable("id")Long id, @PathVariable("id") Board qnaNo, BoardDto boardDto, Model model) {
		System.out.println("???????????? ?????? qnaNo ??? :" + qnaNo);
		BoardDto boardDtl2 = boardService.boardDtl(id);
		System.out.println("?????????  boardDtl2.getTitle()?????? ?????? ???????????????????"+boardDtl2.getTitle());		
		
		boardService.updateQboard(boardDto, id, qnaNo);		
		BoardDto boardDtl = boardService.boardDtl(id);
		
		System.out.println("title ??? ????"+id);
		System.out.println("???????????? boardDtl.getTitle()?????? ?????? ???????????????????"+boardDtl.getTitle());		
		
		return "board/boardDtl";
	}
	// ????????????
	@GetMapping(value="/delete/{id}")
	public String DelBoard(@PathVariable("id")Long id  , BoardDto boardDto) {
		boardService.deleteQboard(boardDto);
		return "redirect:/board/list"; 
	}
}

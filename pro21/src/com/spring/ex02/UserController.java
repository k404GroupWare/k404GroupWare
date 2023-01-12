package com.spring.ex02;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
public class UserController extends MultiActionController {
	
	//Controller -> 데이터와 뷰를 같이 전달. 
	// 비교 되는 부분 RestController -> 데이터만 던진다. 
	//ModelAndView -> 데이터와 뷰를 같이 취급하는 그릇.
	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//테스트 하기위한 아이디와 패스워드 임시값.
		String userID = "";
		String passwd = "";
		String name = "";
		String lunch = "";
		String price = "";
		// ModelAndView 사용하기 위해서 해당 객체를 생성.
		ModelAndView mav = new ModelAndView();

		// 해당 한글 2~3바이트 웹브라우저로 그냥 전달하면 깨짐. 
		// 인코딩을 수동으로 하건, 필터에 기능을 추가해서 처리를 하건.
		// utf-8 3바이트 단위로 전달하는 중간 데이터 형식. 
		
		request.setCharacterEncoding("utf-8");
		// 뷰에서 일반 데이터를 받아서 처리는 request 라는 객체에 담아서 작업 했음. 
		// 파일 데이터 멀티파트 request 객체 : 일반 데이터와 파일 데이터를 같이 작업 할 때 사용. 
		
		// 사용자가 입력한 폼에서 정보를 가져오는 역할. 
		userID = request.getParameter("userID");
		passwd = request.getParameter("passwd");

		name = request.getParameter("name");
		lunch = request.getParameter("lunch");
		price = request.getParameter("price");

		//mav : ModelAndView 여기서 키와 값 의 형식으로 데이터를 저장. 
		mav.addObject("userID", userID);
		mav.addObject("passwd", passwd);
		// 연습하기. 해당 컨트롤러에서 하드코딩으로 입력된 값을 해당 뷰에 넘기는 연습. 
		mav.addObject("name",name);
		mav.addObject("lunch",lunch);
		mav.addObject("price",price);

		// mav 를  해당 뷰에 적요하는 부분.( 위에서 설정한 데이터를 같이 전송.)
		// result -> 뷰 페이지를 의미. 경로 /test/result.jsp
		// prefix : /test/
		// suffix : .jsp 
		// 해당 뷰의 알맹이 이름만 있으면 자동으로 매핑. 
		mav.setViewName("result");
		// 결론은 mav 객체에는 , 뷰 와 데이터 같이 들어 있습니다. 
		return mav;
	}
	
//	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String userID = "";
//		String passwd = "";
//		ModelAndView mav = new ModelAndView();
//		request.setCharacterEncoding("utf-8");
//		userID = request.getParameter("userID");
//		passwd = request.getParameter("passwd");
//		String viewName=getViewName(request);
//		
//		mav.addObject("userID", userID);
//		mav.addObject("passwd", passwd);
//		//mav.setViewName("result");
//		mav.setViewName(viewName);
//	    System.out.println("ViewName:"+viewName);
//		return mav;
//	}
	public ModelAndView memberInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
	    ModelAndView mav=new ModelAndView();
	    String id=request.getParameter("id");
	    String pwd=request.getParameter("pwd");
	    String name=request.getParameter("name");
	    String email=request.getParameter("email");
	    mav.addObject("id",id);
	    mav.addObject("pwd",pwd);
	    mav.addObject("name",name);
	    mav.addObject("email",email);
	    mav.setViewName("memberInfo");
	    return mav;
	}	
	
	private  String getViewName(HttpServletRequest request) throws Exception {
	      String contextPath = request.getContextPath();
	      System.out.println("contextPath : " +contextPath);
	      String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
	      if(uri == null || uri.trim().equals("")) {
	         uri = request.getRequestURI();
	      }
	      System.out.println("2 uri : " +uri);
	      
	      
	      int begin = 0;
	      if(!((contextPath==null)||("".equals(contextPath)))){
	         begin = contextPath.length();
	      }
	      System.out.println("3 begin : " +begin);
	      
	      
	      
	      int end;
	      if(uri.indexOf(";")!=-1){
	         end=uri.indexOf(";");
	         System.out.println("4 end ; : " +begin);
	      }else if(uri.indexOf("?")!=-1){
	         end=uri.indexOf("?");
	         System.out.println("5 end ? : " +begin);
	      }else{
	         end=uri.length();
	         System.out.println("6 end 아무것도 없음. : " +begin);
	      }
	      String fileName=uri.substring(begin,end);
	      
	      System.out.println(" uri : " +uri);
	      
	      
	      if(fileName.indexOf(".")!=-1){
	         fileName=fileName.substring(0,fileName.lastIndexOf("."));
	      }
	      if(fileName.lastIndexOf("/")!=-1){
	         fileName=fileName.substring(fileName.lastIndexOf("/"),fileName.length());
	      }
	      return fileName;
	   }
	
	
}

package com.inbook.chatRoom.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inbook.chatRoom.service.ChatRoomDeleteService;
import com.inbook.chatRoom.service.ChatRoomListService;
import com.inbook.chatRoom.service.ChatRoomUpdateService;
import com.inbook.chatRoom.service.ChatRoomWriteService;
import com.inbook.chatRoom.vo.ChatRoomVO;
import com.inbook.main.Controller;
import com.inbook.main.Execute;
import com.inbook.member.vo.LoginVO;
import com.webjjang.util.PageObject;

public class ChatRoomController implements Controller {

	private ChatRoomListService chatRoomListService;
	private ChatRoomWriteService chatRoomWriteService;
	private ChatRoomUpdateService chatRoomUpdateService;
	private ChatRoomDeleteService chatRoomDeleteService;

	public void setChatRoomListService(ChatRoomListService chatRoomListService) {
		this.chatRoomListService = chatRoomListService;
	}

	public void setChatRoomWriteService(ChatRoomWriteService chatRoomWriteService) {
		this.chatRoomWriteService = chatRoomWriteService;
	}

	public void setChatRoomUpdateService(ChatRoomUpdateService chatRoomUpdateService) {
		this.chatRoomUpdateService = chatRoomUpdateService;
	}

	public void setChatRoomDeleteService(ChatRoomDeleteService chatRoomDeleteService) {
		this.chatRoomDeleteService = chatRoomDeleteService;
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception {

		System.out.println("ChatRoomController.execute() - 채팅방 처리하고 있다.");

		String jsp = null;

		String uri = request.getServletPath();
		String serviceUri = uri.substring(uri.indexOf("/", 1));
		System.out.println("ChatRoomController.execute().serviceUri - " + serviceUri);

		switch (serviceUri) {
		// 채팅방 리스트
		case "/list.do":

			// Page 정보 받기
			PageObject pageObject = PageObject.getInstance(request);
			request.setAttribute("list", Execute.service(chatRoomListService, pageObject));
			request.setAttribute("pageObject", pageObject);

			jsp = "chatRoom/list";
			break;

		case "/view.do":

			break;

		case "/writeForm.do":

			break;

		// 채팅방 글쓰기 처리
		case "/chatRoomWrite.do":

			// 넘어오는 데이터 받기
			String title = request.getParameter("title");
			String id = (String) ((LoginVO) request.getSession().getAttribute("login")).getId();
			String strPerPageNum = request.getParameter("perPageNum");

			// 넘겨 받은 데이터를 vo에 생성해서 넣어준다.
			ChatRoomVO vo = new ChatRoomVO();
			vo.setTitle(title);
			vo.setId(id);

			// DB 등록
			Execute.service(chatRoomWriteService, vo);

			// redirect: - url 이동, 없으면 jsp로 이동
			jsp = "redirect:list.do?perPageNum=" + strPerPageNum;
			break;

		case "/updateForm.do":

			break;

		// 채팅방 수정 처리
		case "/chatRoomUpdate.do":

			// 넘어오는 데이터 받기
			String strCno = request.getParameter("cno");
			Long cno = Long.parseLong(strCno);
			title = request.getParameter("title");

			// 넘겨 받은 데이터를 vo에 생성해서 넣어준다.
			vo = new ChatRoomVO();
			vo.setCno(cno);
			vo.setTitle(title);

			// DB 등록
			Execute.service(chatRoomUpdateService, vo);

			// 수정 처리 후 이동할 페이지 정보를 "redirect:"를 붙여서 넣어 준다.
			jsp = "redirect:list.do";

			break;

		// 채팅방 삭제 처리
		case "/chatRoomDelete.do":

			// 넘어오는 데이터 받기
			strCno = request.getParameter("cno");
			cno = Long.parseLong(strCno);
			id = (String) ((LoginVO) request.getSession().getAttribute("login")).getId();

			// 넘겨 받은 데이터를 vo에 생성해서 넣어준다.
			vo = new ChatRoomVO();
			vo.setCno(cno);
			vo.setId(id);

			// DB 등록
			Execute.service(chatRoomDeleteService, vo);

			jsp = "redirect:list.do?no =" + request.getParameter("cno") + "&page=" + request.getParameter("page")
					+ "&perPageNum=" + request.getParameter("perPageNum") + "&key=" + request.getParameter("key")
					+ "&word=" + request.getParameter("word") + "&chatRoomPage=1";

			break;

		default:
			throw new Exception("잘못된 페이지를 요청하셨습니다.");

		}

		return jsp;
	}
}

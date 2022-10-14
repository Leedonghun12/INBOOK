package com.inbook.chat.controller;

import javax.servlet.http.HttpServletRequest;

import com.inbook.chat.service.ChatMaxNoService;
import com.inbook.main.Controller;
import com.inbook.main.Execute;
import com.inbook.member.vo.LoginVO;
import com.webjjang.util.PageObject;

public class ChatController implements Controller {

	private ChatMaxNoService chatMaxNoService;

	public void setChatMaxNoService(ChatMaxNoService chatMaxNoService) {
		this.chatMaxNoService = chatMaxNoService;
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception {

		System.out.println("ChatController.execute() - 채팅 처리하고 있다.");

		String jsp = null;

		// uri - /chat/list.do - 처리 service 결정하는 - /list.do
		String uri = request.getServletPath();
		String serviceUri = uri.substring(uri.indexOf("/", 1));
		System.out.println("ChatController.execute().serviceUri - " + serviceUri);

		switch (serviceUri) {
		case "/list.do":

			// 넘어오는 데이터 받기
			String cnoStr = request.getParameter("cno");
			Long cno = Long.parseLong(cnoStr);

			request.setAttribute("MaxNo", Execute.service(chatMaxNoService, cno));
			
			jsp = "chat/list";
			break;

		case "/view.do":

			break;

		case "/writeForm.do":

			break;

		case "/write.do":

			break;

		case "/updateForm.do":

			break;

		case "/update.do":

			break;

		case "/delete.do":

			break;

		default:
			break;
		}

		return jsp;
	}
}

package com.liminal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FinishGame
 */
@WebServlet("/FinishGame")
public class FinishGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinishGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String service = request.getParameter("serviceUrl");
		HttpSession sess = request.getSession();
		sess.removeAttribute("HostedGame");
		sess.removeAttribute("CurrentGame");
		sess.removeAttribute("GameJoined");
		response.sendRedirect(request.getContextPath() + "/myapp/jsps/loggedin.jsp");
	}

}

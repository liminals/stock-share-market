package com.liminal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class GameEnded
 */
@WebServlet("/GameEnded")
public class GameEnded extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameEnded() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sess = request.getSession();
		if (sess.getAttribute("HostedGame") != null)
			sess.removeAttribute("HostedGame");
		if (sess.getAttribute("CurrentGame") != null)
			sess.removeAttribute("CurrentGame");
		if (sess.getAttribute("GameJoined") != null)
			sess.removeAttribute("GameJoined");
		if (sess.getAttribute("GameJoinData") != null)
			sess.removeAttribute("GameJoinData");
		
		JSONObject ob = new JSONObject();
		String urlToRedirect = request.getContextPath() + "/myapp/jsps/loggedin.jsp";
		ob.put("url", urlToRedirect);
		response.setContentType("application/json");
		response.getWriter().write(ob.toString());
	}

}

package com.liminal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.liminal.model.Player;

/**
 * Servlet implementation class LogoutPlayer
 */
@WebServlet("/LogoutPlayer")
public class LogoutPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutPlayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String service = request.getParameter("serviceUrl");
		String target = service + "player/logout";
		
		HttpSession session = request.getSession();
		Player p = (Player) session.getAttribute("CurrentPlayer");
		
		Client client = ClientBuilder.newClient();
		Response res = client.target(target).request().post(Entity.json(p));
		
		// 204 no content
		if (res.getStatus() == 204) {
			session.removeAttribute("CurrentPlayer");
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}

}

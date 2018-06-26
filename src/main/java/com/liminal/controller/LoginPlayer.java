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
 * Servlet implementation class LoginPlayer
 */
@WebServlet("/LoginPlayer")
public class LoginPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginPlayer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String serviceUrl = request.getParameter("serviceUrl");
		String targetUrl = serviceUrl + "player/login";
		
		Player p = new Player();
		p.setUsername(username);
		p.setPassword(password);
		
		Client client = ClientBuilder.newClient();
		Response serviceResponse = client.target(targetUrl).request().post(Entity.json(p));
		
		if (serviceResponse.getStatus() == 200) {
			// if id = 0, invalid username
			Player resPlayer = serviceResponse.readEntity(Player.class);
			int playerId = resPlayer.getId();
			if (playerId == -1) {
				request.setAttribute("LoginErrorMessage", "Invalid Username");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else if(playerId == 0) {
				request.setAttribute("LoginErrorMessage", "Invalid Password");
				request.setAttribute("Username", resPlayer.getUsername());
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				HttpSession session = request.getSession();
				//save message in session
				session.setAttribute("CurrentPlayer", resPlayer);
				response.sendRedirect(request.getContextPath() + "/myapp/jsps/loggedin.jsp");
							
				// request.setAttribute("CurrentPlayer", resPlayer);
				// request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
	}

}

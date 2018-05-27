package com.liminal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.liminal.model.GameHostingData;
import com.liminal.model.Player;

/**
 * Servlet implementation class RegisterPlayer
 */
@WebServlet("/RegisterPlayer")
public class RegisterPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterPlayer() {
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
		String targetUrl = serviceUrl + "player/register";
		
		Player p = new Player();
		p.setUsername(username);
		p.setPassword(password);

		Client client = ClientBuilder.newClient();
		Response serviceResponse = client.target(targetUrl).request().post(Entity.json(p));
		
		if (serviceResponse.getStatus() == 200) {
			Player resPlayer = serviceResponse.readEntity(Player.class);
			// if id = 0, username exists else success
			//request.getSession().setAttribute("HostedGame", serviceResponse.readEntity(GameHostingData.class));
			//request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

}

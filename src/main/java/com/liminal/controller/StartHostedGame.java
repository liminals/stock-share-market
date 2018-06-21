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

import com.liminal.model.ClientTurn;
import com.liminal.model.GameHostingData;

/**
 * Servlet implementation class StartHostedGame
 */
@WebServlet("/StartHostedGame")
public class StartHostedGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartHostedGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameHostingData gameHostingData = (GameHostingData)request.getSession().getAttribute("HostedGame");

		String serviceUrl = request.getParameter("serviceUrl");
		String targetUrl = serviceUrl + "game/start/";
		String playersUrl = serviceUrl + "game/" + gameHostingData.getId() + "/checkForPlayers";
		
		Client client1 = ClientBuilder.newClient();
		Response res = client1.target(playersUrl).request().get();
		
		if (res.getStatus() == 200) {
			gameHostingData.setPlayers(res.readEntity(String.class));
		}
		
		Client client2 = ClientBuilder.newClient();
		Response serviceResponse = client2.target(targetUrl).request().post(Entity.json(gameHostingData));
		
		if (serviceResponse.getStatus() == 200) {
			request.getSession().setAttribute("CurrentGame", serviceResponse.readEntity(ClientTurn.class));
			request.getRequestDispatcher("myapp/jsps/gamepage.jsp").forward(request, response);
		}
	}

}

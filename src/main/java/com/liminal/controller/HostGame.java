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

import org.json.JSONObject;

import com.liminal.model.Game;
import com.liminal.model.GameHostingData;

/**
 * Servlet implementation class HostGame
 */
@WebServlet("/HostGame")
public class HostGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HostGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String createdBy = request.getParameter("createdBy");
		int turns = Integer.parseInt(request.getParameter("turns"));
		String serviceUrl = request.getParameter("serviceUrl");
		String targetUrl = serviceUrl + "game/create/";
		String accountUrl = serviceUrl + "bank/setAccount/";
		
		
		JSONObject players = new JSONObject();
		players.put("1", createdBy);
		players.put("2", "AI");
		
		accountUrl += createdBy;
		
		GameHostingData gcd = new GameHostingData();
		gcd.setTurns(turns);
		gcd.setCreatedBy(createdBy);
		gcd.setPlayers(players.toString());
		
		Client client = ClientBuilder.newClient();
		Response serviceResponse = client.target(targetUrl).request().post(Entity.json(gcd));
		
		if (serviceResponse.getStatus() == 200) {
			// setup account for each game
			Client accountReq = ClientBuilder.newClient();
			Response res = accountReq.target(accountUrl).request().post(Entity.json(""));
			
			request.getSession().setAttribute("HostedGame", serviceResponse.readEntity(GameHostingData.class));
			request.getRequestDispatcher("myapp/jsps/loggedin.jsp").forward(request, response);
		}
	}

}

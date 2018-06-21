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

import com.liminal.model.GameJoinData;

/**
 * Servlet implementation class JoinHostedGame
 */
@WebServlet("/JoinHostedGame")
public class JoinHostedGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinHostedGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gameId = Integer.parseInt(request.getParameter("gameId"));
		String name = request.getParameter("username");
		String serviceUrl = request.getParameter("serviceUrl");
		
		String targetUrl = serviceUrl + "game/join";
		GameJoinData data = new GameJoinData();
		data.setGameId(gameId);
		data.setPlayerName(name);
		data.setStatus(GameJoinData.STATUS.REQUESTING.toString());
		
		Client client = ClientBuilder.newClient();
		Response serviceResponse = client.target(targetUrl).request().post(Entity.json(data));
		
		if (serviceResponse.getStatus() == 200){
			GameJoinData resData = (GameJoinData) serviceResponse.readEntity(GameJoinData.class);
			if (resData.getStatus().equalsIgnoreCase(GameJoinData.STATUS.ACCEPTED.toString())) {
				request.getSession().setAttribute("GameJoinData", resData);
				resData.setStatus(GameJoinData.STATUS.WAITING_FOR_START.toString());
				request.getRequestDispatcher("myapp/jsps/gamepage.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("GameJoinData", resData);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
	}

}

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

import com.liminal.model.GameHostingData;

/**
 * Servlet implementation class StopHostedGame
 */
@WebServlet("/StopHostedGame")
public class StopHostedGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StopHostedGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GameHostingData gameHostingData = (GameHostingData)request.getSession().getAttribute("HostedGame");
		String target = request.getParameter("serviceUrl");
		target += "game/stop";
		
		Client client = ClientBuilder.newClient();
		Response rs = client.target(target).request().post(Entity.json(gameHostingData));
		
		if (rs.getStatus() == 204) {
			HttpSession sess = request.getSession();
			sess.removeAttribute("HostedGame");
			response.sendRedirect(request.getContextPath() + "/myapp/jsps/loggedin.jsp");
		}
	}

}

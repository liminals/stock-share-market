package com.liminal.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.liminal.dao.StockDAO;
import com.liminal.model.Stock;

@Path("/stock")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockService {
	
	StockDAO dao = new StockDAO();
	
	@GET
	@Path("/all")
	public List<Stock> getAll(){
		System.out.println("getAll stocks called!");
		return dao.getAll();
	}
	
	@POST
	@Path("/updateprice")
	public Stock updatePrice(Stock s) {
		return dao.updatePrice(s);
	}
}

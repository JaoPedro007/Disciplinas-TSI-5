package br.edu.utfpr.td.tsi.delegacia.endpoint;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("bo")
public class BO {
	
	@GET
	public Response boletimGet() {
		return Response.ok("Boletim de ocorrencia").build();
	}

}

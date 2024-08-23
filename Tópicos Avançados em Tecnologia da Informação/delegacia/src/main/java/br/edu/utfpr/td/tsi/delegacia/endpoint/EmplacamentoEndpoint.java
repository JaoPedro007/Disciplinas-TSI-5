package br.edu.utfpr.td.tsi.delegacia.endpoint;

import br.edu.utfpr.td.tsi.delegacia.modelo.Emplacamento;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("emplacamento")
public class EmplacamentoEndpoint {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response emplacamentoGet() {
		
		Emplacamento e = new Emplacamento();
		e.setPlaca("AXJ-9242");
		e.setCidade("Toledo");
		e.setEstado("PR");
		
		return Response.ok(e).build();

	}
	
	@POST
	@Path("emplacamento")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response emplacamentoPost(Emplacamento emplacamento) {
		return Response.ok("Emplacamento cadastrado com sucesso").build();	
	}

}

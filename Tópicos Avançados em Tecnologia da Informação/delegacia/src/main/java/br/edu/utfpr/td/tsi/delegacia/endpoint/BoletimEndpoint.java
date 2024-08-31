package br.edu.utfpr.td.tsi.delegacia.endpoint;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.service.IBoletimService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Component
@Path("boletim")
public class BoletimEndpoint {

	@Autowired
	IBoletimService boletimService;
	
	@QueryParam("id")
	private String id;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBoletim(
	    @QueryParam("identificador") String identificadorFiltro,
	    @QueryParam("cidade") String cidadeFiltro,
	    @QueryParam("periodo") String periodoFiltro,
	    @QueryParam("page") @DefaultValue("0") int page,
	    @QueryParam("size") @DefaultValue("10") int size) {

	    try {
	        List<BoletimFurtoVeiculo> boletins;
	            boletins = boletimService.listarComFiltros(identificadorFiltro, cidadeFiltro, periodoFiltro, page, size);

	        return Response.ok(boletins).build();
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Erro ao buscar boletins: " + e.getMessage()).build();
	    }
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postBoletim(BoletimFurtoVeiculo b) {
		try {
			boletimService.registrar(b);
			return Response.status(Response.Status.CREATED).entity(b.getIdentificador()).build();

		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.BAD_REQUEST).entity(iae.getMessage())
					.build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao registrar o boletim: " + e.getMessage()).build();
		}
	}
	
	
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putBoletim(BoletimFurtoVeiculo b) {
        try {
                b.setIdentificador(id);
                boletimService.editar(b);
                return Response.status(Response.Status.OK).entity("Boletim atualizado com sucesso").build();
                
        } catch (IllegalArgumentException iae) {
            return Response.status(Response.Status.BAD_REQUEST).entity(iae.getMessage())
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar o boletim: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBoletim() {
        try {
        	boletimService.excluir(id);
            return Response.status(Response.Status.OK).entity("Boletim removido com sucesso")
                           .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover o boletim: " + e.getMessage()).build();
        }
    }

	
	


}

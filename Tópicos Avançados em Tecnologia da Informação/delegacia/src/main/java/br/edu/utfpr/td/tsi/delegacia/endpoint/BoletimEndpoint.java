package br.edu.utfpr.td.tsi.delegacia.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.service.IBoletimService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
	
    @QueryParam("identificador")
    private String identificadorFiltro;

    @QueryParam("cidade")
    private String cidadeFiltro;

    @QueryParam("periodo")
    private String periodoFiltro;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoletim() {
        try {
            if (id != null && !id.isEmpty()) {
                BoletimFurtoVeiculo boletim = boletimService.listar(id);
                if (boletim != null) {
                    return Response.ok(boletim).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Boletim não encontrado").build();
                }
            } else {
                List<BoletimFurtoVeiculo> boletins = boletimService.listarComFiltros(identificadorFiltro, cidadeFiltro, periodoFiltro);
                return Response.ok(boletins).build();
            }
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
			return Response.status(Response.Status.BAD_REQUEST).entity("Falha na validação: " + iae.getMessage())
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
            return Response.status(Response.Status.BAD_REQUEST).entity("Falha na validação: " + iae.getMessage())
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar o boletim: " + e.getMessage()).build();
        }
    }

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteBoletim(){
	    try {
	        System.out.println("ID do boletim recebido: " + id);
	        
	        boolean removido = boletimService.excluir(id);
	        if (removido) {
	            return Response.status(Response.Status.OK).entity("Boletim removido com sucesso").build();
	        } else {
	            System.out.println("Boletim não encontrado para o ID: " + id);
	            return Response.status(Response.Status.NOT_FOUND).entity("Boletim não encontrado").build();
	        }
	    } catch (Exception e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Erro ao remover o boletim: " + e.getMessage()).build();
	    }
	}


}

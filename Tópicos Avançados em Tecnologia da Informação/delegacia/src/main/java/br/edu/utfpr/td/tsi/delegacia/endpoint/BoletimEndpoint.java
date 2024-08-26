package br.edu.utfpr.td.tsi.delegacia.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.service.IBoletimService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Component
@Path("boletim")
public class BoletimEndpoint {

	@Autowired
	IBoletimService boletimService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBoletim() {
		return Response.ok(boletimService.listarTodos()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postBoletim(BoletimFurtoVeiculo b) {
		try {
			boletimService.registrar(b);
			return Response.status(Response.Status.CREATED).entity("Boletim registrado com sucesso").build();

		} catch (IllegalArgumentException iae) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Falha na validação: " + iae.getMessage())
					.build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao registrar o boletim: " + e.getMessage()).build();
		}
	}

}

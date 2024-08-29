package br.edu.utfpr.td.tsi.delegacia.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;
import br.edu.utfpr.td.tsi.delegacia.service.IBoletimService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Component
@Path("veiculo")
public class VeiculoEndpoint {

    @Autowired
    private IBoletimService boletimService;
    
    @QueryParam("placa")
    private String placaFiltro;

    @QueryParam("cor")
    private String corFiltro;

    @QueryParam("tipo")
    private String tipoFiltro;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVeiculos() {
    	System.out.println("Fui chamado: " + placaFiltro + corFiltro + tipoFiltro );
    	
        List<Veiculo> veiculos = boletimService.listarVeiculosComFiltros(placaFiltro, corFiltro, tipoFiltro);
        if (veiculos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nenhum veículo encontrado").build();
        } else {
            return Response.ok(veiculos).build();
        }
    }
}
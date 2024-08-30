package br.edu.utfpr.td.tsi.delegacia.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;
import br.edu.utfpr.td.tsi.delegacia.service.IVeiculoService;
import jakarta.ws.rs.DefaultValue;
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
    private IVeiculoService veiculoService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoletim(@QueryParam("placa") String placaFiltro,
                               @QueryParam("cor") String corFiltro,
                               @QueryParam("tipo") String tipoFiltro,
                               @QueryParam("page") @DefaultValue("0") int page,
                               @QueryParam("size") @DefaultValue("10") int size) {
        try {
            List<Veiculo> veiculos = veiculoService.listarVeiculosComFiltros(placaFiltro, corFiltro, tipoFiltro, page, size);
            return Response.ok(veiculos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar boletins: " + e.getMessage()).build();
        }
    }

}
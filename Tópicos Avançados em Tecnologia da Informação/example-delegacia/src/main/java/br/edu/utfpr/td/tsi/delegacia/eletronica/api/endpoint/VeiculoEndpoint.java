package br.edu.utfpr.td.tsi.delegacia.eletronica.api.endpoint;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.regras.RegrasVeiculo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("veiculos")
public class VeiculoEndpoint {
   @Autowired
   private RegrasVeiculo regrasVeiculo;

   @GET
   @Produces({"application/json"})
   public Response listarTodosVeiculosRoubados(@QueryParam("placa") String placa, @QueryParam("cor") String cor, @QueryParam("tipo") String tipo) {
      List<Veiculo> veiculosSelecionados = new ArrayList();
      if (placa != null) {
         return Response.ok(this.regrasVeiculo.procurarPorPlaca(placa)).build();
      } else {
         if (cor == null && tipo == null) {
            veiculosSelecionados = this.regrasVeiculo.listarTodos();
         } else if (cor != null && tipo == null) {
            veiculosSelecionados = this.regrasVeiculo.procurarPorCor(cor);
         } else if (cor == null && tipo != null) {
            veiculosSelecionados = this.regrasVeiculo.procurarPorTipo(tipo);
         } else if (cor != null && tipo != null) {
            List<Veiculo> selecionados = new ArrayList();
            List<Veiculo> corDesejada = this.regrasVeiculo.procurarPorCor(cor);
            Iterator var7 = corDesejada.iterator();

            while(var7.hasNext()) {
               Veiculo veiculo = (Veiculo)var7.next();
               if (veiculo.getTipoVeiculo().equalsIgnoreCase(tipo)) {
                  selecionados.add(veiculo);
               }
            }

            veiculosSelecionados = selecionados;
         }

         return ((List)veiculosSelecionados).size() > 0 ? Response.ok(veiculosSelecionados).build() : Response.status(Status.NOT_FOUND).build();
      }
   }
}

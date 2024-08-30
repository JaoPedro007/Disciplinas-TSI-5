package br.edu.utfpr.td.tsi.delegacia.eletronica.api.endpoint;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.regras.RegrasBoletimFurtoVeiculo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
@Path("bo-furto-veiculo")
public class BoletimOcorrenciaVeiculoFurtadoEndpoint {
   @Autowired
   private RegrasBoletimFurtoVeiculo regrasBoletimFurtoVeiculo;

   @GET
   @Produces({"application/json"})
   public Response listarTodosBos(@QueryParam("identificador") String identificador, @QueryParam("cidade") String cidade, @QueryParam("periodo") String periodo) {
      List<BoletimFurtoVeiculo> bosSelecionados = new ArrayList();
      if (identificador != null) {
         BoletimFurtoVeiculo boletim = this.regrasBoletimFurtoVeiculo.procurarPorIdentificador(identificador);
         return boletim == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(boletim).build();
      } else {
         if (cidade == null && periodo == null) {
            bosSelecionados = this.regrasBoletimFurtoVeiculo.listarTodos();
         } else if (cidade != null && periodo == null) {
            bosSelecionados = this.regrasBoletimFurtoVeiculo.procurarPorCidade(cidade);
         } else if (cidade == null && periodo != null) {
            bosSelecionados = this.regrasBoletimFurtoVeiculo.procurarPorPeriodo(periodo);
         } else if (cidade != null && periodo != null) {
            List<BoletimFurtoVeiculo> cidadeDesejada = this.regrasBoletimFurtoVeiculo.procurarPorCidade(cidade);
            Iterator var6 = cidadeDesejada.iterator();

            while(var6.hasNext()) {
               BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var6.next();
               if (boletimFurtoVeiculo.getPeriodoOcorrencia().equalsIgnoreCase(periodo)) {
                  ((List)bosSelecionados).add(boletimFurtoVeiculo);
               }
            }
         }

         return ((List)bosSelecionados).size() > 0 ? Response.ok(bosSelecionados).build() : Response.status(Status.NOT_FOUND).build();
      }
   }

   @POST
   @Produces({"application/json"})
   @Consumes({"application/json"})
   public Response criar(BoletimFurtoVeiculo bo) {
      this.regrasBoletimFurtoVeiculo.persistir(bo);
      return Response.ok(bo).build();
   }

   @PUT
   @Produces({"application/json"})
   @Consumes({"application/json"})
   public Response editar(BoletimFurtoVeiculo bo) {
      this.regrasBoletimFurtoVeiculo.persistir(bo);
      return Response.ok(bo).build();
   }

   @DELETE
   public Response remover(@QueryParam("identificador") String identificador) {
      this.regrasBoletimFurtoVeiculo.excluir(identificador);
      return Response.ok().build();
   }
}

package br.edu.utfpr.td.tsi.delegacia.eletronica.api;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.endpoint.BoletimOcorrenciaVeiculoFurtadoEndpoint;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.endpoint.VeiculoEndpoint;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/delegacia")
public class JerseyConfiguration extends ResourceConfig {
   public JerseyConfiguration() {
      this.register(VeiculoEndpoint.class);
      this.register(BoletimOcorrenciaVeiculoFurtadoEndpoint.class);
      this.register(CorsInterceptor.class);
   }
}

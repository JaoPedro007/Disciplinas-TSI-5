package br.edu.utfpr.td.tsi.delegacia;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.endpoint.BoletimEndpoint;
import br.edu.utfpr.td.tsi.delegacia.endpoint.VeiculoEndpoint;
import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig{
	public JerseyConfiguration() {
		register(BoletimEndpoint.class);
		register(VeiculoEndpoint.class);
	}
}

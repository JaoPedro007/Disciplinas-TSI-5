package br.edu.utfpr.td.tsi.delegacia.eletronica.api;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Provider
public class CorsInterceptor implements ContainerResponseFilter {
   private final Integer corsPreflightMaxAgeInSeconds = 1800;

   public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
      responseContext.getHeaders().add("Access-Control-Allow-Origin", requestContext.getHeaderString("origin"));
      responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
      responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
      List<String> allowedHeaders = (List)requestContext.getHeaders().get("Access-Control-Request-Headers");
      if (allowedHeaders != null) {
         Iterator var4 = allowedHeaders.iterator();

         while(var4.hasNext()) {
            String allowedHeader = (String)var4.next();
            responseContext.getHeaders().add("Access-Control-Allow-Headers", allowedHeader);
         }
      }

      responseContext.getHeaders().add("Access-Control-Max-Age", this.corsPreflightMaxAgeInSeconds);
   }
}

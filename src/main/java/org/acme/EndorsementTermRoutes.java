package org.acme;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.*;

import org.apache.camel.builder.RouteBuilder;

public class EndorsementTermRoutes extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    execute();
  }

  public void execute() {
    from(file("./files/input/"))
        .log("Processing file ${in.headers.CamelFilePath}.")
        .process("endorsementTermAppendProcessor")
        .to(file("./files/output/"))
        .log("Processed file ${in.headers.CamelFilePath}.");
  }
}

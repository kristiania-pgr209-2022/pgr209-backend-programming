# Deploy Jetty server to Azure

1. [x] Running Jetty server - serve static content
2. JAX-RS endpoint `@Path` and `@GET` and `@POST`
   1. `@Inject` BookRepository
   2. `@Produces(application/json)`  automatic conversion
3. Executable jar-file `java -jar kristiania-library.jar`
4. `azure-webapp-maven-plugin` deploys on Azure

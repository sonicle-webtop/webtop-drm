package com.sonicle.webtop.drm.swagger.v2.api;

import com.sonicle.webtop.drm.swagger.v2.model.ApiEmployee;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/employee")
@Api(description = "the employee API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-06-17T16:01:28.552+02:00[Europe/Rome]")
public abstract class EmployeeApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Get Employee Profile", notes = "Get Employee Profile data", response = ApiEmployee.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiEmployee.class)
    })
    public Response getEmployeeProfile() {
        return Response.ok().entity("magic!").build();
    }
}

package com.sonicle.webtop.drm.swagger.v2.api;

import com.sonicle.webtop.drm.swagger.v2.model.ApiCompaniesResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/companies")
@Api(description = "the companies API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-02-23T11:21:10.810+01:00[Europe/Rome]")
public abstract class CompaniesApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "List Companies", notes = "List Companies of a specific user", response = ApiCompaniesResult.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiCompaniesResult.class)
    })
    public Response listCompanies(@QueryParam("userId")   String userId) {
        return Response.ok().entity("magic!").build();
    }
}

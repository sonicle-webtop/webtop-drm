package com.sonicle.webtop.drm.swagger.v2.api;

import com.sonicle.webtop.drm.swagger.v2.model.ApiManagersResult;
import com.sonicle.webtop.drm.swagger.v2.model.ApiUsersResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/managers")
@Api(description = "the managers API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-06-17T16:01:28.552+02:00[Europe/Rome]")
public abstract class ManagersApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @GET
    @Path("/users")
    @Produces({ "application/json" })
    @ApiOperation(value = "List managed Users by a Manager", notes = "List managed Users by a Manager", response = ApiUsersResult.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiUsersResult.class)
    })
    public Response listManagedUsers() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "List Managers", notes = "List Managers of a specific user", response = ApiManagersResult.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiManagersResult.class)
    })
    public Response listManagers(@QueryParam("userId")   String userId) {
        return Response.ok().entity("magic!").build();
    }
}

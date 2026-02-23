package com.sonicle.webtop.drm.swagger.v2.api;

import com.sonicle.webtop.drm.swagger.v2.model.ApiLeaveRequest;
import com.sonicle.webtop.drm.swagger.v2.model.ApiLeaveRequestsResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/leaveRequests")
@Api(description = "the leaveRequests API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-02-23T11:21:10.810+01:00[Europe/Rome]")
public abstract class LeaveRequestsApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @POST
    @Consumes({ "application/json" })
    @ApiOperation(value = "Add new Leave Requesst", notes = "Add a new Leave Request", response = Void.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class)
    })
    public Response addLeaveRequest(@Valid ApiLeaveRequest apiLeaveRequest) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @ApiOperation(value = "Delete Leave Request", notes = "Delete a Leave Request", response = Void.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class)
    })
    public Response deleteLeaveRequest(@QueryParam("leaveRequestId")   Integer leaveRequestId,@QueryParam("action")   String action,@QueryParam("text")   String text) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "List Leave Requests", notes = "List Leave Requests", response = ApiLeaveRequestsResult.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiLeaveRequestsResult.class)
    })
    public Response listLeaveRequests(@QueryParam("userId")   String userId,@QueryParam("startDate")   String startDate,@QueryParam("leaveRequestId")   Integer leaveRequestId) {
        return Response.ok().entity("magic!").build();
    }

    @PUT
    @Consumes({ "application/json" })
    @ApiOperation(value = "Update Leave Request", notes = "Update Leave Request", response = Void.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class)
    })
    public Response updateLeaveRequests(@Valid ApiLeaveRequest apiLeaveRequest) {
        return Response.ok().entity("magic!").build();
    }
}

package com.sonicle.webtop.drm.swagger.v2.api;

import com.sonicle.webtop.drm.swagger.v2.model.ApiTimetableEntriesResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/timetable")
@Api(description = "the timetable API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-02-13T16:27:04.844+01:00[Europe/Rome]")
public abstract class TimetableApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "List Timetable Entries", notes = "List timetable entries for a specific month / year", response = ApiTimetableEntriesResult.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={ "me" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ApiTimetableEntriesResult.class)
    })
    public Response listTimetableEntries(@QueryParam("operatorId")  @ApiParam("Effective user")  String operatorId,@QueryParam("year")   Integer year,@QueryParam("month")   Integer month) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @ApiOperation(value = "Punch", notes = "Register a Punch (Clock-in / Clock-out automatic)", response = Void.class, authorizations = {
        
        @Authorization(value = "auth-bearer"),
        
        @Authorization(value = "auth-apikey-username"),
        
        @Authorization(value = "auth-apikey-bearer"),
        
        @Authorization(value = "auth-basic")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class)
    })
    public Response punch(@QueryParam("type")  @ApiParam("M &#x3D; Manual , S &#x3D; Special")  String type,@QueryParam("location")  @ApiParam("O &#x3D; Office, S &#x3D; Smart, A &#x3D; App")  String location) {
        return Response.ok().entity("magic!").build();
    }
}

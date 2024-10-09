package com.sonicle.webtop.drm.swagger.v1.api;

import com.sonicle.webtop.drm.swagger.v1.model.ApiTimetableEntry;
import java.util.List;

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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2024-10-07T15:48:23.458+02:00[Europe/Berlin]")
public abstract class TimetableApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Adds new timetable entries", notes = "Add an array of timetable entries", response = Object.class, authorizations = {
        
        @Authorization(value = "Basic authentication")
         }, tags={ "timetable" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = Object.class)
    })
    public Response addTimetableEntries(@Valid @NotNull List<ApiTimetableEntry> body) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @ApiOperation(value = "", notes = "Delete a range of timetable entries", response = Void.class, authorizations = {
        
        @Authorization(value = "Basic authentication")
         }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Void.class)
    })
    public Response deleteTimetableEntries(@QueryParam("fromDate") @NotNull  @ApiParam("From entrance/exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)")  String fromDate,@QueryParam("toDate") @NotNull  @ApiParam("To entrance/exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)   ")  String toDate,@QueryParam("employeeIds")  @ApiParam("Employee id (null or empty array for all employees)")  List<String> employeeIds) {
        return Response.ok().entity("magic!").build();
    }
}

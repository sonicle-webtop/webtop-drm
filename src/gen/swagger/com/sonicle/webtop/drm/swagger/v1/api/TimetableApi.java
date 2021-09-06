package com.sonicle.webtop.drm.swagger.v1.api;

import java.util.List;
import com.sonicle.webtop.drm.swagger.v1.model.TimetableEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/timetable")
@Api(description = "the timetable API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2021-09-06T09:07:11.625+02:00")
public abstract class TimetableApi extends com.sonicle.webtop.core.sdk.BaseRestApiResource {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Adds new timetable entries", notes = "Add an array of timetable entries", response = Object.class, authorizations = {
        @Authorization(value = "Basic authentication")
    }, tags={ "timetable",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Success", response = Object.class) })
    public Response addTimetableEntries(@Valid List<TimetableEntry> body) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @ApiOperation(value = "", notes = "Delete a range of timetable entries", response = Void.class, authorizations = {
        @Authorization(value = "Basic authentication")
    }, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Void.class) })
    public Response deleteTimetableEntries(@QueryParam("fromDate") @NotNull   @ApiParam("From entrance/exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)")  String fromDate,@QueryParam("toDate") @NotNull   @ApiParam("To entrance/exit date/time (ISO date/time YYYYMMDD&#39;T&#39;HHMMSS&#39;Z&#39;)   ")  String toDate,@QueryParam("employeeIds")   @ApiParam("Employee id (null or empty array for all employees)")  List<String> employeeIds) {
        return Response.ok().entity("magic!").build();
    }
}

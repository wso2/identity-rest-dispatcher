/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.rest.api.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.carbon.identity.rest.api.sample.dto.*;
import org.wso2.carbon.identity.rest.api.sample.EchoApiService;
import org.wso2.carbon.identity.rest.api.sample.factories.EchoApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.identity.rest.api.sample.dto.ErrorDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/echo")
@io.swagger.annotations.Api(value = "/echo", description = "the echo API")
public class EchoApi  {

    @Autowired
    private EchoApiService delegate;

    @Valid
    @GET
    @Path("/{message}")
    @Produces({ "application/text" })
    @io.swagger.annotations.ApiOperation(value = "Echo back the query string",
            notes = "Echo back the path string",
            response = String.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully retrieved the request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Resource Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error") })

    public Response echo(@ApiParam(value = "Message",required=true ) @PathParam("message")  String message) {

        return delegate.echo(message);
    }

}

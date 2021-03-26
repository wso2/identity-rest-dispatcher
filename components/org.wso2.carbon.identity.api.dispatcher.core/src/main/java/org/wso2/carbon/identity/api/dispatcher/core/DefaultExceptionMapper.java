/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.api.dispatcher.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.api.server.common.error.ErrorDTO;
import org.wso2.carbon.identity.api.server.common.error.ErrorResponse;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Handles all the unhandled server errors, (ex:NullPointer).
 * Sends a default error response.
 */
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Log log = LogFactory.getLog(DefaultExceptionMapper.class);
    public static final String PROCESSING_ERROR_CODE = "SE-50000";
    public static final String PROCESSING_ERROR_MESSAGE = "Unexpected Processing Error.";
    public static final String PROCESSING_ERROR_DESCRIPTION = "Server encountered an error while serving the request.";

    @Override
    public Response toResponse(Throwable e) {

        log.error("Server encountered an error while serving the request.", e);
        ErrorDTO errorDTO = new ErrorResponse.Builder().withCode(PROCESSING_ERROR_CODE)
                .withMessage(PROCESSING_ERROR_MESSAGE)
                .withDescription(PROCESSING_ERROR_DESCRIPTION).build();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
    }
}

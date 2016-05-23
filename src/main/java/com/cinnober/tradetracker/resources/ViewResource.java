/*
 * Copyright (c) 2013 Cinnober Financial Technology AB, Stockholm,
 * Sweden. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Cinnober Financial Technology AB, Stockholm, Sweden. You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Cinnober.
 *
 * Cinnober makes no representations or warranties about the suitability
 * of the software, either expressed or implied, including, but not limited
 * to, the implied warranties of merchantibility, fitness for a particular
 * purpose, or non-infringement. Cinnober shall not be liable for any
 * damages suffered by licensee as a result of using, modifying, or
 * distributing this software or its derivatives.
 */
package com.cinnober.tradetracker.resources;

import com.google.common.base.Charsets;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/views")
public class ViewResource {
    @GET
    @Produces("text/html;charset=UTF-8")
    @Path("/utf8.ftl")
    public View freemarkerUTF8() {
        return new View("/views/ftl/utf8.ftl", Charsets.UTF_8) {
        };
    }

    @GET
    @Produces("text/html;charset=ISO-8859-1")
    @Path("/iso88591.ftl")
    public View freemarkerISO88591() {
        return new View("/views/ftl/iso88591.ftl", Charsets.ISO_8859_1) {
        };
    }

    @GET
    @Produces("text/html;charset=UTF-8")
    @Path("/utf8.mustache")
    public View mustacheUTF8() {
        return new View("/views/mustache/utf8.mustache", Charsets.UTF_8) {
        };
    }

    @GET
    @Produces("text/html;charset=ISO-8859-1")
    @Path("/iso88591.mustache")
    public View mustacheISO88591() {
        return new View("/views/mustache/iso88591.mustache", Charsets.ISO_8859_1) {
        };
    }
}

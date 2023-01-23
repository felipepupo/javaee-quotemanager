package com.example.resources;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.example.resources.json.JsonQuote;
import com.example.services.QuoteService;

@Path("quote")
@RequestScoped
public class QuoteResource {
    @Inject
    private QuoteService quoteService;

    @GET
    @Path("{id}")
    public JsonQuote findById(@PathParam("id") final long id) {
        return quoteService.findById(id) // delegation to the business layer
                .map(quote -> { // the model conversion
                    final JsonQuote json = new JsonQuote();
                    json.setId(quote.getId());
                    json.setName(quote.getName());
                    json.setValue(quote.getValue());
                    json.setCustomerCount(
                        Optional.ofNullable(quote.getCustomers()).map(Collection::size).orElse(0)
                    );
                    
                    return json;
                })
                .orElseThrow(() -> new WebApplicationException(Response.Status.NO_CONTENT));
    }
}
package com.photo.rest;

import com.photo.model.user.User;
import com.photo.security.PasswordHasher;
import com.photo.security.SignupRequest;
import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response signup(SignupRequest request) {
        Log.infof("Signup request: %s", request.toString());

        if (User.find("name", request.getUsername()).firstResult() != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Username già esistente")
                    .build();
        }

        User user = new User();
        user.setName(request.getUsername());
        user.setPassword(PasswordHasher.hashPassword(request.getPassword()));

        user.persist();

        return Response.status(Response.Status.CREATED).build();
    }
}

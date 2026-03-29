package com.photo.rest;

import com.photo.AppConfig;
import com.photo.exception.MimeTypeNotSupportedException;
import com.photo.model.FindAllPhotosDTO;
import com.photo.model.Outcome;
import com.photo.model.Photo;
import com.photo.model.PhotoUploadForm;
import com.photo.model.user.User;
import com.photo.repository.UserRepository;
import com.photo.response.PhotoResponse;
import com.photo.service.PhotoService;
import com.photo.utils.FileUtils;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.File;
import java.util.*;


@Path("/photos")
public class PhotoResource {

    @Inject
    AppConfig config;
    @Inject
    UserRepository userRepository;
    @Inject
    PhotoService photoService;

    @POST
    @Path("/upload")
    @RunOnVirtualThread
    public Response upload(@RestForm("files") List<FileUpload> files,
                           @RestForm("userId") String userId) {

        Log.infof("Received upload photo request of %s files with userId %s", files.size(), userId);

        if(files.isEmpty()) {
            return Response.serverError().entity("Zero foto caricate").build();
        }

        if(files.size() > config.maxUploadNumber()) {
            return Response.serverError().entity("Puoi caricare al massimo 10 foto alla volta").build();
        }

        User user = userRepository.findById(Long.parseLong(userId));

        String tempPath = config.downloadDir();

        List<PhotoUploadForm> uploadedPhotos = new ArrayList<>();

        try {
            uploadedPhotos = FileUtils.getRawData(files, tempPath, user);

        } catch (MimeTypeNotSupportedException e) {
            Log.errorf(e.getMessage());
            return Response.serverError().entity("Una o più foto non hanno un formato riconosciuto").build();
        }

        Log.infof("Uploaded Photos: %s", Arrays.toString(uploadedPhotos.toArray()));

        Map<String, Outcome> outcomes = new HashMap<>();

        outcomes = photoService.analyzeAndPersistPhotos(uploadedPhotos, user.getName());

        List<String> notPersistedPhotos = outcomes.entrySet().stream()
                .filter(entry -> entry.getValue().equals(Outcome.FAILURE))
                .map(Map.Entry::getKey)
                .toList();

        if(!notPersistedPhotos.isEmpty()) {
            String notPersistedPhotoDetails = Arrays.toString(notPersistedPhotos.toArray());
            Log.infof("Not persisted photos: %s", notPersistedPhotoDetails);
            return Response.ok().entity(
                    "Le seguenti foto non sono state salvate: " + notPersistedPhotoDetails).build();
        }

        Log.infof("All photos were persisted %s", outcomes);

        return Response.ok().entity("Tutte le foto sono state salvate").build();
    }

    @GET
    @Path("/all")
    @RunOnVirtualThread
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPhotos(@QueryParam("page") @DefaultValue("0") int page,
                                 @QueryParam("size") @DefaultValue("10") int size) {

        Log.infof("Received get all photos request");

        FindAllPhotosDTO photosDTO = photoService.getAllPhotos(page, size);

        if (photosDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No photos found").build();
        }

        PhotoResponse photoResponse = new PhotoResponse(photosDTO);

        return Response.ok().entity(photoResponse).build();
    }

    @GET
    @Path("/{id}")
    @RunOnVirtualThread
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getPhoto(@PathParam("id") long id) {

        Log.infof("Received get photo request");

        Photo photo = photoService.findById(id);

        if (photo == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Photo not found with id %s" + id).build();
        }

        File file = new File(photo.getStoragePath());

        Log.infof("Returning binary content from %s", file.getAbsolutePath());

        return Response.ok()
                .header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"")
                .header("Content-Length", file.length())
                .entity(file)
                .build();
    }

    @DELETE
    @Path("/delete/{id}")
    @RunOnVirtualThread
    public Response deletePhoto(@PathParam("id") long id) {

        Log.infof("Received delete photo request");

        Outcome outcome = photoService.delete(id);

        if(outcome.equals(Outcome.SUCCESS)) {
            return Response.ok().entity("Photo deleted with id " + id).build();
        }

        return Response.serverError().entity("Could not delete photo with id " + id).build();
    }

    @PUT
    @Path("/{id}")
    @RunOnVirtualThread
    public Response updatePhoto(@PathParam("id") long id, Photo photo) {

        Log.infof("Received update photo request");

        Outcome outcome = photoService.updateMetadata(photo, id);

        if(outcome.equals(Outcome.SUCCESS)) {
            return Response.ok().entity("Photo metadata updated").build();
        }

        return Response.serverError().entity("Could not update photo metadata with id " + id).build();
    }
}
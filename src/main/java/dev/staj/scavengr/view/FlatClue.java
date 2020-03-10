package dev.staj.scavengr.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.staj.scavengr.model.entity.Organizer;
import java.net.URI;
import java.util.UUID;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "clue_name", "hunt", "media", "media_tag","hunt_order"})

public interface FlatClue {


    @NonNull
    UUID getId();

    @NonNull
    String getClueName();

    @NonNull
    String getMedia();

    @NonNull
    String getMediaTag();

    @NonNull
    Integer getHuntOrder();

    @NonNull
    URI getHref();

  }



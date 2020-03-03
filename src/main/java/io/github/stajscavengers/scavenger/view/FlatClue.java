package io.github.stajscavengers.scavenger.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.stajscavengers.scavenger.model.entity.Organizer;
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
    Organizer getHunt();

    @NonNull
    Object getMedia();

    @NonNull
    Object getMediaTag();

    @NonNull
    Integer getHunterOrder();

    @NonNull
    URI getHref();

  }



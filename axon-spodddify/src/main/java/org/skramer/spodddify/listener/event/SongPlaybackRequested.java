package org.skramer.spodddify.listener.event;

import lombok.Value;

@Value
public class SongPlaybackRequested {
    String songTitle;
}

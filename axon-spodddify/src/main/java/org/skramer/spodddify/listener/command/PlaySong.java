package org.skramer.spodddify.listener.command;

import javax.persistence.Id;

import lombok.Value;

@Value
public
class PlaySong {
    @Id
    String listenerId;
    String songTitle;
}

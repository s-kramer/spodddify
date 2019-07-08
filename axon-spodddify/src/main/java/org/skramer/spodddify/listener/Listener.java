package org.skramer.spodddify.listener;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateRoot;
import org.axonframework.spring.stereotype.Aggregate;
import org.skramer.spodddify.listener.command.CreateListener;
import org.skramer.spodddify.listener.command.PlaySong;
import org.skramer.spodddify.listener.event.ListenerCreated;
import org.skramer.spodddify.listener.event.SongPlaybackRequested;
import org.skramer.spodddify.payment.event.PaymentMissing;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
class Listener {
    @AggregateIdentifier
    private String listenerId;
    private ListenerStatus listenerStatus;

    @CommandHandler
    public Listener(CreateListener cmd) {
        log.info("Creating listener");
        AggregateLifecycle.apply(new ListenerCreated(UUID.randomUUID().toString(), ListenerStatus.ACTIVE, cmd.getPaymentPlan()));
    }

    @EventHandler
    void on(ListenerCreated evt) {
        listenerId = evt.getListenerId();
        listenerStatus = evt.getListenerStatus();
        log.info("Created listener {} with status {}", listenerId, listenerStatus);
    }

    @CommandHandler
    public void playSong(PlaySong cmd) {
        if (ListenerStatus.ACTIVE != listenerStatus) {
            throw new PaymentRequiredException(listenerId);
        }

        AggregateLifecycle.apply(new SongPlaybackRequested(cmd.getSongTitle()));
    }

    @EventHandler
    void on(SongPlaybackRequested evt) {
        log.info("Playing song {}", evt.getSongTitle());
        // to be implemented one day
    }

    @EventHandler
    void on(PaymentMissing evt) {
        listenerStatus = ListenerStatus.BLOCKED;
    }


}

package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.model.phased.Envelop;
import net.contexx.thoth.core.model.phased.Envelopes;

public class SenderController {

    private final Sender senderImpl;

    public SenderController(Sender senderImpl) {
        this.senderImpl = senderImpl;
    }

    public void send(Envelopes envelopes) {
        //todo
    }

    public void send(Envelop envelop) {
        //todo
    }
}

package net.contexx.thoth.core.model.phased;

import net.contexx.thoth.core.model.phasec.Addressee;
import net.contexx.thoth.core.model.phasec.Document;

import java.util.UUID;

public class Envelop {

    public Envelop(UUID id, Addressee addressee, Letter letter) {
        this.id = id;
        //todo
        this.addressee = addressee;
        this.letter = letter;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // id

    private final UUID id;

    public UUID getId() {
        return id;
    }

    //_____________________________________________________
    // document

    private final Addressee addressee; //todo is this not duplicate to letter.document?

    public Addressee getAddressee() {
        return addressee;
    }

    //_____________________________________________________
    // letter
    private final Letter letter;

    public Letter getLetter() {
        return letter;
    }
}

package net.contexx.thoth.core.model.phasec;

import net.contexx.thoth.core.model.common.Sticky;
import net.contexx.thoth.core.model.phasea.Symbol;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.unmodifiableSet;

public class Folder extends Sticky {

    public Folder(Symbol symbol) {
        this.uuid = UUID.randomUUID();
        this.symbol = symbol;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // uuid

    private final UUID uuid; //todo Sollte das nicht executionIdentifier heißen?

    public UUID getUUID() {
        return null;
    }

    //_____________________________________________________
    // symbol

    private final Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    //_____________________________________________________
    // document

    private final Set<Document> documents = new HashSet<>();

    public Document add(Document document) {
        assert document.getFolder() != this : "Document '"+document.getName()+"' already added.";

        if(document.getFolder() != null && document.getFolder() != this) throw new RuntimeException("TODO"); //todo Write thewException message

        document.setFolder(this);
        documents.add(document);

        return document;
    }

    public Set<Document> getDocuments() {
        return unmodifiableSet(documents);
    }
}

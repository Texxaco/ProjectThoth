package net.contexx.thoth.core.persistence.phacec;

import net.contexx.thoth.core.model.phasec.Folder;

import java.util.UUID;

public interface Storage {
    void store(Folder folder);
    Folder load(UUID executionIdentifier);
}

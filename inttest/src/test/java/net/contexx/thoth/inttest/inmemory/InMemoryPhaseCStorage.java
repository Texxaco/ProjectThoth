package net.contexx.thoth.inttest.inmemory;

import net.contexx.thoth.core.model.phasec.Folder;
import net.contexx.thoth.core.persistence.phacec.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryPhaseCStorage implements Storage {

    private final Map<UUID, Folder> store = new HashMap<>();

    @Override
    public void store(Folder folder) {
        store.put(folder.getUUID(), folder);
    }

    @Override
    public Folder load(UUID executionIdentifier) {
        return store.get(executionIdentifier);
    }
}

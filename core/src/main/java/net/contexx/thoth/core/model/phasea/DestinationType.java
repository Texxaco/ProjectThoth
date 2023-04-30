package net.contexx.thoth.core.model.phasea;


public class DestinationType extends AbstractEntity  {
    private final String name;

    public DestinationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

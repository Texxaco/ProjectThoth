package net.contexx.thoth.core.model.phasea;

import net.contexx.thoth.core.model.common.Sticky;

public class AbstractEntity extends Sticky implements HasDomain {
    private Domain domain;

    @Override
    public net.contexx.thoth.core.model.common.Domain getDomain() {
        return domain;
    }

    void setDomain(net.contexx.thoth.core.model.phasea.Domain domain){
        assert this.domain == null : "Domain should not be overwritten.";
        this.domain = domain;
    }
}

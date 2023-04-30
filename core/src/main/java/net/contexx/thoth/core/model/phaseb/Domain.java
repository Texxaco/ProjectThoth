package net.contexx.thoth.core.model.phaseb;

import net.contexx.thoth.core.model.common.Sticky;

import java.util.HashSet;
import java.util.Set;

public class Domain<E> extends Sticky {

    public Domain(net.contexx.thoth.core.model.phasea.Domain<E> originDomain) {
        if(originDomain == null) throw new RuntimeException("No origin domain was given.");
        this.originDomain = originDomain;
//        this.identifier = identifier;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // origin domain
    private net.contexx.thoth.core.model.phasea.Domain<E> originDomain;

    public String getName(){
        return originDomain.getName();
    }
    public net.contexx.thoth.core.model.phasea.Domain<E> getOriginDomain() {
        return originDomain;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // identifier

//    private UUID identifier;
//
//    public UUID getIdentifier() {
//        return identifier;
//    }



    //_____________________________________________________
    // template

    private Set<Template> templates = new HashSet<>();

    public Set<Template> getTemplates() {
        return templates;
    }

    public Template add(Template template){
        assert template.getDomain() != this : "Template '"+template.getName()+"'/"+template.getIdentifier()+" already added.";

        if(template.getDomain() != null && template.getDomain() != this) throw new RuntimeException("TODO"); //todo Write thewException message

        template.setDomain(this);
        templates.add(template);

        return template;
    }
}

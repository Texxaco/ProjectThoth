package net.contexx.thoth.core.model.phasec;

import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.common.attribute.Attributable;
import net.contexx.thoth.core.model.common.attribute.AttributeValue;

import java.util.Set;

public class Document extends Attributable {
    public Document(Set<AttributeValue<?>> attributeValues, String name, Addressee addressee, RenderEngine.RenderInfo renderInfo) {
        super(attributeValues);
        this.name = name;
        this.addressee = addressee; //assertNotNull
        this.renderInfo = renderInfo; //todo assertNotNull
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // folder

    private Folder folder;
    void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Folder getFolder() {
        return folder;
    }

    //_____________________________________________________
    // name

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //_____________________________________________________
    // addressee

    private Addressee addressee;

    public Addressee getAddressee() {
        return addressee;
    }

    //_____________________________________________________
    // renderInfo

    private RenderEngine.RenderInfo renderInfo;

    public RenderEngine.RenderInfo getRenderInfo() {
        return renderInfo;
    }
}

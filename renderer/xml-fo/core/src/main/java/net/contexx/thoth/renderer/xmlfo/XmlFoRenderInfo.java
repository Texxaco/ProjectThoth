package net.contexx.thoth.renderer.xmlfo;

import net.contexx.thoth.core.controller.RenderEngine;

import static net.contexx.thoth.renderer.xmlfo.XmlFoRenderer.ENGINE_NAME;

public class XmlFoRenderInfo implements RenderEngine.RenderInfo {

    public XmlFoRenderInfo(String templateIdentifier) {
        this.templateIdentifier = templateIdentifier;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //

    @Override
    public String getRendererName() {
        return ENGINE_NAME;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // attributes

    //_____________________________________________________
    // template identifier

    private final String templateIdentifier;

    public String getTemplateIdentifier() {
        return templateIdentifier;
    }
}

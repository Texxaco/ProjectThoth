package net.contexx.thoth.json.phaseb.plugins;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.phasea.Domain;

import java.io.IOException;

public interface RenderEnginePlugin<RI extends RenderEngine.RenderInfo> {
    String getRenderEngineName();

    int getVersion();

    boolean supports(RenderEngine.RenderInfo renderInfo);

    Module getJacksonModule();

    RI load(JsonNode node, Domain<?> domain, int version) throws IOException;
}

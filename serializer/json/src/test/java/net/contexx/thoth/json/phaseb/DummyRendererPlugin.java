package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.phaseb.plugins.RenderEnginePlugin;

import java.io.IOException;

public class DummyRendererPlugin implements RenderEnginePlugin {

    private static final String NAME = "DummyRenderer";

    @Override
    public String getRenderEngineName() {
        return NAME;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public boolean supports(RenderEngine.RenderInfo renderInfo) {
        return renderInfo instanceof DummyRenderInfo;
    }

    @Override
    public Module getJacksonModule() {
        final SimpleModule module = new SimpleModule("json serializer dummy renderer");
        module.addSerializer(new DummyRenderInfoJsonConverter());
        return module;
    }

    @Override
    public RenderEngine.RenderInfo load(JsonNode node, Domain domain, int version) throws IOException {
        return null;
    }

    public static class DummyRenderInfo implements RenderEngine.RenderInfo {
        @Override
        public String getRendererName() {
            return NAME;
        }
    }
}

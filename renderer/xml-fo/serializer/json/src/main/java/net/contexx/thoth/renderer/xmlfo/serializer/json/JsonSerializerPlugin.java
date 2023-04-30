package net.contexx.thoth.renderer.xmlfo.serializer.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.json.phaseb.plugins.RenderEnginePlugin;
import net.contexx.thoth.renderer.xmlfo.XmlFoRenderer;
import net.contexx.thoth.renderer.xmlfo.XmlFoRenderInfo;

import java.io.IOException;

public class JsonSerializerPlugin implements RenderEnginePlugin<XmlFoRenderInfo> {
    private XmlFoRenderInfoJsonConverter ruleXmlFoRenderInfoJsonConverter = new XmlFoRenderInfoJsonConverter();

    @Override
    public String getRenderEngineName() {
        return XmlFoRenderer.ENGINE_NAME;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public boolean supports(RenderEngine.RenderInfo renderInfo) {
        return XmlFoRenderInfo.class.isAssignableFrom(renderInfo.getClass());
    }

    @Override
    public Module getJacksonModule() {
        final SimpleModule module = new SimpleModule("json serializer XML-FO renderer");
        module.addSerializer(ruleXmlFoRenderInfoJsonConverter);
        return module;
    }

    @Override
    public XmlFoRenderInfo load(JsonNode node, Domain<?> domain, int version) throws IOException {
        return ruleXmlFoRenderInfoJsonConverter.load(node, domain, version).orElseThrow();
    }
}

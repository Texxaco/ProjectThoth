package net.contexx.thoth.json.phaseb;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.json.phaseb.plugins.PluginRegister;
import net.contexx.thoth.json.phaseb.plugins.RenderEnginePlugin;

import java.io.IOException;
import java.util.Optional;

public class RenderInfoAdapter {

    private static final String META = "renderEngineMetaData";
    private static final String META_ENGINE_NAME = "engineName";
    private static final String META_DATA_VERSION = "dataVersion";
    private static final String RENDER_INFO = "renderInfo";

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //RuleSetAdapter implementation

    public static void serialize(JsonGenerator gen, RenderEngine.RenderInfo renderInfo) throws IOException {
        if(renderInfo == null) return;

        final RenderEnginePlugin<RenderEngine.RenderInfo> renderEnginePlugin = PluginRegister.findRenderEnginePlugin(renderInfo).orElseThrow(); //todo passende Exception eintragen.

        //todo
        gen.writeObjectFieldStart(META);
            gen.writeStringField(META_ENGINE_NAME, renderEnginePlugin.getRenderEngineName());
            gen.writeNumberField(META_DATA_VERSION, renderEnginePlugin.getVersion());
        gen.writeEndObject();

        gen.writeObjectField(RENDER_INFO, renderInfo);
    }

    public static RenderEngine.RenderInfo load(JsonNode parentNode, net.contexx.thoth.core.model.phasea.Domain<?> domain, String templateName) throws IOException {
        final JsonNode renderInfo = parentNode.get(RENDER_INFO);
        if(renderInfo == null) throw new IOException("No Render Info given for template '"+templateName+"'");

        final JsonNode meta = parentNode.get(META);
        if(meta == null) throw new IOException("Missing renderEngine-Metainformation-Block in:\n"+parentNode.toPrettyString());

        final String renderEngineName = meta.get("engineName").asText(); //todo null prüfung?
        final int renderInfoDataVersion = meta.get("dataVersion").asInt(); //todo null prüfung?

        final Optional<RenderEnginePlugin<RenderEngine.RenderInfo>> renderEnginePlugin = PluginRegister.getRenderEnginePlugin(renderEngineName); //todo passende Exception eintragen.

        if(renderEnginePlugin.isPresent()) {
            return renderEnginePlugin.get().load(renderInfo, domain, renderInfoDataVersion);
        } else throw new IOException(String.format("Render Engine with name '%s' not found.", renderEngineName));
    }
}

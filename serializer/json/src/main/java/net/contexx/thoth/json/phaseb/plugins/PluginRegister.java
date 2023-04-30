package net.contexx.thoth.json.phaseb.plugins;

import com.fasterxml.jackson.databind.Module;
import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.phaseb.RuleSet;

import java.util.*;

public class PluginRegister {


    private PluginRegister() { /*no instanciation, static methods only*/} //todo static only sollte im Hinblick auf Spring Context noch mal hinterfragt werden!

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // RuleEnginePlugin

    private static final Set<RuleEnginePlugin<?>> ruleEnginePlugIns = new LinkedHashSet<>(); //hack; no full generics for RuleEngine due to compiler restrictions
    private static final Map<String, RuleEnginePlugin<?>> ruleEnginePlugInMap = new HashMap<>(); //hack; no full generics for RuleEngine due to compiler restrictions

    @SuppressWarnings("unchecked")
    public static <RS extends RuleSet> Optional<RuleEnginePlugin<RS>> findRuleEnginePlugin(RS ruleSet) {
        return Optional.ofNullable( //hack; the repack of Optional is sadly necessary due to compiler restrictions
                (RuleEnginePlugin<RS>) ruleEnginePlugIns.parallelStream().filter(ruleEnginePlugin -> ruleEnginePlugin.supports(ruleSet)).findFirst().orElse(null)
        );
    }

    @SuppressWarnings("unchecked")
    public static <RS extends RuleSet> Optional<RuleEnginePlugin<RS>> getRuleEnginePlugin(String ruleEngineName) {
        return Optional.ofNullable((RuleEnginePlugin<RS>) ruleEnginePlugInMap.get(ruleEngineName));
    }

    public static void register(RuleEnginePlugin<?> ruleEnginePlugIn) {
        ruleEnginePlugIns.add(ruleEnginePlugIn);
        ruleEnginePlugInMap.put(ruleEnginePlugIn.getRuleEngineName(), ruleEnginePlugIn);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // RenderEngine

    private static final Set<RenderEnginePlugin<?>> renderEnginePlugIns = new LinkedHashSet<>(); //hack; no full generics for RenderEngine due to compiler restrictions
    private static final Map<String, RenderEnginePlugin<?>> renderEnginePlugInMap = new HashMap<>(); //hack; no full generics for RebnderEngine due to compiler restrictions

    @SuppressWarnings("unchecked")
    public static <RI extends RenderEngine.RenderInfo> Optional<RenderEnginePlugin<RI>> findRenderEnginePlugin(RI renderInfo) {
        return Optional.ofNullable( //hack; the repack of Optional is sadly necessary due to compiler restrictions
                (RenderEnginePlugin<RI>) renderEnginePlugIns.parallelStream().filter(renderEnginePlugin -> renderEnginePlugin.supports(renderInfo)).findFirst().orElse(null)
        );
    }

    @SuppressWarnings("unchecked")
    public static <RI extends RenderEngine.RenderInfo> Optional<RenderEnginePlugin<RI>> getRenderEnginePlugin(String renderEngineName) {
        return Optional.ofNullable((RenderEnginePlugin<RI>) renderEnginePlugInMap.get(renderEngineName));
    }

    public static void register(RenderEnginePlugin<?> renderEnginePlugIn) {
        renderEnginePlugIns.add(renderEnginePlugIn);
        renderEnginePlugInMap.put(renderEnginePlugIn.getRenderEngineName(), renderEnginePlugIn);
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // jackson modules


    public static Iterable<Module> getJacksonModules(){
        final Set<Module> result = new HashSet<>();

        ruleEnginePlugIns.parallelStream().map(RuleEnginePlugin::getJacksonModule).forEach(result::add);
        renderEnginePlugIns.parallelStream().map(RenderEnginePlugin::getJacksonModule).forEach(result::add);

        return result;


    }
}

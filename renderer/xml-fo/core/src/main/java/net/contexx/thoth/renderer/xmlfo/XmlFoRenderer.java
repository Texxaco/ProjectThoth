package net.contexx.thoth.renderer.xmlfo;

import net.contexx.thoth.core.controller.RenderEngine;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phasec.Addressee;
import net.contexx.thoth.core.model.phasec.Document;
import net.contexx.thoth.core.model.phasec.ExecutionContext;
import net.contexx.thoth.core.model.phasec.Variables;
import net.contexx.thoth.core.model.phased.Letter;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.IdentityHashMap;
import java.util.Locale;


import org.apache.fop.apps.*;
import org.xml.sax.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;

public class XmlFoRenderer implements RenderEngine {

    public static final String ENGINE_NAME = "XML-FO";

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // constructor


    public XmlFoRenderer() {
        try {
            fopFactory = FopFactory.newInstance(new File(".").toURI(), XmlFoRenderer.class.getClassLoader().getResourceAsStream("fop.conf.xml")); //todo passt das?
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //
    @Override
    public String getName() {
        return ENGINE_NAME;
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // rendering
    @Override
    public <IdentityType> Letter render(RenderInfo renderInfo, Domain<IdentityType> domain, IdentityType ident, Document document, Addressee addressee, Variables variables) throws RenderException {
        if(renderInfo instanceof XmlFoRenderInfo xmlFoInfo) {
            final ITemplateResolver iTemplateResolver = new ClassLoaderTemplateResolver();

            final TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(iTemplateResolver);

            final IdentityHashMap<String, Object> thymeleafVariables = new IdentityHashMap<>();
            thymeleafVariables.put("document", document);
            thymeleafVariables.put("data", new DataProvider<>(new ExecutionContext<IdentityType>(domain, variables)));



            final IContext context = new Context(Locale.getDefault(), thymeleafVariables);
            final String html = templateEngine.process(xmlFoInfo.getTemplateIdentifier(), context);

//            System.out.println(html);//todo

            return new Letter(convertXML2PDF(html)); //todo
        } else throw new RenderException("Unknown RenderInfo: "+renderInfo.getClass().getName());
    }


    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // Apache FOP

    private static FopFactory fopFactory; //todo ???

//    private static TransformerFactory tFactory = TransformerFactory.newInstance();

    private byte[] convertXML2PDF(String html) {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, byteArrayOutputStream);

            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer

            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(new StringReader(html));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } catch (FOPException | TransformerException e) {
            throw new RuntimeException(e);
        }

        return byteArrayOutputStream.toByteArray();


//        OutputStream out;
//        try {
//            //Load the stylesheet
//            Templates templates = tFactory.newTemplates(new StreamSource(this.getClass().getClassLoader().getResourceAsStream("stylesheet.xslt")));
//
//            //First run (to /dev/null)
////            out = new org.apache.commons.io.output.NullOutputStream();
//            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
//            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent);
//            Transformer transformer = templates.newTransformer();
//            transformer.setParameter("page-count", "#");
//            transformer.transform(new StreamSource(new StringReader(html)), new SAXResult(fop.getDefaultHandler()));
//
//            //Get total page count
//            String pageCount = Integer.toString(driver.getResults().getPageCount());
//
//            //Second run (the real thing)
//            out = new java.io.FileOutputStream(args[2]);
//            out = new java.io.BufferedOutputStream(out);
//            try {
//                foUserAgent = fopFactory.newFOUserAgent();
//                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
//                transformer = templates.newTransformer();
//                transformer.setParameter("page-count", pageCount);
//                transformer.transform(new StreamSource(new File(args[0])), new SAXResult(fop.getDefaultHandler()));
//            } finally {
//                out.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }









//        return new byte[0];
    }

    //_________________________________________________________________________
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // handling RenderInfo

    public RenderInfo getRenderInfo(String xmlFoTemplateIdentifier) { //todo
        return new XmlFoRenderInfo(xmlFoTemplateIdentifier);
    }
}

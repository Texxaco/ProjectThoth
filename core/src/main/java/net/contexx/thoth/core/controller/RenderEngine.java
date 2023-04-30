package net.contexx.thoth.core.controller;

import net.contexx.thoth.core.model.common.attribute.AttributeValue;
import net.contexx.thoth.core.model.phasea.Domain;
import net.contexx.thoth.core.model.phasec.Addressee;
import net.contexx.thoth.core.model.phasec.Document;
import net.contexx.thoth.core.model.phasec.Variables;
import net.contexx.thoth.core.model.phased.Letter;

import java.util.Set;

public interface RenderEngine {

    String getName();

    <IdentityType> Letter render(RenderInfo renderInfo, Domain<IdentityType> domain, IdentityType ident, Document document, Addressee addressee, Variables variables) throws RenderException;

    interface RenderInfo {
        String getRendererName();
    }

    class RenderException extends Exception {
        public RenderException(String message) {
            super(message);
        }

        public RenderException(String message, Throwable cause) {
            super(message, cause);
        }

        public RenderException(Throwable cause) {
            super(cause);
        }

        protected RenderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}

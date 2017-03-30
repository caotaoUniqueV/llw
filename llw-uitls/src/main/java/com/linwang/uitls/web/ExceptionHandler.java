package com.linwang.uitls.web;

import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ExceptionHandler implements TemplateExceptionHandler {

    private static Logger LOGGER = LogManager.getLogger();
    
    @Override
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException{
    	LOGGER.error("",te);
        try {
            TemplateExceptionHandler.HTML_DEBUG_HANDLER.handleTemplateException(te, env, out);
        } catch (Exception e) {}
    }

}

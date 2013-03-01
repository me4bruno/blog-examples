package de.bruns.example.freemarker.exception;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionExample {

	public static void main(String[] args) throws IOException, TemplateException {
		TemplateExceptionHandler exceptionHandler = new TemplateExceptionHandler() {
			public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
				try {
					out.write("[Missing Value]");
				} catch (IOException e) {
					throw new TemplateException("Failed to print error message. Cause: " + e, env);
				}
			}
		};
//		exceptionHandler = TemplateExceptionHandler.DEBUG_HANDLER;
//		exceptionHandler = TemplateExceptionHandler.HTML_DEBUG_HANDLER;
//		exceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER;
//		exceptionHandler = TemplateExceptionHandler.IGNORE_HANDLER;

		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(FreemarkerExceptionExample.class, "");
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setTemplateExceptionHandler(exceptionHandler);

		Map<String, Object> addressModel = new HashMap<String, Object>();
		addressModel.put("country", "USA");
		addressModel.put("city", "Springfield");

		Map<String, Object> personModel = new HashMap<String, Object>();
		personModel.put("name", "Homer Simpson");
		// personModel.put("address", addressModel);

		Template temp = configuration.getTemplate("template.ftl");
		Writer out = new OutputStreamWriter(System.out);
		temp.process(personModel, out);
		out.flush();
	}
}

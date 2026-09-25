package mx.gob.imss.ctirss.delta.cobranza.web.utils;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

public class GenericXmlAbstractView extends AbstractView {
	private static final String CONTENT_TYPE = "application/xml";
	private static final String EXTENSION = ".xml";

	private String fileName;

	public GenericXmlAbstractView() {
		setContentType(CONTENT_TYPE);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType(getContentType());

		String customizedFileName = (String) model.get("customizedFileName");
		if (StringUtils.isNotBlank(customizedFileName)) {
			fileName = customizedFileName;
		}

		response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + EXTENSION + "\"");

		String strFileContent = (String) model.get("fileContent");
		

		PrintWriter writer = response.getWriter();
		writer.write(strFileContent);
		writer.close();
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

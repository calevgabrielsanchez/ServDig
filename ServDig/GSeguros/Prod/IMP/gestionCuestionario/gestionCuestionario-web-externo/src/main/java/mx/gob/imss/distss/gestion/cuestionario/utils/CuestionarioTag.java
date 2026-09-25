package mx.gob.imss.distss.gestion.cuestionario.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mx.gob.imss.distss.gestion.cuestionario.modelo.Cuestionario;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Opcion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Pregunta;
import mx.gob.imss.distss.gestion.cuestionario.modelo.Seccion;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TipoRespuestaElementEnum;

import org.apache.commons.lang.StringUtils;

public class CuestionarioTag extends SimpleTagSupport {

	private Cuestionario cuestionario;

	public void doTag() throws JspException {

		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();

		StringBuffer html = new StringBuffer();

		html.append(armarCuestionario(cuestionario));
		
		try {
			out.print(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Cuestionario getCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(Cuestionario cuestionario) {
		this.cuestionario = cuestionario;
	}
	
	private String armarCuestionario(Cuestionario cuestionario) {
		
		StringBuffer html = new StringBuffer();
		
		html.append("<div id=\"cuestionarioForm\" class=\"form-horizontal\">");
		html.append("<fieldset>");
		html.append("<div class=\"m-b-md\"><h4>");
		html.append(cuestionario.getTitulo()).append("</h4>");
		html.append("<hr class=\"red m-b-none\"></div>");
			
		if (cuestionario.getSecciones().size() == 1) {
			html.append(armarSeccion(cuestionario.getSecciones().get(0), false, 1));
		} else {
			html.append("<div class=\"panel-group ficha-collapse\" id=\"accordion\">");

			Seccion seccion = null;
			for (int i = 0 ; i < cuestionario.getSecciones().size(); i++) {
				seccion = cuestionario.getSecciones().get(i);
				html.append(armarSeccion(seccion, true, i + 1));
			}
			
			html.append("</div>");
		}
			
		html.append("</fieldset>");
		html.append("</div>");	
		
		return  html.toString();
		
	}
	
	private String armarSeccion(Seccion seccion, boolean isCollapsible, int idx) {
		
		StringBuffer html = new StringBuffer();
		String titulo = StringUtils.isBlank(seccion.getTitulo()) ? "Sección " + idx : seccion.getTitulo();
		
		html.append("<div class=\"panel panel-default\">");
		html.append("<div class=\"panel-heading\">");
		html.append("<h4 class=\"panel-title\" ");
		html.append("id=\"seccionCuestionario").append(seccion.getClave()).append("Header\">");
		
		if (isCollapsible) {
			html.append("<a href=\"#collapse");
			html.append(seccion.getClave()).append("\" "); 
			html.append("data-parent=\"#accordion\" data-toggle=\"collapse\" ");
			html.append("class=\"collapsed\" aria-expanded=\"false\">");
			html.append(titulo);
	        html.append("</a>");
		} else {
			html.append(titulo);
		}
		
		html.append("<span class=\"error hiddenElement\" id=\"seccionCuestionario").append(seccion.getClave()).append("Error\"></span>");
		html.append("</h4>");
		if (isCollapsible) {
			html.append("<button aria-controls=\"collapse");
			html.append(seccion.getClave()).append("\" ");
			html.append("aria-expanded=\"false\" href=\"#collapse");
			html.append(seccion.getClave()).append("\" ");
			html.append("data-toggle=\"collapse\" class=\"collpase-button ");
			html.append("collapsed\" type=\"button\"></button>");
		}
		html.append("</div>");
		html.append("<div");
		
		if (isCollapsible) {
			html.append(" id=\"collapse").append(seccion.getClave());
			html.append("\" class=\"panel-collapse collapse\">");
		} else {
			html.append(">");
		}
					
		html.append("<div class=\"panel-body\">");
		html.append("<div class=\"seccionCuestionario\" ");
		html.append("id=\"seccionCuestionario").append(seccion.getClave());
		html.append("\" csc=\"").append(seccion.getClave()).append("\">");
		
		for(Pregunta pregunta : seccion.getPreguntas()) {
			html.append(armarPregunta(pregunta, false, seccion.getClave()));
		}

		html.append("</div>");
		html.append("</div>");
		html.append("</div>");
		html.append("</div>");
		
		return html.toString();
	}
	
	private String armarPregunta(Pregunta pregunta, boolean isDependiente, int seccion) {
		
		StringBuffer html = new StringBuffer();
		
		html.append("<div class=\"panel panel-default");
		if(isDependiente) {
			html.append(" pregDependiente");
		}
		html.append("\">");
		
		if(isDependiente) {
			html.append("<div class=\"panel-body\">");
			html.append("<p class=\"pregDependiente\">");
			if (pregunta.isObligatoria()) {
			    html.append("<span class=\"required\">*</span>");
			}
			html.append(pregunta.getInciso()).append(". ");
			html.append(pregunta.getDescripcion());
			html.append("<span id=\"errorPreg").append(pregunta.getClave()).append("\" ");
			html.append("class=\"error hiddenElement\"></span></p>");
		} else {
			html.append("<div class=\"panel-heading\">");
			if (pregunta.isObligatoria()) {
			    html.append("<span class=\"required\">*</span>");
			}
			html.append(pregunta.getInciso()).append(". ");
			html.append(pregunta.getDescripcion());
			html.append("<span id=\"errorPreg").append(pregunta.getClave()).append("\" ");
			html.append("class=\"error hiddenElement\"></span></div>");
			html.append("<div class=\"panel-body\">");
		}
				
		html.append("<div class=\"opciones\">");
		
		if(pregunta.getTipoRespuesta().getClave() == TipoRespuestaElementEnum.BOOLEANA.getClave() 
				|| pregunta.getTipoRespuesta().getClave() == TipoRespuestaElementEnum.MULTIPLE_BOOLEANA.getClave()) {
			html.append(armarRadioButtons(pregunta, seccion));
		} else if(pregunta.getTipoRespuesta().getClave() == TipoRespuestaElementEnum.MULTIPLE_PLURAL.getClave()) {
			html.append(armarCheckboxes(pregunta, seccion));
		} else if(pregunta.getTipoRespuesta().getClave() == TipoRespuestaElementEnum.SELECCION.getClave()){
			html.append(armarSelect(pregunta, seccion));
		}
		
		html.append("</div></div></div>");
		
		return html.toString();
		
	}
	
	private String armarRadioButtons(Pregunta pregunta, int seccion) {
		
		StringBuffer html = new StringBuffer();
		
		for(Opcion opcion : pregunta.getOpciones()) {
			html.append("<div class=\"radio\">");
			html.append("<label>"); 
			html.append("<input type=\"radio\" name=\"opc");
			html.append(pregunta.getClave()).append("\" ");
			html.append("id=\"opc").append(pregunta.getClave()).append(opcion.getClave()).append("\" "); 
			html.append("value=\"").append(opcion.getValor()).append("\" ");
			html.append("cP=\"").append(pregunta.getClave()).append("\" ");
			html.append("cOpc=\"").append(opcion.getClave()).append("\" ");
			html.append("numP=\"").append(pregunta.getInciso()).append("\" ");
			html.append("numSec=\"").append(seccion).append("\" ");
			html.append("class=\"respuesta\" ");
			
			if (opcion.isHabilitarDependencia()) {
				html.append("dependencia=\"true\"");
			}
			
			html.append("/>").append(opcion.getDescripcion().toUpperCase()).append("</label>");
			html.append("</div>");
			
			if (opcion.isHabilitarDependencia()) {
				html.append(armarPreguntaDependiente(pregunta, opcion, seccion));
			}
		}
		
		return html.toString();
	}
	
	private String armarCheckboxes(Pregunta pregunta, int seccion) {
		
		StringBuffer html = new StringBuffer();
		
		// Se suma uno por la opción (NINGUNA DE LAS OPCIONES) creada al vuelo
		int numOpciones = pregunta.getOpciones().size() + (pregunta.isObligatoria() ? 1 : 0);
		
		int numColumnas = numOpciones >= 10 ? 2 : 1;
		
		int indices[][] = new int[numColumnas][2];
		
		if (numColumnas == 1) {
			indices[0][0] = 0;
			indices[0][1] = numOpciones - (pregunta.isObligatoria() ? 1 : 0);
		} else {
			int numOpcionesColumna = numOpciones / numColumnas;
			indices[0][0] = 0;
			indices[0][1] = numOpcionesColumna + (numOpciones % numColumnas) - (pregunta.isObligatoria() ? 1 : 0);
			indices[1][0] = indices[0][1];
			indices[1][1] = numOpciones - (pregunta.isObligatoria() ? 1 : 0);
		}
		
		html.append("<div class=\"row\">");
		
		for(int i = 0; i < numColumnas; i++) {
			html.append("<div class=\"col-sm-").append(numColumnas == 1 ? "12" : "6").append("\">");
			if (pregunta.isObligatoria() && i == 0) {
				html.append("<div class=\"checkbox\">");
				html.append("<label style=\"font-style: italic;\">"); 
				html.append("<input type=\"checkbox\" name=\"opc");
				html.append(pregunta.getClave()).append("\" ");
				html.append("id=\"opc").append(pregunta.getClave()).append("Ninguna\" ");
				html.append("value=\"0\" ");
				html.append("cP=\"").append(pregunta.getClave()).append("\" ");
				html.append("cOpc=\"-1\" ");
				html.append("numP=\"").append(pregunta.getInciso()).append("\" ");
				html.append("numSec=\"").append(seccion).append("\" ");
				html.append("class=\"respuesta no-option\" ");
				html.append("/>NINGUNA DE LAS OPCIONES</label>");
				html.append("</div>");
			}
			
			for(Opcion opcion : pregunta.getOpciones().subList(indices[i][0], indices[i][1])) {
				html.append("<div class=\"checkbox\">");
				html.append("<label>"); 
				html.append("<input type=\"checkbox\" name=\"opc");
				html.append(pregunta.getClave()).append("\" ");
				html.append("id=\"opc").append(pregunta.getClave()).append(opcion.getClave()).append("\" ");
				html.append("value=\"").append(opcion.getValor()).append("\" ");
				html.append("cP=\"").append(pregunta.getClave()).append("\" ");
				html.append("cOpc=\"").append(opcion.getClave()).append("\" ");
				html.append("numP=\"").append(pregunta.getInciso()).append("\" ");
				html.append("numSec=\"").append(seccion).append("\" ");
				html.append("class=\"respuesta\" ");
				
				if (opcion.isHabilitarDependencia()) {
					html.append("dependencia=\"true\"");
				}
				
				html.append("/>").append(opcion.getDescripcion().toUpperCase()).append("</label>");
				html.append("</div>");
				
				if (opcion.isHabilitarDependencia()) {
					html.append(armarPreguntaDependiente(pregunta, opcion, seccion));
				}
			}
			html.append("</div>");
		}
		html.append("</div>");
		
		return html.toString();
	}
	
	private String armarSelect(Pregunta pregunta, int seccion) {
		
		StringBuffer html = new StringBuffer();
		StringBuffer pregDependientesDiv = new StringBuffer();
		
		html.append("<select name=\"opc").append(pregunta.getClave()).append("\" ");
		html.append("id=\"opc").append(pregunta.getClave()).append("\" ");
		html.append("cP=\"").append(pregunta.getClave()).append("\" ");
		html.append("class=\"respuesta form-control input-sm\">");
		html.append("<option value=\"-1\">-- SELECCIONE UNA OPCIÓN --</option>");
		for(Opcion opcion : pregunta.getOpciones()) {
			html.append("<option value=\"").append(opcion.getValor()).append("\" ");
			html.append("cOpc=\"").append(opcion.getClave()).append("\" ");
			html.append("numP=\"").append(pregunta.getInciso()).append("\" ");
			html.append("numSec=\"").append(seccion).append("\" ");
			
			if (opcion.isHabilitarDependencia()) {
				html.append("dependencia=\"true\"");
			}
			
			html.append(">").append(opcion.getDescripcion().toUpperCase()).append("</option>");
			
			if (opcion.isHabilitarDependencia()) {
				pregDependientesDiv.append(armarPreguntaDependiente(pregunta, opcion, seccion));
			}
		}
		html.append("</select>");
		html.append(pregDependientesDiv);
		
		return html.toString();
	}
	
	private String armarPreguntaDependiente(Pregunta preguntaPadre, Opcion opcion, int seccion) {
		
		StringBuffer html = new StringBuffer();
		
		html.append("<div id=\"opc").append(preguntaPadre.getClave()).append(opcion.getClave());
		html.append("Pregunta\" class=\"pregDependienteContainer\" style=\"display: none;\" >");
		for (Pregunta preguntaDep : opcion.getDependencias()) {
			html.append(armarPregunta(preguntaDep, true, seccion));
		}
		
		html.append("</div>");
		
		return html.toString();
	}


}

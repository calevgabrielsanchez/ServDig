package mx.gob.imss.ctirss.delta.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;


public class ComboTag extends SimpleTagSupport {
	
	
	private String entidad = "";
	private String idHtml = "";
	private String entidadPadre = "";
	private String idHtmlPadre = "";
	private String idHtmlContenedor = "";
	private String idHtmlValor = "";
	private String idHtmlValorPadre = "";
	private boolean mostrarSoloActivos;
	private String campoVigencia = "";
	private boolean mostrarSoloEntidades ;
	private String cssClassname	= "";

	public void doTag() throws JspException {

		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();

		try {
			if (!(entidad != null && !entidad.equals("") && idHtml != null
					&& !idHtml.equals("") && idHtmlContenedor != null && !idHtmlContenedor
						.equals(""))) {
				System.out.println("El combo no se ha podido generar, el parametro 'entidad'/'idHtml'/'idHtmlContenedor' es obligatorio");
			} else {
				boolean bValidado = false;
				boolean bComboSimple = false;
				if (entidadPadre != null && !entidadPadre.equals("")) {
					if (!(idHtmlPadre != null && !idHtmlPadre.equals(""))) {
						System.out.println("El combo no se ha podido generar, el parametro 'idHtmlPadre' no es correcto");
					} else
						bValidado = true;
				} else {
					bValidado = true;
					bComboSimple = true;
				}
				
				if (bValidado) {
					if (bComboSimple) {
						out.println(procesaComboSimple(entidad, idHtml,
								idHtmlContenedor, idHtmlValor,mostrarSoloActivos,campoVigencia, cssClassname,mostrarSoloEntidades));
					} else {
						out.println(procesaComboDependiente(entidad, idHtml,
								idHtmlContenedor, entidadPadre, idHtmlPadre,
								idHtmlValor, idHtmlValorPadre,mostrarSoloActivos,campoVigencia, cssClassname));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIdHtmlValor() {
		return idHtmlValor;
	}

	public void setIdHtmlValor(String idHtmlValor) {
		this.idHtmlValor = idHtmlValor;
	}

	/**
	 * 
	 * Genera el codigo HTML/javascript para un combo simple
	 * 
	 * @param sNombreTabla
	 * @param sNombreAtributoTabla
	 * @param sNombreContenedor
	 * @param idHtmlValor
	 * @return
	 */
	private String procesaComboSimple(String sNombreTabla,
			String sNombreAtributoTabla, String sNombreContenedor,
			String idHtmlValor, boolean validVigencia, String campoVig,
			String cssClass,boolean mostrarSoloEntidades) {
		
		StringBuffer sbRes = new StringBuffer();
		String sNombreTablaSinPaquetes = this
				.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape = this
				.getCadenaPuntosConEscape(sNombreAtributoTabla);

		// Genera el elemento select
		sbRes.append("<select id='").append(sNombreAtributoTabla).append("' name='").append(sNombreAtributoTabla);
		sbRes.append("' class='").append(cssClass).append("'>");
		sbRes.append("<option value=''>--Selecciona por favor--</option>");
		sbRes.append("</select>");
		sbRes.append("<img alt='Cargando' id='").append(sNombreAtributoTabla).append("ImgCargando' ");
		sbRes.append("src='http://").append(((PageContext) getJspContext()).getRequest().getServerName()).append("/delta/resources/imagenes/loading.gif' ");
		sbRes.append("class='cargando-combo' style='display:none' />\n");
		// Genera la funcion que realizara la consulta asincrona
		sbRes.append("<script languaje='JavaScript'>\n");
		sbRes.append("var url = \"/portal-web/combo/simple.do\";\n");
		sbRes.append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase());
		sbRes.append(" = new comboCtrlSimple(url, '").append(sNombreTabla).append("', '#").append(sNombreContenedor);
		sbRes.append(" select#").append(sNombreAtributoTabCEscape).append("' ,'").append(idHtmlValor).append("',");
		sbRes.append(""+validVigencia+",'").append(campoVig).append("', '").append(sNombreAtributoTabCEscape).append("',").append(""+mostrarSoloEntidades+"").append(");\n");
		sbRes.append("$(document).ready(function() {\n");
		sbRes.append("      cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargar();\n");
		sbRes.append("});\n");
		sbRes.append("</script>\n");

		return sbRes.toString();
	}

	/**
	 * Genera el codigo HTML/javascript para un combo dependiente
	 * 
	 * @param sNombreTabla
	 * @param sNombreAtributoTabla
	 * @param sNombreContenedor
	 * @param sNombreTablaPadre
	 * @param sNombreAtributoTablaPadre
	 * @param idHtmlValor
	 * @param idHtmlValorPadre
	 * @return
	 */
	private String procesaComboDependiente(String sNombreTabla,
			String sNombreAtributoTabla, String sNombreContenedor,
			String sNombreTablaPadre, String sNombreAtributoTablaPadre,
			String idHtmlValor, String idHtmlValorPadre, boolean validVigencia,
			String campoVig, String cssClass) {

		StringBuffer sbRes = new StringBuffer();
		String sNombreTablaSinPaquetes = this
				.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape = this
				.getCadenaPuntosConEscape(sNombreAtributoTabla);
		String sNombreAtrTabPadCEscape = this
				.getCadenaPuntosConEscape(sNombreAtributoTablaPadre);
		String sNombreAtrTabPadSinHijo = sNombreTablaPadre;

		sbRes.append("<select id='").append(sNombreAtributoTabla).append("' name='").append(sNombreAtributoTabla);
		sbRes.append("' ").append(" class='").append(cssClass).append("'>\n");
		sbRes.append("<option value=''>--Selecciona por favor--</option>\n");
		sbRes.append("</select>");
		sbRes.append("<img alt='Cargando' id='").append(sNombreAtributoTabla).append("ImgCargando' ");
		sbRes.append("src='http://").append(((PageContext) getJspContext()).getRequest().getServerName()).append("/delta/resources/imagenes/loading.gif' ");
		sbRes.append("class='cargando-combo' style='display:none' />\n");
		sbRes.append("<script languaje='JavaScript'>\n");
		sbRes.append("var url = \"/portal-web/combo/dependiente.do\";\n");
		sbRes.append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase());
		sbRes.append(" = new comboCtrlDependiente(url, '").append(sNombreTabla).append("', '#").append(sNombreContenedor);
		sbRes.append(" select#").append(sNombreAtributoTabCEscape).append("','").append(sNombreAtrTabPadSinHijo).append("','#").append(sNombreContenedor);
		sbRes.append(" select#").append(sNombreAtrTabPadCEscape).append("' ,'").append(idHtmlValor).append("' ,'");
		sbRes.append(idHtmlValorPadre).append("',").append(""+validVigencia+",'");
		sbRes.append(campoVig).append("', '").append(sNombreAtributoTabCEscape).append("');\n");
		sbRes.append("$(document).ready(function(){\n");
		sbRes.append("  $('#").append(sNombreContenedor).append(" select#").append(sNombreAtrTabPadCEscape).append("').change(function(){\n");
		sbRes.append("	  try{\n");
		sbRes.append("	    cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargardep();\n");
		sbRes.append("	  }catch (e) {\n");
		sbRes.append("		alert(e);\n");
		sbRes.append("	  }\n");
		sbRes.append("  })\n");
		sbRes.append(" try{ $('#").append(sNombreContenedor).append(" select#").append(sNombreAtrTabPadCEscape).append("').trigger('change'); }catch(err){}\n");
		sbRes.append("});\n");
		sbRes.append("</script>\n");

		return sbRes.toString();
	}

	private String getNombreTablaSinPaquetes(String sNombreTabla) {
		return (sNombreTabla.lastIndexOf(".") != -1) ? sNombreTabla
				.substring(sNombreTabla.lastIndexOf(".") + 1) : sNombreTabla;
	}

	private String getCadenaPuntosConEscape(String sCadena) {
		return sCadena.replaceAll("\\.", "\\\\\\\\.");
	}

	@SuppressWarnings("unused")
	private String getCadenaAntesPrimerPunto(String sCadena) {
		return (sCadena.indexOf(".") != -1) ? sCadena.substring(0,
				sCadena.indexOf(".")) : sCadena;
	}

	@SuppressWarnings("unused")
	private String getCadenaSinPadre(String sCadena, String sCadenaPadre) {
		return (sCadena.toLowerCase().indexOf(sCadenaPadre.toLowerCase()) != -1) ? sCadena
				.substring(sCadena.toLowerCase().indexOf(
						sCadenaPadre.toLowerCase())) : sCadena;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getIdHtml() {
		return idHtml;
	}

	public void setIdHtml(String idHtml) {
		this.idHtml = idHtml;
	}

	public String getEntidadPadre() {
		return entidadPadre;
	}

	public void setEntidadPadre(String entidadPadre) {
		this.entidadPadre = entidadPadre;
	}

	public String getIdHtmlPadre() {
		return idHtmlPadre;
	}

	public void setIdHtmlPadre(String idHtmlPadre) {
		this.idHtmlPadre = idHtmlPadre;
	}

	public String getIdHtmlContenedor() {
		return idHtmlContenedor;
	}

	public void setIdHtmlContenedor(String idHtmlContenedor) {
		this.idHtmlContenedor = idHtmlContenedor;
	}

	public String getIdHtmlValorPadre() {
		return idHtmlValorPadre;
	}

	public void setIdHtmlValorPadre(String idHtmlValorPadre) {
		this.idHtmlValorPadre = idHtmlValorPadre;
	}

	public boolean isMostrarSoloActivos() {
		return mostrarSoloActivos;
	}

	public void setMostrarSoloActivos(boolean mostrarSoloActivos) {
		this.mostrarSoloActivos = mostrarSoloActivos;
	}

	public String getCampoVigencia() {
		return campoVigencia;
	}

	public void setCampoVigencia(String campoVigencia) {
		this.campoVigencia = campoVigencia;
	}

	public String getCssClassname() {
		return cssClassname;
	}

	public void setCssClassname(String cssClassname) {
		this.cssClassname = cssClassname;
	}

	public boolean isMostrarSoloEntidades() {
		return mostrarSoloEntidades;
	}

	public void setMostrarSoloEntidades(boolean mostrarSoloEntidades) {
		this.mostrarSoloEntidades = mostrarSoloEntidades;
	}
	
	
}

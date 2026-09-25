/**
 * 
 */
package mx.gob.imss.ctirss.delta.correccion.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 07/10/2011
 *
 */
public class ComboTag extends SimpleTagSupport{

	private String entidad 			= "";
	private String idHtml			= "";
	private String entidadPadre		= "";
	private String idHtmlPadre		= "";
	private String idHtmlContenedor	= "";
	

	public void doTag() throws JspException {

		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();

		try {
			if(!(entidad!=null && !entidad.equals("") && idHtml!=null && !idHtml.equals("") &&
				 idHtmlContenedor!=null && !idHtmlContenedor.equals(""))){
				System.out.println("El combo no se ha podido generar, el parametro 'entidad'/'idHtml'/'idHtmlContenedor' es obligatorio");
			}
			else{
				boolean bValidado	= false;
				boolean bComboSimple= false;
				if(entidadPadre!=null && !entidadPadre.equals("")){
				  if(!(idHtmlPadre!=null && !idHtmlPadre.equals(""))){
					  System.out.println("El combo no se ha podido generar, el parametro 'idHtmlPadre' no es correcto");
				  }
				  else bValidado = true;
				}
				else{
				  bValidado 	= true;
				  bComboSimple 	= true;
				}
				if(bValidado){
				  if(bComboSimple){
					out.println(procesaComboSimple(entidad, idHtml, idHtmlContenedor));
				  }
				  else{
					out.println(procesaComboDependiente(entidad, idHtml, idHtmlContenedor, entidadPadre, idHtmlPadre));					
				  }
				}//if(bValidado){
			}//fin else if(entidadPadre!=null && !entidadPadre.equals("")){
		} catch (Exception e) {
			e.printStackTrace();
		}

	}//doTag()
	
	private String procesaComboSimple(String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor){
		StringBuffer sbRes 				= new StringBuffer();
		String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
		
			//Genera el elemento select
			sbRes.append("<select id='").append(sNombreAtributoTabla).append("' name='").append(sNombreAtributoTabla).append("'>")
				 .append("<option value=''>--Por favor seleccione--</option>")
				 .append("</select>\n");
			//Genera la funcion  que realizara la consulta asincrona
			sbRes.append("<script languaje='JavaScript'>\n")
				 .append("var url = \"\"+context_path+\"/combo/simple.do\";\n")
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase())
				 		.append(" = new comboCtrlSimple(url, '").append(sNombreTabla).append("', 'form#").append(sNombreContenedor)
				 		.append(" select#").append(sNombreAtributoTabCEscape).append("');\n")
				 .append("$(document).ready(function() {\n")
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargar();\n")
				 .append("});\n")
				 .append("</script>\n");
		return sbRes.toString();
	}//procesaComboSimple
	
	private String procesaComboDependiente(	String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor,
											String sNombreTablaPadre, String sNombreAtributoTablaPadre){
		StringBuffer sbRes 				= new StringBuffer();
		String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
	//	String sNombreTabPadSinPaquetes	= this.getNombreTablaSinPaquetes(sNombreTablaPadre);
		String sNombreAtrTabPadCEscape	= this.getCadenaPuntosConEscape(sNombreAtributoTablaPadre);
		String sNombreAtrTabPadSinHijo	= sNombreTablaPadre;
		
			sbRes.append("<select id='").append(sNombreAtributoTabla).append("' name='").append(sNombreAtributoTabla).append("'>\n")
				 .append("<option value=''>--Por favor seleccione--</option>\n")
				 .append("</select>\n");
			sbRes.append("<script languaje='JavaScript'>\n")
				 .append("		var url = \"\"+context_path+\"/combo/dependiente.do\";\n")			
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase())
				 		.append(" = new comboCtrlDependiente(url, '").append(sNombreTabla).append("', 'form#").append(sNombreContenedor)
				 		.append(" select#").append(sNombreAtributoTabCEscape).append("','").append(sNombreAtrTabPadSinHijo).append("','form#").append(sNombreContenedor)
				 		.append(" select#").append(sNombreAtrTabPadCEscape).append("');\n")	
				 .append("$(function(){\n")
				 .append("  $('form#").append(sNombreContenedor).append(" select#").append(sNombreAtrTabPadCEscape).append("').change(function(){\n")
				 .append("	  try{\n")
				 .append("	    cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargardep();\n")	 				 
				 .append("	  }catch (e) {\n")
				 .append("		alert(e);\n")
				 .append("	  }\n")
				 .append("  })\n")
				 .append("})\n")
				 .append("</script>\n");
		return sbRes.toString();
	}//procesaComboDependiente
	
	private String getNombreTablaSinPaquetes(String sNombreTabla){
		return (sNombreTabla.lastIndexOf(".")!=-1)?sNombreTabla.substring(sNombreTabla.lastIndexOf(".")+1):sNombreTabla;
	}
	
	private String getCadenaPuntosConEscape(String sCadena){
		return sCadena.replaceAll("\\.", "\\\\\\\\.");
	}	
	
	private String getCadenaAntesPrimerPunto(String sCadena){
		return (sCadena.indexOf(".")!=-1)?sCadena.substring(0,sCadena.indexOf(".")):sCadena;
	}		
	
	private String getCadenaSinPadre(String sCadena, String sCadenaPadre){
		return (sCadena.toLowerCase().indexOf(sCadenaPadre.toLowerCase())!=-1)?sCadena.substring(sCadena.toLowerCase().indexOf(sCadenaPadre.toLowerCase())):sCadena;
	}	

	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the idHtml
	 */
	public String getIdHtml() {
		return idHtml;
	}

	/**
	 * @param idHtml the idHtml to set
	 */
	public void setIdHtml(String idHtml) {
		this.idHtml = idHtml;
	}

	/**
	 * @return the entidadPadre
	 */
	public String getEntidadPadre() {
		return entidadPadre;
	}

	/**
	 * @param entidadPadre the entidadPadre to set
	 */
	public void setEntidadPadre(String entidadPadre) {
		this.entidadPadre = entidadPadre;
	}

	/**
	 * @return the idHtmlPadre
	 */
	public String getIdHtmlPadre() {
		return idHtmlPadre;
	}

	/**
	 * @param idHtmlPadre the idHtmlPadre to set
	 */
	public void setIdHtmlPadre(String idHtmlPadre) {
		this.idHtmlPadre = idHtmlPadre;
	}

	/**
	 * @return the idHtmlContenedor
	 */
	public String getIdHtmlContenedor() {
		return idHtmlContenedor;
	}

	/**
	 * @param idHtmlContenedor the idHtmlContenedor to set
	 */
	public void setIdHtmlContenedor(String idHtmlContenedor) {
		this.idHtmlContenedor = idHtmlContenedor;
	}	
	
}

/**
 * 
 */
package mx.imss.ctirss.web.utils;

import java.util.StringTokenizer;

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

	private String id				= "";
	private String entidad 			= "";
	private String idHtml			= "";
	private String entidadPadre		= "";
	private String idHtmlPadre		= "";
	private String entidadPadre2	= "";
	private String idHtmlPadre2		= "";
	private String idHtmlContenedor	= "";
	private String idHtmlValor		= "";
	private String param			= "";
	private String paramValue		= "";
	private String overWriteId		= "";
	private String overWriteValue	= "";
	private String entidadRelacion 	= "";
	private String idHtmlRelacion   = "";
	private String compValorRelacion = "";
	private String idSustituto 		= "";
	private String activa 			= "";
	private String disabled 		= "";
	private String onchange			= "";
	private String style			= "";
	private String onfocus			= "";

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
				boolean bComboDoble = false;
				if(entidadPadre!=null && !entidadPadre.equals("")){
					if(!(idHtmlPadre!=null && !idHtmlPadre.equals(""))){
					  System.out.println("El combo no se ha podido generar, el parametro 'idHtmlPadre' no es correcto");
					}else if(entidadPadre2!=null && !entidadPadre2.equals("")){
						if(!(idHtmlPadre2!=null && !idHtmlPadre2.equals(""))){
							  System.out.println("El combo de dependencia doble no se ha podido generar, el parametro 'idHtmlPadre2' no es correcto");
							}
						else{
							bComboDoble = true;
							bComboSimple = false;
						}
						}
						
				     bValidado = true;
				} 
				else{
				  bValidado 	= true;
				  bComboSimple 	= true;
				  
				}
				if(bValidado){
				  if(bComboSimple){
					  if(param!=null && !param.equals("") && paramValue!=null && !paramValue.equals("")){
						  out.println(procesaComboSimpleCustom(entidad, idHtml, idHtmlContenedor, param, paramValue, ""));
					  }else{
						  if(idHtmlValor!=null && !idHtmlValor.equals(""))
						    out.println(procesaComboSimple(entidad, idHtml, idHtmlContenedor, idHtmlValor));
						  else
							out.println(procesaComboSimple(entidad, idHtml, idHtmlContenedor));
					  }
					
				  }else if(bComboDoble){
					out.println(procesaComboDoblementeDependiente(entidad, idHtml, idHtmlContenedor, entidadPadre, idHtmlPadre, entidadPadre2, idHtmlPadre2));
				  }else{
					if(overWriteId.equals("Yes")){
						out.println(procesaComboDependienteRelacion(entidad, idHtml, idHtmlContenedor, entidadPadre, idHtmlPadre, entidadRelacion, idHtmlRelacion, compValorRelacion));
					}else{
						if(idHtmlValor!=null && !idHtmlValor.equals(""))
							out.println(procesaComboDependiente(entidad, idHtml, idHtmlContenedor, entidadPadre, idHtmlPadre,idHtmlValor));
						else
							out.println(procesaComboDependiente(entidad, idHtml, idHtmlContenedor, entidadPadre, idHtmlPadre));
					}
				  }
				}//if(bValidado){
			}//fin else if(entidadPadre!=null && !entidadPadre.equals("")){
		} catch (Exception e) {
			e.printStackTrace();
		}

	}//doTag()
	
	private String procesaComboSimple(String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor){
		return procesaComboSimple(sNombreTabla, sNombreAtributoTabla, sNombreContenedor, null);
	}
	
	private String procesaComboSimple(String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor, String sValorSelect){
		StringBuffer sbRes 				= new StringBuffer();
		String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
		
		String sNombreFuncion = getCleanFuntionName(sNombreAtributoTabla);
		
			//Genera el elemento select
		sbRes.append("<select onchange=\"").append(onchange).append("\" id=\"").append(sNombreAtributoTabla).append("\" style='").append(style).append("' name='").append(sNombreAtributoTabla).append("'>")
				 .append("<option value='-1'>--Por favor seleccione--</option>")
				 .append("</select>\n");
			//Genera la funcion  que realizara la consulta asincrona
			sbRes.append("<script languaje='JavaScript'>\n")
				 .append("var url = \"\"+getAppContextParaJS()+\"/combo/simple.do\";\n")
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(sNombreFuncion)
				 		.append(" = new comboCtrlSimple(url, '").append(sNombreTabla).append("', 'form#").append(sNombreContenedor)
				 		.append(" select#").append(sNombreAtributoTabCEscape).append("'").append(",'"+sValorSelect+"');\n")
				 .append("$(document).ready(function() {\n")
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(sNombreFuncion).append(".cargar();\n")
				 .append("});\n")
				 .append("$(function(){\n")
				 .append("  $('form#").append(sNombreContenedor).append(" select#").append(sNombreAtributoTabCEscape).append("').change(function(){\n")
				 .append("	  try{\n")
				 .append("	  }catch (e) {\n")
				 .append("		alert(e);\n")
				 .append("	  }\n")
				 .append("  })\n")
				 .append("})\n");
			sbRes.append("</script>\n");
		return sbRes.toString();
	}//procesaComboSimple
	
	private String procesaComboSimpleCustom(String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor, String sParam, String sParamValue, String x){
		StringBuffer sbRes 				= new StringBuffer();
		String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
		String sParamTabCEscape   = this.getCadenaPuntosConEscape(sParam);
		String sParamValueTabCEscape = this.getCadenaPuntosConEscape(sParamValue);
			//Genera el elemento select
			sbRes.append("<select class=\"etiqueta2\" onfocus=\"").append(onfocus).append("\" onchange=\"").append(onchange).append("\"  id=\"").append(sNombreAtributoTabla).append("\" style='").append(style).append("' name='").append(sNombreAtributoTabla).append("'>")
				 .append("<option value='-1'>--Por favor seleccione--</option>")
				 .append("</select>\n");
			//Genera la funcion  que realizara la consulta asincrona
			sbRes.append("<script languaje='JavaScript'>\n")
				 .append("var url = \"\"+getAppContextParaJS()+\"/combo/simpleCustom.do\";\n")
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase())
				 		.append(" = new comboCtrlSimpleCustom(url, '").append(sNombreTabla)
				 		.append("','form#").append(sNombreContenedor).append(" select#").append(sNombreAtributoTabCEscape)
				 		.append("', '").append(sParam)
				 		.append("', '").append(sParamValueTabCEscape)	
				 		.append("', '").append(sParamValueTabCEscape).append("');\n")
				 .append("$(document).ready(function() {\n")
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargar();\n")
				 .append("});\n")
				 .append("</script>\n");
		return sbRes.toString();
	}//procesaComboSimple
	
	private String procesaComboDependiente(	String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor,
			String sNombreTablaPadre, String sNombreAtributoTablaPadre){
		
		return procesaComboDependiente(	sNombreTabla, sNombreAtributoTabla, sNombreContenedor,
				sNombreTablaPadre, sNombreAtributoTablaPadre, null );
		
	}
	private String procesaComboDependiente(	String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor,
											String sNombreTablaPadre, String sNombreAtributoTablaPadre, String sValorSelect ){
		StringBuffer sbRes 				= new StringBuffer();
		String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
		String sNombreTabPadSinPaquetes	= this.getNombreTablaSinPaquetes(sNombreTablaPadre);
		String sNombreAtrTabPadCEscape	= this.getCadenaPuntosConEscape(sNombreAtributoTablaPadre);
		String sNombreAtrTabPadSinHijo	= this.getCadenaSinPadre(sNombreAtributoTablaPadre, sNombreTabPadSinPaquetes);

		String sNombreTablaSinPaquetesB="";
		String sNombreFuncion="";
		if(sNombreAtrTabPadCEscape.contains(",")){
			sNombreTablaSinPaquetesB = sNombreAtrTabPadCEscape.substring(0,sNombreAtrTabPadCEscape.indexOf(","));
		}else{
			sNombreTablaSinPaquetesB = sNombreAtrTabPadCEscape;
		}
		
		if(sNombreTablaSinPaquetesB.contains("@")){
			sNombreTablaSinPaquetesB = sNombreTablaSinPaquetesB.substring(0,sNombreTablaSinPaquetesB.indexOf("@"));
		}
		
		sNombreFuncion = getCleanFuntionName(sNombreAtributoTabla);
		
		    sbRes.append("<select  onchange=\"").append(onchange).append("\" id='").append(sNombreAtributoTabla).append("' style=\"").append(style).append("\" name='").append(sNombreAtributoTabla).append("'>")		
				 .append("<option value='0'>--Por favor seleccione--</option>\n")
				 .append("</select>\n");
			sbRes.append("<script languaje='JavaScript'>\n")
				 .append("		var url = \"\"+getAppContextParaJS()+\"/combo/dependiente.do\";\n")			
				 .append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(sNombreFuncion)
				 		.append(" = new comboCtrlDependiente('"+sValorSelect+"',url,\n '").append(sNombreTabla).append("',\n 'form#").append(sNombreContenedor)
				 		.append(" select#").append(sNombreAtributoTabCEscape).append("',\n'");
			
				formatJSVariablesGenerico(sbRes,sNombreAtributoTablaPadre);
				 		
			sbRes.append("',\n'form#").append(sNombreContenedor).append(" select#");
			
				
				formatJSVariables(sbRes,sNombreAtrTabPadCEscape);
			
				sbRes.append("$(function(){\n")
				 .append("  $('form#").append(sNombreContenedor).append(" select#").append(sNombreTablaSinPaquetesB).append("').change(function(){\n")
				 .append("	  try{\n")
				 .append("	    cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(sNombreFuncion).append(".cargardep();\n")	 
				 .append("	  }catch (e) {\n")
				 .append("		alert(e);\n")
				 .append("	  }\n")
				 .append("  })\n")
				 .append("})\n")
				 .append("</script>\n");
		return sbRes.toString();
	}//procesaComboDependiente
	
	
	private void formatJSVariablesGenerico(StringBuffer sbRes ,String variables){
		
		if(variables.contains(",")){
			StringTokenizer st = new StringTokenizer(variables,",");
			String aux ="";
			Boolean firstExecution = true;
			while(st.hasMoreTokens()){
				aux = st.nextToken();
				if(st.hasMoreTokens()){
					if(firstExecution){
						sbRes.append(aux.trim()).append(",' \n");
						firstExecution = false;
					}else
						sbRes.append("+'").append(aux.trim()).append(",' \n");
					
				}else{
					sbRes.append("+'").append(aux.trim());
				}
				
			}
		
		}else{
			sbRes.append(variables);
			
		}
		
		
	}
	
	private void formatJSVariables(StringBuffer sbRes ,String variables){
		
		if(variables.contains(",")){
			StringTokenizer st = new StringTokenizer(variables,",");
			String aux ="";
			Boolean firstExecution = true;
			while(st.hasMoreTokens()){
				aux = st.nextToken();
				if(st.hasMoreTokens()){
					if(firstExecution){
						sbRes.append(aux.trim()).append(",' \n");
						firstExecution = false;
					}else
						sbRes.append("+'").append(aux.trim()).append(",' \n");
					
				}else{
					sbRes.append("+'").append(aux.trim()).append("'); \n");
				}
				
			}
		
		}else{
			sbRes.append(variables).append("');\n");
			
		}
		
		
	}
	
private String procesaComboDependienteRelacion(	String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor,
			String sNombreTablaPadre, String sNombreAtributoTablaPadre, String sNombreTablaRelacion, String sNombreAtributoTablaRelacion, String sNombreAtributoComparaRelacion){
StringBuffer sbRes 				= new StringBuffer();
String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
String sNombreTabPadSinPaquetes	= this.getNombreTablaSinPaquetes(sNombreTablaPadre);
String sNombreAtrTabPadCEscape	= this.getCadenaPuntosConEscape(sNombreAtributoTablaPadre);
String sNombreAtrTabPadSinHijo	= this.getCadenaSinPadre(sNombreAtributoTablaPadre, sNombreTabPadSinPaquetes);

sbRes.append("<select class=\"etiqueta2\" onfocus=\"").append(onfocus).append("\" onchange=\"").append(onchange).append("\" id=\"").append(sNombreAtributoTabla).append("\" style='").append(style).append("' name='").append(sNombreAtributoTabla).append("'>\n")
.append("<option value='0'>--Por favor seleccione--</option>\n")
.append("</select>\n");
sbRes.append("<script languaje='JavaScript'>\n")
.append("		var url = \"\"+getAppContextParaJS()+\"/combo/relacion.do\";\n")			
.append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase())
.append(" = new comboCtrlDependienteRelacion(url, '").append(sNombreTabla)
.append("', 'form#").append(sNombreContenedor).append(" select#").append(sNombreAtributoTabCEscape)
.append("','").append(sNombreAtrTabPadSinHijo)
.append("','form#").append(sNombreContenedor).append(" select#").append(sNombreAtrTabPadCEscape)
.append("','").append(sNombreTablaRelacion)
.append("','").append(sNombreAtributoTablaRelacion)
.append("','").append(sNombreAtributoComparaRelacion)

.append("');\n")	
.append("$(function(){\n")
.append("  $('form#").append(sNombreContenedor).append(" select#").append(sNombreAtrTabPadCEscape).append("').change(function(){\n")
.append("	  try{\n")
.append("	    cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargardeprel();\n")	 
.append("	  }catch (e) {\n")
.append("		alert(e);\n")
.append("	  }\n")
.append("  })\n")
.append("})\n")
.append("</script>\n");
return sbRes.toString();
}//procesaComboDependiente
	
	
	private String procesaComboDoblementeDependiente(	String sNombreTabla, String sNombreAtributoTabla, String sNombreContenedor,
					String sNombreTablaPadre, String sNombreAtributoTablaPadre, String sNombreTablaPadre2, String sNombreAtributoTablaPadre2 ){
		StringBuffer sbRes 				= new StringBuffer();
		String sNombreTablaSinPaquetes 	= this.getNombreTablaSinPaquetes(sNombreTabla);
		String sNombreAtributoTabCEscape= this.getCadenaPuntosConEscape(sNombreAtributoTabla);
		String sNombreTabPadSinPaquetes	= this.getNombreTablaSinPaquetes(sNombreTablaPadre);
		String sNombreTabPadSinPaquetes2= this.getNombreTablaSinPaquetes(sNombreTablaPadre2);
		String sNombreAtrTabPadCEscape	= this.getCadenaPuntosConEscape(sNombreAtributoTablaPadre);
		String sNombreAtrTabPadCEscape2	= this.getCadenaPuntosConEscape(sNombreAtributoTablaPadre2);
		String sNombreAtrTabPadSinHijo	= this.getCadenaSinPadre(sNombreAtributoTablaPadre, sNombreTabPadSinPaquetes);
		String sNombreAtrTabPadSinHijo2	= this.getCadenaSinPadre(sNombreAtributoTablaPadre2, sNombreTabPadSinPaquetes2);
		
		sbRes.append("<select class=\"etiqueta2\" onfocus=\"").append(onfocus).append("\" onchange=\"").append(onchange).append("\" id=\"").append(sNombreAtributoTabla).append("\" style='").append(style).append("' name='").append(sNombreAtributoTabla).append("'>\n")
		.append("<option value='0'>--Por favor seleccione--</option>\n")
		.append("</select>\n");
		sbRes.append("<script languaje='JavaScript'>\n")
		.append("		var url = \"\"+getAppContextParaJS()+\"/combo/doble.do\";\n")	
		
		
		
		.append("cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase())
		
		
		.append(" = new comboCtrlDblDependiente(url, '")//pUrl
												.append(sNombreTabla)//pEntidad
												.append("', 'form#").append(sNombreContenedor)//pIdHtml-> form#miFormulario select# miObjHijo\\.mipropiedadHijo
												.append(" select#").append(sNombreAtributoTabCEscape).append("','")
												.append(sNombreAtrTabPadSinHijo)//pEntidadPadre
												.append("','form#").append(sNombreContenedor)//pIdHtmlPadre-> form#miFormulario select#miObjPadre\\mipropiedadPadre
												.append(" select#").append(sNombreAtrTabPadCEscape).append("','")
												
												.append(sNombreAtrTabPadSinHijo2)//pEntidadPadre2
												.append("','form#").append(sNombreContenedor)//pIdHtmlPadre2-> form#miFormulario select#miObjPadre\\mipropiedadPadre
												.append(" select#").append(sNombreAtrTabPadCEscape2).append("');\n")											
		.append("$(function(){\n")
		.append("  $('form#").append(sNombreContenedor).append(" select#").append(sNombreAtrTabPadCEscape).append("').change(function(){\n")
		.append("	  try{\n")
		.append("	    cmb").append(sNombreContenedor).append(sNombreTablaSinPaquetes.toLowerCase()).append(".cargardbldep();\n")	 	
		.append("	  }catch (e) {\n")
		.append("		alert(e);\n")
		.append("	  }\n")
		.append("  })\n")
		.append("})\n")
		.append("$(function(){\n")
		.append("  $('form#").append(sNombreContenedor).append(" select#").append(sNombreAtributoTabCEscape).append("').change(function(){\n")
		.append("	  try{\n")
		.append("	   activa('txtNombre'); \n")	 
		.append("	   activa('txtRP'); \n")	 
		.append("	   activa('txtDV'); \n")	 
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
	
	private String getCleanFuntionName(String sCadena){
		return sCadena.replaceAll("\\.", "");
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

	public String getEntidadPadre2() {
		return entidadPadre2;
	}

	public void setEntidadPadre2(String entidadPadre2) {
		this.entidadPadre2 = entidadPadre2;
	}

	public String getIdHtmlPadre2() {
		return idHtmlPadre2;
	}

	public void setIdHtmlPadre2(String idHtmlPadre2) {
		this.idHtmlPadre2 = idHtmlPadre2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getOverWriteId() {
		return overWriteId;
	}

	public void setOverWriteId(String overWriteId) {
		this.overWriteId = overWriteId;
	}

	public String getOverWriteValue() {
		return overWriteValue;
	}

	public void setOverWriteValue(String overWriteValue) {
		this.overWriteValue = overWriteValue;
	}

	public String getEntidadRelacion() {
		return entidadRelacion;
	}

	public void setEntidadRelacion(String entidadRelacion) {
		this.entidadRelacion = entidadRelacion;
	}

	public String getIdHtmlRelacion() {
		return idHtmlRelacion;
	}

	public void setIdHtmlRelacion(String idHtmlRelacion) {
		this.idHtmlRelacion = idHtmlRelacion;
	}

	public String getCompValorRelacion() {
		return compValorRelacion;
	}

	public void setCompValorRelacion(String compValorRelacion) {
		this.compValorRelacion = compValorRelacion;
	}

	public String getIdSustituto() {
		return idSustituto;
	}

	public void setIdSustituto(String idSustituto) {
		this.idSustituto = idSustituto;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getIdHtmlValor() {
		return idHtmlValor;
	}

	public void setIdHtmlValor(String idHtmlValor) {
		this.idHtmlValor = idHtmlValor;
	}
	

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	


	
}

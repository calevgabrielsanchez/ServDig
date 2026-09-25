<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="sp">
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/catalogos/trabajadores/trabajadores.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
				src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>	
	<div id="cuerpo">
		
		<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a>Trabajadores</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		  </div>
		  
  		  <div id="dgTrabajadores"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogTrabajadores" style="background-color: #f2fff2;">
			<table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
			<td valign="top">
				<form action="/catalogo/trabajadores/consultar.do" 	method="post" id="trabajadoresForm">
					<input type="hidden"  id="cveSubdelegcionOficialTrab" value="<%=request.getAttribute("valCveSubdel") %>"/>	
						<fieldset>
							
  
      <table width="100%" class="tablaverde2" cellpadding="2">
      	<tbody>
      	<tr valign="top" class="impar">
			<td align="left" colspan="4">&nbsp;</td>
		</tr>
  <tr class="par">
    <td width="25%" class="etiqueta2">
   <span class="etiquetaError">*</span>Folio de Correcci&oacute;n:</td>
    <td width="25%"><input type="text" id="folioCorreccion" name="folioCorreccion" size="20" maxlength="18" onblur="this.value=(this.value).toUpperCase();" 
			 onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion','trabajadoresForm');"/><label for="folioCorreccion"></label></td>
    <td width="25%" class="etiqueta2"><label>Per&iacute;odo:</label></td>
    <td width="25%"><input type="text" id="periodo" name="periodo" size="20" maxlength="4"  onkeyup="validaCampo('PermiteSoloNumeros','periodo','trabajadoresForm');"/></td>
  </tr>
  <tr class="par">
    <td width="25%">
    <label class="etiqueta2">Nombre Asegurado:</label></td>
    <td width="25%"><input  type="text" id="nombreAsegurado" name="nombreAsegurado" onkeyup="validaCampo('PermiteSoloLetrasYPunto','nombreAsegurado','trabajadoresForm');" size="20" maxlength="30" /></td>
    <td width="25%"><label class="etiqueta2">Apellido Paterno:</label></td>
    <td width="25%"><input type="text" id="apPaternoAsegurado" name="apPaternoAsegurado" onkeyup="validaCampo('PermiteSoloLetrasYPunto','apPaternoAsegurado','trabajadoresForm');" size="20" maxlength="30" /></td>
  </tr>
  <tr class="par">
    <td width="25%"><label class="etiqueta2">Apellido Materno:</label></td>
    <td width="25%"><input type="text" id="apMaternoAsegurado" name="apMaternoAsegurado" onkeyup="validaCampo('PermiteSoloLetrasYPunto','apMaternoAsegurado','trabajadoresForm');" size="20" maxlength="30" /></td>
    <td width="25%"><label class="etiqueta2">N&uacute;mero de Seguro Social:</label></td>
    <td width="25%"><input  type="text" id="nuNss" name="nuNss" size="20" maxlength="11" 
    					onkeyup="validaCampo('PermiteSoloNumeros','nuNss','trabajadoresForm');"
    /></td>
  </tr>
  <tr class="par">
    <td width="25%"><label class="etiqueta2">RFC:</label></td>
    <td width="25%">
      <input type="text" id="txRfc" name="txRfc" maxlength="13" size="20"
      		onkeyup="validaCampo('noCaracteresEspeciales','txRfc','trabajadoresForm');"
      />
    </td>
    <td width="25%"><label class="etiqueta2">Indicador:</label></td>
    <td width="25%">
     <select id="indicadorTrabajador" name="indicadorTrabajador">
			<option value="-1">Seleccione</option>
  			<option value="1">Prueba Selectiva</option>
  			<option value="2">Salarios Topados</option>
  			<option value="3">An&aacute;lisis Tiempo Extra</option>
  			<option value="4">An&aacute;lisis Honorarios</option>
  	  </select> 
    </td>
  </tr>
  <tr valign="top" class="impar">
	<td align="left" colspan="4">&nbsp;</td>
	</tr>
  </tbody>
	</table>
	
<table width="100%" border="0" cellspacing="8" cellpadding="0">
 
  <tr align="center">
		<td align="center" width="100px" colspan="4">
			<a  id="btnBusquedaTrabajadores" href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a> &nbsp;&nbsp;
			<a href="#" onclick="javascript:registra();"><span class="boton">Agregar</span></a>
		</td>
   </tr>	
</table>      
						</fieldSet>
				</form>
				</td>
				</tr>
			</table>
		</div>
	</div>
		
		<div id="registroTrabajadores">
				<jsp:include page="trabajadoresRegistro.jsp" />
			</div>
		
			
			<div id="trabajadoresData" style="overflow: auto; width:900px; height:320px;" align="center" >
				<jsp:include page="trabajadoresData.jsp" />
			</div>
			
			<div id="trabajadoresBorrar" align="center">
				<jsp:include page="trabajadoresBorrar.jsp" />
			</div>
			
			<div id="trabajadoresButtons">
				<jsp:include page="trabajadoresButtons.jsp" />
			</div>
		
	</div>

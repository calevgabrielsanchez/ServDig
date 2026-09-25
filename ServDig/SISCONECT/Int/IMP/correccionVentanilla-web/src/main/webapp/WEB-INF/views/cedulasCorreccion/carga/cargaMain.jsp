<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html lang="sp">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/cedulasConstruccion/cedulasConstruccionCarga.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>

	
	<script type="text/javascript">
	
		function validaCarga(){
			
			document.forms[0].action = '<%=request.getContextPath()%>/cedulasCorreccion/carga/archivo.do';
			
			return true;
			
		}
		
		
		function validaCampoFolio(){
			if($('#folioCorreccion').val()!='' && !validaFolioInternet($('#folioCorreccion').val())){
				$('#folioCorreccion').val('');alert('Folio generado desde internet');
				return;
			}
		}
	</script>
<div id="cuerpo">
	<div class="menu_principal" style="height: 2em !important;">
		<!--inicia menu principal-->
  		   		<div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Carga de C&eacute;dulas</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
  		      </div>
		    </div>
		    
 		    <div style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		    	<div id="wrapperDialogDescCedulaCorreccion" style="background-color: #f2fff2;">
		    	<input type="hidden" id="idMensaje" value="<%=request.getAttribute("mensaje")%>">
		    	<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
		        <form enctype="multipart/form-data" action="" id="cargaMainForm" onsubmit="return validaCarga()" method="post">
		    		<fieldset>
		    			<table style="width: 900px" align="center">
		    				<tr valign="middle">
		    					<td align="center" width="900px">
		    						<table class="tablaverde2" style="width: 900px">
		    							<tbody>
		    							<tr valign="middle">
											<td align="center" width="900px">
												<span class="etiquetaError"><c:out value="${msg}"></c:out></span> 
											</td>
										</tr>
		    								<tr valign="top" class="impar">
												<td align="left" colspan="4">&nbsp;</td>
											</tr>
											<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>Folio Correcci&oacute;n:
											</td>
											<td align="left" width="100px" colspan="1">
											<input type="text" value="${folioCorreccion}" name="folioCorreccion" id="folioCorreccion" size="20" maxlength="18" onchange="validaCampoFolio()" onblur="this.value=(this.value).toUpperCase();" 
											 onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion');"/><label for="folioCorreccion"></label>
											</td>
											</tr>
											<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>C&eacute;dula:
											</td>
											<td align="left" width="100px" colspan="1"><label for="idArchivoCarga"></label>
											<select id="idArchivoCarga" name="idArchivoCarga">
											    <option value="">Seleccione</option>
  												<option value="1">Cedula A</option>
  												<option value="2">Cedula G</option>
  												<option value="3">Cedula H</option>
  												<option value="4">Cedula I</option>
  												<option value="5">Cedula O</option>
  												<option value="6">Cedula Q</option>
  												<option value="7">Detalle de Trabajadores</option>
  												<option value="8">COP Pagadas</option>
											</select> 
											</td>
											</tr>
											
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>Cargar Archivo:
											</td>
											<td align="left" width="100px" colspan="1">
											<input type="file" name="fileData" id="fileData"/><label for="fileData"></label>
											</td>
										</tr>
										<tr align="center">
										  <td align="center" width="100px" colspan="4">
										  &nbsp;
										  </td>
										</tr>	
										<tr align="center">
										  <td align="center" width="100px" colspan="4">
										  <!-- <input type="submit" value="Cargar" class="boton"> -->
										  		<a href="#" id="btnCargar"><span class="boton">Cargar</span></a>
										  		<a href="<%=request.getContextPath() %>/cedulasCorreccion/monitor.do" id="btnMonitor"><span class="boton">Ir a Monitor</span></a>
										  </td>
										</tr>	
		    							</tbody>
		    						</table>
		    					</td>
		    				</tr>
		    			</table>
		    		</fieldset>
		    	</form>
		    	</div>
		    </div>
		    
</div>
</html>
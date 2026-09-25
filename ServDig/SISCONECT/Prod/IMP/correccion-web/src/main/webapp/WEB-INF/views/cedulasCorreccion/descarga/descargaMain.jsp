<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="sp">
 

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>

<script>

	function validar(){
		
		var idArchivoDescarga = document.getElementById("idArchivoDescarga").value;
		var folioCorreccion = document.getElementById("folioCorreccion").value;
		var periodo = document.getElementById("periodo").value;
		var cveSubDelOficial = document.getElementById("cveSubdelegcionOficialCedula").value;
				
		
		if(!tieneDato(folioCorreccion,"Folio Correcci\u00F3n")) return false;
		if(!checaCero(periodo,"Ejercicio")) return false;
		if(!longitudMandatoria(periodo,4,"Ejercicio")) return false;
		if(!checaCero(idArchivoDescarga,"Tipo de Documento")) return false;
		
		if(folioCorreccion.substring(0,4) == cveSubDelOficial){
			bloquear();
						 
			var URL_ACTION = 'cedulasCorreccion/descarga/descargaWindow.do?idArchivoDescarga='+idArchivoDescarga+'&folioCorreccion='+folioCorreccion+'&periodo='+periodo;
			var resultado = openDownloadFileWindow('<%=request.getContextPath() %>',URL_ACTION);
			document.forms[0].action='<%=request.getContextPath()%>/cedulasCorreccion/descarga/descargaReturn.do';
		
		 }else{
			 alert("El Folio de Correcci\u00F3n NO corresponde a la Subdelegaci\u00F3n");
				return false;
		}
		
	}
	
	function obtenerPeriodos(){
		
		var folioCorreccion = document.getElementById("folioCorreccion").value;
		
		if(folioCorreccion!=""){
			var variable = '{"folioCorreccion":'+'"'+folioCorreccion+'"}';
			
			var variableJson = jQuery.parseJSON(variable);
						
			bloquear();
			
			$.postJSON(getAppContextParaJS()+"/cedulasCorreccion/descarga/getPeriodosCorreccion.do",variableJson,function(data) { 
				if(data == null && data.length>0){
			
					alert('El aviso no tiene ejercicio asignado');
					desbloquear();
				}else{
			
					var myselect=document.getElementById("periodo");
					myselect.options.length = 1;
					for(var i = 0 ; i < data.length ; i++){
						myselect.add(new Option(data[i], data[i]));
					}	
					desbloquear();
				}
				
			});
	
		}
		
	}
	

	function validaDescargaFolioValor(){		
		if($('#folioCorreccion').val()!=undefined && $('#folioCorreccion').val()!='' && !validaFolioInternet($('#folioCorreccion').val())){
			$('#folioCorreccion').val('');
			alert('Folio generado desde internet');
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
		            <li><a> Descarga de C&eacute;dulas</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgGasto"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogDescCedulaCorreccion" style="background-color: #f2fff2;">	
		<input type="hidden" id="idContexto" value="<%=request.getContextPath()%>">
			<form  action="descarga/descargaReturn.do" method="post" id="descCedulaCorreccionForm" onsubmit="return validar()">	
			<input type="hidden"  id="cveSubdelegcionOficialCedula" value="<%=request.getAttribute("valCveSubdelCedula") %>"/>	
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
									<span class="etiquetaError"><c:out value="${msg}"></c:out></span> 
							</td>
						</tr>
						<tr valign="middle">
						
							<td align="center" width="900px">
			
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>Folio Corrección:
											</td>
											<td align="left" width="100px" colspan="1">
										<input type="text" name="folioCorreccion" id="folioCorreccion" size="20" maxlength="18" onblur="this.value=(this.value).toUpperCase();obtenerPeriodos();validaDescargaFolioValor();"  onkeyup="validaCampo('noCaracteresEspeciales','folioCorreccion','descCedulaCorreccionForm');" /><label for="folioCorreccion"></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>Ejercicio:
											<td align="left" width="100px" colspan="1">
												<select id="periodo" name="periodo">
													<option id="">--Por favor seleccione--</option>
												</select>
											</td>
										</tr>
										
										<tr valign="top" class="par">
											<td align="left" width="100px" colspan="1" class="etiqueta2">
											<span class="etiquetaError">*</span>Cédula:
											</td>
											<td align="left" width="100px" colspan="1"><label for="idArchivoDescarga"></label>
												<select name="idArchivoDescarga"  id="idArchivoDescarga">
												    <option value="">Seleccione</option>
													<option value="1">Cedula A</option>
													<option value="2">Cedula G</option>
													<option value="3">Cedula H</option>
													<option value="4">Cedula I</option>
													<option value="5">Cedula O</option>
													<option value="6">Cedula Q</option>
													<option value="7">Detalle Trabajadores</option>
													<option value="8">COPs Pagadas</option>
												</select>
											</td>
										</tr>
										<tr align="center">
										  <td align="center" width="100px" colspan="4">
										  &nbsp;
										  </td>
										</tr>	
										<tr align="center">
										  <td align="center" width="100px" colspan="4">
										  	<input type="submit" value="Descargar" class="boton">
										  </td>
										</tr>	
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
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
	
<script>
$(document).ready(function() {
	
	triggerPatronInternet('','folioCorreccion','folioCorreccion','onblur');
	
});

</script>
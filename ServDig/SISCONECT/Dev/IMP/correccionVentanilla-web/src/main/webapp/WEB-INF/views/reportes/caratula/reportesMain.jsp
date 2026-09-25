<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<script>
    var vecesCam=0;
	function validar(){
		
		var tipoReporte = document.getElementById("tipoReporte").value;
		var delegaCampo = document.getElementById("delegacion").value;
		var subdelegaCampo = document.getElementById("subdelegacion").value;
		
		
		if(!checaCero(tipoReporte,"Tipo de reporte") || !checaCero(delegaCampo,"Delegacion") || !checaCero(subdelegaCampo,"Subdelegacion")) return false;

		bloquear();
			
		var dele='&delegaParam='+$("form#reportesCaratulaForm #delegacion").val();
		var subdele='&subdelegaParam='+$("form#reportesCaratulaForm #subdelegacion").val();
		var nombreDelega='&nombreDelega='+$("form#reportesCaratulaForm #delegacion option:selected").text();
		var nombreSubdele='&nombreSubDelega='+$("form#reportesCaratulaForm #subdelegacion option:selected").text();
		var URL_ACTION = 'reportes/caratula/reportWindow.do?tipoReporte='+tipoReporte+dele+subdele+nombreDelega+nombreSubdele;
		var resultado = openDownloadFileWindow('<%=request.getContextPath() %>',URL_ACTION);
		document.forms[0].action='<%=request.getContextPath()%>/reportes/caratula/caratulaRetun.do';
		
	}
	var delega;
	var subDelega;
	
	function bloquarCampos(){
		
		$("form#reportesCaratulaForm #delegacion").trigger('change');		
		delega = document.getElementById("valorDe").value;	
		subDelega = document.getElementById("valorSubde").value;
		if(delega>0){
			$("form#reportesCaratulaForm #delegacion").attr("disabled","disabled");
		}
		 if(subDelega>0){
			$("form#reportesCaratulaForm #subdelegacion").val(subDelega);
			$("form#reportesCaratulaForm #subdelegacion").attr("disabled","disabled");
			setTimeout(recargaSubde, 1000);
		} 		
	}
	

	function recargaSubde(){
		$('form#reportesCaratulaForm #subdelegacion').val(subDelega);
	}

	
	
</script>

		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Reportes Control Gestión</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <!-- <div id="dgGasto" title="Reportes Control Gestión" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;"> -->
		<div id="wrapperDialogRepControlGestion" style="background-color: #f2fff2;">	
			<form:form modelAttribute="modeloReporte" action="caratula/caratulaRetun.do"
						method="post" id="reportesCaratulaForm" onsubmit="return validar()">
						<form:hidden path="delegacion" id="valorDe"/>
						<form:hidden path="subdelegacion" id="valorSubde"/>
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
	
								<table class="tablaverde2" style="width: 900px">
									<tr>
										<td class="error">
											<span class="required">${modeloReporte.mensaje}</span>
										</td>
									</tr>
									
									
									<tr align="left">
										<td>
											<label>Delegaci&oacuten:</label>
										</td>
										<td>
										<div id="dgGasto"  style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
											<combo:creaCombo
												    entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion"
													idHtml="delegacion"
													idHtmlContenedor="reportesCaratulaForm"										
													idHtmlValor="${modeloReporte.delegacion}" 
													style="width: 400px"
										/>
										</div>
										</td>
									</tr>
									
									
									
									<tr align="left">
										<td>
											<label>Subdelegaci&oacuten:</label>
										</td>
										<td>
										<div   style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
										<combo:creaCombo entidad="mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion"
												idHtml="subdelegacion" 
												idHtmlPadre="delegacion@sacDelegacion.cvePk"
												entidadPadre="mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion"	
												idHtmlContenedor="reportesCaratulaForm"
												style="width: 400px"
										/>
										</div>
										</td>
									</tr>
									<tr align="left">
										<td>
											<label>Reporte:</label>											
										</td>
										<td>
										<div   style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
											<form:select path="tipoReporte" id="tipoReporte" cssStyle="width:400px">
												<form:option value="-1">--Por favor seleccione--</form:option>
												<form:options items="${modeloReporte.lsTipoReporte}" itemValue="id" itemLabel="label"/>												
											</form:select>
										</div>
										</td>
									</tr>
									<tr>
										<td><input type="submit" value="Generar Reporte"/></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
			
				</fieldset>
			</form:form>							    
	  	</div>
<!-- </div> -->
		   	    
</div>



<script type="text/javascript">
$(document).ready(function() {	
	setTimeout(bloquarCampos, 1000);	
});
</script>
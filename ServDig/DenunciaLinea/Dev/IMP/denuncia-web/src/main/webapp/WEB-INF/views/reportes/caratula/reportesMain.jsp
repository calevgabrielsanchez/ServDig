<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>

<script>

	function validar(){
		
		var tipoReporte = document.getElementById("tipoReporte").value;
		
		if(!checaCero(tipoReporte,"Tipo de reporte")) return false;

		bloquear();

		var URL_ACTION = 'reportes/caratula/reportWindow.do?tipoReporte='+tipoReporte;
		var resultado = openDownloadFileWindow('<%=request.getContextPath() %>',URL_ACTION);
		document.forms[0].action='<%=request.getContextPath()%>/reportes/caratula/caratulaRetun.do';
		
	}
	
</script>

		
		<div id="cuerpo">
			
			
			<div class="menu_principal" style="height: 2em !important;"> 
		      <!--inicia menu principal-->
		      <div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Reportes Control Gesti�n</a></li>
		          </ul>
		        </div>
		        <!--fin centrado--> 
		      </div>
		    </div>
		    
		    <div id="dgGasto" title="Reportes Control Gesti�n" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogRepControlGestion" style="background-color: #f2fff2;">	
			<form:form modelAttribute="modeloReporte" action="caratula/caratulaRetun.do"
						method="post" id="reportesCaratulaForm" onsubmit="return validar()">
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
									<tr>
										<td>
											<form:select path="tipoReporte" id="tipoReporte">
												<form:option value="-1">--Por favor seleccione--</form:option>
												<form:options items="${modeloReporte.lsTipoReporte}" itemValue="id" itemLabel="label"/>												
											</form:select>
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
	</div>
		   	    
</div>	    


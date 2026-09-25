<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/delta/solicitudProrroga/autorizacionProrroga/autorizacionProrroga.js"></script>
<div id="cuerpo">

	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
			    <ul style="height: 1em !important;">
			    	<li><a> AUTORIZACI&Oacute;N DE PR&Oacute;RROGA  </a></li>
			  	</ul>
			</div>
		<!--fin centrado--> 
	  </div>
	</div>
	
	<div id="dgBusqueda">
		<div id="wrapperDialogBusqueda" style="background-color: #f2fff2;">	
			<form:form modelAttribute="crtProrroga" method="post" id="crtProrrogaForm">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
										<tr valign="top" class="par">
											<td align="left" width="15%">
												<label style="color: red;">* </label>
												<label class="etiqueta2">Fecha Inicial</label>
											</td>
											<td align="left" width="35%">
												<form:input path="fecInicio" maxlength="10" id="fecInicio" onchange="jsValidaFecIni(this.value);"/>
												<label id="labelFecInicio"></label>
											</td>
											<td align="left" width="15%">
												<label style="color: red;">* </label>
												<label class="etiqueta2">Fecha Final</label>
											</td>
											<td align="left" width="35%">
												<form:input path="fecFinal" maxlength="10" id="fecFinal" onchange="jsValidaFecFinal(this.value);"/>
												<label id="labelFecFinal"></label>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>	
										</tr>
										<tr valign="top" class="impar" >
											<td align="center" colspan="4">
												<a href="javascript:buscar();" onclick="javascript:buscar();">
													<span class="boton">Buscar</span>
												</a>
											</td>	
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</fieldset>
			</form:form>
		</div>
	</div>
	<br>
	<div id="autorizacionData" style="overflow:scroll; width: auto;">
		<jsp:include page="autorizacionProrrogaData.jsp"/>
	</div>
	
	<div id="autorizacionButtons">
		<jsp:include page="autorizacionProrrogaButtons.jsp"/>
	</div>
	
	
</div>

<div id="autorizacionConfirmar">
		<jsp:include page="autorizacionProrrogaGuardar.jsp"/>
	</div>
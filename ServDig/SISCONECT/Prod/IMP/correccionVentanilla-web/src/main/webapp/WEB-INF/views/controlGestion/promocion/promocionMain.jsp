<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<html lang="sp">

<style>
	label.error { float: none; font-size:xx-small; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
 
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/promocion/promocion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/calendarios.js"></script>


		
<div id="cuerpo">
			
	<div class="menu_principal" style="height: 2em !important;"> 
		<!--inicia menu principal-->
		<div align="center">
			<div class="centrado">
		    	<ul style="height: 1em !important;">
					<li><a> Promoci&oacute;n Satic A</a></li>
				</ul>
			</div>
		<!--fin centrado--> 
		</div>
	</div>
		    
	<div id="dgPromocion" style="background-color: white !important;  opacity: .7 !important ; filter:Alpha(Opacity=70) !important;">
		<div id="wrapperDialogPromocion" style="background-color: #f2fff2;">	
			<form:form modelAttribute="crtDeteccion" action="/catalogo/promocion/consultar.do"
						method="post" id="promocionForm">
				<input type="hidden" name="cveFkPatron" id="cveFkPatron">
				<fieldset>
					<table style="width: 900px" align="center">
						<tr valign="middle">
							<td align="center" width="900px">
							<div id="labelError"></div>	
								<table class="tablaverde2" style="width: 900px">
									<tbody>
										<tr valign="top" class="impar">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">   	
										   	<td align="left" width="25%" colspan="4">
											   	<form:label id="fechaIncialLabel" for="fechaIncial" path="fechaIncial"
												cssErrorClass="error"><span class="required">*</span><font class="etiqueta2">Fecha de Detecci&oacute;n De:</font></form:label>																																				
												<form:input path="fechaIncial" id="fechaIncial" readonly="true"
												onchange="jsValidarFecOficioPromo();jsValidaFechasLimite();"/>
												<form:errors path="fechaIncial" />
												<form:label id="fechaFinalLabel" for="fechaFinal" path="fechaFinal"
												cssErrorClass="error"><span class="required">*</span><font>al:</font></form:label>
												<form:input path="fechaFinal" id="fechaFinal" readonly="true"
												onchange="jsValidarFecOficioPromo();jsValidaFechasLimite();"/>
												<label id="labelPeriodo"></label>
											</td>																						
										</tr>	
										<tr valign="top" class="par">   												
											<td align="left" width="25%" class="etiqueta2">
											    	<form:label for="nuFoliodeteccion" id="nuFoliodeteccionLabel" path="nuFoliodeteccion" 
										    		cssErrorClass="error"><font class="etiqueta2">Folio de Detecci&oacute;n: </font></form:label>	    		
											</td>
											<td align="left" width="25%">
												<form:input path="nuFoliodeteccion" id="nuFoliodeteccion" size="20" maxlength="18" onkeyup="validaCampo('noCaracteresEspeciales','nuFoliodeteccion','promocionForm')"/>
												<form:errors path="nuFoliodeteccion" />
											</td>
											<td align="left" width="25%">
											    <form:label for="regPatron" id="regPatronLabel" path="regPatron" 
										    	cssErrorClass="error"><font class="etiqueta2">Registro Patronal:</font> </form:label>
											</td>
											<td align="left" width="100px" colspan="1">
												<form:input path="regPatron" id="regPatron" size="20" maxlength="10" onkeyup="validaCampo('noCaracteresEspeciales','regPatron','promocionForm')" />
												<form:errors path="regPatron" />
												<label id="labelRegPatron"></label>
											</td>											
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
										</tr>
										<tr valign="top" class="par">
											<td align="center" width="80px" colspan="4">
												<a href="#" onclick="javascript:buscar();"><span class="boton">Buscar</span></a>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="#" onclick="javascript:limpiar();"><span class="boton">Limpiar</span></a>
											</td>
										</tr>
										<tr valign="top" class="par">
											<td align="left" colspan="4">&nbsp;</td>
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
			</form:form>							    
	  	</div>
	</div>		
	
	<div id="promocionObrasDeteccion" style="overflow: auto; width:950px; height:300px;display: none;" align="center" class="centrado">
		<jsp:include page="registrosDeteccion_Promocion.jsp" />
	</div>	
	<br>	
	<div id="btnVisualizar" style="display:none" align="center">
		<a href="#" onclick="javascript:mostrar();"><span class="boton">Ver</span></a>
	</div>
	<div id="promocionDatosCompletosDet">
		<jsp:include page="datosCompletosDeteccion.jsp" />
	</div>					    	   
	<div id="registroProm">
		<jsp:include page="registroPromocion.jsp" />
	</div>	
	<div id="confirmarSaticA" align="center" class="centrado" >
		<jsp:include page="confirmarSaticA.jsp" />
	</div>		
	<div id="promoverSaticA" align="center" class="centrado" >
		<jsp:include page="promoverSaticA.jsp" />
	</div>		    	   
</div>	    
	

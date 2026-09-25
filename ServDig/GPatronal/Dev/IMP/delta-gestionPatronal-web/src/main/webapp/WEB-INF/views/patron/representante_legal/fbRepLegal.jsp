<%@ include file="../../general/taglibs.jsp"%>

<style>
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/repLegal.js" htmlEscape="true" />"></script>
	
<center>
<div id="homecontenido" class="contenedor">
	<div class="row" style="height: 500px;">
		<div class="cell" style="padding: 20px;">
			<div class="row" style="height: 500px;">
				<div id="representanteLegal" style="height: 100%">
					<!-- JSP principal del modulo de Representante Legal -->
					<c:set var="cveIdPatronSujetoObligado"
 						value="${sujetoObligado.cveIdSujetoObligado}"></c:set> 
 					<c:set var="tipoPersonaFiscal"
 						value="${sujetoObligado.tipoPersonaFiscal}"></c:set><!--<c:out value="${cveIdPatronSujetoObligado}"></c:out>--><!--<c:out value="${tipoPersonaFiscal}"></c:out>--> 
					<input type="hidden" id="tipoPersonaFiscalHidden" value="${tipoPersonaFiscal}">
					<div style="display: none;"></div>
					<input type="hidden" id="idSolicitudRL" name="idSolicitudRL" value="${idSolicituRL}"/>
					<h3>Actual</h3>
					<div id="lista" style="display: table-row !important;">									
						<table id="tbRepresentanteLegal" style="width: 100%; vertical-align: top;">											
							<thead></thead>
							<tbody style="width: 100%;"></tbody>
							<tfoot></tfoot>
						</table>
					</div>				
					<br><br>
					<table style="width: 100%;">
						<tr>
							<td>
								<div class="opciones">
									<div class="opcion">
										<form>
											<input type="button" class="mboton" name="regresar" style="font-size: .8em !important;"
												onclick="history.back();"
											value="<spring:message code="label.regresar" />"></input>
									
											<input type="button"
											onclick="fnOpenDialogNuevoRepresentanteLegal();"
											class="mboton" value="Agregar"
											style="font-size: .8em !important;">
									
											<input type="button"
											onclick="fnOpenDialogEliminarRepresentanteLegal();"
											class="mboton" value="Eliminar"
											style="font-size: .8em !important;">
											<input type="button"
											onclick="fnOpenDialogModificarRepresentanteLegal();"
											class="mboton" value="Modificar"
											style="font-size: .8em !important;">										
									</form>
								</div>
							</td>
						</tr>						
					<h3>Tramite</h3>																	
					<div id="listaForSession" style="width: 100%;">						
						<table id="tbRepresentanteLegalForSession" border=2
							style="width: 100%; vertical-align: top;">
							<thead></thead>
							<tbody style="width: 100%;"></tbody>											
						</table>
					</div></tr>
										</table>																									
																				
			</div>		
			
			<table>
			<tr>
				<td>
					<form>
						<div class="opcion">
							<input type="button"
							onclick="actualizarDatosRepLegal('finalizar');" class="mboton"
							name="aDatosRepLegal" value="<spring:message code="label.finalizar"/>" />
						</div>
					</form>
				</td>
			</tr>
			</table>																														
				<div id="dgNuevoRepresentanteLegal" title="Agregar elemento"
					style="background-color: white !important;">

					<jsp:include page="nuevo.jsp"></jsp:include>
				</div>
				<div id="dgEliminarRepresentanteLegal" title="¿Eliminar elemento?">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>El registro
						seleccionado será eliminado, ¿Esta Ud. seguro?
					</p>
					</br> <span id="errorNegocioLabel" class=" hiddenElement error"></span>
				</div>
				<div id="dgErrorSinSeleccionRepresentanteLegal"
					title="Debe seleccionar un elemento">
					<p>
						<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>No se ha
						seleccionado ningun registro para la acción.
					</p>
				</div>
				<div id="dgModificarRepresentanteLegal" title="Modificar elemento"
					style="background-color: white !important;">

					<jsp:include page="modificar.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dgActualizaDatosRepLegal" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.datos.rep.legal.actualizados" />
	</p>
</div>
</center>



<div id="representanteLegalConsultar"   style="display:none;">
<table>
<tr>
<td>	
	<legend class="separadorseccion"> 
			<center>Representante Legal</center>
	</legend>
	</td>
	</tr>	
</table>
<table>
<tr>
	<td>
	<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="rowDetalleSujetoObligado" style="width: 1000px;" >
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />					
					<div class="cell form-comment" id="celdaCaptura" style=" float:right;  width:800px !important; height: 100% !important; ">
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
						<form:form modelAttribute="sujetoObligado" action="${contextpath}/fb/consultarRepLegal" id="registroForm2">
						<table boder=2>						
						<tr>
							<td width=400>					
								<fieldset>								
										<fieldset class="fsInterno">
											<label style="width:85%">
												Poder para: &nbsp; &nbsp;&nbsp; &nbsp;Actos de Administraci&oacute;n
											</label>											
											<form:checkbox path="cveIdSujetoObligado" value="1" cssStyle="width:5%"/>
										</fieldset>																															
										<fieldset class="fsInterno">
											<label style="width:35%">
												Primer Apellido:
											</label>											
											<form:input readonly="true" path="fisica.nombre" maxlength="14" cssStyle="width:40%"/>
										</fieldset>																															
										<fieldset class="fsInterno">
											<label style="width:35%">
												Nombre(s):
											</label>
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>																															
										<fieldset class="fsInterno">
											<label style="width:35%">
												CURP:
											</label>
											<form:input readonly="true" path="fisica.curp" maxlength="14" cssStyle="width:40%"/>
										</fieldset>												
										<fieldset class="fsInterno">
											<label style="width:35%">
												Tel&eacute;fono Fijo:
											</label>
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>
										<fieldset class="fsInterno">
											<label style="width:35%">
												Tel&eacute;fono m&oacute;vil con clave de larga distancia:
											</label>
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>												
																						
														
								</fieldset>											
							</td>
							<td width=400>										
								<fieldset>							
										<fieldset class="fsInterno">
											<label style="width:35%">
												Actos de Dominio
											</label>
											<form:checkbox path="cveIdSujetoObligado" value="1" cssStyle="width:10%"/> 											
										</fieldset>
										<fieldset class="fsInterno">
											<label style="width:35%">
												Segundo Apellido:
											</label>
											<form:input readonly="true" path="fisica.segundoApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>
										<fieldset class="fsInterno">
											<label style="width:35%">
												RFC:
											</label>
											<form:input readonly="true" path="fisica.rfc" maxlength="14" cssStyle="width:40%"/>
										</fieldset>																
										<fieldset class="fsInterno">
											<label style="width:35%">
												Ext:
											</label>
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>																
										<fieldset class="fsInterno">
											<label style="width:35%">
												Direcci&oacute;n de Correo electr&oacute;nico:
											</label>
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>														
										<fieldset class="fsInterno">
											<label style="width:35%">
												Direcci&oacute;n de Correo electr&oacute;nico alterno:
											</label>
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
										</fieldset>																												
								</fieldset>					
							</td>
						</tr>
						</table>							
						</form:form>
						</div>
					</div>	
				</div>
			</div>
		</div>
	</div>
	</td>	
</tr>	
</table>	

</div>


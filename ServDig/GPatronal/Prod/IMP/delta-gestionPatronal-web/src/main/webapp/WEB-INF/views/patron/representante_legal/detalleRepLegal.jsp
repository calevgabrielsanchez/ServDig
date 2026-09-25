<div id="homecontenido" class="contenedor">
	<div class="row" style="height: 500px;">
		<div class="cell" style="padding: 20px;">
			<div class="row" style="height: 500px;">
				<div id="representanteLegal" style="height: 100%">
					<!-- JSP principal del modulo de Representante Legal -->
					<c:set var="cveIdPatronSujetoObligado"
 						value="${sujetoObligado.cveIdSujetoObligado}"></c:set> 
 					<c:set var="tipoPersonaFiscal"
 						value="${sujetoObligado.tipoPersonaFiscal}"></c:set>
 						<!--<c:out value="${cveIdPatronSujetoObligado}"></c:out>--> 
 						<!--<c:out value="${tipoPersonaFiscal}"></c:out>--> 
					<input type="hidden" id="tipoPersonaFiscalHidden" value="${tipoPersonaFiscal}">
					<div style="display: none;">
						<form:form modelAttribute="representanteLegal"
							id="representanteLegalFormPaginar">
							<form:hidden path="cveIdPatronSujetoObligado"
								id="cveIdPatronSujetoObligado" />
						</form:form>
					</div>
					<input type="hidden" id="idSolicitudRL" name="idSolicitudRL" value="${idSolicituRL}"/>
					<h2>Actual</h2>
							<div id="lista" style="display: table-row !important;">									
									<table id="tbRepresentanteLegal" style="width: 100%; vertical-align: top;">											
											<thead>
											</thead>
											<tbody style="width: 100%;">
											</tbody>
											<tfoot>
										</tfoot>
									</table>
								</div>				
								<br><br>						
								<center><h2>Tramite</legend></h2>							
					
					<table style="width: 100%;" border=2>
						<tr>
							<td>
								<div id="listaForSession" style="width: 100%;">
									<div id="tablaForSession">
										<table id="tbRepresentanteLegalForSession" border=2
											style="width: 100%; vertical-align: top;" cellpadding="0"
											cellspacing="0">
											<thead>
											</thead>
											<tbody style="width: 100%;">
											</tbody>
											
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
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
																</form>
															</div>
															<form>
																<div class="opcion">
																	<input type="button"
																		onclick="fnOpenDialogNuevoRepresentanteLegal();"
																		class="mboton" value="Agregar"
																		style="font-size: .8em !important;">
																</div>
																<div class="opcion">
																	<input type="button"
																		onclick="fnOpenDialogEliminarRepresentanteLegal();"
																		class="mboton" value="Eliminar"
																		style="font-size: .8em !important;">
																</div>
																<div class="opcion">
																	<input type="button"
																		onclick="fnOpenDialogModificarRepresentanteLegal();"
																		class="mboton" value="Modificar"
																		style="font-size: .8em !important;">
																</div>
															</form>
														</div>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<form>
									<div class="opcion"><input type="button"
										onclick="actualizarDatosRepLegal('finalizar');" class="mboton"
										name="aDatosRepLegal" value="<spring:message code="label.finalizar"/>" />
									</div>
								</form>
							</td>
						</tr>
					</table>
				</div>
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
						<form:form modelAttribute="sujetoObligado" action="${contextpath}/fb/consultarRepLegal" id="registroForm">
						<table boder=2>						
						<tr>
							<td width=400>					
								<fieldset>								
										<fieldset class="fsInterno">
											<label style="width:35%">
												Poder para:
											</label>
											Actos de Administraci&oacute;n
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
											<form:input readonly="true" path="fisica.primerApellido" maxlength="14" cssStyle="width:40%"/>
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


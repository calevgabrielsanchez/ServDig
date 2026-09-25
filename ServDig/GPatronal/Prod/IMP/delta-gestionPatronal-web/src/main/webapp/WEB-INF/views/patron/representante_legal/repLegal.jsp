<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/repLegal.js" htmlEscape="true" />"></script>

<div id="homecontenido" class="contenedor">
	<div class="row" style="height: 500px;">
		<div class="cell" style="padding: 20px;">
			<div class="row">
				<div id="representanteLegal" style="height: 100%">
					<!-- JSP principal del modulo de Representante Legal -->
					<c:set var="cveIdPatronSujetoObligado"
 						value="${sujetoObligado.cveIdSujetoObligado}"></c:set> 
 					<c:set var="tipoPersonaFiscal"
 						value="${sujetoObligado.tipoPersonaFiscal}"></c:set>
<%-- 						<c:out value="${cveIdPatronSujetoObligado}"></c:out> --%>
<%-- 						<c:out value="${tipoPersonaFiscal}"></c:out> --%>
					<input type="hidden" id="tipoPersonaFiscalHidden" value="${tipoPersonaFiscal}">
					<div style="display: none;">
						<form:form modelAttribute="representanteLegal"
							id="representanteLegalFormPaginar">
							<form:hidden path="cveIdPatronSujetoObligado"
								id="cveIdPatronSujetoObligado" />
						</form:form>
					</div>
					<input type="hidden" id="idSolicitudRL" name="idSolicitudRL" value="${idSolicituRL}"/>
					<table style="width: 100%;">
						<tr>
							<td>
								<div id="lista" style="width: 100%;">
									<div id="tabla">
										<table id="tbRepresentanteLegal"
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
					
					<table style="width: 100%;">
						<tr>
							<td>
								<div id="listaForSession" style="width: 100%;">
									<div id="tablaForSession">
										<table id="tbRepresentanteLegalForSession"
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
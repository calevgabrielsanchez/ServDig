<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/socios/socios.js" htmlEscape="true" />"></script>

<div id="homecontenido" class="contenedor">
	<div class="row" style="height: 500px;">
		<div class="cell" style="padding: 20px;">
			<div class="row">
				<div id="socios" style="height: 100%">
					<!-- JSP principal de la seccion de socios -->
					<c:set var="cveIdPatronSujetoObligado"
						value="${socio.cveIdPatronSujetoObligado}"></c:set>
					<div style="display: none;">
						<form:form modelAttribute="socio" id="socioFormPaginar">
							<form:hidden path="cveIdPatronSujetoObligado"
								id="cveIdPatronSujetoObligado" />
						</form:form>
					</div>
					<input type="hidden" id="idSolicitud" name="idSolicitud" value="${idSolicitud}"/>
					<table style="width: 100%;">
						<tr>
							<td>
								<div id="lista" style="width: 100%;">
									<div id="tabla">
										<table id="tbSocios" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
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
										<table id="tbSociosForSession" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
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
							
													<td align="right" colspan="6">
														<form>
															<div class="izquierda">
																<input type="button" class="mboton" name="regresar"
																onclick="history.back();"
																value="<spring:message code="label.regresar" />"></input>
															</div>
														</form>
												
														<div class="opciones"><form>
															<div class="opcion">
																<input type="button"
																	onclick="agregarSocio();" class="mboton"
																	value="Agregar" style="font-size: .8em !important;">
															</div>
															<div class="opcion">
																<input type="button"
																onclick="fnOpenDialogEliminarSocio();" class="mboton"																		value="Eliminar" style="font-size: .8em !important;">
															</div>			
															<div class="opcion">
																<input type="button"
																	onclick="modificarSocio();" class="mboton"
																	value="Modificar" style="font-size: .8em !important;">
															</div>
														</form></div>
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
										onclick="actualizarDatosSocio('finalizar');" class="mboton"
										name="aDatosSocio" value="<spring:message code="label.finalizar"/>" />
									</div>
								</form>
							</td>
						</tr>
					</table>
				</div>

				<div id="dgNuevoSocio" style="background-color: white !important;">
<!--					<iframe id="componentePersona" name="componentePersona" width="100%" height="100%"> </iframe>-->
						<jsp:include page="nuevoSocio.jsp"></jsp:include>
				</div>

				<div id="dgEliminarSocio" title="¿Eliminar elemento?">
					<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
						El registro seleccionado será eliminado, ¿Esta Ud. seguro?
					</p>
					</br> <span id="errorNegocioLabel" class=" hiddenElement error"></span>
				</div>

				<div id="dgErrorSinSeleccionSocio"
					title="Debe seleccionar un elemento">
					<p>
						<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
						No se ha seleccionado ningún registro para la acción.
					</p>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="dgActualizaDatosSocio" title="<spring:message code="label.exito"/>">
	<p><span class="ui-icon ui-icon-alert"
		style="float: left; margin: 0 7px 20px 0;"> </span>
		<spring:message code="label.datos.socio.actualizados" />
	</p>
</div>
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/administrar/datosComplementarios.js" htmlEscape="true" />"></script>

<div class="page_holder">
	<div class="contenedor">
		<div>
			<div style="display: inline; width: 100%">
				<div class="row" style="width: 100%">

					<div id="titulo" class="row" style="width: 100%">
						<h2>Detalle Domicilio Geogr&aacute;fico</h2>
					</div>

					<div id="asentamientoMapa" class="row" style="width: 100%">
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
						<div id="datosComplementarios" class="cell"
							style="float: left; width: 450px !important;">

							<form:form action="" modelAttribute="domicilio"
								id="formComplemento">
								<fieldset style="margin: 20px !important;">
									<legend>
										<strong>Componentes principales del domicilio</strong>
									</legend>

									<div>
										<div>

											<!-- Tipo de Vialidad -->
											<fieldset class="fsInterno fielsetgris disabled ">
												<div>
													<div>
														<form:label path="vialidadPrimaria.tipoVialidad.clave">
															<spring:message code="label.vialidad.tipo" />
														</form:label>
													</div>
													<div id="vialidadPrimaria">
														<combo:creaCombo
															idHtml="vialidadPrimaria.tipoVialidad.clave"
															idHtmlContenedor="vialidadPrimaria"
															entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
															mostrarSoloActivos="false"/>

														<form:hidden path="vialidadPrimaria.tipoVialidad.clave"
															id="vialidadPrimaria.tipoVialidad.clave.hidden" />
													</div>
												</div>
											</fieldset>

											<!-- Vialidad Primaria -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<div>
														<form:label path="vialidadPrimaria.nombre">
															<spring:message code="label.vialidadPrimaria" />
														</form:label>
													</div>
													<div>
														<select id="vialidadPrimaria.clave"
															name="vialidadPrimaria.clave"
															style="float: left; width: 300px !important;">
															<option value="-1">--Por favor seleccione--</option>
														</select>
														<form:hidden path="vialidadPrimaria.clave"
															id="vialidadPrimaria.clave.hidden" />
														<form:hidden path="vialidadPrimaria.nombre"
															id="vialidadPrimaria.nombre.hidden" />
													</div>
												</div>
											</fieldset>

											<!-- Numero y Letra exterior principal -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<div>
														<form:label path="numExterior1">
															<spring:message code="label.numeroLetraExterior" />
														</form:label>
													</div>
													<div>
														<span style="font-size: 0.7em;"> <strong>Ej.
																999 / Mz. 9 Lt. 8</strong>
														</span>
													</div>
													<div>
														<form:input type="text" path="numExterior1" maxlength="5"
															size="3" cssClass="numerico" />
														<span> <strong>/</strong>
														</span>
														<form:input type="text" path="numExteriorAlf"
															maxlength="35" cssClass="alfanumerico" />
													</div>
												</div>
											</fieldset>

											<!-- Numero y Letra  interior-->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<div>
														<form:label path="numInterior">
															<spring:message code="label.numeroLetraInterior" />
														</form:label>
													</div>
													<div>
														<span style="font-size: 0.7em;"> <strong>Ej.
																54 / Depto. 45 Int.</strong>
														</span>
													</div>
													<div>
														<form:input type="text" path="numInterior" maxlength="5"
															size="3" cssClass="numerico" />
														<span> <strong>/</strong>
														</span>
														<form:input type="text" path="numInteriorAlf"
															maxlength="35" cssClass="alfanumerico" />
													</div>
												</div>
											</fieldset>

											<!-- Numero exterior secundario  -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<div>
														<form:label path="numExterior2">
															<spring:message code="label.numExterior2" />
														</form:label>
													</div>
													<form:input type="text" path="numExterior2" maxlength="5"
														cssClass="numerico" />
												</div>
											</fieldset>

											<!-- Nombre del asentamiento -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<div>
														<form:label path="asentamiento.nombre">
															<spring:message code="label.asentamiento" />
														</form:label>
													</div>
													<form:input path="asentamiento.nombre" readonly="true"
														cssClass="disabled" size="30" />
													<form:hidden path="asentamiento.nombre" />
													<form:hidden path="asentamiento.clave" />
													<form:hidden
														path="asentamiento.tipoAsentamiento.descripcion" />
												</div>
											</fieldset>

											<!-- Campo del codigo postal -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<form:label path="codigoPostal.codigoPostal">
														<spring:message code="label.codigoPostal" />
													</form:label>
													<form:input path="asentamiento.codigoPostal.codigoPostal"
														maxlength="8" readonly="true" cssClass="disabled" />
													<form:hidden path="asentamiento.codigoPostal.codigoPostal" />

												</div>
											</fieldset>

											<!-- Localidad -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<form:label path="asentamiento.localidad.nombre">
														<spring:message code="label.localidad" />
													</form:label>
													<form:input path="asentamiento.localidad.nombre"
														readonly="true" cssClass="disabled" size="30" />
													<form:hidden path="asentamiento.localidad.clave" />
													<form:hidden path="asentamiento.localidad.nombre" />
												</div>
											</fieldset>

											<!-- Municipio -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<form:label path="asentamiento.localidad.municipio.nombre">
														<spring:message code="label.municipio" />
													</form:label>
													<form:input path="asentamiento.localidad.municipio.nombre"
														readonly="true" cssClass="disabled" />
													<form:hidden path="asentamiento.localidad.municipio.nombre" />
													<form:hidden path="asentamiento.localidad.municipio.clave" />
													<input type="text" id="municipio.nombre"
														name="municipio.nombre"
														value="${domicilio.asentamiento.localidad.municipio.nombre}"
														style="display: none;" />
												</div>
											</fieldset>

											<!-- Entidad Federativa -->
											<fieldset class="fsInterno fielsetgris disabled">
												<div>
													<form:label
														path="asentamiento.localidad.municipio.entidadFederativa.nombre">
														<spring:message code="label.entidadFederativa" />
													</form:label>
													<form:input
														path="asentamiento.localidad.municipio.entidadFederativa.nombre"
														readonly="true" cssClass="disabled" size="30" />
													<form:hidden
														path="asentamiento.localidad.municipio.entidadFederativa.nombre" />
													<form:hidden
														path="asentamiento.localidad.municipio.entidadFederativa.clave" />
													<input type="text" id="entidadFederativa.nombre"
														name="entidadFederativa.nombre"
														value="${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}"
														style="display: none;" />
												</div>
											</fieldset>
										</div>
									</div>
								</fieldset>


								<!-- Datos complementarios del domicilio -->
								<fieldset style="margin: 20px !important;">
									<legend>
										<strong> Componentes secundarios del domicilio</strong>
									</legend>

									<div>
										<!-- Tipo de Vialidad -->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<div>
													<form:label
														path="vialidadReferenciaPrimaria.tipoVialidad.clave">
														<spring:message code="label.vialidad.tipo" />
													</form:label>
												</div>
												<div id="vialidadReferenciaPrimaria">
													<combo:creaCombo
														idHtml="vialidadReferenciaPrimaria.tipoVialidad.clave"
														idHtmlContenedor="vialidadReferenciaPrimaria"
														entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
														mostrarSoloActivos="false"/>

													<form:hidden
														path="vialidadReferenciaPrimaria.tipoVialidad.clave"
														id="vialidadReferenciaPrimaria.tipoVialidad.clave.hidden" />
												</div>
											</div>
										</fieldset>

										<!-- Vialidad referencia primaria-->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<div>
													<form:label path="vialidadReferenciaPrimaria.clave">
														<spring:message code="label.vialidadReferenciaPrimaria" />
													</form:label>
												</div>
												<div>
													<select id="vialidadReferenciaPrimaria.clave"
														name="vialidadReferenciaPrimaria.clave"
														style="float: left; width: 300px !important;">
														<option value="-1">--Por favor seleccione--</option>
													</select>
													<form:hidden path="vialidadReferenciaPrimaria.clave"
														id="vialidadReferenciaPrimaria.clave.hidden" />
												</div>
											</div>
										</fieldset>

										<!-- Tipo de Vialidad -->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<div>
													<form:label
														path="vialidadReferenciaSecundaria.tipoVialidad.clave">
														<spring:message code="label.vialidad.tipo" />
													</form:label>
												</div>
												<div id="vialidadReferenciaSecundaria">
													<combo:creaCombo
														idHtml="vialidadReferenciaSecundaria.tipoVialidad.clave"
														idHtmlContenedor="vialidadReferenciaSecundaria"
														entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
														mostrarSoloActivos="false"/>
													<form:hidden
														path="vialidadReferenciaSecundaria.tipoVialidad.clave"
														id="vialidadReferenciaSecundaria.tipoVialidad.clave.hidden" />
												</div>
											</div>
										</fieldset>

										<!-- Vialidad referencia secundaria-->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<div>
													<form:label path="vialidadReferenciaSecundaria.clave">
														<spring:message code="label.vialidadReferenciaSecundaria" />
													</form:label>
												</div>
												<div>
													<select id="vialidadReferenciaSecundaria.clave"
														name="vialidadReferenciaSecundaria.clave"
														style="float: left; width: 300px !important;">
														<option value="-1">--Por favor seleccione--</option>
													</select>
													<form:hidden path="vialidadReferenciaSecundaria.clave"
														id="vialidadReferenciaSecundaria.clave.hidden" />
												</div>
											</div>
										</fieldset>

										<!-- Tipo de Vialidad -->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<div>
													<form:label
														path="vialidadReferenciaPosterior.tipoVialidad.clave">
														<spring:message code="label.vialidad.tipo" />
													</form:label>
												</div>
												<div id="vialidadReferenciaPosterior">
													<combo:creaCombo
														idHtml="vialidadReferenciaPosterior.tipoVialidad.clave"
														idHtmlContenedor="vialidadReferenciaPosterior"
														entidad="mx.gob.imss.ctirss.delta.persistence.DgCatVialidad" 
														mostrarSoloActivos="false"/>
													<form:hidden
														path="vialidadReferenciaPosterior.tipoVialidad.clave"
														id="vialidadReferenciaPosterior.tipoVialidad.clave.hidden" />
												</div>
											</div>
										</fieldset>

										<!-- Vialidad referencia posterior-->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<div>
													<form:label path="vialidadReferenciaPosterior.clave">
														<spring:message code="label.vialidadReferenciaPosterior" />
													</form:label>
												</div>
												<div>
													<select id="vialidadReferenciaPosterior.clave"
														name="vialidadReferenciaPosterior.clave"
														style="float: left; width: 300px !important;">
														<option value="-1">--Por favor seleccione--</option>
													</select>
													<form:hidden path="vialidadReferenciaPosterior.clave"
														id="vialidadReferenciaPosterior.clave.hidden" />
												</div>
											</div>
										</fieldset>

										<!-- Descripcion del domicilio -->
										<fieldset class="fsInterno fielsetgris disabled">
											<div>
												<form:label path="descripcion">
													<spring:message code="label.descripcion" />
												</form:label>
											</div>
											<div>
												<form:textarea path="descripcion" maxlength="255"
													cssStyle="width:360px !important; height: 120px !important;"
													cols="10" cssClass="alfanumerico" />
											</div>
										</fieldset>
									</div>
								</fieldset>

								<!-- datos de la longitud y latitud -->
								<form:hidden path="latitud" />
								<form:hidden path="longitud" />
							</form:form>
						</div>

						<div class="cell fielsetgris"
							style="float: right; width: 450px !important; height: 100% !important;">
							<div style="width: 100%;" align="center">
								<div class="ui-widget">
									<div style="margin-top: 20px; padding: 0 .7em;"
										class="ui-state-highlight ui-corner-all">
										<p>
											<span
												style="font-size: 0.7em !important; float: left; margin-right: .3em;"
												class="ui-icon ui-icon-info"></span> <strong> Nota:</strong>
											El mapa muestra la ubicaci&oacute;n aproximada del domicilio
											localizado, este mapa es solo para fines y usos internos del
											Instituto Mexicano del Seguro Social
										</p>
									</div>
								</div>
							</div>
							</br>
							<div id="map_canvas" style="width: 450px; height: 900px"></div>
						</div>

					</div>

					<!-- Fin del div del asentamiento y mapa -->

				</div>
				<!-- Fin del div contenedor -->

			</div>
		</div>
	</div>
</div>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios.js" htmlEscape="true" />"></script>

<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=true"></script>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/domicilios-map.js" htmlEscape="true" />"></script>




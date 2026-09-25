<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>
<jsp:include page="../../common/llenaSexo.jsp"></jsp:include>
<jsp:include page="../../common/llenaParentescos.jsp"></jsp:include>
<jsp:include page="../../common/llenarRazonRegistro.jsp"></jsp:include>

<style>
#tablaDatosPersonales input,textarea,.uneditable-input {
	text-transform: uppercase;
}
</style>
<input type="hidden" value="${isPatronImss ? 1 : 0}" id="isPatronImss"/>
<input type="hidden" value = "${registro.parentesco.idParentesco}" id="parentescoDelRegistro"/>
<input type="hidden" value = "${registro.razonRegistro.idRazonRegistro}" id="razonDelRegistro"/>

<c:set var="parentescoHijo" value="<%=ParentescoEnum.HIJOS.getId()%>"></c:set>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/datosPersonales.js" htmlEscape="true" />"></script>

<div class="contenedor col-sm-12">
	<input type="hidden" value="${solicitudRegistro.solicitudId}" id="idSolicitud"/>
	<div class="contenido row" > 
	<div class="col-sm-12">
	
		<jsp:include page="encabezadoRegistro.jsp">
			<jsp:param name="paso" value="1" />
		</jsp:include>
		
		<c:if test="${empty error}">
		<form:form id="formRegistro" method="post" modelAttribute="registro">
			<div id="seccionPersonaRegistro" class="table_form">
				
				<div class="separadorseccion">
					<span>
						<spring:message code="label.datosPersoales"/>
					</span>
				</div>

				
				
				
				<form:hidden path="fisica.idPersona"/>
				<form:hidden path="razonRegistro.idRazonRegistro"/>
				<form:hidden path="razonRegistro.descripcion"/>
				<form:hidden path="paso"/>
				<form:hidden path="datosAsegurado.sexo.idSexo"/>
				<form:hidden path="datosAsegurado.primerApellido"/>
				<form:hidden path="datosAsegurado.idAsignacionNSS"/>
				<form:hidden path="indHijosProcreados"/>
				
				<table id="tablaDatosPersonales" style="width:100%" class="table table-striped table-bordered" >
					<c:if test="${registro.parentesco.idParentesco eq parentescoHijo}">
						<tr>
							<td>
								<label class="control-label" for="rnSi">
									<spring:message code="label.rn"/><span class="required">*</span>:
								</label>
							</td>
							<td colspan="3">
								<div class="btn-group" data-toggle="buttons-radio">
									 <button type="button" class="btn btn-primary" id="rnSi" onclick="habilitarCamposRecienNacido(true,true,true)">Si</button>
									 <button type="button" class="btn btn-primary" id="rnNo" onclick="habilitarCamposRecienNacido(false,true,true)">No</button>
								</div>
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td>
							<label class="control-label" for="fisica.curp">
								<spring:message code="label.curp"/><span class="required" id="indCurpObligatoria">*</span>:
							</label>
						</td>
						<td>
							<form:input path="fisica.curp" cssClass="form-control ns_" maxlength="18"/>
							<span id="fisica.curpError" class="error hiddenElement"></span>
						</td>
						<td colspan="2">
							<div id="wrapperCurp">
								<button class="btn btn-primary" type="button" id = "buscarXCurp" onclick="uid_call('imss.derechohabientes.registro.datosPersonales.btn_buscarCurp','clickin')">
									<spring:message code="label.buscarPersona"/>
								</button>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="fisica.nombre">
								<spring:message code="label.nombre" /><span class="required">*</span>:
							</label>
						</td>
						<td>
							<form:input path="fisica.nombre" cssClass="form-control ns_"/>
							<span id="fisica.nombreError" class="error hiddenElement"></span>
						</td>
						
						<td>
							<label class="control-label" for="fisica.lugarNacimiento.clave">
								<spring:message code="label.lugarNac" /><span class="required">*</span>:
							</label>
						</td>
						<td>
							<combo:creaCombo
								idHtml="fisica.lugarNacimiento.clave"
								idHtmlContenedor="formRegistro"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
								idHtmlValor="${registro.fisica.lugarNacimiento.clave}"
								mostrarSoloActivos="true" cssClassname="form-control ns_"
							/>
							<span id="fisica.lugarNacimiento.claveError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="fisica.primerApellido">
							<spring:message code="label.primerApe" />:
							</label>
						</td>
						<td>
							<form:input path="fisica.primerApellido" cssClass="form-control ns_"/>
							<span id="fisica.primerApellidoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="fisica.sexo.idSexo">
							<spring:message code="label.sexo" /><span class="required">*</span>:
							</label>
						</td>
						<td>
							<combo:creaCombo
								idHtmlValor="${registro.fisica.sexo.idSexo}"
								idHtml="fisica.sexo.idSexo" idHtmlContenedor="formRegistro"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
								mostrarSoloActivos="true" 
								cssClassname="form-control ns_"
							/>
							<span id="fisica.sexo.idSexoError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="fisica.segundoApellido">
								<spring:message code="label.segundoApe" />:
							</label>
						</td>
						<td>
							<form:input path="fisica.segundoApellido" cssClass="form-control ns_"/>
							<span id="fisica.segundoApellidoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="fisica.fechaNacimiento">
								<spring:message code="label.fechaNac" /><span class="required">*</span>: 
							</label>
						</td>
						<td>
							<form:input path="fisica.fechaNacimiento" cssClass="form-control ns_"/>
							<span id="fisica.fechaNacimientoError" class="error hiddenElement"></span>
						</td>
					</tr>
					
				</table>
				<br>
				<div class="separadorseccion">
					<span>
						<spring:message code="label.titulo.datosEnGrupo"/>
					</span>
				</div>
				<table id="tablaDatosGrupo" style="width: 100%" class="table table-striped table-bordered" >
					<tr>
						<td>
							<label class="control-label" for="parentesco.idParentesco">
								<spring:message code="label.parentesco" /><span class="required">*</span>:
							</label>
						</td>
						<td>
							<combo:creaCombo
								idHtmlValor="${registro.parentesco.idParentesco}"
								idHtml="parentesco.idParentesco" idHtmlContenedor="formRegistro"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco" 
								mostrarSoloActivos="true" 
								cssClassname="form-control ns_"
							/>
							<span id="parentesco.idParentescoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="fisica.estadoCivil.idEstadoCivil">
								<spring:message code="label.edoCivil"/><span class="required">*</span>:
							</label>
						</td>
						<td>
							<combo:creaCombo
								idHtmlValor="${registro.fisica.estadoCivil.idEstadoCivil}"
								idHtml="fisica.estadoCivil.idEstadoCivil" idHtmlContenedor="formRegistro"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil" 
								mostrarSoloActivos="true" 
								cssClassname="form-control ns_"
							/> 
							<span id="fisica.estadoCivil.idEstadoCivilError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="razonRegistro.idRazonRegistro">
								<spring:message code="label.razonRegistro"/>:
							</label>
						</td>
						<td colspan="3">
							<combo:creaCombo
								idHtml="razonRegistro.idRazonRegistro"
								idHtmlValor="${registro.razonRegistro.idRazonRegistro}"
								idHtmlContenedor="formRegistro"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro" 
								mostrarSoloActivos="true" 
								cssClassname="form-control ns_"
							/>
						</td>
					</tr>
					
					<c:if test="${registro.parentesco.idParentesco == 4 }">
					
					<tr>
						<td>
							<label class="control-label" for="indHijosProcreados">
								<spring:message code="label.hijosProcreados"/>:
							</label>
						</td>
						<td colspan="3">
							<c:if test="${registro.indHijosProcreados == 1 }">
								<input type="checkbox" id="hijosProcreadosCheck" checked="checked">
							</c:if>
							<c:if test="${registro.indHijosProcreados == 0 }">
								<input type="checkbox" id="hijosProcreadosCheck">
							</c:if>
						</td>
						</tr>
					
					</c:if>
				</table>
				<br>
				<div class="separadorseccion">
					<span>
						<spring:message code="label.titulo.mediosContacto"/>
					</span>
				</div>
				<table id="datosContactoRegistro" style="width: 100%" class="table table-striped table-bordered" >
					<tr>
						<td>
							<label class="control-label" for="fisica.correoElectronico.correo">
								<spring:message code="label.correo" /><span class="required">*</span>:
							</label>
						</td>
						<td>
							<form:hidden path="fisica.correoElectronico.clave"/>
							<form:input path="fisica.correoElectronico.correo" cssClass="form-control ns_" maxlength="100"/>
							<span id="fisica.correoElectronico.correoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="fisica.telefonoFijo.claveLada">
								<spring:message code="label.telefono" />:
							</label>
						</td>
						<td>
							<form:hidden path="fisica.telefonoFijo.clave"/>
							<form:input path="fisica.telefonoFijo.claveLada" cssClass="form-control ns_ numericoSinPunto" maxlength="15"/>
						</td>
					</tr>
				</table>
			</div>
		</form:form>
		</c:if>
	</div>
	</div>
	<br>
	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos" /></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<c:if test="${empty error}">
				<div class="pull-right">
					<button id="continuarADomicilio" class="btn btn-default" type="button" onclick="uid_call('imss.derechohabientes.registro.datosPersonales.btn_siguiente','clickin')"><spring:message code="wizard.button.siguiente" /><span class="glyphicon glyphicon-step-forward"></span></button>
					<div class="btn-group dropup">
						<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> <a href="#"
							data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
							class="caret"></span></a>
						<ul class="dropdown-menu pull-right">
							<li><a id="guardarTramite" onclick="uid_call('imss.derechohabientes.registro.datosPersonales.link_guardar','clickin')"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite" /></a></li>
							<li><a id="guardarCerrarTramite" onclick="uid_call('imss.derechohabientes.registro.datosPersonales.link_guardarCerrar','clickin')"><i class="glyphicon glyphicon-remove"></i><spring:message code="wizard.button.guardarCerrar" /></a></li>
							<li><a id="cancelarTramite" onclick="uid_call('imss.derechohabientes.registro.datosPersonales.link_cancelar','clickin')"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite" /></a></li>
						</ul>
					</div>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>

<jsp:include page="pieRegistro.jsp"/>

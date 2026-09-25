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
<input type="hidden" value="${isPatronImss?1:0}" id="isPatronImss"/>

<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/registroDerechohabiente/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/registroDerechohabiente/datosPersonales.js" htmlEscape="true" />"></script>

<input type="hidden" value="${registro.parentesco.idParentesco}" id="parentescoDelRegistro"/>
<input type="hidden" value="${registro.razonRegistro.idRazonRegistro}" id="razonDelRegistro"/>

<div class="container-fluid"> 
	<div class="wizard row">
		<div class="contenedor col-sm-12">
			<div class="contenido row""> 
				<div class="col-sm-12">
				<c:if test="${not empty error}">
					<div class="alert alert-info">
						<button type="button" class="close" data-dismiss="alert">�</button>
						<strong>Importante: </strong>${error}
					</div>
				</c:if>
				
				<c:if test="${empty error}">
				<div class="titulo" align="center">
						<span> PASO 1/2 - Captura de datos personales</span>
				</div>
					
				<form:form id="formRegistro" method="post" modelAttribute="registro">
					<div id="seccionPersonaRegistro" class="table_form">
						
						<div class="separadorseccion">
							<span>
								Datos personales del derechohabiente
							</span>
						</div>
						
						
						<form:hidden path="fisica.idPersona"/>
						<form:hidden path="razonRegistro.descripcion"/>
						<form:hidden path="paso"/>
						<form:hidden path="datosAsegurado.sexo.idSexo"/>
						<form:hidden path="datosAsegurado.primerApellido"/>
						<form:hidden path="datosAsegurado.idAsignacionNSS"/>
						
						<table id="tablaDatosPersonales" width="100%" class="table table-striped table-bordered" >
							
							
							<tr>
								<td>
									<label class="control-label" for="fisica.nombre">
										<span class="required">*</span>&nbsp;<spring:message code="label.nombre" /> :
									</label>
								</td>
								<td>
									<form:hidden path="fisica.curp"/>
									<form:input path="fisica.nombre" cssClass="form-control"/>
									<span id="fisica.nombreError" class="error hiddenElement"></span>
								</td>
								
								<td>
									<label class="control-label" for="fisica.lugarNacimiento.clave">
										<span class="required">*</span>&nbsp;<spring:message code="label.lugarNac" /> :
									</label>
								</td>
								<td>
									<combo:creaCombo
										idHtml="fisica.lugarNacimiento.clave"
										idHtmlContenedor="formRegistro"
										entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
										idHtmlValor="${registro.fisica.lugarNacimiento.clave}"
										mostrarSoloActivos="true" cssClassname="form-control"
									/>
									<form:hidden path="fisica.lugarNacimiento.nombre"/>
									<span id="fisica.lugarNacimiento.claveError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="fisica.primerApellido">
									<span class="required">*</span>&nbsp;<spring:message code="label.primerApe" /> :
									</label>
								</td>
								<td>
									<form:input path="fisica.primerApellido" cssClass="form-control"/>
									<span id="fisica.primerApellidoError" class="error hiddenElement"></span>
								</td>
								<td>
									<label class="control-label" for="fisica.sexo.idSexo">
									<span class="required">*</span>&nbsp;<spring:message code="label.sexo" /> :
									</label>
								</td>
								<td>
									<combo:creaCombo
										idHtmlValor="${registro.fisica.sexo.idSexo}"
										idHtml="fisica.sexo.idSexo" idHtmlContenedor="formRegistro"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" 
										mostrarSoloActivos="true" 
										cssClassname="form-control"
									/>
									<form:hidden path="fisica.sexo.descripcion"/>
									<span id="fisica.sexo.idSexoError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="fisica.segundoApellido">
										<spring:message code="label.segundoApe" /> :
									</label>
								</td>
								<td>
									<form:input path="fisica.segundoApellido" cssClass="form-control"/>
									<span id="fisica.segundoApellidoError" class="error hiddenElement"></span>
								</td>
								<td>
									<label class="control-label" for="fisica.fechaNacimiento">
										<span class="required">*</span>&nbsp;<spring:message code="label.fechaNac" /> : 
									</label>
								</td>
								<td>
									<form:input path="fisica.fechaNacimiento" cssClass="form-control"/>
									<span id="fisica.fechaNacimientoError" class="error hiddenElement"></span>
								</td>
							</tr>
							
						</table>
						<br>
						<div class="separadorseccion">
							<span>
								Datos del integrante dentro del grupo familiar
							</span>
						</div>
						<table id="tablaDatosGrupo" width="100%" class="table table-striped table-bordered" >
							<tr>
								<td>
									<label class="control-label" for="parentesco.idParentesco">
										<span class="required">*</span>&nbsp;Parentesco :
									</label>
								</td>
								<td>
									<combo:creaCombo
										idHtmlValor="${registro.parentesco.idParentesco}"
										idHtml="parentesco.idParentesco" idHtmlContenedor="formRegistro"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco" 
										mostrarSoloActivos="true" 
										cssClassname="form-control"
									/>
									<form:hidden path="parentesco.descripcion"/>
									<span id="parentesco.idParentescoError" class="error hiddenElement"></span>
								</td>
								<td>
									<label class="control-label" for="fisica.estadoCivil.idEstadoCivil">
										<span class="required">*</span>&nbsp;Estado Civil :
									</label>
								</td>
								<td>
									<combo:creaCombo
										idHtmlValor="${registro.fisica.estadoCivil.idEstadoCivil}"
										idHtml="fisica.estadoCivil.idEstadoCivil" idHtmlContenedor="formRegistro"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil" 
										mostrarSoloActivos="true" 
										cssClassname="form-control"
									/> 
									<form:hidden path="fisica.estadoCivil.descripcion"/>
									<span id="fisica.estadoCivil.idEstadoCivilError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="razonRegistro.idRazonRegistro">
									 Razon registro :
									</label>
								</td>
								<td colspan="3">
									<combo:creaCombo
										idHtml="razonRegistro.idRazonRegistro"
										idHtmlValor="${registro.razonRegistro.idRazonRegistro}"
										idHtmlContenedor="formRegistro"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro" 
										mostrarSoloActivos="true" 
										cssClassname="form-control"
									/>
									<form:hidden path="razonRegistro.descripcion"/>
								</td>
							</tr>
						</table>
						<br>
						<div class="separadorseccion">
							<span>
								Medios de contacto de la persona
							</span>
						</div>
						
						<table width="100%" class="table table-striped table-bordered" >
							<tr>
								<td>
									<label class="control-label" for="fisica.correoElectronico.correo">
										<span class="required">*</span>&nbsp;<spring:message code="label.correo" /> :
									</label>
								</td>
								<td colspan="3">
									<form:hidden path="fisica.correoElectronico.clave"/>
									<form:input path="fisica.correoElectronico.correo" cssClass="form-control" maxlength="100"/>
									<span id="fisica.correoElectronico.correoError" class="error hiddenElement"></span>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="fisica.facebook.cuenta">
										<spring:message code="label.face" /> :
									</label>
								</td>
								<td>
									<form:hidden path="fisica.facebook.clave"/>
									<form:input path="fisica.facebook.cuenta" cssClass = "form-control alfanumerico"  maxlength="45"/>
								</td>
								<td>
									<label class="control-label" for="fisica.twitter.cuenta">
										<spring:message code="label.twitter" /> :
									</label>
								</td>
								<td>
									<form:hidden path="fisica.twitter.clave"/>
									<form:input path="fisica.twitter.cuenta" cssClass = "form-control alfanumerico"  maxlength="45"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="control-label" for="fisica.telefonoFijo.claveLada">
										<spring:message code="label.telefono" /> :
									</label>
								</td>
								<td>
									<form:hidden path="fisica.telefonoFijo.clave"/>
									<form:input path="fisica.telefonoFijo.claveLada" cssClass="form-control numericoSinPunto" maxlength="15"/>
								</td>
								<td>
									<label class="control-label" for="fisica.telefonoMovil.numero">
										<spring:message code="label.movil" /> :
									</label>
								</td>
								<td>
									<form:hidden path="fisica.telefonoMovil.clave"/>
									<form:input path="fisica.telefonoMovil.numero" cssClass="form-control numericoSinPunto" maxlength="15"/>
								</td>
							</tr>
						</table>
					</div>
				</form:form>
				</c:if>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6">
				</div>
				<div class="controles col-sm-6">
					<div class = "pull-right">
						 <button class="btn btn-default" id="cerrarWizard">CERRAR</button>
						 <c:if test="${empty error}">
							<button id="continuarADomicilio" class="btn btn-primary" type="button">
							  Siguiente <span class="glyphicon glyphicon-step-forward"></span>
							</button>
						</c:if> 
					</div>	
				</div>
			</div>
		</div>
	</div>
	<div id="pie" class="row"></div>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>

<div id="dialog-error" title="Error">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeError"></label>
	</p>
</div>

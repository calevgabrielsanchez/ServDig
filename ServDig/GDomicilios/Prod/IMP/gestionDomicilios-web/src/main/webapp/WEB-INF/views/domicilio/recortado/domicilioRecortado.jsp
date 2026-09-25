<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="bloquearCampos" value="${inicializadores.bloquearFormulario}"/>
<c:set var="bloqueoCampo" value="${inicializadores.bloquearFormulario?'disabled=\"disabled\"':''}" />
<c:set var="isDomicilioNull" value="${empty inicializadores.domicilio}" />
<c:set var="numExtAlf" value="${inicializadores.domicilio.numExterior1} ${inicializadores.domicilio.numExteriorAlf}" />
<%
String numExtAlfRec = pageContext.getAttribute("numExtAlf").toString().trim();
if( numExtAlfRec.length() > 35) {
	numExtAlfRec = numExtAlfRec.substring(0,35); 
}
%>

<style>
.ui-widget-overlay {
	position: fixed;
}

<c:if test="${!inicializadores.mostrarTitulosDialogs}">
.ui-dialog-titlebar {
    display:none;
}
</c:if>

span.error-custom {
	float: none !important;
	vertical-align: super;
}

.filtros-busqueda .row {
	margin-bottom: 12px;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

input[type="text"] {
	margin-bottom: 0px;
	text-transform: uppercase;
}

.alert-temp {
	background-color: #f8f8f8;
	border-color: #d9d9d9;
	color: #black;
}

.icono-help {
    color: black;
    font-family: FontAwesome;
    font-size: 20px;
    padding: 0 10px;
    text-decoration: none;
}

</style>

<input type="hidden" id="codigoPostalSeleccionado" value=""/>
<form id="datosDomicilioRecortadoForm" action="#" class="form-horizontal" method="post" role="form">

<c:choose>
<c:when test="${inicializadores.bootsTrapHabilitado}">
<div class="row">
	<div class="col-sm-3">
		<label class="control-label" for="domicilio.codigoPostal.codigoPostal">
			<spring:message code="label.codigoPostal"/>
			<span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			<a class="btn btn-xs icono-help" data-toggle="tooltip" data-placement="top" title="Introduce el C&oacute;digo Postal de tu domicilio a cinco posiciones"> </a>		
		</label>
		<input class="numerico form-control ns_ campoBloqueable" id="domicilio.codigoPostal.codigoPostal" name="domicilio.codigoPostal.codigoPostal" type="text"
						${bloqueoCampo} value="${inicializadores.domicilio.codigoPostal.codigoPostal}"
						maxlength = "5"/>
		<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement text-left"></span>
		
	</div>
	<div class="col-sm-9">
		<label class="control-label"></label>
		<div class="divOcultoRecortado" <c:if test="${bloquearCampos}">style="display:none"</c:if>>
			<button class="btn btn-default " type="button" id="limpiarForm">
				<span class="glyphicon glyphicon-refresh"></span> Limpiar
			</button>
			<button class="btn btn-primary " type="button" id="busquedaCp">
				<span class="glyphicon glyphicon-search"></span> Buscar
			</button>	
		</div>
	</div>
</div>
<div class="row" id="infoEstadoMun" style="display: none;">
	<div class="col-sm-6">
			<label class="control-label" for="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" style="text-align: left">
				<spring:message code="label.entidadFederativa"></spring:message>: 
			</label>
			
			<input id="domicilio.asentamiento.localidad.clave" name="domicilio.asentamiento.localidad.clave" type="hidden" 
							value="${inicializadores.domicilio.asentamiento.localidad.clave}"
						/>
						<input id="domicilio.asentamiento.localidad.nombre" name="domicilio.asentamiento.localidad.nombre" type="hidden" value="${inicializadores.domicilio.asentamiento.localidad.nombre}"/>
						
						<input id="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave"
						name="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" type="hidden" 
						value="${inicializadores.domicilio.asentamiento.localidad.municipio.entidadFederativa.clave}"
						/> 
						
						<input class="form-control ns_" id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" 
						name="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" type="text"
						readonly="readonly" value="${inicializadores.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}"/>
						
						<span id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombreError" class="error hiddenElement"></span>
			
	</div>
	<div class="col-sm-6">
			<label class="control-label" for="domicilio.asentamiento.localidad.municipio.nombre" style="text-align: left">
						<spring:message code="label.municipio"/>: 
					</label>
						<input id="domicilio.asentamiento.localidad.municipio.clave" name="domicilio.asentamiento.localidad.municipio.clave" type="hidden"
						value="${inicializadores.domicilio.asentamiento.localidad.municipio.clave}"/>
						
						<input class="form-control ns_" id="domicilio.asentamiento.localidad.municipio.nombre" 
							name="domicilio.asentamiento.localidad.municipio.nombre" type="text"
							readonly="readonly" value="${inicializadores.domicilio.asentamiento.localidad.municipio.nombre}"/>
								
						<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>

	</div>
</div>
<div class="row" id="infoDireccion" style="display: none;">
	<div class="col-sm-6">
			<label class="control-label" for="domicilio.asentamiento.clave" style="text-align: left">
						<spring:message code="label.selecciona.colonia"/>
						<span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
						<a class="btn btn-xs icono-help" data-toggle="tooltip" data-placement="top" title="Selecciona la colonia en donde vives"></a>
					</label>
			<select class="form-control ns_ campoBloqueable" ${bloqueoCampo} id="domicilio.asentamiento.clave" name="domicilio.asentamiento.clave">
							<c:choose>
								<c:when test="${isDomicilioNull}">
									<option value="-1"><spring:message code="label.dom.recortado.proporcionaCP"/></option>
								</c:when>
								<c:otherwise>
									<option value="${inicializadores.domicilio.asentamiento.clave}">${inicializadores.domicilio.asentamiento.nombre}</option>
								</c:otherwise>
							</c:choose>
							
						</select>		
						<span id="domicilio.asentamiento.claveError" class="error hiddenElement text-left"></span>
						<input id="domicilio.asentamiento.nombre" name="domicilio.asentamiento.nombre" type="hidden" value="${inicializadores.domicilio.asentamiento.nombre}"/>
	</div>
	<div class="col-sm-6"></div>
</div>
<div class="row" id="infoDireccion" style="display: none;">
	<div class="col-sm-6">
		<label class="control-label" for="domicilio.calle" style="text-align: left">
						<spring:message code="label.vialidadPrimaria"/><span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
						<a class="btn btn-xs icono-help" data-toggle="tooltip" data-placement="top" title="Introduce la calle en donde vives"></a>
					</label>
						<input class="form-control ns_ campoBloqueable" ${bloqueoCampo} id="domicilio.calle" name="domicilio.calle" maxlength="70" type="text" value="${inicializadores.domicilio.calle}"/>
						 
						<input class="form-control ns_" id="domicilio.vialidadPrimaria.nombre" name="domicilio.vialidadPrimaria.nombre" type="hidden" 
						value="${inicializadores.domicilio.vialidadPrimaria.nombre}"/>
						<input class="form-control ns_" id="domicilio.vialidadPrimaria.clave" name="domicilio.vialidadPrimaria.clave" type="hidden"
						value="${inicializadores.domicilio.vialidadPrimaria.clave}"/>
						<input class="form-control ns_" id="domicilio.vialidadPrimaria.tipoVialidad.descripcion" name="domicilio.vialidadPrimaria.tipoVialidad.descripcion" type="hidden"
						value="${inicializadores.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"/>
						<input class="form-control ns_" id="domicilio.vialidadPrimaria.tipoVialidad.clave" name="domicilio.vialidadPrimaria.tipoVialidad.clave" type="hidden"
						value="${inicializadores.domicilio.vialidadPrimaria.tipoVialidad.clave}"/>
						<span id="domicilio.calleError" class="error hiddenElement text-left"></span>
	</div>
	<div class="col-sm-3">
		<label class="control-label" for="domicilio.numExteriorAlf" style="text-align: left">
						<spring:message code="label.dom.recortado.num"/><span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
						<a class="btn btn-xs icono-help" data-toggle="tooltip" data-placement="top" title="Introduce el n&uacute;mero de tu vivienda"></a>
					</label>
						<input class="form-control ns_ campoBloqueable" ${bloqueoCampo} id="domicilio.numExteriorAlf" name="domicilio.numExteriorAlf" maxlength="10" type="text"
							  value="${inicializadores.domicilio.numExterior1} ${inicializadores.domicilio.numExteriorAlf}"/>
						<span id="domicilio.numExteriorAlfError" class="error hiddenElement text-left"></span>
					
	</div>
	<div class="col-sm-3">
		<label class="control-label" for="domicilio.numInteriorAlf" style="text-align: left">
						<spring:message code="label.numInterior"/>:
						<a class="btn btn-xs icono-help"data-toggle="tooltip" data-placement="top" title="En caso de tener n&uacute;mero interior escr&iacute;belo aqu&iacute; (ej. depto 3)"></a>
					</label>
						<input class="form-control ns_ campoBloqueable" ${bloqueoCampo} id="domicilio.numInteriorAlf" name="domicilio.numInteriorAlf" maxlength="10" type="text"
						 value="${inicializadores.domicilio.numInterior} ${inicializadores.domicilio.numInteriorAlf}"/>
						<span id="domicilio.numInteriorAlfError" class="error hiddenElement text-left"></span>
	</div>
</div>

<div style="margin-top:15px; margin-bottom:15px;">
	<c:if test="${inicializadores.mostrarMesajeRequeridos}">
		<div class="row">
			<div class="col-sm-12 text-left divOcultoRecortado" <c:if test="${bloquearCampos}">style="display:none"</c:if> >
				<span class="required">*</span> <spring:message code="label.campos.obli"/>
			</div>
		</div>
	</c:if>
</div>
</c:when>
<c:otherwise>
<table class="${inicializadores.classTabla}" style="width:95%">
	<tr>
		<td style="width:25%">
			<strong>
				<spring:message code="label.codigoPostal"/>
				<span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			</strong>
		</td>
		<td style="width:25%">
			<input class="numerico campoBloqueable ${inicializadores.classInputs}" id="domicilio.codigoPostal.codigoPostal" name="domicilio.codigoPostal.codigoPostal" type="text"
			${bloqueoCampo} value="${inicializadores.domicilio.codigoPostal.codigoPostal}" maxlength = "5" />
			
			<span id="domicilio.codigoPostal.codigoPostalError" class="error hiddenElement text-left"></span>
			<span id="errorNegocioLabel" class="error hiddenElement"></span>
		</td>
		<td colspan="2"  style="width:50%">
			<div class="divOcultoRecortado" <c:if test="${bloquearCampos}">style="display:none"</c:if> >
				<button class="${inicializadores.classButtonAceptar}" type="button" id="busquedaCp">
					<span class="glyphicon glyphicon-ok"></span> <spring:message code="label.btn.aceptar"/>
				</button>	
						 
				<button class="${inicializadores.classButtonLimpiar}" type="button" id="limpiarForm">
					<span class="glyphicon glyphicon-refresh"></span> Limpiar
				</button>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				<spring:message code="label.entidadFederativa"></spring:message>: 
			</strong>
		</td>
		<td>
			<input id="domicilio.asentamiento.localidad.clave" name="domicilio.asentamiento.localidad.clave" type="hidden" value="${inicializadores.domicilio.asentamiento.localidad.clave}"/>
			<input id="domicilio.asentamiento.localidad.nombre" name="domicilio.asentamiento.localidad.nombre" type="hidden" value="${inicializadores.domicilio.asentamiento.localidad.nombre}"/>
			
			<input id="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" name="domicilio.asentamiento.localidad.municipio.entidadFederativa.clave" type="hidden" 
			value="${inicializadores.domicilio.asentamiento.localidad.municipio.entidadFederativa.clave}"/> 
						
			<input class=" ${inicializadores.classInputs}" id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" name="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre" 
			type="text" readonly="readonly" value="${inicializadores.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}" style="width: 90%"/>
			
			<span id="domicilio.asentamiento.localidad.municipio.entidadFederativa.nombreError" class="error hiddenElement"></span>
		</td>
		<td>
			<strong>
				<spring:message code="label.municipio"/>: 
			</strong>
		</td>
		<td>
			<input id="domicilio.asentamiento.localidad.municipio.clave" name="domicilio.asentamiento.localidad.municipio.clave" type="hidden" value="${inicializadores.domicilio.asentamiento.localidad.municipio.clave}"/>
						
			<input class=" ${inicializadores.classInputs}" id="domicilio.asentamiento.localidad.municipio.nombre" name="domicilio.asentamiento.localidad.municipio.nombre" type="text"
			readonly="readonly" value="${inicializadores.domicilio.asentamiento.localidad.municipio.nombre}" style="width: 90%"/>
								
			<span id="domicilio.asentamiento.localidad.municipio.nombreError" class="error hiddenElement"></span>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				<spring:message code="label.selecciona.colonia"/>
				<span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			</strong>
		</td>
		<td colspan = "3">
			<select class="campoBloqueable ${inicializadores.classInputs}" ${bloqueoCampo} id="domicilio.asentamiento.clave" name="domicilio.asentamiento.clave" style="width: 50%">
			<c:choose>
				<c:when test="${isDomicilioNull}">
					<option value="-1"><spring:message code="label.dom.recortado.proporcionaCP"/></option>
				</c:when>
				<c:otherwise>
					<option value="${inicializadores.domicilio.asentamiento.clave}">${inicializadores.domicilio.asentamiento.nombre}</option>
				</c:otherwise>
			</c:choose>
			</select>		
			<span id="domicilio.asentamiento.claveError" class="error hiddenElement text-left"></span>
			<input id="domicilio.asentamiento.nombre" name="domicilio.asentamiento.nombre" type="hidden" value="${inicializadores.domicilio.asentamiento.nombre}"/>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				<spring:message code="label.vialidadPrimaria"/><span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			</strong>
		</td>
		<td colspan="3">
			<input class="campoBloqueable ${inicializadores.classInputs}" ${bloqueoCampo} id="domicilio.calle" name="domicilio.calle" maxlength="70" type="text" style="width: 97%" value="${inicializadores.domicilio.calle}"/>
			<span id="domicilio.calleError" class="error hiddenElement text-left"></span>
			
			<input id="domicilio.vialidadPrimaria.nombre" name="domicilio.vialidadPrimaria.nombre" type="hidden" value="${inicializadores.domicilio.vialidadPrimaria.nombre}"/>
			<input id="domicilio.vialidadPrimaria.clave" name="domicilio.vialidadPrimaria.clave" type="hidden" value="${inicializadores.domicilio.vialidadPrimaria.clave}"/>
			<input id="domicilio.vialidadPrimaria.tipoVialidad.descripcion" name="domicilio.vialidadPrimaria.tipoVialidad.descripcion" type="hidden" value="${inicializadores.domicilio.vialidadPrimaria.tipoVialidad.descripcion}"/>
			<input id="domicilio.vialidadPrimaria.tipoVialidad.clave" name="domicilio.vialidadPrimaria.tipoVialidad.clave" type="hidden" value="${inicializadores.domicilio.vialidadPrimaria.tipoVialidad.clave}"/>
		</td>
	</tr>
	<tr>
		<td>
			<strong>
				<spring:message code="label.dom.recortado.num"/><span class="required labelObligatorio" <c:if test="${bloquearCampos}">style="display:none"</c:if> >*</span>: 
			</strong>
		</td>
		<td>
			<input class="campoBloqueable ${inicializadores.classInputs}" ${bloqueoCampo} id="domicilio.numExteriorAlf" name="domicilio.numExteriorAlf" maxlength="35" type="text" value="<%=numExtAlfRec%>" style="width: 90%"/>
			<span id="domicilio.numExteriorAlfError" class="error hiddenElement text-left"></span>
		</td>
		<td>
			<strong>
				<spring:message code="label.numInterior"/>: 
			</strong>
		</td>
		<td>
			<input class="campoBloqueable ${inicializadores.classInputs}" ${bloqueoCampo} id="domicilio.numInteriorAlf" name="domicilio.numInteriorAlf" maxlength="10" type="text" value="${inicializadores.domicilio.numInterior} ${inicializadores.domicilio.numInteriorAlf}" style="width: 90%"/>
			<span id="domicilio.numInteriorAlfError" class="error hiddenElement text-left"></span>
		</td>
	</tr>
	<tr>
		<td colspan="4">
		<div class="text-left divOcultoRecortado" <c:if test="${bloquearCampos}">style="display:none"</c:if> >
			<span class="required">*</span><spring:message code="label.campos.obli"/>
		</div>
		</td>
	</tr>
</table>

</c:otherwise>
</c:choose>
</form>

<div id="dialog-confirm"></div>
<div id="dialog-error"></div>
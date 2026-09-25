<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect" %>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto" %>

<script type="text/javascript">
	$.fn.clearCustomForm = function() {
		/* Si la condición se cumple, entonces el id existe
		 * y se trata de una modificación y no es necesario
		 * implementar la limpieza del formulario
		 */
		if(!$('#indexMedioContacto').length){
		    return this.each(function() {
		        $('input,select,textarea', this).clearCustomFields();
		    });
		}
	};

	$.fn.clearCustomFields = $.fn.clearInputs = function() {
	    return this.each(function() {
	        var t = this.type, tag = this.tagName.toLowerCase();
	        if (t == 'text' || t == 'password' || tag == 'textarea')
	            this.value = '';
	        else if (t == 'checkbox' || t == 'radio')
	            this.checked = false;
	    });
	};

	$(document).ready(function(){
		$('#tipoMedioContacto\\.idTipoMedioContacto').change(function(){
			$('#agregarMedioContactoForm').clearCustomForm();
			ocultarFormulariosAlta();
			
			if($(this).val() == <%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO.intValue()%>){
				$('#correoElectronico').show();
				$('#control').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_TELEFONO_FIJO.intValue()%>){
				$('#telefonoFijo').show();
				$('#control').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_TELEFONO_MOVIL.intValue()%>){
				$('#telefonoMovil').show();
				$('#control').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_FACEBOOK.intValue()%>){
				$('#facebook').show();
				$('#control').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_TWITTER.intValue()%>){
				$('#twitter').show();
				$('#control').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
			}
			
			setSizeWithinIframe(document);
		});
		
		$('#btnGuardarMedioContacto').click(function(){
			fnGuardarModificarMedioContacto();
		});
		
		if($('#indexMedioContacto').val() >= 0){
			$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
		}
	});
	
	function ocultarFormulariosAlta(){
		$('#correoElectronico').hide();
		$('#telefonoFijo').hide();
		$('#telefonoMovil').hide();
		$('#facebook').hide();
		$('#twitter').hide();
		
		$('#control').hide();
	}
	
	var fnGuardarModificarMedioContacto = function (){
		var url;
		var objCtrlMediosContacto;
		
		if($('#indexMedioContacto').val() >= 0){
			// Significa que se está editando
			url = "/gestionMediosContacto-web/medios/particulares/administrar/confirmar-modificacion/" + $('#indexMedioContacto').val();
			objCtrlMediosContacto = parent.objModifMedio;
			
			$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", false);
		}else {
			url = $("form#agregarMedioContactoForm").attr('action');
			objCtrlMediosContacto = parent.objRegistroMedio;
		}
		
		fnHideErrores("form#agregarMedioContactoForm");

		var oForm = $("form#agregarMedioContactoForm").serializeObject(true);
		
		$.post(url, oForm, function(data) {
			objCtrlMediosContacto.mediosContacto = data;
			objCtrlMediosContacto.cerrar();
		}).error(function(data){
			if($('#indexMedioContacto').val() >= 0){
				$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
			}
			fnProcesarErrores(data, "form#agregarMedioContactoForm");
			setSizeWithinIframe(document);
		});
	};
		
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="col-xs-12">
	<form:form action="${contextpath}/medios/particulares/administrar/agregar" method="POST"
		modelAttribute="medioContactoFormWrapper" id="agregarMedioContactoForm" role="form">

		<form:hidden path="idPersona" id="idPersona" />

		<c:if test="${not empty indexMedioContacto}">
			<input type="hidden" id="indexMedioContacto" value="${indexMedioContacto}" />
			<form:hidden path="telefonoFijo.clave" />
			<form:hidden path="telefonoMovil.clave" />
			<form:hidden path="correoElectronico.clave" />
			<form:hidden path="facebook.clave" />
			<form:hidden path="twitter.clave" />
		</c:if>

		<div class="form-group">
			<label for="tipoMedioContacto.idTipoMedioContacto">
				<span class="required">*</span>
				Tipo de Medio de contacto
			</label>
			<combo:creaCombo idHtml="tipoMedioContacto.idTipoMedioContacto" idHtmlContenedor="agregarMedioContactoForm"
				entidad="mx.gob.imss.ctirss.delta.persistence.DitTipoContacto"
				idHtmlValor="${medioContactoFormWrapper.tipoMedioContacto.idTipoMedioContacto}" mostrarSoloActivos="false"
				cssClassname="form-control" />
			<form:hidden path="tipoMedioContacto.descripcion" id="tipoMedioContacto.descripcion" />
		</div>

		<div id="telefonoFijo" style="display: none;">
			<fieldset class="fieldset-medios">
				<legend>
					Indique los datos de su tel&eacute;fono particular
					<span></span>
				</legend>
				<!-- Campo de clave de larga distancia -->
				<div class="form-group">
					<label for="">
						<spring:message code="label.telefono.claveLada" />
					</label>
					<form:input type="text" path="telefonoFijo.claveLada" maxlength="4" cssClass="numericoSinPunto form-control" />
					<span id="telefonoFijo.claveLadaError" class="error hiddenElement"></span>
				</div>
				<!-- Campo del numero -->
				<div class="form-group">
					<label for="">
						<span class="required">*</span>
						<spring:message code="label.telefono.numero" />
					</label>
					<form:input type="text" path="telefonoFijo.numero" maxlength="8" cssClass="numericoSinPunto form-control" />
					<span id="errorNegocioLabel" class="error hiddenElement"></span>
					<span id="telefonoFijo.numeroError" class="error hiddenElement"></span>
				</div>
				<!-- Campo de extension -->
				<div class="form-group">
					<label for="">
						<spring:message code="label.telefono.extension" />
					</label>
					<form:input type="text" path="telefonoFijo.extension" maxlength="6" cssClass="numericoSinPunto form-control" />
					<span id="telefonoFijo.extensionError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>


		<div id="correoElectronico" style="display: none;">
			<fieldset class="fieldset-medios">
				<legend>
					Indique los datos de cuenta de correo electr&oacute;nico
				</legend>

				<!-- Correo Electronico -->
				<div class="form-group">
					<label for="">
						<span class="required">*</span>
						<spring:message code="label.correoElectronico" />
					</label>
					<form:input type="text" path="correoElectronico.correo" maxlength="50" cssClass="form-control" />
					<span id="correoElectronico.correoError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="telefonoMovil" style="display: none;">
			<fieldset class="fieldset-medios">
				<legend>
					Indique los datos de su tel&eacute;fono m&oacute;vil
				</legend>

				<!-- Campo del numero -->
				<div class="form-group">
					<label for="">
						<span class="required">*</span>
						<spring:message code="label.telefono.numero" />
					</label>
					<form:input type="text" path="telefonoMovil.numero" maxlength="15" cssClass="numericoSinPunto form-control" />
					<span id="telefonoMovil.numeroError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="facebook" style="display: none;">
			<fieldset class="fieldset-medios">
				<legend>
					Indique los datos de su cuenta de Facebook
				</legend>

				<!-- Campo cuenta -->
				<div class="form-group">
					<label for="">
						<span class="required">*</span>
						<spring:message code="label.cuenta" />
					</label>
					<form:input type="text" path="facebook.cuenta" maxlength="50" cssClass="form-control" />
					<span id="errorNegocioLabel" class="error hiddenElement"></span>
					<span id="facebook.cuentaError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="twitter" style="display: none;">
			<fieldset class="fieldset-medios">
				<legend>
					Indique los datos de su cuenta de Twitter
				</legend>

				<!-- Campo cuenta -->
				<div class="form-group">
					<label for="">
						<span class="required">*</span>
						<spring:message code="label.cuenta" />
					</label>
					<form:input type="text" path="twitter.cuenta" maxlength="50" cssClass="form-control" />
					<span id="errorNegocioLabel" class="error hiddenElement"></span>
					<span id="twitter.cuentaError" class="error hiddenElement"></span>
				</div>
			</fieldset>
		</div>

		<div id="control" class="text-right" style="display: none;">
			<button type="button" class="btn btn-primary" id="btnGuardarMedioContacto">Aceptar</button>
		</div>
	</form:form>
</div>
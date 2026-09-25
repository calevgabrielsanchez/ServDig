<%@ include file="../../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect" %>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto" %>

<style type="text/css">

	.site_position_center {
	    width: 100%;
	}
	
	.page_holder {
	    margin: 0;
	    width: 100%;
	}

	.page_holder table, .page_holder_no_height table {
	    margin: none;
	}
	
	.page_holder table tr td, .page_holder_no_height table tr td {
		border: none;
	}
</style>

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
			}
		});
		
		$('#btnGuardarMedioContactoFiscal').click(function(){
			fnGuardarModificarMedioFiscal();
		});
		
		if($('#indexMedioContacto').val() >= 0){
			$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
			$('#tipoMedioContacto\\.idTipoMedioContacto').trigger('change');
		}
	});
	
	function ocultarFormulariosAlta(){
		$('#correoElectronico').hide();
		$('#telefonoFijo').hide();
		$('#telefonoMovil').hide();
		
		$('#control').hide();
	}
	
	var fnGuardarModificarMedioFiscal = function (){
		var url;
		
		if($('#indexMedioContacto').val() >= 0){
			// Significa que se está editando
			url = "/gestionMediosContacto-web/medios/fiscales/administrar/confirmar-modificacion/" + $('#indexMedioContacto').val();
			$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", false);
		}else {
			url = $("form#agregarMedioContactoForm").attr('action');
		}
		
		fnHideErrores("form#agregarMedioContactoForm");
		
		var oForm = $("form#agregarMedioContactoForm").toObject();
		
		$.postJSON(url, oForm, function(data) {
			parent.objCtrlMediosFiscales.mediosContacto = data;
			parent.objCtrlMediosFiscales.cerrar();
		}).error(function(data){
			fnProcesarErrores(data, "form#agregarMedioContactoForm");
			
			if($('#indexMedioContacto').val() >= 0){
				$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
			}
			
		});
	};
		
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<form:form
	action="${contextpath}/medios/fiscales/administrar/agregar"
	method="POST" modelAttribute="medioContactoFormWrapper" id="agregarMedioContactoForm">
	
	<c:if test="${not empty indexMedioContacto}">
		<input type="hidden" id="indexMedioContacto" value="${indexMedioContacto}" />
		<form:hidden path="telefonoFijo.clave"/>
		<form:hidden path="telefonoMovil.clave"/>
		<form:hidden path="correoElectronico.clave"/>
	</c:if>
	
	<div class="page_holder">
		<div class=" contenedor">
			<div class="row">
				<div class="cell">
					<table id="medios" style="border: none;">
						<tr>
							<td style="vertical-align: top;">
								<form:label path="tipoMedioContacto.idTipoMedioContacto"
									cssClass="wide">Tipo de Medio de contacto*</form:label> 
								
								<form:select path="tipoMedioContacto.idTipoMedioContacto" id="tipoMedioContacto.idTipoMedioContacto">
									<form:option value="-1">--Por favor seleccione--</form:option>
									<form:option value="<%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO.intValue()%>">Correo Electr&oacute;nico</form:option>
									<form:option value="<%=TipoMedioContacto.TIPO_TELEFONO_FIJO.intValue()%>">Tel&eacute;fono Fijo</form:option>
									<form:option value="<%=TipoMedioContacto.TIPO_TELEFONO_MOVIL.intValue()%>">Tel&eacute;fono M&oacute;vil</form:option>
								</form:select>
								
								<form:hidden path="tipoMedioContacto.descripcion" id="tipoMedioContacto.descripcion" />
							</td>
						</tr>
						<tr>
							<td>
								<span id="errorNegocioLabel" class="error hiddenElement"></span>
								<div id="telefonoFijo" style="display: none;">
									<fieldset>
										<legend>
											<strong>Indique los datos de su
											tel&eacute;fono particular.</strong><span></span>
										</legend>

										<!-- Campo del numero -->
										<fieldset class="fsInterno">
											<div>
												<span id="telefonoFijo.numeroError" class="error hiddenElement"></span>
											</div>
											<div>
												<span></span>
												<form:label path="telefonoFijo.numero">
													<spring:message code="label.telefono.numero" />

												</form:label>
												<form:input type="text" path="telefonoFijo.numero"
													maxlength="15" cssClass="numerico" />
											</div>
										</fieldset>

										<!-- Campo de clave de larga distancia -->
										<fieldset class="fsInterno">
											<div>
												<span id="telefonoFijo.claveLadaError" class="error hiddenElement"></span>
											</div>
											<div>
												<form:label path="telefonoFijo.claveLada">
													<spring:message code="label.telefono.claveLada" />
												</form:label>

												<form:input type="text" path="telefonoFijo.claveLada"
													maxlength="4" cssClass="numerico" />
											</div>
										</fieldset>

										<!-- Campo de extension -->
										<fieldset class="fsInterno">
											<div>
												<span id="telefonoFijo.extensionError" class="error hiddenElement"></span>
											</div>
											<div>
												<form:label path="telefonoFijo.extension">
													<spring:message code="label.telefono.extension" />
												</form:label>

												<form:input type="text" path="telefonoFijo.extension"
													maxlength="4" cssClass="numerico" />
											</div>
										</fieldset>
									</fieldset>
								</div>


								<div id="correoElectronico" style="display: none;">
									<fieldset>
										<legend>
											<strong>Indique los datos de cuenta de
											correo electr&oacute;nico.</strong>
										</legend>

										<!-- Correo Electronico -->
										<fieldset class="fsInterno">
											<div>
												<span id="correoElectronico.correoError" class="error hiddenElement"></span>
											</div>
											<div>
												<form:label path="correoElectronico.correo">
													<spring:message code="label.correoElectronico" />
												</form:label>

												<form:input type="text" path="correoElectronico.correo"
													maxlength="30" />

											</div>
										</fieldset>
									</fieldset>
								</div>

								<div id="telefonoMovil" style="display: none;">
									<fieldset>
										<legend>
											<strong>Indique los datos de su
											tel&eacute;fono m&oacute;vil.</strong>
										</legend>

										<!-- Campo del numero -->
										<fieldset class="fsInterno">
											<div>
												<span id="telefonoMovil.numeroError" class="error hiddenElement"></span>
											</div>
											<div>
												<form:label path="telefonoMovil.numero">
													<spring:message code="label.telefono.numero" />
												</form:label>
												<form:input type="text" path="telefonoMovil.numero"
													maxlength="15" cssClass="numerico" />
											</div>
										</fieldset>
									</fieldset>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="control" style="float: right; display: none;">
									<input type="button" value="Aceptar" class="mboton" id="btnGuardarMedioContactoFiscal"/>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	</div>
</form:form>
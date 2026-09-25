<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto" %>

<script type="text/javascript">
	var idTipoContactoMovil = <%=TipoMedioContacto.TIPO_TELEFONO_MOVIL.intValue()%>;
	var idTipoContactoFijo = <%=TipoMedioContacto.TIPO_TELEFONO_FIJO.intValue()%>;
	var idTipoContactoCorreo = <%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO.intValue()%>;
	var idTipoContactoFacebook = <%=TipoMedioContacto.TIPO_FACEBOOK.intValue()%>;
	var idTipoContactoTwitter = <%=TipoMedioContacto.TIPO_TWITTER.intValue()%>;
	
	$.fn.clearCustomForm = function() {
	    return this.each(function() {
	        $('input,select,textarea', this).clearCustomFields();
	    });
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
			$('#agregarMedioContactoFormAlta').clearCustomForm();
			ocultarFormulariosMediosContactoAlta();
			fnOcultaErrores($("#errorMedioContacto"));
			if($(this).val() == <%=TipoMedioContacto.TIPO_CORREO_ELECTRONICO.intValue()%>){
				$('#correoElectronico').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
				$('#correoElectronico\\.correo').focus();
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_TELEFONO_FIJO.intValue()%>){
				$('#telefonoFijo').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
				$('#telefonoFijo\\.numero').focus();
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_TELEFONO_MOVIL.intValue()%>){
				$('#telefonoMovil').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
				$('#telefonoMovil\\.numero').focus();
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_FACEBOOK.intValue()%>){
				$('#facebook').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
				$('#facebook\\.cuenta').val('facebook.com/');
				$('#facebook\\.cuenta').focus();
			}else if($(this).val() == <%=TipoMedioContacto.TIPO_TWITTER.intValue()%>){
				$('#twitter').show();
				$('#tipoMedioContacto\\.descripcion').val($('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text());
				$('#twitter\\.cuenta').focus();
			} 	
		});
		
		/*$('#btnGuardarMedioContacto').click(function(){
			fnGuardarModificarMedioContacto();
		});*/
		
		if($('#indexMedioContacto').val() >= 0){
			$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
		}
	});
	
	function ocultarFormulariosMediosContactoAlta(){
		$('#correoElectronico').hide();
		$('#telefonoFijo').hide();
		$('#telefonoMovil').hide();
		$('#facebook').hide();
		$('#twitter').hide();
		
	}
	
	function resetMedioContactoOptions(){
		$('#tipoMedioContacto\\.idTipoMedioContacto').val(-1);
	}
	
</script>

<div id="divMedioContactoAlta" style="display: none;" class="page_holder dialogo_holder">

	<c:set var="contextpath" value="<%=request.getContextPath()%>" />

	<form:form modelAttribute="medioContactoFormAlta" id="agregarMedioContactoFormAlta">
	
		<c:if test="${not empty indexMedioContacto}">
			<input type="hidden" id="indexMedioContacto" value="${indexMedioContacto}" />
			<form:hidden path="telefonoFijo.clave"/>
			<form:hidden path="telefonoMovil.clave"/>
			<form:hidden path="correoElectronico.clave"/>
			<form:hidden path="facebook.clave"/>
			<form:hidden path="twitter.clave"/>
		</c:if>
			
		<table id="medios" style="border: none !important;">
			<tr>
				<td style="border: none !important;">
					<form:label path="tipoMedioContacto.idTipoMedioContacto"
						cssClass="wide">Tipo de Medio de contacto*</form:label> 
					<combo:creaCombo
						idHtml="tipoMedioContacto.idTipoMedioContacto" idHtmlContenedor="agregarMedioContactoFormAlta"
						entidad="mx.gob.imss.ctirss.delta.persistence.DitTipoContacto"
						idHtmlValor="${medioContactoFormAlta.tipoMedioContacto.idTipoMedioContacto}" 
						mostrarSoloActivos = "false"
						/>
					<form:hidden path="tipoMedioContacto.descripcion" id="tipoMedioContacto.descripcion" />
				</td>
			</tr>
			<tr>
				<td style="border: none !important;">
					<div id="errorMedioContacto" style="color: red !important;"></div>
				</td>
			</tr>
			<tr>
				<td style="border: none !important;">
					<div id="telefonoFijo" style="display: none;">
						<div>
							<h3>Tel&eacute;fono particular</h3>
							<p style="font-size: .9em;">Indique los datos de su
								tel&eacute;fono particular.</p>
						</div>
						<br />
						
						<form:label path="telefonoFijo.numero">
							<spring:message code="label.telefono.numero" />

						</form:label>
						<form:input type="text" path="telefonoFijo.numero"
							maxlength="15" cssClass="numerico" />
						<br />
						
						<form:label path="telefonoFijo.claveLada">
							<spring:message code="label.telefono.claveLada" />
						</form:label>

						<form:input type="text" path="telefonoFijo.claveLada"
							maxlength="4" cssClass="numerico" />
						
						<br />
						
						<form:label path="telefonoFijo.extension">
							<spring:message code="label.telefono.extension" />
						</form:label>

						<form:input type="text" path="telefonoFijo.extension"
							maxlength="4" cssClass="numerico" />
						
					</div>


					<div id="correoElectronico" style="display: none;">
						<div>
							<h3>Correo electr&oacute;nico</h3>
							<p style="font-size: .9em;">Indique los datos de cuenta de
								correo electr&oacute;nico.</p>
						</div>
						<br />
							<form:input type="text" path="correoElectronico.correo" maxlength="30" />
							
					</div>

					<div id="telefonoMovil" style="display: none;">

						<div>
							<h3>Tel&eacute;fono m&oacute;vil</h3>
							<p style="font-size: .9em;">Indique los datos de su
								tel&eacute;fono m&oacute;vil.</p>
						</div>
						<br />

						<form:input type="text" path="telefonoMovil.numero"
										maxlength="15" cssClass="numerico" />
					</div>

					<div id="facebook" style="display: none;">
						<div>
							<h3>Facebook</h3>
							<p style="font-size: .9em;">Indique los datos de su cuenta
								de Facebook.</p>
						</div>
						<br />

						<form:input type="text" path="facebook.cuenta"
										maxlength="50" />
					</div>

					<div id="twitter" style="display: none;">
						<div>
							<h3>Twitter</h3>
							<p style="font-size: .9em;">Indique los datos de su cuenta
								de Twitter.</p>
						</div>
						<br />

						<form:input type="text" path="twitter.cuenta" maxlength="50" />
					</div>
				</td>
			</tr>
		</table>
	</form:form>
</div>
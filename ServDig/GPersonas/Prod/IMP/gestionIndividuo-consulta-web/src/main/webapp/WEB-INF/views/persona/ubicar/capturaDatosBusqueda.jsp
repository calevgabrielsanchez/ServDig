<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script>
	var msgRequerido = 'Campo requerido';
	var msgFechaInvalida = 'Fecha inv\u00e1lida'
	
	$(function() {
		var fecha = new Date();
		anioFin = fecha.getFullYear();
		anioFin = anioFin ;
		rango = "1900:"+anioFin;
	    
		$('input#fechaNacimiento').datepicker({
	    	showOn : "button",
	    	buttonImage: context_path + "/static/resources/imagenes/calendar.gif",
	    	buttonImageOnly : true,
	    	changeMonth : true,
	    	changeYear : true,
	    	yearRange : rango,
	    	maxDate:new Date(),
	    	onSelect: function (date) {
	    		$('input#fechaNacimiento').focus();
	    	    }
	    		
	    });
		
	    $('div#busquedaFormContainerPF').hide();
	    $('div#busquedaFormContainerPM').hide();
	    
	    // SE CHECA QUÉ FORMULARIO PRESENTAR
		if (window.parent.formulario.tipo == 'fisica') {
			$('div#busquedaFormContainerPM').hide();
			$('div#busquedaFormContainerPF').show();
			$nombre = $('input#nombre');
			$nombre.focus();
			$('#sexo\\.idSexo').attr('tabindex', 5);
			$('#lugarNacimiento\\.clave').attr('tabindex', 6);
			$('#buscarpf').attr('tabindex', 8)
		} else if (window.parent.formulario.tipo == 'moral') {
			$('div#busquedaFormContainerPF').hide();
			$('div#busquedaFormContainerPM').show();
			$rfc = $('input#rfcpm');
			$rfc.focus();
		}

		//ACCION BUSCAR PERSONA FISICA
		$('button#buscarpf').click(function(e) {
			e.preventDefault();
			validarForma('busquedaFormPF');
		});
		
		//ACCION BUSCAR PERSONA MORAL
		$('button#buscarpm').click(function(e) {
			e.preventDefault();
			validarForma('busquedaFormPM');
		});
				
		$('button#limpiarpf').click(function(e){
			$('form').each (function(){
				  this.reset();
			});
			
			$('span.error').text('').removeClass('show').addClass('hide');
			$('input#nombre').focus();
			
			setSizeWithinIframe(document);
		});
		

		$('button#limpiarpm').click(function(e) {
			$('form').each(function() {
				this.reset();
			});
			
			$('span.error').text('').removeClass('show').addClass('hide');
			$('input#rfcpm').focus();
			
			setSizeWithinIframe(document);
		});
		
		$('#sinApellido').click(function(e){
			var marcado = $("#sinApellido").prop("checked") ? true : false;
			if (marcado) {
				$("#segundoApellido").removeAttr("disabled");
			} else {
				$("#segundoApellido").attr("disabled", "disabled");
				$("input#segundoApellido").val("");
			}
		});
	});
	
	function validarForma(idForma) {
		
		$('span.error').text('').removeClass('show').addClass('hide');
		
		var errors = false;
		
		$('#' + idForma + ' .req').each(function() {
			var spanError = null;
			if($(this).is('input')) {
				if($(this).val() == "") {
					spanError = $(this).parent().find('span.error');
					mostrarError(spanError, msgRequerido);
					errors = true;
				} else {
					if ($(this).hasClass('hasDatepicker')) {
						if (!validaFecha($(this).val())){
							spanError = $(this).parent().find('span.error');
							mostrarError(spanError, msgFechaInvalida);
							errors = true;
						}
					} else if ($(this).hasClass('rfc')) {
						var msgErrorRfc = validaRfc($(this).val());
						
						if (msgErrorRfc != null) {
							spanError = $(this).parent().find('span.error');
							mostrarError(spanError, msgErrorRfc);
							errors = true;
						}
					}
				}
			} else if($(this).is('select')) {
				if($(this).val() == -1) {
					spanError = $(this).parent().find('span.error');
					mostrarError(spanError, msgRequerido);
					errors = true;
				}
			}
		});
		
		if (!errors) {
			convertirInputsAMayuscula();
			$('form#' + idForma).submit();
		} else {
			setSizeWithinIframe(document);
		}
	}
	
	function validaFecha(fecha) {
		var reg = new RegExp("(((0[123456789]|1[0123456789]|2[0123456789]|3[01])/(0[123456789]|10|11|12)/(([1][9][0-9][0-9])|([2][0-9][0-9][0-9]))))");
		
		if (reg.test(fecha)) {
			var splitFecha = fecha.split("/");
			var fechaNueva = new Date(splitFecha[2],parseInt(splitFecha[1])-1,parseInt(splitFecha[0]));
			
			if(fechaNueva.getDate() == parseInt(splitFecha[0]) 
					&& fechaNueva.getMonth() == parseInt(splitFecha[1])-1 
					&& fechaNueva.getFullYear() == splitFecha[2]){
				return true;
			}else{
				//LA FECHA RECIBIDA NO EXISTE EN EL CALENDARIO
				return false;
			}
		} 
		else{
			//NO CUMPLE CON EL PATRON DE DIGITOS LA FECHA
			return false;
		}
	}
	
	function validaRfc(rfc) {
		var msgError = null;
		
		if(rfc.length <= 11) {
			msgError = 'Longitud del RFC es inv\u00e1lido';
		} else { 
			var rfcRegEx = '(([A-Z]|[a-z]){3})([0-9]{6})((([A-Z]|[a-z]|[0-9]){3}))';
			var validRfc = new RegExp(rfcRegEx);
			
			if (!validRfc.test(rfc)) {
				msgError = 'Formato de RFC es inv\00e1lido';
			}
		}
		
		return msgError;
	}
	
	function convertirInputsAMayuscula() {
		$("input").each(function() {
			var elemento = $(this);
			$(this).val(elemento.val().toUpperCase());
		});
	}
	
	function mostrarError(spanError, errorMsg) {
		spanError.text(errorMsg).removeClass('hide').addClass('show');
	}
</script>

<div class="container-fluid">
	<div class="row" id="busquedaFormContainerPF">
		<div class="col-sm-12">

			<div class="alert alert-info">
				<strong>Paso 1: </strong>
				Consulta de Personas F&iacute;sicas por datos b&aacute;sicos
			</div>

			<p>La persona a localizar ser&aacute; la persona que requiere
				iniciar el tr&aacute;mite</p>

			<form:form modelAttribute="fisicaForm" role="form" id="busquedaFormPF" method="post"
				action="${contextoOrigen}/ubicar/persona/buscar/fisica">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<form:label path="nombre">
								<span class="required">*</span>Nombre(s)
							</form:label>
							<form:input path="nombre" id="nombre" maxlength="50" cssClass="alfanumericoNSS form-control req"
									style="text-transform: uppercase" tabindex="1"/>
							<span class="error hide"></span>
						</div>
						<div class="form-group">
							<input type="checkbox" name="sinApellido" id="sinApellido" checked="checked" tabindex="3" class="m-r-xs"/>
							<form:label path="segundoApellido">
								Segundo Apellido			
							</form:label>							
							<form:input path="segundoApellido" id="segundoApellido" maxlength="50" cssClass="alfanumericoNSS form-control"
								style="text-transform: uppercase;" tabindex="4"/>
						</div>
						<div class="form-group">
							<form:label path="lugarNacimiento.clave">
								<span class="required">*</span>Lugar de Nacimiento
							</form:label>
							<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="busquedaFormPF"
								entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" idHtmlValor="${fisica.lugarNacimiento.clave}"
								mostrarSoloActivos="true" cssClassname="form-control req" />
							<span class="error hide"></span>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">		
							<form:label path="primerApellido">
								<span class="required">*</span>Primer Apellido
							</form:label>
							<form:input path="primerApellido" id="primerApellido" maxlength="50" cssClass="alfanumericoNSS form-control req"
								style="text-transform: uppercase" tabindex="2" />
							<span class="error hide"></span>
						</div>
						<div class="form-group">
							<form:label path="sexo.idSexo">
								<span class="required">*</span>Sexo
							</form:label>
							<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="busquedaFormPF"
								entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${fisica.sexo.idSexo}"
								mostrarSoloActivos="true" cssClassname="form-control req" />
							<span class="error hide"></span>
						</div>
						<div class="form-group">
							<form:label path="fechaNacimiento">
								<span class="required">*</span>Fecha de Nacimiento
							</form:label>
							<form:input path="fechaNacimiento" id="fechaNacimiento" cssStyle="margin-right: 8px; width: 90%;"
								maxlength="10" class="form-control req date" tabindex="7"/>
							<span class="error hide"></span>
						</div>
					</div>
				</div>
				<div class="text-right m-t-sm">
					<button type="button" id="limpiarpf" class="btn btn-default">LIMPIAR</button>
					<button type="button" id="buscarpf" class="btn btn-primary">BUSCAR</button>
				</div>
			</form:form>
		</div>
	</div>

	<div class="row" id="busquedaFormContainerPM">
		<div class="col-sm-12">
			<div class="alert alert-info">
				<strong>Paso 1:</strong>
				Consulta de Personas Morales por RFC.
			</div>

			<p>La persona a localizar ser&aacute; la persona que requiere iniciar el tr&aacute;mite</p>
			<div class="row">
  				<div class="col-sm-8 col-sm-offset-1">
					<form:form modelAttribute="moralForm" role="form" id="busquedaFormPM" method="post"
						action="${contextoOrigen}/ubicar/persona/buscar/moral" cssClass="form-horizontal">
						<div class="form-group">
							<form:label path="rfc" cssClass="col-sm-4 control-label">
								<span class="required">*</span>RFC:
							</form:label>
							<div class="col-sm-8">
								<form:input path="rfc" id="rfcpm" maxlength="12" cssClass="alfanumericoEstricto form-control rfc req"
									style="text-transform: uppercase" />
								<span class="error hide"></span>
							</div>
						</div>
						
						<div class="text-right m-t-sm">
							<button type="button" id="limpiarpm" class="btn btn-default">LIMPIAR</button>
							<button type="button" id="buscarpm" class="btn btn-primary">BUSCAR</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
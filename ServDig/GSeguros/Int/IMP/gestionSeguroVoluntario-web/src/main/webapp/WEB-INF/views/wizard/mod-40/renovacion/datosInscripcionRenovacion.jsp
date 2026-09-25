<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
.ui-selectable li {
	padding: 15px 25px;
}

.sub-header {
	padding-bottom: 10px;
	border-bottom: 2px solid #eee;
}

.popover {
	width: 450px;
	max-width: 450px;
}
</style>

<script type="text/javascript">
	$(function(){
		$('a#siguientePaso').click(function(){
			$('#validacionSalarioMin').hide();
			$('#validacionSalarioMax').hide();
			$('#validacionSalarioUltReg').hide();
			$('span#msgErrorFechaBaja').text('');
			$('span#msgErrorFechaBaja').hide();
			$('span#msgErrorSalario').text('');
			$('span#msgErrorSalario').hide();
			var salario = $('input#salario').val();
			var formatSalario = parseFloat(salario);
			
				if(salario === ''){
					$('span#msgErrorSalario').text('* El campo es requerido.');
					$('span#msgErrorSalario').show();
					setSizeWithinIframe(document);
					return false;
				}else if(!isNumeric(salario)){
					$('span#msgErrorSalario').text('* El formato del dato no es v&aacute;lido.');
					$('span#msgErrorSalario').show();
					setSizeWithinIframe(document);
					return false;
				}
			    else if(formatSalario < '${sdiUltReg}'){
				  $('#validacionSalarioUltReg').show();
				  setSizeWithinIframe(document);
				  return false;
			  }else if(formatSalario > '${sdiMax}'){
				  $('#validacionSalarioMax').show();
				  setSizeWithinIframe(document);
				  return false;
			  }else if(formatSalario < '${sdiMin}'){
                    $('#validacionSalarioMin').show();
                    setSizeWithinIframe(document);
                    return false;
                }else{
				  $( "#confirmarDatosForm" ).submit();
			  }
		});
		
		function isNumeric(n) {
			  return !isNaN(parseFloat(n)) && isFinite(n);
	    }
        
	});
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:set var="defaultLocale" value="${pageContext.request.locale}" />
			<fmt:setLocale value="es_MX" scope="session" />
			<div class="titulo">
				<span>Paso 2 de 4: Indicar salario</span>
			</div>

			<div class="titulo">
				<span>Datos de Reingreso</span>
				<hr class="red m-b-md">
			</div>

			<div class="alert alert-danger" style="display: none;"
				id="validacionSalarioMin">
				<span id="mensaje-validacion">El salario para tu
					reingreso en la continuaci&oacute;n voluntaria debe ser al
					menos de <fmt:formatNumber value="${sdiMin}" type="currency" />
				</span>
			</div>
			<div class="alert alert-danger" style="display: none;"
				id="validacionSalarioUltReg">
				<span id="mensaje-validacion">El salario para tu
					reingreso en la continuaci&oacute;n voluntaria debe ser al
					menos de <fmt:formatNumber value="${sdiUltReg}" type="currency" />
				</span>
			</div>
			<div class="alert alert-danger" style="display: none;"
				id="validacionSalarioMax">
				<span id="mensaje-validacion">El salario para tu
					reingreso en la continuaci&oacute;n voluntaria debe ser
					m&aacute;ximo <fmt:formatNumber value="${sdiMax}" type="currency" />
				</span>
			</div>
			<div id="datosIncripcion" class="m-b-lg">
				<form class="form-horizontal" role="form" id="confirmarDatosForm"
					action="${contextPath}/wizard/continuacionVoluntaria/renovacion/comunes/confirmarDatos"
					method="POST">
					<div class="form-group col-sm-12">
						<label class="col-sm-7 control-label"><span
							style="color: red">* </span>Salario base con el que deseas
							cotizar. Escribe el salario en pesos y centavos.</label> 
							<input
							class="col-sm-2 numericoPositivo2Decimales" type="text"
							maxlength="9" style="text-align: right; margin-top: 10px"
							value="${sdiUltReg}" name="salario" id="salario"> <input
							type="hidden" id="baja" name="baja" value="baja">
						<div style="float: left;">
							<a data-toggle="popover" id="ayudaSalarioRenovacion"
								class="btn btn-default btn-xs icono-help"
								style="margin-left: 5px; margin-top: 10px; text-decoration: none;"
								data-original-title="" title=""> </a>
						</div>
					</div>
					<div class="form-group col-sm-12">
						<div class="col-sm-12">
							<span style="color: red; display: none; margin-left: 10px;"
								id="msgErrorSalario"></span>
						</div>
					</div>

				</form>
			</div>
			<fmt:setLocale value="${defaultLocale}" scope="session" />
		</div>
	</div>
	<br>

	<div class=""></div>

	<div class="pie row">
		<div class="col-sm-4"></div>
		<div class="controles col-sm-8">
			<div class="pull-right">
				<button id="cancelarTramiteAux" class="btn btn-default">
					Cancelar</button>
				<a id="siguientePaso" class="btn btn-primary">Continuar</a>
			</div>
		</div>
	</div>
	<div id="dialogoMsgSeleccion"></div>
</div>

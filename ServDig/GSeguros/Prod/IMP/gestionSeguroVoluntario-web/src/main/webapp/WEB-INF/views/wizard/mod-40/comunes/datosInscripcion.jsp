<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.ui-selectable li {
	    padding: 15px 25px;
	}
	
	.sub-header {
  		padding-bottom: 10px;
  		border-bottom: 2px solid #eee;
	}
	
	.popover{
    	width: 450px;
        max-width: 450px;
	}
</style>

<script type="text/javascript">
	existeErrorFecha = false;
	existeErrorSalario = false;

	$(function(){
		var mensajeCampoRequerido = 'Este campo es obligatorio.';
		$('#baja').click(function(){
            $('#hoy').attr('checked',false);
            existeErrorFecha = false;
            validarErroresCampos()
        });

        $('#hoy').click(function(){
            $('#baja').attr('checked',false);
            existeErrorFecha = false;
            validarErroresCampos()
        });
        
        $("#salario").on("keyup", function(){
        	var longitud = $(this).val().length;
        	
        	if(longitud > 0) {
        		existeErrorSalario = false;
        	} else {
        		existeErrorSalario = true;
        		$('#msgErrorSalario').html(mensajeCampoRequerido);
        	}
        	
        	validarErroresCampos();
        })
		
		$('#siguientePaso').click(function(){
			$('#validacionSalarioMin').hide();
			$('#validacionSalarioMax').hide();
			$('#validacionSalarioUltReg').hide();
			$('#msgErrorFechaBaja').text('');
			$('#msgErrorFechaBaja').hide();
			$('#msgErrorSalario').text('');
			$('#msgErrorSalario').hide();
			
			var radiofechaAlta = $('#hoy').filter(":checked").val();
			var radiofechaBaja = $('#baja').filter(":checked").val();
			var salario = $('#salario').val();
			
			
			if(salario === ''){
				$('#msgErrorSalario').html(mensajeCampoRequerido);
				existeErrorSalario = true;
			}else if(!isNumeric(salario)){
				$('#msgErrorSalario').html('El formato del dato no es v&aacute;lido.');
				existeErrorSalario = true;
			} else {
				existeErrorSalario = false;
			}
			
			var formatSalario = parseFloat(salario);
			
			  if(radiofechaAlta == undefined && radiofechaBaja == undefined){
				  $('#msgErrorFechaBaja').text(mensajeCampoRequerido);
				  existeErrorFecha = true;
			  }else if(radiofechaAlta && formatSalario < '${sdiMin}'){
				  //$('#validacionSalarioMin').show();
				  $('#msgErrorSalario').html($('#validacionSalarioMin').html());
				  existeErrorSalario = true;
				  existeErrorFecha = false;
			  }else if(radiofechaBaja && formatSalario < '${sdiUltRegNum}'){
				  //$('#validacionSalarioUltReg').show();
				  $('#msgErrorSalario').html($('#validacionSalarioUltReg').html());
				  existeErrorSalario = true;
				  existeErrorFecha = false;
			  }else if(formatSalario > '${sdiMax}'){
				  //$('#validacionSalarioMax').show();
				  $('#msgErrorSalario').html($('#validacionSalarioMax').html());
				  existeErrorSalario = true;
				  existeErrorFecha = false;
			  } else {
				  existeErrorFecha = false;
			  }
			  
			  validarErroresCampos();
			  
			  if(existeErrorFecha || existeErrorSalario) {
				  setSizeWithinIframe(document);
				  return false;
			  } else {
				  $( "#confirmarDatosForm" ).submit();
			  }
		});
		
		function isNumeric(n) {
			  return !isNaN(parseFloat(n)) && isFinite(n);
	    }
        
		function validarErroresCampos() {
			if(existeErrorSalario) {
				$("#labelSalario").addClass("errorDocs");
				$("#salario").addClass("errorDocs");
				$('#msgErrorSalario').show();
			} else {
				$("#labelSalario").removeClass("errorDocs");
				$("#salario").removeClass("errorDocs");
				$('#msgErrorSalario').hide();
			}
			
			if(existeErrorFecha) {
				$("#labelFecha").addClass("errorDocs");
				$('#msgErrorFechaBaja').show();
			} else {
				$("#labelFecha").removeClass("errorDocs");
				$('#msgErrorFechaBaja').hide();
			}
			
			if(existeErrorFecha || existeErrorSalario) {
				$("#divErrorCampos").show()
			}  else {
				$("#divErrorCampos").hide()
			}
			
			setSizeWithinIframe(document);
			
		}
		
	});
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<c:set var="defaultLocale" value="${pageContext.request.locale}" />
			<fmt:setLocale value="es_MX" scope="session" />
			<jsp:include page="encabezadoMod40.jsp">
				<jsp:param name="paso" value="2" />
			</jsp:include>
            		        
			<div class="titulo">
                <span>Datos de inscripci&oacute;n</span>
                <hr class="red m-b-md">
            </div>
	        <div class="alert alert-danger" style="display: none" id="divErrorCampos">
				<strong>&iexcl;Error en el formulario!</strong> no has llenado todos los campos requeridos. Por favor verifica.
	        </div>
			<div class="alert alert-danger" style="display: none;" id="validacionSalarioMin">
				<span id="mensaje-validacion">El salario para tu inscripci&oacute;n en la continuaci&oacute;n voluntaria debe ser al menos de <fmt:formatNumber value="${sdiMin}" type="currency"/></span>
			</div>
			<div class="alert alert-danger" style="display: none;" id="validacionSalarioUltReg">
				<span id="mensaje-validacion">El salario para tu inscripci&oacute;n en la continuaci&oacute;n voluntaria debe ser al menos de <fmt:formatNumber value="${sdiUltRegNum}" type="currency"/></span>	
			</div>
			<div class="alert alert-danger" style="display: none;" id="validacionSalarioMax">
				<span id="mensaje-validacion">El salario para tu inscripci&oacute;n en la continuaci&oacute;n voluntaria debe ser m&aacute;ximo <fmt:formatNumber value="${sdiMax}" type="currency"/></span>
			</div>
			<div class="alert alert-info">
				Puedes elegir entre el &uacute;ltimo salario que ten&iacute;as registrado al momento de tu baja como asegurado,
				o un salario mayor que no rebase el l&iacute;mite de 25 veces la UMA en la Ciudad de M&eacute;xico
				vigente al momento de tu inscripci&oacute;n.
			</div>
            <form class="form-horizontal" role="form" id="confirmarDatosForm" action="${contextPath}/wizard/continuacionVoluntaria/comunes/confirmarDatos" method="POST">
                <div class="form-group">
                    <label class="col-sm-6 control-label" for="salario">
                    	Salario base con el que deseas cotizar<span id="labelSalario" class="required">*</span>:
                    	<a data-toggle="popover" id="ayudaSalario" class="btn btn-xs icono-help" data-original-title="" title=""> </a>
                    </label>
                    <div class="col-sm-6">
                    	<input class="numericoPositivo2Decimales form-control ns_" type="text" maxlength="9" value="${sdiUltReg}" name="salario" id="salario">
                    	<span class="error" style="display:none" id="msgErrorSalario"></span>
                	</div>
                </div>
                
                <div class="form-group">
                	<label class="col-sm-6 control-label">
                		La inscripci&oacute;n inicial<span class="required" id="labelFecha">*</span>:
                	</label>
                    <div class="col-sm-6">
						<input type="radio" id="hoy" name="hoy" value="hoy" checked class="ns_"> A la fecha de solicitud.<br>
						<span class="error" style="display:none" id="msgErrorFechaBaja"></span>
                    </div>
                </div>
            </form>
		<fmt:setLocale value="${defaultLocale}" scope="session" />
		</div>
	</div>
	<br>
	
	<div class="">
	</div>
			
	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span>Campos obligatorios</div>
		</div>
		<div class="controles col-sm-8">
			<div class="pull-right">
				<button id="cancelarTramite" class="btn btn-default" onclick="uid_call('imss.gestion.seguro.voluntario.mod40.datosInscripcion.btn_cancelar','clickout');">
					Cancelar
				</button>
				<a id="siguientePaso" class="btn btn-primary" onclick="uid_call('imss.gestion.seguro.voluntario.mod40.datosInscripcion.btn_continuar','clickin');">
					Continuar</a>
			</div>
		</div>
	</div>
	<div id="dialogoMsgSeleccion"></div>
</div><script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
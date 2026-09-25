<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/resources/js/jquery/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/cambioClinica/funcionesComunes.js" htmlEscape="true" />"></script>

<style>
	input{ text-transform: uppercase; 	}
</style>

<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> Cambio de cl&iacute;nica </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					actualizar su domicilio y/o cl�nica
				</p>
			</div>

			<div class="opciones">
				<c:if test="${empty error}">
					<button class="btn btn-primary btn-block" id="btnInciaTramiteClinica">
									<span class="ui-button-text"><spring:message
								code="label.boton.solicitud.iniciar" /></span>
					</button>
				</c:if>
				<button
					class="btn btn-default btn-block" id="btnInicioCancelarTramiteClinica">
					<span class="ui-button-text"><spring:message
						code="label.boton.solicitud.cancelar" /></span>
				</button>

			</div>
		</div>

		<div class="instrucciones col-sm-8" >
			<h3>Instrucciones:</h3>
			<c:if test="${empty error}">
				
				<div class="m-b-md">
					A continuaci&oacute;n proporcione la informaci&oacute;n requerida
					para el tr&aacute;mite, una vez que complete los campos de clic
					en el bot&oacute;n <strong>"Iniciar Solicitud"</strong>
				</div>

				<c:choose>
				    <c:when test="${opciones.ind_cambio_clinica_beneficiarios}">
				        <div class="alert alert-info">Por favor introduzca la curp del beneficiario</div>
				    </c:when>
				    <c:otherwise>
				        <div class="alert alert-info">Por favor introduzca la curp del asegurado</div>
				    </c:otherwise>
				</c:choose>
				
				<div class="well" style="background-color: white;">
					<form id="clinicaBeneficiariosForm" onsubmit="return false;" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-3 control-label">
								<span class="required" id="indCurpObligatoria">*</span>
								CURP:
							</label>
							<div class="col-sm-8">
								<input type="text" name="curpBeneficiario"
									class="form-control" maxlength="18" value="" />
							</div>
						</div>
					</form>
				</div>

			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">�</button>
					<strong>Importante: </strong>${error}
				</div>
			</c:if>
		</div>
	</div>

</div>


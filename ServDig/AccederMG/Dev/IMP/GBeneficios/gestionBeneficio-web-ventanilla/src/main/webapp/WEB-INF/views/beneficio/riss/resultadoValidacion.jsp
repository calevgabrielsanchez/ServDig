<%@ include file="../../general/taglibs.jsp"%>

<style>
.empty-state .titulo {
	font-size: 15px !important;
	margin-bottom: 20px;
}

.imagen {
	margin-bottom: 20px;
	text-align: center;
}

div.imagen i {
	display: inline-block;
	width: 100%;
}

#tblDescuentosRissResumen {
	width: 70%;
	margin: 20px auto 45px !important;
}

#tblDescuentosRissResumen td {
	text-align: center;
}
</style>

<script
	src="<spring:url value="/static/resources/js/delta/riss/resultadoValidacion.js" htmlEscape="true" />"></script>

<div class="contenedor">
	<div>
	<c:choose>
		<c:when test="${not empty msgError or not empty  msgMotivoRechazo}">
			<div id="info-paso">
				<h3 style="font-size: 1.8em !important">Paso 3: Beneficio rechazado. </h3>			
			</div>	
			
			<div class="container-fluid empty-state">
				<div class="row">
					<!-- Imagen -->
					<div class="col-xs-12 imagen">
						<i class="glyphicon glyphicon-remove-sign"></i>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 titulo">LO SENTIMOS UD. NO APLICA AL
						BENEFICIO POR EL SIGUIENTE MOTIVO:</div>
				</div>
				<div class="row">
					<div class="col-xs-10 col-xs-offset-1">
						<div class="alert alert-danger">
							<c:choose>
								<c:when test="${not empty  msgMotivoRechazo}">
									<strong>${msgMotivoRechazo}</strong>
								</c:when>
								<c:otherwise>
									${msgError}
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>

			<div style="float: right; margin-top: 10px;">
				<form action="${contextpath}/alta/riss/iniciar"
					method="get">
					<button type="submit" class="btn btn-primary">ACEPTAR</button>
				</form>
			</div>
		</c:when>
		<c:otherwise>
			<div id="info-paso">
				<h3 style="font-size: 1.8em !important">Paso 3: Detalle del beneficio otorgado.</h3>			
			</div>	
			
			<div class="alert alert-success alert-block">
				La solicitud ha sido iniciada correctamente y se le ha asignado a
				dicha solicitud el folio: <strong>${SOLIC_RISS.noFolioSolicitud}</strong>
			</div>

			<div class="container-fluid empty-state">
				<div class="row">
					<!-- Imagen -->
					<div class="col-xs-12 imagen">
						<i class="glyphicon glyphicon-ok-sign"></i>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 titulo">
						<strong>UD. ES CANDIDATO A RECIBIR LOS SIGUIENTES
							DESCUENTOS DEL BENEFICIO:</strong>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-1"></div>
							<div class="col-xs-4">
								<strong>Tipo de Beneficio: </strong>
							</div>
							<div class="col-xs-7">${BENEF_RISS.tipoBeneficio.descripcion}</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-1"></div>
							<div class="col-xs-4">
								<strong>RFC</strong>
							</div>
							<div class="col-xs-7">${BENEF_RISS.fisica.rfc}</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
							<div class="col-xs-1"></div>
							<div class="col-xs-4">
								<strong>Podr&aacute; recibir los descuentos como:</strong>
							</div>
							<div class="col-xs-7">
								<ul style="list-style: disc inside none;">
									<c:if test="${not empty BENEF_RISS.fisica.idPersona}">
										<li>Trabajador independiente</li>
									</c:if>
									<c:if test="${not empty BENEF_RISS.listaSujetosObligados}">
										<li>Patr&oacute;n</li>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
								
				<!--  <div id="tblDescuentosRissResumen"
					style="width: 100%; margin: 0 auto;">
					<table id="tblDescuentosRissResumen" style="width: 100%;"
							class="table table-striped table-bordered" cellpadding="0"
							cellspacing="0" border="0">
						<thead>
							<tr>
								<th>A&ntilde;o fiscal</th>
								<th>Descuento</th>
								<th>Fecha Inicio - Fecha Fin</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${BENEF_RISS.listaDescuentosBeneficio}"
								var="descuentoB" varStatus="indice">
								<tr>
									<td align="center">${descuentoB.anioFiscal}</td>
									<td align="center">${descuentoB.porcentajeDescuento} %</td>
									<td align="center"><fmt:formatDate
											value="${descuentoB.fecInicio}" pattern="dd/MM/yyyy" /> - <fmt:formatDate
											value="${descuentoB.fechaFin}" pattern="dd/MM/yyyy" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div> -->
			</div>
			
			<div class="text-right m-t-lg">
				<button type="button" id="btnCancelarSolic" class="btn btn-danger">CANCELAR
					SOLICITUD</button>
				<button type="button" id="btnProcesarSolic" class="btn btn-primary">PROCESAR
					SOLICITUD</button>
			</div>
			
			<form action="${contextpath}/alta/riss/concluir"
				method="post" id="concluirSolicForm">
				<input type="hidden" name="idSolicitud" id="idSolic"
					value="${SOLIC_RISS.solicitudId }" />
			</form>
		</c:otherwise>
	</c:choose>
	</div>
</div>

<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
		solicitud pendiente con folio: <strong>${SOLIC_RISS.noFolioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeDialogo"></label>
	</p>
</div>
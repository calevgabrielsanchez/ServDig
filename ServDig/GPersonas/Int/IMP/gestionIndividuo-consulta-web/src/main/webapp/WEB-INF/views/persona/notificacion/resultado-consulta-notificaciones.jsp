<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/notificacion/resultado-consulta-notificaciones.js" htmlEscape="true" />"></script>

<style>
	
	.site_position_center {
	    width: auto;
	}
	
	.main_wrap {
	    width: auto;
	}

	body {
		font-size: x-small; 
		line-height: normal;
	}
	
	.container {
    	width: auto;
   	}
		
	.page_holder table tr th,.page_holder_no_height table tr th {
		background-color: #E8E8E8;
		border-color: #E8E8E8;
		font-size: x-small;
	}
	
	.page_holder button {
		border: none;
		color: white;
		margin: auto;
		padding: -5px;
		padding: 4px 14px;
		font-size: small;
	}
	
	.alignCenter {
		text-align: center;
	}
	
	div.innerDetails {
		display: none;
	}
	
	td.details {
		background-color: transparent;
	}
	
	.contenedor {
	    display: table;
	    height: auto;
	    width: 100%;
	}
	
	.contenedor .row {
	    border-bottom: thin solid #EEEEEE;
	    width: 100%;
	}
	
	.contenedor .row:last-child {
	    border-bottom: none;
	}
	
	.contenedor .cell {
		padding: 5px !important;
		display: table-cell;
	}
	
	.tblDetalleCambios {
		width: 100% !important;
		border: hidden !important;
		margin: 0px !important;
		padding: 0px !important;
	}
	
	.btn-secondary {
		background-color: #363636 !important;
	}
	
	.btn-success {
		background-color: #5bb75b !important;
	}
</style>

<div class="page_holder" style="margin: 0px; width: auto;">
	<div class="container">
		<div class="hero-unit">
			<div class="form-comment">
	
				<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
				La persona <strong>${nombrePersona}</strong>, ha sufrido cambios en sus datos, por
					lo que se debe verificar si requiere de alguna acci&oacute;n, los
					cambios fueron por:
				<br>
				<table id="tblNotificaciones" style="width: 100%">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>TRAMITE</th>
							<th>FECHA</th>
							<th>EXPIRAR</th>
							<th>DETALLES DEL CAMBIO</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="notificacion" items="${notificaciones}" varStatus="indice">
							<tr>
								<td style="text-align: center">
									<img src="<spring:url value="/static/resources/estilos/images/details_open.png" htmlEscape="true" />" />
								</td>
								<td>${notificacion.tramite.tipoTramite.descripcion }</td>
								<td>${notificacion.tramite.fechaConclusionParse }</td>
								<td><button type="button" class="btn btn-success" style="font-size: 11px; padding: 2px 10px" data-toggle="button" value="${notificacion.idNotificacion}" onclick="changeTextButton(this)">Expirar</button></td>
								<td>${notificacion.detalleCambio }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br><br><br>
				<div style="float: right;">
					<button type="button" class="btn btn-secondary" id="btnAceptar">Aceptar</button>
					<button type="button" class="btn btn-secondary" id="btnExpirar">Expirar</button>
				</div>
			</div>
		</div>
	</div>
</div>


<form id="refreshNotifForm" action="">
	<input type="hidden" id="idPersona" value="${idPersona}"/>
	<input type="hidden" id="idModulo" value="${idModulo}"/>
	<input type="hidden" id="isMoral" value="${isMoral}"/>
</form>

<div id="expirarNotifConfirmDiv"
	title="Confirmar expiraci&oacute;n de notificaciones">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>¿Desea expirar las
		notificaciones seleccionadas?
	</p>
</div>
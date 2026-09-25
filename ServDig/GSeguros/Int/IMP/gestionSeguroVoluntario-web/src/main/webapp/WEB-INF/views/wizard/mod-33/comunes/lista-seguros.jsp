
<!-- JSP Contenido del portlet Example -->
<%@ include file="../../../general/taglibs.jsp"%>

<style>
.total {
	font-size: 25px;
}

table.table {
	font-size: initial !important;
}
</style>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-33/comunes/lista-seguros.js" htmlEscape="true" />"></script>

<script type="text/javascript">
	$(document).ready(function() {
		enviaComprobanteRenvacionSSF();
	});
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<p>Listado de los integrantes de tu Seguro de Salud para la
				Familia</p>
			<table id="tablaSeguros"
				class="table table-striped table-bordered table-word-wrap-fixed"
				cellpadding="0" cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Nombre del asegurado</th>
						<th>Estado</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${segurosFamiliares.seguroIvro}" var="seguro">
						<tr>
							<td>${seguro.tramite.beneficiarios[0].nombreCompleto}</td>
							<td class="text-center"><c:choose>
									<c:when test="${seguro.estadoSeguro.idEstadoSeguro == 1}">
										<span
											class="label label-warning label-imss label-warning-imss">
									</c:when>
									<c:when test="${seguro.estadoSeguro.idEstadoSeguro == 3}">
										<span class="label label-danger label-imss label-danger-imss">
									</c:when>
									<c:when test="${seguro.estadoSeguro.idEstadoSeguro == 4}">
										<span class="label label-danger label-imss label-danger-imss">
									</c:when>
									<c:otherwise>
										<span
											class="label label-success label-imss label-success-imss">
									</c:otherwise>
								</c:choose> ${seguro.estadoSeguro.descripcion} </span></td>
							<td>
								<div class="btn-group custom-btn-group">
									<button class="btn btn-default btn-xs">Acciones</button>
									<a class="btn btn-default btn-xs dropdown-toggle"
										data-toggle="dropdown" href="#"> <span class="caret"></span>
									</a>
									<ul class="dropdown-menu pull-right">
										<li idSeguro="${seguro.cveIdSeguroIvroCifrado}" class="link-detalle">
											<a href="#">Ver detalle</a>
										</li>
									</ul>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6 text-right">                        
                        <c:choose>
                                    <c:when test="${enRenovacion and not extemporanea}">
                            <button class="btn btn-primary" id="iniciarTramite">Renovar</button>
                                    </c:when>
                                    <c:when test="${extemporanea}">
                            <button class="btn btn-primary" id="btnExtemporaneoSSF">Renovar</button>
                                    </c:when>
                        </c:choose>
            <button id="cerrar" class="btn btn-default">Cerrar</button>
        </div>
        <div id="dialogoMensajes">
            <p><span id="textoMensaje"></span></p>
        </div>
	</div>
</div>

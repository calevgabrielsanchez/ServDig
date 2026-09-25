<!-- JSP Contenido del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>
<style>
	.subtitulo {
		color: #157164;
		font-size: small;
		font-weight: bold;
		line-height: 1em;
		margin-bottom: 0.5em;
		margin-top: 0;
	}
	
	.linkFolio {
		cursor: pointer;
		color: #0088CC;
	}
	
	.desc-campo {
		font-weight: bold !important;
	}
</style>

<c:choose>
	<c:when test="${empty error}">
		<c:if test="${not empty grupos }">
			<div id="datosGruposFamiliaresWrapper" style="width: 100%; margin: 0 auto;">
				<table id="tblGruposFamiliaresResumen" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th>Nss</th>
							<th>Parentesco</th>
							<th>Estado</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${grupos}" var="grupo">
							<tr>
								<td align="center">${grupo.asignacionNSS.nss}</td>
								<td align="center">${grupo.parentesco.descripcion}</td>
								<td align="center">${grupo.estadoDerechohabiente.descripcion}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<c:if test="${modalidad17}">
					<div class="row" style="">
						<div class="col-sm-12" style="">    
					    	<span style="float: right; font-size: 16px;"><strong>Sin derecho al servicio m&eacute;dico</strong></span>
					    </div>
					</div>
				</c:if>
			</div>
			<br><br>
		</c:if>
		<c:if test="${empty grupos }">
			<div class="row-fluid empty-state">
			<div class="row-fluid">
				<!-- Imagen -->
				<div class="span12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row-fluid">
				<!-- Titulo -->
				<div class="span12 titulo">
					<spring:message
				code="label.portlet.sin.resultados.otros.grupoFamiliar" />
				</div>
			</div>
			
			</br>
			
			<!-- Opciones del empty state, si en el properties de opciones estan activas, aquí es donde se pondrán -->
			<div id="opcNavEmptyStateGrupoFamiliar">
				
			</div>

		</div>
		</c:if>
	</c:when>
	<c:otherwise>
		
	</c:otherwise>
</c:choose>

<script id="initPortlet">
	$('#tblGruposFamiliaresResumen').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [{"sSortDataType": "html", "sType": "html", "aTargets": [0]}]
	});
</script>
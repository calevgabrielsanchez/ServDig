<!-- JSP Contenido del Widget de los Medios Fiscales. -->
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<c:choose>
	<c:when test="${not empty representantes }">
		<div id="tblRepresentanteWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblRepresentantesResumen" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>RFC</th>
						<th>CURP</th>
						<th>Nombre</th>
						<th>Actos de dominio</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${representantes}" var="representante"
						varStatus="indice">
						<tr>
							<td>${representante.personaFisica.rfc }</td>
							<td>${representante.personaFisica.curp }</td>
							<td>${representante.personaFisica.nombre }
								${representante.personaFisica.primerApellido }
								${representante.personaFisica.segundoApellido }</td>
							<td class="texto-centrado">
								${representante.indActAdmonDominio }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
	
		<div class="container-fluid empty-state">
			<div class="row">
				<!-- Imagen -->
				<div class="col-xs-12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row">
				<!-- Titulo -->
				<div class="col-xs-12 titulo">
					<spring:message code="label.portlet.sin.resultados.representantes.legales" />
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>


<script id="initPortlet">

	$('#tblRepresentantesResumen').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [
			{ "fnRender": function ( oObj ) {				
				return parseIndicador(oObj.aData[3]);
			}, "aTargets": [ 3 ] },
			{ "bSortable": false, "aTargets": [ 3 ] }
			
		]
	});

</script>
	







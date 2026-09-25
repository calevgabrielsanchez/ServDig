<!-- JSP Contenido del Portlet de Representados Legales. -->
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona"%>

<c:set var="tipoPersonaMoral"
	value="<%=TipoPersona.TIPO_PERSONA_MORAL%>" scope="page"></c:set>


<c:choose>
	<c:when test="${not empty representados }">
		<div id="representadosWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblRepresentadosResumen" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>RFC</th>
						<th>Nombre / Raz&oacute;n Social</th>
						<th>Tipo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${representados}" var="representado"
						varStatus="indice">
						<tr>
							<td><c:choose>
									<c:when
										test="${representado.tipoPersona.idTipoPersona == tipoPersonaMoral}">
										<a href="#" class="representadoMoral link"
											id="${representado.idPersona}">${representado.rfc }</a>
									</c:when>
									<c:otherwise>
										<a href="#" class="representadoFisica link"
											id="${representado.idPersona}"> ${representado.rfc } </a>
									</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when
										test="${representado.tipoPersona.idTipoPersona == tipoPersonaMoral}">
											<input type="hidden" id="curp${representado.idPersona}"
												value=""
												>
												<span id="nombre${representado.idPersona}">
												${representado.razonSocial}</span>
											</c:when>
									<c:otherwise>
												<input type="hidden" id="curp${representado.idPersona}"
												value="${representado.curp}"
												>
												<span id="nombre${representado.idPersona}">
												${representado.nombre } 
												${representado.primerApellido } 
												${representado.segundoApellido }
												</span>
											</c:otherwise>
								</c:choose></td>
							<td><c:choose>
									<c:when
										test="${representado.tipoPersona.idTipoPersona == tipoPersonaMoral}">
										<input type="hidden" id="idPersonaFM${representado.idPersona}"
											value="${representado.cveMoral}">
												Moral
											</c:when>
									<c:otherwise>
										<input type="hidden" id="idPersonaFM${representado.idPersona}"
											value="${representado.cveFisica}">
												Fisica
											</c:otherwise>
								</c:choose></td>
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
					<spring:message
				code="label.portlet.sin.resultados.representados" />
				</div>
			</div>
					
			<!-- Opciones del empty state, si en el properties de opciones estan activas, aquí es donde se pondrán -->
			<div id="opcNavEmptyStateRepresentados">
				
			</div>

		</div>
		
	</c:otherwise>
</c:choose>
<form id="formRepresentado" action="#" method="post">
	<input id="hdnIdRepresentado" type="hidden" value="" name="idPersona">
	<input id="hdnRfc" type="hidden" value="" name="rfc">
	<input id="hdnNombre" type="hidden" value="">
	<c:choose>
		<c:when
			test="${representado.tipoPersona.idTipoPersona == tipoPersonaMoral}">
			<input id="hdnIdRepresentadoFM" type="hidden" value="" name="cveMoral">
		</c:when>
		<c:otherwise>
			<input id="hdnIdRepresentadoFM" type="hidden" value=""
				name="cveFisica">
			<input id="hdnCurp" type="hidden" value="" name = "curp">
		</c:otherwise>
	</c:choose>
</form>

<script id="initPortlet">
	
	$.post("/portal-web/utility/menu/opciones/7/2",null,function(data) {
		$("#opcNavEmptyStateRepresentados").html(data);
	});

	$('#tblRepresentadosResumen').dataTable({
		"bDestroy": true,
		"bLengthChange": false,
		"sPaginationType": "bootstrap",
		"aoColumnDefs": [{"sSortDataType": "html", "sType": "html", "aTargets": [0]}]
	});
	
	$('.representadoMoral').live( 'click' , function(){
		$.blockUI();
		var idPersona = $(this).attr('id');
		var cvePersonaMoral  = $("#idPersonaFM"+idPersona).val(); 
		var rfc = $("#"+idPersona).text();
		var nombre = $.trim($("#nombre"+idPersona).text()).split('"').join('');
		var curp = $("#curp"+idPersona).val();
		
		$('#hdnIdRepresentado').val(idPersona);
		$('#hdnIdRepresentadoFM').val(cvePersonaMoral);
		$('#hdnNombre').val(nombre);
		$('#hdnNombre').attr('name','razonSocial');
		$('#hdnRfc').val(rfc);
		
		document.getElementById('formRepresentado').action =  context_path + '/portal/persona/moral/ingresar/';
		document.getElementById('formRepresentado').submit();
	});

	$('.representadoFisica').live( 'click' , function(){
		$.blockUI();
		var idPersona = $(this).attr('id');
		var cveFisica  = $("#idPersonaFM"+idPersona).val(); 
		var rfc = $("#"+idPersona).text();
		var nombre = $.trim($("#nombre"+idPersona).text()).split('"').join('');
		var curp = $("#curp"+idPersona).val();
		
		$('#hdnIdRepresentado').val(idPersona);
		$('#hdnIdRepresentadoFM').val(cveFisica);
		$('#hdnCurp').val(curp);
		$('#hdnNombre').val(nombre);
		$('#hdnNombre').attr('name','nombre');
		$('#hdnRfc').val(rfc);
		
		document.getElementById('formRepresentado').action =  context_path + '/portal/persona/fisica/ingresar/';
		document.getElementById('formRepresentado').submit();
	});
</script>
<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript">
	$(document).ready(function(){
		
		$('#regresarBtn').click(function(){
			var url = '<%=request.getContextPath()%>';			
			if('${origen}' == 'bs'){
				url += "/solicitud/validar/";
			}else{
				url += "/persona/" + '${tipoPersona}' + "/registro-masivo";
			}
			
			$('#resultadoBusquedaSolicitudForm').attr("action", url);
			$('#resultadoBusquedaSolicitudForm').submit();
			
		});
		
		$('#procesarBtn').click(function() {
			aceptado = 0;
			rechazado = 2;
			lstSize = "<c:out value='${fn:length(solicitud.tramite)}' />";
			for(var i=0; i < lstSize; i++) { 
				radioName = "tramite["+ i + "].tramitadorValidaDatos";
				indexRadio = $('input[name="'+ radioName + '"]:checked').index();
				if(indexRadio == rechazado) {
					razonRechazoId = $("#tramite" + i + "idRazonResultado").val();
					if(razonRechazoId == -1) {
						alert("Verifique que haya seleccionado una raz\u00f3n de rechazo para todos los tr\u00e1mites rechazados");
						return false;
					}
				}
			}
			$('#solicitudForm').submit();
		});

	});
</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
		<div style="color: #157164; font-size: 1em; font-weight: bold; width: 35%; float: left; border: 1px">
			Identificador: <c:out value="${solicitud.idSolicitud}"/><br/>
			Estado: <c:out value="${solicitud.desEstadoSolicitud}"/><br/>
			Fecha Registro: <c:out value="${solicitud.fechaRegistroFormateada}"/><br/>
		</div>
	
		<h2 style="font-size:1em !important;"><c:out value="${msgSolicitudSinTramitesPorValidar}"></c:out></h2>
	
		<c:if test="${hayTramitesPendientes}">
			<c:choose>
				<c:when test="${tipoPersona == 'moral'}">
					<jsp:include page="resultadoBusquedaSolicitudMoral.jsp"></jsp:include>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${tipoPersona == 'fisica'}">
							<jsp:include page="resultadoBusquedaSolicitudFisica.jsp"></jsp:include>
						</c:when>
						<c:otherwise>
							Tipo de Persona no V&aacute;lido: '<c:out value="${tipoPersona}"></c:out>'
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:if>
		
		<!-- Formulario generico para simular una funcion window.history.back(), ya que si tratamos de usarla hace mmds -->
		<form id="resultadoBusquedaSolicitudForm"></form>
		
	</div>
</div>
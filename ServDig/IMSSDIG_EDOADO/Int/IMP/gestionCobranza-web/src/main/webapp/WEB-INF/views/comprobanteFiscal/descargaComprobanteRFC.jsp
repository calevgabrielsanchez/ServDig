<!-- JSP Contenido del Widget de Comprobante Fiscal. -->
<%@ include file="../general/taglibs.jsp"%>

<div class="contenedor">
	<div class="contenido" style="width: 100%;">
		<div class="well" style="background-color: white;" >
			<jsp:include page="../common/descargaComprobantesZip.jsp"></jsp:include>
		</div>
	</div>
	<br>
	<div class="pie">
		<div class="opciones"></div>
		<div class="controles">
			
		</div>
	</div>
</div>
<!-- JSP Contenido del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>
<c:choose>
	<c:when test="${not empty derechohabiente}">
		<jsp:include page="../../common/detalleDerechohabiente.jsp"></jsp:include>
	</c:when>
	<c:otherwise>
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
					${errores}
				</div>
			</div>
			
			</br>
			
			<!-- Opciones del empty state, si en el properties de opciones estan activas, aquí es donde se pondrán -->
			<div id="opcNavEmptyStateDetalleDerechohabiente">
				
			</div>

		</div>
	</c:otherwise>
</c:choose>
<%@ include file="../../general/taglibs.jsp"%>

<c:set var="FROM_ADMON_DOMICILIO" value="true" scope="request" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="request" />

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/administrar/datosComplementarios.js" htmlEscape="true" />"></script>

<div class="page_holder">
	<div class="contenedor">
		<div>
			<div style="display: inline; width: 100%">
				<div class="row" style="width: 100%">

					<div id="titulo" class="row" style="width: 100%">
						<h2>Modificar Domicilio Geogr&aacute;fico</h2>
					</div>

					<jsp:include page="../common/datosComplementariosCommon.jsp"></jsp:include>

				</div>
			</div>
		</div>
	</div>
</div>
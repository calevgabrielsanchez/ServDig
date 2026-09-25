<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>"
	scope="request" />

<div>
	<div id="titulo" class="row">
		<div id="titulo" class="col-xs-12" style="width: 100%">
			<h2>Ubicar domicilio geogr&aacute;fico nacional</h2>
			<h3 style="font-size: .9em; color: #666666">Paso 2 / 3</h3>
		</div>
	</div>
	<div class="alert alert-danger" style="display: none" id="divErrorCampos"></div>
	<jsp:include page="./common/datosComplementariosCommon.jsp"></jsp:include>

</div>

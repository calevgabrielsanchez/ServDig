<%@ include file="../general/taglibs.jsp"%>

<!-- GobMx -->
<link href="http://cdn.imss.gob.mx/assets/styles/main.css" rel="stylesheet">

<!-- Bootstrap -->
<link type="text/css" href="${staticResourcesPath}/estilos/bootstrap/DT_bootstrap.css" rel="stylesheet" />

<!-- Jquery-->
<link type="text/css" href="${staticResourcesPath}/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />

<!-- Fonts -->
<link type="text/css" href="${staticResourcesPath}/estilos/font-awesome/css/font-awesome.css" rel="stylesheet" />

<!-- IMSS -->
<link type="text/css" href="${staticResourcesPath}/estilos/imss/portal.css" rel="stylesheet" />
<!--[if IE]> <link href="${staticResourcesPath}/estilos/imss/ie.css" type="text/css" rel="stylesheet" /><![endif]-->

<!-- jQuery -->
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-post-json.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/dtable/jquery.dataTables.pagination.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/form2Object/form2object.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/jquery/form2Object/jquery.toObject.js"></script>
<!-- Bootstrap -->
<script type="text/javascript" src="${staticResourcesPath}/js/bootstrap/bootstrap.min.js"></script>
<!-- JSON -->
<script type="text/javascript" src="${staticResourcesPath}/js/json/json2.js"></script>
<script type="text/javascript" src="${staticResourcesPath}/js/json/json.min.js"></script>

<div class="container-fluid">
	<div class="alert alert-info">Para continuar con el tr&aacute;mite es necesario adjuntar la siguiente
		documentaci&oacute;n (Solo se podr&aacute;n adjuntar documentos en formato PDF y con un tama&ntilde;o m&aacute;ximo de
		1024 KB por archivo):</div>

	<c:choose>
		<c:when test="${not empty documentos}">

			<input type="hidden" value="${documentosRequeridos}" id="documentosRequeridos" />

			<table id="tablaDocumentos" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<c:forEach var="doctoReq" items="${documentos}">
					<thead>
						<tr>
							<th align="center">${doctoReq.titulo}</th>
						<tr>
					</thead>
					<tbody>
						<c:forEach var="docto" items="${doctoReq.documentos}">
							<tr>
								<td>${docto.desDocumento}</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:forEach>
			</table>
		</c:when>

		<c:when test="${not empty documentosMovPat}">


			<table id="tablaDocumentos" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<tbody>
					<c:forEach var="doctoReq" items="${documentosMovPat}">
						<tr>
							<td>${doctoReq.desDocumento}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		
		
		<c:otherwise>
			<span class="error-widget">Este tr&aacute;mite no requiere de documentos</span>
		</c:otherwise>

	</c:choose>
</div>

<!-- GobMx -->
<script src="${staticResourcesPath}/js/gobmx/gobmx-fonts.js"></script>
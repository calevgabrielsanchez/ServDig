<%@ include file="../general/taglibs.jsp"%>
<!-- Main Wizard page template -->

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';
	history.go(1);
	$(function() {
		$('form:not(.formNotBlock)').submit(function() {
			$.blockUI();
		});
	});
</script>

<!--<script type="text/javascript" id="User1st_Loader" src="https://fe.user1st.info/Loader/head"></script>-->

</head>

<body class="p-t-none">
	<main class="page m-t-none">
		<div class="container-fluid">	
			<div class="wizard row">
				<tiles:insertAttribute name="contenido" />
			</div>
		</div>
	</main>
	
	<!-- GobMx -->
	<script src="${staticResourcesPath}/js/gobmx/gobmx-fonts.js"></script>
    <jsp:include page="btn-accesibilidad.jsp"/>
</body>

</html>

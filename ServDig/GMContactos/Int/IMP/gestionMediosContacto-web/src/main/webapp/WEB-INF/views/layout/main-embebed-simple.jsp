<%@ include file="../general/taglibs.jsp"%>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%= request.getContextPath()%>';
	var server_name = '<%= request.getServerName()%>';
</script>

<div class="container">
	<div>
		<div id="cuerpo">
			<tiles:insertAttribute name="contenido" />
		</div>
	</div>
</div>


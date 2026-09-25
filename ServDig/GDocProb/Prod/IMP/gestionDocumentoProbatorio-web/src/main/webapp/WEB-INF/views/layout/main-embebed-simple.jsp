<%@ include file="../general/taglibs.jsp"%>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';
</script>


<div class="site_position_center">
	<div>
		<div id="cuerpo">
			<tiles:insertAttribute name="contenido" />
		</div>
	</div>
</div>


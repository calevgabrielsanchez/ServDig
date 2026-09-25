<%@ include file="../../../general/taglibs.jsp" %>

<div class="row">
	<div class="col-sm-12">
		<jsp:include page="pasosEscritoDesacuerdo.jsp">
			<jsp:param value="2" name="paso"/>
		</jsp:include>
	</div>
</div>

<div class="row">
	<jsp:include page="../contenidoEscrito.jsp">
	</jsp:include>
</div>

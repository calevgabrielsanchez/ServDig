<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/controlGestion/correccion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/controlGestion/ctrlGestionFxComunes.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>



<body>
			<div id="anexoTrabajadoresA">
						<jsp:include page="dgTrabajadores.jsp" />
			</div>
			<div id="anexoTrabajadoresR">
						<jsp:include page="dgTrabajadoresR.jsp" />
			</div>
			<div id="foliosPromocion">
						<jsp:include page="foliosPromocion.jsp" />
			</div>
			
		<div id="conceptosOmitidos">
						<jsp:include page="dgConceptos.jsp" />
			</div>
			<div id="conceptosOmitidosC">
						<jsp:include page="dgConceptosC.jsp" />
			</div>
			<div id="conceptosOmitidosPAI">
						<jsp:include page="dgConceptosPAI.jsp" />
			</div>
			<div id="conceptosOmitidosAPAI">
						<jsp:include page="dgConceptosAPAI.jsp" />
			</div>
				<div id="anexoPatrones">
						<jsp:include page="dgPatron.jsp" />
			</div>
			<div id="anexoPagos">
						<jsp:include page="dgAnexoPagos.jsp" />
			</div>
			<div id="anexoPagosR">
						<jsp:include page="dgAnexoPagosR.jsp" />
			</div>
			<div id="filtros" >
				<jsp:include page="correccionCGInicial.jsp" />
			</div>
			<div id="buscarCorrecciones">
						<jsp:include page="correccionCGBuscar.jsp" />
			</div>			
</body>
</html>
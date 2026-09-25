<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">


<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/controlGestion/promocion.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/controlGestion/ctrlGestionFxComunes.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>



<body>

			<div id="foliosPromocion">
						<jsp:include page="promocionCGBuscar.jsp" />
			</div>			
			<div id="anexoPagos">
						<jsp:include page="promocionCGAnexoPagos.jsp" />
			</div>
			
			<div id="filtros">
				<jsp:include page="promocionCGInicial.jsp" />
			</div>
			<br>
			

		
	
	</div>
</body>
</html>
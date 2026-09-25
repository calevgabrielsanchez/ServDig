<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/clasificacion/detalle/detalleSolicitud.js" htmlEscape="true" />"></script>


<div class="page_holder">
<h3>Ha ocurrido un error inesperado.</h3>
  <h5 class="error">${errorInconsistencia}</h5>
  <button>Regresar</button>
  
</div>
<div class="marcointerno" style="text-align: center;">
					<input type="button" class="botonDDiv" name="btnRegresar"
						id="Regresar" value="Regresar" />
				</div>

<form id="regresaForm"
					action="<%=request.getContextPath()%>/analisis/viene/detalle"
					method="POST">
					
					
</form>
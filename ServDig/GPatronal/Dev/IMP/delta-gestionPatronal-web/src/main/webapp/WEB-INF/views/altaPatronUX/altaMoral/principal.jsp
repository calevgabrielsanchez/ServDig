<%@ include file="../../general/taglibs.jsp"%>	
<jsp:include page="encabezadoMoral.jsp"></jsp:include>

<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/principal.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/navegacion.js" htmlEscape="true" />"></script>

<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';

function getContext(){
	return contextPath;
}

</script>


<div class="row">
	<div class="col-sm-12">
		<div class="seccionTramite row" id="registrarRepresentante">
			<div class="col-sm-12">
				<jsp:include page="../altaRepLegal/registraRepLegal.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="firmaEmpresa">
			<div class="col-sm-12">
				<jsp:include page="../altaRepLegal/firmaEmpresa.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="confirmacionDatos">
			<div class="col-sm-12">
				<jsp:include page="revisarDatos.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="domicilioCentroTrabajo">
			<div class="col-sm-12">
				<jsp:include page="domicilioCentroTrabajo.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="giroEmpresa">
			<div class="col-sm-12">
				<jsp:include page="giroEmpresa.jsp"></jsp:include>
			</div>
		</div> 
		<div class="seccionTramite row" style="display:none" id="procesos">
			<div class="col-sm-12">
				<jsp:include page="procesos.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="materiales">
			<div class="col-sm-12">
				<jsp:include page="materiales.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="recursosHumanos">
			<div class="col-sm-12">
				<jsp:include page="recursosHumanos.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="personasAutorizadas">
			<div class="col-sm-12">
				<jsp:include page="personasAutorizadas.jsp"></jsp:include>
			</div>
		</div>
		<div class="seccionTramite row" style="display:none" id="escrituraSindicato">
			<div class="col-sm-12">
				<jsp:include page="escrituraSindicato.jsp"></jsp:include>
			</div>
		</div>		
		
		<div class="seccionTramite row" style="display:none" id="vistaPrevia">
			<div class="col-sm-12">
				<jsp:include page="vistaPrevia.jsp"></jsp:include>
			</div>
		</div>
		
		<div class="seccionTramite row" style="display:none" id="firmaTramiteAlta">
			<div class="col-sm-12">
				<jsp:include page="firmaAltaPatronal.jsp"></jsp:include>
			</div>
		</div>
		
		<div class="seccionTramite row" style="display:none" id="resumenRegistro">
			<div class="col-sm-12">
				<jsp:include page="resumenRegistro.jsp"></jsp:include>
			</div>
		</div>

	</div>
</div>

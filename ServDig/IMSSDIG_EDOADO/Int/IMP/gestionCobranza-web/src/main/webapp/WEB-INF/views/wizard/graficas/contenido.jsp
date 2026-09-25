<!-- JSP Contenido del Portlet de Clasificacion. -->
<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/graficas/contenido.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<input type="hidden" id="nrp" value="${nrp}"/>
			<div id="titulos">
				<div style="width:50%; box-sizing:border-box; float:left; text-align:center;">
					<span style="color: black; font-size:medium;">Cr&eacute;ditos IMSS</span>
				</div>
			
				<div style="width:50%; box-sizing:border-box; float:right;text-align:center;">
					<span style="color: black; font-size:medium;">Cr&eacute;ditos RCV</span>
				</div>
			</div>
			<div id="totales">
				<div style="width:50%; box-sizing:border-box; float:left;text-align:center; color: black;">
					<span id="totalesImss" style="color: black; font-size:medium;"></span>
				</div>
			
				<div style="width:50%; box-sizing:border-box; float:right;text-align:center; color: black;">
					<span id="totalesRCV" style="color: black; font-size:medium;"></span>
				</div>
			</div>
			<div id="graficas">
				<div id="chartimss"  style="height: 250px; width:50%; box-sizing:border-box; float:left;" align="left"></div>
				
				<div id="chartrcv" style="height: 250px; width:50%; box-sizing:border-box; float:right;" align="left"></div>
			</div>
		</div>
	</div>
	<br>
	<div class="pie row">
		<div class="opciones col-sm-6">
				<div class="btn-group" id="operacionesReportes">
					<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="reporteMot">CONSULTAR <spring:message code="reporte.tipo.cobro" /></a></li>
						<li><a id="reporteMotRCV">CONSULTAR <spring:message code="reporte.tipo.cobro.rcv" /></a></li>
					</ul>
				</div>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
			</div>
		</div>
	</div>
</div>
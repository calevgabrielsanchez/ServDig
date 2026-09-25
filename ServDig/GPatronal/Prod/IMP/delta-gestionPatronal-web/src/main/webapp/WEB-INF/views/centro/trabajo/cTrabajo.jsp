<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/centro/trabajo/centroTrabajo.js" htmlEscape="true" />"></script>

<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="divSujetoObligado" style="width: 1000px;" >
					<div class="cell form-comment" id="captura" style=" float:right;  width:500px !important; height: 700px !important; ">
						<div class="row">
							<h2><spring:message code="title.busqueda.personas"/></h2>
						</div>
						<div class="row">
							<h3>Centro de Trabajo</h3>
							<p>A continuaci&oacute;n se presentan los detalles del centro de trabajo</p>
						</div>
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
							<fieldset style="margin: 20px !important;">
								<legend>
									<strong>
										<spring:message code="titulo.centro.trabajo" />
									</strong>
								</legend>
								<c:if test="${sujetoObligado.cntroTrabajo.clave != null}">
									<script type="text/javascript">
	
									$.ajax({ 
								        url: '/${mvn.web.app.rootDomicilios}/domicilio/nacional/detalle/${sujetoObligado.cntroTrabajo.clave}', 
								        success: function(data) { 
								          $('div#centrotrabajo').html(data);           
								        }
								      });
																
									
									</script>
									
									<div id="centrotrabajo"> 
									</div>
									
									<div class="derecha">
									<input type="button" class="mboton" onclick="javascript:desplegarModificar();" id="bUbicar"  name="Ubicar"
									value="<spring:message code="label.ubicar.centro" />"/>
								</div>
								</c:if>
								<c:if test="${sujetoObligado.cntroTrabajo.clave == null}">
									<strong>
										<spring:message code="err.centro.trabajo.inexistente" />
									</strong>
								</c:if>
							</fieldset>	
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
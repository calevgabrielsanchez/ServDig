<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/clasificacion/avisoClasificacion.js" htmlEscape="true" />"></script>


<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="divSujetoObligado" style="width: 1000px;" >
					<br>
					<fieldset style="width: 900px">
							
							
							<h2><spring:message code="label.solicitud.finalizada"/></h2>
							
										
								<table style="width: 100% !important; border: none !important;">
									
									<tr>
										<td style="border: none !important;" colspan="4">
											<div id="fechasError" style="color: red;"></div>
											<table>
												<tr class="fielsetgris">
													<td  align="center">
														<spring:message code="texto.clasificacion.solicitud.finalizada" />
														<br />
														<br />
														<spring:message code="texto.clasificacion.solicitud.finalizada.complemento" />
													</td>
													
													
												</tr>
												
											</table>
										</td>
									</tr>
									
									
									<tr>
										<td colspan="4" align="right" style="border: none !important;">
											<form id="formaParaEstilo">
												<!--  button type="button" class="mboton" name="btnContinuar" id="btnContinuar">
													<spring:message code="label.concluir"/>
												</button>  -->
												<button type="button" onclick=" recuperaCargaAcuse()" class="mboton" name="btnAcuse">
													<spring:message code="label.generar.acuse"/>
												</button>
											</form>
										</td>
									</tr>
									
								</table>
							<br><br>
							
							
						
						</fieldset>
				</div>
			</div>
		</div>
	</div>
</div>



<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<form id="formaRegresoDetalle" name="formaRegresoDetalle" action="${contextpath}/clasificacion/mostrarAcuse" method="POST" target="_blank"></form>


<form:form modelAttribute="socio"  action="${contextpath}/login/iniciar" id="formaRegresoDetalleFin" name="formaRegresoDetalleFin" method="POST">
	<form:hidden path="rfc" />
</form:form>



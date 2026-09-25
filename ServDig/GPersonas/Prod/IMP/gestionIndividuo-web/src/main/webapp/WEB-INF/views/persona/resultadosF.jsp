<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<c:forEach items="${personasFisicas}" var="personaFisica" varStatus="index">
				<div class='elemento-resultado' style="display: table; width: 100%">
					
					<div style="display: table-cell; width: 90%;">	
						<span class="titulo1"> ${personaFisica.nombre}&nbsp;${personaFisica.primerApellido}&nbsp;${personaFisica.segundoApellido} </span>
						<p>
							<span class="detalle">${personaFisica.curp}&nbsp;(${personaFisica.fechaNacimientoFormateada})</span>
						</p>
						<input type="hidden" value="${personaFisica.idPersona}" id="idPersona"/>
					</div>
					<div style="display: table-cell; width: 10%; padding-bottom: 15px; padding-top: 15px;">
						<span class="ui-icon ui-icon-triangle-1-e"></span>
					</div>
				</div>
			</c:forEach>
			
			Tamaño de lista: <c:out value="${fn:length(personasFisicas)}" />
			
			<div>
				<!-- Seccion de controles para la paginacion -->
			</div>	

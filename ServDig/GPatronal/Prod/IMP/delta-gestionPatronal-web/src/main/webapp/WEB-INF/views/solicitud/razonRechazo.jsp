<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<div id="dialogoRechazo">
	<form:form modelAttribute="solicitud" id="solicitudRechazoForm" 
										name="solicitudRechazoForm" action="${contextpath}/solicitud/actualizarEstatus">
		<table style="border: none !important; width: 100%">
			<tr>
				<td>
					<spring:message code="label.solicitud.razon.rechazo"/>
				</td>
				<td>
					<combo:creaCombo idHtml="idRazonCancelacion" idHtmlContenedor="solicitudRechazoForm"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion" 
							mostrarSoloActivos = "true"/>
				</td>
			</tr>
		</table>
	</form:form>
</div>

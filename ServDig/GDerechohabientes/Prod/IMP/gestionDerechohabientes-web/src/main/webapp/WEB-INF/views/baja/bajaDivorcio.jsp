<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/procesosBaja.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/baja/bajaDerechohabienteDivorcio.js" htmlEscape="true" />"></script>
	
<h4 align="center">BAJA DE DERECHOHABIENTE POR DIVORCIO</h4>
<div class="form-comment">

<c:choose>
<c:when test="${empty errores}">
<jsp:include page="/WEB-INF/views/prorrogas/grupoFamiliar.jsp"></jsp:include>
<input type="hidden" value="${idPersona}" id="idPersona">
<br><br>
<form>
	<div align="center">
			<table>
			<tr>
				<td align="center">
					<input id="aceptar" type="button" value = "Aceptar" class="mboton" />
					<input id="cancelar" type="button" value="Regresar" class="mboton" />
				</td>
			</tr>
			</table>
	</div>
</form>
</c:when>
<c:otherwise>
	<jsp:include page="/WEB-INF/views/general/encabezadoGF.jsp"></jsp:include>
	<br>
	<%@ include file="/WEB-INF/views/error/paginaExcepcion.jsp" %>
</c:otherwise>
</c:choose>
</div>
<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<%-- <jsp:include page="../../common/llenaEstadoDerechohabiente.jsp"></jsp:include>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/widget/vigencia/vigenciaWidget.js" htmlEscape="true" />"></script>
<input type="hidden" value="mostrarOpcionesVigencia" value="${mostrarOpciones}"/>
<input type="hidden" value="idPersonaWidgetVigencia" value="${fisica.idPersona}"/> --%>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="widget-section" style="padding: 0; border: none;">
    <table style="border-collapse: collapse; width: 100%; ">
        <tr>
            <td style="border: 1px solid #ccc; padding: 8px; text-align: center;">
                <strong>Restricciones</strong><br>
                <strong>Padecimiento o tratamiento no cubierto</strong><br>
            </td>
        </tr>
        <c:forEach var="item" items="${articulo82}">
            <tr>
                <td style="border: 1px solid #ccc; padding: 8px; font-size: 12px;">
                    ${item.desArticulo}
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

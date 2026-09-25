<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="widget-section" style="padding: 0; border: none;">
    <table style="border-collapse: collapse; width: 100%; border: none; ">
        <tr>
            <!-- Primera columna con "Contratación aseguramiento" -->
            <td style="border: 1px solid #ccc; padding: 8px; text-align: center;">
                <strong>Contrataci&oacute;n</strong>
                <strong>Aseguramiento</strong>
            </td>
            <!-- Segunda columna con "Padecimiento o Tratamiento" -->
            <td style="border: 1px solid #ccc; padding: 8px; text-align: center;">
                <strong>Padecimiento o Tratamiento</strong>
            </td>
        </tr>

        <c:set var="lastTiempoEspera" value="" />
        <c:set var="rowspan" value="0" />
        <c:forEach var="item" items="${articulo83}">
            <c:if test="${item.tiempoEspera != lastTiempoEspera}">
                <c:set var="rowspan" value="0" />
                <!-- Contar cuántas veces se repite tiempoEspera -->
                <c:forEach var="temp" items="${articulo83}">
                    <c:if test="${temp.tiempoEspera == item.tiempoEspera}">
                        <c:set var="rowspan" value="${rowspan + 1}" />
                    </c:if>
                </c:forEach>
            </c:if>

            <tr>
                <c:if test="${item.tiempoEspera != lastTiempoEspera}">
                    <td style="border: 1px solid #ccc; padding: 8px; font-size: 12px; text-align: center;" rowspan="${rowspan}">
                        ${item.tiempoEspera} meses
                    </td>
                    <c:set var="lastTiempoEspera" value="${item.tiempoEspera}" />
                </c:if>
                <td style="border: 1px solid #ccc; padding: 8px; font-size: 12px;">
                    ${item.desArticulo}
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

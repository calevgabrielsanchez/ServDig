<!-- JSP Inicial del Widget de Persona Fisica. -->
<%@ include file="../general/taglibs.jsp"%>


<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="widget-section" style="padding: 0; border: none;">
    <table style="border-collapse: collapse; width: 100%; ">
        <tr>
            <td style="border: 1px solid #ccc; padding: 8px; text-align: center;">
				El asegurado cuenta con restricciones para el acceso a 
				la atenci&oacute;n m&eacute;dica para los siguientes padecimientos de 
				acuerdo con los tiempos de espera a partir del inicio de su vigencia:
            </td>
        </tr>
    </table>
</div>

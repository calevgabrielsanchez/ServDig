<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<c:set var="contextpath" value="<%=request.getContextPath()%>"/>

<script type="text/javascript">

    $(function () {

        $(function validaPagos() {
            console.log("Código de error enviado: " + $('#CodigoErrorConsultaPagos').val());
            console.log("Código indicador de pagos: " + $('#IndicadorPagos').val());
            console.log("ID Seguro: " + $('#ErrorFormGeneral').val());
            console.log("entrado a la funcion");
            setSizeWithinIframe(document);
        });

        $('#CerrarWizard').live('click', function (event) {
            event.preventDefault();
            parent.WizardSeguroIvroIndivCtrl.cerrar();
        });

        $('#CancelarBeneficio').live('click', function (event) {
            console.log("Entra a la función clic");
            event.preventDefault();
            $('#datosCancelaRiss').submit();

        });
    });
    window.onload = validaPagos();
</script>
<fmt:setLocale value="es_MX" scope="session"/>
<input type="hidden" id="CodigoErrorConsultaPagos" value=${codigoError}>
<input type="hidden" id="IndicadorPagos" value=${indicadorPagos}>
<div id="contenedor" class="contenedor col-sm-12">
    <div class="contenido row">
        <div class="col-sm-12">
            <div style="min-height: 200px;">
                <c:if test="${not empty datosRiss.errorFormGeneral}">
                    <div class="alert alert-danger">
                        <c:choose>
                            <c:when test="${codigoError == '1'}">
                                <p>Para verificar la continuidad en el beneficio al R&eacute;gimen de Incorporaci&oacute;n a la Seguridad Social (RISS) se agradecer&aacute; acuda a la Subdelegaci&oacute;n correspondiente con todos sus antecedentes de pago, seleccione la opci&oacute;n &lt;Cancelar&gt;. En caso de desear continuar la solicitud sin beneficio RISS seleccione la opci&oacute;n &lt;Siguiente&gt;.</p>
                            </c:when>
                            <c:when test="${codigoError == '2'}">
                                <spring:message code="label.wizard.valida.pagos.error.consulta"/>
                            </c:when>
                            <c:when test="${codigoError == '3'}">
                                <spring:message code="label.wizard.valida.pagos.periodo.renovacion"/>
                            </c:when>
                            <c:when test="${codigoError == '4'}">
                                <spring:message code="label.wizard.valida.pagos.error.pagos.futuros"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="label.wizard.valida.pagos.error.conexion"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>
            </div>
            <form:form modelAttribute="persona" id="datosCancelaRiss" method="post"
                       action="${contextpath}/wizard/individual/procesaFaltaPago">
                <form:hidden path="idPersona" maxlength="20"/>
                <form:hidden path="rfc" maxlength="20"/>
                <form:hidden path="tipoPersona.idTipoPersona" maxlength="20"/>
            </form:form>
        </div>
    </div>
    <div class="pie row">
        <div class="opciones col-sm-6"></div>
        <div class="controles col-sm-6">
            <div class="pull-right">
                <button class="btn btn-default" id="CerrarWizard">Cerrar</button>
                <c:if test="${indicadorPagos == '0'}">
                    <a id="CancelarBeneficio" class="btn btn-primary"> <i class="glyphicon glyphicon-step-forward"></i>
                        Continuar
                    </a>
                </c:if>
            </div>
        </div>
    </div>

</div>

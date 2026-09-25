<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
        src="<spring:url value="/static/resources\js\wizard\rtt\inciarRtt.js" htmlEscape="true" />">
</script>

<div class="contenedor col-sm-12">
    <br />
    <p style="text-align: justify;">
        En atenci&oacute;n a su consulta, con motivo de la revisi&oacute;n
        anual de la siniestralidad
        <fmt:formatDate value="${fecSiniestra}" pattern="yyyy" />, se le informa que no se localiz&oacute; ning&uacute;n resultado para el
        ${tipoParam} ingresado para su b&uacute;squeda, ya que no existe.
    </p>
    <br />
</div>
<br />
<div class="pull-right">
    <button id="cerrar" onclick="cerrar()"
            class="btn btn-default">
        Cerrar
    </button>
</div>
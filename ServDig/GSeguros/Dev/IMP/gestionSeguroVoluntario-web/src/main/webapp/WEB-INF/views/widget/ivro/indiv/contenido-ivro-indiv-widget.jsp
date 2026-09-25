<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum" %>
<script type="text/javascript"
  src="<spring:url value="/static/resources/js/widget/ivro-indiv-widget.js" htmlEscape="true" />"></script>
<!--   <script type="text/javascript" -->
<%--   src="<spring:url value="/static/resources/js/widget/ivro-personal.js" htmlEscape="true" />"></script> --%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoAdvertenciaIvro = '<p style="text-align: left;">' + 
	'Si tu seguro aparece como vencido puede ser debido al incumplimiento en el pago oportuno de alguna de ' +
	'tus l&iacute;neas de captura.</p>' +
	'<p style="text-align: left;">Una vez que tu seguro ha sido vencido no se reactivar&aacute; aunque pagues una l&iacute;nea de ' +
	'captura subsecuente, por lo que para reactivar tus servicios m&eacute;dicos debes realizar la compra de un nuevo seguro y cubrir ' +
	'las l&iacute;neas de captura correspondientes en tiempo y forma.</p>';

$('#idPopoverAdvertenciaIvro').popover({
	animation : true,
	html: true,
	content : infoAdvertenciaIvro,
	trigger: 'hover',
	container : 'body'
});

</script>

<div class="widget-section">
    <c:if test="${not empty seguros}">
		<div style="float: right;">
			<a class="btn btn-sm icono-help" id="idPopoverAdvertenciaIvro" data-toggle="popover" title="Importante">
			</a>
		</div>
		<address>
        <span><strong> Fecha Inicio Vigencia </strong></span><br>
        <span><fmt:formatDate pattern="dd/MM/yyyy" value="${seguros[0].fechaInicio}" /> </span><br>
        <span><strong> Fecha Fin Vigencia </strong></span><br>
        <span><fmt:formatDate pattern="dd/MM/yyyy" value="${seguros[0].fechaFin}" /></span><br>
        <span><strong> Modalidad </strong></span><br>
        <span>${seguros[0].modalidad.descripcion}</span><br>
        <span><strong> Estado </strong></span><br>
        <c:choose>
          <c:when test="${seguros[0].estadoSeguro.idEstadoSeguro == 1}">
            <span class="label label-warning label-imss label-warning-imss">
          </c:when>
          <c:when test="${seguros[0].estadoSeguro.idEstadoSeguro == 3}">
            <span class="label label-danger label-imss label-danger-imss">
          </c:when>
          <c:when test="${seguros[0].estadoSeguro.idEstadoSeguro == 4}">
            <span class="label label-danger label-imss label-danger-imss">
          </c:when>
          <c:otherwise>            
            <span class="label label-success label-imss label-success-imss">
   	      </c:otherwise>
        </c:choose>
        ${seguros[0].estadoSeguro.descripcion}</span>
        </address>
        <br>
    </c:if>
    <c:if test="${empty seguros}">
    No cuenta con seguro contratado.
    </c:if>

</div>
<div class="widget-section">
  <div class="pie">
	<div class="opciones">
      
		<div class="btn-group">
			<a class="btn btn-default btn-sm" href="#"><spring:message
					code="label.menus.opciones" /> </a> <a
				class="btn btn-default btn-sm dropdown-toggle"
				data-toggle="dropdown" href="#"><span class="caret"></span></a>
			<ul class="dropdown-menu" id="accionesWidgetIvroIndiv">
				<c:if test="${compra && !esExtemporanea}">
					<li><a id="compraModalidadIvroIndiv"> Comprar </a></li>
				</c:if>

                <c:if test="${not empty seguros}">
                    <li><a id="detalleSeguroIndividual" onclick="muestraDetalle(${seguros[0].cveIdSeguroIvro})"> Ver Detalle </a></li>
                </c:if>
			</ul>
		</div>
      </div>
	</div>
</div>
<!-- Dialogo de confirmacion de cancelacion de solicitud ivro personal 
lanzado al momento de cerrar el wizard principal desde el boton cerrar localizado en la 
parte superior derecha -->
<div id="dlg-confirm-cancel-solic-widget-ivro-indiv" title="Confirmar cancelaci&oacute;n de solicitud">
	
</div>

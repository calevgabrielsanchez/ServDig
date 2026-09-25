<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="codigoTrabajadorCampo" value="<%=ModalidadEnum.CUARENTAYTRES.getId()%>" />
<c:set var="codigoTrabajadorUrbano" value="<%=ModalidadEnum.CUARENTAYCUATRO.getId()%>" />

<style>
.ui-selectable li {
	padding: 15px 25px;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#selectable").selectable();

		$("#siguienteCotizacion").live('click', function(event) {
			event.preventDefault();

			$('#validacion').hide();

			$('#modalidad').val($('.ui-selected').attr('value'));

			if ($('#modalidad').val() != '') {
				$('#formModalidadIvro').submit();
			} else {
				$('#validacion').show();
				setSizeWithinIframe(document);
			}
		});

		$("#cerrarWizard, #cerrarWizardMod").live('click', function(event) {
			event.preventDefault();
			parent.WizardSeguroIvroIndivCtrl.cerrar();
		});
	});
</script>
<fmt:setLocale value="es_MX" scope="session" />
<div class="contenedor col-sm-12">
  <div class="contenido row">
    <jsp:include page="pasosIndividual.jsp">
      <jsp:param name="paso" value="1" />
    </jsp:include>
    <div class="col-sm-12">
      <div id="errorNegocio"></div>
      <div id="successNegocio"></div>

      <!-- Mensaje de Error RISS -->
      <c:if test="${not empty datosRiss.errorFormGeneral}">
        <div class="alert alert-danger">
          <button type="button" class="close" data-dismiss="alert">×</button>

          <c:choose>
            <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'Debido a cancelaci')}">
              <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> Se ubica en los supuestos de terminaci&oacute;n del otorgamiento al subsidio, de conformidad con las disposiciones de car&aacute;cter general para la aplicaci&oacute;n del est&iacute;mulo fiscal al pago de las cuotas obrero patronales al Seguro Social.
            </c:when>
            <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'No cumple requisitos RIF')}">
              <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> El SAT informa que no pertenece al R&eacute;gimen de Incorporaci&oacute;n Fiscal (RIF).
            </c:when>
            <c:when test="${fn:contains(datosRiss.errorFormGeneral, 'Error de comunicaci')}">
              <strong>LO SENTIMOS, POR EL MOMENTO NO ES POSIBLE VERIFICAR SI PERTENCE AL R&Eacute;GIMEN DE INCORPORACI&Oacute;N FISCAL (RIF) ANTE EL SAT, PARA GOZAR DEL BENEFICIO DEL R&Eacute;GIMEN </strong>
            </c:when>
            <c:when test="${!fn:contains(datosRiss.errorFormGeneral, 'Ya cuentas')}">
              <strong>LO SENTIMOS, NO APLICA AL BENEFICIO DEL R&Eacute;GIMEN DE INCORPORACI&Oacute;N AL SEGURO SOCIAL (RISS), POR EL SIGUIENTE MOTIVO:</strong> ${datosRiss.errorFormGeneral}
            </c:when>
            <c:otherwise>${datosRiss.errorFormGeneral}</c:otherwise>
          </c:choose>
        </div>
      </c:if>

      <c:if test="${not empty datosCotizacion.errorFormGeneral}">
        <div style="min-height: 200px;">
          <div class="alert alert-danger">${datosCotizacion.errorFormGeneral}</div>
        </div>
      </c:if>
      <c:if test="${empty datosCotizacion.errorFormGeneral}">
        <div id="tipoTrabajador">
          <div class="titulo separadorseccion">
            <span> Seleccione el Tipo de Trabajador </span>
          </div>
          <div class="alert alert-danger" style="display: none;" , id="validacion">
            <span id="mensaje-validacion">Seleccione el tipo de trabajador</span>
          </div>
          <form:form modelAttribute="datos" id="formModalidadIvro" method="post"
            action="${contextpath}/wizard/individual/modalidad">
            <form:hidden path="modalidad" id="modalidad" />
          </form:form>
        </div>

        <ol id="selectable">
          <li class="ui-state-default" value="${codigoTrabajadorUrbano}">
            <h3>
              <spring:message code="label.wizard.ivro.cont.leyenda.modalidad.uno.title" />
            </h3>
            <p>
              <spring:message code="label.wizard.ivro.cont.leyenda.modalidad.uno" />
            </p>
          </li>
          <li class="ui-state-default" value="${codigoTrabajadorCampo}">
            <h3>
              <spring:message code="label.wizard.ivro.cont.leyenda.modalidad.dos.title" />
            </h3>
            <p>
              <spring:message code="label.wizard.ivro.cont.leyenda.modalidad.dos" />
            </p>
          </li>

        </ol>
      </c:if>
    </div>
  </div>


  <div class="pie row">
    <div class="opciones col-sm-6"></div>
    <div class="controles col-sm-6">
      <div class="pull-right">
        <c:if test="${empty datosCotizacion.errorFormGeneral}">
          <button class="btn btn-default" id="cerrarWizardMod">Cerrar</button>
          <a id="siguienteCotizacion" class="btn btn-primary"> <i class="glyphicon glyphicon-step-forward"></i> Siguiente
          </a>
        </c:if>
        <c:if test="${not empty datosCotizacion.errorFormGeneral}">
          <button class="btn btn-default" id="cerrarWizard">Cerrar</button>
        </c:if>
      </div>
    </div>
  </div>

</div>

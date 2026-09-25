<%@ page import="mx.gob.imss.ctirss.delta.model.enums.FormaPagoEnum" %>
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<style>
.total-ivro {
	border-top: 1px solid lightgray;
	font-size: 25px;
	font-weight: bolder;
}
</style>

<script type="text/javascript"
  src="<spring:url value="/static/resources/js/wizard/individual/finaliza-ivro-individual.js" htmlEscape="true" />"></script>
<script type="text/javascript"
        src="<spring:url value="/static/resources/js/wizard/comunes/obtenerPais.js" htmlEscape="true" />">
</script>
<script type="text/javascript">
	isInternet = ${internet};
	datosSol = {};
	datosSol.idTipoSolicitud = ${solicitud.tipoSolicitud.idTipoSolicitud};
	datosSol.descripcionTipoSolicitud = '${solicitud.tramite[0].tipoTramite.descripcion}';
	datosSol.folioSolicitud = '${solicitud.numSolicitud}';
	datosSol.idTipoTramite = [];
	datosSol.idTipoTramite[0] = ${solicitud.tramite[0].tipoTramite.idTipoTramite};
	datosEntradaFirma = {
		fechaElectronica : '${firmaEntrada.fechaElectronicaFormateada}',
		cadenaOriginal : '${firmaEntrada.cadenaOriginal}'
	};
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="formaPagoAnual" value="<%=FormaPagoEnum.ANUAL.getId()%>" />
<c:set var="formaPagoBimestral" value="<%=FormaPagoEnum.BIMESTRAL.getId()%>" />

<fmt:setLocale value="es_MX" scope="session" />

<div class="contenedor col-sm-12" style="min-height: 400px;">
  <div class="contenido row">
      <c:if test="${ not esRenovacion and not esExtemporanea }">
      <jsp:include page="pasosIndividual.jsp">
          <jsp:param name="paso" value="5" />
      </jsp:include>
      </c:if>
    <div class="col-sm-12">

      <c:if test="${not empty solicitud.errorFormGeneral}">
        <div style="min-height: 200px;">
          <div class="alert alert-danger">${solicitud.errorFormGeneral}</div>
        </div>
      </c:if>
      <div id="error-guardarSolicitud" style="display: none;">
        <div style="min-height: 200px;">
          <div class="alert alert-danger">
            <span id="mensajeErrorSolicitud"></span>
          </div>
        </div>
      </div>
      <c:if test="${empty solicitud.errorFormGeneral}">
        <div class="alert alert-success">
          Tu solicitud ha iniciado correctamente y tu n&uacute;mero de folio es: <strong>${solicitud.numSolicitud}</strong>
        </div>
          <input type="hidden" id="idSolicitud" value=${solicitud.idSolicitud} >

        <div id="divVigencias">
          <div class="container-fluid empty-state">
            <div class="row">
              <div class="col-xs-12 imagen">
                <i class="glyphicon glyphicon-exclamation-sign"></i>
              </div>
            </div>

            <div class="row">
              <div class="col-xs-12 titulo">
                <spring:message code="label.wizard.ivro.cont.personal.finish.leyend" />
              </div>
            </div>
          </div>

          <div class="separadorseccion">
            <span>Datos de la cotizaci&oacute;n</span>
          </div>

          <c:forEach var="emp" items="${solicitud.tramite[0].cotizacion.detalle.empleados}">
            <div class="row">
              <div class="col-xs-4">
                <strong>Solicitante:</strong>
              </div>
              <div class="col-xs-8">${emp.nombreTrabajador}</div>
            </div>

          </c:forEach>
          <div class="row">
            <div class="col-xs-4">
              <strong>Fecha inicio vigencia:</strong>
            </div>
            <div class="col-xs-2">
              <fmt:formatDate pattern="dd/MM/yyyy"
                value="${solicitud.tramite[0].cotizacion.detalle.fechaInicioCalculo.time}" />
            </div>
            <div class="col-xs-4">              
              <strong>Fecha fin vigencia:</strong>
            </div>
            <div class="col-xs-2">            
              <c:choose>
                <c:when test="${solicitud.tramite[0].cotizacion.detalle.empleados[0].conBeneficio}">
                  <fmt:formatDate pattern="dd/MM/yyyy" value="${solicitud.tramite[0].cotizacion.detalle.empleados[0].periodos[0].finPeriodo.time}" />
                </c:when>
                <c:otherwise>
                  <fmt:formatDate pattern="dd/MM/yyyy" value="${solicitud.tramite[0].cotizacion.detalle.fechaFinCalculo.time}" />
                </c:otherwise>
              </c:choose>
              
            </div>

              <div class="col-xs-4">
                  <strong>Forma de pago:</strong>
              </div>
              <div class="col-xs-2">
                  <c:choose>
                      <c:when test="${formaPago eq formaPagoAnual}">
                          Anual
                      </c:when>
                      <c:when test="${formaPago eq formaPagoBimestral}">
                          Bimestral
                      </c:when>
                  </c:choose>
              </div>
          </div>

            <br>


            <div class="separadorseccion">
            <span>Domicilio</span>
          </div>
          <div class="row">
              <div class="col-xs-2"><strong>Calle :</strong></div>
              <div class="col-xs-2">${domicilio.calle}</div>
              <div class="col-xs-2"><strong>N&uacute;m. Ext. :</strong></div>
              <div class="col-xs-2">${domicilio.numExteriorAlf} ${domicilio.numExterior1}</div>
              <div class="col-xs-2"><strong>N&uacute;m. Int. :</strong></div>
              <div class="col-xs-2">${domicilio.numInteriorAlf} ${domicilio.numInterior}</div>
          </div>
          <div class="row">
              <div class="col-xs-2"><strong>Colonia :</strong></div>
              <div class="col-xs-2">${domicilio.colonia}</div>
              <div class="col-xs-2"><strong>Municipio :</strong></div>
              <div class="col-xs-2">
                <c:if test="${domicilio.localidad != null and domicilio.localidad.municipio != null}">
                  ${domicilio.localidad.municipio.nombre}
                </c:if></div>
              <div class="col-xs-2"><strong>C.P. :</strong></div>
              <div class="col-xs-2">${domicilio.codigoPostal}</div>
          </div>
			
			<br>
			<div class="separadorseccion">
				<span>Medios de contacto</span>
			</div>
			<c:forEach items="${personaMC.mediosContacto}" var="medioContacto">
				<div class="row">
					<div class="col-xs-4">
						<strong>
							<c:choose>
								<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 1}">
									Correo electr&oacute;nico:
								</c:when>
								<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 2}">
									Tel&eacute;fono fijo:
								</c:when>
								<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 3}">
									Tel&eacute;fono m&oacute;vil:
								</c:when>
								<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 4}">
									Facebook:
								</c:when>
								<c:when test="${medioContacto.tipoMedioContacto.idTipoMedioContacto == 5}">
									Twitter:
								</c:when>
							</c:choose>
						</strong>
					</div>
					<div class="col-xs-2"><span>${medioContacto.desFormaContacto}</span></div>
				</div>
			</c:forEach>

          <br>
          <fmt:setLocale value="es_MX" />
          <div class="row total-ivro">
            <div class="col-xs-4 col-xs-offset-5" style="text-align: right;">Costo :</div>
            <div class="col-xs-3">
              <c:choose>
                <c:when test="${solicitud.tramite[0].cotizacion.detalle.empleados[0].conBeneficio}">
                  <fmt:formatNumber value="${solicitud.tramite[0].cotizacion.detalle.empleados[0].periodos[0].total}" type="currency" />
                </c:when>
                <c:otherwise>
                  <fmt:formatNumber value="${solicitud.tramite[0].cotizacion.detalle.cuotaTotal}" type="currency" />
                </c:otherwise>
              </c:choose>              
            </div>
          </div>

          
          <br>


        </div>

        <div class="alert alert-info">
          <strong>Acciones al concluir tu solicitud</strong>
          <c:if test="${internet}">
            <p>Una vez concluida tu solicitud, le recordamos que para activar tu seguro debe imprimir sus
              l&iacute;neas de captura y realizar el pago correspondiente.</p>
            <p>Debe imprimir sus l&iacute;neas de captura en la secci&oacute;n "Seguros Personales" mediante la
              opci&oacute;n "Detalle", en esta misma opci&oacute;n podr&aacute; realizar la impresi&oacute;n de su
              comprobante y cuestionario m&eacute;dico.</p>
            <p>Debe imprimir Cuestionario M&eacute;dico para presentar en la UMF.</p>
            <p>Para que el IMSS est&eacute; en contacto con usted, es necesario que tenga registrada una
              direcci&oacute;n de correo electr&oacute;nico, si no cuenta con una ingrese a la secci&oacute;n de Datos
              Personales y actualice sus Medios de Contacto</p>
          </c:if>
          <c:if test="${!internet}">
            <p>Una vez concluida la solicitud, le recordamos que tiene que iniciar nuevamente el tr&aacute;mite para
              poder ver la impresi&oacute;n de líneas de captura, comprobante y cuestionario m&eacute;dico.</p>
          </c:if>
          
			<p>Recuerda imprimir tus L&iacute;neas de Captura y realizar el pago correspondiente antes de la fecha de fin de vigencia de cada l&iacute;nea. Recuerda que el incumplimiento en el pago de tus l&iacute;neas de captura en tiempo y forma es motivo de cancelaci&oacute;n de tus servicios m&eacute;dicos.</p>
          
        </div>
      </c:if>
    </div>
  </div>

  <div id="cancelar-dialogo" title="Cancelaci&oacute;n de solicitud">
    <p>
      <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
      solicitud pendiente?
    </p>
  </div>

  <div id="confirmar-dialogo" title="Notificaci&oacute;n de cancelaci&oacute;n">
    <p>
      <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> Tu solicitud fue cancelada
      exitosamente.
    </p>
  </div>

  <div id="dialog-confirm" title="Aviso">
    <p>
      <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> A continuaci&oacute;n se te
      pedir&aacute; la informaci&oacute;n de tu FIEL para finalizar el tr&aacute;mite.
    </p>
  </div>

  <div class="pie row">
    <div class="opciones col-sm-6">
      <c:if test="${empty solicitud.errorFormGeneral}">
        <div class="btn-group dropup">
          <a href="#" class="btn btn-primary">Acciones </a> <a href="#" data-toggle="dropdown"
            class="btn btn-primary dropdown-toggle"><span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a id="terminarTramite"><i class="glyphicon glyphicon-ok"></i>Finalizar Solicitud</a></li>
            <li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i>Cancelar Solicitud</a></li>
          </ul>
        </div>
      </c:if>
    </div>
    <div class="controles col-sm-6">
      <div class="pull-right">
        <c:if test="${not empty solicitud.errorFormGeneral}">
          <button class="btn btn-default" id="cerrarWizard">Cerrar</button>
        </c:if>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
    a.print {
        color: inherit;
        text-decoration: none;
    }

    a.print:hover {
        color: black;
        text-decoration: none;
    }

    table.table {
        font-size: initial !important;
    }
</style>

<script type="text/javascript"
src="<spring:url value="/static/resources/js/wizard/mod-40/renovacion/wizardIniciarRenovacion.js" htmlEscape="true" />"></script>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum"%>

<c:set var="today" value="<%=new java.util.Date()%>" />
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="codigoTrabajadorUrbano" value="<%=ModalidadEnum.TREINTAYCUATRO.getId()%>" />

<c:set var="estadoPagoPendiente" value="<%=EstadoPagoEnum.POR_PAGAR.getId()%>" />
<c:set var="estadoPagoActivo" value="<%=EstadoPagoEnum.PAGADO.getId()%>" />
<c:set var="estadoPagoVencido" value="<%=EstadoPagoEnum.VENCIDO.getId()%>" />

<div class="contenedor col-sm-12 m-t-sm">
    <div id="errorNegocio"></div>
    <c:set var="defaultLocale" value="${pageContext.request.locale}" />
    <fmt:setLocale value="es_MX" scope="session" />
    <c:if test="${_terminando_sol != null && _terminando_sol}">
        <c:if test="${not seguro.tramite.renovacion}">
            <div class="alert alert-success">La solicitud de inscripci&oacute;n en la Continuaci&oacute;n Voluntaria en el R&eacute;gimen Obligatorio ha sido exitosa.</div>
        </c:if>
    </c:if>	
    <div id="datosSeguro">
        <div class="titulo">
            <span>Datos del seguro</span>
            <hr class="red m-b-none">
        </div>
        <div class="row">
            <div class="col-xs-2">
                <strong>Contratante:</strong>
            </div>
            <div class="col-xs-8">${seguro.titular.nombre}</div>
            <div class="col-xs-2"></div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-2">
                <strong>Tipo Seguro:</strong>
            </div>
            <div class="col-xs-2">
                Continuaci&oacute;n Voluntaria
            </div>
            <div class="col-xs-2">
                <strong>Modalidad:</strong>
            </div>
            <div class="col-xs-6">${seguro.modalidad.numModalidad}- ${seguro.modalidad.descripcion}</div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-2">
                <strong>Fecha Inicio Vigencia:</strong>
            </div>
            <div class="col-xs-2">
                <fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaInicio}" />
            </div>
            <div class="col-xs-2">
                <strong>Fecha Fin Vigencia:</strong>
            </div>
            <div class="col-xs-2">
                <fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fechaFin}" />
            </div>
            <div class="col-xs-1">
                <strong>Estado:</strong>
            </div>
            <div class="col-xs-3">
                <span class="${claseEstado}">${seguro.estadoSeguro.descripcion}</span>
            </div>
        </div>
    </div>


	<!--  
    <div id="recordatorio" class="m-t-md">
        <div class="alert alert-info" style="text-align: justify;">
            <strong>Te encuentras en estatus Baja por Mora, a&uacute;n est&aacute; dentro del periodo para tu reingreso. Es necesario estar al corriente en los pagos vencidos, selecciona la opci&oacute;n Iniciar Tr&aacute;mite para que sean generadas las l&iacute;neas de captura correspondientes a los meses de atraso.</strong>
        </div>
    </div>
    -->

	
<%-- ==================== DETALLE BAJA POR FALTA DE PAGO (MORA) ==================== --%>
<%-- <c:if test="${not empty detalleMora}"> --%>
<!--     <div id="infoBajaMora" class="m-t-xl"> -->
<!--         <div class="panel panel-warning"> -->
<!--             <div class="panel-heading"> -->
<!--                 <h4 class="panel-title"> -->
<!--                     <i class="fa fa-exclamation-triangle"></i> -->
<!--                     Informaci&oacute;n de baja por falta de pago -->
<!--                 </h4> -->
<!--             </div> -->
<!--             <div class="panel-body"> -->
<!--                 <p class="text-warning"> -->
<!--                     <strong>Este seguro fue dado de baja por falta de pago.</strong> -->
<!--                 </p> -->

<!--                 Resumen general en grid -->
<!--                 <div class="row m-t-md"> -->
<!--                     <div class="col-md-3 col-sm-6"> -->
<!--                         <div class="well well-sm text-center"> -->
<!--                             <small class="text-muted">Fecha de baja</small><br> -->
<!--                             <strong> -->
<%--                                 <fmt:formatDate value="${detalleMora.fechaEfectivaBaja}" pattern="dd/MM/yyyy" /> --%>
<!--                             </strong> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                     <div class="col-md-3 col-sm-6"> -->
<!--                         <div class="well well-sm text-center"> -->
<!--                             <small class="text-muted">Meses sin pago</small><br> -->
<!--                             <strong style="font-size: 20px; color: #c9302c;"> -->
<%--                                 ${detalleMora.mesesMora} --%>
<!--                             </strong> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                     <div class="col-md-3 col-sm-6"> -->
<!--                         <div class="well well-sm text-center"> -->
<!--                             <small class="text-muted">Per&iacute;odos vencidos</small><br> -->
<!--                             <strong style="font-size: 20px; color: #c9302c;"> -->
<%--                                 <c:out value="${detalleMora.periodosVencidos}" /> --%>
<!--                             </strong> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                     <div class="col-md-3 col-sm-6"> -->
<!--                         <div class="well well-sm text-center"> -->
<!--                             <small class="text-muted">Montos adeudados</small><br> -->
<!--                             <strong style="font-size: 18px; color: #c9302c;"> -->
<%--                                 <c:out value="${detalleMora.montosAdeudos}" /> --%>
<!--                             </strong> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->

<!--                 ============= ACORDE?N CON PAGOS VENCIDOS ============= -->
<!--                 <div class="m-t-md"> -->
<!--                     <button type="button" -->
<!--                             class="btn btn-warning btn-block" -->
<!--                             data-toggle="collapse" -->
<!--                             data-target="#detallePagosVencidos" -->
<!--                             aria-expanded="false"> -->
<!--                         <i class="fa fa-list"></i> -->
<!--                         <strong>Ver detalle de los pagos vencidos</strong> -->
<!--                         <span class="caret"></span> -->
<!--                     </button> -->

<!--                     <div id="detallePagosVencidos" class="collapse m-t-sm"> -->
<!--                         <div class="panel panel-default"> -->
<!--                             <div class="panel-body"> -->
<!--                                 <div class="table-responsive"> -->
<!--                                     <table class="table table-bordered table-hover table-condensed"> -->
<!--                                         <thead class="bg-danger text-white"> -->
<!--                                             <tr> -->
<!--                                                 <th class="text-center">#</th> -->
<!--                                                 <th>Per&iacute;odo</th> -->
<!--                                                 <th class="text-right">Monto</th> -->
<!--                                                 <th class="text-center">Fecha l&iacute;mite pago</th> -->
<!--                                             </tr> -->
<!--                                         </thead> -->
<!--                                         <tbody> -->
<%--                                             Filtrar solo los pagos VENCIDOS --%>
<%--                                             <c:set var="contador" value="1" /> --%>
<%--                                             <c:forEach items="${seguro.compra.pagos}" var="pago"> --%>
<%--                                                 <c:if test="${pago.estadoPago.idEstadoPago eq estadoPagoVencido}"> --%>
<!--                                                     <tr> -->
<!--                                                         <td class="text-center"> -->
<%--                                                             <span class="badge badge-danger">${contador}</span> --%>
<!--                                                         </td> -->
<!--                                                         <td> -->
<%--                                                             <fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fechaInicioPeriodo}" /> --%>
<!--                                                             al -->
<%--                                                             <fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fechaFinPeriodo}" /> --%>
<!--                                                         </td> -->
<!--                                                         <td class="text-right"> -->
<!--                                                             <strong style="color: #c9302c;"> -->
<%--                                                                 <fmt:formatNumber value="${pago.monto}" type="currency" /> --%>
<!--                                                             </strong> -->
<!--                                                         </td> -->
<!--                                                         <td class="text-center"> -->
<%--                                                             <fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fechaLimitePago}" /> --%>
<!--                                                         </td> -->
<!--                                                     </tr> -->
<%--                                                     <c:set var="contador" value="${contador + 1}" /> --%>
<%--                                                 </c:if> --%>
<%--                                             </c:forEach> --%>
<!--                                         </tbody> -->
<!--                                     </table> -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->

<!--                 Info adicional -->
<!--                 <div class="alert alert-info m-t-md"> -->
<!--                     <i class="fa fa-calendar"></i> -->
<!--                     <strong>Rango de vencimientos:</strong> -->
<%--                     <fmt:formatDate value="${detalleMora.fechaPrimerVencimiento}" pattern="dd/MM/yyyy" /> --%>
<!--                     al -->
<%--                     <fmt:formatDate value="${detalleMora.fechaUltimoVencimiento}" pattern="dd/MM/yyyy" /> --%>
<%--                     <c:if test="${not empty detalleMora.fechaUltimoPago}"> --%>
<!--                         <br> -->
<!--                         <i class="fa fa-check-circle"></i> -->
<!--                         <strong>&Uacute;ltimo pago recibido:</strong> -->
<%--                         <fmt:formatDate value="${detalleMora.fechaUltimoPago}" pattern="dd/MM/yyyy" /> --%>
<%--                     </c:if> --%>
<!--                 </div> -->
<!--             </div> -->
<!--         </div> -->
<!--     </div> -->
<%-- </c:if> --%>




    <%-- ==================== DETALLE BAJA RO ==================== --%>        
            
<c:if test="${not empty detalleReingresoRO}">
        <div id="infoReingresoRO" class="m-t-xl">
            <div class="titulo">
                <span>Informaci&oacute;n del reingreso al R&eacute;gimen Obligatorio</span>
                <hr class="red m-b-none">
            </div>

            <div class="alert alert-info m-t-md" style="text-align: justify;">
                <strong><i class="glyphicon glyphicon-info-sign"></i> Importante:</strong>
                Su seguro fue dado de baja debido a su reingreso al R&eacute;gimen Obligatorio. 
                Si requiere m&aacute;s informaci&oacute;n consulte su reporte de Vigencia de Derechos 
                o solicite informaci&oacute;n en la Subdelegaci&oacute;n que corresponda a su domicilio o 
                en los medios electr&oacute;nicos que este Instituto pone a su disposici&oacute;n en la p&aacute;gina: 
                <a href="http://www.imss.gob.mx/contacto" target="_blank">http://www.imss.gob.mx/contacto</a>.
            </div>
        </div>
    </c:if>





<%-- ======================================== --%>
	

    <c:if test="${not empty detalleBajaExpresa}">
        <div id="infoBajaExpresa" class="m-t-xl">
            <div class="titulo">
                <span>Informaci&oacute;n de la baja expresa en Modalidad 40</span>
                <hr class="red m-b-none">
            </div>

            <div class="alert alert-info m-t-md" style="text-align: justify;">
                <strong><i class="glyphicon glyphicon-info-sign"></i> Importante:</strong>
                Su seguro en Modalidad 40 fue dado de baja mediante una solicitud de baja expresa
                confirmada a trav&eacute;s del enlace enviado a su correo electr&oacute;nico.
            </div>

            <div class="row m-t-md">
                <div class="col-xs-6">
                    <div class="form-group">
                        <label><strong>Fecha de baja expresa:</strong></label>
                        <p class="form-control-static">
                            <fmt:formatDate value="${detalleBajaExpresa.fechaBajaExpresa}"
                                            pattern="dd 'de' MMMM 'de' yyyy" />
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <div id="pagos" class="m-t-xl">
        <div class="titulo">
            <span>Pagos</span>
            <hr class="red m-b-none">
        </div>
        <div id="tblSegurosWrapper" class="table-responsive">
            <table id="tblIvroSegurosIndivResume" class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Fecha Inicio</th>
                        <th>Fecha Fin</th>
                        <th>Monto</th>
                        <th>Fecha L&iacute;mite Pago</th>
                        <th>Estatus</th>
                        <c:if test="${imprimir}">
                            <th></th>
                        </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${seguro.compra.pagos}" var="pago">
                    <tr>
                        <td>
                    <fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fechaInicioPeriodo}" />
                    </td>
                    <td>
                    <fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fechaFinPeriodo}" />
                    </td>
                    <td>
                    <fmt:formatNumber value="${pago.monto}" type="currency" />
                    </td>
                    <td>
                    <fmt:formatDate pattern="dd/MM/yyyy" value="${pago.fechaLimitePago}" />
                    </td>
                    <td class="text-center">
                    <c:choose>
                        <c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoPendiente}">
                            <c:set var="cssClassEdoPago" value="label-warning" />
                        </c:when>
                        <c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoActivo}">
                            <c:set var="cssClassEdoPago" value="label-success" />
                        </c:when>
                        <c:when test="${pago.estadoPago.idEstadoPago eq estadoPagoVencido}">
                            <c:set var="cssClassEdoPago" value="label-danger" />
                        </c:when>
                    </c:choose>
                    <span class="label ${cssClassEdoPago}">${pago.estadoPago.descripcion}</span>
                    </td>
                    <c:if test="${today.time lt (pago.fechaLimitePago.time + 86400000) and (pago.imprimible or (pago.estadoPago.idEstadoPago ne estadoPagoActivo and pago.estadoPago.idEstadoPago ne estadoPagoVencido))
						and (empty detalleReingresoRO && empty detalleBajaExpresa)}">
                    <td class="text-center">
                        <a class="link print" onclick="imprimePago(${pago.idPago})" title="Descargar">
                            <i class="glyphicon glyphicon-download-alt fa-2x"></i>
                        </a>
                    </td>
                    </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
            



            
        </div>
    </div>

    <div class="pie row">
        <div class="opciones col-sm-3">
        </div>
        <div class="controles col-sm-9 text-right">
            <button class="btn btn-primary" id="iniciarTramite">Iniciar Tr&aacute;mite</button>
            <a class="btn btn-primary" id="verComprobante" data-id="${ID_CIFRADO}" onclick="imprSegPerIvro(this)">Obtener comprobante</a>
            <button class="btn btn-default" id="cerrarWizard">Cancelar</button>
        </div>
    </div>
    <fmt:setLocale value="${defaultLocale}" scope="session" />
</div>
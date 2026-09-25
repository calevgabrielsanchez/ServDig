<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/Domicilio.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/administrar/initAdmonDomicilios.js" htmlEscape="true" />"></script>

<style>
.site_position_center {
	width: 100% !important;
}

ul#icons li {
	cursor: pointer;
	float: left;
	list-style: none outside none;
	margin: 2px;
	padding: 4px;
	position: relative;
}

.contenedor {
	height: auto;
}
</style>

<div class="page_holder" style="margin: 0; width: 100%;">
	<div class=" contenedor">
		<table id="tblAdmonDomicilios" style="width: 100%">
			<thead>
				<tr>
					<th>Descripci&oacute;n corta</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="domicilio" items="${domicilios}" varStatus="indice">
					<tr>
						<td>
							<address>
								<strong>Domicilio
									${domicilio.dicTipoDomicilio.descripcion}</strong><br>
								<spring:message code="label.calle.num" />
								: <span id="nombreVialidadPrimaria">
									${domicilio.vialidadPrimaria.nombre} </span> <span
									id="numExterior">
									${domicilio.numExterior1} </span> <span
									id="numExteriorAlfa">
									${domicilio.numExteriorAlf} </span>, <span
									id="numInterior">
									${domicilio.numInterior} </span> <span
									id="numInteriorAlfa">
									${domicilio.numInteriorAlf} </span><br>
								<spring:message code="label.colonia" />
								: <span id="nombreAsentamiento">
									${domicilio.asentamiento.nombre} </span><br>
								<spring:message code="label.municipio" />
								: <span id="nombreMunicipio">
									${domicilio.asentamiento.localidad.municipio.nombre}
								</span><br>
								<spring:message code="label.entidadFederativa" />
								: <span id="nombreEstado">
									${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}
								</span><br> C.P. <span id="codigoPostal">
									${domicilio.asentamiento.codigoPostal.codigoPostal}
								</span>
							</address>
						</td>
						<td><c:choose>
								<c:when
									test="${domicilio.estadoAdministracionDomicilio.clave == 1}">
									<label style="float: left">POR SER AGREGADO</label>
								</c:when>
								<c:when
									test="${domicilio.estadoAdministracionDomicilio.clave == 2}">
									<label style="float: left">POR SER
										${domicilio.estadoAdministracionDomicilio}</label>
								</c:when>
								<c:when
									test="${domicilio.estadoAdministracionDomicilio.clave == 3  }">
									<label style="float: left">POR SER
										${domicilio.estadoAdministracionDomicilio}</label>
								</c:when>
							</c:choose></td>
						<td style="vertical-align: top; text-align: right;"><c:if
								test="${domicilio.estadoAdministracionDomicilio.clave != 3  }">
								<ul id="icons" class="ui-widget">
									<li class="ui-state-default ui-corner-all"><a
										href="${contextpath}/domicilio/administrar/particular/modificar/${indice.index}"
										style="text-decoration: none;"
										target="modificarDomicilioFrame" class="modificarDom"> <span
											class="ui-icon ui-icon-document"
											style="display: inline-block" title="MODIFICAR"></span>
									</a></li>
									<li class="ui-state-default ui-corner-all"><a href="#"
										style="text-decoration: none;"
										onclick="eliminarDomicilio(${indice.index})"> <span
											class="glyphicon glyphicon-trash" style="display: inline-block"
											title="ELIMINAR"></span>
									</a></li>
								</ul>
							</c:if> <c:if
								test="${domicilio.estadoAdministracionDomicilio.clave == 3  }">
								<div style="text-align: center;">
									<ul id="icons" class="ui-widget">
										<li class="ui-state-default ui-corner-all"><span
											class="ui-icon ui-icon-arrowreturnthick-1-s"
											style="display: inline-block" title="DESHACER"
											onclick="deshacerEliminar(${indice.index})"></span></li>
									</ul>
								</div>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div id="dialog-confirm"
	title="Confirmar eliminaci&oacute;n del domicilio">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>¿Desea eliminar el
		domicilio?
	</p>
</div>

<!-- FORMA PARA ELIMINAR UN DOMICILIO -->
<form
	action="${contextpath}/domicilio/administrar/particular/eliminar"
	id="deleteDomForm"></form>
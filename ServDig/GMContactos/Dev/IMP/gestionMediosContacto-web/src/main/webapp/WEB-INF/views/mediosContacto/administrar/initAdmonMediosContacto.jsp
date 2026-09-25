<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/administrar/AdmonRegistroMedioContacto.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/administrar/AdmonModifMedioContacto.js" htmlEscape="true" />"></script>	
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/administrar/initAdmonMediosContacto.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
	table.table {
		font-size: 15px !important;
	}
	
	div.dataTables_wrapper {
		font-size: 15px;
	}
	
	.btn-toolbar-medio .btn {
	    box-shadow: none;
	}
		
	table#tblAdmonMedios td address {
		margin-bottom: 0px;
	}
</style>

<c:set var="LST_MEDIOS" value="mediosContacto_${idPersona }" />

<div style="width: 100% !important; margin: 0px;">
	<div class=" contenedor">
	
		<span id="errorNegocioLabel" class="error hiddenElement"></span>
		
		<input type="hidden" name="idPersona" id="idPersona" value="${idPersona }" />
		
		<table id="tblAdmonMedios" style="width: 100%;"
				class="table table-striped" cellpadding="0"
				cellspacing="0" border="0">
			<thead>
				<tr>
					<th>Descripci&oacute;n corta</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="medioContacto" items="${sessionScope[LST_MEDIOS]}"
					varStatus="indice">
					<tr>
						<td>
							<address>
								<strong>${medioContacto.tipoMedioContacto.descripcion}</strong><br>
								${medioContacto.desFormaContacto}<br>
							</address>
						</td>
						<td style="vertical-align: middle;" ><c:if
								test="${medioContacto.estadoAdministracionMedioContacto.clave == 1}">
								<label style="float: left">POR SER AGREGADO</label>
							</c:if> <c:if
								test="${medioContacto.estadoAdministracionMedioContacto.clave == 2}">
								<label style="float: left">POR SER
									${medioContacto.estadoAdministracionMedioContacto}</label>
							</c:if> <c:if
								test="${medioContacto.estadoAdministracionMedioContacto.clave == 3  }">
								<label style="float: left">POR SER
									${medioContacto.estadoAdministracionMedioContacto}</label>
							</c:if></td>
						<td class="text-center">
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave != 3  }">
								<div role="toolbar" class="btn-toolbar-medio">
									<div class="btn-group btn-group-sm">
										<button aria-label="MODIFICAR" class="btn btn-default" type="button"
											onclick="modificarMedioContacto(${indice.index})">
											<span aria-hidden="true" class="glyphicon glyphicon-pencil"></span>
										</button>
										<button aria-label="ELIMINAR" class="btn btn-default" type="button"
											onclick="eliminarMedioContacto(${indice.index})">
											<span aria-hidden="true" class="glyphicon glyphicon-trash"></span>
										</button>
									</div>
								</div>
							</c:if>
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave == 3  }">
								<div role="toolbar" class="btn-toolbar-medio">
									<div class="btn-group btn-group-sm">
										<button aria-label="DESHACER" class="btn btn-default" type="button"
											onclick="deshacerEliminar(${indice.index})">
											<span aria-hidden="true" class="fa fa-undo"></span>
										</button>
									</div>
								</div>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br><br>
	</div>
</div>
<div style="float: right; display: inline-block; text-align: right; width: 100%; margin-bottom: 20px;">
	<button type="button" class="btn btn-primary" id="btnMediosContacto">REGISTRAR MEDIO</button>
</div>

<!-- Para medios de contacto particulares -->
<div id="agregarMedioContactoDialog"></div>
<div id="modificarMedioContactoDialog"></div>

<div id="eliminarMedioConfirm"
	title="Confirmar eliminaci&oacute;n del medio de contacto">
	<p class="m-n">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>¿Desea eliminar el
		medio de contacto?
	</p>
</div>

<!-- FORMA PARA ELIMINAR UN MEDIO DE CONTACTO -->
<form action="${contextpath }/medios/particulares/administrar/eliminar" id="deleteMedioForm"></form>

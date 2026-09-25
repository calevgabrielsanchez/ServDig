<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/administrar/AdmonMedioContactoFiscal.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/administrar/initAdmonMediosContactoFiscales.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

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
	
	.contenedor{
		height: auto;
	}
</style>

<div class="page_holder" style="width: 100% !important; margin: 0px;">
	<div class=" contenedor">
	
		<span id="errorNegocioLabel" class="error hiddenElement"></span>
	
		<table id="tblAdmonMediosFiscales" style="width: 100%">
			<thead>
				<tr>
					<th>Descripci&oacute;n corta</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="medioContacto" items="${mediosContactoFiscales}" varStatus="indice">
					<tr>
						<td>
							<address>
								<strong>${medioContacto.tipoMedioContacto.descripcion}</strong><br>
								${medioContacto.desFormaContacto}<br>
							</address>
						</td>
						<td>
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave == 1}">
								<label style="float: left">POR SER AGREGADO</label>
							</c:if>
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave == 2}">
								<label style="float: left">POR SER ${medioContacto.estadoAdministracionMedioContacto}</label>
							</c:if>
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave == 3  }">
								<label style="float: left">POR SER ${medioContacto.estadoAdministracionMedioContacto}</label>
							</c:if>
						</td>
						<td style="vertical-align: top; text-align: right;">
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave != 3  }">
								<ul id="icons" class="ui-widget">
									<li class="ui-state-default ui-corner-all">
										<span class="ui-icon ui-icon-document" style="display: inline-block"
											title="MODIFICAR" onclick="modificarMedioFiscal(${indice.index})"></span>
									</li>
									<li class="ui-state-default ui-corner-all">
										<a href="#" style="text-decoration: none;" onclick="eliminarMedioFiscal(${indice.index})"> 
											<span class="ui-icon ui-icon-trash" style="display: inline-block"
												title="ELIMINAR"></span>
										</a>
									</li>
								</ul>
							</c:if>	
							<c:if test="${medioContacto.estadoAdministracionMedioContacto.clave == 3  }">
								<div style="text-align: center;">
									<ul id="icons" class="ui-widget">
										<li class="ui-state-default ui-corner-all">
											<span class="ui-icon ui-icon-arrowreturnthick-1-s" style="display: inline-block"
												title="DESHACER" onclick="deshacerEliminarMedioFiscal(${indice.index})"></span>
										</li>
									</ul>
								</div>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<br><br>
		<form action="" id="formAuxCSS">
			<div style="text-align: right; float: right;">
				<input type="button" value="Registrar Medio de Contacto"
					class="mboton" id="btnMediosFiscales" />
			</div>
		</form>	
	</div>
</div>

<!-- Para medios de contacto particulares -->
<div id="agregarMedioContactoFiscalDialog"></div>

<div id="eliminarMedioFiscalConfirm"
	title="Confirmar eliminaci&oacute;n del Medio Fiscal">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>¿Desea eliminar el
		medio de contacto fiscal?
	</p>
</div>

<!-- FORMA PARA ELIMINAR UN MEDIO DE CONTACTO -->
<form action="${contextpath }/medios/fiscales/administrar/eliminar" id="deleteMedioFiscalForm"></form>
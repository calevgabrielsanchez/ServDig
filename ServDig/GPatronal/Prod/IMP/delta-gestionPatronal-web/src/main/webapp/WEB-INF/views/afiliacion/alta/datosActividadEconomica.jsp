<%@ include file="../../general/taglibs.jsp"%>



<div id="seccionDatosGenerales">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="titulo.datos.generales.patron"/>
	</legend>
	<table width="920px" border="0" cellpadding="0" cellspacing="0" style="margin: 0px !important;">
		<tr>
			<td>
				<c:if test="${esOperador == true}">
					<span class="etiqueta"><spring:message code="label.fecha.presentacion"/>:</span>
				</c:if>
				<c:if test="${esOperador == false}">
					<span class="etiqueta"><spring:message code="label.fecha.captura"/>:</span>
				</c:if>
			</td>
			<td style="width:120px">
			<c:if test="${clasificacion.fecPresentacion != null}">
				<input type="text" id="fechaPresentacion" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${clasificacion.fecPresentacion}"/>" style="width: 100px;" disabled="disabled">
			</c:if>
			<c:if test="${clasificacion.fecPresentacion == null}">
				<input type="text" id="fechaPresentacion" value="" style="width: 100px;" disabled="disabled">
			</c:if>
			</td>
			<td colspan="3">
				<span class="etiqueta" style="float: right;"><spring:message code="label.fecha.surte.efecto"/>:</span>
			</td>
			<td style="width:180px">
				<input type="text" id="fechaEfecto" style="width: 70px;" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${clasificacion.fecEfecto}"/>" onkeypress="evaluar(this, event)" maxlength="10" onblur="evaluarOnblur(this, event)" onclick="showCalendar()"/><label id="fechaEfectoInvalidaMsg" style="color: red; display: none;">
					fecha inv&aacute;lida
				</label>
			</td>
		</tr>
	</table>
</div>
<br/>
<div id="seccionClasificacion">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.aviso.seguro.riesgos"/>
	</legend>
	<table width="920px">
	<tr>
		<td width="30%">
			<span class="etiqueta">Especificar su giro o actividad: </span>
		</td>
		<td width="70%">
			<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
				<div style="display: table-row;" id="giro">
					<div style=" width: 100%; height: 150px;">
						<textarea class="alfanumerico" style="width:97%;"
								  rows="7" 
								  id="giroClasificacion"  
								  onkeydown="validaSize(this, 300, event);" 
								  onkeyup="validaSize(this, 300, event);"
								  onblur="validaSize(this, 300, event);"><c:if test="${clasificacion != null}">${clasificacion.giro}</c:if></textarea>
					</div>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<span id="labelServicioDePersonal" class="etiqueta">Presta servicio de personal:</span>
		</td>
		<td>
			<form:radiobutton path="clasificacion.indPrestaServicioPersonal" value="1" label="SI" />
			<form:radiobutton path="clasificacion.indPrestaServicioPersonal" value="0" label="NO" />
		</td>
	</tr>
	</table>		
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.aviso.ley.seguro"/>
	</legend>
	<span class="etiqueta" style="font-size: .75em !important; text-align:justify">
		<spring:message code="label.aviso.conformidad"/>:
	</span>
	<center>
		<div id="wrapperIntsAnterior" class="ui-widget" style="width: 75% !important;">
			<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
				<p>
					<i class="glyphicon glyphicon-info-sign"></i></span>
					<strong>
						Seleccione su nueva clasificaci&oacute;n:
					</strong> 
						Conozca el nuevo cat&aacute;logo de actividades econ&oacute;micas 
					<strong>
						<a href="javascript:seleccionarClasificacion();"> aqu&iacute;</a>
					</strong>
				</p>
			</div>
		</div>
	</center>
	<table width="920px" cellpadding="0;" cellspacing="0">
		<tr class="fielsetgris">
			<td align="center"><span id="cveFracc" class="etiqueta"><spring:message code="label.rp.clave.fraccion"/></span></td>
			<td align="center"><span id="division" class="etiqueta"><spring:message code="label.rp.division"/></span></td>
			<td align="center"><span id="gruposFr" class="etiqueta"><spring:message code="label.rp.grupo"/></span></td>
			<td align="center"><span id="strFracc" class="etiqueta"><spring:message code="label.rp.fraccion"/></span></td>
			<td align="center"><span id="claseFrc" class="etiqueta"><spring:message code="label.rp.clase"/></span></td>
			<td align="center"><span id="primaSTR" class="etiqueta"><spring:message code="label.rp.prima.srt"/></span></td>
		</tr>
		<tr>
			<td align="center">
				<span class="dato" id="claveDivisionCompleta">
					${clasificacion.fraccion.grupo.division.numDivision}${clasificacion.fraccion.grupo.numGrupo}${clasificacion.fraccion.numFraccion}
				</span>
				<span class="dato" id="claveDivision" style="visibility: hidden;">
					${clasificacion.fraccion.grupo.division.numDivision}
				</span>
				<span class="dato" id="claveGrupo" style="visibility: hidden;">
					${clasificacion.fraccion.grupo.numGrupo}
				</span>
				<span class="dato" id="claveFraccion" style="visibility: hidden;">
					${clasificacion.fraccion.numFraccion}
				</span>
				<input type="hidden" id="fraccion" value="${clasificacion.fraccion.id}"/>
			</td>
			<td align="center">
				<span class="dato" id="textDivison">
					${clasificacion.fraccion.grupo.division.descripcion}
				</span>
				<input type="hidden" id="divison" value="${clasificacion.fraccion.grupo.division.id}"/>
			</td>
			<td align="center">
				<span class="dato" id="textGrupo">
					${clasificacion.fraccion.grupo.descripcion}
				</span>
				<input type="hidden" id="grupo" value="${clasificacion.fraccion.grupo.id}"/>
			</td>
			<td align="center">
				<span class="dato" id="textFraccion">
					${clasificacion.fraccion.descripcionDetallada}
				</span>
			</td>
			<td align="center">
				<input type="hidden" id="claveClase" value="${clasificacion.fraccion.clase.clave}">
				<span class="dato" id="textClase">
					${clasificacion.fraccion.clase.descripcion}
				</span>
				<input type="hidden" id="clase"/>
			</td>
			<td align="center">
				<span class="dato" id="textPrimaAnt">
					${clasificacion.fraccion.primaSRT}
				</span>
				<input type="hidden" id="primaAnt"/>
			</td>
		</tr>
	</table>
</div>
<div id="seccionProductosMaeriales">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.datos.actividad.declara"/>
	</legend>
	<table width="100%">
		<tr>
			<td style="vertical-align: top; max-width: 400px;">
				<table id="gridProductosServicios" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
				<thead>
				</thead>
				<tbody style="width: 100%;">
				</tbody>
				<tfoot>
				<tr>
					<td></td>
					<td align="center">
						<div class="opciones">
							<div class="opcion">
								<input type="button" onclick="dialogoEdicion('producto', 'Agregar');" class="mboton"
									value="Agregar" style="font-size: .8em !important;">
							</div>
							<div class="opcion">
								<input type="button" onclick="dialogoEdicion('producto', 'Modificar');" class="mboton"
									value="Modificar" style="font-size: .8em !important;">
							</div>
							<div class="opcion">
								<input type="button" onclick="dialogoEdicion('producto', 'Eliminar');" class="mboton"
									value="Eliminar" style="font-size: .8em !important;">
							</div>			

						</div>
					</td>
				</tr>
				</tfoot>
				</table>
			</td>
			<td style="vertical-align: top; max-width: 400px;">
				<table id="gridMaeriasMateriales" style="width: 100%; vertical-align: top;" cellpadding="0" cellspacing="0">
				<thead>
				</thead>
				<tbody style="width: 100%;">
				</tbody>
				<tfoot>
				<tr>
					<td></td>
					<td align="center">
						<div class="opciones">
							<div class="opcion">
								<input type="button" onclick="dialogoEdicion('material', 'Agregar');" class="mboton"
									value="Agregar" style="font-size: .8em !important;">
							</div>
							<div class="opcion">
								<input type="button" onclick="dialogoEdicion('material', 'Modificar');" class="mboton"
									value="Modificar" style="font-size: .8em !important;">
							</div>
							<div class="opcion">
								<input type="button" onclick="dialogoEdicion('material', 'Eliminar');" class="mboton"
									value="Eliminar" style="font-size: .8em !important;">
							</div>			
						</div>
					</td>
				</tr>
				</tfoot>
				</table>
			</td>
		</tr>
	</table>
</div>
<div id="seccionMaquinariaEquipo">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.maquinaria.equipo"/>
	</legend>
	<table id="gridMaquinariaEquipo" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
	<thead>
	</thead>
	<tbody style="width: 100%;">
	</tbody>
	<tfoot>
	<tr>
		<td></td>
		<td align="center" colspan="5">
			<div class="opciones">
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('equipo', 'Agregar');" class="mboton"
						value="Agregar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('equipo', 'Modificar');" class="mboton"
						value="Modificar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('equipo', 'Eliminar');" class="mboton"
						value="Eliminar" style="font-size: .8em !important;">
				</div>			
			</div>
		</td>
	</tr>
	</tfoot>
	</table>
</div>
<div id="seccionTransporte">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.equipo.transporte"/>
	</legend>
	<table>
		<tr>
			<td>
				<span class="etiqueta">&iquest;Cuenta con equipo de transporte?</span>
			</td>
			<td>
				<form:radiobutton id="siCuetaConTransporte" path="cuentaConTransporte" value="1" label="SI" onchange="toggleCuentaConTransporte(1)"/>
				<form:radiobutton id="noCuetaConTransporte" path="cuentaConTransporte" value="0" label="NO" onchange="toggleCuentaConTransporte(0)" />
			</td>
		</tr>
	</table>
	<table id="gridTransporte" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
	<thead>
	</thead>
	<tbody style="width: 100%;">
	</tbody>
	<tfoot>
	<tr>
		<td></td>
		<td align="center" colspan="5">
			<div class="opciones">
				<div class="opcion">
					<input id="agregarTransporte" type="button" onclick="dialogoEdicion('transporte', 'Agregar');" class="mboton"
						value="Agregar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input id="modificarTransporte"type="button" onclick="dialogoEdicion('transporte', 'Modificar');" class="mboton"
						value="Modificar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input id="eliminarTransporte"type="button" onclick="dialogoEdicion('transporte', 'Eliminar');" class="mboton"
						value="Eliminar" style="font-size: .8em !important;">
				</div>			
			</div>
		</td>
	</tr>
	</tfoot>
	</table>
</div>
<div id="seccionProcesos">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.proceso.trabajo"/>
	</legend>
	<table cellspacing="0" cellpadding="0" class="tablaverde2" style="width: 100%;" id="tbActividades">
		<tbody>
			<tr>
				<td>
					<input type="hidden" id="procesoClave" value="<c:if test='${proceso != null}'>${proceso.clave}</c:if>"/>
					<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
						<div style="display: table-row;" id="proceso-inicial">
							<h6 style="font-size: 1.0em !important;"><spring:message code="label.procesos.iniciales"/></h6>
							<div style=" width: 100%; height: 150px;">
								<textarea style="width:98%;" rows="7" id="procesoInicial" onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"><c:if test="${proceso != null}">${proceso.desInicial}</c:if></textarea>
							</div>
						</div>
						<div style="display: table-row;" id="proceso-intermedio">
							<h6 style="font-size: 1.0em !important;"><spring:message code="label.procesos.intermedios"/></h6>
							<div style="width: 100%; height: 150px;">
								<textarea style="width:98%;" rows="7" id="procesoIntermedio"  onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"><c:if test="${proceso != null}">${proceso.desIntermedio}</c:if></textarea>
							</div>
							
						</div>
						<div style="display: table-row;" id="proceso-final">
							<h6 style="font-size: 1.0em !important;"><spring:message code="label.procesos.finales"/></h6>
							<div style=" width: 100%;height: 150px;">
								<textarea style="width:98%;" rows="7" id="procesoFinal"  onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)"><c:if test="${proceso != null}">${proceso.desFinal}</c:if></textarea>
							</div>			
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div id="seccionPersonal">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.personal"/>
	</legend>
	<table id="gridPersonal" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
	<thead>
	</thead>
	<tbody style="width: 100%;">
	</tbody>
	<tfoot>
	<tr>
		<td></td>
		<td align="center" colspan="2">
			<div class="opciones">
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('personal', 'Agregar');" class="mboton"
						value="Agregar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('personal', 'Modificar');" class="mboton"
						value="Modificar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('personal', 'Eliminar');" class="mboton"
						value="Eliminar" style="font-size: .8em !important;">
				</div>			
			</div>
		</td>
	</tr>
	</tfoot>
	</table>
</div>
<div id="seccionActividades">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.actividades.complementarias"/>
	</legend>
	<table width="100%">
			<tr>
				<td style="width: 50%;">
					<spring:message code="label.distribuidor.entrega"/>
					<br>
					<table width="100%" style="padding-left:55px;">
						<tbody><tr class="odd">
							<td class="dtJustifyClassColumn">
							<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
								<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" disabled="disabled"><spring:message code="label.transporte.propio"/>
							</c:if>
							<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
							<c:if test="${sujetoTramite.clasificacion.indTransportePropio == 1}">
								<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" checked="true"><spring:message code="label.transporte.propio"/>
							</c:if>
							<c:if test="${sujetoTramite.clasificacion.indTransportePropio != 1}">
								<input type="checkbox" id="indTransportePropioTemp" name="indTransportePropioTemp" ><spring:message code="label.transporte.propio"/>
							</c:if>
							</c:if>
							</td>
						</tr>
						<tr class="even">
							<td class="dtJustifyClassColumn">
							<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
									<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" disabled="disabled"><spring:message code="label.transporte.ajeno"/>
							</c:if>
							<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
								<c:if test="${clasificacion.indTransporteAjeno == 1}">
									<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp" checked="true"><spring:message code="label.transporte.ajeno"/>
								</c:if>
								<c:if test="${sujetoTramite.clasificacion.indTransporteAjeno != 1}">
									<input type="checkbox" id="indTransporteAjenoTemp" name="indTransporteAjenoTemp"><spring:message code="label.transporte.ajeno"/>
								</c:if>
							</c:if>
							</td>
						</tr>
						<tr class="odd">
							<td class="dtJustifyClassColumn">
							<c:if test="${sujetoTramite.cuentaConTransporte == 0}">
								<input type="checkbox" id="indNoDistribuyeTemp" name="indNoDistribuyeTemp"  checked="true"><spring:message code="label.no.distribuye"/>
							</c:if>
							<c:if test="${sujetoTramite.cuentaConTransporte == 1}">
								<input type="checkbox" id="indNoDistribuyeTemp" name="indNoDistribuyeTemp"  disabled="disabled"><spring:message code="label.no.distribuye"/>
							</c:if>
							</td>
						</tr>
					</tbody></table>
				</td>
				<td valign="top" style="width: 50%;">
				<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros == 1}">
					<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp" checked="true"><spring:message code="label.servicios.terceros"/>
				</c:if>
				<c:if test="${sujetoTramite.clasificacion.indServiciosATerceros != 1}">
					<input type="checkbox" id="indServiciosTercerosTemp" name="indServiciosTercerosTemp"><spring:message code="label.servicios.terceros"/>
				</c:if>
				</td>
			</tr>
		</table>
</div>
<div id="seccionBienesInmuebles">
	<legend class="separadorseccion" style="width:930px">
		<spring:message code="label.bienes.inmuebles"/>
	</legend>
	<table id="gridBienes" style="vertical-align: top; width:100%; max-width: 930px;" cellpadding="0" cellspacing="0">
	<thead>
	</thead>
	<tbody style="width: 100%;">
	</tbody>
	<tfoot>
	<tr>
		<td></td>
		<td align="center" colspan="5">
			<div class="opciones">
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('bienes', 'Agregar');" class="mboton"
						value="Agregar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('bienes', 'Modificar');" class="mboton"
						value="Modificar" style="font-size: .8em !important;">
				</div>
				<div class="opcion">
					<input type="button" onclick="dialogoEdicion('bienes', 'Eliminar');" class="mboton"
						value="Eliminar" style="font-size: .8em !important;">
				</div>			
			</div>
		</td>
	</tr>
	</tfoot>
	</table>
	<table cellspacing="0" cellpadding="0" class="tablaverde2" style="width: 100%;" id="tbActividades">
		<tbody>
			<tr>
				<td>
					<div style="display: table; padding: 30px; width: 100%;" id="tbProceso">
						<div style="display: table-row;" id="proceso-intermedio">
							<h6 style="font-size: 1.0em !important;"><spring:message code="label.bienes.uso"/></h6>
							<div style="width: 100%; height: 150px;">
								<textarea style="width:98%;" rows="7" id="afectacionBienes"  
								onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 600, event)">${desUsosBienes}</textarea>
							</div>
						</div>
						<div style="display: table-row;" id="proceso-inicial">
							<h6 style="font-size: 1.0em !important;"><spring:message code="label.bienes.afectacion"/></h6>
							<div style=" width: 100%; height: 150px;">
								<textarea style="width:98%;" rows="7" id="usoBienes" 
								onkeypress="validaSize(this, 600, event)" onkeyup="validaSize(this, 300, event)">${desAfectacion}</textarea>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
					
					
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/promocion/generico/estatusObraGenericoTab.js"></script>

<table class="tablaverde2" width="100%">
	<tr>
		<td colspan="4" width="100%"><form:form id="estatusObraTABForm"
				method="post" modelAttribute="seguimientoGenericoVO" action="">
				<form:hidden path="cvePromocion" id="cvePromocion" />
				<form:hidden path="estatusObraVO.fechaPresentacion" id="fecPresentacionEstObraGenerico" />
				<form:hidden path="estatusObraVO.incidenciasModelo.numeroDeRegistroDeObra" id="numeroDeRegistroDeObra" />
				<table class="tablaverde2" style="width: 100%">

					<tr class="impar">
						<td align="left" class="etiqueta2" width="100%" colspan="6">
							&nbsp;</td>
					</tr>

					<tr class="par">
						<td align="left" class="etiqueta2" width="100%" colspan="6">
						</td>
					</tr>
					<tr>
						<td align="left" class="etiqueta2">&nbsp;</td>
						<td align="right" class="etiqueta2"><label>Fecha de	Atenci&oacute;n del Oficio: </label></td>
						<td align="left" >
						   <form:input path="estatusObraVO.fechaAtencionOficio" id="fecAtencionOficioEstObraGenerico" 
								onchange="jsValidaFecAtnEstObra(this.value);" />
								 <span class="boton_limpiar" onclick="limpiaFechaAtencionOficio();" id="spnFecAtnEstObra">X</span>
							<div id="labelfecAtencionOficioEstObraGenerico"></div></td>
						<td align="right" class="etiqueta2" width="16%">
							<form:checkbox 	path="estatusObraVO.regularizarObra" id="cbxRegulaObraEstObraGen" onclick="seleccionaRegulaObra()" />
						</td>
						<td align="left" width="16%" class="etiqueta2"><label>Regularizar
								Obra </label></td>
						<td align="left" class="etiqueta2">&nbsp;</td>
					</tr>
					
					<tr valign="top" class="par">
						<td align="left" colspan="6" class="etiqueta2">&nbsp;
						</td>
					</tr>


					<tr valign="top" class="par">
						<td align="left" colspan="3" class="etiqueta2">

							<div id="labelErrorConsultaInc"></div>
							<div id="wrapperTableIncidencias"
								style="background-color: white !important; width: 600px"
								align="center">
								<label>INCIDENCIAS </label>
								<table id="dtIncidenciasDisponiblesPromocion"
									style="width: 600px" align="center">
									<thead>
									</thead>
									<tbody align="center">
									</tbody>
								</table>

							</div></td>
						<td align="left" colspan="3" class="etiqueta2">

							<div id="labelErrorConsultaPeriodos"></div>
							<div id="wrapperTablePeriodosPresentados"
								style="background-color: white !important; width: 400px"
								align="center">
								<label>PERIODOS PRESENTADOS </label>
								<table id="dtPeriodosPresentadosPromocion" style="width: 400px"
									align="center">
									<thead>
									</thead>
									<tbody align="center">
									</tbody>
								</table>

							</div>
						</td>
					</tr>

					<tr valign="top" class="par">
						<td align="center" colspan="6" ><input
							type="button" value="Guardar" id="btnGuardarEstObraGen"
							width="10px" height="12px" class="boton"
							onclick="guardarEstatusRegularizada()"></td>
					</tr>
					<tr valign="top" class="impar">
						<td align="center" colspan="6" class="etiqueta2">&nbsp; </td>
					</tr>
				</table>
				<input  type="hidden"  id="functionAuxEstatusObra"/>
			</form:form></td>
	</tr>

</table>



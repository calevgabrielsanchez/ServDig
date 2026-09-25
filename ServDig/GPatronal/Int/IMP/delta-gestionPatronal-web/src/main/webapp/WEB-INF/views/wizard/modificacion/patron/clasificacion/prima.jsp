<%@ include file="../../../../general/taglibs.jsp"%>

<div id="divPrimaMSRTRestoTramites" style="display:${param.codigo == 11 || param.codigo == 13  || param.codigo == 14 || param.codigo == 15
										  || param.codigo == 16 || param.codigo == 17 || param.codigo == 18 || param.codigo == 19
										  || param.codigo == 12 || param.codigo == 7 ? 'block' : 'none'}">		

	<div class="row">
		<div id="wrapperIntsAnterior" class="col-sm-12">
			<div class="alert alert-info">												
				Esta prima es la sugerida conforme a las reglas del art&iacute;culo 28
					del RACERF, &iquest;est&aacute; de acuerdo con ella?. 					
			</div>
		</div>
	</div>							

	<div class="separadorseccion">
		<span>
			Prima<span class="required" id="gridPatronesFusionados">*</span>
		</span>
	</div>

	<div class="row" id="primaPatronRestoTramites">
		<label class="control-label col-sm-4" for="primaRestoTramites">Prima sugerida<span id="primaSRT">*</span>:</label>
		<div class="col-sm-4">
			<input type="text" maxlength="8" id="primaSRTRestoTramites" name="primaSRTRestoTramites" class="form-control" 
				value="${param.prima}" disabled="disabled" />
			
			<span  class="error" id="primaSRTRestoTramitesError"></span>		
		</div>
		<div class="col-sm-4">
			<input type="checkbox" id="modPrimaPatron" name="modPrimaPatron" 
		 		onclick="marcaModificarPrima(this);" class="ns_"/>&nbsp;Modificar prima
		</div>				
	</div>
	
	<div class="row" style="display: block;">
		<div class="col-sm-8"><span>&nbsp;</span></div>
	</div>
	
</div>

<div id="divPrimaMSRT" style="display:${param.codigo == 20 || param.codigo == 21 || param.codigo == 175 || param.codigo == 176 ? 'block' : 'none'}">

	<div class="row">
		<div id="wrapperIntsAnterior" class="col-sm-12">
			<div class="alert alert-info">												
				Esta prima es la sugerida conforme a las reglas del art&iacute;culo 28
					del RACERF, &iquest;est&aacute; de acuerdo con ella?. 
			</div>
		</div>
	</div>							
				
	<div class="separadorseccion">
		<span>
			Prima<span class="required" id="gridPatronesFusionados">*</span>
		</span>
	</div>
	<div class="row" id="primaPatronPrincipal" style="display:${param.codigo == 20 || param.codigo == 21 || param.codigo == 175 || param.codigo == 176 ? 'block' : 'none'}">
		<label class="control-label col-sm-4" for="primaFusion">Prima sugerida<span id="primaSRTFusionSustReq">*</span>:</label>
		<div class="col-sm-4">	
			<input type="text" maxlength="8" id="primaSRTFusionSust" name="primaSRTFusionSust"
				disabled="disabled" value="${param.prima}" class="form-control"/>
			<span  class="error" id="primaSRTFusionSustError"></span>		
		</div>
		
		<div class="col-sm-4">
			<input type="checkbox" id="modPrimaPatronFS" name="modPrimaPatronFS" 
		 		onclick="marcaModificarPrima(this);" class="ns_"/>&nbsp;Modificar prima
		</div>				
<!-- 		
		<div class="col-sm-4">
			<button class="btn btn-default btn-sm" id="habilitarCapturaPrima" style="display:none">Modificar prima</button>
		</div>
 -->				
	</div>
	
	<div class="row" style="display: block;">
		<div class="col-sm-8"><span>&nbsp;</span></div>
	</div>	
		
	<c:if test="${param.codigo==175}">
		<div class="row" style="display: block;">
			<div class="col-sm-8"><span>&nbsp;</span></div>
		</div>
		<div class="row" style="display: block;">
			<div class="col-sm-8">	
					<span>
						<b><spring:message code="label.aviso.formato.prima.sustituta" /></b>
					</span>
			</div>
		</div>
	</c:if>
	
	<!-- Calculo de la prima -->
	
	<c:if test="${param.codigo==21}">
	<form action="#" id="formCalculoPrima">
	<div class="row" id="datosCalculoPrima">

		<div class="col-sm-12">
			<div class="row">
				<label class="control-label col-sm-4" for="diasSubsidiados">
					Total de d&iacute;as subsidiados a causa de incapacidad temporal <span id="diasSubsidiadosReq">*</span>:
				</label>
				<div class="col-sm-4">
					<input type="hidden" id="nrpFus" value="${param.nrpFus}" /> 	
					<input type="text" maxlength="8" id="diasSubsidiados" name="diasSubsidiados" value="" class="form-control prima-bloqueable" placeholder="Captura los dias subsidiados"/>
					<span  class="error" id="diasSubsidiadosError"></span>
				</div>
				<label class="control-label col-sm-3" for="diasSubsidiados">
					Factor de prima:
				</label>
				<div class="col-sm-1">
					2.3
				</div>
			</div>
			<div class="row">
				<label class="control-label col-sm-4" for="porcentajeIncapacidades">
					Suma de los porcentajes de las incapacidades permatentes parciales y totales,
					divididos entre 100
					 <span id="porcentajeIncapacidadesReq">*</span>:
				</label>
				<div class="col-sm-4">
					<input type="text" maxlength="8" id="porcentajeIncapacidades" name="porcentajeIncapacidades" value="" class="form-control prima-bloqueable" placeholder="Captura el porcentaje de incapacidades"/>
					<span  class="error" id="porcentajeIncapacidadesError"></span>
				</div>
				<label class="control-label col-sm-3" for="diasSubsidiados">
					A&ntilde;os promedio de vida activa:
				</label>
				<div class="col-sm-1">
					28
				</div>
			</div>
			<div class="row">
				<label class="control-label col-sm-4" for="numeroDefunciones">
					N&uacute;mero de defunciones
					 <span id="numeroDefuncionesReq">*</span>:
				</label>
				<div class="col-sm-4">
					<input type="text" maxlength="8" id="numeroDefunciones" name="numeroDefunciones" value="" class="form-control prima-bloqueable" placeholder="Captura el numero de defunciones"/>
					<span  class="error" id="numeroDefuncionesError"></span>
				</div>
				<label class="control-label col-sm-3" for="diasSubsidiados">
					Prima m&iacute;nima de riesgos :
				</label>
				<div class="col-sm-1">
					0.0050
				</div>
			</div>
			<div class="row">
				<label class="control-label col-sm-4" for="numeroTrabajadores">
					N&uacute;mero de trabajadores promedio expuestos al riesgo
					 <span id="numeroTrabajadoresReq">*</span>:
				</label>
				<div class="col-sm-4">
					<input type="text" maxlength="8" id="numeroTrabajadores" name="numeroTrabajadores" value="" class="form-control prima-bloqueable" placeholder="Captura el numero de trabajadores"/>
					<span  class="error" id="numeroTrabajadoresError"></span>
				</div>
				<label class="control-label col-sm-3" for="diasSubsidiados">
					N&uacute;mero de d&iacute;as naturales del a&ntilde;o :
				</label>
				<div class="col-sm-1">
					365
				</div>
			</div>
			<div class="row">
				<label class="control-label col-sm-4" for="primaAnterior">
					Prima anterior :
				</label>
				<div class="col-sm-4">
					<input type="text" maxlength="8" id="primaAnterior" value="${param.primaActual}" class="form-control" readonly="readonly" placeholder="Prima anterior"/>
				</div>
				<div class="col-sm-4">
				</div>
			</div>
			<div class="row">
				<label class="control-label col-sm-4" for="primaCalculada">
					Nueva prima :
				</label>
				<div class="col-sm-4">
					<input type="text" maxlength="8" id="primaCalculada" value="" class="form-control" readonly="readonly" placeholder="Prima calculada"/>
				</div>
				<div class="col-sm-4">
				</div>
			</div>
			<div class="row">
				<label class="control-label col-sm-4" for="primaResultado">
					Resultado :
				</label>
				<div class="col-sm-4">
					<input type="text" maxlength="8" id="primaResultado" value="" class="form-control" readonly="readonly" placeholder="Prima Resultante"/>
					<span  class="error" id="primaResultadoError"></span>
				</div>
				<div class="col-sm-1"><br><label>%</</label></div>
				<div class="col-sm-3">
					<button class="btn btn-primary btn-sm" type="button" id="btnCalcularPrima">Calcular prima</button>
					<div id="botonesPrima" style="display:none">
						<button class="btn btn-danger btn-sm" type="button" id="btnLimpiarPrima">Limpiar</button>
						<button class="btn btn-primary btn-sm" type="button" id="btnAceptarPrima">Aceptar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
	</c:if>

</div>


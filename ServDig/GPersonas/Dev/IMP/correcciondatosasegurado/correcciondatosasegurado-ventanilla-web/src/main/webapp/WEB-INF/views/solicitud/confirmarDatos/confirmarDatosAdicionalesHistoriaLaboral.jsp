<div id="info-paso" style="margin-bottom: 50px;">
<div class="contenedor">
	<h3>
		<spring:message code="label.solicitud.datosContacto" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
</div>
<div class="row">
	<label for="telefonoFijo"
		class="col-md-3 col-sm-5 col-xs-12 control-label"
		style="text-align: left;"> <spring:message
		code="label.solicitud.placeholder.telefonoFijo" /><span>:</span>
	</label>
		<div class="col-md-3 col-sm-7 col-xs-12">
			${solicitud.tramites[0].personaRENAPO.telefonoFijo.numero}
			</div>			
		
			<label for="telefonoCelular"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
		code="label.solicitud.placeholder.telefonoCelular" /><span>:</span>
			</label>
			<div class="col-md-3 col-sm-7 col-xs-12">
				${solicitud.tramites[0].personaRENAPO.telefonoMovil.numero}
			</div>
		</div>

		<div class="row">
			<label for="correoElectronico"
				class="col-md-3 col-sm-5 col-xs-12 control-label"
				style="text-align: left;"> <spring:message
					code="label.datosContacto.correoElectronico" />
			</label>
			<div class="col-md-5 col-sm-7 col-xs-12">
				${solicitud.tramites[0].personaRENAPO.correoElectronico.correo}
			</div>
		</div>	

<div id="info-paso" style="margin-bottom: 50px;">
<div class="contenedor">
	<h3>
		<spring:message code="label.solicitud.observaciones" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;">
</div>
	<div class="form-group">
		<div class="row">
			<div class="col-md-12 col-sm-7 col-xs-12">
				<textarea id="observaciones" disabled="disabled" maxlength="250" style="width: 100%;resize: none;outline: none;font-size: 18px;">${solicitud.tramites[0].observacion}</textarea>			
			</div>
		</div>
	</div>	
</div>
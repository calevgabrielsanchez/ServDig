<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="/gestionDomicilios-web-ciudadano/static/resources/js/delta/domicilios/recortado/DomicilioRecortadoCtrl.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/domicilioCentroTrabajo.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>3. Registrar domicilio del centro de trabajo</h4>
				<h5>Paso 1 de 1: Captura los campos requeridos</h5>
			</div>
		</div>
		<form class="form-horizontal" role="form" id="form_nom_comercial">
			<div class="form-group">
					<label class="col-sm-4 control-label m-b-xs" for="nombreComercial">
						Nombre comercial:
					</label>
					<div class="col-sm-8 m-b-xs">
						<textarea class="nombreComercial form-control" rows="2" id="nombreComercial" name="nombreComercial" maxlength="120" placeholder="Ingresa el nombre comercial de la persona f&iacute;sica o empresa a registrar"></textarea>
					</div>
			</div>
		</form>
		<h4 class="subtitulo">Domicilio del centro de trabajo</h4>
		<div class="row">
			<div class="col-sm-12">
				<div class="checkbox">
				    <label>
				      <input type="checkbox" id="mismoDomicilioFiscal">
				      Es mi mismo domicilio fiscal
				    </label>
				  </div>
				<div id="componenteDomicilio"></div>
			</div>
		</div>
		<h4 class="subtitulo">Contacto de la empresa o centro de trabajo</h4>
		<form class="form-horizontal" role="form" id="form-medios-contacto">
				
				<div class="form-group">
					<label class="col-sm-1 control-label" for="lada">
						<spring:message code="label.lada"/><span class="required" style="color: black;">*</span>:
					</label>
					<div class="col-sm-2">
						<input id="lada" class="form-control" maxlength="3" placeholder="000" type="text" value="" name="lada">
					</div>
					<label class="col-sm-3 control-label" for="telefonoFijo">
						<spring:message code="label.telefonoFijo"/><span class="required" style="color: black;">*</span>:
					</label>
					<div class="col-sm-2">
						<input id="telefonoFijo" class="form-control" maxlength="8" placeholder="55555555" type="text" value="" name="telefonoFijo">
					</div>
					<label class="col-sm-2 control-label" for="extension">
						<spring:message code="label.extension"/>:
					</label>
					<div class="col-sm-2">
						<input id="extension" class="form-control" maxlength="6" placeholder="2222"  type="text" value="" name="extension">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label" for="lada2">
						<spring:message code="label.lada"/>:
					</label>
					<div class="col-sm-2">
						<input id="lada2" class="form-control" maxlength="3" placeholder="000" type="text" value="" name="lada2">
					</div>
					<label class="col-sm-3 control-label" for="telefonoFijo2">
						<spring:message code="label.telefonoFijo"/>:
					</label>
					<div class="col-sm-2">
						<input id="telefonoFijo2" class="form-control" maxlength="8" placeholder="55555555" type="text" value="" name="telefonoFijo2">
					</div>
					<label class="col-sm-2 control-label" for="extension2">
						<spring:message code="label.extension"/>:
					</label>
					<div class="col-sm-2">
						<input id="extension2" class="form-control" maxlength="6" placeholder="2222" type="text" value="" name="extension2">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label" for="correo">
						<spring:message code="label.correoElectronico"/><span class="required" style="color: black;">*</span>:
					</label>
					<div class="col-sm-8">
						<input id="correo" class="form-control" style="text-transform: none" placeholder="Ingresa el correo electr&oacute;nico" type="text" value="" name="correo" maxlength="50">
					</div>
				</div>
		</form>
		
		<div class="row">
			<div class="col-sm-12">
				<h4 class="subtitulo">Asignaci&oacute;n de subdelegaci&oacute;n</h4>
				<p align="justify">
					Por el domicilio se te ha asignado la siguiente subdelegaci&oacute;n. En caso de que te aparezca una segunda opci&oacute;n, elige la que mas te convenga.
				</p>
				<div id="contenedorSubdelegacion">
					<div class="alert alert-info">Seleccione una colonia para ubicar la subdelegaci&oacute;n que le corresponde.</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 text-left">
				*Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
				<button id="cancelarDomicilioCT" class="btn btn-default">Atr&aacute;s</button>
				<button id="guardarDomicilioCT" class="btn btn-primary">Siguiente</button>
			</div>
		</div>
	</div>
</div>
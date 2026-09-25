<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/personasAutorizadas.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>6. Registrar personas autorizadas</h4>
				<h5>Paso 1 de 1: Registra el personal autorizado para realizar tr&aacute;mites ante el IMSS.</h5>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12" id="divPersonasAutorizadas"></div>
		</div>
		<form class="form-horizontal" role="form" id="form-busqueda">
			<h4>Persona autorizada <span id="numeroPersonaAut">1</span></h4>
			<p>Ingresa los datos de la persona autorizada</p>
			<div class="alert alert-danger" id="errorFormBusquedaPA" style="display:none"></div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="rfc">
					RFC<span class="required">*</span>:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control alfanumericoEstricto " name="rfc" id="rfcPA" value="" maxlength="13" placeholder="RFC"/>
				</div>
				<label class="control-label col-sm-2" for="curp">
					CURP<span class="required">*</span>:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control alfanumericoEstricto " name="curp" id="curpPA" value="" maxlength="18" placeholder="CURP"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-right">
					<button type="button" class="btn btn-primary" id="buscarPersona">Buscar</button>
				</div>
			</div>
		</form>
		<form class="form-horizontal" role="form" id="form-resultado" style="display:none">
			<div class="alert alert-info">Para añadir esta persona a tu registro patronal presiona Aceptar.<br>
			Si los datos mostrados no coinciden, realiza una nueva b&uacute;squeda.</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="nombre">
					Nombre (s):
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="nombre" name="nombre" disabled="disabled" value="" placeholder="RFC"/>
				</div>
				<label class="control-label col-sm-2" for="primerApellido">
					Primer apellido:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="primerApellido" name="primerApellido"  disabled="disabled" value="" placeholder="CURP"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="segundoApellido">
					Segundo apellido:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="segundoApellido" name="segundoApellido" disabled="disabled" value="" placeholder="RFC"/>
				</div>
				<label class="control-label col-sm-2" for="telefonoFijo">
					Tel&eacute;fono fijo:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="telefonoFijo" id="telefonoFijo" maxlength="15" placeholder="Tel&eacute;fono fijo"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="telefonoMovil">
					Tel&eacute;fono m&oacute;vil:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="telefonoMovil" id="telefonoMovil" maxlength="10" value="" placeholder="Tel&eacute;fono m&oacute;vil"/>
				</div>
				<label class="control-label col-sm-2" for="extension">
					Extensi&oacute;n:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="extension" id="extension" maxlength="4" value="" placeholder="Extensi&oacute;n"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="correo">
					Correo electr&oacute;nico:
				</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="correo" id="correo" value="" placeholder="Introduzca su correo electr&oacute;nico"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-right">
					<button type="button" class="btn btn-primary" id="guardaPersonaAutorizada">Aceptar</button>
				</div>
			</div>
		</form>
		
		<div class="row">
			<div class="col-sm-6 text-left">
				*Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
				<button class="btn btn-default" id="cancelarPersonasAutorizadas">Atr&aacute;s</button>
				<button class="btn btn-primary" id="guardarPersonasAutorizadas">Siguiente</button>
			</div>
		</div>
	</div>
</div>
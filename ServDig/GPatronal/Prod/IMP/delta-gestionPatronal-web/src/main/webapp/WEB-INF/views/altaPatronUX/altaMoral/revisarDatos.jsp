<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/confirmacionDatos.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
			<h4>2. Revisar tus datos fiscales</h4>
			<h5>Paso 1 de 1: Revisi&oacute;n de datos</h5>
			<p align="justify">
				<strong>IMPORTANTE: </strong>
				A continuaci&oacute;n se muestran los datos obtenidos del SAT, con ellos se realizar&aacute; tu tr&aacute;mite en el IMSS. 
				Verifica que est&aacute;n correctos y completos, si alg&uacute;n dato es err&oacute;neo acude a la dependencia correspondiente para realizar la aclaraci&oacute;n.
			</p>
			<h5>Datos de la persona moral proporcionados por SAT:</h5>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				
				<table class="table table-bordered table-striped">
					<tr>
						<th>RFC(con homoclave)</th>
						<th>Raz&oacute;n social</th>
					</tr>
					<tr>
						<td><span id="rfcConfirmacion"></span></td>
						<td><span id="razonSocialConfirmacion"></span></td>
					</tr>
					<tr>
						<th>Estatus</th>
						<th>Fecha de inicio de operaciones</th>
					</tr>
					<tr>
						<td><span id="estatusConfirmacion"></span></td>
						<td><span id="fechaInicioConfirmacion"></span></td>
					</tr>
				</table>
				<br>
				<table class="table table-bordered table-striped">
					
					<tr>
						<th>Tel&eacute;fono fijo</th>
						<th>Extensi&oacute;n</th>
						<th>Tel&eacute;fono m&oacute;vil</th>
					</tr>
					<tr>
						<td><span id="telefonoFiscalConfirmacion"></span></td>
						<td><span id="extensionFiscalConfirmacion"></span></td>
						<td><span id="movilFiscalConfirmacion"></span></td>
					</tr>
					<tr>
						<th colspan="3">Correo electr&oacute;nico</th>
					</tr>
					<tr>
						<td colspan="3"><span id="correoFiscalConfirmacion"></span></td>
					</tr>
				</table>
				<br>
				<table  class="table table-bordered table-striped">
					<tr>
						<th>C&oacute;digo postal</th>
						<th>Estado</th>
						<th>Municipio o alcald&iacute;a</th>
						<th>Localidad</th>
					</tr>
					<tr>
						<td><span id="codigoPostalConfirmacion"></span></td>
						<td><span id="entidadConfirmacion"></span></td>
						<td><span id="municipioConfirmacion"></span></td>
						<td><span id="localidadConfirmacion"></span></td>
					</tr>
					<tr>
						<th>Colonia</th>
						<th>Calle</th>
						<th>N&uacute;mero exterior</th>
						<th>N&uacute;mero interior</th>
						
					</tr>
					<tr>
						<td><span id="coloniaConfirmacion"></span></td>
						<td><span id="calleConfirmacion"></span></td>
						<td><span id="numeroConfirmacion"></span></td>
						<td><span id="numeroIntConfirmacion"></span></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 text-right">
				<strong>¿Tus datos est&aacute;n completos y correctos?</strong>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 text-right">
				<button id="rechazoConfirmacion" class="btn btn-default">No</button>
				<button id="aceptarConfirmacion" class="btn btn-primary">Si</button>
			</div>
		</div>
	</div>
</div>
<div id="dialogMensajes"></div>
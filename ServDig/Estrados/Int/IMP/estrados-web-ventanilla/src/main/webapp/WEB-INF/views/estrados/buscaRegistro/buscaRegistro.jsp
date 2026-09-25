<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/commons/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/estrados/buscaRegistro/buscaRegistro.js"></script>
</head>

<body>
	<div style="width: 1010px">
		<table width="1010px">
			<tr>
				<td width="150px" align="right">
					<table id="idTablaConsultaInterna"
						title="Notificaciones presentadas"
						class="table table-striped table-bordered">
						<thead>
							<tr>
								<th></th>
								<th>
									<select name="idStatusCombo" id="idStatusCombo">
										<option selected="SELECCIONE.." value="2">PUBLICAR</option>
										<option value="3">RETIRAR</option>
									</select> 
									<script> $("#idStatusCombo").change(function (val) {
		            					if ($( this ).val == 2) {
											$("#idFechaPublicacion").datepicker({
												minDate: "-10D", maxDate: "-1D"});
										} else if($( this ).val == 3) {
											$("#idFechaPublicacion").datepicker({maxDate: "-11D"});
										}
	          								});
	          						</script>
          						</th>
								<th colspan="1" align="right">
									<br>
									<a title="" id="idToolTipFechaPublicacion" class="btn-small icono-help"
									data-original-title="Fecha de publicaci&oacute;n"></a>
									Seleccionar fecha
								</th>
								<th colspan="1">
									<form id="idFormFechaPublicacion">
										<br>
										<input type="text" id="idFechaPublicacion" style="text-transform: uppercase; width: 100px"
											readonly="readonly"></input>
									</form>
								</th>
								<th></th>
								<th></th>
							</tr>
							<tr>
								<th></th>
								<th>Registros a ejecutar</th>
								<th><input type="text" id="idResultado" readonly="readonly"/></th>
								<th>de la fecha</th>
								<th><input type="text" id="deLaFecha" readonly="readonly"/></th>
								<th></th>
							</tr>
							<tr>
								<th></th>
								<th><div class="btn-group">
											<button type="button" id="btnSalir" class="btn btn-primary btn-sm">Regresar</button>
										</div></th>
								<th>
									<div class="btn-group">
										<button type="button" id="botonLimpiar"
												class="btn btn-primary btn-sm">Limpiar</button>
									</div>
									<div class="btn-group">
										<button type="button" id="botonBuscar"
												class="btn btn-primary btn-sm">Buscar</button>
									</div>
								</th>
								<th>
									<div class="btn-group">
										<button type="button" id="botonPublicar"
											class="btn btn-primary btn-sm">Publicar manualmente</button>
									</div>
								</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="dialogoEjecutados"></div>
</body>
<form action="<%=request.getContextPath()%>/estrados/consultaInterna.do" id="redireccionListado"></form>
</html>

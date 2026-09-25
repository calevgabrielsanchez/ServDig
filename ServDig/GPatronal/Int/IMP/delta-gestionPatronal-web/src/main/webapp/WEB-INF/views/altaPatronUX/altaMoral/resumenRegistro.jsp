<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/resumenRegistro.js" htmlEscape="true" />"></script>
<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>8. Generar tu N&uacute;mero de Registro Patronal</h4>
				<h5>Paso 2 de 2: Imprime tu comprobante y documentos</h5>
			</div>
		</div>
		<div class="alert alert-success">Tu Número de Registro Patronal es <strong><span id="registroPatronalCreado"></span></strong></div>
		<p align="justify">Los comprobantes de Alta Patronal Persona Moral han sido enviados a tu correo electr&oacute;nico. Tambi&eacute;n puedes
			descargarlos en este momento. Es muy importante que los conserves para cualquier aclaraci&oacute;n.</p>
		</br>
		<table id="idTblResumenRegistro" class="table table-bordered table-striped table-word-wrap-fixed">
			<thead>
		       	<tr>
		         	<th width="180px"><h5>Folio</h5></th>
		         	<th width="160px"><h5>Fecha</h5></th>
		         	<th><h5>Documentos</h5></th>
		         	<th width="160px"></th>
		    	</tr>
			</thead>
			<tbody>
		   		<tr>
					<td><span id="folioSolARP"></span></td>
					<td><span id="fechaSolARP"></span></td>
		         	<td><label for="txtDesEquipoTransporte" class="control-label" >
							ARP (Aviso de Registro Patronal Persona Moral)
						</label>
					</td>
		         	<td>
		         		<div class="row icono-tramite">
							<div class="col-sm-12  text-center">
								<a class="icon-printing" id="imprimirARP"></a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="glyphicon glyphicon-save"	id="descargarARP"></a>
							</div>
						</div>
		         	</td>
		    	</tr>
		    	<tr>
					<td><span id="folioSolTIP"></span></td>
					<td><span id="fechaSolTIP"></span></td>
					<td><label for="txtDesEquipoTransporte" class="control-label" >
							TIP (Tarjeta de Identificaci&oacute;n Patronal)
						</label>
					</td>
		         	<td>
		         		<div class="row icono-tramite">
							<div class="col-sm-12  text-center">
								<a class="icon-printing" id="imprimirTIP"></a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a class="glyphicon glyphicon-save"	id="descargarTIP"></a>
							</div>
						</div>
		         	</td>
		    	</tr>
			</tbody>
		</table>
		</br>
		<div class="col-sm-12 text-right">
			<button id="idBtnFinalizarTramite" class="btn btn-danger">Finalizar tr&aacute;mite</button>
		</div>
		<form id="formImpresionReporte" method="POST" target="_blank">
			<input type="hidden" id="idSolicitud" name="idSolicitud" value=""/>
			<input type="hidden" id="idTramite" name="idTramite" value=""/>
			<input type="hidden" id="tipoDocumento" name="tipoDocumento" value=""/>
		</form>
	</div>
</div>
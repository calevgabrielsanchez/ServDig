<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaRepLegal/altaRepLegal.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>1. Registrar representante legal</h4>
				<h5>Paso 1 de 1: Registra al representante legal de tu empresa</h5>
			</div>
		</div>
		<form class="form-horizontal" role="form" id="busquedaRFCForm">
			<p>Selecciona el tipo de poder que se le otorga:</p>
			<div class="form-group">
				<label class="col-sm-2"> 
					<input type="radio" name="idTipoPoder" value="1"  checked >Dominio
				</label> 
				<label class="col-sm-5"> 
					<input type="radio" name="idTipoPoder" value="3">Especial, para tr&aacute;mites ante el IMSS
				</label> 
				<label class="col-sm-5"> 
					<input type="radio" name="idTipoPoder" value="2">Administraci&oacute;n
				</label> 
				
			</div>
			
			<p>Ingresa el RFC de la empresa a representar</p>
			<div class="alert alert-danger" id="errorFormBusqueda" style="display:none"></div>
			<div class="alert alert-danger" id="errorNegocioRepLegal" style="display:none"></div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="rfcAltaRep">
					RFC<span class="required">*</span>:
				</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" name="rfcAltaRep" id="rfcAltaRep" value="" maxlength="12" placeholder="RFC" readonly="true"/>
				</div>
			</div>
			<div class="form-group" hidden="true">
				<div class="col-sm-7 text-right">
					<button type="button" class="btn btn-primary" id="buscarPersonaRepLegal" >Buscar</button>
				</div>
			</div>
			
			<div id="datosEmpresa" class="form-group" hidden="true">
				<label class="control-label col-sm-2">
					Raz&oacute;n social:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="razonSocial" id="razonSocialId" value="" disabled="true" />
				</div>
				
				<label class="control-label col-sm-2">
					Tipo sociedad:
				</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="tipoSociedad" id="tipoSociedadId" value="" disabled="true" />
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-sm-6 text-left">
				*Campos obligatorios
			</div>
			<div class="col-sm-6 text-right" id="botonesDiv" hidden="true">
				<!-- <button class="btn btn-default" >Cancelar</button> -->
				<button class="btn btn-primary" onclick="firmar();">Continuar</button>
			</div>
		</div>
	</div>
</div>

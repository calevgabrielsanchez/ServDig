<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/procesos.js" htmlEscape="true" />"></script>
<style>
	table tr.row_selected td {
		background-color: #F6F6F6;
	}
</style>
<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>4. Seleccionar giro de la empresa</h4>
				<h5>Paso 2 de 2: Indica los procesos de trabajo de la empresa</h5>
			</div>
		</div>
		<form class="form-horizontal" id="formProcesos1" name="formProcesos1" role="form" >
			<h5>Completa los siguientes campos:</h5>
			<div class="form-group">
					<label class="col-sm-4 control-label m-b-xs" for="giroActividad">
						Describe el giro o actividad<span class="required">*</span>:
					</label>
					<div class="col-sm-8 m-b-xs">
						<textarea id="giroActividad" name="giroActividad" class="form-control" rows="2" maxlength="300" placeholder="Ingresa la actividad que realiza la empresa o negocio. Por ejemplo: Comercializaci&oacute;n de art&iacute;culos importados, reparaci&oacute;n de veh&iacute;culos automotores, servicios contables, etc."></textarea>
						<span id="" class="error hiddenElement text-left"></span>
					</div>
			</div>
		</form>
		<h5>
			<label for="hdnValidaTablaProdcsServs">
			Productos elaborados o servicios prestados<span class="required">*</span>:
			</label>
		</h5>
		<div class="row">
			<div class="col-sm-12">
				<p align="justify">
					Enumera los bienes, mercanc&iacute;as o servicios que ofrece el negocio o empresa.
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<form id="idFrmTablaProdcsServs" class="form-horizontal" role="form">
					<table id="idTblProdcsServs" class="table table-bordered table-striped table-word-wrap-fixed"></table>
					<input type="hidden" id="idHdnValidaTablaProdcsServs" name="hdnValidaTablaProdcsServs" />
				</form>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12">
		<div class="panel-group ficha-collapse" id="idCollapseProdcsServs">
			<a data-parent="#idCollapseProdcsServs" data-toggle="collapse" href="#idPanelProdcsServs" aria-expanded="true" aria-controls="idPanelProdcsServs">
				<button type="button" class="btn btn-link" id="idLinkAgregarProdcsServs">
					<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Agregar otro producto o servicio
				</button>
			</a>
			<div class="panel-collapse collapse" id="idPanelProdcsServs">
				<div class="panel-body">
					<form id="idFrmAgregarProdcsServs" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="txtNomProdServ" class="col-sm-3 control-label" >
                                   Producto elaborado o servicio prestado<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="txtNomProdServ" name="txtNomProdServ"
									   class="form-control textClasificacion" value="" maxlength="50" placeholder="Ingresa el bien, mercanc&iacute;a o servicio que ofrece el negocio o empresa.">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12 text-right">
								<button id="idBtnAgregarProdcsServs" type="button" name="btnAgregarProdcsServs" class="btn btn-primary">Agregar</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		</div>
		</div>

		<form class="form-horizontal" id="formProcesos2" name="formProcesos2" role="form" >
		<h5>Ingresa los procesos iniciales, procesos intermedios y procesos finales:</h5>
			<div class="form-group">
					<label class="col-sm-4 control-label m-b-xs" for="procesosTrabajo">
						Procesos iniciales, procesos intermedios, procesos finales<span class="required">*</span>:
					</label>
					<div class="col-sm-8 m-b-xs">
						<textarea id="procesosTrabajo" name="procesosTrabajo" class="form-control" rows="10" placeholder="Describe los pasos que se siguen, para transformar los insumos o materia prima, en los productos de tu empresa o negocio, especificando el uso que se le da a las materias primas, as&iacute; como a la maquinaria, herramienta o equipo se&ntilde;alado.
Trat&aacute;ndose de empresas prestadoras de servicios, deber&aacute;s describir las actividades o pasos que se siguen para prestar los servicios, especificando el uso que se le da a la maquinaria, herramienta o equipo se&ntilde;alado." maxlength="1000"></textarea>
						<span class="error hiddenElement text-left"></span>
					</div>
			</div>
		</form>
		
		<div class="row">
			<div class="errorPage col-sm-6 text-left">
                <span class="required">*</span>Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
                <button class="btn btn-default" id="atrasProcesos">Atr&aacute;s</button>
                <button class="btn btn-primary" id="siguienteProcesos">Siguiente</button>
			</div>
		</div>
	</div>
</div>
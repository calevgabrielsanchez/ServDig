<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/materiales.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="alert alert-danger" id="idErrorFormMateriales" style="display:none"></div>
		<div class="row">
			<div class="col-sm-12">
				<h4>5. Registrar recursos materiales y humanos</h4>
				<h5>Paso 1 de 2: Registra los productos y materias primas que utilizas</h5>
			</div>
		</div>
		<h5>Completa los siguientes campos:</h5>
		<label for="hdnValidaTablaMateriasPrimas">Materias primas y materiales utilizados<span class="required">*</span>:</label>
		</br>
		<form id="idFrmTablaMateriasPrimas" class="form-horizontal" role="form">
			<table id="idTblMateriasPrimas" class="table table-bordered table-striped table-word-wrap-fixed"></table>
			<input type="hidden" id="idHdnValidaTablaMateriasPrimas" name="hdnValidaTablaMateriasPrimas" />
		</form>
		</br>
		<div class="panel-group ficha-collapse" id="idCollapseMateriasPrimas">
			<a data-parent="#idCollapseMateriasPrimas" data-toggle="collapse" href="#idPanelMateriasPrimas" aria-expanded="true" aria-controls="idPanelMateriasPrimas">
				<button type="button" class="btn btn-link" id="idLinkAgregarMateriaPrima">
					<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Agregar materia prima/materiales
				</button>
			</a>
			<div class="panel-collapse collapse" id="idPanelMateriasPrimas">
		    	<div class="panel-body">
		    		<form id="idFrmAgregarMateriasPrimas" class="form-horizontal" role="form">
		    			<div class="form-group">
		    				<label for="txtDesMateriaPrima" class="col-sm-3 control-label">
								Descripci&oacute;n<span class="required">*</span>:
							</label>
							<div id="idDivTxtAreaMateriasPrimas" class="col-sm-9">
								<textarea id="idTxtDesMateriaPrima" name="txtDesMateriaPrima" rows="3" style="height: 120px; width: 100%; margin-bottom: 15px;" class="form-control textClasificacion"></textarea>
							</div>
		    			</div>
	    				<div class="text-right">
	    					</br>
							<button type="button" id="idBtnAgregarMateriaPrima" name="btnAgregarMateriaPrima" class="btn btn-primary btn-sm">Agregar</button>
						</div>
	    			</form>
		      	</div>
		    </div>
		</div>
		<h4 class="subtitulo">Registra la maquinaria y equipo de transporte que utilizas</h4>
		<label for="hdnValidaTablaMaquinariaEquipo">Maquinaria y equipo utilizado<span class="required">*</span>:</label>
		<div class="row">
			<div class="col-sm-12">
				<p>
					Registra todas las m&aacute;quinas, herramientas o equipos que emplean para transformar los insumos o materias primas, 
					en los productos o servicios que provee la empresa o negocio.
				</p>
			</div>
		</div>
		</br>
		<form id="idFrmTablaMaquinariaEquipo" class="form-horizontal" role="form">
			<table id="idTblMaquinariaEquipo" class="table table-bordered table-striped table-word-wrap-fixed"></table>
			<input type="hidden" id="idHdnValidaTablaMaquinariaEquipo" name="hdnValidaTablaMaquinariaEquipo" />
		</form>
		</br>
		<div class="panel-group ficha-collapse" id="idCollapseMaquinariaEquipo">
			<a data-parent="#idCollapseMaquinariaEquipo" data-toggle="collapse" href="#idPanelMaquinariaEquipo" aria-expanded="true" aria-controls="idPanelMaquinariaEquipo">
				<button type="button" class="btn btn-link" id="idLinkAgregarMaquinariaEquipo">
					<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Agregar otro tipo de maquinaria
				</button>
			</a>
			<div class="panel-collapse collapse" id="idPanelMaquinariaEquipo">
		    	<div class="panel-body">
		    		<form id="idFrmAgregarMaquinariaEquipo" class="form-horizontal" role="form">
		    			<div class="form-group">
		    				<label for="txtDesMaquinariaEquipo" class="col-sm-3 control-label" >
								Nombre<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtDesMaquinariaEquipo" name="txtDesMaquinariaEquipo"
									class="form-control" value="" maxlength="30" placeholder="Ingresa la descripci&oacute;n">
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="txtCapacidadMaquinariaEquipo" class="col-sm-3 control-label" >
								Capacidad/Potencia:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtCapacidadMaquinariaEquipo" name="txtCapacidadMaquinariaEquipo"
									class="form-control" value="" maxlength="25" placeholder="Ingresa la capacidad/potencia">
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="sltTipoMaquinaria" class="col-sm-3 control-label" >
								Tipo maquinaria<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<combo:creaCombo idHtml="sltTipoMaquinaria"	idHtmlContenedor="idFrmAgregarMaquinariaEquipo"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoMaquinariaEquipo" mostrarSoloActivos="true" cssClassname="form-control"/>
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="txtUsoMaquinariaEquipo" class="col-sm-3 control-label" >
								Uso<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtUsoMaquinariaEquipo" name="txtUsoMaquinariaEquipo"
									class="form-control" value="" maxlength="100" placeholder="Ingresa el uso del transporte">
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="txtUnidadesMaquinariaEquipo" class="col-sm-3 control-label" >
								Unidades(num&eacute;rico)<span  class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtUnidadesMaquinariaEquipo" name="txtUnidadesMaquinariaEquipo"
									class="form-control" value="" maxlength="5" placeholder="Ingresa la cantidad de unidades">
							</div>
		    			</div>
	    				<div class="text-right">
	    					</br>
							<button type="button" id="idBtnAgregarMaquinariaEquipo" name="btnAgregarMaquinariaEquipo" class="btn btn-primary btn-sm">Agregar</button>
						</div>
	    			</form>
		      	</div>
		    </div>
		</div>
		<h5>Registra el equipo de transporte:</h5>
		<div class="form-group">
				<label class="col-sm-6 control-label m-b-xs" for="hdnValidaTablaEquipoTransporte">
					¿Cuentas con equipo de transporte?<span id="idSpanRequiredTablaTransporte" class="required" hidden="true">*</span>:
				</label>
				<div class="radio col-sm-6">
				  <label>
				    <input id="siCuetaConTransporte" type="radio" name="radio-01" value="1" checked="checked"> Si
				  </label>
				  <label>
				    <input id="noCuetaConTransporte" type="radio" name="radio-01" value="0" checked="checked"> No
				  </label>
				</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<p>
					Registra el equipo de transporte que emplean o utilizan para desarrollar las actividades de la empresa o negocio. 
					Por ejemplo, para acopio, traslado, entrega, distribuci&oacute;n o venta.
				</p>
			</div>
		</div>
		</br>
		<form id="idFrmTablaEquipoTransporte" class="form-horizontal" role="form">
			<table id="idTblEquipoTransporte" class="table table-bordered table-striped table-word-wrap-fixed"></table>
			<input type="hidden" id="idHdnValidaTablaEquipoTransporte" name="hdnValidaTablaEquipoTransporte" />
		</form>
		</br>
		<div class="panel-group ficha-collapse" id="idCollapseEquipoTransporte">
			<div id="idDivHiddenPanelEquipoTransporte" hidden="true">
				<a data-parent="#idCollapseEquipoTransporte" data-toggle="collapse" href="#idPanelEquipoTransporte" aria-expanded="true" aria-controls="idPanelEquipoTransporte">
					<button type="button" class="btn btn-link" id="idLinkAgregarEquipoTransporte">
						<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Agregar otro tipo de equipo de transporte
					</button>
				</a>
			</div>
			<div class="panel-collapse collapse" id="idPanelEquipoTransporte">
		    	<div class="panel-body">
		    		<form id=idFrmAgregarEquipoTransporte class="form-horizontal" role="form">
		    			<div class="form-group">
		    				<label for="txtDesEquipoTransporte" class="col-sm-3 control-label" >
								Nombre<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtDesEquipoTransporte" name="txtDesEquipoTransporte" 
									class="form-control" value="" maxlength="30" placeholder="Ingresa la descripci&oacute;n">
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="txtCapacidadEquipoTransporte" class="col-sm-3 control-label" >
								Capacidad/Potencia<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtCapacidadEquipoTransporte" name="txtCapacidadEquipoTransporte" 
									class="form-control" value="" maxlength="25" placeholder="Ingresa la capacidad/potencia">
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="sltTipoCombustibleEquipoTransporte" class="col-sm-3 control-label" >
								Tipo combustible<span class=+"required">*</span>:
							</label>
							<div class="col-sm-9">
								<combo:creaCombo idHtml="sltTipoCombustibleEquipoTransporte" idHtmlContenedor="idFrmAgregarEquipoTransporte"
										entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible" mostrarSoloActivos="true" cssClassname="form-control"/>
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="txtUsoEquipoTransporte" class="col-sm-3 control-label" >
								Uso<span  class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtUsoEquipoTransporte" name="txtUsoEquipoTransporte" 
									class="form-control" value="" maxlength="40" placeholder="Ingresa el uso del transporte">
							</div>
		    			</div>
		    			<div class="form-group">
		    				<label for="txtUnidadesEquipoTransporte" class="col-sm-3 control-label" >
								Unidades(num&eacute;rico)<span class="required">*</span>:
							</label>
							<div class="col-sm-9">
								<input type="text" id="idTxtUnidadesEquipoTransporte" name="txtUnidadesEquipoTransporte"
									class="form-control" value="" maxlength="5" placeholder="Ingresa la cantidad de unidades">
							</div>
		    			</div>
	    				<div class="text-right">
	    					</br>
							<button type="button" id="idBtnAgregarEquipoTransporte" name="btnAgregarEquipoTransporte" class="btn btn-primary btn-sm">Agregar</button>
						</div>
	    			</form>
		      	</div>
		    </div>
		</div>
		<label for="hdnValidaCuentaConTransPropio">Selecciona la opci&oacute;n de distribuci&oacute;n o entrega de mercanc&iacute;as<span id="idSpanRequiredChkTRansporte" class="required" hidden="true">*</span>:</label>
		<div class="form-group">
			<label class="col-sm-6"> 
				<input type="checkbox" id="idChkConTransportePropio" name="chkConTransportePropio">Con transporte propio
			</label>
			<label class="col-sm-6"> 
				<input type="checkbox" id="idChkConTransporteAjeno">Con transporte ajeno
			</label>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<form id="idFrmChkEquipoTransportePropio" class="form-horizontal" role="form">
					<input type="hidden" id="idHdnValidaCuentaConTransPropio" name="hdnValidaCuentaConTransPropio" />
				</form>
			</div>
			<div class="col-sm-6">
				<form id="idFrmChkEquipoTransporteAjeno" class="form-horizontal" role="form">
					<input type="hidden" id="idHdnValidaCuentaConTransAjeno" name="hdnValidaCuentaConTransAjeno" />
				</form>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-12"> 
				<input type="checkbox" id="idChkNoDistribuye">No se distribuye, ni se entrega
			</label>
		</div>
		<div class="form-group">
			<label class="col-sm-12"> 
				<input type="checkbox" id="idChkServInstalacion">Servicios de instalaci&oacute;n, reparaci&oacute;n o mantenimiento a terceros
			</label>
		</div>
		<div class="row">
			<div class="col-sm-6 text-left">
				<span id="labelCamposObligatoriosGeneral" class="required" style="color: red;">*</span>Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
				<button id="idBtnAtrasMateriales" class="btn btn-default">Atr&aacute;s</button>
				<button id="idBtnSiguienteMateriales" class="btn btn-primary">Siguiente</button>
			</div>
		</div>
	</div>
</div>
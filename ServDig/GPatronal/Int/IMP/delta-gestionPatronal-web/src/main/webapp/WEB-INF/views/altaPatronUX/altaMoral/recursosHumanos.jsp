<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/recursosHumanos.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="alert alert-danger" id="idErrorFormRecursosHumanos" style="display:none"></div>
		<div class="row">
			<div class="col-sm-12">
				<h4>5. Registrar recursos materiales y humanos</h4>
				<h5>Paso 2 de 2: Registra tus recursos humanos por tipo de actividad</h5>
			</div>
		</div>
		<label for="hdnValidaTablaRecursosHumanos">Completa los siguientes campos<span class="required">*</span>:</label>
		<div class="row">
			<div class="col-sm-12">
				<p>
					Registra el n&uacute;mero de trabajadores con que cuenta la empresa, por grupos de oficio u ocupaci&oacute;n, describiendo el trabajo que desarrollan.
				</p>
			</div>
		</div>
		</br>
		<form id="idFrmTablaRecursosHumanos" class="form-horizontal" role="form">
			<table id="idTblRecursosHumanos" class="table table-bordered table-striped table-word-wrap-fixed"></table>
			<input type="hidden" id="idHdnValidaTablaRecursosHumanos" name="hdnValidaTablaRecursosHumanos" />
		</form>
		</br>
		<div class="panel-group ficha-collapse" id="idCollapseRecursosHumanos">
				<a data-parent="#idCollapseRecursosHumanos" data-toggle="collapse" href="#idPanelRecursosHumanos" aria-expanded="true" aria-controls="idPanelRecursosHumanos">
					<button type="button" class="btn btn-link" id="idLinkAgregarRecursosHumanos">
						<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>Agregar otro grupo de personal
					</button>
				</a>
				<div class="panel-collapse collapse" id="idPanelRecursosHumanos">
			    	<div class="panel-body">
			    		<form id="idFrmAgregarRecursosHumanos" class="form-horizontal" role="form">
			    			<div class="form-group">
			    				<div class="form-group">
				    				<label for="txtNumTrabajadores" class="col-sm-3 control-label" >
										No. Trabajadores(num&eacute;rico):<span class="required">*</span>:
									</label>
									<div class="col-sm-9">
										<input type="text" id="idTxtNumTrabajadores" name="txtNumTrabajadores"
											class="form-control textClasificacion" value="" maxlength="5" placeholder="Ingresa el n&uacute;mero de trabajadores">
									</div>
				    			</div>
			    			</div>
			    			<div class="form-group">
			    				<div class="form-group">
				    				<label for="txtOficioOcupacion" class="col-sm-3 control-label" >
										Oficio u ocupaci&oacute;n:<span class="required">*</span>:
									</label>
									<div class="col-sm-9">
										<input type="text" id="idTxtOficioOcupacion" name="txtOficioOcupacion"
											class="form-control textClasificacion" value="" maxlength="50" placeholder="Ingresa el oficio u ocupaci&oacute;n">
									</div>
				    			</div>
			    			</div>
		    				<div class="text-right">
		    					</br>
								<button type="button" id="idBtnAgregarRecursosHumanos" name="btnAgregarRecursosHumanos" class="btn btn-primary btn-sm">Agregar</button>
							</div>
		    			</form>
			      	</div>
			    </div>
			</div>
		
		<div class="row">
			<div class="col-sm-6 text-left">
				<span id="labelCamposObligatoriosGeneral" class="required" style="color: red;">*</span>Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
				<button id="idBtnAtrasRecursosHumanos" class="btn btn-default">Atr&aacute;s</button>
				<button id="idBtnSiguienteRecursosHumanos" class="btn btn-primary">Siguiente</button>
			</div>
		</div>
	</div>
</div>
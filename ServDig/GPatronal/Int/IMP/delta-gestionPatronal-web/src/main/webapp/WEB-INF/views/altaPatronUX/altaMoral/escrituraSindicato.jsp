<%@ include file="../../general/taglibs.jsp"%>	
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/escrituraSindicato.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>7. Registrar escritura constitutiva o sindicato</h4>
				<h5>Paso 1 de 1: Registra socios o sindicato (seg&uacute;n aplique)</h5>
				<p align="justify">Selecciona la opci&oacute;n de acuerdo a la constituci&oacute;n de tu empresa y llena los campos requeridos.</p>
			</div>
		</div>
		<div id="selectEscrituraSindicato" class="row">
			<div class="col-sm-12" id="contenedorSelects">
				<div class="well" style="background-color: white;">
					<div id="listaTramitesBeneficiarios" title="Tr&aacute;mites">
						<ol id="selectable">
							<li class="ui-widget-content" id="1">Escritura constitutiva</li>
							<li class="ui-widget-content" id="2">Sindicato</li>
							<li class="ui-widget-content" id="3">Contrato social (Sociedad de acci&oacute;n simplificada)</li>
						</ol>
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="escritura" style="display:none">
			<div class="col-sm-12">
                <div id="divFormEscritura">
                    <form class="form-horizontal" role="form" id="formEscritura">
                        <div class="form-group">
                            <label class="col-sm-2 contro-label" for="numEscritura">N&uacute;mero de escritura<span class="required">*</span>:</label>
                            <div class="col-sm-4">
                                <input type="text" maxlength="12" value="" placeholder="Ingresa n&uacute;mero de escritura" class="numericoSinPunto form-control" id="numEscritura" name="numEscritura"/>
                            </div>
                            <label class="col-sm-2 contro-label" for="numNotaria">N&uacute;mero de notar&iacute;a o corredur&iacute;a<span class="required">*</span>:</label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="Ingresa n&uacute;mero de escritura"  maxlength="15" class="caracterNotaria form-control" id="numNotaria" name="numNotaria"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 contro-label" for="lugarExpedicion.entidadFederativa.clave">Estado<span class="required">*</span>:</label>
                            <div class="col-sm-4">
                                <combo:creaCombo
                                    idHtml="lugarExpedicion.entidadFederativa.clave"
                                    idHtmlContenedor="formEscritura"
                                    entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
                                    mostrarSoloActivos="true" cssClassname="form-control"
                                />

                            </div>
                            <label class="col-sm-2 contro-label" for="lugarExpedicion.clave">Municipio o alcald&iacute;a<span class="required">*</span>:</label>
                            <div class="col-sm-4">
                                <combo:creaCombo
                                entidad="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio"
                                idHtml="lugarExpedicion.clave"
                                entidadPadre="id.cveEnt"
                                idHtmlPadre="lugarExpedicion.entidadFederativa.clave"
                                idHtmlContenedor="formEscritura"
                                mostrarSoloActivos = "false"
                                cssClassname="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 contro-label" for="fechaExpedicion">
                                Fecha de expedici&oacute;n:<span class="required">*</span>
                            </label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="DD/MM/AAAA" readOnly="readOnly" class="form-control" id="fechaExpedicion" name="fechaExpedicion"/>
                            </div>
                            <label class="col-sm-2 contro-label" for="folioMercantil">Folio mercantil:</label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="Folio" class="asterisco form-control" maxlength="13" id="folioMercantil" name="folioMercantil"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 contro-label" for="seccion">Secci&oacute;n:</label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="Secci&oacute;n" class="caracterEsp form-control" maxlength="18" id="seccion" name="seccion"/>
                            </div>
                            <label class="col-sm-2 contro-label" for="partida">Partida:</label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="Partida"  class="caracterEsp form-control" maxlength="15" id="partida" name="partida"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 contro-label" for="volumen">Volumen:</label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="Volumen" class="caracterEsp form-control" maxlength="15" id="volumen" name="volumen"/>
                            </div>
                            <label class="col-sm-2 contro-label" for="foja">Foja:</label>
                            <div class="col-sm-4">
                                <input type="text" value="" placeholder="Foja" maxlength="15" class="caracterEsp form-control" id="foja" name="foja"/>
                            </div>
                        </div>
                    </form>
                </div>
				<h5>Registrar socio(s)</h5>
				<hr class="red">
				<div class="row">
					<div class="col-sm-12">
						<p align="justify">
							Ingresa los datos de tu socio, en caso de que sea persona f&iacute;sica deber&aacute;s capturar RFC y CURP. Si es persona moral, solamente RFC.
						</p>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12" id="listadoSocios"></div>
				</div>
				<form class="form-horizontal" role="form" id="formBusquedaSocio">
					<h4>Socio <span id="numeroSocioActual">1</span></h4>
					<div class="form-group">
						<label class="control-label col-sm-2" for="rfc">
							RFC<span class="required">*</span>:
						</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="rfc" id="rfc" value="" maxlength="13" placeholder="RFC"/>
						</div>
						<label class="control-label col-sm-2" for="curp">
							CURP<span class="required" id="curpRequired" style="display:none">*</span>:
						</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" name="curp" value="" maxlength="18" placeholder="CURP"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12 text-right">
							<button type="button" class="btn btn-primary" id="buscarSocio">Buscar</button>
						</div>
					</div>
				</form>
				<form class="form-horizontal" role="form" id="formResultadoFisica" style="display:none">
					<div class="alert alert-info">Para a&ntilde;adir este socio a tu registro patronal presiona Aceptar. Si los datos mostrados
					no coinciden realiza una nueva busqueda.</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="nombre">
							Nombre (s):
						</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="nombre" name="nombre" disabled="disabled" value="Mario" placeholder="RFC"/>
						</div>
						<label class="control-label col-sm-2" for="primerApellido">
							Primer apellido:
						</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="primerApellido" name="primerApellido"  disabled="disabled" value="Teran" placeholder="CURP"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="segundoApellido">
							Segundo apellido:
						</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" id="segundoApellido" name="segundoApellido" disabled="disabled" value="Blanco" placeholder="RFC"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12 text-right">
							<button type="button" class="btn btn-primary" id="anadirSocioFisica"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Aceptar</button>
						</div>
					</div>
				</form>
				<form class="form-horizontal" role="form" id="formResultadoMoral" style="display:none">
					<div class="alert alert-info">Para a&ntilde;adir este socio a tu registro patronal presiona Aceptar. Si los datos mostrados
					no coinciden realiza una nueva busqueda.</div>
					<div class="form-group">
						<label class="control-label col-sm-6" for="razonSocial">
							Raz&oacute;n o denominaci&oacute;n social:
						</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="razonSocial" name="razonSocial" disabled="disabled" value="Mario" placeholder="RFC"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-6" for="tipoSociedad.descripcion">
							Tipo de sociedad:
						</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="tipoSociedad.descripcion" name="tipoSociedad.descripcion" disabled="disabled" value="Blanco" placeholder="RFC"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12 text-right">
							<button type="button" class="btn btn-primary" id="anadirSocioMoral"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Aceptar</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="row" id="sindicato" style="display:none">
			<div class="col-sm-12">
				<form class="form-horizontal" role="form" id="formSindicato">
					<div class="form-group">
						<label class="col-sm-6 contro-label" for="numReferenciadocRegistro">N&uacute;mero de referencia de registro<span class="required">*</span>:</label>
						<div class="col-sm-6">
							<input type="text" value="" placeholder="Ingresa n&uacute;mero de referencia" class="numerico form-control" maxlength="20" id="numReferenciadocRegistro" name="numReferenciadocRegistro"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-6 contro-label" for="fechaRegistro">Fecha de registro<span class="required">*</span>:</label>
						<div class="col-sm-6">
							<input type="text" value=""  readOnly="readOnly"  placeholder="DD/MM/AAAA" class="form-control" id="fechaRegistro" name="fechaRegistro"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-6 contro-label" for="autoridadLaboral">Autoridad laboral<span class="required">*</span>:</label>
						<div class="col-sm-6">
							<input type="text" value="" placeholder="Ingresa autoridad laboral" class="autoridad form-control" maxlength="100" id="autoridadLaboral" name="autoridadLaboral"/>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 text-left">
				*Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
				<button class="btn btn-default" id="cancelarEscritura">Atr&aacute;s</button>
				<button class="btn btn-primary" id="guardarEscritura">Siguiente</button>
			</div>
		</div>
	</div>
</div>
<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/clasificador/clasificadorComponent.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/altaMoral/giroEmpresa.js" htmlEscape="true" />"></script>
<c:set var="today" value="<%=new java.util.Date()%>" />
<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>4. Seleccionar giro de la empresa</h4>
				<h5>Paso 1 de 2: Especifique la actividad de tu empresa</h5>
			</div>
		</div>
		<form class="form-horizontal" id="formFechas" role="form">
            <div class="alert alert-danger" id="errorFormGiroEmpresa" style="display:none"></div>
			<div class="form-group">
					<label class="col-sm-3 control-label" for="fecPresentacion">
						Fecha de presentaci&oacute;n:
					</label>
					<div class="col-sm-3 m-b-xs">
						<input type="text" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${today}" />" class="form-control" id="fecPresentacion" readOnly="readonly" name="fecPresentacion"/>
					</div>
					<label class="col-sm-3 control-label" for="fecEfecto">
						Fecha a partir de la cual surte efecto este tr&aacute;mite<span class="required">*</span>:
					</label>
					<div class="col-sm-3 m-b-xs">
						<input type="text" readOnly="readonly" class="form-control" id="fecEfecto" name="fecEfecto" placeholder="DD/MM/AAAA"/>
					</div>
			</div>
			<div class="form-group">
					<label class="col-sm-6 control-label" for="prestaServicioPersonal">
						Presta servicios de personal(outsourcing)<span class="required">*</span>:
					</label>
					<div class="radio col-sm-6">
					  <label>
					    <input type="radio" name="indPrestaServicioPersonal"  id="serviciosPersonalSi" value="1"> Si
					  </label>
					  <label>
						  <div>
							  <input type="radio" name="indPrestaServicioPersonal" id="serviciosPersonalNo" value="0"> No
							  <input type="hidden" id="prestaServicioPersonal" name="prestaServicioPersonal" />
						  </div>
					  </label>
					</div>
			</div>
			<div class="form-group" id="divCentrosTrabajo" style="display:none">
					<label class="col-sm-6 control-label m-b-xs" for="numCentrosTraba">
						Numero de centros de trabajo*:
					</label>
					<div class="col-sm-6 m-b-xs">
						<input type="text" id="numCentrosTraba" placeholder="000" maxlength="3" name="numCentrosTraba" class="form-control"/>
                        <input type="hidden" id="validadoNumCentrosTraba" name="validadoNumCentrosTraba" />
					</div>
			</div>
		</form>
		<div class="row">
			<div class="col-sm-12">
				<label for="clasificacionElegida">
					<p align="justify"><strong>Busca y selecciona la fracci&oacute;n del Cat&aacute;logo de actividades que mejor describa el giro de tu empresa<span class="required">*</span></strong></p>
				</label>
				<p align="justify">
					Si deseas conocer todas las fracciones que conforman el Cat&aacute;logo de actividades, consulta el
					<a href="http://www.imss.gob.mx/sites/all/statics/pdf/reglamentos/RecaudacionyFiscalizacion.pdf" target="_blank">
						Reglamento de la Ley del Seguro Social en Materia de Afiliaci&oacute;n, Clasificaci&oacute;n de Empresas, Recaudaci&oacute;n y Fiscalizaci&oacute;n.
					</a>
				</p>
			</div>
		</div>
		<div id="contenedorClasificador"></div>
		<form id="formClasificacionElegida" name="formClasificacionElegida" role="form">
			<input type="hidden" id="clasificacionElegida" name="clasificacionElegida" />
		</form>
		<div class="row">
			<div class="errorPage col-sm-6 text-left">
				<span class="required">*</span>Campos obligatorios
			</div>
			<div class="col-sm-6 text-right">
				<button class="btn btn-default" id="atrasGiroEmpresa">Atr&aacute;s</button>
				<button class="btn btn-primary" id="siguienteGiroEmpresa">Siguiente</button>
			</div>
		</div>
	</div>
</div>
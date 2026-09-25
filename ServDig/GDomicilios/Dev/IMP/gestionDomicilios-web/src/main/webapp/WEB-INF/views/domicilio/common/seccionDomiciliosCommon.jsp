<%@ include file="../../general/taglibs.jsp"%>

<style>
	.empty-state .titulo {
	    font-size: 15px !important;
	    margin-bottom: 20px;
	}
</style>

<input type="hidden" id="hdnIdTipoValidacionDomicilio" value="${idTipoValidacionDomicilio}"/>

<!-- Sección DOMICILIO FISCAL -->
<c:if test="${not empty domicilioFiscal}">
	<fieldset>
		<legend><strong>&nbsp;Domicilio Fiscal</strong></legend>
		
		<div class="container-fluid empty-state">
			<div class="row">
				<div class="col-xs-12"><div class="row">
					<div class="col-xs-4"><strong>Calle </strong></div>
					<div class="col-xs-7">${domicilioFiscal.calle}</div>
				</div></div>
			</div>
			<div class="row">
				<div class="col-xs-12"><div class="row">
					<div class="col-xs-4"><strong>No. Exterior </strong></div>
					<div class="col-xs-7">${domicilioFiscal.numExterior1}</div>
				</div></div>
			</div>	
			<div class="row">
				<div class="col-xs-12"><div class="row">
					<div class="col-xs-4"><strong>No. Interior </strong></div>
					<div class="col-xs-7">${domicilioFiscal.numInterior}</div>
				</div></div>
			</div>
			<div class="row">
				<div class="col-xs-12"><div class="row">
					<div class="col-xs-4"><strong>C.P. </strong></div>
					<div class="col-xs-7">${domicilioFiscal.codigoPostal.codigoPostal}</div>
				</div></div>
			</div>
			<div class="row">
				<div class="col-xs-12"><div class="row">
					<div class="col-xs-4"><strong>Municipio </strong></div>
					<div class="col-xs-7">${domicilioFiscal.asentamiento.localidad.municipio.nombre}</div>
				</div></div>
			</div>	
			<div class="row">
				<div class="col-xs-12"><div class="row">
					<div class="col-xs-4"><strong>Entidad Federativa </strong></div>
					<div class="col-xs-7">${domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}</div>
				</div></div>
			</div>	
		</div>		
	</fieldset>
</c:if>

<!-- Sección DOMICILIOS PARTICULARES -->
<c:if test="${not empty listaDomiciliosParticulares}">
<fieldset>
	<legend><strong>&nbsp;Domicilios Particulares</strong></legend>

	<table id="tblDomiciliosParticulares" style="width: 100%;" class="table table-striped table-bordered" 
		cellpadding="0" cellspacing="0" border="0">	
		<thead>
			<tr>
				<th>Calle</th>					
				<th>No. Exterior</th>
				<th>No. Interior</th>
				<th>C.P.</th>
				<th>Municipio</th>
				<th>Entidad Federativa</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listaDomiciliosParticulares}" var="domicilio" varStatus="indice">
				<tr>
					<td align="center">${domicilio.calle}</td>
					<td align="center">${domicilio.numExterior1}</td>
					<td align="center">${domicilio.numInterior}</td>
					<td align="center">${domicilio.codigoPostal.codigoPostal}</td>
					<td align="center">${domicilio.asentamiento.localidad.municipio.nombre}</td>
					<td align="center">${domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}</td>
				</tr>
				</c:forEach>
		</tbody>
	</table>			
</fieldset>
</c:if>
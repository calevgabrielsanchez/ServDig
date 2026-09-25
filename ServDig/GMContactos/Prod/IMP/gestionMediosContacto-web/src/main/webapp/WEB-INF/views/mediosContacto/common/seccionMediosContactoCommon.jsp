<%@ include file="../../general/taglibs.jsp"%>

<style>
	.empty-state .titulo {
	    font-size: 15px !important;
	    margin-bottom: 20px;
	}
</style>

<input type="hidden" id="hdnIdTipoValidacionMedios" value="${idTipoValidacionMedios}"/>

<!-- Sección MEDIOS C. FISCALES -->
<c:if test="${not empty listaMediosContactoFiscales}">
<fieldset>
	<legend><strong>&nbsp;Medios de Contacto Fiscales</strong></legend>

	<table id="tblMediosContactoFiscales" style="width: 100%;" class="table table-striped table-bordered" 
		cellpadding="0" cellspacing="0" border="0">	
		<thead>
			<tr>
				<th>Tipo medio</th>					
				<th>Medio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listaMediosContactoFiscales}" var="medioF" varStatus="indice">
				<tr>
					<td align="center">${medioF.tipoMedioContacto.descripcion}</td>
					<td align="center">${medioF.desFormaContacto}</td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
</fieldset>
</c:if>

<!-- Sección MEDIOS C. PARTICULARES -->
<c:if test="${not empty listaMediosContactoParticulares}">
<fieldset>
	<legend><strong>&nbsp;Medios de Contacto Particulares</strong></legend>

	<table id="tbltblMediosContactoParticulares" style="width: 100%;" class="table table-striped table-bordered" 
		cellpadding="0" cellspacing="0" border="0">	
		<thead>
			<tr>
				<th>Tipo medio</th>					
				<th>Medio</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listaMediosContactoParticulares}" var="medioP" varStatus="indice">
				<tr>
					<td align="center">${medioP.tipoMedioContacto.descripcion}</td>
					<td align="center">${medioP.desFormaContacto}</td>
				</tr>
				</c:forEach>
		</tbody>
	</table>
</fieldset>
</c:if>
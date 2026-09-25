<%@ include file="../../general/taglibs.jsp"%>

<legend class="separadorseccion" style="width:920px">
	<spring:message code="titulo.centro.trabajo"/>
</legend>

<table style="border: none !important;">
	<tr>
		<td style="border: none !important;">
			<input type="button" class="mboton" id="btnUbicarDomicilio" value="Ubicar Domicilio" onclick="fnUbicarDomicilio()"/>
		</td>
	</tr>
</table>

<table style="width: 100%; border: none">
	<tr>
		<td class="label_patrones" width=100 height=35>																												
			<spring:message code="label.calle"/>
		</td>			
		<td class="label_patrones_data" width=200>
			<form:input  readonly="true" path="cntroTrabajo.vialidadPrimaria.nombre" maxlength="14"/> 
			<form:hidden path="cntroTrabajo.vialidadPrimaria.clave"/>
			<form:hidden path="cntroTrabajo.vialidadPrimaria.tipoVialidad.clave"/>
		</td>						
		<td class="label_patrones" width=100 height=35>
			N&uacute;mero Exterior
		</td>			
		<td class="label_patrones_data" width=200 height=35>
			<form:input  readonly="true" path="cntroTrabajo.numExterior1" maxlength="14" /> 					
		</td>																		
		<td class="label_patrones" width=100 height=35>												
			Letra Exterior
		</td>			
		<td class="label_patrones_data" width=200 height=35>
			<form:input  readonly="true" path="cntroTrabajo.numExteriorAlf" maxlength="14"/> 					
		</td>	
	</tr>
	<tr>
		<td class="label_patrones" width=100>											
			N&uacute;mero Interior
		</td>			
		<td class="label_patrones_data" width=200>
			<form:input  readonly="true" path="cntroTrabajo.numInterior" maxlength="14"/> 					
		</td>								
		<td class="label_patrones" width=100 height=35>												
			Letra Interior
		</td>			
		<td class="label_patrones_data" width=200 height=35>
			<form:input  readonly="true" path="cntroTrabajo.numInteriorAlf" maxlength="14"/> 					
		</td>
		<td class="label_patrones" width=100 height=35>
			Referencia Primaria
		</td>			
		<td class="label_patrones_data" width=200 height=35>
			<form:input readonly="true" path="cntroTrabajo.vialidadReferenciaPrimaria.nombre" maxlength="14"/> 									
			 <form:hidden path="cntroTrabajo.vialidadReferenciaPrimaria.clave"/>
			 <form:hidden path="cntroTrabajo.vialidadReferenciaPrimaria.tipoVialidad.clave"/>
		</td>
	</tr>
	<tr>
		<td class="label_patrones"  width=100 height=35>										
			Referencia Secundaria
		</td>
		<td class="label_patrones_data"  width=200 height=35>
			<form:input readonly="true" path="cntroTrabajo.vialidadReferenciaSecundaria.nombre" maxlength="14"/> 
				 <form:hidden path="cntroTrabajo.vialidadReferenciaSecundaria.clave"/>
				 <form:hidden path="cntroTrabajo.vialidadReferenciaSecundaria.tipoVialidad.clave"/>
		</td>		
		<td class="label_patrones"  width=100 height=35>										
			Referencia Posterior
		</td>
		<td class="label_patrones_data"  width=200 height=35>
			<form:input readonly="true" path="cntroTrabajo.vialidadReferenciaPosterior.nombre" maxlength="14"/> 
			 <form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.clave"/>
			 <form:hidden path="cntroTrabajo.vialidadReferenciaPosterior.tipoVialidad.clave"/>
		</td>										
		<td class="label_patrones" width=100>												
			Asentamiento
		</td>			
		<td class="label_patrones_data" width=200>
			<form:input  readonly="true" path="cntroTrabajo.asentamiento.nombre" maxlength="14"/> 
			<form:hidden path="cntroTrabajo.asentamiento.clave"/>
		</td>
	</tr>
	<tr>
		<td class="label_patrones" width=100 height=35>												
			Localidad
		</td>			
		<td class="label_patrones_data" width=200 height=35>
			<form:input  readonly="true" path="cntroTrabajo.asentamiento.localidad.nombre" maxlength="14" /> 
			<form:hidden path="cntroTrabajo.asentamiento.localidad.clave"/>
							
		</td>							
		<td class="label_patrones" width=100>										
			Municipio
		</td>										
		<td class="label_patrones_data" width=200>
			<form:input  readonly="true" path="cntroTrabajo.asentamiento.localidad.municipio.nombre"/> 
			<form:hidden path="cntroTrabajo.asentamiento.localidad.municipio.clave"/>
		</td>
		<td class="label_patrones" width=100 height=35>
			Entidad Federativa
		</td>			
		<td class="label_patrones_data" width=200 height=35>
			<form:input  readonly="true" path="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.nombre" maxlength="14"/> 
		 	<form:hidden path="cntroTrabajo.asentamiento.localidad.municipio.entidadFederativa.clave"/>
		</td>
	</tr>
	<tr>
		<td  class="label_patrones" width=100 height=35>
			C&oacute;digo Postal										
		</td>
		<td class="label_patrones_data" width=200 height=35>
			<form:input readonly="true" path="cntroTrabajo.codigoPostal.codigoPostal" maxlength="14"/>
		</td>																																					
		<td class="label_patrones" width=100 height=35>																										
			Tipo de Vialidad
		</td>			
		<td class="label_patrones_data" width=200 height=35>														
			<form:input  readonly="true" path="cntroTrabajo.vialidadPrimaria.tipoVialidad.descripcion" maxlength="14" /> 					
		</td>																			
	</tr>							
</table>

<legend class="separadorseccion" style="width:920px">
	<spring:message code="label.contacto.centro.trabajo"/>
</legend>
<center>
	<div style="width: 500px !important; " align="center">
		<table id="gridMediosContactoAltaCentroTrabajo" 
			style="width: 500px !important; vertical-align: top;">
			<thead>
			</thead>
			<tbody style="width: 500px !important;">
			</tbody>
			<tfoot></tfoot>
		</table>
	</div>
</center>
<center>
	<table style="width: 500px !important; border: none !important;" >
		<tr>
			<td style="border: none !important;">
				<input type="button" 
				onclick="fnInicializarDialogoAgregarCentroTrabajo();"
				class="mbutton" value="Agregar"
				style="font-size: .8em !important; color: white !important;">
			
				<input type="button"
				onclick="fnAbrirDialogoEliminarMedioContacto('fromRLAltaAgregar');"
				class="mbutton" value="Eliminar"
				style="font-size: .8em !important; color: white !important;">
			
				<input type="button"
				onclick="fnAbrirDialogoModificarMedioContacto('fromRLAltaAgregar');"
				class="mbutton" value="Modificar"
				style="font-size: .8em !important; color: white !important;">
				
			</td>
		</tr>
	</table>
</center>
<div id="domicilioLocaliza"></div>
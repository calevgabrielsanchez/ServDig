<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
</style>
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/jquery.formatCurrency-1.4.0.pack.js"></script>

<div id="wrapperData" style="background-color: white !important; width: 100%;border: medium; table-layout: fixed; ">
 <form action="" method="post" id="formCorre">
 	<input type="hidden" id="regPatHidden" value="">
 	<input type="hidden" id="guardadoHidden" value="0">
 	<input type="hidden" id="idStatusHidden" value="0">
 	<br />
 	<div class="menu_principal" style="height: 2em !important;">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul style="height: 1em !important;">
								<li><a> CORRECCI&Oacute;N </a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<br />
 	<table width="100%" border="0" align="left" class="tablaverde2">
 	<tr>
 	<td width="90%">
	<table >
 		<tr valign="middle" class="par">
  			<td align="center" width="15%" class="etiqueta2">Tipo</td>
  			<td align="center" width="30%" class="etiqueta2">Origen</td>
  			<td align="center" width="10%" class="etiqueta2">Folio</td>
  			<td> </td>
  			<td></td>
  			<td align="center" width="25%" class="etiqueta2">Criterio de Selección</td>
  			<td>Del: </td>
  			<td><input type="text" style="text-align: center;" size="15" maxlength="10" id="dtpGPeriodoDel" class="etiqueta2" name="dtpGPeriodoDel" onfocus="setMensaje('dtpGPeriodoDel')" disabled="disabled" onkeypress="validar(event, 'dtpGPeriodoDel')" onchange="if(validafechaSistema('dtpGPeriodoDel')){validar(event, 'dtpGPeriodoDel');inhabilitar(event, 'dtpGPeriodoDel');}"><label for="dtpGPeriodoDel" > </label></td>
  			
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="left" width="15%">
  				<combo:creaCombo  
  								entidad="mx.gob.imss.ctirss.correccion.model.CgcCatTipo" 
  								idHtml="cgcCatTipo.idTipo"
  								idHtmlContenedor="formCorre" param="idTipo" paramValue="1,2,3,4" onfocus="setMensaje('cbxGTipo');"
  								onchange="buscaElemento('cbxGTipo');llenaComboOrigen();setMensaje('cbxGTipo');" style="width:100px;" 
  								 />
			</td>
			
  				<td align="center" width="30%">
					<select id="cbxGOrigen" name="cbxGOrigen" disabled="disabled" style="width:150px;" onchange="generaFolioInicial();llenaCriterioSeleccion();setMensaje('cbxGOrigen');validaOrigenPromocion()" class="etiqueta2" onfocus="">
    						<option value="">--Por favor seleccione--</option>
    					</select>
				<input type="button" value="..." id="buscaOrigenPromocion" name="buscaOrigenPromocion"  onclick="abreFoliosPagos();"/>
			</td>
  			<td align="center" width="15%"><input type="text" id="txtFolio" class="etiqueta2" size="10"  id="folio" /></td>
  			<td><input type="text" id="txtFolioAnio" name="txtFolioAnio" class="etiqueta2" size="4" maxlength="4">  </td>
  			<td><input type="text" id="txtFolioNum" name="txtFolioNum" class="etiqueta2" size="4" maxlength="4" onchange="llenaCeros('txtFolioNum');"></td>
  			  
  			<td align="center" width="25%">
					<select id="cbxGCriterioSeleccion"  name="cbxGCriterioSeleccion" style="width:200px;" disabled="disabled" onchange="buscaElemento('cbxGCriterioSeleccion');setMensaje('cbxGCriterioSeleccion');validaOrigenPromocion();" onfocus="" class="etiqueta2">
    						<option value="">--Por favor seleccione--</option>
    					</select>
  			
  			</td>
  				<td>Al:</td>
  			<td><input type="text" style="text-align: center;" size="15" maxlength="10" id="dtpGPeriodoAl" class="etiqueta2" name="dtpGPeriodoAl" onfocus="setMensaje('dtpGPeriodoAl')" disabled="disabled" onkeypress="validar(event, 'dtpGPeriodoAl')" onchange="if(validafechaSistema('dtpGPeriodoAl')==true){if(validaFecha('dtpGPeriodoDel','dtpGPeriodoAl')==true){validar(event, 'dtpGPeriodoAl');inhabilitar(event, 'dtpGPeriodoAl');validaGuardadoAl();}}"><label for="dtpGPeriodoAl" > </label></td>
  		
  			<!-- return KeyPressed(this,event,'validchar', null, 'uppercase=no') -->
  		</tr>
  		</table>
  		
  		
  		</td>
  		
  		</tr>
  		<tr>
  		<td>
  		<table  width="100%" align="center" >
  			<tr valign="middle" class="impar">
  			
  			<td align="center" width="10%" class="etiqueta2">Registro Patronal</td>
  			<td align="center" width="5%" class="etiqueta2"></td>
  			<td align="center" width="35%" class="etiqueta2">Nombre</td>
  			<td align="center" width="25%" class="etiqueta2">AFIL-15/RO</td>
  			<td align="center" width="5%" class="etiqueta2">RPA</td>
  			<td align="center" width="20%" class="etiqueta2"></td>
  			
  			
  		</tr>
  	<tr valign="middle" class="par">
  			<td align="left" width="10%"><input type="text" id="txtGRP" name="txtGRP" class="etiqueta2" size="20" maxlength="10" disabled="disabled"  style="text-align: center; text-transform: uppercase;" onfocus="setMensaje('txtGRP')" onkeypress="validar(event, 'txtGRP');"  ><label for="txtGRP" >  </label> 	</td>
  			<td align="left" width="5%"><input type="button" value="Buscar" size="100px" id="cmdBuscar" width="60px" onclick="validarRegPatronal('txtGRP');" disabled="disabled"> </td>
  			<td align="center" width="35%"><input type="text" id="txtGNombre" name="txtGNombre" class="etiqueta2" size="50" disabled="disabled" onfocus="setMensaje('txtGNombre')" onkeypress="validar(event, 'txtGNombre')" onchange="inhabilitar(event, 'txtGNombre'); buscaElemento('txtGNombre');" ><label for="txtGNombre" > </label>  </td>
  			<td align="right" width="20%"><input type="text" id="txtAFIL15" class="etiqueta2" name="txtAFIL15" size="20" maxlength="22" disabled="disabled" onfocus="setMensaje('txtAFIL15')"  onkeypress="validar(event, 'txtAFIL15'); return KeyPressed(this,event,'validchar', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtAFIL15'); buscaElemento('txtAFIL15');"><label for="txtAFIL15" > </label></td>
  			<td align="right" width="5%"><input type="text" id="txtGNoRPA" class="etiqueta2" name="txtGNoRPA" size="3" disabled="disabled" onfocus="setMensaje('txtAFIL15')" readonly="readonly"  onkeypress="validar(event, 'txtAFIL15')" onchange="inhabilitar(event, 'txtAFIL15'); buscaElemento('txtAFIL15');"><label for="txtAFIL15" > </label></td>
  			<td align="center" width="20%" ><input type="button" value="RPA" id="btnRPA" name="btnRPA" id="btnRPA" onclick="openDgAgregarRP();" disabled="disabled" /></td>
  		</tr>
  		</table>
  		</td>
  		</tr>
  		<tr>
  		<td>
  			<div style="border: thin; border-color: black;border-style: solid;">
						<ul class="tabs">
							<li class="etiqueta2"><a href="#promocionSaticBObrasSatic">Autodeterminacion</a></li>
							<li class="etiqueta2"><a href="#tab2">Revisi&oacute;n</a></li>
						</ul>
						
						<div class="tab_container"	>
							<div id="promocionSaticBObrasSatic" class="tab_content">
								<jsp:include page="autodeterminacion.jsp" />
							</div>
							<div id="tab2" class="tab_content">
								<jsp:include page="revision.jsp" />

							</div>

						</div>
			</div>
		</td>
  		 <td>
  		 	<table ><tr><td>
	
   </td>
   <td style="vertical-align: top">
   	<table style="vertical-align: top;" >
		<tr >
  			<td align="center" colspan="8" class="etiqueta2">Status</td>
  		</tr>
		<tr>
  			<td align="center" colspan="8" ><textarea class="etiqueta2" wrap="hard"  rows="6" cols="20" id="txtStatus" draggable="false"  readonly="readonly" style="background: blue; color: white; text-align: center" ></textarea> </td>
  		</tr>
  		
  		<tr>
  			<td align="center" colspan="8" height="10px"> &nbsp; </td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" ><input type="button" value="Nuevo" size="100px" id="cmdNuevo" width="60px" class="boton"  onclick="nuevaCorreccion();">  </td>
  		</tr>
  		
  		<tr>
  			<td align="center" colspan="8" height="10px"> &nbsp; </td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" ><input type="button" value="Guardar" id="cmdGuardar" width="60px" class="boton" disabled="disabled"></td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" height="10px"> &nbsp; </td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" ><input type="button" value="Buscar" size="100px" id="cmdBuscar2" name="cmdBuscar2" width="60px" class="boton" onclick="listarCorrecciones();">  </td>
  		</tr>
  		
  		<tr>
  			<td align="center" colspan="8" height="10px">&nbsp;  </td>
  		</tr>
  </table>
   
   
   </td>
  </tr>		
 
</table>
  		 </td>
  		</tr>
  		<tr>
  			<td>	<div>
			<table style="border: thin; border-color: black; border-style: solid; width: 100%" >
				<tr>
					<td class="etiqueta2">SD</td>
					<td><input type="text" size="20" style="text-align: center;" id="dtpCSD1" class="etiqueta2" name="dtpCSD1" disabled="disabled" onchange="if(validafechaSistemaC('dtpCSD1')){validar(event, 'dtpCSD1');inhabilitar(event, 'dtpCSD1');verificaNivelAdicionalC('dtpCSD1','dtpAAPP');verificaNivelAdicionalC('dtpCSD1','dtpAACP');verificaNivelAdicionalC('dtpCSD1','dtpAASR');verificaNivelAdicionalC('dtpCSD1','dtpCC1');verificaNivelAdicionalC('dtpCSD1','dtpASP');verificaNivelAdicionalC('dtpCSD1','dtpCPAI1');verificaNivelAdicionalC('dtpCSD1','dtpCDS1');verificaNivelAdicionalC('dtpCSD1','dtpRRDN');verificaNivelAdicionalC('dtpCSD1','dtpROD');verificaNivelAdicionalC('dtpCSD1','dtpRNOD');verificaNivelAdicionalC('dtpCSD1','dtpRVDA');}validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();" onblur="validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();"  onfocus="setMensaje('dtpCSD1');" onkeypress="validar(event, 'dtpCSD1');" ></td>
					
					<td class="etiqueta2">DS</td>
					<td><input type="text" size="20" style="text-align: center;" id="dtpCDS1" class="etiqueta2" name="dtpCDS1" disabled="disabled" onchange="if(validafechaSistemaC('dtpCDS1')){validar(event, 'dtpCDS1');inhabilitar(event, 'dtpCDS1');verificaNivelAdicionalDS('dtpCDS1', 'dtpASP');verificaNivelAdicionalDS('dtpCDS1', 'dtpAAPP');verificaNivelAdicionalDS('dtpCDS1', 'dtpAACP');verificaNivelAdicionalDS('dtpCDS1', 'dtpAASR');verificaNivelAdicionalDS('dtpCDS1', 'dtpCSD1');verificaNivelAdicionalDS('dtpCDS1', 'dtpCPAI1');}validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();" onfocus="setMensaje('dtpCDS1');" onkeypress="validar(event, 'dtpCDS1')" onblur="validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();" ></td>
					<td class="etiqueta2">PAI</td>
					<td><input type="text"  size="20" style="text-align: center;" id="dtpCPAI1" class="etiqueta2" name="dtpCPAI1" disabled="disabled" onchange="if(validafechaSistemaC('dtpCPAI1')){validar(event, 'dtpCPAI1');inhabilitar(event, 'dtpCPAI1');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpASP');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpCSD1');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpCDS1');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpAAPP');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpAACP');verificaNivelAdicionalC('dtpCPAI1', 'dtpAASR');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpRDC');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpRVPP');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpRVTC');verificaNivelAdicionalPAI('dtpCPAI1', 'dtpRVDA');}validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();" onfocus="setMensaje('dtpCPAI1');" onblur="validaReglasCierre();validaReglasCierre();" onkeypress="validar(event, 'dtpCPAI1')"></td>
					<td class="etiqueta2">¿Presento Pagos?
						<input type="checkbox" id="chkCPresentoPagos" name="chkCPresentoPagos" disabled="disabled" onclick="validarPresentoPagos();">
					</td>
				</tr>
				<tr>
					<td class="etiqueta2">No. Oficio</td>
					<td><input type="text" style="text-align: center;" size="20" id="txtCNoOficio" class="etiqueta2" name="txtCNoOficio" onfocus="setMensaje('txtCNoOficio')" disabled="disabled" ></td>
					<td class="etiqueta2">C</td>
					<td><input type="text" style="text-align: center;" size="20" id="dtpCC1" class="etiqueta2"  name="dtpCC1" onfocus="setMensaje('dtpCC1')" onkeypress="validar(event, 'dtpCC1');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="if(validaFechasCGCorre('dtpCC1', 'dtpAOI', 'dtpFechaSistema')==true){validar(event, 'dtpCC1');inhabilitar(event, 'dtpCC1');verificaNivelAdicionalC('dtpCC1','dtpRRDN');verificaNivelAdicionalC('dtpCC1', 'dtpCSD1');verificaNivelAdicionalC('dtpCC1', 'dtpRNOD');verificaNivelAdicionalC('dtpCC1', 'dtpAOIN');}validaReglasCierre();validaReglasCierre();validaReglasCierre();validaReglasCierre();" onblur="validaReglasCierre();validaReglasCierre();"></td>
					<td class="etiqueta2">Motivo</td>
					<td>
					<select id="cbxCMotivoCancelacion" name="cbxCMotivoCancelacion" style="width:220px;"  class="etiqueta2" onfocus="setMensaje('cbxCMotivoCancelacion')" onchange="buscaElemento('cbxMotivoCancelacion');">
    						<option value="">--Por favor seleccione--</option>
    				</select>
    				</td>	
				</tr>
				
				<tr>
					<td class="etiqueta2">Auditor</td>
					<td>
					<select id="cbxAuditor" name="cbxAuditor" disabled="disabled" class="etiqueta2" onfocus="setMensaje('cbxAuditor')">
    						<option value="">--Por favor seleccione--</option>
    					</select>
					</td>
					
				</tr>
			
			</table>
		
		</div></td>
  		</tr>
  		</table>
 <table  width="100%" border="0" align="center" >
		<tr>
  			<td align="left" ><textarea  id="txtGMensajeAyuda" cols="100" rows="3" readonly="readonly" style="background-color: yellow;" onkeypress="validar(event, 'txtObservaciones')" ></textarea></td>
  		</tr>
	</table>

  </form>
</div>

<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<style>
	label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
	label  {  float: none; }
	
</style>
  <style type="text/css">
            body, div  { margin:0px auto; padding:0px; }

            .main { margin:40px; }
            
            .sample { float:left; margin:10px; padding:4px; border:1px solid #888; width:350px; }
            
            .sample h3 { margin:-4px; margin-bottom:10px; padding:4px; background:#555; color:#eee; }
            
            .currencyLabel { display:block; }        
        </style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/KeyPressed.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/delta/jquery.formatCurrency-1.4.0.pack.js"></script>
<br/>
					<div class="menu_principal" style="height: 2em !important;">
					<!--inicia menu principal-->
					<div align="center">
						<div class="centrado">
							<ul style="height: 1em !important;">
								<li><a>PROMOCI&Oacute;N</a>
								</li>
							</ul>
						</div>
						<!--fin centrado-->
					</div>
				</div>
				<br />

<div id="wrapperData" style="background-color: white !important;">
 <form action="" method="post" id="formPromo">
 	<input type="hidden" id="regPatHidden" value="">
 	<input type="hidden" id="guardadoHidden" value="0">
 	<input type="hidden" id="idStatusHidden" value="0">
 	<table border="0" align="center">
	<tr>
		<td>
	<table width="100%" class="tablaverde2" border="0px" align="center">
 		<tr valign="middle" class="par" >
  			<td align="center" width="25%" class="etiqueta2">Clasificaci&oacute;n</td>
  			<td align="center" width="25%" class="etiqueta2">Fuente</td>
  			<td align="center" width="15%" class="etiqueta2">Folio</td>
  			<td></td>
  			<td></td>
  			<td align="center" width="35%" class="etiqueta2">Elemento de Selecci&oacute;n</td>
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="left" width="25%">
  				<combo:creaCombo  
  								entidad="mx.gob.imss.ctirss.correccion.model.CgcCatTipo" 
  								idHtml="cgcCatTipo.idTipo"
  								idHtmlContenedor="formPromo" param="idTipo" paramValue="5,6,7,8"
  								onchange="buscaElemento('cbxTipo');llenaComboOrigen();desabilitaForma();setMensaje('cbxTipo');eliminaOpcionesSelect('cbxOrigen');eliminaOpcionesSelect('cbxCriterioSeleccion');" style="width:115px;" 
  								 />
  								 
			</td>
			<td align="center" width="25%">
					<select id="cbxOrigen" autofocus="autofocus"  name="cbxOrigen" disabled="disabled" style="width:180px" onfocus="setMensaje('cbxOrigen')" onchange="generaFolioInicial();llenaCriterioSeleccion();buscaElemento('cbxOrigen')" class="etiqueta2">
    						<option value="">--Por favor seleccione--</option>
    					</select>
				
			</td>
			<td align="center" width="18%"><input type="text" id="txtFolio" class="etiqueta2" size="10" readonly="readonly" id="folio" />  </td>
			<td><input type="text" id="txtFolioAnio" class="etiqueta2" size="4" name="txtFolioAnio" maxlength="4" /></td>
			<td><input type="text" id="txtFolioNum" class="etiqueta2" size="4" name="txtFolioNum" maxlength="4" onchange="llenaCeros('txtFolioNum')" /></td>
  			<td align="center" width="32%">
					<select id="cbxCriterioSeleccion" name="cbxCriterioSeleccion" onfocus="setMensaje('cbxCriterioSeleccion')" 
					disabled="disabled" onchange="buscaElemento('cbxCriterioSeleccion');llenaDatosTiposObras();" onfocus="setMensaje('cbxCriterioSeleccion')" class="etiqueta2" style="width:380px;overflow: visible;" >
    						<option value="">--Por favor seleccione--</option>
    					</select>
  			
  			</td>
  			<!-- -->
  		</tr>
  		</table>
  		<table  width="100%" align="center" class="tablaverde2">
  	<tr valign="middle" class="par">
  			
  			<td align="center" width="10%" class="etiqueta2">Registro Patronal</td>
  			<td align="center" width="5%" class="etiqueta2"></td>
  			<td align="center" width="40%" class="etiqueta2">Nombre</td>
  			<td align="center" width="40%" class="etiqueta2">Ubicación</td>
  			
  			
  		</tr>
  	<tr valign="middle" class="impar">
  			<td align="left" width="10%"><input type="text" id="txtRP" name="txtRP" class="etiqueta2" size="20" maxlength="10" disabled="disabled"  style="text-align: center; text-transform: uppercase;" onfocus="setMensaje('txtRP')" onkeypress="validarRegPat(event, 'txtRP', 'txtDV')" onchange="validaActivacion('txtRP');"><label for="txtRP" >  </label> 	</td>
  			<td align="left" width="5%"><input type="button" value="Buscar" size="100px" id="cmdBuscar" width="60px" onclick="validarRegPatronal('txtRP');" disabled="disabled"> </td>
  			<td align="center" width="40%"><input type="text" id="txtNombre" maxlength="100" name="txtNombre" class="etiqueta2" size="55" disabled="disabled" onfocus="setMensaje('txtNombre')" onkeypress="validar(event, 'txtNombre')" onchange="buscaElemento('txtNombre');inhabilitar(event, 'txtNombre');" ><label for="txtNombre" > </label>  </td>
  			<td align="right" width="40%"><input type="text" id="txtUbicacion" maxlength="100" class="etiqueta2" name="txtUbicacion" size="60" disabled="disabled" onfocus="setMensaje('txtUbicacion')"  onkeypress="validar(event, 'txtUbicacion')" onchange="buscaElemento('txtUbicacion');inhabilitar(event, 'txtUbicacion');llenaDatosTiposObras();"><label for="txtUbicacion" > </label></td>
  			
  			
  		</tr>
  		</table>
  		<table  width="100%"  align="center" class="tablaverde2">
 		
  		<tr valign="middle" class="par">
  			<td align="center" width="5%" class="etiqueta2">ME</td>
  			<td align="center" width="10%" class="etiqueta2">ETR</td>
  			<td align="center" width="15%" class="etiqueta2">AFIL-15/RO</td>
  			<td align="center" width="45%" class="etiqueta2">Tipo de Obra</td>
  			<td align="center" width="5%" class="etiqueta2">SE</td>
  			<td align="center" width="10%" class="etiqueta2">CTC</td>
  			<td align="center" width="10%" class="etiqueta2">PEA</td>
  			
  			
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="left" width="5%"><input type="text" style="text-align: center" maxlength="3" size="10" class="etiqueta2" id="txtMesesEstimados" name="txtMesesEstimados" disabled="disabled" onfocus="setMensaje('txtMesesEstimados')" onkeypress="validar(event, 'txtMesesEstimados');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtMesesEstimados');buscaElemento('txtMesesEstimados');"><label for="txtMesesEstimados" > </label></td>
  			<td align="center" width="10%"><input type="text" style="text-align: center" maxlength="5" size="10" class="etiqueta2" id="txtEstimadoTrabReg" name="txtEstimadoTrabReg" disabled="disabled" onfocus="setMensaje('txtEstimadoTrabReg')" onkeypress="validar(event, 'txtEstimadoTrabReg');return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');" onchange="inhabilitar(event, 'txtEstimadoTrabReg');buscaElemento('txtEstimadoTrabReg');"><label for="txtEstimadoTrabReg" > </label></td>
  			<td align="center" width="15%"><input type="text" style="text-align: center" size="30" maxlength="22" class="etiqueta2" id="txtAFLI15" name="txtAFLI15" disabled="disabled" onfocus="setMensaje('txtAFLI15')" onkeypress="validar(event, 'txtAFLI15');return KeyPressed(this,event,'validchar', null, 'uppercase=no');" onchange="inhabilitar(event, 'txtAFLI15');buscaElemento('txtAFLI15');"><label for="txtAFLI15" > </label></td>
  			<td align="center" width="45%">
  						<select id="cbxTipoObra" name="cbxTipoObra" disabled="disabled" style="width:180px" onfocus="setMensaje('cbxTipoObra')" onchange="buscaElemento('cbxTipoObra');"  class="etiqueta2">
    						<option value="">--Por favor seleccione--</option>
    					</select></td>
  			<td align="center" width="5%"><input type="text" style="text-align: center" size="10" maxlength="7" class="etiqueta2" id="txtSuperficieEstimada" name="txtSuperficieEstimada" disabled="disabled" onfocus="setMensaje('txtSuperficieEstimada')" onkeypress="validar(event, 'txtSuperficieEstimada');return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');" onchange="inhabilitar(event, 'txtSuperficieEstimada');buscaElemento('txtSuperficieEstimada');"><label for="txtSuperficieEstimada" > </label></td>
  			<td align="center" width="10%"><input type="text" style="text-align: center" size="13" class="etiqueta2" id="txtCostoTotalContratado" name="txtCostoTotalContratado" disabled="disabled" onfocus="setMensaje('txtCostoTotalContratado')" onkeypress="validar(event, 'txtCostoTotalContratado');return KeyPressed(this,event,'moneda', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtCostoTotalContratado')"><label for="txtCostoTotalContratado" > </label></td>
  			<td align="right" width="10%"><input type="text" style="text-align: center" size="10" maxlength="3" class="etiqueta2" id="txtPorcAvanceEstimado"  name="txtPorcAvanceEstimado" disabled="disabled" onfocus="setMensaje('txtPorcAvanceEstimado')" onkeypress="validar(event, 'txtPorcAvanceEstimado');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="inhabilitar(event, 'txtPorcAvanceEstimado');buscaElemento('txtPorcAvanceEstimado');"><label for="txtPorcAvanceEstimado" > </label></td>
  		</tr>
  		</table>
  		<table  width="100%"  align="center" class="tablaverde2" >
  		<tr valign="middle" class="par">
  			<td align="center" width="5%"  class="etiqueta2">OPE</td>
  			<td align="center" width="5%"><input type="text" style="text-align: center;" size="20" id="dtpOPE" class="etiqueta2" name="dtpOPE" onfocus="setMensaje('dtpOPE')" disabled="disabled" onkeypress="validar(event, 'dtpOPE');" onchange="validaFechasCGPromo('dtpOPE', 'dtpPFechaMin', 'dtpFechaSistema');validar(event, 'dtpOPE');inhabilitar(event, 'dtpOPE');minFecha();"><label for="dtpOPE" > </label></td>
  			<td align="center" width="5%" class="etiqueta2">No. Oficio</td>
  			<td align="center" width="5%"><input type="text" size="20" maxlength="10" style="text-align: center;"  id="txtNoOficioOPE" class="etiqueta2" name="txtNoOficioOPE" onfocus="setMensaje('txtNoOficioOPE')" disabled="disabled" onkeypress="validar(event, 'txtNoOficioOPE')"><label for="txtNoOficioOPE" > </label></td>
  			
  			<td align="center" widht="100%" colspan="2">
  				<table align="center" style="width: 100%"  cellspacing="0">
  					<tr >
  						<td align="right" width="50%" class="etiqueta3" style="text-align: right;">Trab. Revisados</td>
  						<td align="left" width="50%" ><input type="text" size="10" maxlength="4" class="etiqueta3" style="height: 8px; font-size: 7; text-align: right;" id="txtTrabRevisados" name="txtTrabRevisados" onfocus="setMensaje('txtTrabRevisados')" onkeypress="validar(event, 'txtTrabRevisados');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" disabled="disabled"></td>
  					</tr>
  					<tr>
  						<td align="right" width="50%" class="etiqueta3" style="text-align: right;">Trab. Omisos</td>
  						<td align="left" width="50%" ><input type="text" size="10" maxlength="4" id="txtTrabOmisos" class="etiqueta3" style="height: 8px; font-size: 7; text-align: right;" name="txtTrabOmisos" onfocus="setMensaje('txtTrabOmisos')" onkeypress="validar(event, 'txtTrabOmisos');sumaTrabajadores(event);return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="sumarTrabajadores();" disabled="disabled" ><label for="txtTrabOmisos" > </label></td>		
  					</tr>
  					<tr>
  						<td align="right" width="50%" class="etiqueta3" style="text-align: right;">Trab. Subdeclarados</td>
  						<td align="left" width="50%" ><input type="text" size="10" maxlength="4" id="txtTrabSubdeclarados" class="etiqueta3" style="height: 8px; font-size: 7; text-align: right;" name="txtTrabSubdeclarados" onfocus="setMensaje('txtTrabSubdeclarados')" onkeypress="validar(event, 'txtTrabSubdeclarados');sumaTrabajadores(event);return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" onchange="sumarTrabajadores();" disabled="disabled"><label for="txtTrabSubdeclarados" > </label></td>
  					</tr>		
  					<tr>
  						<td align="right" width="50%" class="etiqueta3" style="text-align: right;">Trab. Regularizados</td>
  						<td align="left" width="50%" ><input type="text" size="10" maxlength="6" id="txtTrabRegularizados" class="etiqueta3" style="height: 8px; font-size: 7; text-align: right;" name="txtTrabRegularizados" onfocus="setMensaje('txtTrabRegularizados')" readonly="readonly" onkeypress="validar(event, 'txtTrabRegularizados');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');" ><label for="txtTrabRegularizados" > </label></td>
  					</tr>
  				</table>
  			</td>
  			
  			
  		</tr>
  		
  		<tr valign="middle" class="impar">
  			<td align="center" width="5%" class="etiqueta2">NOP</td>
  			<td align="center" width="5%"><input type="text" style="text-align: center;" size="20" id="dtpNOP" name="dtpNOP" class="etiqueta2" onfocus="setMensaje('dtpNOP')" disabled="disabled" onkeypress="validar(event, 'dtpNOP')" onchange="if (validaFechasCGPromo('dtpNOP', 'dtpOPE', 'dtpFechaSistema')==true){validar(event, 'dtpNOP');inhabilitar(event, 'dtpNOP');verificaNivel('dtpNOP');}" ><label for="dtpNOP" > </label></td>
  			<td align="center" width="5%" class="etiqueta2">C</td>
  			<td align="center" width="5%"><input type="text" style="text-align: center;" size="20" id="dtpC" name="dtpC" class="etiqueta2" onfocus="setMensaje('dtpC')" disabled="disabled" onkeypress="validar(event, 'dtpC')" onchange="if (validaFechasCGPromo('dtpC', 'dtpOPE', 'dtpFechaSistema')==true){validar(event, 'dtpC');inhabilitar(event, 'dtpC');verificaNivel('dtpC');llenaDatosMotCanc();}"><label for="dtpC" > </label></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">Motivo</td>
  			<td align="center" width="5%" colspan="2"><combo:creaCombo  
  								id="cbxMotivoCancelacion" entidad="mx.gob.imss.ctirss.correccion.model.CgcCatMotivoCancelacion" 
  								idHtml="cbxMotivoCancelacion"
  								idHtmlContenedor="formPromo" style="width:310px; font-family: Verdana;	font-size: 9px;	color: #000000;	letter-spacing : 0px;	line-height : 12px;	font-weight: bold;"  
  								onchange="buscaElemento('cbxMotivoCancelacion');" 
  								 /></td>
  			
  		</tr>
  			<tr valign="middle" class="par">
  			<td align="center" width="5%" class="etiqueta2">AOP</td>
  				<td align="center" width="5%"><input type="text" style="text-align: center;" size="20" id="dtpAOP" class="etiqueta2" onfocus="setMensaje('dtpAOP')" disabled="disabled" onkeypress="validar(event, 'dtpAOP')" onchange="if(validaFechasCGPromo('dtpAOP', 'dtpNOP', 'dtpFechaSistema')==true){validar(event, 'dtpAOP');inhabilitar(event, 'dtpAOP');verificaNivelExcluye('dtpAOP', 'dtpOI', '6');verificaNivelOI('dtpAOP', 'dtpPAI', '7');verificaNivelOI('dtpPAI', 'dtpAOP', '7');verificaNivelOI('dtpAOP', 'dtpPAI', '8');verificaNivelOI('dtpPAI', 'dtpAOP', '8');minFechaAl();}"></td>
  			<td align="center" width="5%" class="etiqueta2">No. Oficio Canc</td>
  			<td align="center" width="5%"><input type="text" style="text-align: center;" maxlength="10" size="20" id="txtNoOficioC" name="txtNoOficioC" onfocus="setMensaje('txtNoOficioC');" disabled="disabled" class="etiqueta2" ><label for="txtNoOficioC" > </label></td>
  			<td align="center" width="5%"></td>
  			<td>
  				
  					<table style="width: 100%">
  						<tr>
  							<td align="center" width="50%" class="etiqueta2">COP</td>
  							<td align="center" width="50%" class="etiqueta2">RCV</td>		
  						</tr>
  					</table>
  				
  			</td>
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="center" width="5%" class="etiqueta2">SP</td>
  			<td align="center" width="5%"><input type="text" style="text-align: center;" size="20" id="dtpSP" name="dtpSP" class="etiqueta2" onfocus="setMensaje('dtpSP')" disabled="disabled" onchange="if(validaFechasCGPromo('dtpSP', 'dtpAOP', 'dtpFechaSistema')==true){verificaDatos();}"></td>
  			<td align="center" width="5%" class="etiqueta2">PAI	</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="dtpPAI" class="etiqueta2" onfocus="setMensaje('dtpPAI')" disabled="disabled" onkeypress="validar(event, 'dtpPAI')" onchange="if(validaFechasCGPromo('dtpPAI', 'dtpNOP', 'dtpFechaSistema')==true){validar(event, 'dtpPAI');inhabilitar(event, 'dtpPAI');verificaNivel('dtpPAI'); verificaNivelOI('dtpAOP', 'dtpPAI', '6'); verificaNivelOI('dtpOI', 'dtpPAI', '6')}"></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">SP</td>
  			<td>
  				
  					<table style="width: 100%">
  						<tr>
  							<td align="center" width="50%"><input type="text" class="etiqueta2" size="25" id="txtCOPPagSP" onfocus="setMensaje('txtCOPPagSP')" disabled="disabled" onkeypress="validar(event, 'dtpPAI')" style="text-align: right;" onchange="$('txtCOPPagSP').formatCurrency();"></td>
  							<td align="center" width="50%"><input type="text" class="etiqueta2" size="25" id="txtRCVPagSP" onfocus="setMensaje('txtRCVPagSP')" disabled="disabled" onkeypress="validar(event, 'dtpPAI')" style="text-align: right;"></td>		
  						</tr>
  					</table>
  				
  			</td>
  			
  		</tr>
  		<tr valign="middle" class="par">
  			<td align="center" width="5%" class="etiqueta2">OI</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="dtpOI" class="etiqueta2" onfocus="setMensaje('dtpOI')" disabled="disabled" onkeypress="validar(event, 'dtpOI')" onchange=" if (validaFechasCGPromo('dtpOI', 'dtpNOP,dtpAOP', 'dtpFechaSistema')==true){validar(event, 'dtpOI');inhabilitar(event, 'dtpOI');verificaNivel('dtpOI');verificaNivelOI('dtpAOP', 'dtpOI', '5')}" ></td>
  			<td align="center" width="5%"></td>
  			<td align="center" width="5%"><input type="text" size="20" class="etiqueta2" id="" disabled="disabled" ></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">Act</td>
  			<td>
  				
  					<table style="width: 100%">
  						<tr>
  							<td align="center" width="50%"><input type="text" size="25" id="txtCOPPagAct" class="etiqueta2" onfocus="setMensaje('txtCOPPagAct')" disabled="disabled" onkeypress="validar(event, 'txtCOPPagAct');" style="text-align: right;"></td>
  							<td align="center" width="50%"><input type="text" size="25" id="txtRCVPagAct" class="etiqueta2" onfocus="setMensaje('txtRCVPagAct')" disabled="disabled" onkeypress="validar(event, 'txtRCVPagAct')" style="text-align: right;"></td>		
  						</tr>
  					</table>
  				
  			</td>
  			
  			
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="center" width="5%" class="etiqueta2">PR</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="dtpPR" class="etiqueta2" onfocus="setMensaje('dtpPR')" disabled="disabled" onkeypress="validar(event, 'dtpPR')" onchange="if(validaFechasCGPromo('dtpPR', 'dtpAOP', 'dtpFechaSistema')==true){validar(event, 'dtpPR');inhabilitar(event, 'dtpPR');verificaNivel('dtpPR');}"></td>
  			<td align="center" width="5%" class="etiqueta2">Del:</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="dtpPeriodoDel" class="etiqueta2" onfocus="setMensaje('dtpPeriodoDel');" onkeypress="validar(event, 'dtpPeriodoDel')"  onchange="validar(event, 'dtpPeriodoDel');inhabilitar(event, 'dtpPeriodoDel');verificaNivel('dtpPeriodoDel');validaFechasCGPromo('dtpPeriodoDel', 'dtpPFechaMin', 'dtpOPE');minFechaAl();" disabled="disabled"></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">Rec</td>
  			<td>
  				
  					<table style="width: 100%">
  						<tr>
  			<td align="center" width="5%"><input type="text" size="25" class="etiqueta2" id="txtCOPPagRec" class="etiqueta2" onfocus="setMensaje('txtCOPPagRec')" disabled="disabled" onkeypress="validar(event, 'txtCOPPagRec')" style="text-align: right;"></td>
  			<td align="center" width="5%"><input type="text" size="25" class="etiqueta2" id="txtRCVPagRec" class="etiqueta2" onfocus="setMensaje('txtRCVPagRec')" disabled="disabled" onkeypress="validar(event, 'txtRCVPagRec')" style="text-align: right;"></td>			</tr>
  					</table>
  				
  			</td>
  			
  		</tr>
  		<tr valign="middle" class="par">
  			<td align="center" width="5%" class="etiqueta2">CR</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="dtpCR" class="etiqueta2" onfocus="setMensaje('dtpCR')" disabled="disabled" onkeypress="validar(event, 'dtpCR')" onchange="if(validaFechasCGPromo('dtpCR', 'dtpAOP', 'dtpPFechaMax')==true){validar(event, 'dtpCR');inhabilitar(event, 'dtpCR');verificaNivel('dtpCR');}"></td>
  			<td align="center" width="5%" class="etiqueta2">Al:</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="dtpPeriodoAl" class="etiqueta2" onfocus="setMensaje('dtpPeriodoAl')"  onkeypress="validar(event, 'dtpPeriodoAl')" onchange="validar(event, 'dtpPeriodoAl');inhabilitar(event, 'dtpPeriodoAl');verificaNivel('dtpPeriodoAl');" disabled="disabled"></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">TP</td>
 			<td>
  				
  					<table style="width: 100%">
  						<tr>
  							<td align="center" width="5%"><input type="text" class="etiqueta2" size="25" id="txtCOPPagTotal" onfocus="setMensaje('txtCOPPagTotal')" disabled="disabled" onkeypress="validar(event, 'txtCOPPagTotal')" style="text-align: right;"></td>
  							<td align="center" width="5%"><input type="text" class="etiqueta2" size="25" id="txtRCVPagTotal" onfocus="setMensaje('txtRCVPagTotal')" disabled="disabled" onkeypress="validar(event, 'txtRCVPagTotal')" style="text-align: right;"></td>		</table>
  			</td>
 			
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="center" width="5%" class="etiqueta2">% Regularizado</td>
  			<td align="center" width="5%"><input type="text" size="20" style="text-align: center;" id="txtPorcRegularizado" name="txtPorcRegularizado" maxlength="3" class="etiqueta2" onfocus="setMensaje('txtPorcRegularizado')" disabled="disabled" onkeypress="validar(event, 'txtPorcRegularizado');return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');inhabilitar(event, 'txtPorcRegularizado');" onchange="inhabilitar(event, 'txtPorcRegularizado');buscaElemento('txtPorcRegularizado');" ><label for="txtPorcRegularizado" > </label></td>
  			<td align="center" width="5%" class="etiqueta2">No. Convenio</td>
  			<td align="center" width="5%"><input type="text" size="20" maxlength="20"  style="text-align: center;" id="txtNoConvenio" class="etiqueta2" onfocus="setMensaje('txtNoConvenio')" disabled="disabled" onkeypress="validar(event, 'txtNoConvenio');return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');inhabilitar(event, 'txtNoConvenio');" name="txtNoConvenio" onchange="inhabilitar(event, 'txtNoConvenio');buscaElemento('txtNoConvenio');"><label for="txtNoConvenio" ></label></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">SPD</td>
  			<td>
  				<table style="width: 100%">
  						<tr>
  							<td align="center" width="5%"><input type="text" size="25" style="text-align: right;" maxlength="14" class="etiqueta2" id="txtCOPConvSP" name="txtCOPConvSP" onfocus="setMensaje('txtCOPConvSP')" disabled="disabled" onkeypress="validar(event, 'txtCOPConvSP');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"></td>
							<td align="center" width="5%"><input type="text" style="text-align: right;" size="25" maxlength="14" class="etiqueta2" id="txtRCVConvSP" name="txtRCVConvSP" onfocus="setMensaje('txtRCVConvSP')" disabled="disabled" onkeypress="validar(event, 'txtRCVConvSPl');return KeyPressed(this,event,'entero', null, 'uppercase=no', null, 'no');"></td>			
						</tr>
  				</table>
  			</td>
  		</tr>
  		<tr valign="middle" class="par">
  			<td align="center" width="5%" class="etiqueta2">% Avance</td>
  			<td align="center" width="5%"><input type="text" size="20" maxlength="3" style="text-align: center;" id="txtPorcAvance" class="etiqueta2" onfocus="setMensaje('txtPorcAvance')" disabled="disabled" onkeypress="validar(event, 'txtPorcAvance');return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');inhabilitar(event, 'txtPorcAvance');" name="txtPorcAvance" onchange="inhabilitar(event, 'txtPorcAvance');buscaElemento('txtPorcAvance');"><label for="txtPorcAvance" ></label></td>
  			<td align="center" width="5%" class="etiqueta2">No. de Parcialidades</td>
  			<td align="center" width="5%"><input type="text" size="20" maxlength="2" style="text-align: center;" id="txtNoParcialidades" class="etiqueta2" onfocus="setMensaje('txtNoParcialidades')" disabled="disabled" onkeypress="validar(event, 'txtNoParcialidades');return KeyPressed(this,event,'entero', null, 'uppercase=no', null,'yes');inhabilitar(event, 'txtNoParcialidades');" name="txtNoParcialidades" onchange="inhabilitar(event, 'txtNoParcialidades');buscaElemento('txtNoParcialidades');"><label for="txtNoParcialidades" ></label></td>
  			<td align="center" width="5%" class="etiqueta2" style="text-align: right;">SPP</td>
 			<td>
  				<table style="width: 100%">
  						<tr>
  							<td align="center" width="5%"><input type="text" style="text-align: right;" size="25" class="etiqueta2" id="txtCOPxPagSP" onfocus="setMensaje('txtCOPxPagSP')" disabled="disabled" onkeypress="validar(event, 'dtpPAI')"></td>
  							<td align="center" width="5%"><input type="text" style="text-align: right;" size="25" class="etiqueta2" id="txtRCVxPagSP" onfocus="setMensaje('txtRCVxPagSP')" disabled="disabled" onkeypress="validar(event, 'dtpPAI')"></td>			
						</tr>
  				</table>
  			</td>
 			
  		</tr>
  		<tr valign="middle" class="impar">
  			<td align="center" width="5%" class="etiqueta2"></td>
  			<td align="center" width="5%"></td>
  			<td align="center" width="5%" class="etiqueta2"></td>
  			<td align="center" width="5%"></td>
  			
 			<td align="center" width="5%" colspan="2"><input type="button" disabled="disabled" value="Agregar Pago" id="cmdPagos" width="60px" onclick="agregarPagos();"></td>
  			
  		</tr>
  		<tr class="par">
  			<td align="center" width="100%" colspan="8" class="etiqueta2">Observaciones</td>
  		</tr>
  		<tr class="impar">
  			<td align="center" colspan="8" ><input type="text" size="130" style="height: 40px" id="txtObservaciones" onkeypress="validar(event, 'txtObservaciones')"></td>
  		</tr>
	</table>
	
	</td>
	<td>
	<table >
	<tr><td>
	<table style="vertical-align: top;" class="impar">
		<tr >
  			<td align="center" colspan="8" class="etiqueta2">Status</td>
  		</tr>
		<tr>
  			<td align="center" colspan="8" ><textarea class="etiqueta2" wrap="hard"  rows="6" cols="20" id="txtStatus" draggable="false"  readonly="readonly" style="background: blue; color: white; text-align: center" ></textarea> </td>
  		</tr>
  		
  		<tr>
  			<td align="center" colspan="8" height="50px"> &nbsp; </td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" ><input type="button" value="Nuevo" size="100px" id="cmdNuevo" name="cmdNuevo" width="60px" onclick="nuevaPromocion();">  </td>
  		</tr>
  		
  		<tr>
  			<td align="center" colspan="8" height="50px"> &nbsp; </td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" ><input type="button" value="Guardar" id="cmdGuardar" name="cmdGuardar" width="60px" disabled="disabled"></td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" height="50px"> &nbsp; </td>
  		</tr>
  		<tr>
  			<td align="center" colspan="8" ><input type="button" value="Buscar" size="100px" id="cmdBuscar1" name="cmdBuscar1" width="60px" onclick="listarPromociones();">  </td>
  		</tr>
  		
  		<tr>
  			<td align="center" colspan="8" height="50px">&nbsp;  </td>
  		</tr>
  		
  		
  		 
	</table>
	</td>
	</tr>
	
	</table>
	</td>
	</tr>
	</table>
	<table  width="100%" border="0" align="center" >
		<tr>
  			<td align="left" ><textarea  id="txtMensajeAyuda" cols="100" rows="3" readonly="readonly" style="background-color: yellow;" onkeypress="validar(event, 'txtObservaciones')" ></textarea></td>
  		</tr>
	</table>
  </form>
</div>

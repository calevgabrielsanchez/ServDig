<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%-- <script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/timerSession.js"></script> --%>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/calendar.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/estrados/registroNotificacion/estradosRegistroNotificacion.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/commons/visorArchivoPDF.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="row-fluid">
		<div class="row-fluid">
			<div class="span8">
				<div class="separadorseccion">
					<span><label id="etiquetaTitulo">&nbsp;</label>
						 
					</span>
				</div>
			</div>
		</div>
	</div>
	<div class="well" style="background-color: white; width: 970px; font-size: 12px" align="center" id="contenedorPrincipalReg" >
       
       <form id="idFormRegistroPrincipal">
       <input type="hidden" id="domicilioValor">
        <table style="width: 100%; align=left">
        	<tr>
        		<th>
        			<label>
        				<a title="" data-toggle="popover" id="idToolTipAreaResponsableNotificacion" class="btn-small icono-help" data-original-title="&Aacute;rea responsable de la notificaci&oacute;n"></a>
        				<span class="required">* </span><span id="idSpanAreaResponsableNotificacion"></span>
        			</label>
        		</th>
	       		<td>
	       			<select id="cmbAreaResposableNotificacion" name="cmbAreaResposableNotificacion" style="text-transform: uppercase;" onchange="recuperaTiposDocumento();">		              
		             </select>
			   	</td>
	  		</tr>
	  		<tr>
	       		<td>
	       			&nbsp;
			   	</td>
	  		</tr>
	  		<tr>
	  			<td colspan="2">
	  				<div class="row-fluid">
						<div class="row-fluid">
							<div class="span8">
								<div class="separadorseccion">
									<span>
										 Datos del Sujeto a notificar
									</span>
								</div>
							</div>
						</div>
					</div>
				</td>
	  		</tr>
	  		<tr>
	       		<td>
	       			&nbsp;
			   	</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				<a title="" data-toggle="popover" id="idToolTipSujetoNotificar" class="btn-small icono-help" data-original-title="Sujeto a notificar"></a>
		  			<table width="400px">
		  				<tr>
			  				<th align="right" width="220px"><label><span class="required">* </span>Patr&oacute;n</label></th>
			  				<td width="20px">
			  				</td>
				            <td>
				            	<input type="radio" name="rdSujetoNotificar" id="rdSujetoNotificar" class="fontD" value="1" onclick="mostrarDivSujetoPatron()" checked="checked" />
				            </td>
			        	</tr>
			        	<tr>
				            <th align="right" width="220px"><label>Sujeto obligado</label></th>
				            <td width="20px">
			  				</td>
				  			<td>
				            	<input type="radio" name="rdSujetoNotificar" id="rdSujetoNotificar" class="fontD" value="2" onclick="mostrarDivSujetoObligado()" />
				            </td>
				        </tr>
			        	<tr>
				            <th align="right" width="220px"><label>Contador p&uacute;blico autorizado</label></th>
				            <td width="20px">
			  				</td>
				  			<td>
				            	<input type="radio" name="rdSujetoNotificar" id="rdSujetoNotificar" class="fontD" value="3" onclick="mostrarDivSujetoContador()" />
				            </td>
				        </tr>
			        	<tr>
				            <th align="right" width="220px"><label>Otro</label></th>
				            <td width="20px">
			  				</td>
				  			<td>
				            	<input type="radio" name="rdSujetoNotificar" id="rdSujetoNotificar" class="fontD" value="4" onclick="mostrarDivSujetoOtro()" />
				            </td>
			        	</tr>
		  			</table>
	  			</td>
	            <td>
	            	<table>
		            	<tr id="divRegistroPatronal">
		            		<th align="right">
		            			<label>
		            				<a title="" data-toggle="popover" id="idToolTipRegistroPatronal" class="btn-small icono-help" data-original-title="Registro Patronal"></a>
		            				<span class="required">* </span>Registro patronal: 
		            			</label>
		            		</th>
		            		<td width="10px">
			  				</td>
				            <td>
				            	<input type="text" id="txtRegistroPatronal" name="txtRegistroPatronal" 
				       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="11" class="alfanumerico"  onkeyup="invalidaPatron()"/>
				            </td>
				            <td width="10px" style="padding-left: 10px;">
				            	<input type="button" id="botonBuscarRP" onclick="validaRegistroPatronal();" value="Buscar"></input>
			  				</td>
				           
	            		</tr>
	            		<tr id="divRegistroIMSS" style="display: none;">
		            		<th align="right">
		            			<label>
		            				<a title="" data-toggle="popover" id="idToolTipNumRegistroImss" class="btn-small icono-help" data-original-title="N&uacute;mero de Registro IMSS"></a>
		            				<span class="required">* </span>N&uacute;mero de registro IMSS: 
		            			</label>
		            		</th>
		            		<td width="10px">
			  				</td>
				            <td>
				            	<input type="text" id="txtRegistroIMSS" name="txtRegistroIMSS" 
				       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="10" class="alfanumerico"/>
				            </td>
	            		</tr>
	            		<tr id="idTrNomDenRazonSocialPatron">
	            			<th align="right">
	            				<label>
		            				<a title="" data-toggle="popover" id="idToolTipNomDenRazonSocialPatron" class="btn-small icono-help" data-original-title="Nombre, denominaci&oacute;n o raz&oacute;n social"></a>
		            				<span class="required">* </span>Nombre, denominaci&oacute;n o raz&oacute;n social: 
	            				</label>
	            			</th>
	            			<td width="10px">
			  				</td>
				       		<td>
				       			<input type="text" id="txtNomDenRazonSocialPatron" name="txtNomDenRazonSocialPatron" 
				       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="80" />
						   	</td>
				        </tr>
				        <tr id="idTrNomDenRazonSocialSujObl" style="display:none;">
	            			<th align="right">
	            				<label>
		            				<a title="" data-toggle="popover" id="idToolTipNomDenRazonSocialSujObl" class="btn-small icono-help" data-original-title="Nombre, denominaci&oacute;n o raz&oacute;n social"></a>
		            				<span class="required">* </span>Nombre, denominaci&oacute;n o raz&oacute;n social:
	            				</label>
	            			</th>
	            			<td width="10px">
			  				</td>
				       		<td>
				       			<input type="text" id="txtNomDenRazonSocialSujObl" name="txtNomDenRazonSocialSujObl" 
				       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="80" class="alfanumerico"/>
						   	</td>
				        </tr>
				        <tr id="idTrNombreContador" style="display:none;">
	            			<th align="right">
	            				<label>
		            				<a title="" data-toggle="popover" id="idToolTipNombreContador" class="btn-small icono-help" data-original-title="Nombre del contador"></a>
		            				<span class="required">* </span>Nombre:
	            				</label>
	            			</th>
	            			<td width="10px">
			  				</td>
				       		<td>
				       			<input type="text" id="txtNombreContador" name="txtNombreContador" 
				       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="80" class="alfanumerico"/>
						   	</td>
				        </tr>
				        <tr id="idTrNomDenRazonSocialOtro" style="display:none;">
	            			<th align="right">
	            				<label>
		            				<a title="" data-toggle="popover" id="idToolTipNomDenRazonSocialOtro" class="btn-small icono-help" data-original-title="Nombre, denominaci&oacute;n o raz&oacute;n social"></a>
		            				<span class="required">* </span>Nombre, denominaci&oacute;n o raz&oacute;n social:
	            				</label>
	            			</th>
	            			<td width="10px">
			  				</td>
				       		<td>
				       			<input type="text" id="txtNomDenRazonSocialOtro" name="txtNomDenRazonSocialOtro" 
				       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="80" class="alfanumerico"/>
						   	</td>
				        </tr>
				  		<tr id="divDomicilioPatron">
				            <th align="right">
				            	<label id="labelDomicilio">
				            		<a title="" data-toggle="popover" id="idToolTipDomicilioPatron" class="btn-small icono-help" data-original-title="Domicilio"></a>
				            		<span class="required">* </span>Domicilio : 
				            	</label>
				            </th>
				            <td width="10px">
			  				</td>
						   	<td>
				       			<textarea id="txtDomicilioPatron" name="txtDomicilioPatron"  
						   				  style="text-transform: uppercase; width: 250px; height: 100px; resize:none;">
				   				</textarea>
						   	</td>
				  		</tr>
				  		<tr id="divDomicilioSujObl" style="display:none;">
				            <th align="right">
				            	<label id="labelDomicilio">
				            		<a title="" data-toggle="popover" id="idToolTipDomicilioSujObl" class="btn-small icono-help" data-original-title="Domicilio"></a>
				            		<span class="required">* </span>Domicilio : 
				            	</label>
				            </th>
				            <td width="10px">
			  				</td>
						   	<td>
				       			<textarea id="txtDomicilioSujObl" name="txtDomicilioSujObl"  
						   				  style="text-transform: uppercase; width: 250px; height: 100px; resize:none;" onchange="setDomicilio(this)">
				   				</textarea>
						   	</td>
				  		</tr>
				  		<tr id="divDomicilioFiscal" style="display:none;">
				            <th align="right">
				            	<label id="labelDomicilio">
				            		<a title="" data-toggle="popover" id="idToolTipDomicilioFiscal" class="btn-small icono-help" data-original-title="Domicilio"></a>
				            		<span class="required">* </span>Domicilio fiscal: 
				            	</label>
				            </th>
				            <td width="10px">
			  				</td>
						   	<td>
				       			<textarea id="txtDomicilioFiscal" name="txtDomicilioFiscal" 
						   				  style="text-transform: uppercase; width: 250px; height: 100px; resize:none;" onchange="setDomicilio(this)">
				   				</textarea>
						   	</td>
				  		</tr>
				  		<tr id="divDomicilioSujNot" style="display:none;">
				            <th align="right">
				            	<label id="labelDomicilio">
				            		<a title="" data-toggle="popover" id="idToolTipDomicilioSujNot" class="btn-small icono-help" data-original-title="Domicilio"></a>
				            		<span class="required">* </span>Domicilio : 
				            	</label>
				            </th>
				            <td width="10px">
			  				</td>
						   	<td>
				       			<textarea id="txtDomicilioSujNot" name="txtDomicilioSujNot"  
						   				  style="text-transform: uppercase; width: 250px; height: 100px; resize:none;" onchange="setDomicilio(this)">
				   				</textarea>
						   	</td>
				  		</tr>
	            	</table>
	            </td>
            </tr>
		</table>
		<br/>
		<div id="divSujetoNotificarOtro" style="display: none;">
			<table width="800px">
				<tr>
	  				<th align="right" width="220px"><label>Responsable solidario</label></th>
	  				<td width="20px">
	  				</td>
		            <td>
		            	<input type="radio" name="rdSujetoNotificarOtro" id="rdSujetoNotificarOtro" value="5" class="fontD" checked="checked" />
		            </td>
	        	</tr>
	        	<tr>
		            <th align="right" width="220px"><label>Asegurado</label></th>
		            <td width="20px">
	  				</td>
		  			<td>
		            	<input type="radio" name="rdSujetoNotificarOtro" id="rdSujetoNotificarOtro" value="6" class="fontD" />
		            </td>
		        </tr>
	        	<tr>
		            <th align="right" width="220px"><label>Beneficiario</label></th>
		            <td width="20px">
	  				</td>
		  			<td>
		            	<input type="radio" name="rdSujetoNotificarOtro" id="rdSujetoNotificarOtro" value="7" class="fontD" />                  
		            </td>
		        </tr>
	        	<tr>
		            <th align="right" width="220px"><label>Derechohabiente</label></th>
		            <td width="20px">
	  				</td>
		  			<td>
		            	<input type="radio" name="rdSujetoNotificarOtro" id="rdSujetoNotificarOtro" value="8" class="fontD" />
		            </td>
	        	</tr>
	        	<tr>
		            <th align="right" width="220px"><label>Trabajador</label></th>
		            <td width="20px">
	  				</td>
		  			<td>
		            	<input type="radio" name="rdSujetoNotificarOtro" id="rdSujetoNotificarOtro" value="9" class="fontD" />
		            </td>
	        	</tr>
			</table>
		</div>
		<br/>
		
		
		
		
		
		
		
		
		
		
		
		<table width="900px">
		    <tr>
		    	<th><input type="button" id="btnLimpiar" value="Limpiar"   onclick="limpiarDatosSujeto()"/></th>
		        <th><input type="button" id="btnSiguiente" value="Siguiente"  onclick="guardadoParcialNotificacion();"/></th>
		        <th><input type="button" id="btnSalir" value="Salir"  onclick="salirAplicacion()"/></th>
		    </tr>
		</table>
		</form>
	</div>
	
	
	
	<div class="well" style="background-color: white; width: 970px; font-size: 12px" align="center" id="contenedorSecundariolReg" >
	

	<table width="900">

		<tbody>
			<tr>
				<td colspan="2"><b>Acuerdo para notificaci&oacute;n por estrados</b><br><br></td>
				<td colspan="2"><b>Documento a notificar</b><br><br></td>
			</tr>
			  <tr>
				<td colspan="2">				
					<form id="idFormNumeroOficio">
						<a title="" data-toggle="popover" id="idToolTipNumOficio" class="btn-small icono-help" data-original-title="N&uacute;mero de oficio del acuerdo"></a>
						N&uacute;mero de oficio del acuerdo:<br><input type="text" id="numeroOficio" style="text-transform: uppercase;" maxlength="50">
					</form>
				</td>
				<td colspan="2">
					<form id="idFormNumeroOficioDocumento">
						<a title="" data-toggle="popover" id="idToolTipNumOficioDocumento" class="btn-small icono-help" data-original-title="N&uacute;mero de oficio o documento"></a>
						N&uacute;mero de oficio o documento:<br><input type="text" id="numeroOficionDocumentoNotificar" style="text-transform: uppercase;" maxlength="50">
					</form>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
				<td colspan="2">
					<form id="idFormCmbCveTipoDenuncianteDT">
						<a title="" data-toggle="popover" id="idToolTipTipoDocumento" class="btn-small icono-help" data-original-title="Tipo de documento"></a>
						Tipo de documento:<br>
						<select id="cmbCveTipoDenuncianteDT" name="cmbCveTipoDenuncianteDT"  style="text-transform: uppercase; width: 430px;">
			            </select>
			        	<br><br>
		        	</form>
				</td>
			</tr>
			<tr>
				<td colspan="1">
					
					<form enctype="multipart/form-data" id="formaArchivoPrimero" action="<%=request.getContextPath()%>/estrados/enviaArchivo.do" method="post">
					    <input type="file" name="file" id="file" onchange="archivoAcuerdoNotificacion('formaArchivoPrimero','validaArchivoAcuerdo()',1,'numeroOficio')"><br><br>
					    <input type="hidden" id="cveNotificaAcuerdoFile" name="cveNotificaAcuerdoFile">	
					    <input type="hidden" id="desNumOficioFile" name="desNumOficioFile">	
					    <input type="hidden" id="tipoDocumentoFile" name="tipoDocumentoFile">		
					    <input type="hidden" id="tipoDocumentoAdjunto" name="tipoDocumentoAdjunto">		
					    <input type="hidden" id="idClaveDocumentoAdjunto" name="idCaveDocumentoAdjunto">		
					</form>
					
					<div class="progress" style="width:250px;" id="progressArchivoA">
					    <div class="bar" id="barArchivoA"><div class="percent" id="percentArchivoA">0%</div ></div>					    
					</div>

					<!-- <div id="statusArchivoA"></div>	 -->		
				</td>
				<td>					
					<div align="right" style="padding-right: 90px;">
										<a title="" data-toggle="popover" id="idToolTipAdjuntarAcuse" class="btn-small icono-help" data-original-title="Adjuntar acuse"></a>
										<button type="button" onclick="visualizaDocumento('formaArchivoPrimero')">Visualizar</button><br><br>
										<button type="button" onclick="eliminarArchivoAdjunto('formaArchivoPrimero','idClaveDocumentoAdjunto','numeroOficio','A')">Eliminar</button>
					</div>

					
				</td>
				<td colspan="1">
				
				
					<form id="formaArchivoSegundo" action="<%=request.getContextPath()%>/estrados/enviaArchivo.do" method="post" enctype="multipart/form-data">
					    <input type="file" name="file" id="file" onchange="archivoAcuerdoNotificacion('formaArchivoSegundo','validaArchivoDocumentoNotificar()',2,'numeroOficionDocumentoNotificar')"><br><br>
					    <input type="hidden" id="cveNotificaAcuerdoFile" name="cveNotificaAcuerdoFile">	
					    <input type="hidden" id="desNumOficioFile" name="desNumOficioFile">	
					    <input type="hidden" id="tipoDocumentoFile" name="tipoDocumentoFile">	
					    <input type="hidden" id="tipoDocumentoAdjunto" name="tipoDocumentoAdjunto">	
					    <input type="hidden" id="idClaveDocumentoAdjunto" name="idCaveDocumentoAdjunto">					  
					</form>
					
					<div class="progress" style="width:250px;" id="progressArchivoB">
					    <div class="bar" id="barArchivoB"><div class="percent" id="percentArchivoB">0%</div ></div>					    
					</div>
					
				<!-- 	<div id="statusArchivoB"></div>	 -->
				
				
				</td>
				<td>					
					<div align="right" style="padding-right: 90px;">
										<a title="" data-toggle="popover" id="idToolTipAdjuntarDocumento" class="btn-small icono-help" data-original-title="Adjuntar documento"></a>
										<button type="button" onclick="visualizaDocumento('formaArchivoSegundo')">Visualizar</button><br><br>
										<button type="button" onclick="eliminarArchivoAdjunto('formaArchivoSegundo','idClaveDocumentoAdjunto','numeroOficionDocumentoNotificar','B')">Eliminar</button>
					</div>

					
				</td>
			</tr>
			<%-- <tr id="trOtrosLabel">
				<td colspan="4"><br><b>Otro tipo de documento</b></td>
			</tr>
			<tr id="trOtros">
				<td colspan="2">
				
					<form id="formaArchivoVarios" action="<%=request.getContextPath()%>/estrados/enviaArchivo.do" method="post" enctype="multipart/form-data">
					    <input type="file" accept="application/pdf" name="file" id="file" onchange="archivoAcuerdoNotificacion('formaArchivoVarios',undefined,3)"><br><br>	
					    <input type="hidden" id="cveNotificaAcuerdoFile" name="cveNotificaAcuerdoFile">	
					    <input type="hidden" id="desNumOficioFile" name="desNumOficioFile">	
					    <input type="hidden" id="tipoDocumentoFile" name="tipoDocumentoFile">		
					    <input type="hidden" id="tipoDocumentoAdjunto" name="tipoDocumentoAdjunto">	 
					    <input type="hidden" id="idClaveDocumentoAdjunto" name="idCaveDocumentoAdjunto">	 
					</form>
					
					<div class="progress" style="width:250px;" id="progressArchivoVarios">
					    <div class="bar" id="barArchivoVarios"><div class="percent" id="percentArchivoVarios">0%</div></div>					    
					</div>
				
				
				</td>
				
				<td colspan="1">				
					<div id="contenedorListaOtros">
						<select size="10" id="listaArchivosOtros">						  
						</select>
					</div>				
				</td>
				<td colspan="1">
				<button type="button" onclick="eliminarArchivoOtros()">Eliminar</button><br><br>
				<button type="button" onclick="visualizaAdjuntoOtros()">Visualizar</button>
				</td>
			</tr> --%>
					
			<tr>
				<td colspan="1" ><br>
					<a title="" data-toggle="popover" id="idToolTipFechaPublicacion" class="btn-small icono-help" data-original-title="Fecha de publicaci&oacute;n"></a>
					Seleccionar fecha de publicaci&oacute;n
				</td>
				<td colspan="1">
					<form id="idFormFechaPublicacion">
						<br>
						<input type="text" id="fechaPublicacion" style="text-transform: uppercase; width: 100px" readonly="readonly"></input>
					</form>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="left">&nbsp;</td>
				<td colspan="1" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fecha de publicaci&oacute;n:</td>		
				<td colspan="1" align="left">
					<form id="idFormFechaPublicacionInfo">
						<input type="text" id="fechaPublicacionInfo" readonly="readonly" style="text-transform: uppercase; width: 80px">
					</form>
				</td>		
			</tr>
	
			<tr>
				<td colspan="2" align="left">&nbsp;</td>
				<td colspan="1" align="left">
				<a title="" data-toggle="popover" id="idToolTipFechaInicioPublicacion" class="btn-small icono-help" data-original-title="Fecha de inicio de publicaci&oacute;n"></a>
					Fecha de inicio publicaci&oacute;n:
				</td>		
				<td colspan="1" align="left">
					<form id="idFormFechaInicioPublicacion">
						<input type="text" id="fechaInicioPublicacion" readonly="readonly" style="width: 80px">
					</form>
				</td>		
			</tr>
				
			<tr>
				<td colspan="2" align="left">&nbsp;</td>
				<td colspan="1" align="left">
					<a title="" data-toggle="popover" id="idToolTipFechaFinPublicacion" class="btn-small icono-help" data-original-title="Fecha fin de la publicaci&oacute;n"></a>
					Fecha fin de publicaci&oacute;n:
				</td>		
				<td colspan="1" align="left">
					<form id="idFormFechaFinPublicacion">
						<input type="text" id="fechaFinPublicacion" readonly="readonly" style="width: 80px">
					</form>
				</td>		
			</tr>
	
			<tr>
				<td colspan="2" align="left">&nbsp;</td>
				<td colspan="1" align="left">
					<a title="" data-toggle="popover" id="idToolTipFechaRetiroPublicacion" class="btn-small icono-help" data-original-title="Fecha de retiro de publicaci&oacute;n"></a>
					Fecha de retiro:
				</td>		
				<td colspan="1" align="left">
					<form id="idFormFechaRetiro">
						<input type="text" id="fechaRetiro" readonly="readonly" style="width: 80px">
					</form>
				</td>		
			</tr>	
			
			<tr>
				<td colspan="4"><br></td>			
			</tr>	
			<tr>
				<th><button type="button"  onclick="limparDatosArchivo()">Limpiar</button></th>
				<th><button type="button" onclick="cambiarPagina(-1)">Regresar</button></th>		
				<th><button type="button" onclick="preFinalizar();">Siguiente</button></th>	
				<th><button type="button" onclick="salirAplicacion()">Salir</button></th>		
			</tr>	
		</tbody>
	</table>
	
	</div>
	
	
	
	
	<div class="well" style="background-color: white; width: 970px; font-size: 12px" align="center"  id="contenedorResumenReg">
       <form id="formaResumen">
        <table style="width: 100%; align=left">
        	<tbody align="left">
        	<tr>
        		<th width="300"><span id="idSpanAreaResponsableNotificacionResumen"></span></th>
	       		<td width="300">
	       			<input type="text" id="txtAreaResponsable" name="txtAreaResponsable" 
	       				   style="text-transform: uppercase; width: 250px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
	  		</tr>
	  		<tr>
	  			<td colspan="4">
	  				<div class="row-fluid">
						<div class="row-fluid">
							<div class="span8">
								<div class="separadorseccion">
									<span>
										 DATOS DEL SUJETO A NOTIFICAR
									</span>
								</div>
							</div>
						</div>
					</div>
				</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				&nbsp;
	  			</td>
	  		</tr>
	  		<tr>
        		<th>Nombre, denominaci&oacute;n o raz&oacute;n social: </th>
	       		<td>
	       			<input type="text" id="txtNombreDenominacionResumen" name="txtNombreDenominacionResumen" 
	       				   style="text-transform: uppercase; width: 500px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
	  		</tr>
	  			  		<tr>
        		
			   	<th>Registro patronal/N&uacute;mero de registro IMSS: </th>
			   	<td>
	       			<input type="text" id="txtRPRimss" name="txtRPRimss" 
	       				   style="text-transform: uppercase; width: 500px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
			   	<th></th>
			   	<td></td>
	  		</tr>
	  		<tr>
			   	<th>
			   		<label id="domicilioResumen"><b>Domicilio: </b></label>
			   	</th>
			   	<td>
	       			<textarea id="txtDomicilioFiscalResumen" name="txtDomicilioFiscalResumen" style="width: 500px; height:110px; resize:none;"  >
	   				</textarea>
			   	</td>
			   	<th></th>
			   	<td></td>
			</tr>
	  		<tr>
	  			<td colspan="4">
	  				<div class="row-fluid">
						<div class="row-fluid">
							<div class="span8">
								<div class="separadorseccion">
									<span>
										 DATOS DEL DOCUMENTO A NOTIFICAR
									</span>
								</div>
							</div>
						</div>
					</div>
				</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				&nbsp;
	  			</td>
	  		</tr>
	  		<tr>
        		<th>N&uacute;mero de oficio de acuerdo: </th>
	       		<td>
	       			<input type="text" id="txtNumeroOficioAcuerdo" name="txtNumeroOficioAcuerdo" 
	       				   style="text-transform: uppercase; width: 500px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
			   	<td>
			   	<input type="button" id="botonVisualizaOficioAcuerdo" value="Visualizar" onclick="visualizaDocumento('formaArchivoPrimero')"/>
			   	</td>
		
	  		</tr>

	  		<tr>
        		<th>N&uacute;mero de oficio o documento a notificar: </th>
	       		<td>
	       			<input type="text" id="txtOficioDocumentoNotificar" name="txtOficioDocumentoNotificar" 
	       				   style="text-transform: uppercase; width: 500px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
			   	<td><input type="button" id="botonVisualizaDocumentoNotificar" value="Visualizar" onclick="visualizaDocumento('formaArchivoSegundo')"/></td>
			   	<td>
	       			
			   	</td>
	  		</tr>
	  		<tr>

			   	<th>Tipo de documento: </th>
			   	<td colspan="3">
	       			<input type="text" id="txtTipoDocumento" name="txtTipoDocumento" 
	       				   style="text-transform: uppercase; width: 500px" size="30" maxlength="150" class="alfanumerico"/>
			   	</td>
			   				   	<th> </th>
			   	<td>
	       			
			   	</td>
	  		</tr>
	  		<!-- <tr>
        		
			   	<th>RP/RIMSS: </th>
			   	<td>
	       			<input type="text" id="txtRPRimss" name="txtRPRimss" 
	       				   style="text-transform: uppercase; width: 500px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
			   	<th></th>
			   	<td></td>
	  		</tr>
	  		<tr>

			   	<th>
			   		<label id="domicilioResumen">Domicilio</label> </th>
			   	<td>
				       			<textarea id="txtDomicilioFiscalResumen" name="txtDomicilioFiscalResumen"  
						   				  style="width: 500px; height:110px;" >
				   				</textarea>
			   	</td>
			   	<th></th>
			   	<td></td>
			</tr> -->
  			<!-- <tr id="trOtrosResumen">
  			
			   	<th>Otro tipo de documento: </th>
			   	<td>

						<select size="10" id="listaArchivosOtrosResumen">						  
						</select>
			   	</td>
			   	<th><button type="button" onclick="visualizaAdjuntoOtrosResumen()" id="botonVisualizaOtrosResumen">Visualizar</button></th>
			   	<td></td>
	  		</tr> -->
	  		<tr>
	  			<td colspan="4">
	  				<div class="row-fluid">
						<div class="row-fluid">
							<div class="span8">
								<div class="separadorseccion">
									<span>
										 FECHAS PARA LA PUBLICACI&Oacute;N
									</span>
								</div>
							</div>
						</div>
					</div>
				</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				&nbsp;
	  			</td>
	  		</tr>
	  		<tr>
        		<th>Fecha de publicaci&oacute;n: </th>
	       		<td>
	       			<input type="text" id="txtFechaPublicacionResumen" name="txtFechaPublicacion" 
	       				   style="text-transform: uppercase; width: 80px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
	  		</tr>
	  		<tr>
        		<th>Fecha de inicio de publicaci&oacute;n: </th>
	       		<td>
	       			<input type="text" id="txtFechaInicioPublicacionResumen" name="txtFechaInicioPublicacion" 
	       				   style="text-transform: uppercase; width: 80px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
	  		</tr>
	  		<tr>
        		<th>Fecha de fin de la publicaci&oacute;n: </th>
	       		<td>
	       			<input type="text" id="txtFechaFinPublicacionResumen" name="txtFechaFinPublicacion" 
	       				   style="text-transform: uppercase; width: 80px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
	  		</tr>
	  		<tr>
        		<th>Fecha de retiro: </th>
	       		<td>
	       			<input type="text" id="txtFechaRetiroResumen" name="txtFechaRetiro" 
	       				   style="text-transform: uppercase; width: 80px" size="30" maxlength="50" class="alfanumerico"/>
			   	</td>
	  		</tr>
	  		</tbody>
		</table>
		</form>
		<table width="900px">
		    <tr>
		    	<th><input type="button" id="btnRegresar" value="Regresar"  onclick="cambiarPagina(-1);" class="mboton"/></th>
		        <th><input type="button" id="btnRegistrar" value="Registrar" class="mboton" onclick="guardadoParcialNotificacion(true);"/></th>
		        <th><input type="button" id="btnSalir" value="Salir" class="mboton" onclick="salirAplicacion()"/></th>
		    </tr>
		</table>
	</div>
<div id="dialogoMensaje"></div>

<div id="visor">
   <form action="/firmaElectronicaWeb/widget/chfecyn/imss/buscaSeguimiento" class = "formNotBlock" method="post" target="firmaIframe" id="formaAcuse">
	    <input type="hidden" name="params" id="idTramite"/>
	</form>
	<iframe id="firmaIframe" name="firmaIframe" height="500" width="850" style="display: none;"></iframe>
</div>

 <form action="<%=request.getContextPath()%>/estrados/consultaInterna.do"  id="redireccionListado">
	    
	</form>

</body>
</html>
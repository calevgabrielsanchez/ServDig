<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/limpiaFormularios.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/validaciones.js"></script>
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/pagos/controlPantallaPagos.js"></script>
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/seguimiento/estudioCorreccion/pagos/pagosControlJSON.js"></script>
		
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/delta/generales/bloquearCrtl-V.js"></script>	
		
	<link href="<%=request.getContextPath()%>/resources/css/pagosSeguimiento/PagosStyle.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/resources/css/pagosSeguimiento/pagosTotalesStyle.css" rel="stylesheet" type="text/css">

					<fieldset>
					<form:form modelAttribute="pagosVO" action=""
												method="post" id="pagosVOForm">
						<table width="900"  border="0" cellspacing="0" cellpadding="0">
						  <tr>
						    <td class="darkGray">
						    							    	
						      <table width="100%"  border="0" cellspacing="1" cellpadding="2">
						        <tr>
						          <td height="50" colspan="2" class="headers">Datos Generales</td>
						          <td height="50" colspan="2" class="headers">C.O.P</td>
						          <td height="50" colspan="2" class="headers">R.C.V</td>
						          <td height="50" colspan="2" class="headers">Movimientos Afiliatorios </td>
						          <td width="8%" rowspan="2" align="center" valign="middle" bgcolor="#FFFFFF">
							          <table width="100%"  border="0" cellspacing="2" cellpadding="2">
							              <tr>
							                <td>&nbsp;</td>
							              </tr>
							              <tr>
							                <td><input type="button" name="addReg" id="addReg" value="Agregar" onClick="newRegistry()"></td>
							              </tr>
							              <tr>
							                <td><input type="button" name="saveData" id="saveData" value="Guardar" onClick="guardarPago()" disabled="disabled"></td>
							              </tr>
							              <tr>
							                <td><input type="button" name="modifyData" id="modifyData" value="Modificar" onClick="guardarPago()" disabled="disabled"></td>
							              </tr>
							              <tr>
							                <td><input type="button" name="deleteData" id="deleteData" value="Eliminar" onclick="eliminarPago()" disabled="disabled"></td>
							              </tr>
							              <tr>
							                <td><input type="button" name="sumarizado" id="sumarizado" value="Ver Sumarizado" onclick="mostrarVentanaSumarizadoPorRP()"></td>
							              </tr>
							              <tr>
							                <td><input type="button" name="exit" id="exit" value="Salir" onclick="window.close()"></td>
							              </tr>
							              <tr>
							                <td>&nbsp;</td>
							              </tr>
							          </table>
						          </td>
						        </tr>
						        <tr >
						          <td colspan="2" align="left" valign="top" class="lightGrey">
									<div id="datosGeneralesDiv">
									  <table width="100%"  border="0" cellspacing="1" cellpadding="2">
										<tr class="tdText" >
										  <td class="lightGrey">Registro Patronal </td>
										  <td class="lightGrey">
										  	<form:select path="pagoModel.cveAnexosolcorrpat" id="regitroPatronal" 
										  	            items="${pagosVO.mapaRegistrosPatronales}" disabled="true"></form:select>
										  </td>
										</tr>
										<tr class="tdText" >
										  <td class="lightGrey">Movimiento</td>
										  <td class="lightGrey">
										  	<form:select path="tipoMovimiento" id="tpMovimiento" items="${pagosVO.mapaTipoMovimiento}" disabled="true" onchange="movementType(this)"></form:select>
										  </td>
										</tr>
										<tr class="tdText" >
										  <td class="lightGrey">Folio SUA </td>
										  <td class="lightGrey">
										  	<form:input path="pagoModel.numFoliosua" id="sua" size="10" maxlength="6" onchange="paymentReference(1)" disabled="true"/>
										  </td>
										</tr>
										<tr class="tdText" >
										  <td class="lightGrey">Orden de Ingreso </td>
										  <td class="lightGrey">
										  	<form:input path="pagoModel.numOrdeningreso" id="oIngreso" size="10" maxlength="10" onchange="paymentReference(2)" disabled="true"/>
										  </td>
										</tr>
										<tr class="tdText" >
										  <td class="lightGrey">Num. Cr&eacute;dito </td>
										  <td class="lightGrey">
										  	<form:input path="pagoModel.numCredito" id="numCredito" size="10" maxlength="9" disabled="true"/>
										  </td>
										</tr>
										<tr class="tdText" >
										  <td class="lightGrey">Fecha de Pago </td>
										  <td class="lightGrey">
										  	<form:input path="fechaPagoStr" id="fechaPago" size="10" maxlength="10" readonly="true" disabled="true"/>
										  </td>
										</tr>
										<tr class="tdText" >
										  <td class="lightGrey">Tipo Documento </td>
										  <td class="lightGrey">
											  <form:select path="pagoModel.idTipodocto" id="tpDocto" items="${pagosVO.mapaTipoDocumento}" disabled="true" ></form:select>
										  </td>
										</tr>
									  </table>
								  </div>		  
								  </td>
						          <td colspan="2" valign="top" class="lightGrey" >
								  
								  <div id="copDiv">
									  <table width="100%"  border="0" cellspacing="1" cellpadding="2">
										  <tr class="tdText" >
											<td>Periodo</td>
											<td>
												<form:select path="copSelect" id="copSelect" items="${pagosVO.mapaPeriodoCOPMA}" disabled="true" onchange="shareComboValue(this)"></form:select>
											</td>
										  </tr>
										  <tr class="tdText" >
											<td >S.P</td>
											<td >$
												<form:input path="pagoModel.impCopsp" cssClass="inputMoney" id="SP_COP" size="10" maxlength="15" disabled="true"
															onkeyup="validaCampo('PermiteSoloNumerosYPunto','SP_COP','pagosVOForm')" 
															onblur="sumDivPayment(this,'copDiv','totalCOP')" 
															/>
											</td>
										  </tr>
										  <tr class="tdText" >
											<td >Act</td>
											<td >$
												<form:input path="pagoModel.impCopact" cssClass="inputMoney" id="ACT_COP" size="10" maxlength="15" disabled="true" 
															onkeyup="validaCampo('PermiteSoloNumerosYPunto','ACT_COP','pagosVOForm')" 
															onblur="sumDivPayment(this,'copDiv','totalCOP')"									
															/>
											</td>
										  </tr>
										  <tr class="tdText" >
											<td >Rec</td>
											<td >$
												<form:input path="pagoModel.impCoprec" cssClass="inputMoney" id="REC_COP" size="10" maxlength="15" disabled="true"
															onkeyup="validaCampo('PermiteSoloNumerosYPunto','REC_COP','pagosVOForm')" 
															onblur="sumDivPayment(this,'copDiv','totalCOP')" 
															/>
											</td>
										  </tr>
										  <tr class="tdText" >
											<td >Multas</td>
											<td >$
												<form:input path="pagoModel.impCopmulta" cssClass="inputMoney" id="MULTA_COP" size="10" maxlength="15" disabled="true" 
															onkeyup="validaCampo('PermiteSoloNumerosYPunto','MULTA_COP','pagosVOForm')"
															onblur="sumDivPayment(this,'copDiv','totalCOP')" 
															/>
											</td>
										  </tr>
										  <tr class="tdText" >
											<td >Total</td>
											<td >$
												<form:input path="pagoModel.impCoptot" cssClass="inputMoney" id="totalCOP" size="10" maxlength="15"  readonly="true" disabled="true"/>
											</td>
										  </tr>
									  </table>
								  </div>
								  </td>
						          <td colspan="2" valign="top" class="lightGrey" >
									 <div id="rcvDiv">		  
									   <table width="100%"  border="0" cellspacing="1" cellpadding="2">
										  <tr >
											<td class="tdText">Periodo</td>
											<td>
												<form:select path="rcvSelect" id="rcvSelect" items="${pagosVO.mapaPeriodoRCV}" disabled="true" onchange="shareComboValue(this)"></form:select>
										   </td>
										  </tr>
										  <tr >
											<td class="tdText" >S.P</td>
											<td class="tdText" >$
												<form:input path="pagoModel.impRcvsp" cssClass="inputMoney" id="SP_RCV" size="10" maxlength="15" disabled="true"
														 onkeyup="validaCampo('PermiteSoloNumerosYPunto','SP_RCV','pagosVOForm')"
														 onblur="sumDivPayment(this,'rcvDiv','totalRCV')" 
														 />
						                    </td>
										  </tr>
										  <tr >
											<td class="tdText" >Act</td>
											<td class="tdText" >$
												<form:input path="pagoModel.impRcvact" cssClass="inputMoney" id="ACT_RCV" size="10" maxlength="15"  disabled="true"
														onkeyup="validaCampo('PermiteSoloNumerosYPunto','ACT_RCV','pagosVOForm')" 
														onblur="sumDivPayment(this,'rcvDiv','totalRCV')"
														/>
						                    </td>
										  </tr>
										  <tr >
											<td class="tdText" >Rec</td>
											<td class="tdText" >$
												<form:input path="pagoModel.impRcvrec" cssClass="inputMoney" id="REC_RCV" size="10" maxlength="15" disabled="true" 
														onkeyup="validaCampo('PermiteSoloNumerosYPunto','REC_RCV','pagosVOForm')"
														onblur="sumDivPayment(this,'rcvDiv','totalRCV')" 
														/>
						                    </td>
										  </tr>
										  <tr >
											<td class="tdText" >Multas</td>
											<td class="tdText" >$
												<form:input path="pagoModel.impRcvmulta" cssClass="inputMoney" id="MULTA_RCV" size="10" maxlength="15" disabled="true" 
														onkeyup="validaCampo('PermiteSoloNumerosYPunto','MULTA_RCV','pagosVOForm')"
														onblur="sumDivPayment(this,'rcvDiv','totalRCV')" 
														/>
						                    </td>
										  </tr>
										  <tr >
											<td class="tdText" >Total</td>
											<td class="tdText" >$
												<form:input path="pagoModel.impRcvtot" cssClass="inputMoney" id="totalRCV" size="10" maxlength="15" onchange="sumDivPayment(this,'rcvDiv','totalRCV')" disabled="true"/>
						                    </td>
										  </tr>
									  </table>
								  	</div>
								  </td>
						          <td colspan="2" valign="top" class="lightGrey" >
									 <div id="maDiv">
									   <table width="100%"  border="0" cellspacing="1" cellpadding="2">
						                 <tr >
						                   <td width="48%" class="tdText">Periodo</td>
						                   <td width="52%">
						                   	<form:select path="maSelect" id="maSelect" items="${pagosVO.mapaPeriodoCOPMA}" disabled="true" onchange="shareComboValue(this)"></form:select>
						              	  </td>
						                 </tr>
						                 <tr >
						                   <td class="tdText" >Trab.Reg</td>
						                   <td><form:input cssClass="inputMoney" path="pagoModel.numTrabregula" id="trabReg" size="10" maxlength="5" disabled="true"  
						                   			onkeyup="validaCampo('PermiteSoloNumeros','trabReg','pagosVOForm')"
						                   			/>
						                   </td>
						                 </tr>
						                 <tr >
						                   <td class="tdText" >Altas</td>
						                   <td ><form:input cssClass="inputMoney" path="pagoModel.numAltas" id="trabAltas" size="10" maxlength="5"  disabled="true"
						                   			onkeyup="validaCampo('PermiteSoloNumeros','trabAltas','pagosVOForm')"
						                   			/>
						                   	</td>
						                 </tr>
						                 <tr >
						                   <td class="tdText" >Bajas</td>
						                   <td ><form:input cssClass="inputMoney" path="pagoModel.numBajas" id="trabBajas" size="10" maxlength="5"  disabled="true"
						                   			onkeyup="validaCampo('PermiteSoloNumeros','trabBajas','pagosVOForm')"
						                  			 />
						                  </td>
						                 </tr>
						                 <tr >
						                   <td class="tdText" >Modif. Salario</td>
						                   <td ><form:input cssClass="inputMoney" path="pagoModel.numModifsalario" id="modSalario" size="10" maxlength="5"  disabled="true"
						                   			onkeyup="validaCampo('PermiteSoloNumeros','modSalario','pagosVOForm')"
						                   			/>
						                   </td>
						                 </tr>
						                 <tr >
						                   <td >&nbsp;</td>
						                   <td >&nbsp;</td>
						                 </tr>
						               </table>
									</div>
								  </td>
						        </tr>
						      </table>
						    
						    </td>
						  </tr>
						</table>
							<form:hidden path="pagoModel.cvePresentacorr" id="cvePresentacorr"/>
					    	<form:hidden path="pagoModel.indTipopago" id="indTipopago"/>
					    	<form:hidden path="pagoModel.cveRevpagos" id="cveRevpagos"/>
					    	<form:hidden path="pagoModel.cveRegulaPagos" id="cveRegulapagos"/>
					    	<form:hidden path="fechaMinDateCalendar" id="fechaMinDateCalendar"/>

						</form:form>
					</fieldset>	
			
		<!-- Pagos a Detalle Realizados por autodeterminación e IMSS -->
		<br>
		<div id="tituloTotalGlobal" class="menu_principal" style="height: 2em !important; width: 900px;"> 
	      	<div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Total Global </a></li>
		          </ul>
		        </div> 
	      	</div>
        </div>
		<div id="pagosDetalleTotalesData" style="overflow: auto; width:900px;" align="center">
			<jsp:include page="pagosDetalleTotales.jsp" />
		</div>
		<br>
		<div id="tituloDetallePagos" class="menu_principal" style="height: 2em !important; width: 900px;"> 
	      	<div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Detalle de Pagos de los Registros Patronales </a></li>
		          </ul>
		        </div> 
	      	</div>
        </div>
		<div id="pagosDetalleData" style="background-color: white !important;border:solid 1px;border-color:black; width:900px;overflow: auto; width:900px; height:320px;" align="center">
			<jsp:include page="pagosDetalleDataGrid.jsp" />
		</div>
		
		<!-- Sumarizado de Registros patronales -->
		<br>
		<div id="tituloSumarizados" class="menu_principal" style="height: 2em !important; width: 900px;"> 
	      	<div align="center">
		        <div class="centrado">
		          <ul style="height: 1em !important;">
		            <li><a> Sumarizado por Registro Patronal </a></li>
		          </ul>
		        </div> 
	      	</div>
        </div>
		<div id="pagosDetallePorRPData" style="background-color: white !important;border:solid 1px;border-color:black; width:900px;overflow: auto; width:900px; height:320px;" align="center">
			<jsp:include page="sumDT.jsp" />
		</div>
		
		<!--  Diálogos de Control Pantalla Pagos -->
		<div id="dialogoBorrarPago"  align="center" style="display: none">
			<jsp:include page="pagosDialogoBorrar.jsp" />
		</div>
		
		
<script>setMinCalendarDay()</script>		

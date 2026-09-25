
var footer = '';

function initTabAnexoPagosGenerico(){

	 
	var idPromocion = $('#:checked').val();
	var dataTemporal = null;
	
	$("form#regularizarObraGenericoTABForm #cvePromocion").prop("value", '"'+idPromocion+'"');
	
	var sAnexoPagos = '{' +
	 '"cvePromocion":"'+idPromocion+'"}';
	  var anexoPagos = jQuery.parseJSON(sAnexoPagos);
	//consultar si la promocion ya tiene un pago 
	$.postJSON(jsContextoPromocion + "seguimiento/generico/consultaPago.do", anexoPagos, function(data) {
		$("form#regularizarObraGenericoTABForm #cvePromocion").val(data.cvePromocion);

		if(data.segRegularizaObraVo != null && data.segRegularizaObraVo.cveRegulaPagos != null && data.segRegularizaObraVo.cveRegulaPagos != ''){
			
			$("form#anexoPagosGenericoForm #cveRegulaPagosGral").val(data.segRegularizaObraVo.cveRegulaPagos);
			$("form#regularizarObraGenericoTABForm #cveRegulaPagos").val(data.segRegularizaObraVo.cveRegulaPagos != null?data.segRegularizaObraVo.cveRegulaPagos:0);
			$("form#regularizarObraGenericoTABForm #perRegularizaDel").val(data.segRegularizaObraVo.periodoRegDel);
			$("form#regularizarObraGenericoTABForm #perRegularizaAl").val(data.segRegularizaObraVo.periodoRegAl);
			
			//alert("data.segRegularizaObraVo.porcAvance" + data.segRegularizaObraVo.porcAvance);
			//alert("data.segRegularizaObraVo.porcRegularizado" + data.segRegularizaObraVo.porcRegularizado);
			//alert("data.segRegularizaObraVo.numParcialidades" + data.segRegularizaObraVo.numParcialidades);
			$("form#regularizarObraGenericoTABForm #porcentajeAvance").val(data.segRegularizaObraVo.porcAvance);
			$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").val(data.segRegularizaObraVo.porcRegularizado);
			$("form#regularizarObraGenericoTABForm #numeroParcialidades").val(data.segRegularizaObraVo.numParcialidades);
			
			//llenar periodo del tab de seguimiento
			$("form#seguimientoSaticbTABForm #fecSolCorrIniSaticb").val(data.segRegularizaObraVo.periodoRegDel);
			$("form#seguimientoSaticbTABForm #fecSolCorrFinSaticb").val(data.segRegularizaObraVo.periodoRegAl);
			
			
			var myNumber = Number(data.segRegularizaObraVo.trabRevisados);
			$("#regularizarObraGenericoTAB_saticb,form#regularizarObraGenericoTABForm,#trabRegularizadosRegObra").val(myNumber.formatMoney(0, '.', ','));
			$("form#regularizarObraGenericoTABForm #trabRevisados").val(myNumber.formatMoney(0, '.', ','));
			 myNumber = Number(data.segRegularizaObraVo.trabOmisos);
			$("form#regularizarObraGenericoTABForm #trabOmisosUni").val(myNumber.formatMoney(0, '.', ','));
			 myNumber = Number(data.segRegularizaObraVo.trabSubdeclarados);
			$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").val(myNumber.formatMoney(0, '.', ','));
			 myNumber = Number(data.segRegularizaObraVo.baseDeterminada);
			$("form#regularizarObraGenericoTABForm #baseDeterminada").val(myNumber.formatMoney(2, '.', ','));
			 myNumber = Number(data.segRegularizaObraVo.suertePpalDetCOP);
			$("form#regularizarObraGenericoTABForm #suertePpalDetCop").val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.segRegularizaObraVo.suertePpalDetRCV);
			$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val(myNumber.formatMoney(2, '.', ','));
			
			
			$("form#regularizarObraGenericoTABForm #trabRevisados").prop("readonly", "readonly");
			 
			$("form#regularizarObraGenericoTABForm #trabOmisosUni").prop("readonly", "readonly");
			 
			$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").prop("readonly", "readonly");
			 
			$("form#regularizarObraGenericoTABForm #baseDeterminada").prop("readonly", "readonly");
			 
			$("form#regularizarObraGenericoTABForm #suertePpalDetCop").prop("readonly", "readonly");
			
			$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").prop("readonly", "readonly");
			$("form#regularizarObraGenericoTABForm #porcentajeAvance").prop("readonly", "readonly");
			$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").prop("readonly", "readonly");
			$("form#regularizarObraGenericoTABForm #numeroParcialidades").prop("readonly", "readonly");

			
			$("form#regularizarObraGenericoTABForm #trabRevisados").removeClass("red");
			 
			$("form#regularizarObraGenericoTABForm #trabOmisosUni").removeClass("red");
			 
			$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").removeClass("red");
			$("form#regularizarObraGenericoTABForm #baseDeterminada").removeClass("red");
			 
			$("form#regularizarObraGenericoTABForm #suertePpalDetCop").removeClass("red");
			$("form#regularizarObraGenericoTABForm #porcentajeAvance").removeClass("red");
			$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").removeClass("red");
			$("form#regularizarObraGenericoTABForm #numeroParcialidades").removeClass("red");
			$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").removeClass("red");
			$("form#regularizarObraGenericoTABForm #btnGuardarRegularizaObra").prop("disabled", "disabled");
			
			$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").removeAttr('disabled');
			
			dataTemporal = data;

			obtenerTotalesPagosVersion2();
			desHabilitaCapturaFechasPeriodoRegulaObra();
		}else{
			$("form#anexoPagosGenericoForm #cveRegulaPagosGral").val('');
			$("form#regularizarObraGenericoTABForm #cveRegulaPagos").val('');
			$("form#regularizarObraGenericoTABForm #perRegularizaDel").val('');
			$("form#regularizarObraGenericoTABForm #perRegularizaAl").val('');
			$("form#regularizarObraGenericoTABForm #porcentajeAvance").val('');
			$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").val('');
			$("form#regularizarObraGenericoTABForm #numeroParcialidades").val('');
			
			
			$("#regularizarObraGenericoTAB_saticb,form#regularizarObraGenericoTABForm,#trabRegularizadosRegObra").val('');
			$("form#regularizarObraGenericoTABForm #trabRevisados").val('');
			$("form#regularizarObraGenericoTABForm #trabOmisosUni").val('');
			$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").val('');
			$("form#regularizarObraGenericoTABForm #baseDeterminada").val('');
			$("form#regularizarObraGenericoTABForm #suertePpalDetCop").val('');
			$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val('');
			
			$('form#regularizarObraGenericoTABForm #suertePrincipalCop').val('');//listo
			$('form#regularizarObraGenericoTABForm #suertePrincipalRcv').val('');//listo
			
			$('form#regularizarObraGenericoTABForm #actualizacionCop').val('');//listo
			$('form#regularizarObraGenericoTABForm #actualizacionRcv').val('');//listo
			
			$('form#regularizarObraGenericoTABForm #recargosCop').val('');//listo
			$('form#regularizarObraGenericoTABForm #recargosRcv').val('');//listo
		
			$('form#regularizarObraGenericoTABForm #multasCopSegProm').val('');//listo
			$('form#regularizarObraGenericoTABForm #multasRcvSegProm').val('');//listo
					
			$('form#regularizarObraGenericoTABForm #totalPagadoCop').val('');//listo
			$('form#regularizarObraGenericoTABForm #totalPagadoRcv').val('');//listo
			
			$('form#regularizarObraGenericoTABForm #suertePpalDetCop').val('');
			$('form#regularizarObraGenericoTABForm #suertePpalDetRcv').val('');
			
			$('form#regularizarObraGenericoTABForm #suertePpalPenPagoCop').val(''); 
			$('form#regularizarObraGenericoTABForm #suertePpalPenPagoRcv').val(''); 
		
			
			//deshabilitar
			//$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").prop('disabled','disabled');
			$("form#regularizarObraGenericoTABForm #btnGuardarRegularizaObra").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #perRegularizaDel").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #perRegularizaAl").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #porcentajeAvance").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #numeroParcialidades").removeAttr('disabled');
			
			$("form#regularizarObraGenericoTABForm #perRegularizaDel").prop('readonly','readonly');
			$("form#regularizarObraGenericoTABForm #perRegularizaAl").prop('readonly','readonly');
			$("form#regularizarObraGenericoTABForm #porcentajeAvance").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #porcentajeRegularizado").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #numeroParcialidades").prop('readonly','');
			
			//$("#regularizarObraGenericoTAB_saticb,form#regularizarObraGenericoTABForm,#trabRegularizadosRegObra").val('');
			$("form#regularizarObraGenericoTABForm #trabRevisados").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #trabOmisosUni").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #baseDeterminada").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #suertePpalDetCop").removeAttr('disabled');
			$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").removeAttr('disabled');
			
			$("form#regularizarObraGenericoTABForm #trabRevisados").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #trabOmisosUni").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #trabSubdeclaUni").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #baseDeterminada").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #suertePpalDetCop").prop('readonly','');
			$("form#regularizarObraGenericoTABForm #suertePpalDetRcv").prop('readonly','');
			
			//$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").removeAttr('disabled');
			
			$("form#regularizarObraGenericoTABForm #perRegularizaDel").datepicker( { dateFormat: 'dd-mm-yy' });
			$("form#regularizarObraGenericoTABForm #perRegularizaAl").datepicker( { dateFormat: 'dd-mm-yy' });
			
			
			
			
			/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
			 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy  */ 
			$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
				}).error(function(data){
					validarSesionExpirada(data);
				}).complete(function(data){
					$("form#regularizarObraGenericoTABForm #perRegularizaDel").datepicker('option', 'maxDate', data.responseText);
					$("form#regularizarObraGenericoTABForm #perRegularizaAl").datepicker('option', 'maxDate', data.responseText);
					
			});
			
			
			
		}
		
		
							
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(data){
		calcTotalTrabRegularizado();
		//Creacion de Datatable de Pagos Detalle
		
		
		//INICIA DT PAGOS
		/*oDtDatosPagosGenerico = $(idDatosPagosGenerico).dataTable({ borrar
			bJQueryUI : true,
			bFilter : false,
			bInfo:true,
			bSort: true,
			//bRetrieve:true,
			"bPaginate": true,
			"bAutoWidth" : false,
			"bServerSide" :	true,
			"bDestroy" : true,
			"iDeferLoading": 0,
			"aoColumns" : [ 
			{
				fnRender :function(oObj){
					var retVal = '<input type="radio" value="' +
					oObj.aData['cveRegulapagosdet'] +'" id="radioTablePagGen" class="radioDeteccion" name="radio" onClick="seleccionPagoDetGen()"/> ';
					return retVal;
				}, 
				aTargets: [0]
			}, {
				"sTitle" : "ID",
				"mDataProp" : "contador",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "RP",
				//"mDataProp" : "regpat",
				 "mDataProp": function ( source, type, val ) {
					 return $("form#regularizarObraGenericoTABForm #registroPatronalPagosDt").val();
				 },
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Folio SUA",
				"mDataProp" : "numFoliosua",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Orden Ingreso",
				"mDataProp" : "numOrdeningreso",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Numero de credito",
				"mDataProp" : "numCredito",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Fecha de pago",
				"mDataProp" : "fechaPago",
				"sClass":"dtCenterClassColumn"
			},{
				"sTitle" : "Tipo Docto",
				"mDataProp" : "idTipoDocto",
				"sClass":"dtCenterClassColumn"
			},{
				"sTitle" : "Periodo COP",
				"mDataProp" : "numPeriodoCop",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "SP COP",
				//"mDataProp" : "impCopsp",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impCopsp));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Act COP",
				//"mDataProp" : "impCopact",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impCopact != null ? source.impCopact:"0"));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Rec COP",
				//"mDataProp" : "impCoprec",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impCoprec));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Total COP",
				//"mDataProp" : "impTotalCop",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impTotalCop));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Multas COP",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impMultasCop));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Periodo RCV",
				"mDataProp" : "numPeriodoRcv",
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "SP RCV",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impRcvsp));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Act RCV",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impRcvact));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Rec RCV",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impRcvrec));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Total RCV",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impTotalRcv));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Multas RCV",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.impMultasRcv));
					 return myNumber.formatMoney(2, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Trab. Regularizados",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.numTrabajadoresRegulariza));
					 return myNumber.formatMoney(0, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Altas",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.numAltas));
					 return myNumber.formatMoney(0, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Bajas",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.numBajas));
					 return myNumber.formatMoney(0, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}, {
				"sTitle" : "Modif. Salario",
				 "mDataProp": function ( source, type, val ) {
					 var myNumber = Number(parseFloatComas(source.numModifSalario));
					 return myNumber.formatMoney(0, '.', ',');
				 },
				"sClass":"dtCenterClassColumn"
			}
				],
			"bProcessing" : false,
			"sAjaxSource" : '' +jsContextoPromocion + 'seguimiento/generico/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {	
				
				var idRegulaPagoGral = $('form#anexoPagosGenericoForm #cveRegulaPagosGral').val();
				
				var wrapper = new Object();
				wrapper.aoData = aoData;	
				
				
				if(idRegulaPagoGral != null && idRegulaPagoGral != '' ){
					
					var sCrtRegulaPago = '{"crtRegulapagos": {"cveRegulapagos":"'  +(idRegulaPagoGral != ''?idRegulaPagoGral:0)+'"}}';
					var oForm = jQuery.parseJSON(sCrtRegulaPago);
					

					wrapper.oForm = oForm;

					$.postJSON(sSource, wrapper, function(data) {
						fnCallback(data);
					}).complete(function(){					
						
						desbloquear();
					 }).error(function(datas){ 
							validarSesionExpirada(datas);				 
					});
				}
				
			},
			
			"fnFooterCallback": function ( nfoot, aaData, iStart, iEnd, aiDisplay ) {
				  			
	            var iTotalSPCop = 0;
	            var iTotalActCop = 0;
	            var iTotalRecCop = 0;
	            var iTotalTotCop = 0;
	            var iTotalMultasCop = 0;
	            
	            var iTotalSPRcv = 0;
	            var iTotalActRcv = 0;
	            var iTotalRecRcv = 0;
	            var iTotalTotRcv = 0;
	            var iTotalMultasRcv = 0;
	            
	            var iTotalTrabRegula = 0;
	            var iTotalAltas = 0;
	            var iTotalBajas = 0;
	            var iTotalModifSalario=0;
	            
	            for ( var i=0 ; i<aaData.length ; i++ )  {
	            	var pagoDetalle = aaData[i];
	            	iTotalSPCop = iTotalSPCop + (pagoDetalle.impCopsp)*1;
	            	iTotalActCop = iTotalActCop + (pagoDetalle.impCopact)*1;
	            	iTotalRecCop = iTotalRecCop + (pagoDetalle.impCoprec)*1;
	            	iTotalTotCop = iTotalTotCop + (pagoDetalle.impTotalCop)*1;	
	            	iTotalMultasCop = iTotalMultasCop + (pagoDetalle.impMultasCop)*1;
	            	
	            	
	            	iTotalSPRcv = iTotalSPRcv + (pagoDetalle.impRcvsp)*1;
	            	iTotalActRcv = iTotalActRcv + (pagoDetalle.impRcvact)*1;
	            	iTotalRecRcv = iTotalRecRcv + (pagoDetalle.impRcvrec)*1;
	            	iTotalTotRcv = iTotalTotRcv + (pagoDetalle.impTotalRcv)*1;
	            	iTotalMultasRcv = iTotalMultasRcv + (pagoDetalle.impMultasRcv)*1;
	            	
	            	iTotalTrabRegula = iTotalTrabRegula + (pagoDetalle.numTrabajadoresRegulariza)*1;
	            	iTotalAltas = iTotalAltas + (pagoDetalle.numAltas)*1;
	            	iTotalBajas = iTotalBajas + (pagoDetalle.numBajas)*1;
	            	iTotalModifSalario = iTotalModifSalario + (pagoDetalle.numModifSalario)*1;
	            }
	             
	                        
	            for ( var i=iStart ; i<iEnd ; i++ ){  //itera los registros encontrados
	                 // alert("aiDisplay[i]  " + aiDisplay[i]);
	            }
	             
	            /* agrega los totales en la seccion de la tabla footer	 
	            	
	            //footer = '<tfoot>' +
	            //'<tr> <td colspan="18">' +
	           // footer = '<tr>';
	             footer = ''+
	            	//<td>' +
	      	  		'<td colspan="2"  align="left">Total:</td>';
	            footer = footer + '<td>' + parseInt(iEnd) + '</td>';   //       1  
	            footer = footer + '<td style="width: 20px"></td>';
	            footer = footer + '<td style="width: 20px"></td> </td>';

	            var myNumber = Number(parseFloatComas(iTotalSPCop));
	            footer = footer + '<td><td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 4
	            
	            myNumber = Number(parseFloatComas(iTotalActCop));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>';       //5
	            
	            myNumber = Number(parseFloatComas(iTotalRecCop));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 6
	            
	            myNumber = Number(parseFloatComas(iTotalTotCop));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 7
	            
	            myNumber = Number(parseFloatComas(iTotalMultasCop));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 8      

	            footer = footer + '<td></td>'; //9
	            
	            myNumber = Number(parseFloatComas(iTotalSPRcv));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; //10
	            
	            myNumber = Number(parseFloatComas(iTotalActRcv));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 11
	            
	            myNumber = Number(parseFloatComas(iTotalRecRcv));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 12
	            
	            myNumber = Number(parseFloatComas(iTotalTotRcv));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>'; // 13
	            
	            myNumber = Number(parseFloatComas(iTotalMultasRcv));
	            footer = footer + '<td>' + myNumber.formatMoney(2, '.', ',') + '</td>';  // 14
	            
	            myNumber = Number(parseFloatComas(iTotalTrabRegula));
	            footer = footer + '<td style="width: 40px">' + myNumber.formatMoney(0, '.', ',') + '</td>';  // 15
	            
	            myNumber = Number(parseFloatComas(iTotalAltas));
	            footer = footer + '<td style="width: 20px">' + myNumber.formatMoney(0, '.', ',') + '</td>'; // 16
	            
	            myNumber = Number(parseFloatComas(iTotalBajas));
	            footer = footer + '<td style="width: 20px">' + myNumber.formatMoney(0, '.', ',') + '</td>';  // 17
	            
	            myNumber = Number(parseFloatComas(iTotalModifSalario));
	            footer = footer + '<td>' + myNumber.formatMoney(0, '.', ',') + '</td>';  // 18
	            
				footer = footer + '</td>';
				 //  + '  </tr> </tfoot>';

	            

	            //cop
	            //calcula total de Regula Pagos
	            var suertePpalDetCOP =parseFloatComas ($("form#regularizarObraGenericoTABForm #suertePpalDetCop").val()!='' ? $("form#regularizarObraGenericoTABForm #suertePpalDetCop").val():0);
	            myNumber = Number(parseFloatComas(iTotalSPCop));
	            $("form#regularizarObraGenericoTABForm #suertePrincipalCop").val(myNumber.formatMoney(0, '.', ','));
	            
	            var suertePpalPenPagoCop = suertePpalDetCOP - parseFloatComas(iTotalSPCop);
	            myNumber = Number(suertePpalPenPagoCop);
	            $("form#regularizarObraGenericoTABForm #suertePpalPenPagoCop").val(myNumber.formatMoney(2, '.', ','));
	            myNumber = Number(iTotalActCop);
	            $("form#regularizarObraGenericoTABForm #actualizacionCop").val(myNumber.formatMoney(0, '.', ','));
	            myNumber = Number(iTotalRecCop);
	            $("form#regularizarObraGenericoTABForm #recargosCop").val(myNumber.formatMoney(0, '.', ','));
	            myNumber = Number(iTotalTotCop);
	            $("form#regularizarObraGenericoTABForm #totalPagadoCop").val(myNumber.formatMoney(0, '.', ','));
	            
	            //rcv
	            var suertePpalDetRCV = parseFloatComas ($("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val()!='' ? $("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val():0);
	            myNumber = Number(parseFloatComas(iTotalSPRcv));
	            $("form#regularizarObraGenericoTABForm #suertePrincipalRcv").val(myNumber.formatMoney(0, '.', ','));
	            
	            var suertePpalPenPagoRcv = suertePpalDetRCV - parseFloatComas(iTotalSPRcv);
	            myNumber = Number(suertePpalPenPagoRcv);
	            $("form#regularizarObraGenericoTABForm #suertePpalPenPagoRcv").val(myNumber.formatMoney(2, '.', ','));
	            myNumber = Number(iTotalActRcv);
	            $("form#regularizarObraGenericoTABForm #actualizacionRcv").val(myNumber.formatMoney(0, '.', ','));
	            myNumber = Number(iTotalRecRcv);
	            $("form#regularizarObraGenericoTABForm #recargosRcv").val(myNumber.formatMoney(0, '.', ','));
	            myNumber = Number(iTotalTotRcv);
	            $("form#regularizarObraGenericoTABForm #totalPagadoRcv").val(myNumber.formatMoney(0, '.', ','));
	            
	            
	            validaBloquearPagos(suertePpalPenPagoCop,suertePpalPenPagoRcv);


	       	 $("#totalesPagos").html(footer );
	        }

		});*/
		
        // TERMINA DT PAGOS
		//calculo manual de los datos de solo lectura de REgulaObra
		//calculaTotalesPagosRegulaObraAlterno(dataTemporal);
		//limpia el ID de pago capturados anteriormente
		//obtenerTotalesPagosVersion2();
		//$("form#anexoPagosGenericoForm #cveRegulaPago").val("");
		
	});
	
	$("form#anexoPagosGenericoForm #fechaPagoGenerico").datepicker( { dateFormat: 'dd-mm-yy' });
	
	$("form#regularizarObraGenericoTABForm #perRegularizaDel").datepicker('option', 'beforeShowDay', null);
	$("form#regularizarObraGenericoTABForm #perRegularizaAl").datepicker('option', 'beforeShowDay', null);
	/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy
	 */ 
	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			$("form#anexoPagosGenericoForm #fechaPagoGenerico").datepicker('option', 'maxDate', data.responseText);
	});


}



function openDialogoPagosGenerico(){

    oDgDatosAnexoPagosGenericos.dialog("open");
}


function seleccionaCOP(){
	$("form#anexoPagosGenericoForm #labelTipoPagoCOPoRCV").html("");
	var seleccion = $("form#anexoPagosGenericoForm #seccionCOP").attr('checked');
	if(seleccion == 'checked'){
		//agrega estilo de Captura
		$("form#anexoPagosGenericoForm #periodoCOPGen").addClass("red");
		$("form#anexoPagosGenericoForm #spCOP").addClass("red");
		$("form#anexoPagosGenericoForm #actCOP").addClass("red");
		$("form#anexoPagosGenericoForm #recCOP").addClass("red");
		$("form#anexoPagosGenericoForm #multasCOP").addClass("red");
		
		$("form#anexoPagosGenericoForm #periodoCOPGen").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #spCOP").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #actCOP").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #recCOP").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #multasCOP").removeAttr('disabled');
		
		$("form#anexoPagosGenericoForm #spCOP").removeAttr("readonly"); 
		$("form#anexoPagosGenericoForm #actCOP").removeAttr("readonly"); 
		$("form#anexoPagosGenericoForm #recCOP").removeAttr("readonly"); 
		$("form#anexoPagosGenericoForm #multasCOP").removeAttr("readonly"); 
		
		
	}else{
		
		$("form#anexoPagosGenericoForm #periodoCOPGen").removeClass("red");
		$("form#anexoPagosGenericoForm #spCOP").removeClass("red");
		$("form#anexoPagosGenericoForm #actCOP").removeClass("red");
		$("form#anexoPagosGenericoForm #recCOP").removeClass("red");
		$("form#anexoPagosGenericoForm #multasCOP").removeClass("red");
		
		$("form#anexoPagosGenericoForm #periodoCOPGen").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #spCOP").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #actCOP").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #recCOP").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #multasCOP").prop("disabled", "disabled");
		
		$("form#anexoPagosGenericoForm #spCOP").prop("readonly","true");
		$("form#anexoPagosGenericoForm #actCOP").prop("readonly","true");
		$("form#anexoPagosGenericoForm #recCOP").prop("readonly","true");
		$("form#anexoPagosGenericoForm #multasCOP").prop("readonly","true");
		
		$("form#anexoPagosGenericoForm #periodoCOPGen").val("-1");
		$("form#anexoPagosGenericoForm #spCOP").val("");
		$("form#anexoPagosGenericoForm #actCOP").val("");
		$("form#anexoPagosGenericoForm #recCOP").val("");
		$("form#anexoPagosGenericoForm #multasCOP").val("");
		$("form#anexoPagosGenericoForm #totalCOP").val("");
		
		
		$("form#anexoPagosGenericoForm #labelSpCOP").html('');
		$("form#anexoPagosGenericoForm #labelPeriodoCOPGen").html('');
		
	}
	
}

function seleccionRCV(){
	
	$("form#anexoPagosGenericoForm #labelTipoPagoCOPoRCV").html("");
	var seleccion = $("form#anexoPagosGenericoForm #seccionRCV").attr('checked');
	if(seleccion == 'checked'){
		$("form#anexoPagosGenericoForm #periodoRCVGen").addClass("red");
		$("form#anexoPagosGenericoForm #spRCV").addClass("red");
		$("form#anexoPagosGenericoForm #actRCV").addClass("red");
		$("form#anexoPagosGenericoForm #recRCV").addClass("red");
		$("form#anexoPagosGenericoForm #multasRCV").addClass("red");
		
		$("form#anexoPagosGenericoForm #periodoRCVGen").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #spRCV").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #actRCV").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #recRCV").removeAttr('disabled');
		$("form#anexoPagosGenericoForm #multasRCV").removeAttr('disabled');
		
		$("form#anexoPagosGenericoForm #spRCV").removeAttr("readonly");
		$("form#anexoPagosGenericoForm #actRCV").removeAttr("readonly");
		$("form#anexoPagosGenericoForm #recRCV").removeAttr("readonly");
		$("form#anexoPagosGenericoForm #multasRCV").removeAttr("readonly");
		
		
	}else{
		$("form#anexoPagosGenericoForm #periodoRCVGen").removeClass("red");
		$("form#anexoPagosGenericoForm #spRCV").removeClass("red");
		$("form#anexoPagosGenericoForm #actRCV").removeClass("red");
		$("form#anexoPagosGenericoForm #recRCV").removeClass("red");
		$("form#anexoPagosGenericoForm #multasRCV").removeClass("red");
		
		$("form#anexoPagosGenericoForm #periodoRCVGen").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #spRCV").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #actRCV").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #recRCV").prop("disabled", "disabled");
		$("form#anexoPagosGenericoForm #multasRCV").prop("disabled", "disabled");
		
		
		$("form#anexoPagosGenericoForm #spRCV").prop("readonly","true");
		$("form#anexoPagosGenericoForm #actRCV").prop("readonly","true");
		$("form#anexoPagosGenericoForm #recRCV").prop("readonly","true");
		$("form#anexoPagosGenericoForm #multasRCV").prop("readonly","true");

		$("form#anexoPagosGenericoForm #periodoRCVGen").val("-1");
		$("form#anexoPagosGenericoForm #spRCV").val("");
		$("form#anexoPagosGenericoForm #actRCV").val("");
		$("form#anexoPagosGenericoForm #recRCV").val("");
		$("form#anexoPagosGenericoForm #multasRCV").val("");
		$("form#anexoPagosGenericoForm #totalRCV").val("");
		
		$("form#anexoPagosGenericoForm #labelSpRCV").html('');
		$("form#anexoPagosGenericoForm #labelperiodoRCVGen").html('');

	}
		
	
	
}
 

function salir(){
	
	limpiarPagoDetalle();
	
	oDgDatosAnexoPagosGenericos.dialog("close");
	
	
}


function procesaFormularioPagos(){
	var objForma = $("form#anexoPagosGenericoForm").toObject({mode:'first'});
	
	document.forms["anexoPagosGenericoForm"].action = jsContextoPromocion + "seguimiento/generico/guardarPagoDet.do";
	
	if (confirm(" Los datos son correctos ?")) {
		var formAction = $("form#anexoPagosGenericoForm").attr('action');
		if(validaCamposPagoDetalle()){
			bloquear();
			
			$.postJSON(formAction, objForma, function(data) {
				alert('El registro se actualizo correctamente');
			
			
			}).error(function(data){
				alert("error : " + data)
				desbloquear();
				validarSesionExpirada(data);
			}).complete(function(){
				//oDtDatosPagosGenerico.fnDraw();
				limpiarPagoDetalle();
				desbloquear();
			});	
		}
	}
	
	
	
}

function validaCamposPagoDetalle(){

	var regresa = true;
	var seleccionCop = $("form#anexoPagosGenericoForm #seccionCOP").attr('checked');
	var seleccionRcv = $("form#anexoPagosGenericoForm #seccionRCV").attr('checked');
	
	
	$("form#anexoPagosGenericoForm #labelFechaPagoGenerico").html('');
	$("form#anexoPagosGenericoForm #labelTipoDocto").html('');
	$("form#anexoPagosGenericoForm #labelSpRCV").html('');
	$("form#anexoPagosGenericoForm #labelSpCOP").html('');
	$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html('');
	$("form#anexoPagosGenericoForm #labelPeriodoCOPGen").html('');
	$("form#anexoPagosGenericoForm #labelperiodoRCVGen").html('');
	$("form#anexoPagosGenericoForm #labelTrabRegularizados").html('');
	$("form#anexoPagosGenericoForm #labelAltasPagos").html('');
	$("form#anexoPagosGenericoForm #labelBajasPagos").html('');
	
	//if(!longitudMandatoria($("form#anexoPagosGenericoForm #ordenIngreso").val(),10,"orden ingreso")) return false;
	if($("form#anexoPagosGenericoForm #ordenIngreso").val().length >0){
		if($("form#anexoPagosGenericoForm #ordenIngreso").val().length<10){
			//alert("orden ingreso : " + $("form#anexoPagosGenericoForm #ordenIngreso").val());
			$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html('<label class="etiquetaError">Capture minimo 10 digitos</label>');
			regresa= false;
		}
	}
	
	
	if($("form#anexoPagosGenericoForm #fechaPagoGenerico").val() == ''){
		$("form#anexoPagosGenericoForm #labelFechaPagoGenerico").html('<label class="etiquetaError">Campo Requerido</label>');
		//alert("fecha generico : " + ($("form#anexoPagosGenericoForm #fechaPagoGenerico").val()));
		regresa=false;
	}
	if($("form#anexoPagosGenericoForm #tipoDoctoPagos").val() == '-1'){
		$("form#anexoPagosGenericoForm #labelTipoDocto").html('<label class="etiquetaError">Campo Requerido</label>');
		//alert("tipoDoctoPagos : " + ($("form#anexoPagosGenericoForm #tipoDoctoPagos").val()));
		regresa=false;
	}
	
	if(seleccionRcv == 'checked'){
		if($("form#anexoPagosGenericoForm #spRCV").val() == '' || parseFloatComas($("form#anexoPagosGenericoForm #spRCV").val())<=0.00){
			$("form#anexoPagosGenericoForm #labelSpRCV").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("spRCV : " + ($("form#anexoPagosGenericoForm #spRCV").val()));
			regresa=false;
		}
		if($("form#anexoPagosGenericoForm #periodoRCVGen").val() == '-1'){
			$("form#anexoPagosGenericoForm #labelperiodoRCVGen").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("periodoRCVGen : " + ($("form#anexoPagosGenericoForm #periodoRCVGen").val()));
			regresa=false;
		}
		
	}
	
	if(seleccionCop == 'checked'){
		
		if($("form#anexoPagosGenericoForm #spCOP").val() == '' || parseFloatComas($("form#anexoPagosGenericoForm #spCOP").val())<=0.00 ){
			$("form#anexoPagosGenericoForm #labelSpCOP").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("spCOP : " + ($("form#anexoPagosGenericoForm #spCOP").val()));
			regresa=false;
		}
		if($("form#anexoPagosGenericoForm #periodoCOPGen").val() == '-1'){
			$("form#anexoPagosGenericoForm #labelPeriodoCOPGen").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("periodoCOPGen : " + ($("form#anexoPagosGenericoForm #periodoCOPGen").val()));
			regresa=false;
		}
		
	}
	
	if($("form#anexoPagosGenericoForm #folioSUAPagos").val() != '' && parseInt($("form#anexoPagosGenericoForm #folioSUAPagos").val(),10)>0 ){
		if($("form#anexoPagosGenericoForm #trabRegularizados").val() == ''){
			$("form#anexoPagosGenericoForm #labelTrabRegularizados").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("trabRegularizados 22: " + ($("form#anexoPagosGenericoForm #trabRegularizados").val()));
			regresa=false;
		}
		
	}
	var numTRabRegularizado = $("form#anexoPagosGenericoForm #trabRegularizados").val();
	
	
	if(numTRabRegularizado != '' && parseInt(numTRabRegularizado) > 0){
		if($("form#anexoPagosGenericoForm #altasPagos").val() == '' ){
			$("form#anexoPagosGenericoForm #labelAltasPagos").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("altasPagos : " + ($("form#anexoPagosGenericoForm #altasPagos").val()));
			regresa=false;
		}else{
			if(!(parseIntComas($("form#anexoPagosGenericoForm #altasPagos").val(),10)>=0)){
				$("form#anexoPagosGenericoForm #labelAltasPagos").html('<label class="etiquetaError">Debe ser mayor o igual a Cero</label>');
				//alert("altasPagos2 else : " + ($("form#anexoPagosGenericoForm #altasPagos").val()));
				regresa=false;
				
			}
			
			if(!(parseIntComas($("form#anexoPagosGenericoForm #altasPagos").val(),10)<= parseIntComas($("form#anexoPagosGenericoForm #trabRegularizados").val(),10))){
				$("form#anexoPagosGenericoForm #labelAltasPagos").html('<label class="etiquetaError">Debe ser menor o igual a Trab Regularizados</label>');
				//alert("altasPagos33 : " + ($("form#anexoPagosGenericoForm #altasPagos").val()));
				//alert(parseIntComas($("form#anexoPagosGenericoForm #trabRegularizados").val(),10));
				regresa=false;
			}
		}
		
		if($("form#anexoPagosGenericoForm #bajasPagos").val() == '' ){
			$("form#anexoPagosGenericoForm #labelBajasPagos").html('<label class="etiquetaError">Campo Requerido</label>');
			//alert("bajasPagos : " + ($("form#anexoPagosGenericoForm #bajasPagos").val()));
			regresa=false;
		}else{
			if(!(parseIntComas($("form#anexoPagosGenericoForm #bajasPagos").val(),10)>=0)){
				$("form#anexoPagosGenericoForm #labelBajasPagos").html('<label class="etiquetaError">Debe ser mayor o igual a Cero</label>');
				regresa=false;
				//alert("bajasPagos >0 : " + ($("form#anexoPagosGenericoForm #bajasPagos").val()));
			}
			
			if(!(parseIntComas($("form#anexoPagosGenericoForm #bajasPagos").val(),10)<= parseIntComas($("form#anexoPagosGenericoForm #altasPagos").val(),10))){
				$("form#anexoPagosGenericoForm #labelBajasPagos").html('<label class="etiquetaError">Debe ser menor o igual a Altas</label>');
				//alert("bajasPagos 3 : " + ($("form#anexoPagosGenericoForm #bajasPagos").val()));
				//alert("parse: " + parseIntComas($("form#anexoPagosGenericoForm #altasPagos").val(),10));
				regresa=false;
			}
			
		}
		//valida la suma de Alta + MOdif salario
		if(parseIntComas($("form#anexoPagosGenericoForm #trabRegularizados").val(),10)>0) {
			var altaEntero = parseIntComas($("form#anexoPagosGenericoForm #altasPagos").val() != ''?$("form#anexoPagosGenericoForm #altasPagos").val():"0",10);
			var modifSalario = parseIntComas($("form#anexoPagosGenericoForm #modifSalario").val() != ''?$("form#anexoPagosGenericoForm #modifSalario").val():"0",10); 
			var sumaDatos = altaEntero + modifSalario;
			if(!(sumaDatos>0)){
				//alert("SUMA : " + sumaDatos);
				//alert("La SUMA de la Alta mas Modif. Salario debe ser mayor a cero");
				regresa=false;
			}
			
		}
	}
	
	if( (seleccionCop == undefined)   && (seleccionRcv == undefined)){
		$("form#anexoPagosGenericoForm #labelTipoPagoCOPoRCV").html('<label class="etiquetaError">Debe seleccionar COP y/o RCV</label>');
		//alert("los cops undefinidos");
		regresa=false;
	}
	
	//alert("regresa: " + regresa);
	return regresa;
}

function limpiarPagoDetalle(){
	
	//elimina los msg de error
	$("form#anexoPagosGenericoForm #labelFechaPagoGenerico").html('');
	$("form#anexoPagosGenericoForm #labelTipoDocto").html('');
	$("form#anexoPagosGenericoForm #labelSpRCV").html('');
	$("form#anexoPagosGenericoForm #labelSpCOP").html('');
	$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html('');
	$("form#anexoPagosGenericoForm #labelPeriodoCOPGen").html('');
	$("form#anexoPagosGenericoForm #labelperiodoRCVGen").html('');
	$("form#anexoPagosGenericoForm #labelTrabRegularizados").html('');
	$("form#anexoPagosGenericoForm #labelAltasPagos").html('');
	$("form#anexoPagosGenericoForm #labelAltasPagos").html('');
	$("form#anexoPagosGenericoForm #labelBajasPagos").html('');
	$("form#anexoPagosGenericoForm #labelTipoPagoCOPoRCV").html('');
	
	
	//limpia los campos
	$("form#anexoPagosGenericoForm #folioSUAPagos").val("");
	$("form#anexoPagosGenericoForm #periodoCOPGen").val("-1");
	$("form#anexoPagosGenericoForm #periodoRCVGen").val(""-1);
	$("form#anexoPagosGenericoForm #ordenIngreso").val("");
	$("form#anexoPagosGenericoForm #spCOP").val("");
	$("form#anexoPagosGenericoForm #spRCV").val("");
	$("form#anexoPagosGenericoForm #trabRegularizados").val("");
	$("form#anexoPagosGenericoForm #numCredito").val("");
	$("form#anexoPagosGenericoForm #actCOP").val("");
	$("form#anexoPagosGenericoForm #actRCV").val("");
	$("form#anexoPagosGenericoForm #altasPagos").val("");
	$("form#anexoPagosGenericoForm #fechaPagoGenerico").val("");
	$("form#anexoPagosGenericoForm #recCOP").val("");
	$("form#anexoPagosGenericoForm #recRCV").val("");
	$("form#anexoPagosGenericoForm #bajasPagos").val("");
	$("form#anexoPagosGenericoForm #tipoDoctoPagos").val("-1");
	$("form#anexoPagosGenericoForm #totalCOP").val("");
	$("form#anexoPagosGenericoForm #totalRCV").val("");
	$("form#anexoPagosGenericoForm #modifSalario").val("");
	$("form#anexoPagosGenericoForm #multasCOP").val("");
	$("form#anexoPagosGenericoForm #multasRCV").val("");
	//hiden del pago 
	$("form#anexoPagosGenericoForm #cveRegulaPago").val("");
	
	
	//required
	$("#spanReqTRabRegula").hide('fast');
	$("#spnAltasPagos").hide('fast');
	$("#spnReqBajasPagos").hide('fast');
	
	//estilos cop
	$("form#anexoPagosGenericoForm #periodoCOPGen").removeClass("red");
	$("form#anexoPagosGenericoForm #spCOP").removeClass("red");
	$("form#anexoPagosGenericoForm #actCOP").removeClass("red");
	$("form#anexoPagosGenericoForm #recCOP").removeClass("red");
	$("form#anexoPagosGenericoForm #multasCOP").removeClass("red");
	
	$("form#anexoPagosGenericoForm #periodoCOPGen").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #spCOP").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #actCOP").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #recCOP").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #multasCOP").prop("disabled", "disabled");
	
	$("form#anexoPagosGenericoForm #spCOP").prop("readonly","true");
	$("form#anexoPagosGenericoForm #actCOP").prop("readonly","true");
	$("form#anexoPagosGenericoForm #recCOP").prop("readonly","true");
	$("form#anexoPagosGenericoForm #multasCOP").prop("readonly","true");
	
	$("form#anexoPagosGenericoForm #periodoCOPGen").val("-1");
	$("form#anexoPagosGenericoForm #spCOP").val("");
	$("form#anexoPagosGenericoForm #actCOP").val("");
	$("form#anexoPagosGenericoForm #recCOP").val("");
	$("form#anexoPagosGenericoForm #multasCOP").val("");
	$("form#anexoPagosGenericoForm #totalCOP").val("");
	
	//estilos RCV
	
	$("form#anexoPagosGenericoForm #periodoRCVGen").removeClass("red");
	$("form#anexoPagosGenericoForm #spRCV").removeClass("red");
	$("form#anexoPagosGenericoForm #actRCV").removeClass("red");
	$("form#anexoPagosGenericoForm #recRCV").removeClass("red");
	$("form#anexoPagosGenericoForm #multasRCV").removeClass("red");
	
	$("form#anexoPagosGenericoForm #periodoRCVGen").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #spRCV").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #actRCV").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #recRCV").prop("disabled", "disabled");
	$("form#anexoPagosGenericoForm #multasRCV").prop("disabled", "disabled");
	
	
	$("form#anexoPagosGenericoForm #spRCV").prop("readonly","true");
	$("form#anexoPagosGenericoForm #actRCV").prop("readonly","true");
	$("form#anexoPagosGenericoForm #recRCV").prop("readonly","true");
	$("form#anexoPagosGenericoForm #multasRCV").prop("readonly","true");

	$("form#anexoPagosGenericoForm #periodoRCVGen").val("-1");
	$("form#anexoPagosGenericoForm #spRCV").val("");
	$("form#anexoPagosGenericoForm #actRCV").val("");
	$("form#anexoPagosGenericoForm #recRCV").val("");
	$("form#anexoPagosGenericoForm #multasRCV").val("");
	
	$("form#anexoPagosGenericoForm #labelSpRCV").html('');
	$("form#anexoPagosGenericoForm #labelperiodoRCVGen").html('');
	
	//quitar check
	$("form#anexoPagosGenericoForm #seccionCOP").attr('checked', false);
	$("form#anexoPagosGenericoForm #seccionRCV").attr('checked', false);
	
	
}

function longitudMandatoriaOrdenIngreso(campo,longitud,leyendaCampo){
	campo = campo +"";
	var mensaje = ""
	var respuesta = false;
		if(campo.length<longitud){
		$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html("<label class='etiquetaError'>La longitud del campo "+leyendaCampo+" debe ser minimo de "+longitud + "</label>");
		
		respuesta = false;
	}else {
		respuesta = true;
	}
	
	return respuesta;
}

function calculaTotalCop(){
	var varSpCOP = 0;
	var varActCOP = 0;
	var varRecCOP = 0;
	var totalCop = 0;
	if($("form#anexoPagosGenericoForm #spCOP").val() != ''){
		varSpCOP = parseFloatComas($("form#anexoPagosGenericoForm #spCOP").val(),10);
	}
	 if($("form#anexoPagosGenericoForm #actCOP").val() != ''){
		 varActCOP = parseFloatComas($("form#anexoPagosGenericoForm #actCOP").val(),10);
	 }
	if($("form#anexoPagosGenericoForm #recCOP").val() != ''){
		varRecCOP = parseFloatComas($("form#anexoPagosGenericoForm #recCOP").val(),10)
	}
	 
	
	
	totalCop = varSpCOP + varActCOP	+ varRecCOP	;
	var myNumber = Number(totalCop);
	$("form#anexoPagosGenericoForm #totalCOP").val(myNumber.formatMoney(2, '.', ','));
	
	
}


function calculaTotalRcv(){
	var varSpRCV = 0;
	var varActRCV = 0;
	var varRecRCV = 0;
	var totalRCV = 0;
	if($("form#anexoPagosGenericoForm #spRCV").val() != ''){
		varSpRCV = parseFloatComas($("form#anexoPagosGenericoForm #spRCV").val(),10);
	}
	 if($("form#anexoPagosGenericoForm #actRCV").val() != ''){
		 varActRCV = parseFloatComas($("form#anexoPagosGenericoForm #actRCV").val(),10);
	 }
	if($("form#anexoPagosGenericoForm #recRCV").val() != ''){
		varRecRCV = parseFloatComas($("form#anexoPagosGenericoForm #recRCV").val(),10)
	}
	 
	
	
	totalRCV = varSpRCV + varActRCV	+ varRecRCV	;
	
	var myNumber = Number(totalRCV);
	$("form#anexoPagosGenericoForm #totalRCV").val(myNumber.formatMoney(2, '.', ','));
	
}


function completaAccionSUA(){

	if($("form#anexoPagosGenericoForm #folioSUAPagos").val() != '' && parseInt($("form#anexoPagosGenericoForm #folioSUAPagos").val(),10)>0 ){
		$("#spanReqTRabRegula").show('fast');
	}else{
		$("#spanReqTRabRegula").hide('fast');
		$("form#anexoPagosGenericoForm #labelTrabRegularizados").html('');
	}
	
	
}

function completaTrabRegulariza(){
	var numTRabRegularizado = $("form#anexoPagosGenericoForm #trabRegularizados").val();
	
	if(numTRabRegularizado != '' && parseInt(numTRabRegularizado) > 0){
	
		$("#spnAltasPagos").show('fast');
		$("#spnReqBajasPagos").show('fast');
		
	}else{
	
		$("#spnAltasPagos").hide('fast');
		$("form#anexoPagosGenericoForm #labelAltasPagos").html('');
		$("#spnReqBajasPagos").hide('fast');
		$("form#anexoPagosGenericoForm #labelBajasPagos").html('');
	}
	
	
}

function seleccionPagoDetGen(){
	
	limpiarPagoDetalle();
	var idRegulaPagoDetalle= $("form#anexoPagosGenericoForm #:checked").val();
	
	var sPagoDetalle = '{"cveRegulaPago":'+idRegulaPagoDetalle+'}';
	var objPagoDetalle = jQuery.parseJSON(sPagoDetalle);
	bloquear();
	$.postJSON(jsContextoPromocion + "seguimiento/generico/consultaPagoDetalle.do", objPagoDetalle, function(data) {
		
		$("form#anexoPagosGenericoForm #cveRegulaPagosGral").val(data.crtRegulapagos.cveRegulapagos);
		$("form#anexoPagosGenericoForm #cveRegulaPago").val(data.cveRegulapagosdet);
		$("form#anexoPagosGenericoForm #folioSUAPagos").val(data.numFoliosua);
		$("form#anexoPagosGenericoForm #ordenIngreso").val(data.numOrdeningreso);
		$("form#anexoPagosGenericoForm #numCredito").val(data.numCredito);
		$("form#anexoPagosGenericoForm #trabRegularizados").val(data.numTrabajadoresRegulariza);
		$("form#anexoPagosGenericoForm #altasPagos").val(data.numAltas);
		$("form#anexoPagosGenericoForm #fechaPagoGenerico").val(data.fechaPago);
		$("form#anexoPagosGenericoForm #bajasPagos").val(data.numBajas);
		$("form#anexoPagosGenericoForm #tipoDoctoPagos").val(data.idTipoDocto);
		$("form#anexoPagosGenericoForm #modifSalario").val(data.numModifSalario);
		

		if(data.numPeriodoCop != null){
			$("form#anexoPagosGenericoForm #seccionCOP").attr('checked', true);
			
			//enabled
			$("form#anexoPagosGenericoForm #periodoCOPGen").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #spCOP").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #actCOP").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #recCOP").removeAttr('disabled');
			//$("form#anexoPagosGenericoForm #totalCOP").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #multasCOP").removeAttr('disabled');
			
			//estilo
			$("form#anexoPagosGenericoForm #periodoCOPGen").addClass("red");
			$("form#anexoPagosGenericoForm #spCOP").addClass("red");
			$("form#anexoPagosGenericoForm #actCOP").addClass("red");
			$("form#anexoPagosGenericoForm #recCOP").addClass("red");
			//$("form#anexoPagosGenericoForm #totalCOP").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #multasCOP").addClass("red");
			
			//valores
			$("form#anexoPagosGenericoForm #periodoCOPGen").val(data.numPeriodoCop);
			$("form#anexoPagosGenericoForm #spCOP").val(data.impCopsp);
			$("form#anexoPagosGenericoForm #actCOP").val(data.impCopact);
			$("form#anexoPagosGenericoForm #recCOP").val(data.impCoprec);
			$("form#anexoPagosGenericoForm #totalCOP").val(data.impTotalCop);
			$("form#anexoPagosGenericoForm #multasCOP").val(data.impMultasCop);
			
			$("form#anexoPagosGenericoForm #spCOP").removeAttr("readonly"); 
			$("form#anexoPagosGenericoForm #actCOP").removeAttr("readonly"); 
			$("form#anexoPagosGenericoForm #recCOP").removeAttr("readonly"); 
			$("form#anexoPagosGenericoForm #multasCOP").removeAttr("readonly"); 
			
		}
		
		
		if(data.numPeriodoRcv != null){
			$("form#anexoPagosGenericoForm #seccionRCV").attr('checked', true);
			//enabled
			$("form#anexoPagosGenericoForm #periodoRCVGen").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #spRCV").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #actRCV").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #recRCV").removeAttr('disabled');
			//$("form#anexoPagosGenericoForm #totalRCV").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #multasRCV").removeAttr('disabled');
			
			//estulos
			$("form#anexoPagosGenericoForm #periodoRCVGen").addClass("red");
			$("form#anexoPagosGenericoForm #spRCV").addClass("red");
			$("form#anexoPagosGenericoForm #actRCV").addClass("red");
			$("form#anexoPagosGenericoForm #recRCV").addClass("red");
			//$("form#anexoPagosGenericoForm #totalRCV").removeAttr('disabled');
			$("form#anexoPagosGenericoForm #multasRCV").addClass("red");
			
			$("form#anexoPagosGenericoForm #spRCV").removeAttr("readonly");
			$("form#anexoPagosGenericoForm #actRCV").removeAttr("readonly");
			$("form#anexoPagosGenericoForm #recRCV").removeAttr("readonly");
			$("form#anexoPagosGenericoForm #multasRCV").removeAttr("readonly");
			
			
			//valores
			$("form#anexoPagosGenericoForm #periodoRCVGen").val(data.numPeriodoRcv);
			$("form#anexoPagosGenericoForm #spRCV").val(data.impRcvsp);
			$("form#anexoPagosGenericoForm #actRCV").val(data.impRcvact);
			$("form#anexoPagosGenericoForm #recRCV").val(data.impRcvrec);
			$("form#anexoPagosGenericoForm #totalRCV").val(data.impTotalRcv);
			$("form#anexoPagosGenericoForm #multasRCV").val(data.impMultasRcv);

		}
		
		
		
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
		desbloquear();
	});
}


function eliminaPagoDet(){
	
	var regulaPagoDetalleChecked= $("form#anexoPagosGenericoForm #:checked").val();
	var idRegulaPagoDetalle =  $("form#anexoPagosGenericoForm #cveRegulaPago").val(); 
	
	if(regulaPagoDetalleChecked != null){
		
		
		if(confirm('Se va a Eliminar el pago con el ID = ' + idRegulaPagoDetalle + '. Desea Continuar?')){
			bloquear();
			var sPagoDetalle = '{"cveRegulaPago":'+idRegulaPagoDetalle+'}';
			var objPagoDetalle = jQuery.parseJSON(sPagoDetalle);
			$.postJSON(jsContextoPromocion + "seguimiento/generico/eliminaPagoDetalle.do", objPagoDetalle, function(data) {
				
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
				limpiarPagoDetalle();
				//oDtDatosPagosGenerico.fnDraw();
				desbloquear();
			});
		}
	
		
	}else{
		alert("Debe seleccionar el pago que desea eliminar.");
		
	}
}



function validaBloquearPagos(suertePpalPenPagoCop,suertePpalPenPagoRcv){
	
	
	var suertePrincipalDetCop =parseFloatComas( $("form#regularizarObraGenericoTABForm #suertePpalDetCop").val() != ''?$("form#regularizarObraGenericoTABForm #suertePpalDetCop").val():0 ,10);
	var suertePrincipalDetRcv =parseFloatComas( $("form#regularizarObraGenericoTABForm #suertePpalDetCop").val() != ''?$("form#regularizarObraGenericoTABForm #suertePpalDetCop").val():0 ,10);
	

	if((suertePpalPenPagoCop <= 0 && suertePrincipalDetCop != '' && suertePrincipalDetCop != "0" && suertePrincipalDetCop != "NaN") || 
			suertePpalPenPagoRcv <= 0 && suertePrincipalDetRcv != '' && suertePrincipalDetRcv != "0" && suertePrincipalDetRcv != "NaN" ){
	//bloquear todo
		$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").prop('disabled','disabled');
		$('form#regularizarObraGenericoTABForm input[type=text]').prop('readonly','readonly');
		$('form#regularizarObraGenericoTABForm input[type=hidden]').prop('disabled','disabled');
		$('form#regularizarObraGenericoTABForm input[type=text]').removeClass("red");
		desHabilitaCapturaFechasPeriodoRegulaObra ();
	}else{
		if(suertePpalPenPagoCop > 0 || suertePpalPenPagoRcv > 0){
			$("form#regularizarObraGenericoTABForm #btnDatosRegularizacion").removeAttr('disabled');
		}
	}
	
}

function limpiaFolioSUA(){
	$("form#anexoPagosGenericoForm #folioSUAPagos").val('');
}

function limpiaOrdenIngreso(){
	$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html("");
	$("form#anexoPagosGenericoForm #ordenIngreso").val('');
}

/*
function calculaTotalesPagosRegulaObraAlterno(data){
	
	 var iTotalSPCop = 0;
     var iTotalActCop = 0;
     var iTotalRecCop = 0;
     var iTotalTotCop = 0;
     var iTotalMultasCop = 0;
     
     var iTotalSPRcv = 0;
     var iTotalActRcv = 0;
     var iTotalRecRcv = 0;
     var iTotalTotRcv = 0;
     var iTotalMultasRcv = 0;
     
     var iTotalTrabRegula = 0;
     var iTotalAltas = 0;
     var iTotalBajas = 0;
     var iTotalModifSalario=0;

     if(data != null && data.segRegularizaObraVo != null && data.segRegularizaObraVo.listaPagosDetalle != null && data.segRegularizaObraVo.listaPagosDetalle.length >0){
	
			for(var i = 0 ; i < data.segRegularizaObraVo.listaPagosDetalle.length ; i++){
				
				var pagoDetalle = data.segRegularizaObraVo.listaPagosDetalle[i];
				
			     iTotalSPCop = iTotalSPCop + (pagoDetalle.impCopsp)*1;
			     iTotalActCop = iTotalActCop + (pagoDetalle.impCopact)*1;
			     iTotalRecCop = iTotalRecCop + (pagoDetalle.impCoprec)*1;
			     iTotalTotCop = iTotalTotCop + (pagoDetalle.impTotalCop)*1;
			     iTotalMultasCop = iTotalMultasCop + (pagoDetalle.impMultasCop)*1;
			     
			     
			     iTotalSPRcv = iTotalSPRcv + (pagoDetalle.impRcvsp)*1;
			     iTotalActRcv = iTotalActRcv + (pagoDetalle.impRcvact)*1;
			     iTotalRecRcv = iTotalRecRcv + (pagoDetalle.impRcvrec)*1;
			     iTotalTotRcv = iTotalTotRcv + (pagoDetalle.impTotalRcv)*1;
			     iTotalMultasRcv = iTotalMultasRcv + (pagoDetalle.impMultasRcv)*1;
			     	
			     iTotalTrabRegula = iTotalTrabRegula + (pagoDetalle.numTrabajadoresRegulariza)*1;
			     iTotalAltas = iTotalAltas + (pagoDetalle.numAltas)*1;
			     iTotalBajas = iTotalBajas + (pagoDetalle.numBajas)*1;
			     iTotalModifSalario = iTotalModifSalario + (pagoDetalle.numModifSalario)*1;
			     
				
			}
	
	     //cop
	     //calcula total de Regula Pagos
	     var suertePpalDetCOP =parseFloatComas ($("form#regularizarObraGenericoTABForm #suertePpalDetCop").val()!='' ? $("form#regularizarObraGenericoTABForm #suertePpalDetCop").val():0);
	     
	     myNumber = Number(parseFloatComas(iTotalSPCop));
	     
	     $("form#regularizarObraGenericoTABForm #suertePrincipalCop").val(myNumber.formatMoney(0, '.', ','));
	     
	     var suertePpalPenPagoCop = suertePpalDetCOP - parseFloatComas(iTotalSPCop);
	     myNumber = Number(suertePpalPenPagoCop);
	     $("form#regularizarObraGenericoTABForm #suertePpalPenPagoCop").val(myNumber.formatMoney(0, '.', ','));
	     myNumber = Number(iTotalActCop);
	     $("form#regularizarObraGenericoTABForm #actualizacionCop").val(myNumber.formatMoney(0, '.', ','));
	     myNumber = Number(iTotalRecCop);
	     $("form#regularizarObraGenericoTABForm #recargosCop").val(myNumber.formatMoney(0, '.', ','));
	     myNumber = Number(iTotalTotCop);
	     $("form#regularizarObraGenericoTABForm #totalPagadoCop").val(myNumber.formatMoney(0, '.', ','));
	     
	     //rcv
	     var suertePpalDetRCV = parseFloatComas ($("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val()!='' ? $("form#regularizarObraGenericoTABForm #suertePpalDetRcv").val():0);
	     myNumber = Number(parseFloatComas(iTotalSPRcv));
	     $("form#regularizarObraGenericoTABForm #suertePrincipalRcv").val(myNumber.formatMoney(0, '.', ','));
	     
	     var suertePpalPenPagoRcv = suertePpalDetRCV - parseFloatComas(iTotalSPRcv);
	     myNumber = Number(suertePpalPenPagoRcv);
	     $("form#regularizarObraGenericoTABForm #suertePpalPenPagoRcv").val(myNumber.formatMoney(0, '.', ','));
	     myNumber = Number(iTotalActRcv);
	     $("form#regularizarObraGenericoTABForm #actualizacionRcv").val(myNumber.formatMoney(0, '.', ','));
	     myNumber = Number(iTotalRecRcv);
	     $("form#regularizarObraGenericoTABForm #recargosRcv").val(myNumber.formatMoney(0, '.', ','));
	     myNumber = Number(iTotalTotRcv);
	     $("form#regularizarObraGenericoTABForm #totalPagadoRcv").val(myNumber.formatMoney(0, '.', ','));
	     
	     
	     
	     //validaBloquearPagos(suertePpalPenPagoCop,suertePpalPenPagoRcv);
     }
	
	

}*/


function validaMinimoOrdenIng(){
	$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html('');
	var ordenIngreso = $("form#anexoPagosGenericoForm #ordenIngreso").val();
	if(ordenIngreso != ''){
		if(ordenIngreso.length < 6){
			$("form#anexoPagosGenericoForm #labelOrdenIngresoGen").html("<label class='etiquetaError'>La longitud del campo  debe ser minimo de 6 de Digitos</label>");
			
		}	
	}	
}

function jsBorraLabelAltas(){
	$('form#anexoPagosGenericoForm #labelAltasPagos').html('');
}


function jsBorraLabelBajas(){
	$('form#anexoPagosGenericoForm #labelBajasPagos').html('');
}

function jsLimpiaLabelFecPagoGen(){
	$('form#anexoPagosGenericoForm #labelFechaPagoGenerico').html('');
}

function jsLimpiaLabelTipoDocto(){
	$('form#anexoPagosGenericoForm #labelTipoDocto').html('');
	
	
}

function jsLimpiaLabelPeriodoCOPGen(){
	$('form#anexoPagosGenericoForm #labelPeriodoCOPGen').html('');
	
}

function jsLimpiaLabelperiodoRCVGen(){
	$('form#anexoPagosGenericoForm #labelperiodoRCVGen').html('');
} 

function jsLimpiaLabelSpCOP(){
	$('form#anexoPagosGenericoForm #labelSpCOP').html('');
	
}

function jsLimpiaLabelSpRCV(){
	
	$('form#anexoPagosGenericoForm #labelSpRCV').html('');
}

function jsBorraLabelTrabRegula(){
	$('form#anexoPagosGenericoForm #labelTrabRegularizados').html('');
}
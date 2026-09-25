$(document).ready(
	function() {	
		var indiceHistoriaLaboral = ($('#datosHistoriaLaboralGrid tr').length - 1);

		$().dateSelectBoxes({
			monthElement : $('#regmesFechaInscripcion'),
			dayElement : $('#regdiaFechaInscripcion'),
			yearElement : $('#reganioFechaInscripcion'),
			monthLabel : "Mes",
			yearLabel : "A\u00F1o",
			dayLabel : "D\u00EDa",
			generateOptions : true,
			keepLabels : true
			//Default: false
		});

		$().dateSelectBoxes({
			monthElement : $('#regmesFechaBaja'),
			dayElement : $('#regdiaFechaBaja'),
			yearElement : $('#reganioFechaBaja'),
			monthLabel : "Mes",
			yearLabel : "A\u00F1o",
			dayLabel : "D\u00EDa",
			generateOptions : true,
			keepLabels : true
		});

		$('#agregarHistoriaLaboral').click(
				function() {
					
					var registroNombrePatron = $("#registronombrePatron").val().toUpperCase();
					var registroEntidadFederativa = $("#registroentidadFederativa").val() === '-1' ? '': $("#registroentidadFederativa option:selected").text();
					var entidadFederativaId = $("#registroentidadFederativa").val() === '-1' ? '': $("#registroentidadFederativa option:selected").val();
					var registroFechaInscripcion = obtenerFecha("FechaInscripcion");
                                        var registroDiaFechaInscripcion = $("#regdiaFechaInscripcion").val() === '0' ? '': $("#regdiaFechaInscripcion option:selected").val();
                                        var registroMesFechaInscripcion = $("#regmesFechaInscripcion").val() === '0' ? '': $("#regmesFechaInscripcion option:selected").val();
                                        var registroAnioFechaInscripcion =$("#reganioFechaInscripcion").val() === '0' ? '': $("#reganioFechaInscripcion option:selected").val();
                           
						//Habilita o deshabilita fech de baja
					  if($('#idVigente').is(':checked')) {
                          var registroFechaBaja ="Vigente a la fecha";
                      }else{
                          var registroFechaBaja = obtenerFecha("FechaBaja");
                          var registroDiaFechaBaja =$("#regdiaFechaBaja").val() === '0' ? '': $("#regdiaFechaBaja option:selected").val();
                          var registroMesFechaBaja =$("#regmesFechaBaja").val() === '0' ? '': $("#regmesFechaBaja option:selected").val();
                          var registroAnioFechaBaja =$("#reganioFechaBaja").val() === '0' ? '': $("#reganioFechaBaja option:selected").val();
                                       
                      }
					var registroNumeroRegistroPatronal = $("#registroNumeroRegistroPatronal").val().toUpperCase();
					if(registroNumeroRegistroPatronal==""){
						registroNumeroRegistroPatronal="N/D";
					}
					var registroActividadEmpresa = $("#registroactividadEmpresa").val().toUpperCase();
					var registroDomicilioEmpresa = $("#registrodomicilioEmpresa").val().toUpperCase();

					fnHideErrores("form#informacionHistoriaLaboralForm");
					fnHideErroresInput("form#informacionHistoriaLaboralForm");
                                        if(registroNombrePatron != null)
                                            $('#regLa1').css({"color":"black"});
                                         if(registroEntidadFederativa != null )
                                         {
                                            $('#regLa2').css({"color":"black"});
                                            $('#registroentidadFederativa').removeClass("error");
                                            $('#registroentidadFederativa').removeAttr("style");
                                         }
                                        if(registroFechaInscripcion != null)
                                        {
                                            $('#regLa3').css({"color":"black"});
                                            $('#regdiaFechaInscripcion').css("border","none");
                                            $('#regmesFechaInscripcion').css("border","none");
                                            $('#reganioFechaInscripcion').css("border","none");
                                        }
                                         if(registroFechaBaja != null)
                                         {
                                            $('#regLa4').css({"color":"black"});
                                            $('#regdiaFechaBaja').css("border","none");
                                            $('#regmesFechaBaja').css("border","none");
                                            $('#reganioFechaBaja').css("border","none");
                                        }
                                        if(registroActividadEmpresa != null )
                                            $('#regLa5').css({"color":"black"});
                                        if(registroDomicilioEmpresa != null)
                                            $('#regLa6').css({"color":"black"});
                                                               

					$.blockUI();
					$.ajax({
							url : contextPath
								+ '/wizard/correccionDatosAsegurado/validar/historiaLaboral',
							type : 'post',
							async : false,
							dataType : 'json',
							contentType : "application/json; charset=utf-8",
							data : JSON.stringify({
										nombrePatron : registroNombrePatron,
										entidadFederativa : registroEntidadFederativa,
										fechaInscripcion : registroFechaInscripcion,
										fechaBaja : registroFechaBaja,
										numeroRegistroPatronal : registroNumeroRegistroPatronal,
										actividadEmpresa : registroActividadEmpresa,
										domicilioEmpresa : registroDomicilioEmpresa,
										claveEntidad: entidadFederativaId,
                                                                          
                                                                        
									}),
							success : function(response) {
								var arrayCampos = [
										registroNombrePatron,
										registroEntidadFederativa,
										registroFechaInscripcion,
										registroFechaBaja,
										registroNumeroRegistroPatronal,
										registroActividadEmpresa,
										registroDomicilioEmpresa];
								fnCrearRow(arrayCampos,++indiceHistoriaLaboral,'datosHistoriaLaboralGrid',entidadFederativaId);
								limpiarCamposAgregados(['registronombrePatron','registroentidadFederativa','registroNumeroRegistroPatronal','registroactividadEmpresa','registrodomicilioEmpresa']);
								 $("#idVigente").removeAttr('checked');
								limpiarSelectsFechas(['FechaInscripcion','FechaBaja' ]);
								 setDisableFecha(false);
								$.unblockUI();
							},
							error : function(error) {
								fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
                                                                
                                                                var objErrores = jQuery.parseJSON(error.responseText);
					                        for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
						                  var campo = objErrores.erroresCaptura[index].campo;
                                                                  campo = campo.replace(/\./g, "\\.");
						                if(campo==='nombrePatron')
                                                                    $('#regLa1').css({"color":"red"});                                                                   
                                                                if(campo==='entidadFederativa')
                                                                   $('#regLa2').css({"color":"red"});
                                                                if(campo==='fechaInscripcion')
                                                                {
                                                                   $('#regLa3').css({"color":"red"});
                                                                   $('#regdiaFechaInscripcion').css("border","1px solid red");
                                                                   $('#regmesFechaInscripcion').css("border","1px solid red");
                                                                   $('#reganioFechaInscripcion').css("border","1px solid red");
                                                               }
                                                                if(campo==='fechaBaja')
                                                                {
                                                                   $('#regLa4').css({"color":"red"});
                                                                   $('#regdiaFechaBaja').css("border","1px solid red");
                                                                   $('#regmesFechaBaja').css("border","1px solid red");
                                                                   $('#reganioFechaBaja').css("border","1px solid red");
                                                               }
                                                               if(campo==='actividadEmpresa')
                                                                   $('#regLa5').css({"color":"red"});
                                                               if(campo==='domicilioEmpresa')
                                                                   $('#regLa6').css({"color":"red"});
                                                               
                                                                }											 
                                                               
                                                                
								$.unblockUI();
							}
						});
					$.unblockUI();
				});
		$('#continuarInformacionHistoriaLaboral').click(function() {		
			fnHideErrores("form#informacionHistoriaLaboralForm");
			fnHideErroresInput("form#informacionHistoriaLaboralForm");
			var gridHistoriaLaboral = fnCrearList('datosHistoriaLaboralGrid', 'informacionHistoriaLaboralForm', 'historiaLaboralGrid', 7);
			var datosHistoriaLaboralVO= {};
			datosHistoriaLaboralVO["historiaLaboralGrid"] = gridHistoriaLaboral;
			limpiarCamposAgregados(['registronombrePatron','registroentidadFederativa','registroNumeroRegistroPatronal','registroactividadEmpresa','registrodomicilioEmpresa']);
			limpiarSelectsFechas(['FechaInscripcion', 'FechaBaja']);
			$.blockUI();
			$.ajax({
					url : contextPath
						+ '/wizard/correccionDatosAsegurado/validar/infHistoriaLaboral',
					type : 'post',
					async : false,
					dataType : 'json',
					contentType : "application/json; charset=utf-8",
					data : JSON.stringify(datosHistoriaLaboralVO),
					success : function(response) {
						fnCrearCamposHidden('datosHistoriaLaboralGrid', 'informacionHistoriaLaboralForm', 'historiaLaboralGrid', 7);	
						document.charset = 'ISO-8859-1';
						$('#informacionHistoriaLaboralForm').submit();
						$.unblockUI();
					},
					error : function(error) {
						fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
                                                
						$.unblockUI();
					}
				});
			$.unblockUI();
		});
		
		

	    $('#idVigente').change(function() {
	        setDisableFecha(this.checked);
	    });		
	});
var setDisable = function (select, bandera) {
    // console.log(bandera)
    if (bandera) {
        $("#"+select).attr("disabled", true);
    } else {
        $("#"+select).removeAttr("disabled");
    }
};


function setDisableFecha(bandera){
    if (bandera) {
            $('#regdiaFechaBaja').val(0);
            $('#regmesFechaBaja').val(0);
            $('#reganioFechaBaja').val(0);
            setDisable("regdiaFechaBaja",true);
            setDisable("regmesFechaBaja",true);
            setDisable("reganioFechaBaja",true);
        } else {
            setDisable("regdiaFechaBaja",false);
            setDisable("regmesFechaBaja",false);
            setDisable("reganioFechaBaja",false);
        }
}


var fnEliminarRow = function(idRow) {
        var registroNombrePatron; 
       var registroEntidadFederativa;
       var registroFechaInscripcion;
       var registroFechaBaja;
       var registroNumeroRegistroPatronal;
       var registroActividadEmpresa;
       var registroDomicilioEmpresa;
       $('#'+ idRow +" td").each(function (index) {
           
                   switch(index){
                       case 0:
                           registroNombrePatron=$(this).text();
                       break;
                       case 1:
                           registroEntidadFederativa=$(this).text();
                       break;
                       case 2:
                           registroFechaInscripcion=$(this).text();
                       break;
                       case 3:
                           registroFechaBaja=$(this).text();
                       break;
                       case 4:
                           registroNumeroRegistroPatronal=$(this).text();
                       break;
                       case 5:
                           registroActividadEmpresa=$(this).text();
                       break;
                       case 6:
                           registroDomicilioEmpresa=$(this).text();
                       break;
                   }
               // console.log($(this).text());
       });
	
	// console.log("Eliminando HL: "+idRow);
	$.blockUI();
	$.ajax({
			url : contextPath
				+ '/wizard/correccionDatosAsegurado/actualizarLista',
			type : 'post',
			async : false,
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify({ 
				  nombrePatron : registroNombrePatron,
                  entidadFederativa : registroEntidadFederativa,
                  fechaInscripcion : registroFechaInscripcion,
                  fechaBaja : registroFechaBaja,
                  numeroRegistroPatronal : registroNumeroRegistroPatronal,
                  actividadEmpresa : registroActividadEmpresa,
                  domicilioEmpresa : registroDomicilioEmpresa}),	
			success : function(response) {
				$('#'+ idRow).remove();
                indexarListaHistorial();
			},
			error : function(error) {
				fnProcesarErrores(error,"form#informacionHistoriaLaboralForm");
				$.unblockUI();
			}
		});
	$.unblockUI(); 
};

function indexarListaHistorial(){
	$('#datosHistoriaLaboralGrid tr').each(function(index){
            var indicetotal=index;
                $(this).attr('id','datosHistoriaLaboralGrid'+indicetotal);
                var element=$(this).children('td:last');
                element.children("a").attr('onclick','return fnEliminarRow(\'' + 'datosHistoriaLaboralGrid'+indicetotal
                            + '\')');
	});
}

function indexarListaDocumento(){
    $('#listadoDocumentosGrid tr').each(function(index){
        $(this).children(' td:first').text(index);
    });
}

var fnCrearRow = function(arrayCampos, rowCount, tableName,idEntidadFed) {
	var idTr = tableName + rowCount;
	var trHtml = '<tr id=\'' + idTr + '\'>';
	for (var i = 0; i < arrayCampos.length; i++) {
		trHtml += '<td style="word-wrap: break-word">' + arrayCampos[i] + '</td>';
	}
	trHtml += '<td> <a href="#" onclick="return fnEliminarRow(\'' + idTr
			+ '\');" >Eliminar</a> <input id="idEntidadFederativa" type="hidden" value="'+idEntidadFed+'"></td></tr>';

	$('#' + tableName + ' tbody').append(trHtml);
};

var fnCrearRowHidden = function(arrayCampos, arrayCamposHidden, rowCount,
		tableName, tipoDocto) {
	var idTr = tableName + rowCount;
	var trHtml = '<tr id=\'' + idTr + '\'>';
	for (var i = 0; i < arrayCampos.length; i++) {
		trHtml += '<td>' + arrayCampos[i] + '</td>';
	}
	for (var i = 0; i < arrayCamposHidden.length; i++) {
		trHtml += '<td hidden="hidden">' + arrayCamposHidden[i] + '</td>';
	}
	trHtml += '<td hidden="hidden">' +tipoDocto+ '</td>';
	trHtml += '<td> <a href="#" onclick="return fnEliminarRow(\'' + idTr
			+ '\','+tipoDocto+');" >Eliminar</a> </td></tr>';

	$('#' + tableName + ' tbody').append(trHtml);
	//indexarListaDocumento();
};


var fnCrearList = function(idTable, idFrom, parameterList, numeroCampos) {
	var arrayCampos = [];
	var arrayList = [];
	$("#" + idTable + " tbody tr th").each(function(index) {
		if (arrayCampos.length < numeroCampos) {
			arrayCampos.push($(this).attr("name"));
		}
	});
	$("#" + idTable + " tbody tr").each(function(index) {
		$(this).children("td").each(
			function(index2) {
				var entry = {};
				if (index2 < numeroCampos) {
					entry[arrayCampos[index2]] = $(this).text();
				}
				arrayList.push(entry);
			});
	});
	return arrayList;
};


var fnCrearCamposHidden = function(idTable, idFrom, parameterList, numeroCampos) {
	var arrayCampos = [];
	$("#" + idTable + " tbody tr th").each(function(index) {
		if (arrayCampos.length <= numeroCampos) {
			var nombre=$(this).attr("name");
                        if("acciones"===nombre){
                            nombre="claveEntidad";
                            // console.log("entro");
                            
                        }
                        arrayCampos.push(nombre);
		}
	});

	$("#" + idTable + " tbody tr").each(
			function(index) {
				$(this).children("td").each(
						function(index2) {
							if (index2 < numeroCampos) {
								$('#' + idFrom).append(
										'<input type="hidden" name="'
												+ parameterList + '['
												+ (index - 1) + '].'
												+ arrayCampos[index2]
												+ '" value="' + $(this).text()
												+ '"/>');
							}else if(index2==numeroCampos){
                                                            var input=$(this).find("input").val();
                                                            $('#' + idFrom).append('<input type="hidden" name="'
												+ parameterList + '['
												+ (index - 1) + '].'
												+ arrayCampos[index2]
												+ '" value="' + input
												+ '"/>');
                                                        }
						});
			});
};

var limpiarCamposAgregados = function(arrayCamposLimpiar) {
	jQuery.each(arrayCamposLimpiar, function(i, val) {
		$("#" + val).val("");
	});
};

var limpiarSelectsFechas = function(arrayCamposLimpiar) {
	jQuery.each(arrayCamposLimpiar, function(i, val) {
		$("#regdia" + val).val("0");
		$("#regmes" + val).val("0");
		$("#regmes" + val).change();
		$("#reganio" + val).val("0");
	});
};

var obtenerFecha = function(fecha) {
	var banderaDia = false;
	var formatoFecha = "";

	if ($("#regdia" + fecha).val() === '0') {
		formatoFecha = "";
	} else {
		formatoFecha = ($("#regdia" + fecha).val().length === 1 ? '0'+ $("#regdia" + fecha).val() + "/" : $("#regdia" + fecha).val() + "/");
		banderaDia = true;
	}

	if (banderaDia) {
		formatoFecha += $("#regmes" + fecha).val().length === 1 ? '0'+ $("#regmes" + fecha).val() + "/" : $("#regmes" + fecha).val() + "/";

	} else {
		formatoFecha += $("#regmes" + fecha).val() === '0' ? '': ($("#regmes" + fecha).val().length === 1 ? '0'
						+ $("#regmes" + fecha).val() + "/" : $("#regmes" + fecha).val()+ "/");
	}
	formatoFecha += $("#reganio" + fecha).val() === "0" ? $("#reganio" + fecha).val(): $("#reganio" + fecha + " option:selected").text();
	return formatoFecha;
};



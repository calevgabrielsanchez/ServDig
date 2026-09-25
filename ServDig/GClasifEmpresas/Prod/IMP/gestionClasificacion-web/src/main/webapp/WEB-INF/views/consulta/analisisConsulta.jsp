<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!--Empieza contenido-->

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes"%>
<%@page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal"%>

<script>
function buscar(){
	limpiarMensaje();
	if (dtSolicitudesConcluidas) {
		dtSolicitudesConcluidas.fnDraw();
	} else {
		var oTable = $('#tableSolicitudesConcluidas').dataTable();  
		var oSettings = oTable.fnSettings();
		oSettings._iDisplayStart = 0;
		oTable.fnDraw();
	}
}

var oDialogDocumentos;
var regPatronal;
var tipoPersona;
var delegacion;
var subdelegacion;
var idSolicitud;
var dtSolicitudesConcluidas;

/*Se ejecuta hasta que la pagina se carga completamente*/
	$(window).load(function(){
		limpiarMensaje();
		initDatable();
		fnInitCombosDelegacion();
		$('form#formFiltros select#modulo').trigger('change');
		//Si se encuentra en el modulo de modificaciones de crea el combo de tipos de de movimiento
        if (${grupoTramite == "2"}) {
		    fnSetComboTipoMovimiento($('select#tipoMovimiento'));
        }
        document.getElementById('menuRepAnalisis').style.display='';
        document.getElementById('menuRepBitacora').style.display='';
		$.ajaxSetup({async:true});
		
		//si viene del detalle setea los filtros de la busqueda guardados en sesion	
        if (${vieneDetalle == '1'}){
        	fnSetFiltrosBusqueda();
        }
	});
	
/*Se ejecuta al momento en el que el DOM esta listo.*/
	$(document).ready(function() {
		$( "#strPeriodoInicio" ).datepicker();
		$( "#strPeriodoInicio" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
		$( "#strPeriodoFin" ).datepicker();
		$( "#strPeriodoFin" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );

		/*configuracion del submit de la forma de filtros*/
		$('form#formFiltros').submit(function(){
			buscar();
			return false;
		});

		document.getElementById("cenefa").innerHTML = "Inicio» An&aacute;lisis y consulta";
		
		/** Este Modal es para mostrar el listado de Documentos ligados a una Solicitud */		
		oDialogDocumentos =  $('#dialogDocumentos').dialog({
			autoOpen: false,
			height: 250,
			width: 400,
			modal: true,
			resizable: false,
			buttons: {
				"Cerrar": function() {
					$( this ).dialog( "close" );
				}
			}
			});
		
		$.ajaxSetup({async:false});
		
				
	});
	
	function fnSetFiltrosBusqueda(){	
		setTimeout(function(){ 
			$.blockUI();
	
			//recupera los filtros introducidos por el usuario en la busqueda para que no se tengan que capturar de nuevo
			
			if($('#regPatConservarH').val() != '12345678')
				$('input:text[name="registroPatronal"]').attr('value', $('#regPatConservarH').val());			
			else
				$('input:text[name="registroPatronal"]').attr('value', '');
	
		   	$('#strPeriodoInicio').datepicker('setDate', $('#fechaInicioConservarH').val());
			$('#strPeriodoFin').datepicker('setDate', $('#fechaFinConservarH').val());
			
	     	var tipoR = $('#tipoRegistroConservarH').val();
			$('input:radio[name="tipoRegistro"]').filter('[value='+tipoR+']').attr('checked', true);
	
	        var options = $('select#estatus').get(0).options;
	     	for(index =0 ; index < options.length ; index++ ){
	     		var option = options[index];
	     		if(option.value == $('#estatusConservarH').val())
	        		option.selected = 'selected';
	     	}
	
	        var options = $('select#tipoPersona').get(0).options;
	     	for(index =0 ; index < options.length ; index++ ){
	     		var option = options[index];
	     		if(option.value == $('#tipoPersonaConservarH').val())
	        		option.selected = 'selected';
	     	}
	     	
	        if (${grupoTramite == "2"}) {
		        var options = $('select#tipoMovimiento').get(0).options;
		     	for(index =0 ; index < options.length ; index++ ){
		     		var option = options[index];
		     		if(option.value == $('#tipoMovimientoConservarH').val())
		        		option.selected = 'selected';
		     	}
	        }
	
			var rol = $("#rol").val();			
			if(rol == <%=CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()%> || rol == <%=CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo()%> ||  
					rol == <%=CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo()%> || rol == <%=CodigoRolClasificacion.NORMATIVO_DEL.getCodigo()%>
			){				
		        var options = $('select#subDelegacion').get(0).options;
		     	for(index =0 ; index < options.length ; index++ ){
		     		var option = options[index];
		     		if(option.value == $('#subdelegacionConservarH').val())
		        		option.selected = 'selected';
		     	}
	        	buscar();
			}else if( rol == <%=CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo()%> ){
	
		        var options = $('select#delegacion').get(0).options;
		     	for(index =0 ; index < options.length ; index++ ){
		     		var option = options[index];
		     		if(option.value == $('#delegacionConservarH').val())
		        		option.selected = 'selected';
		     	}
	
		     	if($('#delegacionConservarH').val() > 0){
		     		$('select#delegacion').change();
			     	setTimeout(function(){ 
				        var options = $('select#subDelegacion').get(0).options;
				     	for(index =0 ; index < options.length ; index++ ){
				     		var option = options[index];
				     		if(option.value == $('#subdelegacionConservarH').val()) 
				        		option.selected = 'selected';
				     	}
			        	buscar();
			     	}, 1500);
		     	}else{
		        	buscar();
		     	}		     	
			}else{ // si el usuario es subdelegacional
	        	buscar();
			}			
			$.unblockUI();	
			return 0;
		}, 1000);		
	}

	function fnInitCombosDelegacion(){			
		/*inicializacion de los combos de delegacion y subdelegacion*/
		var rol = $("#rol").val();
		if(rol == <%=CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo()%> || rol == <%=CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo()%> ||  
				rol == <%=CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo()%> || rol == <%=CodigoRolClasificacion.NORMATIVO_DEL.getCodigo()%> 
		){
			/* Este tipo de rol son DELEGACIONALES, por lo tanto se debe de cargar el combo de las subdelegaciones
			 * 	de la delegacion que el usuario tiene asociado.
			 */
			 /*Inicializamos los combos de delegacion y subdelegacion*/
			 fnSetComboDelegacion($('select#delegacion'));
		}else if( rol == <%=CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo()%> ){
			/* Este rol es de tipo NORMATIVO Nacional por lo tanto ve todo  */
			 fnSetComboNormativo();
		}else{
			/* Si no es ninguno de los dos anteriores entonces es usuario Subdelegacional */
			 /*Inicializamos los combos de delegacion y subdelegacion*/
			fnSetComboDelegacion($('select#delegacion'));
			fnSetComboSubdelegacion($('select#subDelegacion'))															
			document.getElementById('subDelegacion').disabled=true;	
		}				
	}
	
	var sIdSelectDelegacion ="#delegacionHolder";
	var sIdSelectSubDelegacion ="#subdelegacionHolder";
	var sIdDelegacionUsuario ="#delegacionUser";
	var sIdSubdelegacionUsuario ="#subdelegacionUser";
	
	/**
	 * Funcion que setea las opciones de combo de delegacion y subdelegacion para los
	 * usuarios con rol DELEGACIONAL, los cuales solo pueden seleccionar
	 * la subdelegacion de acuerdo a la delegacion asignada
	**/
	function fnSetComboDelegacion( objSelect ){
		var idDelegacion = $(sIdDelegacionUsuario).val();
        var options = objSelect.get(0).options;
     	for(index =0 ; index < options.length ; index++ ){
     		var option = options[index];
     		if(option.value == idDelegacion){
        		option.selected = 'selected';
        	}
     	}
		/* Mostramos el select de subdelegaciones*/
		$(sIdSelectSubDelegacion).removeClass("hiddenElement");
		$(sIdSelectSubDelegacion).addClass("showElement");
		objSelect.change();
		fnSetComboSubdelegacion($('select#subDelegacion'))		
	}

	/**
	 * Función que elimina la opción de inscripción inicial del combo de tipos
	 * de movimiento.
	**/
	function fnSetComboTipoMovimiento(objSelect){
		if (objSelect != null) {
			var idTipoMovimiento = ${tipoMovimientoGrupoTramite};
			var options = objSelect.get(0).options;
		    for(index =0 ; index < options.length ; index++ ){
	     		var option = options[index];
	     		if(option.value == idTipoMovimiento){
	     			option.parentNode.removeChild(option);
	        	}
	     	}
		}
    }
	
	/**
	 * FUncion que setea las opciones de combo de delegacion y subdelegacion para los
	 * usuarios con rol NORMATIVO, los cuales pueden ver tanto delegaciones como subdelegaciones
	**/
	function fnSetComboNormativo(){		
		/*Mostramos los dos combos*/
		$(sIdSelectSubDelegacion).removeClass("hiddenElement");
		$(sIdSelectSubDelegacion).addClass("showElement");		
		$(sIdSelectDelegacion).removeClass("hiddenElement");
		$(sIdSelectDelegacion).addClass("showElement");		
	}
	
	/**
	 * FUncion que setea las opciones de combo de delegacion y subdelegacion para los
	 * usuarios con rol SUBDELEGACIONAL, los cuales no pueden seleccionar
	 * delegacion o subdelegacion.
	**/
	function fnSetComboSubdelegacion(objSelect){		
		var idSubDelegacion = $(sIdSubdelegacionUsuario).val();
        var options = objSelect.get(0).options;
     	for(index =0 ; index < options.length ; index++ ){
     		var option = options[index];
     		if(option.value == idSubDelegacion){
        		option.selected = 'selected';
        	}
     	}     	
		/* Mostramos el select de subdelegaciones*/
		$(sIdSelectSubDelegacion).removeClass("hiddenElement");
		$(sIdSelectSubDelegacion).addClass("showElement");					
	}	
	
	/*Funcion que determina el nombre de la accion a realizar*/
	function setControlName(cveIdEstatusAnalisis){
		 return 'Ver Detalle';
	}	
	
	/*Funcion que determina a partir del estado del analisis la funcionalidad que se puede realizar*/
	function control( cveIdSolicitud , cveIdEstatusAnalisis, regPatronal, tipoPersona, delegacion, subdelegacion ){
		verDetalle(cveIdSolicitud, regPatronal, tipoPersona);
	}
		
	function verDocumentos(cveIdSolicitud){		
		//limpia el dialogo del listado de documentos
		resetlistaDocumentos();
		var sSource = context_path + "/analisis/get/listaDocumentos";
		$.ajaxSetup({cache:false});
		$.getJSON(sSource, {'cveIdSolicitud':cveIdSolicitud} , 
				  function(data){
					setlistaDocumentos(data);
		});
		/*abrimos el dialogo*/
		oDialogDocumentos.dialog('open');
		$.ajaxSetup({cache:true});
	}


	/*Funcion para resetear el listado de los documentos que pueden visualizarse*/
	function resetlistaDocumentos (){
		var sNoExisteDocumento = "EN PROCESO ....";
		document.getElementById("fClem").innerHTML = 'CLEM';
		document.getElementById("fClemDoc").innerHTML = sNoExisteDocumento;
		if('${grupoTramite}' != '1'){
			document.getElementById("fAviso").innerHTML = 'AVISO';
			document.getElementById("fAvisoDoc").innerHTML = sNoExisteDocumento;
		}
		if('${grupoTramite}' == '1'){
			document.getElementById("fTip").innerHTML = 'TIP';
			document.getElementById("fTipDoc").innerHTML = sNoExisteDocumento;
			document.getElementById("fArp").innerHTML = 'ARP';
			document.getElementById("fArpDoc").innerHTML = sNoExisteDocumento;
		}
	}
	
	/*Funcion para setear el listado de los documentos que pueden visualizarse*/
	function setlistaDocumentos (data){
		var sSource = context_path + "/analisis/get/mostrarDocumento";
		var sNoExisteDocumento = "NO EXISTE DOCUMENTO";
		var sMostrarDocumento = "MOSTRAR DOCUMENTO";
		var muestraAvisos = '0';

		console.log("::: muestraAvisos - " + muestraAvisos);

		if(data.existeClem == true){
			if(!data.boIndFirma){

				if(muestraAvisos == '1'){
					document.getElementById("fClem").innerHTML = 
						"<a href='" + sSource + "?id=" + data.cveIdClem + "&tipo=1' onclick='imprimeAvisos();' class='linkDoc'>CLEM</a>";
						
					document.getElementById("fClemDoc").innerHTML = 
						"<a href='" + sSource + "?id=" + data.cveIdClem + "&tipo=1' onclick='imprimeAvisos();' class='linkDoc'>" + sMostrarDocumento + "</a>";
					
				}else{
					document.getElementById("fClem").innerHTML = 
						"<a href='" + sSource + "?id=" + data.cveIdClem + "&tipo=1' class='linkDoc'>CLEM</a>";
						
					document.getElementById("fClemDoc").innerHTML = 
						"<a href='" + sSource + "?id=" + data.cveIdClem + "&tipo=1' class='linkDoc'>" + sMostrarDocumento + "</a>";
				}				
				
/* 				
				document.getElementById("fClem").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdClem + "&tipo=1' class='linkDoc'>CLEM</a>";
				document.getElementById("fClemDoc").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdClem + "&tipo=1' class='linkDoc'>" + sMostrarDocumento + "</a>";
 */
			}else{
				if(muestraAvisos == '1'){
					document.getElementById("fClem").innerHTML = 
						"<a href='" + data.urlClemFirma + "' onclick='imprimeAvisos();' class='linkDoc' target='_blank'>CLEM</a>";

	 				document.getElementById("fClemDoc").innerHTML =
						"<a href='" + data.urlClemFirma + "' onclick='imprimeAvisos();' class='linkDoc' target='_blank'>"+sMostrarDocumento+"</a>";
					
				}else{
					document.getElementById("fClem").innerHTML = 
						"<a href='" + data.urlClemFirma + "' class='linkDoc' target='_blank'>CLEM</a>";

	 				document.getElementById("fClemDoc").innerHTML =
						"<a href='" + data.urlClemFirma + "' class='linkDoc' target='_blank'>"+sMostrarDocumento+"</a>";
				}

/* 				document.getElementById("fClem").innerHTML = 
					"<a href='" + data.urlClemFirma + "' class='linkDoc' target='_blank'>CLEM</a>";

 				document.getElementById("fClemDoc").innerHTML =
					"<a href='" + data.urlClemFirma + "' class='linkDoc' target='_blank'>"+sMostrarDocumento+"</a>";
 				 */
			}				
		}else{
			document.getElementById("fClem").innerHTML = 'CLEM';
			document.getElementById("fClemDoc").innerHTML = sNoExisteDocumento;
		}
		if('${grupoTramite}' != '1'){
			if(data.existeAviso == true){
				document.getElementById("fAviso").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdSolicitud + "&tipo=57' class='linkDoc'>AVISO</a>";
				document.getElementById("fAvisoDoc").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdSolicitud + "&tipo=57' class='linkDoc'>" + sMostrarDocumento + "</a>";
			}else{
				document.getElementById("fAviso").innerHTML = 'AVISO';
				document.getElementById("fAvisoDoc").innerHTML = sNoExisteDocumento;
			}
		}
		if('${grupoTramite}' == '1'){
			if(data.existeTip == true){
				document.getElementById("fTip").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdDocumentoProbatorioTip + "&tipo=55' class='linkDoc'>TIP</a>";
				document.getElementById("fTipDoc").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdDocumentoProbatorioTip + "&tipo=55' class='linkDoc'>" + sMostrarDocumento + "</a>";
			}else{
				document.getElementById("fTip").innerHTML = 'TIP';
				document.getElementById("fTipDoc").innerHTML = sNoExisteDocumento;
			}	
			if(data.existeArp == true){
				document.getElementById("fArp").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdDocumentoProbatorioArp + "&tipo=56' class='linkDoc'>ARP</a>";
				document.getElementById("fArpDoc").innerHTML = 
					"<a href='" + sSource + "?id=" + data.cveIdDocumentoProbatorioArp + "&tipo=56' class='linkDoc'>" + sMostrarDocumento + "</a>";
			}else{
				document.getElementById("fArp").innerHTML = 'ARP';
				document.getElementById("fArpDoc").innerHTML = sNoExisteDocumento;
			}
		}
	}
	
<%--  	
	function verDetalle(cveIdSolicitud, regPatronal, tipoPersona){
		$.blockUI();
		var idForm = "#detalleSolicitudForm";
		$(idForm).attr('action', '<%=request.getContextPath()%>/solicitud/'+cveIdSolicitud+'/'+regPatronal+'/'+tipoPersona+'/detalle');
		$(idForm).submit();
	};
 --%>

	function verDetalle(cveIdSolicitud, regPatronal, tipoPersona){
		console.log("::: Buscando detalle de solicitud" + cveIdSolicitud + " y el registro patronal es " + regPatronal);
		var $formulario = $("#detalleSolicitudForm");
		$formulario.find("#cveIdSolicitud").val(cveIdSolicitud);
		$formulario.find("#regPatronal").val(regPatronal);
		$formulario.find("#tipoPersona").val(tipoPersona);
		$formulario.submit();
		$.blockUI();
	}
	
	function initDatable(){
		
		/* Configuracion del data table de solicitudes concluidas*/
		dtSolicitudesConcluidas = $('#tableSolicitudesConcluidas').dataTable({
				bJQueryUI : true,
				bFilter : false,
				bInfo:true,
				bSort: false,
				"bPaginate": true,
				"bAutoWidth" : false,
				/*Propiedad para que no envie la solicitud de paginar al momento de cargar la pagina*/
				"iDeferLoading": 0,
				"bServerSide" : true,
				//"bRetrieve" : true,
				//"bDestroy" : true,
				//"sPaginationType": "full_numbers",
				"aoColumns" : [ 
				    {"sTitle" : "Registro Patronal",            "mDataProp" : "registroPatronal",  "sClass":"dtJustifyClassColumnTiny"},
					{"sTitle" : "Nombre o raz&oacute;n social", "mDataProp" : "razonSocial",       "sClass":"dtJustifyClassColumnTiny"},
					{"sTitle" : "Fecha de presentaci&oacute;n", "mDataProp" : "fechaPresentacion", "sClass":"dtCenterClassColumnTiny"},
					{"sTitle" : "Estado",                       "mDataProp" : "estatusAnalisis",   "sClass":"dtCenterClassColumnTiny"},
					{"sTitle" : "Delegaci&oacute;n",            "mDataProp" : "delegacion",        "sClass":"dtCenterClassColumnTiny" },
					{"sTitle" : "Subdelegaci&oacute;n",         "mDataProp" : "subdelegacion",     "sClass":"dtCenterClassColumnTiny" },
					{"sTitle" : "Usuario",                      "mDataProp" : "usuario",           "sClass":"dtCenterClassColumnTiny" },
					{"sTitle" : "Acci&oacute;n",                "mDataProp" : "cveIdSolicitud",    "sClass":"dtCenterClassColumnTiny" },
					{"sTitle" : "Documentos", "sClass":"dtCenterClassColumnTiny"}
						
				],"aoColumnDefs": [
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   regPatronal = "'" + oObj.aData['registroPatronal'] + "'";
				                    	   tipoPersona =  oObj.aData['idTipoPersona'];	
				                    	   delegacion = oObj.aData['idDelegacion'];
					                       subdelegacion = oObj.aData['idSubdelegacion'];
					                       idSolicitud = oObj.aData['cveIdSolicitud'];
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);

			                    		  var estatus = oObj.aData['cveIdEstatusAnalisis'];
			                    		  if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
			                    			 var retVal = oObj.aData['registroPatronal'];
			                    		  else
			                    			 var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud'] + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + oObj.aData['registroPatronal'] + ' </a> ';
				                    	   
						               	   return retVal;
				                       },
				                       "aTargets": [ 0 ]
				                   },
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);
				                    	   
											 if(tipoPersona == <%=TipoPersonaFiscal.FISICA.getCodigo()%>){
												  var rs = oObj.aData['nombre'] == null ? '' : oObj.aData['nombre'];
											 }else{
												  var rs = oObj.aData['razonSocial'] == null ? '' : oObj.aData['razonSocial'];
											 }
											 
											 var estatus = oObj.aData['cveIdEstatusAnalisis'];
											 if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
												 var retVal = rs;
											 else
												  	var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + rs + ' </a> ';

						               	   return retVal;
				                       },
				                       "aTargets": [ 1 ]
				                   },				                   
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);
				                    	   
										 if(oObj.aData['fechaPresentacion'] == null){
											   var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > </a> ';
										 }else{
											   var arrFecSol = (oObj.aData['fechaPresentacion']).split('-');
										     var fecSol = (arrFecSol[2] + '/' + arrFecSol[1] + '/' + arrFecSol[0]);
										     
											   var estatus = oObj.aData['cveIdEstatusAnalisis'];
											   if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
												   var retVal = fecSol;
											   else
											   	   var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + fecSol + ' </a> ';
										 }
										 
							               return retVal;
				                       },
				                       "aTargets": [ 2 ]
				                   },
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);

			                    		   var estatus = oObj.aData['cveIdEstatusAnalisis'];
			                    		   if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
			                    			   var retVal = oObj.aData['estatusAnalisis'];
			                    		   else
				                    	       	   var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + oObj.aData['estatusAnalisis'] + ' </a> ';
				                    	   
						               	   return retVal;
				                       },
				                       "aTargets": [ 3 ]
				                   },				                   
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);
				                    	   
										 var estatus = oObj.aData['cveIdEstatusAnalisis'];
										 if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
											   var retVal = oObj.aData['delegacion'];
										 else
											   	   var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + oObj.aData['delegacion'] + ' </a> ';
				                       
				                   
						               	   return retVal;
				                       },
				                       "aTargets": [ 4 ]
				                   },				                   
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);				                    	 			                    	   
			                    		   var estatus = oObj.aData['cveIdEstatusAnalisis'];
			                    		   if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
			                    			   var retVal = oObj.aData['subdelegacion'];
			                    		   else
				                    	       var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + oObj.aData['subdelegacion'] + ' </a> ';
				                    	   
						               	   return retVal;
				                       },
				                       "aTargets": [ 5 ]
				                   },
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis']);				                    	   
				                    	   var retVal = '<a href="javascript:verDetalle(' +   oObj.aData['cveIdSolicitud']  + ',' + regPatronal + ',' + tipoPersona+ ');"  > ' + oObj.aData['cveUsuarioSso'] + ' </a> ';
				                    	   
						               	   return retVal;
				                       },
				                       "aTargets": [ 6 ]
				                   },				                   
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   
				                    	   var idBoton = 'btnControl' + oObj.aData['cveIdSolicitud'];
				                    	   /* Dependiendo del estatus del analisis */
				                    	   var sControlName = setControlName( oObj.aData['cveIdEstatusAnalisis'] );
	   				                       var estatus = oObj.aData['cveIdEstatusAnalisis'];
	   				                       
											if(estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()%> || estatus == <%=EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()%> )
				                    		   var retVal = '';
				                    	   else
				                    		   var retVal = '<button id="' + idBoton+ '"  class="mbotonSmallText" style="background:' + (sControlName == 'Ver Detalle' ? '' : '#2E8B57') + '" name="btnAsignar" onclick="control(' +   oObj.aData['cveIdSolicitud'] + ',' + oObj.aData['cveIdEstatusAnalisis']  + ',' + regPatronal + ',' + tipoPersona + ',' + delegacion + ',' + subdelegacion + ')"  > <font color="' + (sControlName == 'Ver Detalle' ? '' : 'white') + '">' + sControlName + ' </font></button> ';
				                    	   	   
						               	   return retVal;
				                       },
				                       "aTargets": [ 7 ]
				                   },				                   
				                   {				                	   				                	 				                	   				                	   
				                       "fnRender": function ( oObj ) {
				                    	   var idBoton = 'btnDoctos' + idSolicitud;
				                    	   var retVal = "<button id='" + idBoton+ "' class='mbotonSmallText' name='btnDocumentos' onclick='verDocumentos(" + idSolicitud + ")' >Ver Doctos.</button> ";
						               	   return retVal;
				                       },
				                       "aTargets": [ 8 ]
				                   }
				               ],

					"bProcessing" : true,
					"sAjaxSource" : context_path + '/analisis/paginar/solicitudes/concluidas',
					"fnServerData" : function(sSource, aoData, fnCallback) {
						
						fnHideErrores("#formFiltros");
						aoData.push({
							"name" : "sSearch",
							"value" : ''
						});

						var wrapper = new Object();
						wrapper.aoData = aoData;
						var oForm = $('#formFiltros').serializeObject(true);
						wrapper.oForm = oForm;

						$.postJSON(sSource, wrapper, function(data) {
							fnCallback(data);
						}).error(function(data) {
							fnProcesarErrores(data, "#formFiltros");
							fnCallback(dataEmpty);
						});

					}
				});
		
	}

	function limpiarMensaje() {
		$("#mensaje").text('');
		$("#mensaje").hide();
	}

	
	
	  function imprimeDocumentos(){
		  
		    var oficioDesechar = "<%=request.getContextPath()%>/solicitud/download.do";
		    var avisos = "<%=request.getContextPath()%>/solicitud/downloadAvisos.do";
		    window.open(oficioDesechar, '_blank', 'width=600,height=800,top=100,left=300,resizable=1,scrollbars=1');
		    sleep(2000);
		    window.open(avisos, '_blank', 'width=500,height=500,top=100,left=100,resizable=1,scrollbars=1');
		  
	  }
	  
	  function imprimeAvisos(){
		    sleep(2000);
		    var avisos = "<%=request.getContextPath()%>/solicitud/downloadAvisos.do";
		    window.open(avisos, '_blank', 'width=500,height=500,top=100,left=100,resizable=1,scrollbars=1');	   
	  }
	 
	  function sleep(milliseconds) {
		  var start = new Date().getTime();
		  for (var i = 0; i < 1e7; i++) {
		    if ((new Date().getTime() - start) > milliseconds){
		      break;
		    }
		  }
	  }

	  function popup(mylink, windowname)
	  {
	  if (! window.focus)return true;
	  var href;
	  if (typeof(mylink) == 'string')
	     href=mylink;
	  else
	     href=mylink.href;
	  window.open(href, windowname, 'width=400,height=200,scrollbars=yes');
	  return false;
	  }	
		
	
</script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="grupoTramite" value="<%=session.getAttribute(\"grupoTramite\")%>" />
<c:set var="tipoMovimientoGrupoTramite" value="<%=session.getAttribute(\"tipoMovimientoGrupoTramite\")%>" />

<c:set var="vieneDetalle" value="${vieneDetalle}"/>
<input type="hidden" id="regPatConservarH" name="regPatConservarH" value="${regPatConservar}"/>
<input type="hidden" id="fechaInicioConservarH" name="fechaInicioConservarH" value="${fechaInicioConservar}"/>
<input type="hidden" id="fechaFinConservarH" name="fechaFinConservarH" value="${fechaFinConservar}"/>
<input type="hidden" id="tipoPersonaConservarH" name="tipoPersonaConservarH" value="${tipoPersonaConservar}"/>
<input type="hidden" id="tipoRegistroConservarH" name="tipoRegistroConservarH" value="${tipoRegistroConservar}"/>
<input type="hidden" id="estatusConservarH" name="estatusConservarH" value="${estatusConservar}"/>
<input type="hidden" id="delegacionConservarH" name="delegacionConservarH" value="${delegacionConservar}"/>
<input type="hidden" id="subdelegacionConservarH" name="subdelegacionConservarH" value="${subdelegacionConservar}"/>
<input type="hidden" id="tipoMovimientoConservarH" name="tipoMovimientoConservarH" value="${tipoMovimientoConservar}"/>

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="rol" value="${usuario.perfilUsuario}"/>
<input type="hidden" id="rol" name="rol" value="${rol.idPerfilUsuario}"/>

<!-- Delegacion y subdelegacion del usuario -->
<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>

<!-- 
<div >
	<form id="detalleSolicitudForm"></form>
</div>
 -->
 
<div >
	<form id="detalleSolicitudForm" action="${contextpath}/solicitud/verDetalle" method="POST">
		<input id="cveIdSolicitud" name="cveIdSolicitud" type="hidden" value=""/>
		<input id="regPatronal" name="regPatronal" type="hidden" value=""/>
		<input id="tipoPersona" name="tipoPersona" type="hidden" value=""/>
	</form>
</div>

<div class="site_position_center">
    <div class="page_holder_no_height">
		<div id="mensaje"></div>
	</div>
</div>
	
<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		<!--Aquí pega tu código-->
		<div class="form-comment">
			
			<form id="formFiltros">
				<input type="hidden" id="statusAnalisis" name="statusAnalisis"/>
				<fieldset>
					<legend>
						<strong><spring:message code="label.filtros.busqueda" /></strong>
					</legend>
										
					<c:if test="${grupoTramite == '2'}">
						<fieldset class="fsInterno">
							<label class="wide"><spring:message code="label.detalle.tipo.modificacion" />:</label>
							
							<select id="tipoMovimiento" name="tipoMovimiento">
								<option value=""><spring:message code="label.filtros.combos.todos" /></option>
									<c:forEach var="tipoTramite" items="${lstTipoTramite}">
										<option value="${tipoTramite.idTipoTramite}">${tipoTramite.descripcion}</option>
									</c:forEach>  
							</select> 
							 
						</fieldset>	
					</c:if>
					<c:if test="${grupoTramite == '1'}">
						<input type="hidden" id="tipoMovimientoHidden" name="tipoMovimiento" value="${tipoMovimientoGrupoTramite}" />
					</c:if>
					
					<input type="hidden" id="grupoTramite" name="grupoTramite" value="${grupoTramite}" />
					
					<fieldset class="fsInterno">
						<span id="registroPatronalError" class=" hiddenElement error"></span>
						<label class="wide"><spring:message code="label.detalle.registro.patronal" />:</label> 
						<input name="registroPatronal" id="registroPatronal" onchange="this.value=this.value.toUpperCase();" style="width: 100px" type="text" maxlength="11" />
					</fieldset>
					
					<fieldset class="fsInterno">
						<!-- elemento SPAN para mostrar el error del campo especifico. -->
						<span id="strPeriodoInicioError" class=" hiddenElement error"></span>
						<span id="strPeriodoFinError" class=" hiddenElement error"></span>
						
						<label class="wide"><spring:message code="label.filtros.busqueda.periodo.determinado" />:</label> 
						<input name="strPeriodoInicio" id="strPeriodoInicio" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/> 
						<label class="wide" for="periodoFin" style="width: 20px"> a</label> 
						<input name="strPeriodoFin" id="strPeriodoFin" style="width: 100px;  text-align: center" type="text" maxlength="10" title="dd/MM/aaaa"/>
					</fieldset>
					
					<fieldset class="fsInterno">
						<label class="wide "><spring:message code="label.filtros.busqueda.tipo.persona" />:</label> 
						
						<combo:creaCombo idHtml="tipoPersona" idHtmlContenedor="formFiltros" 
						entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoPersona" mostrarSoloActivos="true"/>
						
					</fieldset>
					<fieldset class="fsInterno">
						<label class="wide"><spring:message code="label.filtros.busqueda.tipo.registro" />:</label>
						
						<input id="tipoRegistroTodo"
							class="submit_no_margin" name="tipoRegistro" type="radio" value="-1" CHECKED/> 
						 <label
							for="tipoRegistroTodo"><spring:message code="label.filtros.busqueda.radio.todos" /></label> 
						 
						<input
							id="tipoRegistroArp" class="submit_no_margin"
							name="tipoRegistro" type="radio" value="0"/>
							 
						<label
							for="tipoRegistroArp"><spring:message code="label.filtros.busqueda.radio.arp" /></label>
							
						<input id="tipoRegistroPsp"
							class="submit_no_margin" name="tipoRegistro" type="radio" value="2"/> 
						 <label
							for="tipoRegistroRpc"><spring:message code="label.filtros.busqueda.radio.psp" /></label>
							
						<input id="tipoRegistroRpc"
							class="submit_no_margin" name="tipoRegistro" type="radio" value="1"/> 
						 <label
							for="tipoRegistroRpc"><spring:message code="label.filtros.busqueda.radio.rpc" /></label> 
						
					</fieldset>
					<fieldset class="fsInterno">
						<label class="wide "><spring:message code="label.detalle.estatus" />:</label> 
						
						<select id="estatus" name="estatus">
							<option value=""><spring:message code="label.filtros.combos.todos" /></option>
								<c:forEach var="estatusAnalisis" items="${lstEstatusAnalisis}">
									<option value="${estatusAnalisis.cveIdEstatus}">${estatusAnalisis.desEstatus}</option>
								</c:forEach>  
						</select> 
																	  
					</fieldset>
					<!-- <fieldset class="fsInterno hiddenElement" id="delegacionHolder">-->
					<fieldset class="fsInterno hiddenElement" id="delegacionHolder">
						<span id="delegacion" class=" hiddenElement error"></span>
						<label class="wide"><spring:message code="label.filtros.busqueda.delegacion" />:</label> 
						
						<combo:creaCombo idHtml="delegacion" idHtmlContenedor="formFiltros" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion" mostrarSoloActivos="true"/>
							
					</fieldset>
					<fieldset class="fsInterno hiddenElement" id="subdelegacionHolder">
						<label class="wide"><spring:message code="label.filtros.busqueda.subdelegacion" />:</label>
						<combo:creaCombo 		entidad			="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion" 
		                     					idHtml			="subDelegacion" 
		                     				  	entidadPadre	="dicDelegacion.cveIdDelegacion"
		                     				  	idHtmlPadre		="delegacion"
		                     				  	idHtmlContenedor="formFiltros"
		                     				  	mostrarSoloActivos="true"/>  
					</fieldset>
					
					
					<div style="text-align: right; float: right;">
						<input type="submit" value="Buscar" class="mboton" style="width: 120px;" id="boton" />
					</div>
				</fieldset>
				
			</form>
		</div>
		
		<div>
			<table style="width: 100%"  id="tableSolicitudesConcluidas">
				
			</table>
		</div>
		
		<!--Aquí termina tu código-->
	</div>
	<!--Termino  contenido-->
</div>

<!--
	Estos Divs son para la Creación del Modal de Documentos Ligados al Análisis
 -->
<div id="wrapperDialogDocumentos">
<div id="dialogDocumentos" title="Documentos Ligados al An&aacute;lisis">
	<p> </p>
	<div class="form-comment">
			<form id="formDocumentos">
				
				<table style="width: 100%">
					<thead>
					<tr>
						<th class="ui-state-default dtJustifyClassColumnTiny" rowSpan="1" colSpan="1">Documento</th>
						<th class="ui-state-default dtJustifyClassColumnTiny" rowSpan="1" colSpan="1">Estatus</th>
					</tr>
					</thead>
					<tbody>
						<tr class="odd">
							<td class="dtJustifyClassColumnTiny"><font id="fClem">CLEM</font></td>
							<td class="dtJustifyClassColumnTiny"><font id="fClemDoc">NO EXISTE DOCUMENTO</font></td>
						</tr>
						<c:if test="${grupoTramite != '1'}">
						<tr class="even">
							<td class="dtJustifyClassColumnTiny"><font id="fAviso">AVISO</font></td>
							<td class="dtJustifyClassColumnTiny"><font id="fAvisoDoc">NO EXISTE DOCUMENTO</font></td>
						</tr>
						</c:if>
						<c:if test="${grupoTramite == '1'}">
						<tr class="even">
							<td class="dtJustifyClassColumnTiny"><font id="fTip">TIP</font></td>
							<td class="dtJustifyClassColumnTiny"><font id="fTipDoc">NO EXISTE DOCUMENTO</font></td>
						</tr>
						<tr class="odd">
							<td class="dtJustifyClassColumnTiny"><font id="fArp">ARP</font></td>
							<td class="dtJustifyClassColumnTiny"><font id="fArpDoc">NO EXISTE DOCUMENTO</font></td>
						</tr>
						</c:if>
					</tbody>
				</table>
				
			</form>
		</div>
	</div>
</div>
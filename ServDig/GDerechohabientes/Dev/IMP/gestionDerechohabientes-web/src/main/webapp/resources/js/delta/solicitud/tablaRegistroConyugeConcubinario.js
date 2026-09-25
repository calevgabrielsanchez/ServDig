//Objeto para guardar la referencia al dataTable de registro de conyuge o concubina(rio)
var registroConyuge = null;
//urls de cargas de tablas de solicitudes
//var URLS_DATATABLE = [context_path + '/solicitud/muestraSolicitudesRegistradas',
//                      context_path + '/solicitud/muestraSolicitudesOtrosOrigenes',
//                      context_path + '/reportes/muestraRegistroConyuge'];  
var URLS_DATATABLE = context_path + '/reportes/muestraRegistroConyuge';  

//var IDS_ACCORDION = "accordionRegistroConyuge";
var IDS_TABLAS = "registroConyugeConcubinarioTable";

$(window).load(function(){
	//opciones en comun que tienen todas los acordiones de solicitud
/*	var opcionesComunesAcordion = {
		active:false,
		collapsible: true,
		header : 'div',
		 heightStyle: "content",
         autoHeight: false
	};
		
	$("#accordionRegistroConyuge").accordion($.extend({},opcionesComunesAcordion,{
		change: functionAcordionRegistroConyuge
	})); */
	crearTablaSolicitudes(IDS_TABLAS);
});

/*var functionAcordionRegistroConyuge = function(event, ui) {
	if(registroConyuge == null) {
		//cargamos la tabla de las solicitudes concluidas
		crearTablaSolicitudes(IDS_TABLAS);
	}
}; */

/**
 * Metodo que se usa para pintar la tabla de Registros de conyuge o concubinario
 * @param idTabla - El id de la tabla <table> en el que se pintará la informacion
 */
function crearTablaSolicitudes(idTabla) {
	//verificamos cual consulta se va a realizar
	var urlConsulta = URLS_DATATABLE;
	//obtenemos las columas de las tablas
	var columnasTabla = obtenerColumnasTabla();
	//creamos la tabla y cargamos los datos mediante el plugin
	var dataTableSolicitudes = $('#'+idTabla).dataTable({
		sScrollX: "100%",
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bDestroy":true,
        "bPaginate": true,
        "bAutoWidth" : false,
        "iDeferLoading" : 0,
        "bServerSide" : true, 
        "bProcessing" : true,
        "aoColumns" : columnasTabla,
        "sAjaxSource" : urlConsulta,
        "fnServerData" : function(sSource, aoData, fnCallback) {
        	aoData.push({
        		"name" : "sSearch",
        		"value" : ''
        	});

        	var wrapper = new Object();
        	wrapper.aoData = aoData;
        	var oForm = {};
        	wrapper.oForm = oForm;

        	$.postJSON(sSource, wrapper, function(data) {
        		fnCallback(data);
        		//redibujamos la tabla
        		//$("#"+IDS_ACCORDION[tipoTabla]).accordion('resize');
        	}).error(function(jqXHR, textStatus, errorThrown) {
        		error(errorThrown+" - "+'No fue posible obtener las solicitudes registradas.');
        	});
        }
	});
	
	//redibujamos la tabla
	dataTableSolicitudes.fnDraw();
	
	registroConyuge = dataTableSolicitudes
}

/**
 * Funcion que retorma las columas que se pintaran en la tabla dependiendo del tipo de tabla que se este pintando
 * @param tipoTabla
 * @returns
 */
function obtenerColumnasTabla() {
	
	/**
	 * Columnas de la tabla
	 */
	var columnas = [
		{ 
			"sTitle"    : "NSS Aseg/Pen",
			"mDataProp" : "num_NSS"
		},
		{ 
			"sTitle"    : "Nombre Aseg/Pen",
			"mDataProp" : "nombre_ASEGURADO"
		},
		{ 
			"sTitle"    : "Apellido Paterno Aseg/Pen",
			"mDataProp" : "apellido_PATERNO_ASEGURADO"
		},
		{ 
			"sTitle"    : "Apellido Materno Aseg/Pen",
			"mDataProp" : "apellido_MATERNO_ASEGURADO"
		},
		{ 
			"sTitle"    : "CURP Aseg/Pen",
			"mDataProp" : "curp_ASEGURADO"
		},
		{ 
			"sTitle"    : "Sexo Aseg/Pen",
			"mDataProp" : "sexo_ASEGURADO"
		},
		{ 
			"sTitle"    : "Domicilio Aseg/Pen al momento del tr&aacute;mite",
			"mDataProp" : "domicilio_ASEGURADO_MOMENTO"
		},
		{ 
			"sTitle"    : "Domicilio Aseg/Pen actual",
			"mDataProp" : "domicilio_ASEGURADO_ACTUAL"
		},
		{ 
			"sTitle"    : "Nombre Beneficiario",
			"mDataProp" : "nombre_BENEFICIARIO"
		},
		{ 
			"sTitle"    : "Apellido Paterno Beneficiario",
			"mDataProp" : "apellido_PATERNO_BENEFICIARIO"
		},
		{ 
			"sTitle"    : "Apellido Materno Beneficiario",
			"mDataProp" : "apellido_MATERNO_BENEFICIARIO"
		},
		{ 
			"sTitle"    : "CURP Beneficiario",
			"mDataProp" : "curp_BENEFICIARIO"
		},
		{ 
			"sTitle"    : "Sexo Beneficiario",
			"mDataProp" : "sexo_BENEFICIARIO"
		},
		{ 
			"sTitle"    : "Domicilio Beneficiario al momento del tr&aacute;mite",
			"mDataProp" : "domicilio_BENEFICIARIO_MOMENTO"
		},
		{ 
			"sTitle"    : "Domicilio Beneficiario actual",
			"mDataProp" : "domicilio_BENEFICIARIO_ACTUAL"
		},
		{ 
			"sTitle"    : "Fecha tr&aacute;mite",
			"mDataProp" : "fecha_TRAMITE"
		},
		{ 
			"sTitle"    : "Tipo tr&aacute;mite",
			"mDataProp" : "id_TIPO_TRAMITE"
		},
		{ 
			"sTitle"    : "Delegaci&oacute;n adscripci&oacute;n Aseg/Pen al momento del tr&aacute;mite",
			"mDataProp" : "des_DEL_MOMENTO"
		},
		{ 
			"sTitle"    : "Delegaci&oacute;n adscripci&oacute;n Aseg/Pen actual",
			"mDataProp" : "des_DEL_ACTUAL"
		},
		{ 
			"sTitle"    : "Subdelegaci&oacute;n adscripci&oacute;n Aseg/Pen al momento del tr&aacute;mite",
			"mDataProp" : "des_SUB_MOMENTO"
		},
		{ 
			"sTitle"    : "Subdelegaci&oacute;n adscripci&oacute;n Aseg/Pen actual",
			"mDataProp" : "des_SUB_ACTUAL"
		},
		{ 
			"sTitle"    : "UMF adscripci&oacute;n Aseg/Pen al momento del tr&aacute;mite",
			"mDataProp" : "des_UMF_MOMENTO"
		},
		{ 
			"sTitle"    : "UMF adscripci&oacute;n Aseg/Pen actual",
			"mDataProp" : "des_UMF_ACTUAL"
		},
		{ 
			"sTitle"    : "Descripci&oacute;n del tipo de Tr&aacute;mite",
			"mDataProp" : "tipo_TRAMITE"
		},
		{ 
			"sTitle"    : "Vigencia Aseg/Pen al momento del tr&aacute;mite",
			"mDataProp" : "vigencia_ASEG_MOMENTO"
		},
		{ 
			"sTitle"    : "Vigencia beneficiario al momento del tr&aacute;mite",
			"mDataProp" : "vigencia_BENEF_MOMENTO"
		},
		{ 
			"sTitle"    : "C&oacute;nyuge es del mismo sexo",
			"mDataProp" : "ind_CONYUGE_MISMO_SEXO"
		},
		{ 
			"sTitle"    : "Concubina(rio) es del mismo sexo",
			"mDataProp" : "ind_CONCUBINARIO_MISMO_SEXO"
		},
		{ 
			"sTitle"    : "Cuenta de Usuario",
			"mDataProp" : "cuenta_USUARIO"
		},
		{ 
			"sTitle"    : "Origen del tr&aacute;mite",
			"mDataProp" : "origen_TRAMITE"
		},
		{ 
			"sTitle"    : "Documentos probatorios presentados",
			"mDataProp" : "documentos_PROBATORIOS"
		}
	];

	return columnas;
}



function AgregarNSSDocumentoUI(module) {
	this.module = module;
	this.init();
}

AgregarNSSDocumentoUI.prototype.init = function() {
	this.panel = {
		type : "FormPanelComponent",
		id : "panelAgregarNssDocumento",
		name : "agregarNssDocumento",
		model : "agregarNssDocumento",
		postFetch : "agregarNssDocumentoController.obtenerCombosDocumentosNSS", 
		label : "Agregar N&uacute;meros de Seguridad Social y Documentos",
		components : [
        {type: "AlertComponent" , id:"alert" },
        { type:"LabelComponent" , label: "<b>N&uacute;meros de Seguridad Social involucrados en el tr&aacute;mite:</b>"},
        {type : "TextFieldComponent",field : "agregarNssDocumento.nss", id:"nssBuscar",maxlength:"11", tipo:"number"},
						{type: "AlertComponent" , id:"nssMessage" },
						{type:"ButtonComponent",command:"agregarNssDocumentoController.obtenerDatosNSS",label:" Buscar",className:"btn-primary pull-right", icon:"search",inLineWithLabel:true},
						{
		type:"PanelTabComponent",
        id:"panelTabsAgregarNss",
        tabs: ["agregaNssCanase", "agregaNssCiz1" , "agregaNssCiz2", "agregaNssCiz3", "agregaNssHistorico", "agregaNssBdtu"],
        labels: ["CANASE", 
                 "CIZ 1",
                 "CIZ 2",
                 "CIZ 3",
                 "HIST&OacuteRICO",
                 "BDTU"],
        components:[
		{type : "PanelComponent",
			id : "agregaNssCanase",
			components : [ 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.curp",id : "agregaNssCANASEcurp",label : "CURP",disabled : true},
                            {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.sexo",id : "agregaNssCANASEsexo",label : "Sexo",disabled : true},
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.apellidoPaterno", id : "agregaNssCANASEapellidoPaterno",label : "Primer apellido",disabled : true},
                            {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.fechaNacimiento",id : "agregaNssCANASEfechaNacimiento",label : "Fecha de nacimiento",disabled : true}, 
			    
			    {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.apellidoMaterno",id : "agregaNssCANASEapellidoMaterno",label : "Segundo apellido",disabled : true},
                            {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.lugarNacimiento",id : "agregaNssCANASElugarNacimiento",label : "Lugar de nacimiento",disabled : true},
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.nombre",id : "agregaNssCANASEnombre",label : "Nombre(s)",disabled : true},  
			    {type : "TextFieldComponent",field : "informacionAgregaNssCANASE.nacionalidad",id : "agregaNssCANASEnacionalidad",label : "Nacionalidad",disabled : true}, 
			    
			    {type : "TextAreaFieldComponent",field : "informacionCANASE.datosDocumentoProbatorio",id : "agregaNssCANASEdatosDocumentoProbatorio",label : "Datos del documento probatorio",disabled : true}
			],
			layout : [ [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}],
			           [ {span : 12}]]
		},
		{type : "PanelComponent",
			id : "agregaNssCiz1",
			components : [ 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.curp",id : "agregaNssCIZ1curp",label : "CURP",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.sexo",id : "agregaNssCIZ1sexo",label : "Sexo",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.apellidoPaterno",id : "agregaNssCIZ1apellidoPaterno",label : "Primer apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.fechaNacimiento",id : "agregaNssCIZ1fechaNacimiento",label : "Fecha de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.apellidoMaterno",id : "agregaNssCIZ1apellidoMaterno",label : "Segundo apellido",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.lugarNacimiento",id : "agregaNssCIZ1lugarNacimiento",label : "Lugar de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.nombre",id : "agregaNssCIZ1nombre",label : "Nombre(s)",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ1.nacionalidad",id : "agregaNssCIZ1nacionalidad",label : "Nacionalidad",disabled : true}, 
			    
			    {type : "TextAreaFieldComponent",field : "informacionAgregaNssCIZ1.datosDocumentoProbatorio",id : "agregaNssCIZ1datosDocumentoProbatorio",label : "Datos del documento probatorio",disabled : true}
			],
			layout : [ [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 12}]]
		},
		{type : "PanelComponent",
			id : "agregaNssCiz2",
			components : [ 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.curp",id : "agregaNssCIZ2curp",label : "CURP",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.sexo",id : "agregaNssCIZ2sexo",label : "Sexo",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionCIZ2.apellidoPaterno",id : "agregaNssCIZ2apellidoPaterno",label : "Primer apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.fechaNacimiento",id : "agregaNssCIZ2fechaNacimiento",label : "Fecha de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.apellidoMaterno",id : "agregaNssCIZ2apellidoMaterno",label : "Segundo apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.lugarNacimiento",id : "agregaNssCIZ2lugarNacimiento",label : "Lugar de nacimiento",disabled : true}, 
                            
                            {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.nombre",id : "agregaNssCIZ2nombre",label : "Nombre(s)",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ2.nacionalidad",id : "agregaNssCIZ2nacionalidad",label : "Nacionalidad",disabled : true}, 
			    
			    {type : "TextAreaFieldComponent",field : "informacionAgregaNssCIZ2.datosDocumentoProbatorio",id : "agregaNssCIZ2datosDocumentoProbatorio",label : "Datos del documento probatorio",disabled : true}
			],
			layout : [ [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 12}]]
		},
		{type : "PanelComponent",
			id : "agregaNssCiz3",
			components : [ 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.curp",id : "agregaNssCIZ3curp",label : "CURP",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.sexo",id : "agregaNssCIZ3sexo",label : "Sexo",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.apellidoPaterno",id : "agregaNssCIZ3apellidoPaterno",label : "Primer apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.fechaNacimiento",id : "agregaNssCIZ3fechaNacimiento",label : "Fecha de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.apellidoMaterno",id : "agregaNssCIZ3apellidoMaterno",label : "Segundo apellido",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.lugarNacimiento",id : "agregaNssCIZ3lugarNacimiento",label : "Lugar de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.nombre",id : "agregaNssCIZ3nombre",label : "Nombre(s)",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssCIZ3.nacionalidad",id : "agregaNssCIZ3nacionalidad",label : "Nacionalidad",disabled : true}, 
			    
			    {type : "TextAreaFieldComponent",field : "informacionAgregaNssCIZ3.datosDocumentoProbatorio",id : "informacionAgregaNssCIZ3datosDocumentoProbatorio",label : "Datos del documento probatorio",disabled : true}
			],
			layout : [ [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 12}]]
		},
		{type : "PanelComponent",
			id : "agregaNssHistorico",
			components : [ 
			    {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.curp",id : "agregaNssHISTORICOcurp",label : "CURP",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.sexo",id : "agregaNssHISTORICOsexo",label : "Sexo",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.apellidoPaterno",id : "agregaNssHISTORICOapellidoPaterno",label : "Primer apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.fechaNacimiento",id : "agregaNssHISTORICOfechaNacimiento",label : "Fecha de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.apellidoMaterno",id : "agregaNssHISTORICOapellidoMaterno",label : "Segundo apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.lugarNacimiento",id : "agregaNssHISTORICOlugarNacimiento",label : "Lugar de nacimiento",disabled : true}, 
                            
                            {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.nombre",id : "agregaNssHISTORICOnombre",label : "Nombre(s)",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssHISTORICO.nacionalidad",id : "agregaNssHISTORICOnacionalidad",label : "Nacionalidad",disabled : true}, 
			    
			    {type : "TextAreaFieldComponent",field : "informacionAgregaNssHISTORICO.datosDocumentoProbatorio",id : "agregaNssHISTORICOdatosDocumentoProbatorio",label : "Datos del documento probatorio",disabled : true}
			],
			layout : [ [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 12}]]
		},
		{type : "PanelComponent",
			id : "agregaNssBdtu",
			components : [ 
			    {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.curp",id : "agregaNssBDTUcurp",label : "CURP",disabled : true}, 
                            {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.sexo",id : "agregaNssBDTUsexo",label : "Sexo",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.apellidoPaterno",id : "agregaNssBDTUapellidoPaterno",label : "Primer apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.fechaNacimiento",id : "agregaNssBDTUfechaNacimiento",label : "Fecha de nacimiento",disabled : true}, 
                            
			    {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.apellidoMaterno",id : "agregaNssBDTUapellidoMaterno",label : "Segundo apellido",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.lugarNacimiento",id : "agregaNssBDTUlugarNacimiento",label : "Lugar de nacimiento",disabled : true}, 
                            
                            {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.nombre",id : "agregaNssBDTUnombre",label : "Nombre(s)",disabled : true}, 
			    {type : "TextFieldComponent",field : "informacionAgregaNssBDTU.nacionalidad",id : "agregaNssBDTUnacionalidad",label : "Nacionalidad",disabled : true}, 
			    
			    {type : "TextAreaFieldComponent",field : "informacionAgregaNssBDTU.datosDocumentoProbatorio",id : "agregaNssBDTUdatosDocumentoProbatorio",label : "Datos del documento probatorio",disabled : true}
			],
			layout : [ [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 6}, {span : 6}], 
			           [ {span : 12}]]
		}
		]//termina el arreglo de los tabs
		},// termina paneltabs
		{type:"LabelComponent" ,label: "<b>Documentos probatorios del NSS:</b>"},
		{type:"LabelComponent" ,label: "<b>Listado de documentos:</b>"},
		{type: "SelectFieldComponent", id: "combodocumentosSelectField",field: "combodocumentosSelectField", onchange:"agregarNssDocumentoController.changeDocumentosProbatorios"},
		{type:"TextFieldComponent" ,id:"listadoDocumentosGrid",field : "listadoDocumentosGrid"},
		{type:"TextFieldComponent" ,id:"fileData",field : "fileData"}, {type:"AlertComponent",id:"alertListadoDocumentosGrid"},
		{type:"TextAreaFieldComponent", id:"textAreaObservacionField",field : "observacion",label:"Observaciones", oncopy:"return false", oncut:"return false", onpaste:"return false", maxlength:"500"},
		{type: "LabelComponent"},{type:"ButtonComponent",command:"agregarNssDocumentoController.limpiarDocumentos",label:"Limpiar",className:"btn-default pull-right",inLineWithLabel:true},				
		{type:"ButtonComponent",command:"agregarNssDocumentoController.agregarNSS",label :"Agregar",className : "btn-primary pull-right"},
		{ // INICIO label Panel nss 
			type:"LabelComponent" ,
            label: "<b>N&uacute;meros de Seguridad Social involucrados en el tr&aacute;mite y documentos probatorios:</b>"
      	},// TERMINA label Panel nss 
        
        
        {type: "NssDocumentsComponent", id: "gridNssSolicitud", model: "gridNssSolicitud", editable: "true", numerarDcotos : "true"},
 
        
      	{ // INICIO label nss ventanilla
			type:"LabelComponent" ,
            label: "<b>N&uacute;meros de Seguridad Social y documentos de ventanilla:</b>"
      	},// TERMINA label nss ventanilla 	
        
	{type: "NssDocumentsComponent", id: "gridNssVentanilla", model: "gridNssVentanilla", eliminar: "true", agregado:"true", observaciones : "true", editable: "true"},
    					    
					               
           {type: "LabelComponent", label: " "},{type:"ButtonComponent",command:"agregarNssDocumentoController.salirAconsulta",label :"Salir",className : "btn btn-danger"},//panel nss en
						],//termina los componentes del formpanel
						
		layout : [
        [{span : 12}],//alert
        [{span : 6}],//label curp
		[{span : 6},{span : 4},{span : 2}], 
		[ {span : 12}],//grupPanels
		[ {span : 6},{span : 6}],//label
		[ {span : 6},{span : 6}],//campo select
		[ {span : 6},{span : 6}],// filedata , alert
		[ {span : 12}],// textarea
		[ {span : 8},{span : 2},{span : 2}], //botones
		[ {span : 12}],//label
		[ {span : 12}],[{span : 12}],//nss
		[ {span : 12}],//label
		
		[ {span : 10},{span : 2} ]//tabla
		]};//termina layault de los componentes de FormPanelComponent
};

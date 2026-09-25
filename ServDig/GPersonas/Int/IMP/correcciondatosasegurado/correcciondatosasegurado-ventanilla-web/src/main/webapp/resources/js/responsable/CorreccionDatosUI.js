function CorreccionDatosUI(module) {
  this.module = module;
  this.init();
}


//CorreccionDatosUI.prototype.colorCampos = function () {
//	var origenes = this.metadata.ui.components[0].components[4].components;
//	var destinos = this.metadata.ui.components[0].components[5].components;
//	var orig=this.module.controller.model[this.metadata.ui.components[0].model].informacionRENAPO.idOrigen
//	var fecha=this.module.controller.model[this.metadata.ui.components[0].model].informacionRENAPO.fechaNacimiento;
//  for (var i = 0; i < destinos.length; i++) {
//    $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).css("background-color", "").css("color", "");
//    if ($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val() === "") {
//      $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).css("background-color", "#545454");
//    } else {
//      if ($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val() !==
//    	  $("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val()){
//    	  if(this.reemplazarEspecialesSindo($("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).val()) !==
//    		  this.reemplazarEspecialesSindo($("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val())) {
//    		  $("#" + this.module.render.replaceAll(destinos[i].field, ".", "_")).css("background-color", "#D0021B").css("color", "#FFF");
//    	  }
//      }
//    }
//    if ($("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).val() === "") {
//        $("#" + this.module.render.replaceAll(origenes[i].field, ".", "_")).css("background-color", "#545454");
//    }
//  }
//    if(orig!=6 && ($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val())!=""){
//	  fechaOrig=(($("#" + this.module.render.replaceAll(origenes[5].field, ".", "_")).val()));
//	  fechaDest=(($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val()));
//	  fOrig = fechaOrig.split("/");
//	  fDest = fechaDest.split("/");
//	  //Compara mes
//	  if(fOrig[1]==fDest[1]){
//		($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val(fDest[1]));
//		$("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).css("background-color", "").css("color", "");
//	  }
//	  if((fOrig[1] != fDest[1]) && fDest[1]!= "00"){
//		($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val(fDest[1]));
//		$("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).css("background-color", "#D0021B").css("color", "#FFF");
//	  }
//	  if(fDest[1] == "00"){
//		  ($("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).val(""));
//		  $("#" + this.module.render.replaceAll(destinos[5].field, ".", "_")).css("background-color", "#545454");
//	  }
//  }
//	//Compara lugar de nacimiento
//	lugOrig=($("#" + this.module.render.replaceAll(origenes[6].field, ".", "_")).val());
//	lugDest=($("#" + this.module.render.replaceAll(destinos[6].field, ".", "_")).val())
//	if(lugOrig != undefined && lugDest != undefined){
//		if(lugDest.indexOf(lugOrig) != -1){
//			$("#" + this.module.render.replaceAll(destinos[6].field, ".", "_")).css("background-color", "").css("color", "");	
//		}
//	}
//  $("#" + this.module.render.replaceAll(destinos[destinos.length-1].field, ".", "_")).css("background-color", "#545454");
//};
//
//CorreccionDatosUI.prototype.inhabilitarControles = function () {
//	var checks = this.metadata.ui.components[0].components[2].components;
//	for(var i = 0; i < checks.length; i++){
//		$("#" + this.module.render.replaceAll(checks[i].field, ".", "_")).prop( "disabled", true );
//	}
//	
//};
//
//CorreccionDatosUI.prototype.reemplazarEspecialesSindo = function (val) {
//	if(val != undefined){
//		val = val.replace(/[^\w\s]/gi, '');
//	}
//    return val;
//};

CorreccionDatosUI.prototype.init = function () {
  this.metadata = {
    ui: {
      components: [
        {
          type: "FormPanelComponent",
          id: "panelCorreccionDatos",
          name: "correccionDatos",
          model: "detalle",
          postFetch: "postFetchCorreccionDatosUI",
          label: "Correcci\u00F3n de datos",
          level: 3,
          entity: "TipoRegularizacionNSS",
          components: [
//            {type: "TextFieldComponent", field: "nss", label: "NSS", disabled: true, labelStyle:true},
//            {
//              type: "SelectFieldComponent",
//              label: "Tipo de NSS",
//              id: "tipoNSSSelectField",
//              field: "tipoNSS.idTipoNSSCorreccion",
//              data: "tiposNSS",
//              disabled: true
//            },
//            
//            {
//                  type: "PanelComponent",
//                  label: "Tipo de regularizaci\u00F3n<span>*<span>:",
//                  id:"tipoRegularizacionPanelComponent",                  
//                  level: 4,
//                  id:"panelTipoRegularizacionNss",
//                  components: [
//                    {type: "CheckBoxFieldComponent", field: "grupoCorreccion.nombre", label: "Correcci\u00F3n de nombre"},
//                    {type: "CheckBoxFieldComponent", field: "grupoCorreccion.datosEstadisticos", label: "Correcci\u00F3n de datos estad\u00EDsticos"},                    
//                  ],
//                  layout: [[{span: 4}, {span: 4}]]
//            },
//            {
//            	type: "NavegacionPersonaNSSComponent",
//            	id: "navegacionPersonaNSS",
//            	model:"detalle"
//            },
//          
            
            
            
            
            
            
            {
            	type:"PanelCheckBoxComponent",
            	id:"DatosModificacion",
            	 model:"detalle",

            	components: [
            	    {type:"PanelComponent",
            	    id:"panelDatosRenapoo",
            	   
            	    components:[
            	         {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
                            {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
                            {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
            	    	       ],
            	    	       layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
            	    },
//            	    {type:"PanelComponent",
//                	    id:"panelDatosBDTUU",
//                	    components:[
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo",disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
//                	        {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
//                	        {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
//                	     ],
//                	     layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
//                	    },
            	    	{
            	    	 type:"PanelTabComponent",
            	          id:"panelTabsAsegurado",
            	          //class: definir clase
            	          tabs: ["canase", "ciz1" , "ciz2", "ciz3", "historico", "bdtu"],
            	          labels: ["CANASE", "CIZ1", "CIZ2","CIZ3", "HISTORICO", "BDTU"],
            	          components:[
            	                      {
            	                    	  type:"PanelComponent", 
            	                    	  id:"canase",
            	                    	  model:"detalle",
            	                    	  components:[
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.curp", label: "CURP", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.apellidoPaterno", label: "Primer apellido", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.apellidoMaterno", label: "Segundo apellido", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.nombre", label: "Nombre(s)", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.sexo", label: "Sexo",disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
            	                    	              {type: "TextFieldComponent", field: "informacionCANASE.nacionalidad", label: "Nacionalidad", disabled: false},
            	                    	              {type: "TextAreaFieldComponent", field: "informacionCANASE.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
            	                    	              ],
            	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
            	                      	},
            	                      	{
              	                    	  type:"PanelComponent", 
              	                    	  id:"ciz1",
              	                    	  components:[
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ1.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionCIZ1.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"ciz2",
              	                    	  components:[
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ2.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionCIZ2.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"ciz3",
              	                    	  components:[
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionCIZ3.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionCIZ3.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"historico",
              	                    	  components:[
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionHISTORICO.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionHISTORICO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	},
              	                      {
              	                    	  type:"PanelComponent", 
              	                    	  id:"bdtu",
              	                    	  components:[
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.curp", label: "CURP", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.apellidoPaterno", label: "Primer apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.apellidoMaterno", label: "Segundo apellido", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.nombre", label: "Nombre(s)", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.sexo", label: "Sexo",disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.fechaNacimiento", label: "Fecha de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.lugarNacimiento", label: "Lugar de nacimiento", disabled: false},
              	                    	              {type: "TextFieldComponent", field: "informacionBDTU.nacionalidad", label: "Nacionalidad", disabled: false},
              	                    	              {type: "TextAreaFieldComponent", field: "informacionBDTU.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: false, rows: 8}
              	                    	              ],
              	                    	              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
              	                      	}            	                      	
            	                      ],layout:[[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}],[{span:12}]]
            	    	
            	    		},
                	    {
                            type: "PanelComponent",
                            id: "panelcheckbox",
                            components: [
                                         {type:"LabelComponent", label:"<center><h5>Numero de Seguro Social,<br> Registrado en solicitud<h4/></center>", name:"infonavit", className:"h5"},
                                         {type:"LabelComponent", label:"<center><label id=\"numeroNSS\" ></label></center>", name:"infonavit", className:"h5"},
                                         {type:"LabelComponent", label:"<center><h5>Localizar curp,<br> incluido por el sistema</h5></center>", name:"infonavit", className:"h5"},
                                         {type:"linea"},
                                         {type:"CheckBoxComponent", id:"slide1" , field:"motivoAclaracion.obtenerCredito",label:"Certificador",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide2" , field:"motivoAclaracion.obtenerCredito",label:"Asociado al Certificador",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide3" , field:"motivoAclaracion.obtenerCredito",label:"Corresponde a otra<br> persona",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide4" , field:"motivoAclaracion.obtenerCredito",label:"No existe en CANASE",disabled:false},
                                         {type:"linea"},
                                         {type:"LabelComponent", label:"<center><h5>Tipo regularizacion</h4></center>", name:"infonavit", className:"h5"},
                                         {type:"CheckBoxComponent", id:"slide5" , field:"motivoAclaracion.obtenerCredito",label:"Cancelado por<br> duplicidad",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide6" , field:"motivoAclaracion.obtenerCredito",label:"Corresponde a un<br> homonimo",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide7" , field:"motivoAclaracion.obtenerCredito",label:"No existe en CANASE",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide8" , field:"motivoAclaracion.obtenerCredito",label:"Corresponde a otra<br> asegurado",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide9" , field:"motivoAclaracion.obtenerCredito",label:"Correccion de nombre",disabled:false},
                                         {type:"CheckBoxComponent", id:"slide10" , field:"motivoAclaracion.obtenerCredito",label:"Correccion de datos<br> estadisticos",disabled:false, rows: 12},
                            ],
                            layout: [[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}]]
                	    },            	      
            	    ]
            },
            {
    	    	type: "slaiderr",
                id: "slaider",
                components: [
                             {type:"cargarValores", 
                            	 id:"sliderNSS"                          
                             }                                        
                             
                             ]
                             
	      },		
//            {
//              type: "PanelComponent",
//              id: "panelDatosRenapo",
//              components: [
//                {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
//                {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
//              ],
//              layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
//            },                        
//            {
//              type: "PanelComponent",
//              id: "panelDatosBDTU",
//              components: [
//                {type: "TextFieldComponent", field: "informacionRENAPO.curp", label: "CURP", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.apellidoPaterno", label: "Primer apellido", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.apellidoMaterno", label: "Segundo apellido", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.nombre", label: "Nombre(s)", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.sexo", label: "Sexo",disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.fechaNacimiento", label: "Fecha de nacimiento", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.lugarNacimiento", label: "Lugar de nacimiento", disabled: true},
//                {type: "TextFieldComponent", field: "informacionRENAPO.nacionalidad", label: "Nacionalidad", disabled: true},
//                {type: "TextAreaFieldComponent", field: "informacionRENAPO.datosDocumentoProbatorio", label: "Datos del documento probatorio", disabled: true, rows: 8}
//              ],
//              layout: [[{span: 12}],[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}]]
//            },
//            {
//                type: "PanelComponent",
//                id: "checkbox",
//                components: [
//                             {type:"LabelComponent", label:"Localizar curp,<br> incluido por el sistema", name:"infonavit", className:"h5"},
//                             {type:"CheckBoxComponent", field:"motivoAclaracion.obtenerCredito",label:"Certificador",disabled:true},
//                             {type:"CheckBoxComponent", field:"motivoAclaracion.obtenerCredito",label:"Asociado al Certificador",disabled:true},
//                             {type:"CheckBoxComponent", field:"motivoAclaracion.obtenerCredito",label:"Corresponde a otra persona",disabled:true},
//                             {type:"CheckBoxComponent", field:"motivoAclaracion.obtenerCredito",label:"No existe en CANASE",disabled:true, rows: 5},
//                  
//                ],
//                layout: [[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}],[{span: 12}]]
//              },

        
            {
            	type : "CardLayoutComponent",
				id : "responsableCorreccionButtonsCardLayout",
				components : [
		              {
		            	  type: "ButtonGroupComponent",              
		                  components: [      
		                       {
		                       	type: "ButtonComponent",
		                       	id: "btnRegresarCorreccion",
		                       	label: "Bandeja",
		                       	command: "regresarInicioCorreccion",
		                       	className: "btn-default"
		                       },        
                               {
                                 type: "ButtonComponent",
                                 id: "btnRegresarCorreccion",
                                 label: "Regresar",
                                 command: "regresarInicioCorreccion",
                                 className: "btn-default"
                               },
                               {
                                 type: "ButtonComponent",
                                 label: "Siguiente",
                                 command: "siguienteCorreccion",
                                 className: "btn-primary",
                                 href:"top",
                                 id: "btnSiguiente"
                               }
                          ]
		              }, 
		              {
							type: "ButtonGroupComponent",
							components: [                
							    {
							      type: "ButtonComponent",
							      id: "btnRegresarCorreccion",
								  label: "Regresar",
								  command: "regresarInicioCorreccion",
								  className: "btn-default"
							    }
							]
		              },
		        
		              
				],
		          layout: [{span: 12}]
            },
            {
              type: "ModalComponent",
              title: "Confirmar operaci\u00F3n",
              id: "confirmarModal",
              size: "modal-lg",
              body: {
                type: "PanelComponent",
                components: [
                  {type: "ResumenCorreccionComponent", id:"resumenCorreccion",model:"resumenCorreccion"}
                ],
                layout: [[{span: 12}]]
              },
              footer: {
                type: "PanelComponent",
                components: [
                  {type: "LabelComponent"}, 
                  {type: "ButtonComponent", className: "btn-primary", label: "Aceptar", command: "confirmar"}
                ],
                layout: [[{span: 8}, {span: 4}]]
              }
            }
          ],
          layout: [/*[{span: 4},{span: 8}],/* [{span: 12}],*/ [{span: 12}], [{span: 12}, {span: 12}], [{span: 12}],[{span: 12}]]
        }
      ],
      layout: [[{span: 12}]]
    }
  };
};

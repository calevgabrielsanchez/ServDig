function EditTipoRegularizacionComponent(render) {
  this.render = render;
  this.BUSCAR_CI ="atencionResponsable/readMovimientosCuentaIndividualNss.do";
}
var maquina;
EditTipoRegularizacionComponent.prototype.draw = function (component) {
  this.component = component;
  var html = '<div id="editTipoRegularizacion_' + this.render.toId(this.component.id) + '" >';
  maquina = 1;
  html += this.drawBody();
  html += '</div>';
 
  return html;
};
EditTipoRegularizacionComponent.prototype.drawBody = function () {
  var model = this.render.getModel(this.component.model);
  var parentModel = this.render.getModel(this.component.parentModel);
  var html = "";
  var currentNss = this.render.getModel("currentNss");
  if (currentNss === undefined) {
    currentNss = 0;
  }
  currentNss = parseInt(currentNss);

  if (model !== undefined) {
    var tipoAclaracionDisabled ={ canceladoDup: true, homonimio:true, noExisteCanase:true, otroAsegurado: true, correccionNombre: true, correccionEstadis: true, cuentaIlogica: true, cuentaIndividual: true };
    if( this.component.readOnly !== true){
      tipoAclaracionDisabled = this.render.module.model.correccionDatos.getDisabledTipoAclaracion(parentModel, currentNss);
    }
    // console.log( tipoAclaracionDisabled );
    var metadata = {
      type: "FormPanelComponent",
          name: this.component.model , model: this.component.model , entity: "tipoAclaracion",
          label: " ",
          components: [
			{type:"LabelComponent" ,label: "Tipo de Regularizaci\u00f3n"},
            {type: "CheckBoxFieldComponent", field: "canceladoDup", 
              disabled: tipoAclaracionDisabled.canceladoDup, label: "Cancelado por duplicidad"},
            {type: "CheckBoxFieldComponent", field: "homonimio",
              disabled: tipoAclaracionDisabled.homonimio, label: "Corresponde a un hom\u00f3nimo", 
			   onChangeEvent: {
                    type: "EditTipoRegularizacionComponent",
                    event: "changeRegularizacionCuentaIlogica",
                    key: this.component.id,
                    model: "homonimio"
                  }},
            {type: "CheckBoxFieldComponent", field: "noExisteCanase", 
              disabled: tipoAclaracionDisabled.noExisteCanase, label: "No existe en CANASE"},
            {type: "CheckBoxFieldComponent", field: "otroAsegurado", 
              disabled: tipoAclaracionDisabled.otroAsegurado,  label: "Corresponde a otro asegurado",
			   onChangeEvent: {
                    type: "EditTipoRegularizacionComponent",
                    event: "changeRegularizacionCuentaIlogica",
                    key: this.component.id,
                    model: "otroAsegurado"
                  }},
            {type: "CheckBoxFieldComponent", field: "correccionNombre", 
              disabled: tipoAclaracionDisabled.correccionNombre, label: "Correcci\u00f3n de nombre",
				   onChangeEvent: {
                    type: "EditTipoRegularizacionComponent",
                    event: "changeRegularizacionCorreccionNombre",
                    key: this.component.id,
                    model: "correccionNombre"
                  }},
            {type: "CheckBoxFieldComponent", field: "correccionEstadis", 
              disabled: tipoAclaracionDisabled.correccionEstadis, label: "Correcci\u00f3n de datos estad\u00EDsticos",
			  	   onChangeEvent: {
                    type: "EditTipoRegularizacionComponent",
                    event: "changeRegularizacioncorreccionEstadis",
                    key: this.component.id,
                    model: "correccionEstadis"
                  }},
            {type: "CheckBoxFieldComponent", field: "cuentaIlogica", 
                  disabled: tipoAclaracionDisabled.cuentaIlogica, label: "Cuenta il\u00f3gica",
				   onChangeEvent: {
                    type: "EditTipoRegularizacionComponent",
                    event: "changeRegularizacionCuentaIlogicca",
                    key: this.component.id,
                    model: "cuentaIlogica"
                  }},
            {type: "CheckBoxFieldComponent", field: "cuentaIndividual",   key: this.component.id,
                  disabled: tipoAclaracionDisabled.cuentaIndividual, label: "Cuenta individual"}
          ], layout: [[{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}], [{span: 12}],[{span: 12}], [{span: 12}]]

        };

    var panel = new FormPanelComponent(this.render);
    html += panel.draw(metadata);
    this.render.module.render.volatileComponents[this.render.module.render.volatileComponents.length] = metadata;
  }
  this.render.addLinker(new Link(this.component.id, "EditTipoRegularizacionComponent", this.component));
  return html;
};

EditTipoRegularizacionComponent.prototype.notify = function (metadata, modelName) {
  this.component = metadata;
  // console.log( metadata );
  var html = this.drawBody();
  $("#editTipoRegularizacion_" + this.render.toId(this.component.id)).html(html);
};

EditTipoRegularizacionComponent.prototype.digest = function (link ) {
  this.component = link.metadata;
  //var model = this.render.getModel(this.component);
   maquina = 0;
  
};


EditTipoRegularizacionComponent.prototype.changeRegularizacionCuentaIlogicca = function ( nombreCampo) {
	
	/*var componentCorrecion = this.component.id;
	componentCorrecion = componentCorrecion + '.' + nombreCampo;
	var evaluar = this.render.getModel(componentCorrecion);*/
	var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;
  }
	
	var row ={};
    this.render.module.validator.formToModel("FormPanelComponent-" + this.render.toId(this.component.model), row);
	var model = this.render.getModel(this.component.parentModel);
	
	 var valido = this.render.module.controller.model.correccionDatos;
	 if(valido === undefined)
	 {
		row= model.listaNss[index].tipoAclaracion;
	 }	
	 
		 
	 
	 if(model.listaNss[index].tipoNss.certificador=== true || model.listaNss[index].tipoNss.certificador=== 'true')
	{				
		if( row.canceladoDup === "false" &&
		 row.correccionEstadis === "false" &&
         row.correccionNombre === "false" &&
		 row.cuentaIlogica === "false" &&
		 row.homonimio === "false" &&
		 row.noExisteCanase === "false" &&
		 row.otroAsegurado === "false")
		 {
			 if(maquina === 1){
			 row.cuentaIndividual = model.listaNss[index].tipoAclaracion.cuentaIndividual;
			 return;}
		 }
		 
		 if(row.cuentaIlogica !== undefined){
			 row.cuentaIndividual = (JSON.parse(row.cuentaIlogica)  ) ? true : false;
			 $("#cuentaIndividual").prop('checked', row.cuentaIndividual);
			 }
		  if(valido == undefined)
		 {
			  if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
				  {
				  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
				  }
			  else 
				  {
					  if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
				  
				  }
			 
		 }
		 else
		 {
			this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			
	
			var checkcorreccionNombre = $("#correccionNombre").prop('checked');
			this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIlogica =row.cuentaIlogica;			
		 }
			
	}
			
	
	 
};

EditTipoRegularizacionComponent.prototype.buscarCI = function(module) {	
	var url = this.BUSCAR_CI;
	var params = module.controller.model.consultaSolicitud.folio;	
	if(maquina === 0) {
	$("#modal").modal();
	if (url !== null) {
		$.ajax({
			url : url,
			data : JSON.stringify(params),
			type : "POST",
			contentType : "application/json; charset=UTF-8",
			dataType : "json",
			mode : "abort",
			port : "uniqueport",
			success : function(data) {								
				$("#modal").modal('hide');	
				if (data > 0) {
					$("#borradoCI").modal('show');					
				}
			},
			error : function(errMsg) {
				if (errMsg === null) {
					errMsg = "create.error";
				}
				$("#modal").modal('hide');
				this.module.updateModel("AlertComponent", "alert2", {
					level : "danger",
					message : errMsg
				});
			}
		});
	}}
};


EditTipoRegularizacionComponent.prototype.changeRegularizacioncorreccionEstadis = function ( nombreCampo) {

	var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;
  }
	
	var row ={};
    this.render.module.validator.formToModel("FormPanelComponent-" + this.render.toId(this.component.model), row);
	var model = this.render.getModel(this.component.parentModel);
	
	 var valido = this.render.module.controller.model.correccionDatos;
	 if(valido === undefined)
	 {
		row= model.listaNss[index].tipoAclaracion;
	 }	
	 
	 if((model.listaNss[index].tipoNss.certificador=== true || model.listaNss[index].tipoNss.certificador=== 'true') ||
		 (model.listaNss[index].tipoNss.asociado=== true || model.listaNss[index].tipoNss.asociado=== 'true'))
	{				
		if( row.canceladoDup === "false" &&
		 row.correccionEstadis === "false" &&
         row.correccionNombre === "false" &&
		 row.cuentaIlogica === "false" &&
		 row.homonimio === "false" &&
		 row.noExisteCanase === "false" &&
		 row.otroAsegurado === "false")
		 {
			 if(maquina === 1){
			 row.correccionEstadis = model.listaNss[index].tipoAclaracion.correccionEstadis;
			 return;}
		 }
		  if(valido == undefined)
		 {
			  if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
			  {
			  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			  }
		  else 
			  {
				if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
			  } 
		 }
		 else
		 {
			var checkcorreccionNombre = $("#correccionEstadis").prop('checked');
			this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.correccionEstadis =row.correccionEstadis;	
		 }			
	}
			
	
	 
};


EditTipoRegularizacionComponent.prototype.changeRegularizacionCorreccionNombre = function ( nombreCampo) {

	
	var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;
  }
  
	var row ={};
    this.render.module.validator.formToModel("FormPanelComponent-" + this.render.toId(this.component.model), row);
	var model = this.render.getModel(this.component.parentModel);
	
	 var valido = this.render.module.controller.model.correccionDatos;
	 if(valido === undefined)
	 {
		row= model.listaNss[index].tipoAclaracion;
	 }	
	 
	 if((model.listaNss[index].tipoNss.certificador=== true || model.listaNss[index].tipoNss.certificador=== 'true') ||
		 (model.listaNss[index].tipoNss.asociado=== true || model.listaNss[index].tipoNss.asociado=== 'true'))
	{				
		if( row.canceladoDup === "false" &&
		 row.correccionEstadis === "false" &&
         row.correccionNombre === "false" &&
		 row.cuentaIlogica === "false" &&
		 row.homonimio === "false" &&
		 row.noExisteCanase === "false" &&
		 row.otroAsegurado === "false")
		 {
			 if(maquina === 1){
			 row.correccionNombre = model.listaNss[index].tipoAclaracion.correccionNombre;
			 return;}
		 }
		  if(valido == undefined)
		 {
			  if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
			  {
			  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			  }
		  else 
			  {
				if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
			  } 
		 }
		 else
		 {
			var checkcorreccionNombre = $("#correccionNombre").prop('checked');
			this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.correccionNombre =row.correccionNombre;		
		 }			
	}
			
	
	 
};


EditTipoRegularizacionComponent.prototype.changeRegularizacionCuentaIlogica = function ( nombreCampo) {
    
    // Obtener los valores de la forma
    
	var index = this.render.module.controller.model.currentNss;
  if (index === undefined) {
    index = 0;

  }
  
    var row ={};
    this.render.module.validator.formToModel("FormPanelComponent-" + this.render.toId(this.component.model), row);
	var componentCorrecion = this.component.id;
	componentCorrecion = componentCorrecion + '.' + nombreCampo;
	 var model = this.render.getModel(this.component.parentModel);
	 var evaluar = this.render.getModel(componentCorrecion);
	 
	 var valido = this.render.module.controller.model.correccionDatos;
	 if(valido === undefined)
	 {
		row= model.listaNss[index].tipoAclaracion;
	 }	


	if(model.listaNss[index].tipoNss.certificador=== true || model.listaNss[index].tipoNss.certificador=== 'true')
	{
		//correccionEstadis
		var comprovarcuetnaIlogica= row.cuentaIlogica;
		if(comprovarcuetnaIlogica == undefined)
		{
			row.cuentaIlogica = model.listaNss[index].tipoAclaracion.cuentaIlogica;
		}
		row.cuentaIndividual = (JSON.parse(row.cuentaIlogica)  ) ? true : false;
		 $("#cuentaIndividual").prop('checked', row.cuentaIndividual);
		 
		 if(valido == undefined)
		 {
			 if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
			  {
			  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			  }
		  else 
			  {
				if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
			  } 
		 }
		 else
		 {
			//this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			
	
			//var checkcorreccionNombre = $("#correccionNombre").prop('checked');
			//this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIlogica =row.cuentaIlogica;			
				
			//var checkcorreccionNombre = $("#correccionNombre").prop('checked');
			//this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.correccionNombre =row.correccionNombre;
			
			
			//var checkcorreccionEstadis = $("#correccionEstadis").prop('checked');
			//this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.correccionEstadis =row.correccionEstadis;
			
			
			
		 }
			
	}
	
	else if(model.listaNss[index].tipoNss.corresOtraPersona=== true || model.listaNss[index].tipoNss.corresOtraPersona=== 'true')
	{
		var comprovarotroAsegurado= row.otroAsegurado;
		if(comprovarotroAsegurado == undefined)
		{
			row.otroAsegurado = model.listaNss[index].tipoAclaracion.otroAsegurado;
		}
		row.cuentaIndividual = (JSON.parse(row.cuentaIlogica) | JSON.parse(row.homonimio) | JSON.parse(row.otroAsegurado) ) ? true : false;
		$("#cuentaIndividual").prop('checked', row.cuentaIndividual);
		//this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
		if(valido == undefined)
		 {
			if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
			  {
			  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			  }
		  else 
			  {
					if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
			  } 
			
		 }
		 else
		 {
			 if(this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.blanqueoCurp == true ||
			 this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.blanqueoCurp == 'true')
			 {
				 this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.otroAsegurado = true; 
				 this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.blanqueoCurp = false;
				$("#otroAsegurado").prop('checked', true);				 
			 }
			 this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;			 
			var checkotro = $("#otroAsegurado").prop('checked');
			 if(maquina === 0){
			
			//if(checkotro){
			this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.otroAsegurado =row.otroAsegurado;
			//}
			var checkhomonimio = $("#homonimio").prop('checked');
			//if(checkhomonimio){
				this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.homonimio =row.homonimio;
			// }
		 }}
	}
	else if(model.listaNss[index].tipoNss.asociado=== true || model.listaNss[index].tipoNss.asociado=== 'true')
	{
		var comprovarcanceladoDup= row.canceladoDup;
		if(comprovarcanceladoDup == undefined)
		{
			row.canceladoDup = model.listaNss[index].tipoAclaracion.canceladoDup;
		}
		$("#canceladoDup").prop('checked', true);
		
		row.cuentaIndividual = (JSON.parse(row.cuentaIlogica)  ) ? true : false;
		 $("#cuentaIndividual").prop('checked', row.cuentaIndividual);
		//this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
		 if(valido == undefined)
		 {
			 if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
			  {
			  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
			  }
		  else 
			  {
				if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
			  } 
		 }
		 else
		 {
			 
			 if(maquina === 0){
			 this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.canceladoDup = true;
			 this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;			 
			 var checkcanceladoDup = $("#canceladoDup").prop('checked');
			if(checkcanceladoDup){
			this.render.module.controller.model.correccionDatos.listaNss[index].tipoAclaracion.canceladoDup =checkcanceladoDup;}
			 }
		 }
			
	}
	
	 if(valido == undefined)
		 {
		 if (this.render.module.controller.model.correccionDatosAutorizar === undefined)
		  {
		  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
		  }
	  else 
		  {
			if(this.render.module.controller.model.correccionDatosAutorizar.listaNss === undefined)
					  {
						  this.render.module.controller.model.correccionDatosConsultaPrevia.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
					  else{
						  this.render.module.controller.model.correccionDatosAutorizar.listaNss[index].tipoAclaracion.cuentaIndividual =row.cuentaIndividual;
					  }
		  } 
		 }
		 
			
	

    console.log(JSON.stringify( row ) );
	if((row.homonimio === true || row.homonimio === 'true' )||
	(row.otroAsegurado === true || row.otroAsegurado === 'true' ))
	{
		row.cuentaIndividual= true;
		$("#cuentaIndividual").prop('checked', row.cuentaIndividual);
	}
	

};



EditTipoRegularizacionComponent.prototype.triggerEvent = function (event, parameters) {	
	this.buscarCI(module);
  switch (event) {
    case "changeRegularizacionCuentaIlogica":
     // this.changeRegularizacionCuentaIlogica(  JSON.parse(parameters));
	  this.changeRegularizacionCuentaIlogica( parameters);
      break;    
	 case "changeRegularizacionCorreccionNombre":
	  this.changeRegularizacionCorreccionNombre( parameters);
	  break;
	 case "changeRegularizacioncorreccionEstadis":
	  this.changeRegularizacioncorreccionEstadis( parameters );
	  break;
	 case "changeRegularizacionCuentaIlogicca":
	  this.changeRegularizacionCuentaIlogicca( parameters );
	  break;	 	 
	
  }
};
function BitacoraUI(module) {
  this.module = module;
  this.init();
}


BitacoraUI.prototype.init = function () {
this.metadata={ui:{
 components: [
	  {
		  type: "FormPanelComponent",
	      id: "bitacora", 	
	      name: "bitacora",
	      model: "bitacora",          
	      label: "Informaci\u00F3n de la solicitud",
	      level: 2,
	      components: [
	        {type: "TextFieldComponent", field: "folio", label: "Folio", disabled: true, labelStyle:true},
	        {type: "TextFieldComponent", field: "origen", label: "Origen", disabled: true, labelStyle:true},
	        {
              type:"GridComponent",
              title:"Datos de la bit\u00E1cora de estatus",
              id:"gridEstatus",
              model:"gridEstatus",              
              data:"fetchListEstatus",
              styled:true,
              breakWord:true,
              columns:[
                { label:"Fecha de modificaci\u00F3n",name:"fechaModificacion",width:15},
                { label:"Estado",name:"estatus",width:15},
                { label:"Usuario",name:"usuario",width:15},
                { label:"Asignado a",name:"asignado",width:15},
                { label:"Observaciones",name:"observacion",width:40,breakWord:true}
              ]
	        },
	        {type: "ButtonGroupComponent",              
	            components: [                
	              {type: "ButtonComponent",
	                id: "btnRegresarCorreccion",
	                label: "Regresar",
	                command: "bandeja",
	                className: "btn-default"
	              }
	            ]
	        }
	      ],
	      layout: [[{span:4}],[{span:4}],[{span:12}],[{span:12}]]
	  }
 ]
 }}
};
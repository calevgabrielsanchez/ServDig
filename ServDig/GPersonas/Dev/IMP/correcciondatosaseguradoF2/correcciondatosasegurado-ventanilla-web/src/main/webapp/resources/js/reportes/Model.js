function Model(module) {
	this.module = module;
	this.init();
	this.prepareValidator();
}

Model.prototype.prepareValidator = function(  ){
  var that = this;
  
  $.validator.addMethod("lessThanDate", function (value, element, params) {
    var model = {};
	that.module.validator.formToModel("FormPanelComponent-filter", model);
	// console.log(JSON.stringify(model));
    var other = that.module.validator.getParamValue(params.params, "other");    
    var dt = that.module.validator.stringToDate( value );
    var otherDate = that.module.validator.stringToDate( model[other] );    
	// console.log(value === "");
    return  value =="" || otherDate.getTime() < dt.getTime();
  });  
};


Model.prototype.init = function() {
	this.metadata = {
		model : {
			entities : [ {
				name : "filter",
				fields : [ 
					{name : "folio", domain : "folio"},
					{name : "curp", domain : "curp"},
					{name : "nssInvolucrado", domain : "nss"},
					{name : "curpBeneficiario", domain : "curp"},
					{name : "fechaSolicitudDesde", domain : "fechaSolicitudDesde"},
					{name : "fechaSolicitudHasta", domain : "fechaSolicitudHasta"},
					
					{name : "fechaFinalizacionDesde", domain : "fechaFinalizacionDesde"},					
					{name : "fechaFinalizacionHasta", domain : "fechaFinalizacionHasta"},
					
					{name : "fechaActualizacionDesde", domain : "fechaActualizacionDesde"},
					{name : "fechaActualizacionHasta", domain : "fechaActualizacionHasta"}
				]
			} ],
			domains : [ {
				name : "curp",
				rules : [ 
						 {type: "Length", message: "El campo debe tener m\u00EDnimo 18 caracteres.", params:[{ name: "min", value:18 },{ name: "max", value:18 }] },
						 {type: "MaxLength", params:[{ name: "maxlength", value:18}] },
				         {type: "Regex", message: "El formato del campo no es v\u00E1lido.", params:[{ name: "regex", value:"^[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]{1}$"}] }
				          ]
			},
			{
				name : "nss",
				rules : [ 
						 {type: "Length", message: "El campo debe tener m\u00EDnimo 11 caracteres.", params:[{ name: "min", value:11 },{ name: "max", value:11 }] },
						 {type: "MaxLength", params:[{ name: "maxlength", value:11}] },
				         {type: "Regex", message: "El formato del N\u00FAmero de Seguridad Social no es v\u00E1lido.", params:[{ name: "regex", value:"^[0-9]{11}$"}] }
				          ]
			},
			{
				name : "folio",
				rules : [ 
				         {type: "Length", message: "El campo debe tener m\u00EDnimo 25 caracteres.", params:[{ name: "min", value:20 },{ name: "max", value:25 }] },
						 {type: "MaxLength", params:[{ name: "maxlength", value:25}] },
				         {type: "Regex", message: "El formato del campo no es v\u00E1lido.", params:[{ name: "regex", value:"^[0-9]{21}$"}] }
						 ]
			},
			{ name: "fechaSolicitudDesde",
				rules: [
						{type: "LessThanToday", message: "La fecha seleccionada no debe ser posterior a la fecha actual"}
						]
			},
			{ name: "fechaSolicitudHasta",
				rules: [
						{type: "Custom", name:"lessThanDate" , params:[ {name:"other", value:"fechaSolicitudDesde"} ],  message: "La fecha seleccionada debe ser menor a la fecha  de 'Desde'"}
						]
			},
			{ name: "fechaFinalizacionDesde",
				rules: [
						{type: "LessThanToday", message: "La fecha seleccionada no debe ser posterior a la fecha actual"}
						]
			},
			{ name: "fechaFinalizacionHasta",
				rules: [
						{type: "Custom", name:"lessThanDate" , params:[ {name:"other", value:"fechaFinalizacionDesde"} ],  message: "La fecha seleccionada debe ser menor a la fecha  de 'Desde'"}
						]
			},
			{ name: "fechaActualizacionDesde",
				rules: [
						{type: "LessThanToday", message: "La fecha seleccionada no debe ser posterior a la fecha actual"}
						]
			}			,
			{ name: "fechaActualizacionHasta",
				rules: [
						{type: "Custom", name:"lessThanDate" , params:[ {name:"other", value:"fechaActualizacionDesde"} ],  message: "La fecha seleccionada debe ser menor a la fecha  de 'Desde'"}
						]
			}
			]
		}
	};
}
function PeriodoCuentaIndividualModel(module) {
  this.module = module;
  this.init();
}
PeriodoCuentaIndividualModel.prototype.prepareValidator = function( model, forma ){
  var that = this;
  var formModel = {};
  this.module.validator.formToModel("FormPanelComponent-" + forma, formModel);
  
  $.validator.addMethod("lessThanDate", function (value, element, params) {        
    var other = that.module.validator.getParamValue(params.params, "other");    
    var dt = that.module.validator.stringToDate( value );
    var otherDate = that.module.validator.stringToDate( formModel[other] );    
    return  otherDate.getTime() > dt.getTime();
  });
  
  $.validator.addMethod("before", function (value, element, params) {        
    var other = that.module.validator.getParamValue(params.params, "other");    
    var dt = that.module.validator.stringToDate( value );
    
    var otherDate = that.module.validator.stringToDate( formModel[other] );    
    return  otherDate.getTime() > dt.getTime();
  });
  $.validator.addMethod("after", function (value, element, params) {        
    var other = that.module.validator.getParamValue(params.params, "other");    
    var dt = that.module.validator.stringToDate( value );
    var otherDate = that.module.validator.stringToDate( formModel[other] );  
    return  otherDate.getTime() <= dt.getTime();
  });
  $.validator.addMethod("rn045", function (value, element, params) {
    var inicial = model.tipoMovimientoInicial;
    var final = model.tipoMovimientoFinal;
    return (inicial === "1" || inicial === "8" || inicial === "7") && (final === "0"|| final === "2"||final === "7");
  });
};
PeriodoCuentaIndividualModel.prototype.init = function () {
  this.entities = [
    {name: "periodo",
      fields: [
        {name: "fechaRecepcionMovimiento", domain: "fechaRecepcionMovimiento"},
        {name: "fechaInicioMovimiento", domain: "fechaInicioMovimiento"},
        {name: "fechaFinalMovimiento", domain: "fechaFinalMovimiento"},
        {name: "salarioBase", domain: "salarioBase"},
        {name: "tipoMovimientoInicial", domain: "tipoMovimientoInicial"},
        {name: "origenMovimientoInicial", domain: "origenMovimientoInicial"},
        {name: "tipoSalario", domain: "tipoSalario"},
        {name: "eventual", domain: "eventual"},
        {name: "extemporaneoConvenioSuspension", domain: "extemporaneoConvenioSuspension"},
        {name: "origenMovimientoFinal", domain: "origenMovimientoFinal"},
        {name: "jornadaSemanal", domain: "jornadaSemanal"},
        {name: "tipoMovimientoFinal", domain: "tipoMovimientoFinal"}
      ]
    }
  ];
  this.domains=[
    { name: "fechaInicioMovimiento",
      rules: [
        {type: "Regex", message: " La(s) fecha(s) no cumplen con el formato DD/MM/AAAA, por favor verifique." , params:[{name:"regex", value:/^(\d{1,2})\/(\d{1,2})\/(\d{4})$/}]},
        {type: "Required", message: "El campo es requerido"},
        {type: "BeforeToday", message: "La fecha debe ser menor a la fecha actual"}
      ]
    },
    { name: "salarioBase",
      rules: [
        {type: "Required", message: "El campo es requerido"},
        {type: "MaxLength", message: "El campo es requerido", params:[{name:"maxlength", value:6}]},
        {type: "Regex", message: "El formato es incorrecto" , params:[{name:"regex", value:/^\d?\d?(\d\d\d\.\d\d)?(\d\d\.\d\d)?(\d\.\d\d)?(\d)?(\d\d)?(\d\d\d)?(\d\.\d)?$/}]}
      ]
    },
    { name: "tipoMovimientoInicial",
      rules: [
        {type: "Required", message: "El campo es requerido"}        
      ]
    },
    { name: "tipoMovimientoFinal",
      rules: [
        {type: "Required", message: "El campo es requerido"}        
      ]
    },
    { name: "fechaRecepcionMovimiento",
      rules: [
        {type: "Regex", message: " La(s) fecha(s) no cumplen con el formato DD/MM/AAAA, por favor verifique." , params:[{name:"regex", value:/^(\d{1,2})\/(\d{1,2})\/(\d{4})$/}]},
        {type: "Required", message: "El campo es requerido"},
        {type: "BeforeToday", message: "La fecha debe ser menor a la fecha actual"}
      ]
    },
    { name: "fechaFinalMovimiento",
      rules: [
        {type: "Regex", message: " La(s) fecha(s) no cumplen con el formato DD/MM/AAAA, por favor verifique." , params:[{name:"regex", value:/^(\d{1,2})\/(\d{1,2})\/(\d{4})$/}]},       
        {type: "Required", message: "El campo es requerido"},
        {type: "Custom", name:"after" , params:[ {name:"other", value:"fechaInicioMovimiento"} ],
          message: "La fecha de inicio del movimiento es posterior a la fecha final del movimiento, por favor verifique."}
      ]
    }
  ];
};

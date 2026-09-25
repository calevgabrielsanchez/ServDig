function PeriodoCuentaIndividualModel(module) {
  this.module = module;
  this.init();
}
PeriodoCuentaIndividualModel.prototype.prepareValidator = function( model ){
  var that = this;
  
  $.validator.addMethod("lessThanDate", function (value, element, params) {        
    var other = that.module.validator.getParamValue(params.params, "other");    
    var dt = that.module.validator.stringToDate( value );
    var otherDate = that.module.validator.stringToDate( model[other] );    
    return  otherDate.getTime() > dt.getTime();
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
        {name: "extemporaneoConvenioSuspencion", domain: "extemporaneoConvenioSuspencion"},
        {name: "origenMovimientoFinal", domain: "origenMovimientoFinal"},
        {name: "jornadaSemanal", domain: "jornadaSemanal"}
      ]
    }
  ];
  this.domains=[
    { name: "fechaRecepcionMovimiento",
      rules: [
        {type: "Required", message: "El campo es requerido"},
        {type: "LessThanToday", message: "La fecha debe ser menor a la fecha actual"}
      ]
    },
    { name: "fechaFinalMovimiento",
      rules: [
        {type: "Required", message: "El campo es requerido"},
        {type: "Custom", name:"lessThanDate" , params:[ {name:"other", value:"fechaInicioMovimiento"} ],
          message: "La fecha debe ser menor a la fecha  de inicio de movimiento"}
      ]
    }
  ];
};

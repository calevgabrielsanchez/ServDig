function BandejaSolicitudesController(module) {
    this.module = module;
    this.model = {};
}

BandejaSolicitudesController.prototype.selectTramite = function(index){
  
};

BandejaSolicitudesController.prototype.selectHistorico = function(index){
  
};

BandejaSolicitudesController.prototype.nuevaSolicitud = function(){
  
};

BandejaSolicitudesController.prototype.salir = function(){
  
};

BandejaSolicitudesController.prototype.init = function(){
    this.model = {
      userProfile:{},
      gridTramites:{},
      gridHistorico:{}
    };

    this.module.render.draw();
    this.module.service.getUserProfile(this.module);
};
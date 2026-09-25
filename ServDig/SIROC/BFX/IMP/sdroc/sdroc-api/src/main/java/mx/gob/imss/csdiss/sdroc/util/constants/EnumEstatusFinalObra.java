package mx.gob.imss.csdiss.sdroc.util.constants;

public enum EnumEstatusFinalObra {


		ACTIVA(1),ACTIVA_REACTIVADA(2),CANCELADA(3),SUSPENDIDA(4),TERMINADA(5);
		
		private int estatus;

		EnumEstatusFinalObra(int estatus) {
			this.estatus = estatus;
		}

		/**
		 * @return the estatus
		 */
		public int getEstatus() {
			return estatus;
		}
}

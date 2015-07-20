import cl.adexus.isl.spm.*

fixture {
	
	TipoCuenta.findByCodigo('01')?:(new TipoCuenta([codigo:'01', descripcion: 'Cuenta Corriente'])).save(flush:true)
	TipoCuenta.findByCodigo('02')?:(new TipoCuenta([codigo:'02', descripcion: 'Cuenta Vista'])).save(flush:true)	

}

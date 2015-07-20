import cl.adexus.isl.spm.*

fixture {
	
	TipoCalificacion.findByCodigo('01')?:(new TipoCalificacion([codigo:'01', origen: OrigenSiniestro.findByCodigo('1'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('1'),altaInmediata: 2, descripcion: 'Accidente del trabajo'])).save(flush:true)
	TipoCalificacion.findByCodigo('02')?:(new TipoCalificacion([codigo:'02', origen: OrigenSiniestro.findByCodigo('1'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('2'),altaInmediata: 2, descripcion: 'Accidente de Trayecto'])).save(flush:true)
	TipoCalificacion.findByCodigo('03')?:(new TipoCalificacion([codigo:'03', origen: OrigenSiniestro.findByCodigo('1'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('3'),altaInmediata: 2, descripcion: 'Enfermedad Profesional'])).save(flush:true)
	TipoCalificacion.findByCodigo('04')?:(new TipoCalificacion([codigo:'04', origen: OrigenSiniestro.findByCodigo('1'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('1'),altaInmediata: 1, descripcion: 'Accidente ocurrido a causa o con ocasión del trabajo con alta inmediata'])).save(flush:true)
	TipoCalificacion.findByCodigo('05')?:(new TipoCalificacion([codigo:'05', origen: OrigenSiniestro.findByCodigo('1'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('3'),altaInmediata: 1, descripcion: 'Enfermedad Laboral con Alta inmediata y/o sin Incapacidad Permanente'])).save(flush:true)
	TipoCalificacion.findByCodigo('06')?:(new TipoCalificacion([codigo:'06', origen: OrigenSiniestro.findByCodigo('2'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('1'),altaInmediata: 3, descripcion: 'Accidente Común'])).save(flush:true)
	TipoCalificacion.findByCodigo('07')?:(new TipoCalificacion([codigo:'07', origen: OrigenSiniestro.findByCodigo('2'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('3'),altaInmediata: 3, descripcion: 'Enfermedad Común'])).save(flush:true)
	TipoCalificacion.findByCodigo('08')?:(new TipoCalificacion([codigo:'08', origen: OrigenSiniestro.findByCodigo('3'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('4'),altaInmediata: 3, descripcion: 'Siniestro de trabajador no protegido por la Ley 16.744'])).save(flush:true)
	TipoCalificacion.findByCodigo('09')?:(new TipoCalificacion([codigo:'09', origen: OrigenSiniestro.findByCodigo('1'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('2'),altaInmediata: 1, descripcion: 'Accidente ocurrido en el trayecto con alta inmediata'])).save(flush:true)
	TipoCalificacion.findByCodigo('10')?:(new TipoCalificacion([codigo:'10', origen: OrigenSiniestro.findByCodigo('3'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('4'),altaInmediata: 3, descripcion: 'Accidente de dirigente sindical en cometido gremial'])).save(flush:true)
	TipoCalificacion.findByCodigo('11')?:(new TipoCalificacion([codigo:'11', origen: OrigenSiniestro.findByCodigo('3'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('4'),altaInmediata: 3, descripcion: 'Accidente debido a fuerza mayor extraña ajena al trabajo'])).save(flush:true)
	TipoCalificacion.findByCodigo('12')?:(new TipoCalificacion([codigo:'12', origen: OrigenSiniestro.findByCodigo('3'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('4'),altaInmediata: 3, descripcion: 'No se detecta enfermedad'])).save(flush:true)
	TipoCalificacion.findByCodigo('13')?:(new TipoCalificacion([codigo:'13', origen: OrigenSiniestro.findByCodigo('3'), eventoSiniestro: TipoEventoSiniestro.findByCodigo('4'),altaInmediata: 3, descripcion: 'Derivación a otro organismo administrador'])).save(flush:true)
	
}
import cl.adexus.isl.spm.*

fixture {
	FormatoOrigen.findByCodigo('PAPEL')?:(new FormatoOrigen([codigo: 'PAPEL', descripcion: 'Detalle Papel'])).save()
	FormatoOrigen.findByCodigo('ARCHIVO')?:(new FormatoOrigen([codigo: 'ARCHIVO', descripcion: 'Detalle Archivo'])).save()
}

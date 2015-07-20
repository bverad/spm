package cl.adexus.isl.spm

import cl.adexus.isl.spm.helpers.FormatosISLHelper;
import cl.adexus.helpers.FormatosHelper;

import java.util.concurrent.TimeUnit;

class TaskDataService {
	
	def UsuarioService;

    def taskData(processId,taskName,dataset){
		if(processId=='cl.isl.spm.cm.ingreso')
			return dataCMIngreso(dataset)
		if(processId=='cl.isl.spm.cm.revision')
			return dataCMRevision(dataset)
		if(processId=='cl.isl.spm.cm.fact-ingreso')
			return dataFACTIngreso(dataset)
		if(processId=='cl.isl.spm.calor.calor-calificacion')
			return dataCalorCalificacion(dataset)
		if(processId=='cl.isl.spm.calor.calor-calificacion-ep')
			return dataCalorCalificacion(dataset)
		if(processId=='cl.isl.spm.calor.calor-solantedic')
			return dataCalorSolAnteAdic(dataset)
		if(processId=='cl.isl.spm.otp.otp-77bisingreso')
			return data77bisIngreso(dataset)
		if(processId=='cl.isl.spm.otp.otp-77bispago')
			return data77bisPago(dataset)
		
		def data=[]
		data.add([label:'Error', valor:'(no hay definicion para '+processId+')']);
		data.add([label:'Dataset', valor:dataset]);
		return data;
	}
	
	def dataCMIngreso(dataset){
		log.info("Ejecutando metodo dataCMIngreso : ${dataset['cuentaMedicaId']}")
		return dataCM(dataset['cuentaMedicaId']);
	}
	
	def dataCMRevision(dataset){
		log.info("Ejecutando metodo dataCMRevision : ${dataset['idCuentaMedica']}")
		return dataCM(dataset['idCuentaMedica']);
	}
	
	def dataCM(cid){
		log.info("Ejecutando metodo dataCM : $cid")
		def data=[]
		def cuentaMedica = CuentaMedica.findById(cid)
		if(cuentaMedica!=null){
			data.add([label:'Folio', valor:cuentaMedica.folioCuenta]);
			data.add([label:'Tipo', valor:cuentaMedica.tipoCuenta.descripcion]);
			data.add([label:'RUN Trabajador', valor:FormatosHelper.runFormatStatic(cuentaMedica.trabajador.run)]);
			
		}else{
			data.add([label:'Error', valor:'(no existe la cuenta id:'+cid+'??)']);
		}
		return data;
	}
	
	def dataFACTIngreso(dataset){
		def data=[]
		def factura    = Factura.findById(dataset['facturaId'])
		if(factura!=null){
			data.add([label:'Folio', valor:factura.folio]);
			def nombrePrestador=FormatosISLHelper.nombrePrestadorStatic(factura.prestador)
			data.add([label:'Prestador', valor:nombrePrestador]);
			data.add([label:'RUT Prestador', valor:FormatosISLHelper.rutPrestadorStatic(factura.prestador)]);
		}else{
			data.add([label:'Error', valor:'(no existe la factura id:'+dataset['facturaId']+'??)']);
		}
		return data;
	}
	
	def dataCalorCalificacion(dataset){
		log.info("Ejecutando metodo dataCalorCalificacion")
		log.debug("dataset:  ${dataset}" )
		def data=[]
		def siniestro    = Siniestro.findById(dataset['siniestroId'])
		log.debug("Validando existencia de siniestro")
		if(siniestro!=null){
			log.debug("Siniestro encontrado : $siniestro")
			log.debug("Agregando siniestro a valor de retorno")
			data.add([label:'Codigo Siniestro', valor:siniestro.id]);
			log.debug("Siniestro se ha agregado con exito a valor de retorno")
			
			def long diffInMillies = (new Date()).getTime() - siniestro.creadoEl.getTime();
			def dias= TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
			
			data.add([label:'Dias Transcurridos', valor:dias]);
			
			log.debug("Verificando si el siniestro corresponde a una enfermedad profesional")
			if(siniestro.esEnfermedadProfesional){
				log.debug("Corresponde a una enfermedad profesional")
				data.add([label:'Tipo Siniestro', valor:'Enfermedad Profesional']);
				log.debug("No corresponde a una enfermedad profesional")
			}else{
				
				def diatOA=siniestro.diatOA;
				if(diatOA!=null){
					if(diatOA.esAccidenteTrayecto){
						log.debug("Corresponde a accidente de trayecto")
						data.add([label:'Tipo Siniestro', valor:'Accidente Trayecto']);
					}else{
						log.debug("Corresponde a accidente de trabajo")
						data.add([label:'Tipo Siniestro', valor:'Accidente Trabajo ']);
					}
				}else{
				log.debug("Corresponde a un accidente sin DIAT")
					data.add([label:'Tipo Siniestro', valor:'Accidente (sin DIAT-OA)']);
				}
			}
			
			data.add([label:'RUN Trabajador', valor:FormatosHelper.runFormatStatic(siniestro.trabajador.run)]);
			
			//Buscamos si es 77 BIS
			log.debug("Verificando si siniestro corresponde a 77bis")
			def bis=Bis.findBySiniestro(siniestro);
			if(bis){
				log.debug("Corresponde a 77bis, agregando a data de retorno")
				data.add([label:'Es 77 BIS', valor:'Siniestro solicitado por Articulo 77 BIS']);
				log.debug("Data de retorno agregada")
			}
			
			//Buscamos si tiene informe OPA
			def informeOPA=InformeOPA.findBySiniestro(siniestro);
			data.add([label:'Informe OPA', valor:(informeOPA!=null)?'SI':'NO']);
			
			
		}else{
			data.add([label:'Error', valor:'(no existe el siniestro id:'+dataset['siniestroId']+'??)']);
		}
		return data;
	}
	
	def dataCalorSolAnteAdic(dataset){
		log.info("********** Ejecutando dataCalorSolAnteAdic ********** ")
		def data=[]
		
		log.info("solicitudId : " + dataset['solicitudId'])
		def solicitud  =  AntecedenteAdicional.findById(dataset['solicitudId'])
		
		if(solicitud!=null){
			log.info("cargando solicitud : " + dataset['solicitudId'])
			data.add([label:'Codigo Solicitud', valor:solicitud.id]);
			
			if(solicitud.siniestro!=null){
				def siniestro    = Siniestro.findById(solicitud.siniestro.id)
				if(siniestro!=null){
					data.add([label:'Codigo Siniestro', valor:solicitud.siniestro.id]);
					data.add([label:'RUN Trabajador', valor:FormatosHelper.runFormatStatic(siniestro.trabajador.run)]);
					
					//Vemos si tiene RECA (calificacion) y si la RECA tiene fechaCalificacion
					def reca = RECA.findBySiniestro(solicitud.siniestro);
					def estado= ((reca != null && reca?.fechaCalificacion!=null)? 'Calificado':'Sin calificar');
					data.add([label:'Estado Siniestro', valor: estado]);
				}else{
					data.add([label:'Error', valor:'(no existe el siniestro id:'+solicitud.siniestro.id+'??)']);
				}
			}
			
			if(solicitud.tipoAntecedente.codigo != '3'){
				data.add([label:'Region', valor:solicitud.regionResponsable.descripcion, nombre: 'region', codigo: solicitud.regionResponsable.codigo]);
				log.debug "REGION DE LA SOLICITUD "+solicitud.regionResponsable.descripcion
			}
			
		}else{
			data.add([label:'Error', valor:'(no existe la solicitud id:'+dataset['solicitudId']+'??)']);
		}
		
		
		
		
		
		return data;
	}
	

	def validoParaUsuario(data,user){
		log.info("Ejecutando metodo validoParaUsuario")
		//Comprueba si tiene codigo -> sea igual al del usuario /> retorna true
		def codigoRegionTarea=null;
		log.info("Data values")
		data.each(){
			log.debug("--> : $it")
			if(it.codigo!=null) codigoRegionTarea=it.codigo;
		}
		log.debug("CodigoRegionTarea: $codigoRegionTarea")
		
		
		//Vemos si la tarea tiene region.
		if (codigoRegionTarea!=null){
			//Buscar region del usuario.
			def regionesUsuario=UsuarioService.getRegionesUsuario(user);
			log.debug("region del usuario:"+regionesUsuario)
			//Comprueba el modo dios
			if (regionesUsuario.central){
				log.debug("Usuario es de nivel central: DIOS")
				return true
			}else{
				if (codigoRegionTarea == regionesUsuario.region){
					return true
				}else{
					return false
				}
			}		
		}else{
			return true
		}
	}
	
	def data77bisIngreso(dataset){
		def data = []
		def bis = Bis.findById(dataset['bisId'])
		
		if(bis!=null){
			data.add([label:'Codigo 77bis', valor:bis.id]);
			data.add([label:'Dictamen', valor:bis.dictamen? 'Si' : 'No']);
			data.add([label:'Tipo Siniestro', valor:bis.tipoSiniestro.codigo == '3'?'Enfermedad' : 'Trabajo'])
			data.add([label:'RUN Trabajador', valor:FormatosHelper.runFormatStatic(bis.runTrabajador)]);
			data.add([label:'RUT Empleador', valor:FormatosHelper.runFormatStatic(bis.rutEmpleador)]);
			if (bis?.siniestro){
				data.add([label:'Codigo Siniestro', valor: bis?.siniestro.id]);
				def reca = RECA.findBySiniestro(bis.siniestro)
				if (reca){
					data.add([label:'Estado', valor: 'Listo para enviar a pago']);
				}else{
					if (bis?.siniestro.diatOA || bis?.siniestro.diepOA){
						data.add([label:'Estado', valor: 'En Proceso de Calificacion']);
					}else{
						data.add([label:'Estado', valor: 'A la espera de Denuncia OA']);
					}
				}
			}
		}else{
			data.add([label:'Error', valor:'(no existe la solicitud 77bis con id: '+dataset['bisId']+'??)']);
		}
		
		return data;
	}
	
	def data77bisPago(dataset){
		def data = []
		def bis = Bis.findById(dataset['bisId'])
		
		if(bis?.siniestro!=null){
			data.add([label:'Codigo Siniestro', valor: bis.siniestro.id]);
			data.add([label:'RUN Trabajador', valor:FormatosHelper.runFormatStatic(bis.siniestro.trabajador.run)]);
			data.add([label:'RUT Empleador', valor:FormatosHelper.runFormatStatic(bis.siniestro.empleador.rut)]);
			
			if(bis.siniestro.esEnfermedadProfesional){
				data.add([label:'Tipo Siniestro', valor:'Enfermedad Profesional']);
			}else{
				def diatOA=bis.siniestro.diatOA;
				if(diatOA!=null){
					if(diatOA.esAccidenteTrayecto){
						data.add([label:'Tipo Siniestro', valor:'Accidente Trayecto']);
					}else{
						data.add([label:'Tipo Siniestro', valor:'Accidente Trabajo ']);
					}
				}else{
					data.add([label:'Tipo Siniestro', valor:'Accidente (sin DIAT-OA)']);
				}
			}
			if (bis.montoAprobado){
				if(bis.montoAprobado == 0)
					data.add([label:'Solicitud', valor:'Rechazada en su totalidad']);
				else if (bis.montoAprobado < bis.montoSolicitado)
					data.add([label:'Solicitud', valor:'Parcialmente Aprobada']);
				else if (bis.montoAprobado == bis.montoSolicitado)
					data.add([label:'Solicitud', valor:'Aprobada']);
			}
			
		}else{
			data.add([label:'Error', valor:'(no existe la el siniestro con id: '+dataset['bisId']+'??)']);
		}
		
		return data;
	}
		
}

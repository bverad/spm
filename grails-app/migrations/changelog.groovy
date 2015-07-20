databaseChangeLog = {

	changeSet(author: "Carlo (generated)", id: "1386815646855-1") {
		createTable(tableName: "ACTIVIDAD_TRABAJADOR") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_8")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-2") {
		createTable(tableName: "ARANCEL_BASE") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_D")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ANESTESIAN1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "ANESTESIAN2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "ANESTESIAN3", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CARGA_APROBADA", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO1N1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO1N2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO1N3", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO2N1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO2N2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO2N3", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO3N1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO3N2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO3N3", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO4N1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO4N2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CIRUJANO4N3", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "DESDE", type: "TIMESTAMP")

			column(name: "EQUIPO", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "GLOSA", type: "VARCHAR(5000)") {
				constraints(nullable: "false")
			}

			column(name: "HASTA", type: "TIMESTAMP")

			column(name: "NIVEL_PABELLON", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "ORIGEN", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PROCEDIMIENTON1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "PROCEDIMIENTON2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "PROCEDIMIENTON3", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "SUB_GRUPO_ID", type: "BIGINT")

			column(name: "VALORN1", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALORN2", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALORN3", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-3") {
		createTable(tableName: "ARANCEL_CONVENIO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_D0")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ARANCEL_BASE_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CALCULO", type: "VARCHAR(255)")

			column(name: "CARGO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "CONVENIO_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCUENTO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "NIVEL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PESOS", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PORCENTAJE", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "VALOR", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_NUEVO", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_ORIGINAL", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-4") {
		createTable(tableName: "ARANCEL_PAQUETE") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_6")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ARANCEL_BASE_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CALCULO", type: "VARCHAR(255)")

			column(name: "CANTIDAD", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CARGO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "DESCUENTO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "NIVEL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PAQUETE_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "PESOS", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PORCENTAJE", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "VALOR", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_NUEVO", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_ORIGINAL", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-5") {
		createTable(tableName: "ARANCELES") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_E")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESDE", type: "TIMESTAMP")

			column(name: "HASTA", type: "TIMESTAMP")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-6") {
		createTable(tableName: "BANCO") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_3")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-7") {
		createTable(tableName: "CALIFICACION_DENUNCIANTE") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_C")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-8") {
		createTable(tableName: "CATEGORIA_OCUPACION") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_B")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-9") {
		createTable(tableName: "CENTRO_SALUD") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_7")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ATENCION_AMBULANCIA", type: "BOOLEAN")

			column(name: "ATENCION_URGENCIAS", type: "BOOLEAN")

			column(name: "COMUNA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "CUAL", type: "VARCHAR(255)")

			column(name: "DIRECCION", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "EMAIL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ES_ACTIVO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "HOSPITALIZACION", type: "BOOLEAN")

			column(name: "IMAGENOLOGIA", type: "BOOLEAN")

			column(name: "KINESIOLOGIA", type: "BOOLEAN")

			column(name: "NOMBRE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "NUMERO_AMBULANCIAS", type: "INT")

			column(name: "NUMERO_CAMAS", type: "INT")

			column(name: "OTRO", type: "BOOLEAN")

			column(name: "PABELLON", type: "BOOLEAN")

			column(name: "PRESTADOR_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "RESCATE_URGENCIAS", type: "BOOLEAN")

			column(name: "SALA_DE_RAYOS", type: "BOOLEAN")

			column(name: "TELEFONO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TIPO_CENTRO_SALUD_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TRASLADO_PACIENTES", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-10") {
		createTable(tableName: "CENTRO_SALUD_EN_CONVENIO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_9")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CENTRO_SALUD_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CONVENIO_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-11") {
		createTable(tableName: "COMUNA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_76")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")

			column(name: "PROVINCIA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-12") {
		createTable(tableName: "CONVENIO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_CF")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CARGOISL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "CARGO_RESPONSABLE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "EMAILISL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "EMAIL_RESPONSABLE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ES_ACTIVO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_ADJUDICACION", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_PROXIMO_REAJUSTE", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_RESOLUCION", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "INICIO", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "MONTO_CONVENIDO", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "NOMBRE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "NOMBREISL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "NOMBRE_RESPONSABLE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "NUMERO_LICITACION", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "NUMERO_RESOLUCION", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PERIODO_REAJUSTABLE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PRESTADOR_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "RECARGO_HORARIO_INHABIL", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "TELEFONOISL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TELEFONO_RESPONSABLE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TERMINO", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "TIPO_CONVENIO_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-13") {
		createTable(tableName: "CRITERIO_GRAVEDAD") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_EB")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-14") {
		createTable(tableName: "CUENTA_MEDICA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_B9")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CENTRO_SALUD_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ES_APROBADA", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_DESDE", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_EMISION", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_HASTA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "FOLIO_CUENTA", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "FORMATO_ORIGEN_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ID_ARCHIVO", type: "VARCHAR(255)")

			column(name: "TIPO_CUENTA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_CUENTA", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-15") {
		createTable(tableName: "CUENTA_MEDICA_ODAS") {
			column(name: "CUENTA_MEDICA_ID", type: "BIGINT")

			column(name: "ODAS_INTEGER", type: "INT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-16") {
		createTable(tableName: "CUENTA_MEDICA_OPAEPS") {
			column(name: "CUENTA_MEDICA_ID", type: "BIGINT")

			column(name: "OPAEPS_INTEGER", type: "INT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-17") {
		createTable(tableName: "CUENTA_MEDICA_OPAS") {
			column(name: "CUENTA_MEDICA_ID", type: "BIGINT")

			column(name: "OPAS_INTEGER", type: "INT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-18") {
		createTable(tableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ACCIDENTE_TRABAJO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_2")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ES_ORIGEN_COMUN", type: "BOOLEAN")

			column(name: "ORIGEN_DANYO_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA1", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA1_1", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA2", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA2_1", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA3", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA3_1", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA4", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA4_1", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA5", type: "BOOLEAN") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-19") {
		createTable(tableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ACCIDENTE_TRAYECTO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_4")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ES_ORIGEN_COMUN", type: "BOOLEAN")

			column(name: "PREGUNTA1", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA2", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA2_1", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA3", type: "float") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA4", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "TIPO_ACCIDENTE_TRAYECTO_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-20") {
		createTable(tableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ENFERMEDAD_PROFESIONAL") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_62")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA1", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA2", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA3", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA4", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA5", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA6", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA7", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TIPO_ENFERMEDAD_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-21") {
		createTable(tableName: "CUESTIONARIO_COMPLEJIDAD") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_5")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ACEPTA_PROPUESTA", type: "BOOLEAN")

			column(name: "COMPLEJIDAD_CALCULADA", type: "INT")

			column(name: "PREGUNTA1", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA2", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA3", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA4", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA5", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "PREGUNTA6", type: "BOOLEAN") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-22") {
		createTable(tableName: "CUESTIONARIO_OBRERO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_5E")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ACTIVIDAD_TRABAJADOR_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "OTRO", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-23") {
		createTable(tableName: "DETALLE_CUENTA_MEDICA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_D9")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CANTIDAD", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "CANTIDAD_FINAL", type: "INT")

			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "CONSULTA_CONVENIO", type: "VARCHAR(255)")

			column(name: "CONSULTA_MEDICA", type: "VARCHAR(255)")

			column(name: "CUENTA_MEDICA_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCUENTO_FINAL", type: "BIGINT")

			column(name: "DESCUENTO_UNITARIO", type: "BIGINT")

			column(name: "FECHA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "GLOSA", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "GLOSA_PACTADA", type: "VARCHAR(255)")

			column(name: "IVA", type: "BIGINT")

			column(name: "IVA_FINAL", type: "BIGINT")

			column(name: "IVA_PACTADO", type: "BIGINT")

			column(name: "RECARGO_UNITARIO", type: "BIGINT")

			column(name: "RECARGO_UNITARIO_FINAL", type: "BIGINT")

			column(name: "RECARGO_UNITARIO_PACTADO", type: "BIGINT")

			column(name: "RESPUESTA_CONVENIO", type: "VARCHAR(255)")

			column(name: "RESPUESTA_MEDICA", type: "VARCHAR(255)")

			column(name: "VALOR_CON_IVA_TOTAL", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_CON_IVA_TOTAL_FINAL", type: "BIGINT")

			column(name: "VALOR_CON_IVA_TOTAL_PACTADO", type: "BIGINT")

			column(name: "VALOR_TOTAL", type: "BIGINT")

			column(name: "VALOR_TOTAL_FINAL", type: "BIGINT")

			column(name: "VALOR_TOTAL_PACTADO", type: "BIGINT")

			column(name: "VALOR_UNITARIO", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_UNITARIO_FINAL", type: "BIGINT")

			column(name: "VALOR_UNITARIO_PACTADO", type: "BIGINT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-24") {
		createTable(tableName: "DETALLE_FACTURA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_1")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FACTURA_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "ID_CUENTA_MEDICA", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR_CUENTA_MEDICA", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-25") {
		createTable(tableName: "DIAT") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_20")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CALIFICACION_DENUNCIANTE_ID", type: "VARCHAR(255)")

			column(name: "CATEGORIA_OCUPACION_ID", type: "VARCHAR(255)")

			column(name: "CIIU_EMPLEADOR_ID", type: "VARCHAR(255)")

			column(name: "CIIU_PRINCIPAL_ID", type: "VARCHAR(255)")

			column(name: "CODIGO_ACTIVIDAD_EMPRESA", type: "VARCHAR(255)")

			column(name: "COMO", type: "VARCHAR(2147483647)")

			column(name: "DENUNCIANTE_ID", type: "VARCHAR(9)")

			column(name: "DETALLE_PRUEBA", type: "VARCHAR(255)")

			column(name: "DIRECCION_ACCIDENTE_COMUNA_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_ACCIDENTE_NOMBRE_CALLE", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_COMUNA_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_NOMBRE_CALLE", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_NUMERO", type: "INT")

			column(name: "DIRECCION_EMPLEADOR_RESTO_DIRECCION", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_TIPO_CALLE_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_COMUNA_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_NOMBRE_CALLE", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_NUMERO", type: "INT")

			column(name: "DIRECCION_TRABAJADOR_RESTO_DIRECCION", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_TIPO_CALLE_ID", type: "VARCHAR(255)")

			column(name: "DURACION_CONTRATO_ID", type: "VARCHAR(255)")

			column(name: "EMPLEADOR_ID", type: "VARCHAR(9)")

			column(name: "ES_ACCIDENTE_TRAYECTO", type: "BOOLEAN")

			column(name: "ES_TRABAJO_HABITUAL", type: "BOOLEAN")

			column(name: "ETNIA_ID", type: "VARCHAR(255)")

			column(name: "FECHA_ACCIDENTE", type: "TIMESTAMP")

			column(name: "FECHA_EMISION", type: "TIMESTAMP")

			column(name: "FECHA_INGRESO_EMPRESA", type: "TIMESTAMP")

			column(name: "GRAVEDAD_ID", type: "VARCHAR(255)")

			column(name: "HORA_INGRESO", type: "TIMESTAMP")

			column(name: "HORA_SALIDA", type: "TIMESTAMP")

			column(name: "LUGAR_ACCIDENTE", type: "VARCHAR(255)")

			column(name: "MEDIO_PRUEBA_ID", type: "VARCHAR(255)")

			column(name: "N_TRABAJADORES_HOMBRE", type: "INT")

			column(name: "N_TRABAJADORES_MUJER", type: "INT")

			column(name: "NACIONALIDAD_TRABAJADOR_ID", type: "VARCHAR(255)")

			column(name: "OTRO_PUEBLO", type: "VARCHAR(255)")

			column(name: "PROFESION_TRABAJADOR", type: "VARCHAR(255)")

			column(name: "PROPIEDAD_EMPRESA_ID", type: "VARCHAR(255)")

			column(name: "QUE", type: "VARCHAR(255)")

			column(name: "SINIESTRO_ID", type: "BIGINT")

			column(name: "TELEFONO_DENUNCIANTE", type: "VARCHAR(255)")

			column(name: "TELEFONO_EMPLEADOR", type: "INT")

			column(name: "TELEFONO_TRABAJADOR", type: "INT")

			column(name: "TIPO_ACCIDENTE_TRAYECTO_ID", type: "VARCHAR(255)")

			column(name: "TIPO_EMPRESA_ID", type: "VARCHAR(255)")

			column(name: "TIPO_REMUNERACION_ID", type: "VARCHAR(255)")

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)")

			column(name: "TRABAJO_HABITUAL_CUAL", type: "VARCHAR(255)")

			column(name: "XML_ENVIADO", type: "VARCHAR(2147483647)")

			column(name: "XML_RECIBIDO", type: "VARCHAR(2147483647)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-26") {
		createTable(tableName: "DIATWEB") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_8D")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DIAT_ID", type: "BIGINT")

			column(name: "EMPLEADOR_ID", type: "VARCHAR(9)")

			column(name: "FECHA_SINIESTRO", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-27") {
		createTable(tableName: "DIEP") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_200")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "AGENTE_SOSPECHOSO", type: "VARCHAR(255)")

			column(name: "CALIFICACION_DENUNCIANTE_ID", type: "VARCHAR(255)")

			column(name: "CATEGORIA_OCUPACION_ID", type: "VARCHAR(255)")

			column(name: "CIIU_EMPLEADOR_ID", type: "VARCHAR(255)")

			column(name: "CIIU_PRINCIPAL_ID", type: "VARCHAR(255)")

			column(name: "CODIGO_ACTIVIDAD_EMPRESA", type: "VARCHAR(255)")

			column(name: "DENUNCIANTE_ID", type: "VARCHAR(9)")

			column(name: "DESCRIPCION_TRABAJO", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_COMUNA_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_NOMBRE_CALLE", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_NUMERO", type: "INT")

			column(name: "DIRECCION_EMPLEADOR_RESTO_DIRECCION", type: "VARCHAR(255)")

			column(name: "DIRECCION_EMPLEADOR_TIPO_CALLE_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_COMUNA_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_NOMBRE_CALLE", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_NUMERO", type: "INT")

			column(name: "DIRECCION_TRABAJADOR_RESTO_DIRECCION", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR_TIPO_CALLE_ID", type: "VARCHAR(255)")

			column(name: "DURACION_CONTRATO_ID", type: "VARCHAR(255)")

			column(name: "EMPLEADOR_ID", type: "VARCHAR(9)")

			column(name: "ES_ANTECEDENTE_COMPANERO", type: "BOOLEAN")

			column(name: "ES_ANTECEDENTE_PREVIO", type: "BOOLEAN")

			column(name: "ETNIA_ID", type: "VARCHAR(255)")

			column(name: "FECHA_AGENTE", type: "TIMESTAMP")

			column(name: "FECHA_EMISION", type: "TIMESTAMP")

			column(name: "FECHA_INGRESO_EMPRESA", type: "TIMESTAMP")

			column(name: "FECHA_SINTOMA", type: "TIMESTAMP")

			column(name: "N_TRABAJADORES_HOMBRE", type: "INT")

			column(name: "N_TRABAJADORES_MUJER", type: "INT")

			column(name: "NACIONALIDAD_TRABAJADOR_ID", type: "VARCHAR(255)")

			column(name: "OTRO_PUEBLO", type: "VARCHAR(255)")

			column(name: "PARTE_CUERPO", type: "VARCHAR(255)")

			column(name: "PROFESION_TRABAJADOR", type: "VARCHAR(255)")

			column(name: "PROPIEDAD_EMPRESA_ID", type: "VARCHAR(255)")

			column(name: "PUESTO_TRABAJO", type: "VARCHAR(255)")

			column(name: "SINIESTRO_ID", type: "BIGINT")

			column(name: "SINTOMA", type: "VARCHAR(2147483647)")

			column(name: "TELEFONO_DENUNCIANTE", type: "VARCHAR(255)")

			column(name: "TELEFONO_EMPLEADOR", type: "INT")

			column(name: "TELEFONO_TRABAJADOR", type: "INT")

			column(name: "TIPO_EMPRESA_ID", type: "VARCHAR(255)")

			column(name: "TIPO_REMUNERACION_ID", type: "VARCHAR(255)")

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)")

			column(name: "XML_ENVIADO", type: "VARCHAR(2147483647)")

			column(name: "XML_RECIBIDO", type: "VARCHAR(2147483647)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-28") {
		createTable(tableName: "DOCUMENTACION_ADICIONAL") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_F")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DENUNCIAAT_ID", type: "BIGINT")

			column(name: "DENUNCIAEP_ID", type: "BIGINT")

			column(name: "DESCRIPCION", type: "VARCHAR(255)")

			column(name: "FILE_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "SOLICITUDAT_ID", type: "BIGINT")

			column(name: "SOLICITUDEP_ID", type: "BIGINT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-29") {
		createTable(tableName: "ERROR_CUENTA_MEDICA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_2B")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CUENTA_MEDICA_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "MENSAJE", type: "VARCHAR(2147483647)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-30") {
		createTable(tableName: "ERROR_DETALLE_CUENTA_MEDICA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_A")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DETALLE_CUENTA_MEDICA_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "MENSAJE", type: "VARCHAR(2147483647)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-31") {
		createTable(tableName: "ERROR_DETALLE_FACTURA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_C8")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DETALLE_FACTURA_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "MENSAJE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-32") {
		createTable(tableName: "ERROR_FACTURA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_C85")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FACTURA_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "MENSAJE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-33") {
		createTable(tableName: "ESTRUCTURA_JURIDICA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_8B")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-34") {
		createTable(tableName: "ETNIA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_3F")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-35") {
		createTable(tableName: "EXCEPCION") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_FE")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "AUTORIZADOR", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "CONTEXTO", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "MOTIVO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "SOLICITUDAT_ID", type: "BIGINT")

			column(name: "SOLICITUDEP_ID", type: "BIGINT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-36") {
		createTable(tableName: "FACTURA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_E9")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_ENVIO_PAGO", type: "TIMESTAMP")

			column(name: "FECHA_PAGADO", type: "TIMESTAMP")

			column(name: "FOLIO", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "PRESTADOR_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-37") {
		createTable(tableName: "FORMATO_ORIGEN") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_85")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-38") {
		createTable(tableName: "GRUPO") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_40")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-39") {
		createTable(tableName: "NACION") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_88")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-40") {
		createTable(tableName: "ODA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_13")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CENTRO_ATENCION_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION_EVENTO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_EVENTO", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "SINIESTRO_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-41") {
		createTable(tableName: "OPA") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_132")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CENTRO_ATENCION_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "COMUNA_TRABAJADOR_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR", type: "VARCHAR(255)")

			column(name: "DURACION_DIAS", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_CREACION", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "INICIO_VIGENCIA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "TELEFONO_TRABAJADOR", type: "INT")

			column(name: "USUARIO_EMISOR", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-42") {
		createTable(tableName: "OPAEP") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_47")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CENTRO_ATENCION_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "COMUNA_TRABAJADOR_ID", type: "VARCHAR(255)")

			column(name: "DIRECCION_TRABAJADOR", type: "VARCHAR(255)")

			column(name: "DURACION_DIAS", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_CREACION", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "INICIO_VIGENCIA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "TELEFONO_TRABAJADOR", type: "INT")

			column(name: "USUARIO_EMISOR", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-43") {
		createTable(tableName: "ORIGEN_DANYO") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_9C")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-44") {
		createTable(tableName: "PAQUETE") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_FB")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "COMPLEJIDAD", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "DESDE", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "EFECTIVIDAD", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "ESCALAMIENTO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "GLOSA", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "HASTA", type: "TIMESTAMP")

			column(name: "ORIGEN", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "REPOSO_ESTIMADO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "SUB_GRUPO_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "TIPO_PAQUETE_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "VALOR", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-45") {
		createTable(tableName: "PERSONA_JURIDICA") {
			column(name: "RUT", type: "VARCHAR(9)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_54")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "NOMBRE_FANTASIA", type: "VARCHAR(255)")

			column(name: "RAZON_SOCIAL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-46") {
		createTable(tableName: "PERSONA_NATURAL") {
			column(name: "RUN", type: "VARCHAR(9)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_FEA")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "APELLIDO_MATERNO", type: "VARCHAR(255)")

			column(name: "APELLIDO_PATERNO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_FALLECIMIENTO", type: "TIMESTAMP")

			column(name: "FECHA_NACIMIENTO", type: "TIMESTAMP")

			column(name: "NOMBRE", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "SEXO", type: "VARCHAR(1)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-47") {
		createTable(tableName: "PRESTADOR") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_6F")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "APODERADO_ID", type: "VARCHAR(9)")

			column(name: "BANCO_ID", type: "VARCHAR(255)")

			column(name: "COMUNA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "CUENTA", type: "VARCHAR(255)")

			column(name: "DESDEAP", type: "TIMESTAMP")

			column(name: "DESDERL", type: "TIMESTAMP")

			column(name: "DESIGNACION", type: "VARCHAR(255)")

			column(name: "DIRECCION", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "EMAIL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ES_ACTIVO", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "ES_PERSONA_JURIDICA", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "ESTRUCTURA_JURIDICA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "HASTAAP", type: "TIMESTAMP")

			column(name: "HASTARL", type: "TIMESTAMP")

			column(name: "NUMERO_CUENTA", type: "VARCHAR(255)")

			column(name: "PERSONA_JURIDICA_ID", type: "VARCHAR(9)")

			column(name: "PERSONA_NATURAL_ID", type: "VARCHAR(9)")

			column(name: "REPRESENTANTE_LEGAL_ID", type: "VARCHAR(9)")

			column(name: "TELEFONO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "TIPO_CUENTA_ID", type: "VARCHAR(255)")

			column(name: "TIPO_PRESTADOR_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-48") {
		createTable(tableName: "PROVINCIA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_857")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")

			column(name: "REGION_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-49") {
		createTable(tableName: "REGION") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_8F")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-50") {
		createTable(tableName: "SDAAT") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_4B")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CODIGO_ACTIVIDAD_EMPRESA", type: "INT")

			column(name: "CUESTIONARIO_COMPLEJIDAD_ID", type: "BIGINT")

			column(name: "CUESTIONARIO_OBRERO_ID", type: "BIGINT")

			column(name: "CUESTIONARIO_ORIGEN_TRABAJO_ID", type: "BIGINT")

			column(name: "CUESTIONARIO_ORIGEN_TRAYECTO_ID", type: "BIGINT")

			column(name: "DENUNCIANTE_ID", type: "VARCHAR(9)")

			column(name: "DIAT_ID", type: "BIGINT")

			column(name: "EMPLEADOR_ID", type: "VARCHAR(9)")

			column(name: "ES_ACCIDENTE_TRAYECTO", type: "BOOLEAN")

			column(name: "FECHA_SINIESTRO", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "FIN_SOLICITUD", type: "TIMESTAMP")

			column(name: "INICIO_SOLICITUD", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "OPA_ID", type: "BIGINT")

			column(name: "RELATO", type: "VARCHAR(2147483647)")

			column(name: "SALIDA", type: "VARCHAR(255)")

			column(name: "SINIESTRO_ID", type: "BIGINT")

			column(name: "TIPO_DENUNCIANTE_ID", type: "VARCHAR(255)")

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "USUARIO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-51") {
		createTable(tableName: "SDAEP") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_4B1")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CODIGO_ACTIVIDAD_EMPRESA", type: "INT")

			column(name: "CUESTIONARIO_OBRERO_ID", type: "BIGINT")

			column(name: "CUESTIONARIO_ORIGEN_ID", type: "BIGINT")

			column(name: "DENUNCIANTE_ID", type: "VARCHAR(9)")

			column(name: "DIEP_ID", type: "BIGINT")

			column(name: "EMPLEADOR_ID", type: "VARCHAR(9)")

			column(name: "FECHA_SINTOMAS", type: "TIMESTAMP")

			column(name: "FIN_SOLICITUD", type: "TIMESTAMP")

			column(name: "INICIO_SOLICITUD", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "OPAEP_ID", type: "BIGINT")

			column(name: "RELATO", type: "VARCHAR(2147483647)")

			column(name: "SALIDA", type: "VARCHAR(255)")

			column(name: "SINIESTRO_ID", type: "BIGINT")

			column(name: "SOLICITUD_REINGRESO_ID", type: "BIGINT")

			column(name: "TIPO_DENUNCIANTE_ID", type: "VARCHAR(255)")

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "USUARIO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-52") {
		createTable(tableName: "SHIRO_PERMISSION") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_D4")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "PERMISSION", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "ROL", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-53") {
		createTable(tableName: "SINIESTRO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_50")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CUN", type: "VARCHAR(255)")

			column(name: "DIATOA_ID", type: "BIGINT")

			column(name: "DIEPOA_ID", type: "BIGINT")

			column(name: "EMPLEADOR_ID", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "ES_ENFERMEDAD_PROFESIONAL", type: "BOOLEAN") {
				constraints(nullable: "false")
			}

			column(name: "FECHA", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "OPA_ID", type: "BIGINT")

			column(name: "OPAEP_ID", type: "BIGINT")

			column(name: "RELATO", type: "VARCHAR(2147483647)")

			column(name: "TIPO_PATOLOGIA_ID", type: "VARCHAR(255)")

			column(name: "TRABAJADOR_ID", type: "VARCHAR(9)") {
				constraints(nullable: "false")
			}

			column(name: "USUARIO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-54") {
		createTable(tableName: "SOLICITUD_REINGRESO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_AA")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "FECHA_CREACION", type: "TIMESTAMP") {
				constraints(nullable: "false")
			}

			column(name: "SINIESTRO_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "SOLICITANTE_ID", type: "VARCHAR(9)")

			column(name: "SOLICITUD_ID", type: "BIGINT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-55") {
		createTable(tableName: "SUB_GRUPO") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_D97")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")

			column(name: "GRUPO_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-56") {
		createTable(tableName: "TIPO_ACCIDENTE_TRAYECTO") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_F3")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-57") {
		createTable(tableName: "TIPO_ACTIVIDAD_ECONOMICA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_4D")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-58") {
		createTable(tableName: "TIPO_CALLE") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_DE")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-59") {
		createTable(tableName: "TIPO_CENTRO_SALUD") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_8E")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-60") {
		createTable(tableName: "TIPO_CONVENIO") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_71")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-61") {
		createTable(tableName: "TIPO_CUENTA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_6E")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-62") {
		createTable(tableName: "TIPO_CUENTA_MEDICA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_43")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-63") {
		createTable(tableName: "TIPO_DURACION_CONTRATO") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_D1")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-64") {
		createTable(tableName: "TIPO_EMPRESA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_6A")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-65") {
		createTable(tableName: "TIPO_ENFERMEDAD") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_4C")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-66") {
		createTable(tableName: "TIPO_MEDIO_PRUEBA_ACCIDENTE") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_BE")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-67") {
		createTable(tableName: "TIPO_PAQUETE") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_9B")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-68") {
		createTable(tableName: "TIPO_PRESTADOR") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_91")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-69") {
		createTable(tableName: "TIPO_PROPIEDAD_EMPRESA") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_AF")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-70") {
		createTable(tableName: "TIPO_REMUNERACION") {
			column(name: "CODIGO", type: "VARCHAR(255)") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_DA")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "DESCRIPCION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-71") {
		createTable(tableName: "VALOR_PABELLON") {
			column(autoIncrement: "true", name: "ID", type: "BIGINT") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CONSTRAINT_49")
			}

			column(name: "VERSION", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "CONVENIO_ID", type: "BIGINT") {
				constraints(nullable: "false")
			}

			column(name: "NIVEL_PABELLON", type: "INT") {
				constraints(nullable: "false")
			}

			column(name: "VALOR", type: "INT") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-72") {
		addForeignKeyConstraint(baseColumnNames: "SUB_GRUPO_ID", baseTableName: "ARANCEL_BASE", baseTableSchemaName: "PUBLIC", constraintName: "FK4600AFC4C5591958", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SUB_GRUPO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-73") {
		addForeignKeyConstraint(baseColumnNames: "ARANCEL_BASE_ID", baseTableName: "ARANCEL_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FK37EA80365B0B57F4", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "ARANCEL_BASE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-74") {
		addForeignKeyConstraint(baseColumnNames: "CONVENIO_ID", baseTableName: "ARANCEL_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FK37EA8036A5F2CE59", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-75") {
		addForeignKeyConstraint(baseColumnNames: "ARANCEL_BASE_ID", baseTableName: "ARANCEL_PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FK2E69E7CE5B0B57F4", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "ARANCEL_BASE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-76") {
		addForeignKeyConstraint(baseColumnNames: "PAQUETE_ID", baseTableName: "ARANCEL_PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FK2E69E7CEEC5DDEBB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PAQUETE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-77") {
		addForeignKeyConstraint(baseColumnNames: "COMUNA_ID", baseTableName: "CENTRO_SALUD", baseTableSchemaName: "PUBLIC", constraintName: "FKB936BC131C9E1399", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-78") {
		addForeignKeyConstraint(baseColumnNames: "PRESTADOR_ID", baseTableName: "CENTRO_SALUD", baseTableSchemaName: "PUBLIC", constraintName: "FKB936BC13246D05DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PRESTADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-79") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CENTRO_SALUD_ID", baseTableName: "CENTRO_SALUD", baseTableSchemaName: "PUBLIC", constraintName: "FKB936BC137F91C591", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-80") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_SALUD_ID", baseTableName: "CENTRO_SALUD_EN_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKF91C716D7BB8F0F0", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-81") {
		addForeignKeyConstraint(baseColumnNames: "CONVENIO_ID", baseTableName: "CENTRO_SALUD_EN_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKF91C716DA5F2CE59", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-82") {
		addForeignKeyConstraint(baseColumnNames: "PROVINCIA_ID", baseTableName: "COMUNA", baseTableSchemaName: "PUBLIC", constraintName: "FKAF3F47C7B8936EFB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "PROVINCIA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-83") {
		addForeignKeyConstraint(baseColumnNames: "PRESTADOR_ID", baseTableName: "CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKDE4B88C3246D05DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PRESTADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-84") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CONVENIO_ID", baseTableName: "CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKDE4B88C3490CBDA2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-85") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_SALUD_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK632811127BB8F0F0", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-86") {
		addForeignKeyConstraint(baseColumnNames: "FORMATO_ORIGEN_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK63281112A2FBDEAC", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "FORMATO_ORIGEN", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-87") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CUENTA_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK632811125F896E9D", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-88") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK63281112317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-89") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "CUENTA_MEDICA_ODAS", baseTableSchemaName: "PUBLIC", constraintName: "FK7C607BD4858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-90") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "CUENTA_MEDICA_OPAEPS", baseTableSchemaName: "PUBLIC", constraintName: "FKE6D9CA55858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-91") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "CUENTA_MEDICA_OPAS", baseTableSchemaName: "PUBLIC", constraintName: "FK7C60A8E0858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-92") {
		addForeignKeyConstraint(baseColumnNames: "ORIGEN_DANYO_ID", baseTableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ACCIDENTE_TRABAJO", baseTableSchemaName: "PUBLIC", constraintName: "FKBD5F9093BB405E6", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "ORIGEN_DANYO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-93") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_ACCIDENTE_TRAYECTO_ID", baseTableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ACCIDENTE_TRAYECTO", baseTableSchemaName: "PUBLIC", constraintName: "FKEFD856A3FBCA7669", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ACCIDENTE_TRAYECTO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-94") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_ENFERMEDAD_ID", baseTableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ENFERMEDAD_PROFESIONAL", baseTableSchemaName: "PUBLIC", constraintName: "FK7AD629DDF906062", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ENFERMEDAD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-95") {
		addForeignKeyConstraint(baseColumnNames: "ACTIVIDAD_TRABAJADOR_ID", baseTableName: "CUESTIONARIO_OBRERO", baseTableSchemaName: "PUBLIC", constraintName: "FKE287BF745A981B6", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "ACTIVIDAD_TRABAJADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-96") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "DETALLE_CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FKD44E844A858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-97") {
		addForeignKeyConstraint(baseColumnNames: "FACTURA_ID", baseTableName: "DETALLE_FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FKDDD684B087B47B5B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "FACTURA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-98") {
		addForeignKeyConstraint(baseColumnNames: "CALIFICACION_DENUNCIANTE_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B7846512558", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CALIFICACION_DENUNCIANTE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-99") {
		addForeignKeyConstraint(baseColumnNames: "CATEGORIA_OCUPACION_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78A9EB36B2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CATEGORIA_OCUPACION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-100") {
		addForeignKeyConstraint(baseColumnNames: "CIIU_EMPLEADOR_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B7859C6142", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ACTIVIDAD_ECONOMICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-101") {
		addForeignKeyConstraint(baseColumnNames: "CIIU_PRINCIPAL_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78A85E64BB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ACTIVIDAD_ECONOMICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-102") {
		addForeignKeyConstraint(baseColumnNames: "DENUNCIANTE_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B783AF490EC", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-103") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_ACCIDENTE_COMUNA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B7837C61541", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-104") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_EMPLEADOR_COMUNA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78C3E32CD2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-105") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_EMPLEADOR_TIPO_CALLE_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78BBE92B4B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CALLE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-106") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_TRABAJADOR_COMUNA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78A3F69D17", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-107") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_TRABAJADOR_TIPO_CALLE_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B788890F110", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CALLE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-108") {
		addForeignKeyConstraint(baseColumnNames: "DURACION_CONTRATO_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78F84D80F8", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_DURACION_CONTRATO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-109") {
		addForeignKeyConstraint(baseColumnNames: "EMPLEADOR_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78D91E2A25", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-110") {
		addForeignKeyConstraint(baseColumnNames: "ETNIA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78D2100B3B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "ETNIA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-111") {
		addForeignKeyConstraint(baseColumnNames: "GRAVEDAD_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B7836BEED86", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CRITERIO_GRAVEDAD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-112") {
		addForeignKeyConstraint(baseColumnNames: "MEDIO_PRUEBA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B782F120022", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_MEDIO_PRUEBA_ACCIDENTE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-113") {
		addForeignKeyConstraint(baseColumnNames: "NACIONALIDAD_TRABAJADOR_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B786635A6C5", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "NACION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-114") {
		addForeignKeyConstraint(baseColumnNames: "PROPIEDAD_EMPRESA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78648FCA1C", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_PROPIEDAD_EMPRESA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-115") {
		addForeignKeyConstraint(baseColumnNames: "SINIESTRO_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B7868F4B0DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SINIESTRO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-116") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_ACCIDENTE_TRAYECTO_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78FBCA7669", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ACCIDENTE_TRAYECTO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-117") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_EMPRESA_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78FC041952", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_EMPRESA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-118") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_REMUNERACION_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78C3C52EC2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_REMUNERACION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-119") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "DIAT", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0B78317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-120") {
		addForeignKeyConstraint(baseColumnNames: "DIAT_ID", baseTableName: "DIATWEB", baseTableSchemaName: "PUBLIC", constraintName: "FK62A96CDC4ACAF199", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIAT", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-121") {
		addForeignKeyConstraint(baseColumnNames: "EMPLEADOR_ID", baseTableName: "DIATWEB", baseTableSchemaName: "PUBLIC", constraintName: "FK62A96CDCD91E2A25", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-122") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "DIATWEB", baseTableSchemaName: "PUBLIC", constraintName: "FK62A96CDC317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-123") {
		addForeignKeyConstraint(baseColumnNames: "CALIFICACION_DENUNCIANTE_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF046512558", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CALIFICACION_DENUNCIANTE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-124") {
		addForeignKeyConstraint(baseColumnNames: "CATEGORIA_OCUPACION_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0A9EB36B2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CATEGORIA_OCUPACION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-125") {
		addForeignKeyConstraint(baseColumnNames: "CIIU_EMPLEADOR_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF059C6142", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ACTIVIDAD_ECONOMICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-126") {
		addForeignKeyConstraint(baseColumnNames: "CIIU_PRINCIPAL_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0A85E64BB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ACTIVIDAD_ECONOMICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-127") {
		addForeignKeyConstraint(baseColumnNames: "DENUNCIANTE_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF03AF490EC", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-128") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_EMPLEADOR_COMUNA_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0C3E32CD2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-129") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_EMPLEADOR_TIPO_CALLE_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0BBE92B4B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CALLE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-130") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_TRABAJADOR_COMUNA_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0A3F69D17", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-131") {
		addForeignKeyConstraint(baseColumnNames: "DIRECCION_TRABAJADOR_TIPO_CALLE_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF08890F110", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CALLE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-132") {
		addForeignKeyConstraint(baseColumnNames: "DURACION_CONTRATO_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0F84D80F8", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_DURACION_CONTRATO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-133") {
		addForeignKeyConstraint(baseColumnNames: "EMPLEADOR_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0D91E2A25", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-134") {
		addForeignKeyConstraint(baseColumnNames: "ETNIA_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0D2100B3B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "ETNIA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-135") {
		addForeignKeyConstraint(baseColumnNames: "NACIONALIDAD_TRABAJADOR_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF06635A6C5", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "NACION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-136") {
		addForeignKeyConstraint(baseColumnNames: "PROPIEDAD_EMPRESA_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0648FCA1C", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_PROPIEDAD_EMPRESA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-137") {
		addForeignKeyConstraint(baseColumnNames: "SINIESTRO_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF068F4B0DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SINIESTRO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-138") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_EMPRESA_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0FC041952", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_EMPRESA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-139") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_REMUNERACION_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0C3C52EC2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_REMUNERACION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-140") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "DIEP", baseTableSchemaName: "PUBLIC", constraintName: "FK2F0BF0317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-141") {
		addForeignKeyConstraint(baseColumnNames: "DENUNCIAAT_ID", baseTableName: "DOCUMENTACION_ADICIONAL", baseTableSchemaName: "PUBLIC", constraintName: "FK5D194502FB39AC89", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIAT", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-142") {
		addForeignKeyConstraint(baseColumnNames: "DENUNCIAEP_ID", baseTableName: "DOCUMENTACION_ADICIONAL", baseTableSchemaName: "PUBLIC", constraintName: "FK5D194502FB703989", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-143") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITUDAT_ID", baseTableName: "DOCUMENTACION_ADICIONAL", baseTableSchemaName: "PUBLIC", constraintName: "FK5D1945028706E1C7", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SDAAT", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-144") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITUDEP_ID", baseTableName: "DOCUMENTACION_ADICIONAL", baseTableSchemaName: "PUBLIC", constraintName: "FK5D194502873D6EC7", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SDAEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-145") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "ERROR_CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK9F3E871B858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-146") {
		addForeignKeyConstraint(baseColumnNames: "DETALLE_CUENTA_MEDICA_ID", baseTableName: "ERROR_DETALLE_CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK5BF6E15354A4170B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DETALLE_CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-147") {
		addForeignKeyConstraint(baseColumnNames: "DETALLE_FACTURA_ID", baseTableName: "ERROR_DETALLE_FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FK6E279C794E84694A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DETALLE_FACTURA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-148") {
		addForeignKeyConstraint(baseColumnNames: "FACTURA_ID", baseTableName: "ERROR_FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FK1271384187B47B5B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "FACTURA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-149") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITUDAT_ID", baseTableName: "EXCEPCION", baseTableSchemaName: "PUBLIC", constraintName: "FK584816008706E1C7", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SDAAT", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-150") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITUDEP_ID", baseTableName: "EXCEPCION", baseTableSchemaName: "PUBLIC", constraintName: "FK58481600873D6EC7", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SDAEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-151") {
		addForeignKeyConstraint(baseColumnNames: "PRESTADOR_ID", baseTableName: "FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FKBEEB4778246D05DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PRESTADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-152") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_ATENCION_ID", baseTableName: "ODA", baseTableSchemaName: "PUBLIC", constraintName: "FK1AD2CB56D0C08", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-153") {
		addForeignKeyConstraint(baseColumnNames: "SINIESTRO_ID", baseTableName: "ODA", baseTableSchemaName: "PUBLIC", constraintName: "FK1AD2C68F4B0DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SINIESTRO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-154") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_ATENCION_ID", baseTableName: "OPA", baseTableSchemaName: "PUBLIC", constraintName: "FK1AEA0B56D0C08", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-155") {
		addForeignKeyConstraint(baseColumnNames: "COMUNA_TRABAJADOR_ID", baseTableName: "OPA", baseTableSchemaName: "PUBLIC", constraintName: "FK1AEA0DE8C0EBA", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-156") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_ATENCION_ID", baseTableName: "OPAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK650934BB56D0C08", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-157") {
		addForeignKeyConstraint(baseColumnNames: "COMUNA_TRABAJADOR_ID", baseTableName: "OPAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK650934BDE8C0EBA", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-158") {
		addForeignKeyConstraint(baseColumnNames: "SUB_GRUPO_ID", baseTableName: "PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FKD0AF19A1C5591958", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SUB_GRUPO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-159") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_PAQUETE_ID", baseTableName: "PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FKD0AF19A1C0146252", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_PAQUETE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-160") {
		addForeignKeyConstraint(baseColumnNames: "APODERADO_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8AEF6A89DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-161") {
		addForeignKeyConstraint(baseColumnNames: "BANCO_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8AACD504BB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "BANCO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-162") {
		addForeignKeyConstraint(baseColumnNames: "COMUNA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A1C9E1399", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-163") {
		addForeignKeyConstraint(baseColumnNames: "ESTRUCTURA_JURIDICA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A8C60070E", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "ESTRUCTURA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-164") {
		addForeignKeyConstraint(baseColumnNames: "PERSONA_JURIDICA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A9CB87AF4", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-165") {
		addForeignKeyConstraint(baseColumnNames: "PERSONA_NATURAL_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A3BB34280", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-166") {
		addForeignKeyConstraint(baseColumnNames: "REPRESENTANTE_LEGAL_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A51C00916", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-167") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CUENTA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A4B95E582", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CUENTA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-168") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_PRESTADOR_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8AE490FFB2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_PRESTADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-169") {
		addForeignKeyConstraint(baseColumnNames: "REGION_ID", baseTableName: "PROVINCIA", baseTableSchemaName: "PUBLIC", constraintName: "FKDF613BAD85122879", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "REGION", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-170") {
		addForeignKeyConstraint(baseColumnNames: "CUESTIONARIO_COMPLEJIDAD_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C636A82A484", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUESTIONARIO_COMPLEJIDAD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-171") {
		addForeignKeyConstraint(baseColumnNames: "CUESTIONARIO_OBRERO_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C63E06D51D0", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUESTIONARIO_OBRERO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-172") {
		addForeignKeyConstraint(baseColumnNames: "CUESTIONARIO_ORIGEN_TRABAJO_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C63B83F881A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ACCIDENTE_TRABAJO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-173") {
		addForeignKeyConstraint(baseColumnNames: "CUESTIONARIO_ORIGEN_TRAYECTO_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C636E26EF9A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ACCIDENTE_TRAYECTO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-174") {
		addForeignKeyConstraint(baseColumnNames: "DENUNCIANTE_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C633AF490EC", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-175") {
		addForeignKeyConstraint(baseColumnNames: "DIAT_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C634ACAF199", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIAT", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-176") {
		addForeignKeyConstraint(baseColumnNames: "EMPLEADOR_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C63D91E2A25", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-177") {
		addForeignKeyConstraint(baseColumnNames: "OPA_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C63BABABEDB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "OPA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-178") {
		addForeignKeyConstraint(baseColumnNames: "SINIESTRO_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C6368F4B0DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SINIESTRO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-179") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_DENUNCIANTE_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C63C21189C5", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CALIFICACION_DENUNCIANTE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-180") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "SDAAT", baseTableSchemaName: "PUBLIC", constraintName: "FK6837C63317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-181") {
		addForeignKeyConstraint(baseColumnNames: "CUESTIONARIO_OBRERO_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDBE06D51D0", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUESTIONARIO_OBRERO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-182") {
		addForeignKeyConstraint(baseColumnNames: "CUESTIONARIO_ORIGEN_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDB4C574B8E", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUESTIONARIO_CALIFICACION_ORIGEN_ENFERMEDAD_PROFESIONAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-183") {
		addForeignKeyConstraint(baseColumnNames: "DENUNCIANTE_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDB3AF490EC", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-184") {
		addForeignKeyConstraint(baseColumnNames: "DIEP_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDB4B017E99", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-185") {
		addForeignKeyConstraint(baseColumnNames: "EMPLEADOR_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDBD91E2A25", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-186") {
		addForeignKeyConstraint(baseColumnNames: "OPAEP_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDBF75E32BB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "OPAEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-187") {
		addForeignKeyConstraint(baseColumnNames: "SINIESTRO_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDB68F4B0DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SINIESTRO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-188") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITUD_REINGRESO_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDB707CC930", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SOLICITUD_REINGRESO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-189") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_DENUNCIANTE_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDBC21189C5", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "CALIFICACION_DENUNCIANTE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-190") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "SDAEP", baseTableSchemaName: "PUBLIC", constraintName: "FK6837CDB317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-191") {
		addForeignKeyConstraint(baseColumnNames: "DIATOA_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E25245B0F887", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIAT", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-192") {
		addForeignKeyConstraint(baseColumnNames: "DIEPOA_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E25212768387", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DIEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-193") {
		addForeignKeyConstraint(baseColumnNames: "EMPLEADOR_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E252D91E2A25", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUT", referencedTableName: "PERSONA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-194") {
		addForeignKeyConstraint(baseColumnNames: "OPA_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E252BABABEDB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "OPA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-195") {
		addForeignKeyConstraint(baseColumnNames: "OPAEP_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E252F75E32BB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "OPAEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-196") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_PATOLOGIA_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E25293826DAD", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_ENFERMEDAD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-197") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "SINIESTRO", baseTableSchemaName: "PUBLIC", constraintName: "FKA9F8E252317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-198") {
		addForeignKeyConstraint(baseColumnNames: "SINIESTRO_ID", baseTableName: "SOLICITUD_REINGRESO", baseTableSchemaName: "PUBLIC", constraintName: "FK6D95114368F4B0DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SINIESTRO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-199") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITANTE_ID", baseTableName: "SOLICITUD_REINGRESO", baseTableSchemaName: "PUBLIC", constraintName: "FK6D9511432F15A1C3", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-200") {
		addForeignKeyConstraint(baseColumnNames: "SOLICITUD_ID", baseTableName: "SOLICITUD_REINGRESO", baseTableSchemaName: "PUBLIC", constraintName: "FK6D951143AB038652", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SDAEP", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-201") {
		addForeignKeyConstraint(baseColumnNames: "GRUPO_ID", baseTableName: "SUB_GRUPO", baseTableSchemaName: "PUBLIC", constraintName: "FKFCC77DAA447C3DFB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "GRUPO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815646855-202") {
		addForeignKeyConstraint(baseColumnNames: "CONVENIO_ID", baseTableName: "VALOR_PABELLON", baseTableSchemaName: "PUBLIC", constraintName: "FK42D2406EA5F2CE59", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}
}

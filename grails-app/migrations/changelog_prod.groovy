databaseChangeLog = {

	changeSet(author: "Carlo (generated)", id: "1386815698391-1") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-2") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-3") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-4") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-5") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-6") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-7") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-8") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-9") {
		createTable(tableName: "CUENTA_MEDICA_ODAS") {
			column(name: "CUENTA_MEDICA_ID", type: "BIGINT")

			column(name: "ODAS_INTEGER", type: "INT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-10") {
		createTable(tableName: "CUENTA_MEDICA_OPAEPS") {
			column(name: "CUENTA_MEDICA_ID", type: "BIGINT")

			column(name: "OPAEPS_INTEGER", type: "INT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-11") {
		createTable(tableName: "CUENTA_MEDICA_OPAS") {
			column(name: "CUENTA_MEDICA_ID", type: "BIGINT")

			column(name: "OPAS_INTEGER", type: "INT")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-12") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-13") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-14") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-15") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-16") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-17") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-18") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-19") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-20") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-21") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-22") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-23") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-24") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-25") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-26") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-27") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-28") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-29") {
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

	changeSet(author: "Carlo (generated)", id: "1386815698391-30") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "ATENCION_AMBULANCIA", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-31") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "ATENCION_URGENCIAS", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-32") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "CUAL", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-33") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "HOSPITALIZACION", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-34") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "IMAGENOLOGIA", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-35") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "KINESIOLOGIA", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-36") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "OTRO", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-37") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "PABELLON", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-38") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "RESCATE_URGENCIAS", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-39") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "SALA_DE_RAYOS", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-40") {
		addColumn(tableName: "CENTRO_SALUD") {
			column(name: "TRASLADO_PACIENTES", type: "BOOLEAN")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-41") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "APODERADO_ID", type: "VARCHAR(9)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-42") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "BANCO_ID", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-43") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "COMUNA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-44") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "CUENTA", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-45") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "DESDEAP", type: "TIMESTAMP")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-46") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "DESDERL", type: "TIMESTAMP")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-47") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "DESIGNACION", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-48") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "ESTRUCTURA_JURIDICA_ID", type: "VARCHAR(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-49") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "ES_PERSONA_JURIDICA", type: "BOOLEAN") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-50") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "HASTAAP", type: "TIMESTAMP")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-51") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "HASTARL", type: "TIMESTAMP")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-52") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "NUMERO_CUENTA", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-53") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "REPRESENTANTE_LEGAL_ID", type: "VARCHAR(9)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-54") {
		addColumn(tableName: "PRESTADOR") {
			column(name: "TIPO_CUENTA_ID", type: "VARCHAR(255)")
		}
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-55") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "actividad_trabajador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-56") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "calificacion_denunciante")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-57") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "categoria_ocupacion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-58") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "comuna_id", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-59") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "direccion", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-60") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "email", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-61") {
		modifyDataType(columnName: "es_activo", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-62") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-63") {
		modifyDataType(columnName: "prestador_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-64") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "telefono", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-65") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "tipo_centro_salud_id", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-66") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-67") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "comuna")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-68") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "criterio_gravedad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-69") {
		modifyDataType(columnName: "es_origen_comun", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-70") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-71") {
		modifyDataType(columnName: "pregunta1", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-72") {
		modifyDataType(columnName: "pregunta2", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-73") {
		modifyDataType(columnName: "pregunta3", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-74") {
		modifyDataType(columnName: "pregunta4", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-75") {
		modifyDataType(columnName: "pregunta5", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-76") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trabajo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-77") {
		modifyDataType(columnName: "es_origen_comun", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-78") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-79") {
		modifyDataType(columnName: "pregunta1", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-80") {
		modifyDataType(columnName: "pregunta2", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-81") {
		modifyDataType(columnName: "pregunta3", newDataType: "DOUBLE(17)", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-82") {
		modifyDataType(columnName: "pregunta4", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-83") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-84") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_enfermedad_profesional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-85") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_calificacion_origen_enfermedad_profesional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-86") {
		modifyDataType(columnName: "acepta_propuesta", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-87") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-88") {
		modifyDataType(columnName: "pregunta1", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-89") {
		modifyDataType(columnName: "pregunta2", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-90") {
		modifyDataType(columnName: "pregunta3", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-91") {
		modifyDataType(columnName: "pregunta4", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-92") {
		modifyDataType(columnName: "pregunta5", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-93") {
		modifyDataType(columnName: "pregunta6", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-94") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_complejidad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-95") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_obrero")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-96") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "cuestionario_obrero")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-97") {
		modifyDataType(columnName: "como", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-98") {
		modifyDataType(columnName: "es_accidente_trayecto", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-99") {
		modifyDataType(columnName: "es_trabajo_habitual", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-100") {
		modifyDataType(columnName: "fecha_accidente", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-101") {
		modifyDataType(columnName: "fecha_emision", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-102") {
		modifyDataType(columnName: "fecha_ingreso_empresa", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-103") {
		modifyDataType(columnName: "hora_ingreso", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-104") {
		modifyDataType(columnName: "hora_salida", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-105") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-106") {
		modifyDataType(columnName: "siniestro_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-107") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-108") {
		modifyDataType(columnName: "xml_enviado", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-109") {
		modifyDataType(columnName: "xml_recibido", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "diat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-110") {
		modifyDataType(columnName: "diat_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "diatweb")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-111") {
		modifyDataType(columnName: "fecha_siniestro", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diatweb")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-112") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "diatweb")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-113") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "diatweb")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-114") {
		modifyDataType(columnName: "es_antecedente_companero", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-115") {
		modifyDataType(columnName: "es_antecedente_previo", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-116") {
		modifyDataType(columnName: "fecha_agente", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-117") {
		modifyDataType(columnName: "fecha_emision", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-118") {
		modifyDataType(columnName: "fecha_ingreso_empresa", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-119") {
		modifyDataType(columnName: "fecha_sintoma", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-120") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-121") {
		modifyDataType(columnName: "siniestro_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-122") {
		modifyDataType(columnName: "sintoma", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-123") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-124") {
		modifyDataType(columnName: "xml_enviado", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-125") {
		modifyDataType(columnName: "xml_recibido", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "diep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-126") {
		modifyDataType(columnName: "denunciaat_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "documentacion_adicional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-127") {
		modifyDataType(columnName: "denunciaep_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "documentacion_adicional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-128") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "documentacion_adicional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-129") {
		modifyDataType(columnName: "solicitudat_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "documentacion_adicional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-130") {
		modifyDataType(columnName: "solicitudep_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "documentacion_adicional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-131") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "documentacion_adicional")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-132") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "etnia")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-133") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "excepcion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-134") {
		modifyDataType(columnName: "solicitudat_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "excepcion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-135") {
		modifyDataType(columnName: "solicitudep_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "excepcion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-136") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "excepcion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-137") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "nacion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-138") {
		modifyDataType(columnName: "centro_atencion_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "oda")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-139") {
		modifyDataType(columnName: "fecha_evento", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "oda")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-140") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "oda")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-141") {
		modifyDataType(columnName: "siniestro_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "oda")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-142") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "oda")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-143") {
		modifyDataType(columnName: "centro_atencion_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "opa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-144") {
		modifyDataType(columnName: "fecha_creacion", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "opa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-145") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "opa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-146") {
		modifyDataType(columnName: "inicio_vigencia", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "opa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-147") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "opa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-148") {
		modifyDataType(columnName: "centro_atencion_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "opaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-149") {
		modifyDataType(columnName: "fecha_creacion", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "opaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-150") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "opaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-151") {
		modifyDataType(columnName: "inicio_vigencia", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "opaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-152") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "opaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-153") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "origen_danyo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-154") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "razon_social", schemaName: "dbo", tableName: "persona_juridica")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-155") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "persona_juridica")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-156") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "apellido_paterno", schemaName: "dbo", tableName: "persona_natural")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-157") {
		modifyDataType(columnName: "fecha_fallecimiento", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "persona_natural")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-158") {
		modifyDataType(columnName: "fecha_nacimiento", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "persona_natural")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-159") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "nombre", schemaName: "dbo", tableName: "persona_natural")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-160") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "persona_natural")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-161") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "direccion", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-162") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "email", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-163") {
		modifyDataType(columnName: "es_activo", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-164") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-165") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "telefono", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-166") {
		addNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "tipo_prestador_id", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-167") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-168") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "provincia")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-169") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "region")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-170") {
		modifyDataType(columnName: "cuestionario_complejidad_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-171") {
		modifyDataType(columnName: "cuestionario_obrero_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-172") {
		modifyDataType(columnName: "cuestionario_origen_trabajo_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-173") {
		modifyDataType(columnName: "cuestionario_origen_trayecto_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-174") {
		modifyDataType(columnName: "diat_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-175") {
		modifyDataType(columnName: "es_accidente_trayecto", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-176") {
		modifyDataType(columnName: "fecha_siniestro", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-177") {
		modifyDataType(columnName: "fin_solicitud", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-178") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-179") {
		modifyDataType(columnName: "inicio_solicitud", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-180") {
		modifyDataType(columnName: "opa_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-181") {
		modifyDataType(columnName: "relato", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-182") {
		modifyDataType(columnName: "siniestro_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-183") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaat")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-184") {
		modifyDataType(columnName: "cuestionario_obrero_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-185") {
		modifyDataType(columnName: "cuestionario_origen_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-186") {
		modifyDataType(columnName: "diep_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-187") {
		modifyDataType(columnName: "fecha_sintomas", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-188") {
		modifyDataType(columnName: "fin_solicitud", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-189") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-190") {
		modifyDataType(columnName: "inicio_solicitud", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-191") {
		modifyDataType(columnName: "opaep_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-192") {
		modifyDataType(columnName: "relato", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-193") {
		modifyDataType(columnName: "siniestro_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-194") {
		modifyDataType(columnName: "solicitud_reingreso_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-195") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "sdaep")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-196") {
		modifyDataType(columnName: "diatoa_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-197") {
		modifyDataType(columnName: "diepoa_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-198") {
		modifyDataType(columnName: "es_enfermedad_profesional", newDataType: "BOOLEAN", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-199") {
		modifyDataType(columnName: "fecha", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-200") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-201") {
		modifyDataType(columnName: "opa_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-202") {
		modifyDataType(columnName: "opaep_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-203") {
		modifyDataType(columnName: "relato", newDataType: "VARCHAR(2147483647)", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-204") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "siniestro")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-205") {
		modifyDataType(columnName: "fecha_creacion", newDataType: "TIMESTAMP", schemaName: "dbo", tableName: "solicitud_reingreso")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-206") {
		modifyDataType(columnName: "id", newDataType: "BIGINT", schemaName: "dbo", tableName: "solicitud_reingreso")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-207") {
		modifyDataType(columnName: "siniestro_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "solicitud_reingreso")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-208") {
		modifyDataType(columnName: "solicitud_id", newDataType: "BIGINT", schemaName: "dbo", tableName: "solicitud_reingreso")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-209") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "solicitud_reingreso")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-210") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_accidente_trayecto")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-211") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_actividad_economica")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-212") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_calle")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-213") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_centro_salud")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-214") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_duracion_contrato")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-215") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_empresa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-216") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_enfermedad")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-217") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_medio_prueba_accidente")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-218") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_prestador")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-219") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_propiedad_empresa")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-220") {
		modifyDataType(columnName: "version", newDataType: "BIGINT", schemaName: "dbo", tableName: "tipo_remuneracion")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-221") {
		dropForeignKeyConstraint(baseTableName: "shiro_role_permissions", baseTableSchemaName: "dbo", constraintName: "FK389B46C97659639E")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-222") {
		dropForeignKeyConstraint(baseTableName: "shiro_user_permissions", baseTableSchemaName: "dbo", constraintName: "FK34555A9E1B84277E")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-223") {
		dropForeignKeyConstraint(baseTableName: "shiro_user_roles", baseTableSchemaName: "dbo", constraintName: "FKBA2210577659639E")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-224") {
		dropForeignKeyConstraint(baseTableName: "shiro_user_roles", baseTableSchemaName: "dbo", constraintName: "FKBA2210571B84277E")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-258") {
		dropIndex(indexName: "UQ__shiro_ro__72E12F1BA2C38DDF", schemaName: "dbo", tableName: "shiro_role")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-259") {
		dropIndex(indexName: "UQ__shiro_us__F3DBC572007141B5", schemaName: "dbo", tableName: "shiro_user")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-260") {
		dropIndex(indexName: "UK_principal_name", schemaName: "dbo", tableName: "sysdiagrams")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-261") {
		dropTable(schemaName: "dbo", tableName: "sexo")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-262") {
		dropTable(schemaName: "dbo", tableName: "shiro_role")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-263") {
		dropTable(schemaName: "dbo", tableName: "shiro_role_permissions")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-264") {
		dropTable(schemaName: "dbo", tableName: "shiro_user")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-265") {
		dropTable(schemaName: "dbo", tableName: "shiro_user_permissions")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-266") {
		dropTable(schemaName: "dbo", tableName: "shiro_user_roles")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-267") {
		dropTable(schemaName: "dbo", tableName: "sysdiagrams")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-268") {
		dropTable(schemaName: "dbo", tableName: "tipo_accidente")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-225") {
		addForeignKeyConstraint(baseColumnNames: "SUB_GRUPO_ID", baseTableName: "ARANCEL_BASE", baseTableSchemaName: "PUBLIC", constraintName: "FK4600AFC4C5591958", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SUB_GRUPO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-226") {
		addForeignKeyConstraint(baseColumnNames: "ARANCEL_BASE_ID", baseTableName: "ARANCEL_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FK37EA80365B0B57F4", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "ARANCEL_BASE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-227") {
		addForeignKeyConstraint(baseColumnNames: "CONVENIO_ID", baseTableName: "ARANCEL_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FK37EA8036A5F2CE59", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-228") {
		addForeignKeyConstraint(baseColumnNames: "ARANCEL_BASE_ID", baseTableName: "ARANCEL_PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FK2E69E7CE5B0B57F4", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "ARANCEL_BASE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-229") {
		addForeignKeyConstraint(baseColumnNames: "PAQUETE_ID", baseTableName: "ARANCEL_PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FK2E69E7CEEC5DDEBB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PAQUETE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-230") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_SALUD_ID", baseTableName: "CENTRO_SALUD_EN_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKF91C716D7BB8F0F0", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-231") {
		addForeignKeyConstraint(baseColumnNames: "CONVENIO_ID", baseTableName: "CENTRO_SALUD_EN_CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKF91C716DA5F2CE59", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-232") {
		addForeignKeyConstraint(baseColumnNames: "PRESTADOR_ID", baseTableName: "CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKDE4B88C3246D05DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PRESTADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-233") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CONVENIO_ID", baseTableName: "CONVENIO", baseTableSchemaName: "PUBLIC", constraintName: "FKDE4B88C3490CBDA2", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-234") {
		addForeignKeyConstraint(baseColumnNames: "CENTRO_SALUD_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK632811127BB8F0F0", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CENTRO_SALUD", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-235") {
		addForeignKeyConstraint(baseColumnNames: "FORMATO_ORIGEN_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK63281112A2FBDEAC", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "FORMATO_ORIGEN", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-236") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CUENTA_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK632811125F896E9D", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-237") {
		addForeignKeyConstraint(baseColumnNames: "TRABAJADOR_ID", baseTableName: "CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK63281112317DCD48", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-238") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "CUENTA_MEDICA_ODAS", baseTableSchemaName: "PUBLIC", constraintName: "FK7C607BD4858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-239") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "CUENTA_MEDICA_OPAEPS", baseTableSchemaName: "PUBLIC", constraintName: "FKE6D9CA55858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-240") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "CUENTA_MEDICA_OPAS", baseTableSchemaName: "PUBLIC", constraintName: "FK7C60A8E0858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-241") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "DETALLE_CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FKD44E844A858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-242") {
		addForeignKeyConstraint(baseColumnNames: "FACTURA_ID", baseTableName: "DETALLE_FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FKDDD684B087B47B5B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "FACTURA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-243") {
		addForeignKeyConstraint(baseColumnNames: "CUENTA_MEDICA_ID", baseTableName: "ERROR_CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK9F3E871B858E0B0A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-244") {
		addForeignKeyConstraint(baseColumnNames: "DETALLE_CUENTA_MEDICA_ID", baseTableName: "ERROR_DETALLE_CUENTA_MEDICA", baseTableSchemaName: "PUBLIC", constraintName: "FK5BF6E15354A4170B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DETALLE_CUENTA_MEDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-245") {
		addForeignKeyConstraint(baseColumnNames: "DETALLE_FACTURA_ID", baseTableName: "ERROR_DETALLE_FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FK6E279C794E84694A", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "DETALLE_FACTURA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-246") {
		addForeignKeyConstraint(baseColumnNames: "FACTURA_ID", baseTableName: "ERROR_FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FK1271384187B47B5B", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "FACTURA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-247") {
		addForeignKeyConstraint(baseColumnNames: "PRESTADOR_ID", baseTableName: "FACTURA", baseTableSchemaName: "PUBLIC", constraintName: "FKBEEB4778246D05DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "PRESTADOR", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-248") {
		addForeignKeyConstraint(baseColumnNames: "SUB_GRUPO_ID", baseTableName: "PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FKD0AF19A1C5591958", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "SUB_GRUPO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-249") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_PAQUETE_ID", baseTableName: "PAQUETE", baseTableSchemaName: "PUBLIC", constraintName: "FKD0AF19A1C0146252", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_PAQUETE", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-250") {
		addForeignKeyConstraint(baseColumnNames: "APODERADO_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8AEF6A89DB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-251") {
		addForeignKeyConstraint(baseColumnNames: "BANCO_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8AACD504BB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "BANCO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-252") {
		addForeignKeyConstraint(baseColumnNames: "COMUNA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A1C9E1399", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "COMUNA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-253") {
		addForeignKeyConstraint(baseColumnNames: "ESTRUCTURA_JURIDICA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A8C60070E", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "ESTRUCTURA_JURIDICA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-254") {
		addForeignKeyConstraint(baseColumnNames: "REPRESENTANTE_LEGAL_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A51C00916", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "RUN", referencedTableName: "PERSONA_NATURAL", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-255") {
		addForeignKeyConstraint(baseColumnNames: "TIPO_CUENTA_ID", baseTableName: "PRESTADOR", baseTableSchemaName: "PUBLIC", constraintName: "FKC9D97F8A4B95E582", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "TIPO_CUENTA", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-256") {
		addForeignKeyConstraint(baseColumnNames: "GRUPO_ID", baseTableName: "SUB_GRUPO", baseTableSchemaName: "PUBLIC", constraintName: "FKFCC77DAA447C3DFB", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "CODIGO", referencedTableName: "GRUPO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}

	changeSet(author: "Carlo (generated)", id: "1386815698391-257") {
		addForeignKeyConstraint(baseColumnNames: "CONVENIO_ID", baseTableName: "VALOR_PABELLON", baseTableSchemaName: "PUBLIC", constraintName: "FK42D2406EA5F2CE59", deferrable: "false", initiallyDeferred: "false", onDelete: "RESTRICT", onUpdate: "RESTRICT", referencedColumnNames: "ID", referencedTableName: "CONVENIO", referencedTableSchemaName: "PUBLIC", referencesUniqueColumn: "false")
	}
}

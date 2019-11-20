/*
	Este archivo fue editado al utilizar el nuevo verificar.jar.
	Se agregó el modificador UNSIGNED en las columnas brinda.precio y clases.porcentaje.
	Sin embargo esta edición produjo dos warnings, uno por cada columna.
	Más abajo se aclara el por qué de estos warnings.

*/
\W -- Usamos esto para mostrar a qué se deben las advertencias.

/*
	Como se puede apreciar en la consola al ejecutar el archivo,
	las advertencias se deben a que pronto DECIMAL() junto a UNSIGNED
	quedarán en desuso. En su documentación, mysql, recomienda utilizar CHECK en su
	lugar, para de esta forma poder restringir los valores posibles del atributo.
	
	La documentación dice:
	"UNSIGNED, if specified, disallows negative values.
	As of MySQL 8.0.17, the UNSIGNED attribute is deprecated for columns of type DECIMAL (and any synonyms)
	and will be removed in a future MySQL version. Consider using a simple CHECK constraint instead for such columns."
	
	Fuente: https://dev.mysql.com/doc/refman/8.0/en/numeric-type-overview.html
	(Item DECIMAL[(M[,D])] [UNSIGNED] [ZEROFILL]).
	
	Para obtener la correctitud de el programa 'verificar.jar', se optó por conservar DECIMAL() UNSIGNED a pesar de quedará en desuso.
*/

CREATE DATABASE vuelos;

USE vuelos;

CREATE TABLE ubicaciones(
	pais VARCHAR(50) NOT NULL,
	estado VARCHAR(50) NOT NULL,
	ciudad VARCHAR(50) NOT NULL,
	huso TINYINT NOT NULL, -- 1 byte
	
	CONSTRAINT CHK_ubicaciones
	CHECK (huso >= -12 AND huso <= 12),
	
	CONSTRAINT PK_ubicaciones
	PRIMARY KEY (pais, estado, ciudad)
	
) ENGINE=InnoDB;

CREATE TABLE aeropuertos(
	codigo VARCHAR(30) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	pais VARCHAR(50) NOT NULL,
	estado VARCHAR(50) NOT NULL,
	ciudad VARCHAR(50) NOT NULL,
	
	CONSTRAINT PK_ubicaciones
	PRIMARY KEY (codigo),
	
	CONSTRAINT FK_aeropuertos
	FOREIGN KEY (pais, estado, ciudad) REFERENCES ubicaciones(pais, estado, ciudad)
	
) ENGINE=InnoDB;

CREATE TABLE modelos_avion(
	modelo VARCHAR(50) NOT NULL,
	fabricante VARCHAR(50) NOT NULL,
	cabinas SMALLINT UNSIGNED NOT NULL,
	cant_asientos SMALLINT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_modelos_avion
	PRIMARY KEY (modelo)
	
) ENGINE=InnoDB;

CREATE TABLE vuelos_programados(
	numero VARCHAR(30) NOT NULL,
	aeropuerto_salida VARCHAR(30) NOT NULL,
	aeropuerto_llegada VARCHAR(30) NOT NULL,
	
	CONSTRAINT PK_vuelos_programados
	PRIMARY KEY (numero),
	
	CONSTRAINT FK_vuelos_programados_Asalida
	FOREIGN KEY (aeropuerto_salida) REFERENCES aeropuertos(codigo),
	
	CONSTRAINT FK_vuelos_programados_Allegada
	FOREIGN KEY (aeropuerto_llegada) REFERENCES aeropuertos(codigo)
	
) ENGINE=InnoDB;

CREATE TABLE salidas(
	vuelo VARCHAR(30) NOT NULL,
	dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
	hora_sale TIME(0) NOT NULL,
	hora_llega TIME(0) NOT NULL,
	modelo_avion VARCHAR(50) NOT NULL,
		
	CONSTRAINT PK_salidas
	PRIMARY KEY (vuelo, dia),
	
	CONSTRAINT FK_salidas_vuelo
	FOREIGN KEY (vuelo) REFERENCES vuelos_programados(numero),
	
	CONSTRAINT FK_salidas_modelo
	FOREIGN KEY (modelo_avion) REFERENCES modelos_avion(modelo)

) ENGINE=InnoDB;


CREATE TABLE instancias_vuelo (
	vuelo VARCHAR(30) NOT NULL,
	fecha DATE NOT NULL,
	dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
	estado VARCHAR(30),
	
	CONSTRAINT PK_instancias_vuelo
	PRIMARY KEY (vuelo, fecha),
	
	CONSTRAINT FK_instancias_vuelo
	FOREIGN KEY (vuelo, dia) REFERENCES salidas(vuelo, dia)
	
) ENGINE=InnoDB;


CREATE TABLE clases (
	nombre VARCHAR(40) NOT NULL,
	porcentaje DECIMAL(2,2) UNSIGNED NOT NULL,
	
	CONSTRAINT PK_clases
	PRIMARY KEY (nombre)
	
) ENGINE=InnoDB;


CREATE TABLE comodidades (
	codigo SMALLINT UNSIGNED NOT NULL,
	descripcion TINYTEXT NOT NULL,
	
	CONSTRAINT PK_comodidades
	PRIMARY KEY (codigo)
	
) ENGINE=InnoDB;


CREATE TABLE pasajeros (
	doc_tipo VARCHAR(20) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL, 
	apellido VARCHAR(50) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	nacionalidad VARCHAR(50) NOT NULL,
	
	
	CONSTRAINT PK_pasajeros
	PRIMARY KEY (doc_tipo, doc_nro)
	
) ENGINE=InnoDB;


CREATE TABLE empleados (
	legajo INT UNSIGNED NOT NULL, 
	password CHAR(32) NOT NULL,
	doc_tipo VARCHAR(20) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL, 
	apellido VARCHAR(50) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	direccion VARCHAR(50) NOT NULL,
	telefono VARCHAR(20) NOT NULL,

	CONSTRAINT PK_empleados
	PRIMARY KEY (legajo)
	
) ENGINE=InnoDB;


CREATE TABLE reservas (
	numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	vencimiento DATE NOT NULL,
	estado VARCHAR(30) NOT NULL,
	doc_tipo VARCHAR(20) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL, 
	legajo INT UNSIGNED NOT NULL, 

	CONSTRAINT PK_reservas
	PRIMARY KEY (numero),
	
	CONSTRAINT FK_reservas_doc
	FOREIGN KEY (doc_tipo, doc_nro) REFERENCES pasajeros(doc_tipo, doc_nro),
	
	CONSTRAINT FK_reservas_legajo
	FOREIGN KEY (legajo) REFERENCES empleados(legajo)
	
) ENGINE=InnoDB;


CREATE TABLE brinda (
	vuelo VARCHAR(30) NOT NULL,
	dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
	clase VARCHAR(40) NOT NULL,
	precio DECIMAL(7, 2) UNSIGNED NOT NULL,
	cant_asientos SMALLINT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_brinda
	PRIMARY KEY (vuelo, dia, clase),
	
	CONSTRAINT FK_brinda_vuelo_dia
	FOREIGN KEY (vuelo, dia) REFERENCES salidas(vuelo, dia),
	
	CONSTRAINT FK_brinda_clase
	FOREIGN KEY (clase) REFERENCES clases(nombre)
	
) ENGINE=InnoDB;


CREATE TABLE posee (
	clase VARCHAR(40) NOT NULL,
	comodidad SMALLINT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_posee
	PRIMARY KEY (clase, comodidad),

	CONSTRAINT FK_posee_clase
	FOREIGN KEY (clase) REFERENCES clases(nombre),
	
	CONSTRAINT FK_posee_comodidad
	FOREIGN KEY (comodidad) REFERENCES comodidades(codigo)
	
) ENGINE=InnoDB;


CREATE TABLE reserva_vuelo_clase (
	numero INT UNSIGNED NOT NULL,
	vuelo VARCHAR(30) NOT NULL,
	fecha_vuelo DATE NOT NULL,
	clase VARCHAR(40) NOT NULL,

	CONSTRAINT PK_reserva_vuelo_clase
	PRIMARY KEY (numero, vuelo, fecha_vuelo),
	
	CONSTRAINT FK_reserva_vuelo_clase_numero
	FOREIGN KEY (numero) REFERENCES reservas(numero),
	
	CONSTRAINT FK_reserva_vuelo_clase_vuelo
	FOREIGN KEY (vuelo, fecha_vuelo) REFERENCES instancias_vuelo(vuelo, fecha),
	
	CONSTRAINT FK_reserva_vuelo_clase_clase
	FOREIGN KEY (clase) REFERENCES clases(nombre)
	
) ENGINE=InnoDB;

CREATE TABLE asientos_reservados (
	vuelo VARCHAR(30) NOT NULL,
	fecha DATE NOT NULL,
	clase VARCHAR(40) NOT NULL,
	cantidad INT UNSIGNED NOT NULL,
	
	CONSTRAINT PK_asientos_reservados
	PRIMARY KEY (vuelo, fecha, clase),
	
	CONSTRAINT FK_asientos_reservados_vuelo
	FOREIGN KEY (vuelo, fecha) REFERENCES instancias_vuelo(vuelo, fecha),
	
	CONSTRAINT FK_asientos_reservados_clase
	FOREIGN KEY (clase) REFERENCES clases(nombre)

) ENGINE=InnoDB;


-- Creamos una vista 'vuelos_disponibles'.
CREATE VIEW vuelos_disponibles AS 
	SELECT 
		vuelo,
		dia,
		modelo_avion,
		fecha,
		hora_llega,
		hora_sale,
		TIME(TIMEDIFF(hora_llega, hora_sale) + IF(hora_llega < hora_sale, TIME('24:00:00'), TIME('00:00:00'))) AS duracion_vuelo,
		codigo_aeropuerto_llegada,
		nombre_aeropuerto_llegada,
		ciudad_aeropuerto_llegada,
		estado_aeropuerto_llegada,
		pais_aeropuerto_llegada,
		codigo_aeropuerto_salida,
		nombre_aeropuerto_salida,
		ciudad_aeropuerto_salida,
		estado_aeropuerto_salida,
		pais_aeropuerto_salida,
		precio AS precio_pasaje,
		asientos_disp,
		clase
	FROM
		instancias_vuelo NATURAL JOIN salidas
		NATURAL JOIN 
		(
			SELECT
				numero AS vuelo,
				aeropuerto_salida AS codigo_aeropuerto_salida,
				aeropuerto_llegada AS codigo_aeropuerto_llegada
			FROM
				vuelos_programados
		) AS vuelos_programados
		NATURAL JOIN 
		(
			SELECT
				codigo AS codigo_aeropuerto_llegada,
				nombre AS nombre_aeropuerto_llegada,
				ciudad AS ciudad_aeropuerto_llegada,
				estado AS estado_aeropuerto_llegada,
				pais AS pais_aeropuerto_llegada
			FROM 
				aeropuertos
		) AS aeropuertos_llegada
		NATURAL JOIN
		(
			SELECT
				codigo AS codigo_aeropuerto_salida,
				nombre AS nombre_aeropuerto_salida,
				ciudad AS ciudad_aeropuerto_salida,
				estado AS estado_aeropuerto_salida,
				pais AS pais_aeropuerto_salida
			FROM 
				aeropuertos
		) AS aeropuertos_salida
		NATURAL JOIN
		(
			SELECT -- No seleccionamos el precio aquí ya que no es un atributo utilizado en el gruop by.
				instancias_vuelo.vuelo AS vuelo,
				instancias_vuelo.dia AS dia,
				instancias_vuelo.fecha AS fecha,
				brinda.clase AS clase,
				TRUNCATE(cant_asientos + cant_asientos*porcentaje - IF(reserva_vuelo_clase.clase IS NULL, 0, COUNT(*)), 0) AS asientos_disp 
			FROM
				instancias_vuelo
				LEFT JOIN
				brinda
				ON
					instancias_vuelo.vuelo = brinda.vuelo AND
					instancias_vuelo.dia = brinda.dia
				LEFT JOIN
				clases
				ON
					brinda.clase = clases.nombre
				LEFT JOIN
				reserva_vuelo_clase
				ON
					instancias_vuelo.vuelo = reserva_vuelo_clase.vuelo AND
					instancias_vuelo.fecha = reserva_vuelo_clase.fecha_vuelo AND
					brinda.clase = reserva_vuelo_clase.clase
			GROUP BY instancias_vuelo.vuelo, instancias_vuelo.dia, instancias_vuelo.fecha, brinda.clase
		) AS asientos_disp_por_clase
		NATURAL JOIN -- Este últio NATURAL JOIN es para obtener el valor del precio.
		brinda
;

DELIMITER !

-- Asume que ya hay una transacción iniciada.
-- En caso de que el vuelo no exista o no brinde pasajes para la clase recibida, el estado será nulo.
DROP PROCEDURE IF EXISTS getEstadoReserva !
CREATE PROCEDURE getEstadoReserva(IN vuelo VARCHAR(30), IN fecha DATE, IN clase VARCHAR(40), OUT estado VARCHAR(30))
	BEGIN
		DECLARE asientosReservados, asientosBrinda, asientosTotales INT UNSIGNED;
		DECLARE porcentajeAux DECIMAL(2,2) UNSIGNED;
		
		-- Evaluamos que efectivamente exista un vuelo con los datos recibidos y que brinde la clase recibida por parámetro. 
		IF EXISTS (SELECT * FROM instancias_vuelo NATURAL JOIN salidas NATURAL JOIN brinda WHERE
					instancias_vuelo.vuelo = vuelo AND instancias_vuelo.fecha = fecha AND brinda.clase = clase) THEN
		
			SELECT cant_asientos INTO asientosBrinda FROM instancias_vuelo NATURAL JOIN salidas NATURAL JOIN brinda WHERE
				instancias_vuelo.fecha = fecha AND instancias_vuelo.vuelo = vuelo AND brinda.clase = clase LOCK IN SHARE MODE;
				
			SELECT cantidad INTO asientosReservados FROM asientos_reservados WHERE
				asientos_reservados.vuelo = vuelo AND asientos_reservados.fecha = fecha AND asientos_reservados.clase = clase FOR UPDATE;
			
			SELECT porcentaje INTO porcentajeAux FROM clases WHERE clases.nombre = clase LOCK IN SHARE MODE;
			
			/*
				Si asientosReservados es null se debe a que falta tal fila en asientos_reservados.
				Sin embargo, existe la instancia de vuelo correspondiente y brinda pasajes en la clase recibida.
				Por lo tanto, asumimos que nunca fue creada la fila en asientos_reservados y la cantidad correspondiente es 0.
				Por tal motivo procedemos a crear la fila faltante.
			*/
			
			IF asientosReservados IS NULL THEN
				INSERT INTO asientos_reservados VALUES
					(vuelo, fecha, clase, 0);
				SET asientosReservados = 0;
			END IF;
			
			IF asientosBrinda > asientosReservados THEN
				SET estado = "confirmada";
			ELSE
				SET asientosTotales = TRUNCATE(asientosBrinda + asientosBrinda*porcentajeAux, 0);
				
				IF asientosReservados < asientosTotales THEN 
					SET estado = "en espera";
				ELSE
					SELECT CONCAT('El vuelo ', vuelo, ' en la fecha ', fecha, ' no posee asientos disponibles para la clase ', clase, '.') AS descripcion, 'Error' AS resultado;
				END IF;
			END IF;
		ELSE
			SELECT CONCAT('El vuelo ', vuelo, ' en la fecha ', fecha, ' no existe o no brinda pasajes para la clase ', clase, '.') AS descripcion, 'Error' AS resultado;
		END IF;
	END; !


DROP PROCEDURE IF EXISTS reservarVueloIda !
CREATE PROCEDURE reservarVueloIda(IN tipoDoc_pasajero VARCHAR(20), IN nroDoc_pasajero INT UNSIGNED, IN legajo_empleado INT UNSIGNED, IN vuelo VARCHAR(30), IN fecha DATE, IN clase VARCHAR(40))
	BEGIN
		DECLARE estado VARCHAR(30);
		
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
			SELECT 'Se produjo un error inesperado.' AS descripcion, 'Error' AS resultado;
			ROLLBACK;
		END;
	
		START TRANSACTION;
			IF EXISTS (SELECT * FROM empleados WHERE legajo = legajo_empleado) AND
				EXISTS (SELECT * FROM pasajeros WHERE doc_tipo = tipoDoc_pasajero AND doc_nro = nroDoc_pasajero) THEN

					call getEstadoReserva(vuelo, fecha, clase, estado);
					
					IF estado IS NOT NULL THEN
						INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES
							(CURDATE(), DATE_SUB(fecha, INTERVAL 15 DAY), estado, tipoDoc_pasajero, nroDoc_pasajero, legajo_empleado);
							
						INSERT INTO reserva_vuelo_clase VALUES
							(LAST_INSERT_ID(), vuelo, fecha, clase);
						
						
						UPDATE asientos_reservados SET cantidad = cantidad+1 WHERE
							asientos_reservados.vuelo = vuelo AND asientos_reservados.fecha = fecha AND asientos_reservados.clase = clase;
					
						SELECT CONCAT('Se ha realizado la reserva con estado : ', estado) AS descripcion, 'Éxito' AS resultado;
					END IF;
					
			ELSE
				SET estado = NULL;
				SELECT 'No existe el pasajero o empleado correspondiente a los datos recibidos.' AS descripcion, 'Error' AS resultado;
			END IF;
		COMMIT;
	END; !


DROP PROCEDURE IF EXISTS reservarVueloIdaVuelta !
CREATE PROCEDURE reservarVueloIdaVuelta(IN tipoDoc_pasajero VARCHAR(20), IN nroDoc_pasajero INT UNSIGNED, IN legajo_empleado INT UNSIGNED, IN vueloIda VARCHAR(30), IN fechaIda DATE, IN claseIda VARCHAR(40), IN vueloVuelta VARCHAR(30), IN fechaVuelta DATE, IN claseVuelta VARCHAR(40))
	BEGIN
		DECLARE estadoIda, estadoVuelta, estado VARCHAR(30);
		
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
			SELECT 'Se produjo un error inesperado.' AS descripcion, 'Error' AS resultado;
			ROLLBACK;
		END;
	
		START TRANSACTION;
			IF EXISTS (SELECT * FROM empleados WHERE legajo = legajo_empleado) AND
				EXISTS (SELECT * FROM pasajeros WHERE doc_tipo = tipoDoc_pasajero AND doc_nro = nroDoc_pasajero) THEN

					call getEstadoReserva(vueloIda, fechaIda, claseIda, estadoIda);
					call getEstadoReserva(vueloVuelta, fechaVuelta, claseVuelta, estadoVuelta);
					
					IF estadoIda IS NOT NULL AND estadoVuelta IS NOT NULL THEN
					
						IF STRCMP(estadoIda, "confirmada") = 0 AND STRCMP(estadoVuelta, "confirmada") = 0 THEN
							SET estado = "confirmada";
						ELSE
							SET estado = "en espera";
						END IF;
						
						INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES
							(CURDATE(), DATE_SUB(fechaIda, INTERVAL 15 DAY), estado, tipoDoc_pasajero, nroDoc_pasajero, legajo_empleado);
							
						INSERT INTO reserva_vuelo_clase VALUES
							(LAST_INSERT_ID(), vueloIda, fechaIda, claseIda);
							
						INSERT INTO reserva_vuelo_clase VALUES
							(LAST_INSERT_ID(), vueloVuelta, fechaVuelta, claseVuelta);
						
						
						UPDATE asientos_reservados SET cantidad = cantidad+1 WHERE
							vuelo = vueloIda AND fecha = fechaIda AND clase = claseIda;
						
						UPDATE asientos_reservados SET cantidad = cantidad+1 WHERE
							vueloVuelta = vuelo AND fecha = fechaVuelta AND clase = claseVuelta;
					
						SELECT CONCAT('Se ha realizado la reserva con estado : ', estado) AS descripcion, 'Éxito' AS resultado;
					END IF;
					
			ELSE
				SELECT 'No existe el pasajero o empleado correspondiente a los datos recibidos.' AS descripcion, 'Error' AS resultado;
			END IF;
		COMMIT;
	END; !

DELIMITER ;

-- Creamos el usuario 'admin'.
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

-- Le otorgamos privilegios.
GRANT ALL PRIVILEGES ON vuelos.* TO 'admin'@'localhost' WITH GRANT OPTION;
-- GRANT EXECUTE ON PROCEDURE vuelos.* to 'admin'@'localhost';

-- Creamos el usuario 'empleado'
CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';

-- Le otorgamos privilegios de lectura sobre toda la base de datos a 'empleado'.
GRANT SELECT ON vuelos.* TO 'empleado'@'%';

-- Le otorgamos privilegios de escritura, modificación y eliminación de tuplas en las
-- tablas 'reservas', 'pasajeros' y 'reserva_vuelo_clase' al usuario 'empleado'.
GRANT INSERT, DELETE, UPDATE ON vuelos.reservas TO 'empleado'@'%';
GRANT INSERT, DELETE, UPDATE ON vuelos.pasajeros TO 'empleado'@'%';
GRANT INSERT, DELETE, UPDATE ON vuelos.reserva_vuelo_clase TO 'empleado'@'%';
GRANT EXECUTE ON PROCEDURE vuelos.reservarVueloIda TO 'empleado'@'%';
GRANT EXECUTE ON PROCEDURE vuelos.reservarVueloIdaVuelta TO 'empleado'@'%';

-- Creeamos el usuario 'cliente'.
CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente';

-- Le otorgamos privilegios de lectura sobre la vista 'vuelos_disponibles'.
GRANT SELECT ON vuelos.vuelos_disponibles TO 'cliente'@'%';


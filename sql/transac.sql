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
	
DELIMITER ;
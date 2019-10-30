DELIMITER !

DROP TRIGGER IF EXISTS crear_instancias_vuelo !
CREATE TRIGGER crear_instancias_vuelo
AFTER INSERT ON salidas
FOR EACH ROW
	BEGIN
		DECLARE fechaFinal, fechaInicial DATE;
		DECLARE numeroDia_salida, numeroDia_actual TINYINT UNSIGNED;
		
		SET numeroDia_actual = DAYOFWEEK(CURDATE());
		SET numeroDia_salida = NEW.dia+0;
		SET fechaInicial = CURDATE();
		SET fechaFinal = DATE_ADD(CURDATE(), INTERVAL 1 YEAR);
		
		IF numeroDia_actual > numeroDia_salida THEN
			SET fechaInicial = DATE_ADD(CURDATE(), INTERVAL ((7-numeroDia_actual) + numeroDia_salida) DAY);
		ELSE
			IF numeroDia_actual < numeroDia_salida THEN
				SET fechaInicial = DATE_ADD(CURDATE(), INTERVAL (numeroDia_salida - numeroDia_actual) DAY);
			END IF;
		END IF;
		
		
		WHILE fechaInicial < fechaFinal DO
			INSERT INTO instancias_vuelo VALUES (NEW.vuelo, fechaInicial, NEW.DIA, 'a tiempo');
			SET fechaInicial = DATE_ADD(fechaInicial, INTERVAL 7 DAY);
		END WHILE;
		
	END; !

DELIMITER ;
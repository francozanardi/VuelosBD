
INSERT INTO ubicaciones VALUES ('Argentina', 'Buenos Aires', 'Bahia Blanca', -3);
INSERT INTO ubicaciones VALUES ('Argentina', 'Rio Negro', 'Viedma', -3);
INSERT INTO ubicaciones VALUES ('Argentina', 'Tierra del fuego', 'Ushuaia', -3);

INSERT INTO aeropuertos VALUES ('a0', 'Aeropuerto Bahia', '123456789', 'San Martin 123', 'Argentina', 'Buenos Aires', 'Bahia Blanca');
INSERT INTO aeropuertos VALUES ('a1', 'Aeropuerto Viedma', '987654321', 'Sarmiento 123', 'Argentina', 'Rio Negro', 'Viedma');
INSERT INTO aeropuertos VALUES ('a2', 'Aeropuerto Ushuaia', '135791113', 'Roca 321', 'Argentina', 'Tierra del fuego', 'Ushuaia');

INSERT INTO modelos_avion VALUES ('m0', 'f0', 3, 300);
INSERT INTO modelos_avion VALUES ('m1', 'f1', 3, 400);
INSERT INTO modelos_avion VALUES ('m2', 'f2', 3, 500);

INSERT INTO vuelos_programados VALUES ('v0', 'a0', 'a1');
INSERT INTO vuelos_programados VALUES ('v1', 'a1', 'a2');
INSERT INTO vuelos_programados VALUES ('v2', 'a2', 'a0');
INSERT INTO vuelos_programados VALUES ('v3', 'a0', 'a1');
INSERT INTO vuelos_programados VALUES ('v4', 'a1', 'a0');

INSERT INTO salidas VALUES ('v0', 'do', '00:00:00', '05:00:00', 'm0');
INSERT INTO salidas VALUES ('v0', 'ju', '00:00:00', '05:00:00', 'm0');
INSERT INTO salidas VALUES ('v1', 'lu', '00:00:00', '07:00:00', 'm1');
INSERT INTO salidas VALUES ('v1', 'vi', '00:00:00', '07:00:00', 'm1');
INSERT INTO salidas VALUES ('v2', 'ma', '00:00:00', '11:00:00', 'm2');
INSERT INTO salidas VALUES ('v2', 'sa', '00:00:00', '11:00:00', 'm0');
INSERT INTO salidas VALUES ('v3', 'do', '00:00:00', '09:00:00', 'm1');
INSERT INTO salidas VALUES ('v4', 'sa', '00:00:00', '09:00:00', 'm1');

INSERT INTO clases VALUES ('Turista', 0.30);
INSERT INTO clases VALUES ('Economica', 0.10);
INSERT INTO clases VALUES ('Empresarial', 0.70);

INSERT INTO comodidades(codigo, descripcion) VALUES (0, 'Television');
INSERT INTO comodidades(codigo, descripcion) VALUES (1, 'Bano');
INSERT INTO comodidades(codigo, descripcion) VALUES (2, 'Cafetera');

INSERT INTO pasajeros VALUES ('dni', 30120120, 'Gomez', 'Mauricio', 'Colon 123', '123321123', 'Argentino');
INSERT INTO pasajeros VALUES ('dni', 40120120, 'Rodriguez', 'Pepe', 'Rivadavia 123', '123456123', 'Argentino');
INSERT INTO pasajeros VALUES ('dni', 50120120, 'Perez', 'Roberto', 'Muster 123', '987654123', 'Argentino');

INSERT INTO empleados VALUES (1230, '0FA10FA10FA10FA10FA10FA10FA10FA1', 'dni', 20123123, 'Fernandez', 'Faustino', 'Calle 123', '123123123');
INSERT INTO empleados VALUES (1231, '0FA10FA10FA10FA10FA10FA10FA10FA1', 'dni', 20321321, 'Martinez', 'Martin', 'Calle 321', '321321321');
INSERT INTO empleados VALUES (1232, md5('1234'), 'dni', 20321322, 'Gutierrez', 'Gerardo', 'Calle 234', '23423423');

INSERT INTO brinda VALUES ('v0', 'do', 'Turista', 12323.00, 100);
INSERT INTO brinda VALUES ('v0', 'do', 'Economica', 4300.00, 100);
INSERT INTO brinda VALUES ('v0', 'do', 'Empresarial', 50900.00, 100);
INSERT INTO brinda VALUES ('v1', 'lu', 'Turista', 12000.00, 200);
INSERT INTO brinda VALUES ('v1', 'lu', 'Economica', 5000.00, 200);
INSERT INTO brinda VALUES ('v2', 'ma', 'Turista', 15000.00, 500);
INSERT INTO brinda VALUES ('v0', 'ju', 'Economica', 3000.00, 300);
INSERT INTO brinda VALUES ('v1', 'vi', 'Turista', 7000.00, 200);
INSERT INTO brinda VALUES ('v1', 'vi', 'Economica', 5000.00, 200);
INSERT INTO brinda VALUES ('v2', 'sa', 'Empresarial', 85000.00, 300);
INSERT INTO brinda VALUES ('v3', 'do', 'Empresarial', 85000.00, 300);
INSERT INTO brinda VALUES ('v4', 'sa', 'Turista', 3500.00, 200);
INSERT INTO brinda VALUES ('v4', 'sa', 'Economica', 2500.00, 100);

INSERT INTO posee VALUES ('Economica', 1);
INSERT INTO posee VALUES ('Turista', 1);
INSERT INTO posee VALUES ('Turista', 0);
INSERT INTO posee VALUES ('Empresarial', 0);
INSERT INTO posee VALUES ('Empresarial', 1);
INSERT INTO posee VALUES ('Empresarial', 2);

-- INSERT INTO instancias_vuelo VALUES ('v0', '2019/09/15', 'do', NULL);
-- INSERT INTO instancias_vuelo VALUES ('v1', '2019/09/16', 'lu', NULL);
-- INSERT INTO instancias_vuelo VALUES ('v2', '2019/09/17', 'ma', NULL);
-- INSERT INTO instancias_vuelo VALUES ('v0', '2019/09/19', 'ju', NULL);
-- INSERT INTO instancias_vuelo VALUES ('v3', '2019/09/15', 'do', NULL);
-- INSERT INTO instancias_vuelo VALUES ('v4', '2019/09/21', 'sa', NULL);

-- INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES ('2019/07/10', '2019/09/10', 'PAGADA', 'dni', 30120120, 1230);
-- INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES ('2019/07/12', '2019/09/12', 'PAGADA', 'dni', 30120120, 1231);
-- INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES ('2019/07/14', '2019/09/14', 'PAGADA', 'dni', 40120120, 1230);
-- INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES ('2019/07/16', '2019/09/16', 'PAGADA', 'dni', 40120120, 1231);
-- INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES ('2019/07/18', '2019/09/18', 'PAGADA', 'dni', 50120120, 1230);
-- INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES ('2019/07/20', '2019/09/20', 'PAGADA', 'dni', 50120120, 1231);


-- INSERT INTO reserva_vuelo_clase VALUES (1, 'v0', '2019/09/15', 'Empresarial');
-- INSERT INTO reserva_vuelo_clase VALUES (2, 'v0', '2019/09/15', 'Empresarial');
-- INSERT INTO reserva_vuelo_clase VALUES (3, 'v1', '2019/09/16', 'Economica');
-- INSERT INTO reserva_vuelo_clase VALUES (4, 'v1', '2019/09/16', 'Turista');
-- INSERT INTO reserva_vuelo_clase VALUES (5, 'v2', '2019/09/17', 'Turista');
-- INSERT INTO reserva_vuelo_clase VALUES (6, 'v2', '2019/09/17', 'Turista');


-- INSERT INTO asientos_reservados VALUES ('v0', '2019/09/15', 'Empresarial', 2);
-- INSERT INTO asientos_reservados VALUES ('v1', '2019/09/16', 'Economica', 1);
-- INSERT INTO asientos_reservados VALUES ('v1', '2019/09/16', 'Turista', 1);
-- INSERT INTO asientos_reservados VALUES ('v2', '2019/09/17', 'Turista', 2);


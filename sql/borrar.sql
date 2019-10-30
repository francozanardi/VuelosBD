drop database vuelos;

drop user 'admin'@'localhost';
drop user 'empleado'@'%';
drop user 'cliente'@'%';

drop procedure IF EXISTS reservarVueloIdaVuelta;
drop procedure IF EXISTS reservarVueloIda;
drop procedure IF EXISTS reservarVueloIda_;
drop procedure IF EXISTS getEstadoReserva;

flush privileges;

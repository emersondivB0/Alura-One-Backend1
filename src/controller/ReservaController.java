package controller;

import java.sql.SQLException;
import java.util.List;

import dao.ReservaDao;
import factory.ConnectionFactory;
import modelo.Reserva;

public class ReservaController {
	
private ReservaDao reservaDao;
	
	public ReservaController() throws SQLException {
		this.reservaDao = new ReservaDao(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Reserva reserva) {
		reservaDao.guardar(reserva);
	}
	
	public List<Reserva> listar(String campo) throws SQLException {
		return reservaDao.listarReservas(campo);
	}
	
	public int modificar(String fecha_entrada, String fecha_salida, Double valor, String forma_pago, Long id){
		return reservaDao.modificar(fecha_entrada, fecha_salida, valor, forma_pago, id);	
	}
	
	public int eliminar(Integer id) {		
		return reservaDao.eliminar(id);			
	}

}

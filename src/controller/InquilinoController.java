package controller;

import java.sql.SQLException;
import java.util.List;

import dao.InquilinoDao;
import factory.ConnectionFactory;
import modelo.Inquilino;

public class InquilinoController {

	private InquilinoDao inquilinoDAO;
	
	public InquilinoController() throws SQLException {
		this.inquilinoDAO = new InquilinoDao(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Inquilino inquilino) {
		inquilinoDAO.guardar(inquilino);
	}
	
	public List<Inquilino> listar(String campo) throws SQLException {
		return inquilinoDAO.listarHuespedes(campo);
	}

	public int modificar(String nombre, String apellido, String fecha_nacimiento, String nacionalidad, String telefono, Long id){
		return inquilinoDAO.modificar(nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id);	
	}
	
	public int eliminar(Integer id) {		
		return inquilinoDAO.eliminar(id);			
	}
	
	public int eliminarPorIDReserva(Integer id_reserva) {		
		return inquilinoDAO.eliminarPorIDReserva(id_reserva);			
	}
}
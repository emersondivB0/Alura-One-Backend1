package controller;

import java.sql.SQLException;

import dao.UsuarioDAO;
import factory.ConnectionFactory;
import modelo.Usuario;

public class LoginController {
	private UsuarioDAO usuarioDAO;
	
	public LoginController() throws SQLException
	{
		this.usuarioDAO = new UsuarioDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public boolean IniciarSesion(Usuario usuario) {
		return usuarioDAO.iniciarSesion(usuario);
	}
	

}
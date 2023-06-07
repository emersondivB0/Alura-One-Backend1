package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Usuario;

public class UsuarioDAO {
	private final Connection connection;
	private String sql_select = "SELECT usuario, clave FROM login WHERE (usuario=?) AND (clave=?)";
	
	public UsuarioDAO(Connection connection) { this.connection = connection; }
	private boolean isLogin = false;
	
	public boolean iniciarSesion(Usuario usuario) {
		try{
			var statement = connection.prepareStatement(sql_select);
			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getPassword());
			
			final ResultSet resultSet = statement.executeQuery();
			try(resultSet){
				while(resultSet.next()) {
					String usuarioFound = resultSet.getString("usuario");
					String claveFound = resultSet.getString("clave");
					if(usuarioFound.equals(usuario.getUsuario()) && claveFound.equals(usuario.getPassword())) {
						System.out.println("Usuario encontrado: "+ usuarioFound + "\n"+claveFound);
						isLogin = true;
					}
				}
			}
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
		return isLogin;
	}

}
package modelo;


public class Usuario {
	private int id;
	private String usuario;
	private String clave;
	
	
	
	public Usuario(String usuario, String clave) {

		this.usuario = usuario;
		this.clave = clave;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return clave;
	}


	public void setPassword(String clave) {
		this.clave = clave;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	@Override
	public String toString() {
		return String.format("Id: %d, Usuario: %s", id, usuario);
	}
	

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Inquilino;

public class InquilinoDao {

	final private Connection con;

	public InquilinoDao(Connection con) {
		this.con = con;
	}

	public void guardar(Inquilino inquilino) {

		try {
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO huespedes(reserva_id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono)"
							+ " VALUES(?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {

				ejecutarReserva(inquilino, statement);

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void ejecutarReserva(Inquilino inquilino, PreparedStatement statement) throws SQLException {

		statement.setLong(1, inquilino.getReservaId());
		statement.setString(2, inquilino.getNombre());
		statement.setString(3, inquilino.getApellido());
		statement.setString(4, inquilino.getFechaNacimiento());
		statement.setString(5, inquilino.getNacionalidad());
		statement.setString(6, inquilino.getTelefono());

		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();

		try (resultSet) {
			while (resultSet.next()) {
				inquilino.setId(resultSet.getLong(1));
				// System.out.println(String.format("Fue insertado el huesped %s", huesped));
			}
		}

	}

	public List<Inquilino> listarHuespedes(String campo) throws SQLException {

		List<Inquilino> resultado = new ArrayList<>();

		ConnectionFactory factory = new ConnectionFactory();

		final Connection con = factory.recuperaConexion();

		try (con) {

			var querySelect = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, reserva_id FROM huespedes ";

			if (!campo.isEmpty()) {
				querySelect += "WHERE nombre LIKE ? ";
			}

			querySelect += "ORDER BY id ASC; ";

			// System.out.println(querySelect);

			final PreparedStatement statement = con.prepareStatement(querySelect);

			try (statement) {

				if (!campo.isEmpty()) {
					statement.setString(1, "%" + campo + "%");
				}

				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				try (resultSet) {
					while (resultSet.next()) {
						Inquilino fila = new Inquilino(resultSet.getLong("id"), resultSet.getString("nombre"),
								resultSet.getString("apellido"), resultSet.getString("fecha_nacimiento"),
								resultSet.getString("nacionalidad"), resultSet.getString("telefono"),
								resultSet.getLong("reserva_id"));

						resultado.add(fila);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return resultado;
	}

	public int modificar(String nombre, String apellido, String fecha_nacimiento, String nacionalidad, String telefono,
			Long id) {

		try {

			final PreparedStatement statement = con
					.prepareStatement("UPDATE huespedes SET " + "nombre = ?, " + "apellido = ?, "
							+ "fecha_nacimiento = ?, " + "nacionalidad = ?, " + "telefono = ? " + "WHERE id = ?");

			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setString(3, fecha_nacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setLong(6, id);

				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {

		try {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id = ?");

			try (statement) {
				statement.setInt(1, id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int eliminarPorIDReserva(Integer reserva_id) {

		try {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id_reserva = ?");

			try (statement) {
				statement.setInt(1, reserva_id);
				statement.execute();

				int updateCount = statement.getUpdateCount();

				return updateCount;

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}

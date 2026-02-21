package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Model.Alumno;
import Model.Grupo;
import pool.MyDataSource;

public class DaoSqlimpl implements Dao {

	private static final Logger log = LoggerFactory.getLogger(DaoSqlimpl.class); // pruebo a hacer un log para todo sql

	public DaoSqlimpl() {
	}

	@Override
	public int guardarAlumnoBd(Alumno al) {
		// Ajustado: fecha_nacimiento e id_grupo
		String sql = "INSERT INTO alumnos(nia, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql)) {

			pstm.setInt(1, al.getNia());
			pstm.setString(2, al.getNombre());
			pstm.setString(3, al.getApellidos());
			pstm.setString(4, String.valueOf(al.getGenero()));
			pstm.setDate(5, Date.valueOf(al.getFecha()));
			pstm.setString(6, al.getCiclo());
			pstm.setString(7, al.getCurso());

			if (al.getGrupo() != null) {
				pstm.setInt(8, al.getGrupo().getId());
			} else {
				pstm.setNull(8, java.sql.Types.INTEGER);
			}

			return pstm.executeUpdate();
		} catch (SQLException e) {
			log.error("Error al insertar alumno con NIA={}", al.getNia(), e);
			return -1;
		}
	}

	@Override
	public int guardarGrupo(Grupo g) {
		int result = -1;
		String sql = "INSERT INTO grupos VALUES(?, ?)";
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, g.getId());
			ps.setString(2, g.getNombre());
			result = ps.executeUpdate();
			log.info("Alumno con NIA={} insertado correctamente", g.getId());
			return result;
		} catch (SQLException e) {
			log.error("Error al insertar grupo con Id={}", g.getId(), e);
			return -1;
		}
	}

	@Override
	public int eliminarAlumnoPorApellido(String apellido) {
		String sql = "Delete from alumnos where Apellidos Like ?";
		int result = -1;
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, "%" + apellido + "%");

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Error al borrar alumno con apellidos{}", apellido, e);
		}
		return result;
	}

	@Override
	public int eliminarAlumnoPorNia(int nia) {
		String sql = "Delete from alumnos where Nia=?";
		int result = -1;
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement ps = conexion.prepareStatement(sql)) {
			ps.setInt(1, nia);
			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Error al borrar alumno con Nia{}", nia, e);
		}
		return result;
	}

	@Override
	public int modificarNombreAPartirDeNiaBD(String nombre, int nia) {
		String sql = "Update alumnos set Nombre=? where Nia=?";
		int result = -1;
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement ps = conexion.prepareStatement(sql);) {

			ps.setString(1, nombre);
			ps.setInt(2, nia);

			result = ps.executeUpdate();

		} catch (Exception e) {
			log.error("Error al modificar nombre de alumno con Nia{}", nia, e);
		}
		return result;
	}

	@Override
	public List<Alumno> mostrarAlumnos() {
		List<Alumno> listaAlumnos = new ArrayList<>();
		// Ajustado: a.id_grupo y g.id
		String sql = "SELECT a.*, g.nombre AS nombre_grupo FROM alumnos a " + "LEFT JOIN grupos g ON a.id_grupo = g.id";

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			while (rs.next()) {
				Alumno al = new Alumno(rs.getInt("nia"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("genero").charAt(0), rs.getDate("fecha_nacimiento").toLocalDate(), // Nombre exacto
																										// de la columna
						rs.getString("ciclo"), rs.getString("curso"));

				int idGrupo = rs.getInt("id_grupo");
				if (!rs.wasNull()) {
					Grupo grp = new Grupo(idGrupo, rs.getString("nombre_grupo"));
					al.setGrupo(grp);
				}
				listaAlumnos.add(al);
			}
		} catch (SQLException e) {
			log.error("Error al recuperar la lista de alumnos", e);
		}
		return listaAlumnos;
	}

	public Map<Integer, String> muestroNiayNombres() {
		Map<Integer, String> listaAlumnos = new HashMap<>();

		String sql = "SELECT nia, nombre from alumnos";

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			while (rs.next()) {

				listaAlumnos.put(rs.getInt("nia"), rs.getString("nombre"));
			}

		} catch (SQLException e) {
			log.error("Error al recuperar la lista de alumnos", e);
		}
		return listaAlumnos; // Esta es mi alternativa, no sé si hay alguna mejor
	}

	@Override
	public void insertarListaGrupo(List<Grupo> muestrolista) {
		for (Grupo g : muestrolista) {
			guardarGrupo(g); // Realmente es una funcion para poder hacer el Json pero como solo se puede una
								// interfaz de Dao pues eso
		}

	}

	public List<Grupo> obtenerGruposConAlumnos() {
		Map<Integer, Grupo> mapaGrupos = new HashMap<>();

		String sql = "SELECT g.id AS grupo_id, g.nombre AS grupo_nombre, a.Nia, a.Nombre, a.Apellidos, a.Genero, a.FechaNacimiento, a.Ciclo, a.Curso "
				+ "FROM grupos g LEFT JOIN alumnos a ON g.id = a.id_grupo ORDER BY g.id";

		try (Connection con = MyDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int idGrupo = rs.getInt("grupo_id");

				// Si el grupo aún no está en el mapa, lo creamos
				Grupo grupo = mapaGrupos.get(idGrupo);
				if (grupo == null) {
					grupo = new Grupo(idGrupo, rs.getString("grupo_nombre"));
					mapaGrupos.put(idGrupo, grupo);
				}

				// Si hay alumno (puede ser null por el LEFT JOIN)
				int nia = rs.getInt("Nia");
				if (!rs.wasNull()) {
					Alumno al = new Alumno(nia, rs.getString("Nombre"), rs.getString("Apellidos"),
							rs.getString("Genero").charAt(0), rs.getDate("FechaNacimiento").toLocalDate(),
							rs.getString("Ciclo"), rs.getString("Curso"));

					grupo.getAlumnos().add(al);
				}
			}

		} catch (SQLException e) {
			log.error("Error obteniendo grupos con alumnos", e);
		}

		return new ArrayList<>(mapaGrupos.values());
	}

	@Override
	public int añadirAlumnoAGrupoPorNia(int nia, int id) {
		String sql = "UPDATE alumnos SET id_grupo = ? WHERE Nia = ?"; // como un alumno solo puede tener un grupo he
																		// cambiado la consulta también, claro
		int resultado = -1;
		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement ps = conexion.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.setInt(2, nia);
			log.info("Alumno con NIA={} insertado correctamente en el grupo con Id={}", nia, id);
			resultado = ps.executeUpdate();
			return resultado;

		} catch (SQLException e) {
			log.error("Error al insertar alumno con NIA={} en grupo con Id={}", nia, id);
		}
		return resultado;
	}

	@Override
	public List<Alumno> mostrarAlumnosPorGrupo(int num) {

		List<Alumno> listaAlumnos = new ArrayList<>();

		String sql = "SELECT a.*, g.nombre AS nombre_grupo " + "FROM alumnos a "
				+ "INNER JOIN grupos g ON a.id_grupo = g.id " + "WHERE a.id_grupo = ?";

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);) {
			pstm.setInt(1, num);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					Alumno al = new Alumno(rs.getInt("nia"), rs.getString("nombre"), rs.getString("apellidos"),
							rs.getString("genero").charAt(0), rs.getDate("fecha_nacimiento").toLocalDate(), // Nombre
																											// exacto
																											// de la
																											// columna
							rs.getString("ciclo"), rs.getString("curso"));

					int idGrupo = rs.getInt("id_grupo");
					if (!rs.wasNull()) {
						Grupo grp = new Grupo(idGrupo, rs.getString("nombre_grupo"));
						al.setGrupo(grp);
					}
					listaAlumnos.add(al);
				}
			} catch (SQLException e) {
				log.error("Error al recuperar la lista de alumnos", e);
				return null;
			}
			return listaAlumnos;
		} catch (SQLException e1) {
			log.error("Error al recuperar la lista de alumnos", e1);
			return null;
		}

	}

	@Override
	public List<Alumno> mostrarUnAlumnoNia(int num) {

		List<Alumno> listaAlumnos = new ArrayList<>(); // aunque solo sea un alumno lo meto en una lista y reutilizo
														// codigo en la interfaz

		String sql = "SELECT a.*, g.nombre AS nombre_grupo FROM alumnos a "
				+ "LEFT JOIN grupos g ON a.id_grupo = g.id WHERE a.nia = ?";

		try (Connection conexion = MyDataSource.getConnection();
				PreparedStatement pstm = conexion.prepareStatement(sql);) {
			pstm.setInt(1, num);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					Alumno al = new Alumno(rs.getInt("nia"), rs.getString("nombre"), rs.getString("apellidos"),
							rs.getString("genero").charAt(0), rs.getDate("fecha_nacimiento").toLocalDate(),
							rs.getString("ciclo"), rs.getString("curso"));

					int idGrupo = rs.getInt("id_grupo");
					if (!rs.wasNull()) {
						Grupo grp = new Grupo(idGrupo, rs.getString("nombre_grupo"));
						al.setGrupo(grp);
					}
					listaAlumnos.add(al);
				}
			} catch (SQLException e) {
				log.error("Error al recuperar la lista de alumnos", e);
				return null;
			}
			return listaAlumnos;
		} catch (SQLException e1) {
			log.error("Error al recuperar la lista de alumnos", e1);
			return null;
		}

	}

}

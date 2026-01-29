package Dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Main.Alumno;
import Main.Grupo;

public class DaoJsonimpl implements Dao {

	private static DaoJsonimpl instance;

	static {
		instance = new DaoJsonimpl();
	}

	private DaoJsonimpl() {
	};

	public static DaoJsonimpl getInstance() {
		return instance;
	}

	@Override
	public int insertoAlumno(Alumno a) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int insertoGrupo(Grupo g) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int eliminoAlumnoPorApellido(String apellido) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int eliminoAlumnoPorNia(int nia) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int ModificoNombrePorNia(String nombre, int nia) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public List<Alumno> muestroAlumnos() {
		List<Alumno> listaAlumnos = new ArrayList<>();

		try (FileReader reader = new FileReader("GruposAlumnos.json")) {

			Gson gson = new Gson();

			// Leemos lista de grupos (cada grupo es un Map)
			List<Map<String, Object>> grupos = gson.fromJson(reader, List.class); //convierte el mapa en lineas genericas

			for (Map<String, Object> grupoMap : grupos) { //cada linea de grupos

				Double idGrupoDouble = (Double) grupoMap.get("id"); //me dice que tengo que tener un Double
				int idGrupo = idGrupoDouble.intValue(); //y luego lo pasoa  int
				String nombreGrupo = (String) grupoMap.get("nombre_grupo");

				Grupo grupo = new Grupo(idGrupo, nombreGrupo); //me creo el grupo con los datos del json

				List<Map<String, Object>> alumnos = (List<Map<String, Object>>) grupoMap.get("alumnos");  // hace el mapa de lso alumnos dentro de los grupos

				for (Map<String, Object> alumnoMap : alumnos) {

					int nia = ((Double) alumnoMap.get("nia")).intValue();
					String nombre = (String) alumnoMap.get("nombre");
					String apellidos = (String) alumnoMap.get("apellidos");
					char genero = ((String) alumnoMap.get("genero")).charAt(0);
					String fecha = (String) alumnoMap.get("fecha_nacimiento");

					Alumno al = new Alumno(nia, nombre, apellidos, genero, java.time.LocalDate.parse(fecha), "", "" );

					al.agregarGrupo(grupo);
					grupo.getAlumnos().add(al);
					listaAlumnos.add(al);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaAlumnos;
	}

	@Override
	public void InsertoListaGrupos(List<Grupo> muestrolista) {
		List<Map<String, Object>> gruposParaJson = new ArrayList<>();

		for (Grupo g : muestrolista) { // por cada grupo me haces un Mapa
			Map<String, Object> mapaGrupo = new HashMap<>();
			mapaGrupo.put("id", g.getId());
			mapaGrupo.put("nombre_grupo", g.getNombre());

			// Ahora creamos la lista de alumnos para ESTE grupo
			List<Map<String, Object>> alumnosLista = new ArrayList<>();
			for (Alumno a : g.getAlumnos()) {
				Map<String, Object> mapaAlumno = new HashMap<>();
				mapaAlumno.put("nia", a.getNia());
				mapaAlumno.put("nombre", a.getNombre());
				mapaAlumno.put("apellidos", a.getApellidos());
				mapaAlumno.put("genero", String.valueOf(a.getGenero()));
				mapaAlumno.put("fecha_nacimiento", a.getFecha().toString());
				alumnosLista.add(mapaAlumno); // creo la lista demapas donde le meto el mapa de alumno
			}

			// Metemos la lista de alumnos dentro del mapa del grupo
			mapaGrupo.put("alumnos", alumnosLista); // lo meto en el otro mapa como otro atributo más
			gruposParaJson.add(mapaGrupo); // y ya lo añado al ultimo array de mapas
		}

		// Finalmente guardamos la lista de grupos completa
		try (FileWriter fw = new FileWriter("GruposAlumnos.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(gruposParaJson, fw);
		} catch (IOException e) {
			// Aquí usaría el Logger de Log4j
		}
	}

	@Override
	public int añadoAlumnoAGrupo(int nia, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}

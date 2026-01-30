package Grafica;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import Dao.Dao;
import Dao.DaoBinarioimpl;
import Dao.DaoJsonimpl;
import Dao.DaoSqlimpl;
import Main.Alumno;
import Main.Grupo;

public class VistaControlador implements InterfazGrafica {

	
	private Scanner entrada = new Scanner(System.in);
	
	public VistaControlador() {}
	
	public int mostrarMenu() { //se llama todo desde el controlador do while en el controlador
		System.out.println("\n===== GESTIÓN DE ALUMNOS Y GRUPOS =====");
		System.out.println("1. Insertar alumno");
		System.out.println("2. Insertar grupo");
		System.out.println("3. Añadir alumno a grupo (Asignar)");
		System.out.println("4. Mostrar todos los alumnos con su grupo");
		System.out.println("5. Guardar alumnos en fichero (Binario)");
		System.out.println("6. Leer alumnos desde fichero y guardar en BD");
		System.out.println("7. Modificar nombre de alumno por ID");
		System.out.println("8. Eliminar alumno por ID");
		System.out.println("9. Eliminar alumnos por apellido"); //este era por curso
		System.out.println("10. Exportar grupos a JSON");
		System.out.println("11. Importar grupos desde JSON  a BD");
		System.out.println("0. Salir");
		System.out.print("Seleccione una opción: ");
		
		
		if (entrada.hasNextInt()) {
            int op = entrada.nextInt();
            entrada.nextLine();
            return op;
        }
		entrada.nextLine();
        return -1;
	}
	
	
	public void añadoAlumnoAGrupo() {
		System.out.println("Dime el nia de alumno");
		int nia = entrada.nextInt();
		System.out.println("dime el id del grupo");
		int id = entrada.nextInt();
		
		int resultado = daoSql.añadoAlumnoAGrupo(nia, id);
		if (resultado >= 1) 
		{
			System.out.println("resultado con exito");
		}
		else 
		{
			System.out.println("revisar log");
		}
		}

		public Alumno insertoAlumno() {
			System.out.println("--- Inserción de Alumno ---");
			System.out.print("NIA: ");
			int nia = entrada.nextInt();
			entrada.nextLine();

			System.out.print("Nombre: ");
			String nombre = entrada.nextLine();

			System.out.print("Apellidos: ");
			String apellidos = entrada.nextLine();

			System.out.print("Fecha nacimiento (yyyy-mm-dd): ");
			LocalDate fechaNacimiento = LocalDate.parse(entrada.nextLine());

			System.out.print("Género (M/F/N): ");
			char genero = entrada.next().toUpperCase().charAt(0);
			entrada.nextLine();

			System.out.print("Ciclo: ");
			String ciclo = entrada.nextLine();

			System.out.print("Curso: ");
			String curso = entrada.nextLine();

			Alumno a = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso);
			return a;
		}
		
		public void mostrarMensaje(String msg) {
	        System.out.println(msg);
	    }

	public void MuestroAlumnoPorNia() 
	{
		EnsenioNiayNombreAlumno();
		
		
	}
	
	private void EnsenioNiayNombreAlumno() 
	{
		System.out.println(daoSql.muestroNiayNombres());
	}
	

	private void InsertoAlumnoSql(Alumno a) {
		daoSql.insertoAlumno(a);
		System.out.println("Registro procesado en SQL.\n");
	}

	public void insertoGrupo() {
		System.out.print("ID del grupo: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Nombre del grupo: ");
		String nombre = entrada.nextLine().trim();
		daoSql.insertoGrupo(new Grupo(id, nombre));
		System.out.println("Nuevo grupo registrado.");
	}

	public void MuestroAlumnos() {
		System.out.println("\nLISTADO DE TODOS LOS ALUMNOS");
		System.out.println("------------------------------");
		List<Alumno> alumnos = daoSql.muestroAlumnos();
		if (alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
		} else {
			printCabeceraTablaAlumno();
			alumnos.forEach(this::printAlumnos);
			System.out.println();
		}
	}

	private void printCabeceraTablaAlumno() {
		System.out.printf("%-8s %-20s %-25s %-8s %-12s %-15s %-8s %-6s%n", 
				"NIA", "NOMBRE", "APELLIDOS", "GENERO", "FECH. NAC.", "CICLO", "CURSO", "GRUPO");
		IntStream.range(1, 110).forEach(x -> System.out.print("-"));
		System.out.println();
	}

	private void printAlumnos(Alumno al) {
		String nombreGrupo = (al.getGrupo() != null) ? al.getGrupo().getNombre() : "SIN GRUPO";
		System.out.printf("%-8d %-20s %-25s %-8c %-12s %-15s %-8s %-6s%n", 
				al.getNia(), al.getNombre(), al.getApellidos(), al.getGenero(), 
				al.getFecha(), al.getCiclo(), al.getCurso(), nombreGrupo);
	}

	public void guardarFichero() {
		System.out.println("--- Guardar Alumno en Fichero Binario ---");
		// Reutilizamos la lógica de pedir datos o podrías pedir solo el NIA si ya existe
		System.out.print("NIA: ");
		int nia = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Nombre: ");
		String nombre = entrada.nextLine();
		System.out.print("Apellidos: ");
		String apellidos = entrada.nextLine();
		System.out.print("Fecha (yyyy-mm-dd): ");
		LocalDate fecha = LocalDate.parse(entrada.nextLine());
		System.out.print("Género: ");
		char genero = entrada.next().charAt(0);
		entrada.nextLine();
		System.out.print("Ciclo: ");
		String ciclo = entrada.nextLine();
		System.out.print("Curso: ");
		String curso = entrada.nextLine();

		daoBinario.insertoAlumno(new Alumno(nia, nombre, apellidos, genero, fecha, ciclo, curso));
		System.out.println("Alumno guardado en archivo binario.");
	}

	public void mostrarFichero() {
		System.out.println("\nALUMNOS EN FICHERO BINARIO -> IMPORTANDO A BD");
		List<Alumno> alumnos = daoBinario.muestroAlumnos();
		if (alumnos.isEmpty()) {
			System.out.println("Fichero vacío.");
		} else {
			for (Alumno a : alumnos) {
				printAlumnos(a);
				daoSql.insertoAlumno(a);
			}
			System.out.println("Importación completada.");
		}
	}

	public void modificoAlumnoId() {
		System.out.print("NIA del alumno a modificar: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Nuevo nombre: ");
		String texto = entrada.nextLine();

		int resultado = daoSql.ModificoNombrePorNia(texto, id);
		System.out.println("Filas afectadas: " + resultado);
	}

	public void eliminoAlumnoId() {
		System.out.print("NIA del alumno a borrar: ");
		int id = entrada.nextInt();
		entrada.nextLine();

		System.out.printf("¿Seguro que desea eliminar NIA %d? (s/n): ", id);
		if (entrada.nextLine().equalsIgnoreCase("s")) {
			daoSql.eliminoAlumnoPorNia(id);
			System.out.println("Alumno eliminado.");
		}
	}

	public void eliminoAlumnoApellidos() {
		System.out.print("Apellido de los alumnos a borrar: ");
		String apellidos = entrada.nextLine();

		System.out.printf("¿Eliminar alumnos con apellido '%s'? (s/n): ", apellidos);
		if (entrada.nextLine().equalsIgnoreCase("s")) {
			int result = daoSql.eliminoAlumnoPorApellido(apellidos);
			System.out.println("Se han eliminado " + result + " alumnos.");
		}
	}

	public void exportoAJson() {
		System.out.println("Exportando grupos y alumnos a JSON...");
		List<Grupo> grupos = daoSql.obtenerGruposConAlumnos();
		daoJson.InsertoListaGrupos(grupos);
		System.out.println("Exportación finalizada.");
	}

	public void importoDesdeJson() {
		System.out.println("Importando desde JSON a SQL...");
		List<Alumno> alumnos = daoJson.muestroAlumnos();
		int cont = 0;
		for (Alumno a : alumnos) {
			if (daoSql.insertoAlumno(a) != -1) cont++;
		}
		System.out.println("Importados " + cont + " alumnos.");
	}
}
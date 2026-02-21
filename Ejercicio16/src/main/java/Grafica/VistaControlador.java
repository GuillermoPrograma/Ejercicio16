package Grafica;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import Model.Alumno;
import Model.Grupo;

public class VistaControlador implements InterfazGrafica {

	private Scanner entrada = new Scanner(System.in);

	public VistaControlador() {
	}

	public int mostrarMenu() {
		int opcion;

		System.out.println("\n===== GESTIÓN DE ALUMNOS Y GRUPOS =====");
		System.out.println("1. Insertar alumno");
		System.out.println("2. Insertar grupo");
		System.out.println("3. Añadir alumno a grupo (Asignar)");
		System.out.println("4. Mostrar todos los alumnos con su grupo");
		System.out.println("5. Mostrar alumnos de un grupo en especifico");
		System.out.println("6. Mostrar alumno a partir de su Nia");
		System.out.println("7. Modificar nombre de alumno por Nia ");
		System.out.println("8. Eliminar alumno por Nia");
		System.out.println("9. Eliminar alumnos por apellido");
		System.out.println("0. Salir");
		System.out.print("Seleccione una opción: ");
		opcion = entrada.nextInt();
		entrada.nextLine();
		return opcion;
	}

	public void mostrarMensaje(String msg) {
		System.out.println(msg);
	}

	public Alumno guardoAlumnoGrafica() {
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

	public Grupo guardoGrupoGrafica() {
		System.out.print("ID del grupo: ");
		int id = entrada.nextInt();
		entrada.nextLine();
		System.out.print("Nombre del grupo: ");
		String nombre = entrada.nextLine().trim();
		return new Grupo(id, nombre);
	}

	public int[] añadoAlumnoAGrupoGrafica() {
		System.out.println("Dime el nia de alumno");
		int nia = entrada.nextInt();
		System.out.println("dime el id del grupo");
		int id = entrada.nextInt();
		entrada.nextLine();
		int[] nia_Grupo = new int[2];
		nia_Grupo[0] = nia;
		nia_Grupo[1] = id;
		return nia_Grupo;
	}

	public void muestroAlumnosGrafica(List<Alumno> alumnos, boolean resumido) { // No se si estará bien pero un booleano
		
		if (alumnos.isEmpty()) {
			System.out.println("No hay alumnos registrados.");
		} else {

			printCabeceraTablaAlumno();
			if (resumido == false) {
				System.out.println("\nLISTADO DE TODOS LOS ALUMNOS");
				System.out.println("------------------------------");
				alumnos.forEach(this::printAlumnos);
				System.out.println();
			} else {
				for (Alumno a : alumnos) {
					System.out.println("nombre alumno : " + a.getNombre());
					System.out.println("primari key alumno : " + a.getNia());
				}

			}

		}
	}

	public int muestroAlumnosAPArtirDeGrupoGrafica() {

		System.out.println("Dime el id del grupo del que quieres buscar a los alumnos");
		int num = entrada.nextInt();
		entrada.nextLine();
		return num;
	}

	private void printCabeceraTablaAlumno() {
		System.out.printf("%-8s %-20s %-25s %-8s %-12s %-15s %-8s %-6s%n", "NIA", "NOMBRE", "APELLIDOS", "GENERO",
				"FECH. NAC.", "CICLO", "CURSO", "GRUPO");
		IntStream.range(1, 110).forEach(x -> System.out.print("-"));
		System.out.println();
	}

	private void printAlumnos(Alumno al) {
		String nombreGrupo = (al.getGrupo() != null) ? al.getGrupo().getNombre() : "SIN GRUPO";
		System.out.printf("%-8d %-20s %-25s %-8c %-12s %-15s %-8s %-6s%n", al.getNia(), al.getNombre(),
				al.getApellidos(), al.getGenero(), al.getFecha(), al.getCiclo(), al.getCurso(), nombreGrupo);
	}

	public String[] modificarAlumnoIdGrafica() {
		System.out.print("NIA del alumno a modificar: ");
		String id = entrada.nextLine();
		System.out.print("Nuevo nombre: ");
		String nombre = entrada.nextLine();

		String[] id_nombre = new String[2];

		try {
			Integer.parseInt(id);
		} catch (Exception e) {
			System.err.println("Error, la Ia tiene que ser un número");
		}
		id_nombre[0] = id;
		id_nombre[1] = nombre;
		return id_nombre;
	}

	public int eliminarAlumnoPorNia() {
		System.out.print("NIA del alumno a borrar: ");
		int id = entrada.nextInt();
		entrada.nextLine();

		System.out.printf("¿Seguro que desea eliminar NIA %d? (s/n): ", id);
		if (entrada.nextLine().equalsIgnoreCase("s")) {
			return id;
		} else {
			return -1;
		}
	}

	public String eliminarAlumnoPorApellido() {
		System.out.print("Apellido de los alumnos a borrar: ");
		String apellidos = entrada.nextLine();

		System.out.printf("¿Eliminar alumnos con apellido '%s'? (s/n): ", apellidos);
		if (entrada.nextLine().equalsIgnoreCase("s")) {
			return apellidos;
		} else
			return null;
	}

	@Override
	public int muestroAlumnoAPartirDeNia() {
		System.out.println("Dime el nia del alumno que quieres ver :");
		int num = entrada.nextInt();
		return num;
	}

}
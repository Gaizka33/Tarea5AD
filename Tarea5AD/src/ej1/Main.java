package ej1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
	private static File ficherosolicitado;
	private static FileOutputStream conexionescribir;
	private static FileInputStream conexionleer;
	private static ObjectOutputStream escribir;
	private static ObjectInputStream leer;
	private static Scanner abielto = new Scanner(System.in);
	private static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static Alumno crearAlumno(Scanner scanner) {
		System.out.println("Dame la fecha de nacimiento del alumno (dd-MM-yyyy): ");
		String fechaNacimientoString = scanner.next();
		LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoString, formato);

		System.out.println("Ahora Dame los datos del alumno (Nombre, Apellidos, Ciclo, Curso, Grupo, NIA y Genero): ");
		String nombre = scanner.next();
		String apellidos = scanner.next();
		String ciclo = scanner.next();
		String curso = scanner.next();
		String grupo = scanner.next();
		int nia = scanner.nextInt();
		char genero = scanner.next().charAt(0);

		return new Alumno(nombre, apellidos, ciclo, curso, grupo, nia, genero, fechaNacimiento);
	}

	public static void leerAlumno(File ficherosolicitado) {
		try {
			System.out.println("Ahora vamos a leer lo que pone en el archivo");
			conexionleer = new FileInputStream(ficherosolicitado);
			leer = new ObjectInputStream(conexionleer);

			while (conexionleer.available() > 0) {
				Alumno a = (Alumno) leer.readObject();
				System.out.println("Los alumnos son: /n" + a.toString() + "/n");
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Cierra el DataOutputStream
			try {
				if (leer != null) {
					leer.close();
				}
			} catch (Exception e) {
				e.printStackTrace(); // Imprime la traza de la excepción al cerrar
			}
		}
	}

	public static void main(String[] args) {

		try {
			System.out.println("Cual es el fichero que quieres leer?");
			ficherosolicitado = new File(abielto.next());
			conexionescribir = new FileOutputStream(ficherosolicitado);
			escribir = new ObjectOutputStream(conexionescribir);
			for (int i = 0; i < 5; i++) {
				escribir.writeObject(crearAlumno(abielto));
			}
			abielto.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Cierra el DataOutputStream
			try {
				if (escribir != null) {
					escribir.close();
				}
			} catch (Exception e) {
				e.printStackTrace(); // Imprime la traza de la excepción al cerrar
			}
		}
		leerAlumno(ficherosolicitado);
	}

}

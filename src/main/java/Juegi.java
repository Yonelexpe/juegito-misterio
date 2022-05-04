import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

//import com.elorrieta.clase.Conexion; //ni idea de porque

/**
 * intento de juego donde te mueves por los menus,leyendo las descripciones que
 * estan guardas en las descripcciones en mysql si los objetos empiezan por r ->
 * donde se guardan ls respuestas del usuario
 * 
 * @author jaele
 *
 */
public class Juegi {

	static Scanner sc = new Scanner(System.in);
	/// NOTAS
	private static final int OPCION_ADD = 1;
	private static final int OPCION_VER = 2;
	private static final int OPCION_ELIMINAR = 3;

	/// MENU
	int mirar = 1;
	private static final int OPCION_OBSERVAR = 1;
	private static final int OPCION_PREGUNTAR = 2;
	private static final int OPCION_RESOLVER = 3;
	private static final int OPCION_NOTAS = 4;

	public static void main(String[] args) {

		int opciones;
		boolean repetir = true;
		verIntro();

		do {
			opciones = menu();

			switch (opciones) {
			case OPCION_OBSERVAR:
				observar();
				break;
			case OPCION_PREGUNTAR:
				preguntar();
				break;
			case OPCION_RESOLVER:
				result();
				repetir = false;
				break;
			case OPCION_NOTAS:
				notas();
				break;
			default:
				// salir
				repetir = false;
				break;
			}

		} while (repetir);

	}// main

	private static void notas() {
		int opciones;

//		// ELIMINARNOTAS
//		PreparedStatement pst2 = con.prepareStatement(Modelo.SQL_ELIMINARNOTAS);
//		// ELEGIRNOTAS
//		

		boolean repetir = true;
		do {
			opciones = menunotas();

			switch (opciones) {
			case OPCION_ADD:

				anadirnotas();

				break;
			case OPCION_VER:
				vernotas();
				break;
			case OPCION_ELIMINAR:
				eliminarnotas();
				break;

			default:
				// salir
				repetir = false;
				break;
			}

		} while (repetir);

	}

	private static void eliminarnotas() {
		int respuesta =0;
		int filas =0;
	try (	Connection con = Conexion.getConnection();
			PreparedStatement pst = con.prepareStatement(Modelo.SQL_ELIMINARNOTAS);){
		System.out.println("que notas quieres eliminar\n");
		vernotas();
		respuesta = Integer.parseInt(sc.nextLine());
		
		pst.setInt(1, respuesta);

		filas = pst.executeUpdate();

		if (filas > 0) {
			System.out.println("nota eliminada");
		} else {
			System.out.println("no se ha podido eliminar la nota");
		}
		
		
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		
		
		
	}

	private static void vernotas() {
		try (Connection con = Conexion.getConnection();
				PreparedStatement pst = con.prepareStatement(Modelo.SQL_ELEGIRNOTAS);
				ResultSet rs= pst.executeQuery();) {
			while (rs.next()) {
				String texto = rs.getString("titulo");
				int idnotas = rs.getInt("id_notas");
				System.out.printf("%-2s : %s \n\n", idnotas, texto);
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void anadirnotas() {
		String apunte = null;
		int filas = 0;

		try (Connection con = Conexion.getConnection();
				PreparedStatement pst = con.prepareStatement(Modelo.SQL_ADD_NOTAS);) {

			System.out.println("¿Que es lo que quieres anotar?");
			apunte = sc.nextLine();

			pst.setString(1, apunte);

			filas = pst.executeUpdate();

			if (filas > 0) {
				System.out.println("nota añadida");
			} else {
				System.out.println("no se ha podido añadir la nota");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} // trycatch
	}

	private static int menunotas() {
		int x = 0;
		boolean flat = true;

		System.out.println("----------------------------------------------------");
		System.out.println("-----   ¿Que quieres hacer?      -------------------");
		System.out.println("----------------------------------------------------");
		System.out.println(" 1 - Añadir notas");
		System.out.println(" 2 - Ver notas");
		System.out.println(" 3 - Eliminar notas");
		System.out.println("----------------------------------------------------");
		System.out.println(" 0 - Salir");
		System.out.println("----------------------------------------------------");

		do {
			x = Integer.parseInt(sc.nextLine());
			if (x > 3 || x < 0) {
				System.out.println("error");
			} else {
				flat = false;
			}

		} while (flat);

		return x;
	}

	private static void preguntar() {
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		int ridpe = 0;
		int ridpre = 0;

		try (Connection con = Conexion.getConnection();
				// pregunta a quien
				PreparedStatement pst1 = con.prepareStatement(Modelo.SQL_AQUIEN);
				// pQue quieres preguntar
				PreparedStatement pst2 = con.prepareStatement(Modelo.SQL_QPREGUNTA);
				// respuesta
				PreparedStatement pst3 = con.prepareStatement(Modelo.SQL_PREGUNTA);
				ResultSet rs1 = pst1.executeQuery();) {

			System.out.println("A quien quieres preguntar");
			while (rs1.next()) {
				String nombre = rs1.getString("nombre");
				int idnombre = rs1.getInt("id_persona");
				System.out.printf("%-2s : %s \n", idnombre, nombre);
			}
			// elegir id_persona
			while (flag1) {
				ridpe = Integer.parseInt(sc.nextLine());

				if (ridpe < 1 || ridpe > 3) {
					System.out.println("error introduce un numero correcto");
				} else {
					flag1 = false;
				} // fin else
			} // finwhile

			System.out.println("que quieres preguntarle");
			ResultSet rs2 = pst2.executeQuery();

			while (rs2.next()) {
				String texto = rs2.getString("texto");
				int idpregun = rs2.getInt("id_pregunta");
				System.out.printf("%-2s : %s \n", idpregun, texto);
			}

			// elegir pregunta

			while (flag2) {
				ridpre = Integer.parseInt(sc.nextLine());

				if (ridpre < 1 || ridpre > 1) {
					System.out.println("error introduce un numero correcto");
				} else {
					flag2 = false;
				} // fin else
			} // finwhile

			// mostrar resultados

			pst3.setInt(1, ridpe);
			pst3.setInt(2, ridpre);

			ResultSet rs3 = pst3.executeQuery();

			while (rs3.next()) {

				String respuesta = rs3.getString("respuesta");
				String perso = rs3.getString("nombre");
				System.out.printf("%s te contesta: \n %s\n\n", perso, respuesta);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} // catch

	}// try

	private static void verIntro() {
		try (Connection con = Conexion.getConnection(); // mostar introduccion
				PreparedStatement pst = con.prepareStatement(Modelo.SQL_INTRO);
				ResultSet rs = pst.executeQuery();) {
			while (rs.next()) {

				//
				String intro = rs.getString("intro");
				intro = intro.replaceAll("<p>", "");
				intro = intro.replaceAll("</p>", "\n");
				System.out.println(intro);

			} // while

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error algo");
		} // CATCH

	}

	private static void observar() {
		int rhubi = 0;
		int robje = 0;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;

		try (Connection con = Conexion.getConnection();
				// escenarios
				PreparedStatement pst = con.prepareStatement(Modelo.SQL_ESCENARIOS);
				// objetos
				PreparedStatement pst2 = con.prepareStatement(Modelo.SQL_OBJETOS);
				// descripcion
				PreparedStatement pst3 = con.prepareStatement(Modelo.SQL_DESCRIPCION);
				ResultSet rs = pst.executeQuery();) {

			System.out.println("¿donde quieres buscar?");
			while (rs.next()) {
				String hubi = rs.getString("hubicacion");
				int id = rs.getInt("id_escenarios");

				System.out.printf("%-2s : %s \n", id, hubi);

				// TODO hacer exsecciones

			} // rs.next

// buscar objeto

			while (flag1) {
				rhubi = Integer.parseInt(sc.nextLine());

				if (rhubi < 1 || rhubi > 2) {
					System.out.println("error introduce un numero correcto");
				} else {
					flag1 = false;
				} // fin else

			} // finwhile

			pst2.setInt(1, rhubi);

			ResultSet rs2 = pst2.executeQuery();

			System.out.println("¿que quieres ver?");

			while (rs2.next()) {
				String obje = rs2.getString("objeto");
				int id = rs2.getInt("id_objetos");

				System.out.printf("%-2s : %s \n", id, obje);

			} // rs.next

			if (rhubi == 1) {
				while (flag2) {
					robje = Integer.parseInt(sc.nextLine());

					if (robje < 1 || robje > 2) {
						System.out.println("error introduce un numero correcto");
					} else {
						flag2 = false;
					}

				} // fin while

			} else {
				while (flag2) {
					robje = Integer.parseInt(sc.nextLine());

					if (robje < 3 || robje > 4) {
						System.out.println("error introduce un numero correcto");
					} else {
						flag2 = false;
					}
				}
			}

			// descricion del objeto

			pst3.setInt(1, rhubi);
			pst3.setInt(2, robje);

			ResultSet rs3 = pst3.executeQuery();

			while (rs3.next()) {
				String descri = rs3.getString("descripcion");

				System.out.printf("%s \n\n", descri);

				// TODO hacer exsecciones

			} // rs.next

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// observar

	private static void result() {
		// antonio seria la respuesta coreecta
		String asesino = null;
		try (Connection con = Conexion.getConnection();
				PreparedStatement pst = con.prepareStatement(Modelo.SQL_RESPUESTA);
				ResultSet rs = pst.executeQuery();

		) {
			System.out.println("quien crees que es el asesino");
			String respuesta = sc.nextLine();

			while (rs.next()) {

				asesino = rs.getString("asesino");

			} // rs

			if (asesino.equalsIgnoreCase(respuesta)) {// no se como hacer esto
				System.out.println("enhorabuena");

			} else {
				System.out.println("fallastes");
			} // ifelse
		} catch (Exception e) {

			e.printStackTrace();
		}

	}// fin result

	private static int menu() {
		int x = 0;
		boolean flat = true;

		System.out.println("----------------------------------------------------");
		System.out.println("-----   ¿Que quieres hacer?      -------------------");
		System.out.println("----------------------------------------------------");
		System.out.println(" 1 - Mirar escenario");
		System.out.println(" 2 - Preguntar sospechoso");
		System.out.println(" 3 - Resolver");
		System.out.println(" 4 - Notas");
		System.out.println("----------------------------------------------------");
		System.out.println(" 0 - Salir");
		System.out.println("----------------------------------------------------");

		do {
			x = Integer.parseInt(sc.nextLine());
			if (x > 4 || x < 0) {
				System.out.println("error");
			} else {
				flat = false;
			}

		} while (flat);

		return x;

	}// menu
}// class

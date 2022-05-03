import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import javax.management.loading.PrivateClassLoader;

import com.mysql.jdbc.RowDataStatic;

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
	private static final String SQL_INTRO = "SELECT intro FROM introduccion;";
	private static final String SQL_MIRAR = "SELECT descripcion FROM objetos_escenarios WHERE id_escenarios = 2 AND id_objetos = 3;";
	private static final String SQL_PREGUNTAR = "SELECT respuesta FROM persona_pregunta WHERE id_persona = 1 AND id_pregunta = 1;";
	private static final String Private = null;// private static final int opcion_mirar = 1;
	int mirar = 1;
	private static final int OPCION_OBSERVAR = 1;
	private static final int OPCION_PREGUNTAR = 2;
	private static final int OPCION_RESOLVER = 3;
	

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
				break;
			default:
				// salir
				repetir = false;
				break;
			}
			
		}while(repetir);		

	}// main

	private static void preguntar() {
		// TODO Auto-generated method stub
		
		
		
		
	}

	private static void verIntro() {
		try (Connection con = Conexion.getConnection(); // mostar introduccion
				PreparedStatement pst = con.prepareStatement(SQL_INTRO);
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
				PreparedStatement pst = con.prepareStatement(
						"SELECT hubicacion, id_escenarios FROM escenarios  ORDER BY id_escenarios asc;");
				PreparedStatement pst2 = con.prepareStatement(
						"SELECT o.id_objetos, objeto FROM objetos_escenarios oe , objetos o WHERE oe.id_objetos= o.id_objetos AND oe.id_escenarios = ? ;");
				PreparedStatement pst3 = con.prepareStatement(
						"SELECT descripcion FROM objetos_escenarios oe , objetos o WHERE oe.id_objetos= o.id_objetos AND oe.id_escenarios = ?  AND oe.id_objetos= ? ;");
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

			if (rhubi==1) {
				while (flag2) {
					robje = Integer.parseInt(sc.nextLine());
					
					if (robje <1 || robje >2) {
						System.out.println("error introduce un numero correcto");
					} else {
						flag2 = false;
					}
					
				}//fin while
				
				
				
				
			} else {
				while (flag2) {
					robje = Integer.parseInt(sc.nextLine());
					
					if (robje <3 || robje >4) {
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

				System.out.printf("%s \n", descri);

				// TODO hacer exsecciones

			} // rs.next

			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// observar

	private static void result() {
		// antonio seria la respuesta coreecta

		try (Connection con = Conexion.getConnection();
				PreparedStatement pst = con.prepareStatement("SELECT asesino FROM respuesta;");
				ResultSet rs = pst.executeQuery();

		) {
			System.out.println("quien crees que es el asesino");
			String respuesta = sc.nextLine();

			while (rs.next()) {

				String asesino = rs.getString("asesino");

				if (asesino.equals(respuesta)) {// no se como hacer esto
					System.out.println("enhorabuena");

				} else {
					System.out.println("fallastes");
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
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
		System.out.println(" 2 - preguntar sospechoso");
		System.out.println(" 3 - resolver");
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

	}// menu
}// class

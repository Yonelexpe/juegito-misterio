import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import com.elorrieta.clase.Conexion; //ni idea de porque

public class Juegi {

	private static final String SQL_INTRO = "SELECT intro FROM introduccion;";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String respuesta = Modelo.verEscenario(1, 2);
		System.out.println( respuesta );
		
		
		
		try (Connection con = Conexion.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INTRO);
				ResultSet rs = pst.executeQuery();) {
			while (rs.next()) {

				
				
				String intro = rs.getString("intro");
				intro = intro.replaceAll("<p>", "");
				intro = intro.replaceAll("</p>", "\n");
				System.out.println(intro);

			} // while

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error algo");
		} // CATCH

		
		
	
		
		
	}// MAIN

}// class

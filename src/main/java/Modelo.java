import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Modelo {

	private static final String SQL_ESCENARIO = "SELECT descripcion FROM objetos_escenarios WHERE id_escenarios = ? AND id_objetos = ?;";

	public static String verEscenario(int idEscenario, int idObjeto) {
		String resul = "*** NO EXISTEN Descripciones para el escenario " + idEscenario + " y objeto " + idObjeto;
		try (Connection con = Conexion.getConnection(); PreparedStatement pst = con.prepareStatement(SQL_ESCENARIO);) {

			pst.setInt(1, idEscenario);
			pst.setInt(2, idObjeto);

			try (ResultSet rs = pst.executeQuery()) {

				if (rs.next()) {
					resul = rs.getString("descripcion");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			resul = e.getMessage();
		}
		return resul;
	}

	
	
	
	
}

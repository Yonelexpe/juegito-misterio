import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Modelo {

	public static final String SQL_INTRO = "SELECT intro FROM introduccion;";
	public static final String SQL_ESCENARIO = "SELECT descripcion FROM objetos_escenarios WHERE id_escenarios = ? AND id_objetos = ?;";
/// preguntar
	public static final String SQL_PREGUNTA = "SELECT respuesta, nombre FROM personas p, persona_pregunta pp, preguntas \r\n"
			+ "WHERE p.id_persona=pp.id_persona AND pp.id_pregunta = preguntas.id_pregunta\r\n"
			+ "AND p.id_persona =  ? AND preguntas.id_pregunta= ?  ;";
	public static final String SQL_AQUIEN = "SELECT id_persona,nombre FROM personas;";
	public static final String SQL_QPREGUNTA = "SELECT id_pregunta, texto FROM preguntas;";
///observar
	public static final String SQL_ESCENARIOS = "SELECT hubicacion, id_escenarios FROM escenarios  ORDER BY id_escenarios asc;";
	public static final String SQL_OBJETOS = "SELECT o.id_objetos, objeto FROM objetos_escenarios oe , objetos o WHERE oe.id_objetos= o.id_objetos AND oe.id_escenarios = ? ;";
	public static final String SQL_DESCRIPCION = "SELECT descripcion FROM objetos_escenarios oe , objetos o WHERE oe.id_objetos= o.id_objetos AND oe.id_escenarios = ?  AND oe.id_objetos= ? ;";

/// SOLUCION
	public static final String SQL_RESPUESTA = "SELECT asesino FROM solucion;";

/// NOTAS
	//TODO sustituir nº por ?
	public static final String SQL_ADD_NOTAS = "INSERT INTO notas (titulo) VALUES (?);";
	public static final String SQL_ELIMINARNOTAS = "DELETE FROM misterio.notas WHERE (id_notas = ?);";
	public static final String SQL_ELEGIRNOTAS = "SELECT id_notas,titulo FROM notas ;";
	
	// no se usa // public static final String SQL_VERNOTAS = "SELECT titulo, apuntes FROM notas WHERE id_notas = ? ;";
	
	
	
	
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

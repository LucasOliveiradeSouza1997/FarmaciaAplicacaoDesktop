package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;

public class UsuarioDAO {
	public boolean confereLogin(String login, String senha) {

        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean encontrou = false;

        try {
            ps = conexao.prepareStatement("SELECT * FROM usuario WHERE loginUsuario = ? and senhaUsuario = ?");
            ps.setString(1, login);
            ps.setString(2, senha);

            rs = ps.executeQuery();

            if (rs.next()) {                
                encontrou = true;
            }
        } catch (SQLException ex) {
        	System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(conexao);
            try {
				ps.close();
	            rs.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
        }
        return encontrou;
    }
}

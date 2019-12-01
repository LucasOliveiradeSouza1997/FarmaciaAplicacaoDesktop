package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import Exception.UsuarioNaoEncontradoException;
import model.bean.Usuario;

public class UsuarioDAO {

	public Usuario confereLogin(String login, String senha) {

		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conexao.prepareStatement("SELECT * FROM usuario WHERE loginUsuario = ? and senhaUsuario = ?");
			ps.setString(1, login);
			ps.setString(2, senha);

			rs = ps.executeQuery();

			if (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuario.setNomeUsuario(rs.getString("nomeUsuario"));
				usuario.setTipoUsuario(rs.getString("tipoUsuario"));
				return usuario;
			} else {
				throw new UsuarioNaoEncontradoException("Login ou Senha inválidos");
			}
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
	}
}

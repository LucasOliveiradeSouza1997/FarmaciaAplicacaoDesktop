package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.Estoque;

public class EstoqueDAO {
	public void create(Estoque e) {

		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("INSERT INTO estoque(lote,quantidade,distribuidor)VALUES(?,?,?)");
			ps.setInt(1, e.getLote());
			ps.setInt(2, e.getQuantidade());
			ps.setString(3, e.getDistribuidor());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException ex2) {
	            throw new DAOException(ex2.getMessage());
			}
		}
	}
}
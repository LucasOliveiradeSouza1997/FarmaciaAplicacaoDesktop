package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.MedicamentoVenda;

public class MedicamentoVendaDAO {

	public void create(MedicamentoVenda mV) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("INSERT INTO medicamentoVenda(idMedicamento,idVenda,quantidadeVendida)VALUES(?,?,?)");
			ps.setInt(1, mV.getIdMedicamento());
			ps.setInt(2, mV.getIdVenda());
			ps.setInt(3, mV.getQuantidadeVendida());
			ps.executeUpdate();
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

package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.Medicamento;

public class MedicamentoDAO {
	public void create(Medicamento m) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("INSERT INTO medicamento(lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)VALUES(?,?,?,?,?,?)");
			ps.setInt(1,m.getLote());
			ps.setString(2, m.getNomeMedicamento());
			ps.setString(3, m.getDescricaoMedicamento());
			ps.setBigDecimal(4, m.getPrecoMedicamento());
			ps.setDate(5,new java.sql.Date(m.getValidadeMedicamento().getTime()));
			ps.setBoolean(6, m.isStatusMedicamento());
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

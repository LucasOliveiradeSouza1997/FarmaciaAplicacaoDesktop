package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.Venda;

public class VendaDAO {

	public void create(Venda v) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("INSERT INTO venda(idCaixa,cpfCliente,numeroNotaFiscal,dataVenda,horaVenda,valorTotal,tipoPagamento)VALUES(?,?,?,CURDATE(),CURTIME(),?,?)");
			ps.setInt(1, v.getCaixa().getIdCaixa());
			ps.setString(2, v.getCliente().getCpfCliente());
			ps.setString(3, v.getNumeroNotaFiscal());
			ps.setBigDecimal(4, v.getValorTotal());
			ps.setString(5, v.getTipoPagamento());
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

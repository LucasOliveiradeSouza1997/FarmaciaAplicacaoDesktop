package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.Venda;

public class VendaDAO {

	public int create(Venda v) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		int id=0;
		try {
			ps = conexao.prepareStatement("INSERT INTO venda(idCaixa,cpfCliente,numeroNotaFiscal,dataVenda,horaVenda,valorTotal,tipoPagamento,compraAtiva)VALUES(?,?,?,CURDATE(),CURTIME(),?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, v.getCaixa().getIdCaixa());
			ps.setString(2, v.getCliente().getCpfCliente());
			ps.setString(3, v.getNumeroNotaFiscal());
			ps.setBigDecimal(4, v.getValorTotal());
			ps.setString(5, v.getTipoPagamento());
			ps.setBoolean(6,v.isCompraAtiva());
			ps.executeUpdate();
			final ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
			    id = rs.getInt(1);
			}
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
		return id;
	}
}

package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConnectionFactory.ConnectionFactory;
import Exception.AberturaCaixaException;
import Exception.DAOException;
import model.bean.Caixa;

public class CaixaDAO {

	public void create(Caixa c) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement("SELECT * FROM caixaDisponivel WHERE idCaixaDisponivel=? AND utilizando=false");
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new AberturaCaixaException("Caixa já aberto !");
			}
			ps.close();
			rs.close();
			ps = conexao.prepareStatement(
					"INSERT INTO caixa(idCaixaDisponivel,dataCaixa,horaCaixa,valorInicial,status) VALUES(?,?,CURTIME( ),?,?)");
			ps.setInt(1, c.getCaixaDisponivel().getIdCaixaDisponivel());
			ps.setDate(2, new java.sql.Date(c.getDataCaixa().getTime()));
			ps.setBigDecimal(3, c.getValorInicial());
			ps.setBoolean(4, c.isStatus());
			ps.executeUpdate();
			ps.close();
			
			ps = conexao.prepareStatement("UPDATE caixaDisponivel SET utilizando = true WHERE idCaixaDisponivel = ?");
			ps.setInt(1, c.getCaixaDisponivel().getIdCaixaDisponivel());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException ex2) {
				throw new DAOException(ex2.getMessage());
			}
		}
	}

}

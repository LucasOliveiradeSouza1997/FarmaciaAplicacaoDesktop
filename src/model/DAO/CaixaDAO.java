package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnectionFactory.ConnectionFactory;
import Exception.AberturaCaixaException;
import Exception.DAOException;
import model.bean.Caixa;
import model.bean.CaixaDisponivel;
import model.bean.Estoque;

public class CaixaDAO {

	public void create(Caixa c) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao
					.prepareStatement("SELECT * FROM caixaDisponivel WHERE idCaixaDisponivel=? AND utilizando=false");
			ps.setInt(1, c.getCaixaDisponivel().getIdCaixaDisponivel());
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new AberturaCaixaException("Caixa já aberto !");
			}
			ps.close();
			rs.close();
			ps = conexao.prepareStatement(
					"INSERT INTO caixa(idCaixaDisponivel,dataCaixa,horaCaixa,valorInicial,valorCartao,valorDinheiro,valorCaixaFechado,status) VALUES(?,?,CURTIME( ),?,'0','0','0',?)");
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

	public List<Caixa> readCaixasAbertos() {
		Connection conexao = ConnectionFactory.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Caixa> caixasAbertos = new ArrayList<>();
		try {
			ps = conexao.prepareStatement(
					"SELECT * from caixa INNER JOIN caixaDisponivel ON caixa.idCaixaDisponivel=caixaDisponivel.idCaixaDisponivel WHERE caixa.status=true AND caixaDisponivel.utilizando=true ORDER BY caixa.idCaixaDisponivel ASC");
			rs = ps.executeQuery();

			while (rs.next()) {
				Caixa caixa = new Caixa();
				CaixaDisponivel caixaDisponivel = new CaixaDisponivel();
				caixaDisponivel.setIdCaixaDisponivel(rs.getInt("caixaDisponivel.idCaixaDisponivel"));
				caixa.setCaixaDisponivel(caixaDisponivel);
				caixa.setValorInicial(rs.getBigDecimal("caixa.valorInicial"));
				caixa.setValorCartao(rs.getBigDecimal("caixa.valorCartao"));
				caixa.setValorDinheiro(rs.getBigDecimal("caixa.valorDinheiro"));
				caixasAbertos.add(caixa);
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
		return caixasAbertos;
	}

	public Caixa readCaixaAberto(int numeroCaixa) {
		Connection conexao = ConnectionFactory.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Caixa caixa = new Caixa();
		try {
			ps = conexao.prepareStatement(
					"SELECT * from caixa INNER JOIN caixaDisponivel ON caixa.idCaixaDisponivel=caixaDisponivel.idCaixaDisponivel WHERE caixa.status=true AND caixaDisponivel.utilizando=true AND caixa.idCaixaDisponivel=?");
			ps.setInt(1, numeroCaixa);
			rs = ps.executeQuery();
			if (rs.next()) {
				CaixaDisponivel caixaDisponivel = new CaixaDisponivel();
				caixaDisponivel.setIdCaixaDisponivel(rs.getInt("caixaDisponivel.idCaixaDisponivel"));
				caixa.setIdCaixa(rs.getInt("caixa.idCaixa"));
				caixa.setCaixaDisponivel(caixaDisponivel);
				caixa.setValorInicial(rs.getBigDecimal("caixa.valorInicial"));
				caixa.setValorCartao(rs.getBigDecimal("caixa.valorCartao"));
				caixa.setValorDinheiro(rs.getBigDecimal("caixa.valorDinheiro"));
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
		return caixa;
	}

	public void updateCaixaFechamento(Caixa c) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conexao.prepareStatement("UPDATE caixa SET valorCaixaFechado = ?,status=? WHERE idCaixa = ?");
			ps.setBigDecimal(1, c.getValorCaixaFechado());
			ps.setBoolean(2, c.isStatus());
			ps.setInt(3, c.getIdCaixa());
			ps.executeUpdate();
			ps.close();
			ps = conexao.prepareStatement("UPDATE caixaDisponivel SET utilizando = ? WHERE idCaixaDisponivel = ?");
			ps.setBoolean(1, c.getCaixaDisponivel().isUtilizando());
			ps.setInt(2, c.getCaixaDisponivel().getIdCaixaDisponivel());
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
	
	public void updateCaixaCartao(Caixa c) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("UPDATE caixa SET valorCartao=valorCartao+? WHERE idCaixa=?");
			ps.setBigDecimal(1, c.getValorCartao());
			ps.setInt(2, c.getIdCaixa());
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
	
	public void updateCaixaDinheiro(Caixa c) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("UPDATE caixa SET valorDinheiro=valorDinheiro+? WHERE idCaixa=?");
			ps.setBigDecimal(1, c.getValorDinheiro());
			ps.setInt(2, c.getIdCaixa());
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
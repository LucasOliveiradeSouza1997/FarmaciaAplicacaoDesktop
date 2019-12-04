package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnectionFactory.ConnectionFactory;
import Exception.ClienteNaoEncontradoException;
import Exception.DAOException;
import Exception.MedicamentoInvalidoException;
import model.bean.Estoque;
import model.bean.Medicamento;
import model.bean.Venda;

public class MedicamentoDAO {
	public void create(Medicamento m) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement(
					"INSERT INTO medicamento(lote,nomeMedicamento,descricaoMedicamento,precoMedicamento,validadeMedicamento,statusMedicamento)VALUES(?,?,?,?,?,?)");
			ps.setInt(1, m.getLote());
			ps.setString(2, m.getNomeMedicamento());
			ps.setString(3, m.getDescricaoMedicamento());
			ps.setBigDecimal(4, m.getPrecoMedicamento());
			ps.setDate(5, new java.sql.Date(m.getValidadeMedicamento().getTime()));
			ps.setBoolean(6, m.isStatusMedicamento());
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

	public List<Medicamento> read(boolean lerTodosMedicamentos) {

		Connection conexao = ConnectionFactory.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Medicamento> medicamentos = new ArrayList<>();
		try {
			if (lerTodosMedicamentos) {
				ps = conexao.prepareStatement("SELECT * FROM medicamento WHERE statusMedicamento=true");
			} else { // medicamentos vencidos
				ps = conexao.prepareStatement(
						"SELECT * FROM medicamento WHERE statusMedicamento=true AND validadeMedicamento< NOW()");
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				Medicamento medicamento = new Medicamento();
				medicamento.setIdMedicamento(rs.getInt("idMedicamento"));
				medicamento.setLote(rs.getInt("lote"));
				medicamento.setNomeMedicamento(rs.getString("nomeMedicamento"));
				medicamento.setDescricaoMedicamento(rs.getString("descricaoMedicamento"));
				medicamento.setPrecoMedicamento(rs.getBigDecimal("precoMedicamento"));
				medicamento.setValidadeMedicamento(rs.getDate("validadeMedicamento"));
				medicamentos.add(medicamento);
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
		return medicamentos;
	}

	public void delete(Medicamento m) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("UPDATE medicamento SET statusMedicamento = false WHERE idMedicamento = ?");
			ps.setInt(1, m.getIdMedicamento());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
	}

	public List<Medicamento> readMedicamentoXEstoque() {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Medicamento> medicamentos = new ArrayList<>();
		try {
			ps = conexao.prepareStatement(
					"SELECT medicamento.idMedicamento, medicamento.lote, medicamento.nomeMedicamento, estoque.quantidade, estoque.distribuidor from medicamento inner join estoque on medicamento.lote = estoque.lote");
			rs = ps.executeQuery();

			while (rs.next()) {
				Medicamento medicamento = new Medicamento();
				Estoque estoque = new Estoque();

				medicamento.setIdMedicamento(rs.getInt("medicamento.idMedicamento"));
				medicamento.setLote(rs.getInt("medicamento.lote"));
				medicamento.setNomeMedicamento(rs.getString("medicamento.nomeMedicamento"));
				estoque.setQuantidade(rs.getInt("estoque.quantidade"));
				estoque.setDistribuidor(rs.getString("estoque.distribuidor"));
				medicamento.setEstoque(estoque);
				medicamentos.add(medicamento);
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
		return medicamentos;
	}

	public void update(Medicamento m) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conexao.prepareStatement("UPDATE medicamento SET nomeMedicamento=?,descricaoMedicamento=?,"
					+ "precoMedicamento=?,validadeMedicamento=? WHERE idMedicamento = ?");

			ps.setString(1, m.getNomeMedicamento());
			ps.setString(2, m.getDescricaoMedicamento());
			ps.setBigDecimal(3, m.getPrecoMedicamento());
			ps.setDate(4, new java.sql.Date(m.getValidadeMedicamento().getTime()));
			ps.setInt(5, m.getIdMedicamento());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
	}

	public List<Medicamento> readMedicamentosNaoVencidosComEstoque() {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Medicamento> medicamentos = new ArrayList<>();
		boolean encontrouRegistros = false;
		try {
			ps = conexao.prepareStatement(
					"SELECT medicamento.idMedicamento, medicamento.lote, medicamento.nomeMedicamento, estoque.quantidade, estoque.distribuidor from medicamento inner join estoque on medicamento.lote = estoque.lote WHERE estoque.quantidade > 0 AND medicamento.validadeMedicamento>= NOW()");
			rs = ps.executeQuery();
			while (rs.next()) {
				encontrouRegistros = true;
				Medicamento medicamento = new Medicamento();
				Estoque estoque = new Estoque();

				medicamento.setIdMedicamento(rs.getInt("medicamento.idMedicamento"));
				medicamento.setLote(rs.getInt("medicamento.lote"));
				medicamento.setNomeMedicamento(rs.getString("medicamento.nomeMedicamento"));
				estoque.setQuantidade(rs.getInt("estoque.quantidade"));
				estoque.setDistribuidor(rs.getString("estoque.distribuidor"));
				medicamento.setEstoque(estoque);
				medicamentos.add(medicamento);
			}
			if (!encontrouRegistros) {
				throw new MedicamentoInvalidoException("Nao encontrou medicamento para Venda");
			}

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
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
		return medicamentos;
	}

	public Medicamento readMedicamentosNaoVencidosComEstoque(int idMedicamento) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Medicamento medicamento = null;
		boolean encontrouRegistros = false;
		try {
			ps = conexao.prepareStatement(
					"SELECT medicamento.idMedicamento, medicamento.descricaoMedicamento, medicamento.precoMedicamento,medicamento.lote, medicamento.nomeMedicamento, estoque.quantidade, estoque.distribuidor from medicamento inner join estoque on medicamento.lote = estoque.lote WHERE estoque.quantidade > 0 AND medicamento.validadeMedicamento>= NOW() AND medicamento.idMedicamento=?");
			ps.setInt(1, idMedicamento);
			rs = ps.executeQuery();
			if (rs.next()) {
				encontrouRegistros = true;
				medicamento = new Medicamento();
				Estoque estoque = new Estoque();

				medicamento.setIdMedicamento(rs.getInt("medicamento.idMedicamento"));
				medicamento.setLote(rs.getInt("medicamento.lote"));
				medicamento.setNomeMedicamento(rs.getString("medicamento.nomeMedicamento"));
				medicamento.setDescricaoMedicamento(rs.getString("medicamento.descricaoMedicamento"));
				medicamento.setPrecoMedicamento(rs.getBigDecimal("medicamento.precoMedicamento"));
				estoque.setQuantidade(rs.getInt("estoque.quantidade"));
				estoque.setDistribuidor(rs.getString("estoque.distribuidor"));
				medicamento.setEstoque(estoque);
			}
			if (!encontrouRegistros) {
				throw new MedicamentoInvalidoException("Nao encontrou medicamento para Venda");
			}

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
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
		return medicamento;
	}
	
	public List<Medicamento> read(Venda venda) {

		Connection conexao = ConnectionFactory.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Medicamento> medicamentos = new ArrayList<>();
		try {
			ps = conexao.prepareStatement("SELECT * from medicamentoVenda INNER JOIN medicamento ON medicamentoVenda.idMedicamento = medicamento.idMedicamento WHERE medicamentoVenda.idVenda=?");
			ps.setInt(1, venda.getIdVenda());
			rs = ps.executeQuery();
			while (rs.next()) {
				Medicamento medicamento = new Medicamento();
				medicamento.setIdMedicamento(rs.getInt("medicamento.idMedicamento"));
				medicamento.setLote(rs.getInt("medicamento.lote"));
				medicamento.setNomeMedicamento(rs.getString("medicamento.nomeMedicamento"));
				medicamento.setDescricaoMedicamento(rs.getString("medicamento.descricaoMedicamento"));
				medicamento.setPrecoMedicamento(rs.getBigDecimal("medicamento.precoMedicamento"));
				medicamento.setValidadeMedicamento(rs.getDate("medicamento.validadeMedicamento"));
				Estoque estoque = new Estoque();
				estoque.setQuantidade(rs.getInt("medicamentoVenda.quantidadeVendida"));
				medicamento.setEstoque(estoque);
				medicamentos.add(medicamento);
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
		return medicamentos;
	}
}

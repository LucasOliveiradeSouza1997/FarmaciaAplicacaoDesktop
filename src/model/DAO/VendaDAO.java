package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.Cliente;
import model.bean.Estoque;
import model.bean.Medicamento;
import model.bean.MedicamentoVenda;
import model.bean.Venda;

public class VendaDAO {

	public int create(Venda v) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		int id = 0;
		try {
			ps = conexao.prepareStatement(
					"INSERT INTO venda(idCaixa,cpfCliente,numeroNotaFiscal,dataVenda,horaVenda,valorTotal,tipoPagamento,compraAtiva)VALUES(?,?,?,CURDATE(),CURTIME(),?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, v.getCaixa().getIdCaixa());
			ps.setString(2, v.getCliente().getCpfCliente());
			ps.setString(3, v.getNumeroNotaFiscal());
			ps.setBigDecimal(4, v.getValorTotal());
			ps.setString(5, v.getTipoPagamento());
			ps.setBoolean(6, v.isCompraAtiva());
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

	public List<Venda> read(String consulta) {

		Connection conexao = ConnectionFactory.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Venda> vendas = new ArrayList<>();
		try {
			if (consulta.toUpperCase().equals("TODAS")) {
				ps = conexao.prepareStatement("SELECT * FROM venda WHERE compraAtiva=true");
			} else if (consulta.toUpperCase().equals("DINHEIRO")) {
				ps = conexao.prepareStatement("SELECT * FROM venda WHERE tipoPagamento='D' AND compraAtiva=true");
			} else if (consulta.toUpperCase().equals("CARTAO")) {
				ps = conexao.prepareStatement("SELECT * FROM venda WHERE tipoPagamento='C' AND compraAtiva=true");
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				Venda venda = new Venda();
				Cliente cliente = new Cliente();
				venda.setIdVenda(rs.getInt("idVenda"));
				cliente.setCpfCliente(rs.getString("cpfCliente"));
				venda.setCliente(cliente);
				venda.setValorTotal(rs.getBigDecimal("valorTotal"));
				venda.setTipoPagamento(rs.getString("tipoPagamento"));
				vendas.add(venda);
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
		return vendas;
	}

	public void delete(Venda venda) {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		PreparedStatement psMedicamentosVendas = null;
		PreparedStatement psDesativaVenda = null;
		try {
			ps = conexao.prepareStatement("SELECT * FROM medicamentoVenda INNER JOIN medicamento ON medicamentoVenda.idMedicamento=medicamento.idMedicamento WHERE idVenda=?;");
			ps.setInt(1, venda.getIdVenda());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MedicamentoVenda medicamentoVenda = new MedicamentoVenda();
				medicamentoVenda.setIdVenda(rs.getInt("medicamentoVenda.idVenda"));
				int lote = rs.getInt("medicamento.lote");
				medicamentoVenda.setQuantidadeVendida(rs.getInt("medicamentoVenda.quantidadeVendida"));
				psMedicamentosVendas = conexao.prepareStatement("UPDATE estoque SET quantidade=quantidade+? WHERE lote=?");
				psMedicamentosVendas.setInt(1, medicamentoVenda.getQuantidadeVendida());
				psMedicamentosVendas.setInt(2, lote);
				psMedicamentosVendas.executeUpdate();
				if(psMedicamentosVendas !=null) {
					psMedicamentosVendas.close();
				}
			}
			psDesativaVenda = conexao.prepareStatement("UPDATE venda SET compraAtiva=false WHERE idVenda=?");
			psDesativaVenda.setInt(1, venda.getIdVenda());
			psDesativaVenda.executeUpdate();
			if(psDesativaVenda !=null) {
				psDesativaVenda.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
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

package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnectionFactory.ConnectionFactory;
import Exception.DAOException;
import model.bean.CaixaDisponivel;

public class CaixaDisponivelDAO {
	public List<CaixaDisponivel> read() {
		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CaixaDisponivel> caixasDisponiveis = new ArrayList<>();

		try {
			ps = conexao.prepareStatement("SELECT * FROM caixaDisponivel");
			rs = ps.executeQuery();

			while (rs.next()) {
				CaixaDisponivel caixaDisponivel = new CaixaDisponivel();
				caixaDisponivel.setIdCaixaDisponivel(rs.getInt("idCaixaDisponivel"));
				caixaDisponivel.setUtilizando(rs.getBoolean("utilizando"));
				caixasDisponiveis.add(caixaDisponivel);
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
		return caixasDisponiveis;
	}
}

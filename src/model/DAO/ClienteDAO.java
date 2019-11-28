package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ConnectionFactory.ConnectionFactory;
import model.bean.Cliente;

public class ClienteDAO {

	public void create(Cliente c) {

		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement("INSERT INTO produto (descricao,qtd,preco)VALUES(?,?,?)");
//			ps.setString(1, p.getDescricao());
//			ps.setInt(2, p.getQtd());
//			ps.setDouble(3, p.getPreco());
			ps.executeUpdate();

			JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				ps.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}
}

package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ConnectionFactory.ConnectionFactory;
import Exception.ClienteNaoEncontradoException;
import model.bean.Cliente;

public class ClienteDAO {

	public void create(Cliente c) {

		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement("INSERT INTO cliente(cpfCliente,nomeCLiente,rgCLiente,enderecoCliente,telefoneCLiente,tipoCLiente)VALUES(?,?,?,?,?,?)");

			ps.setString(1,c.getCpfCliente());
			ps.setString(2,c.getNomeCLiente());
			ps.setString(3,c.getRgCLiente());
			ps.setString(4,c.getEnderecoCliente());
			ps.setString(5,c.getTelefoneCLiente());
			ps.setString(6,c.getTipoCLiente());
			ps.executeUpdate();
			ps.close();
			if (c.getTipoCLiente().equals("E")) {
				ps = conexao.prepareStatement("INSERT INTO clienteEspecial(cpfCliente,qtdDEsconto)VALUES(?,?)");
				ps.setString(1, c.getCpfCliente());
				ps.setInt(2, c.getQtdDEsconto());
				ps.executeUpdate();
			}else if(c.getTipoCLiente().equals("N")) {
				ps = conexao.prepareStatement("INSERT INTO clienteNormal(cpfCliente,descontoDinheiro)VALUES(?,?)");
				ps.setString(1, c.getCpfCliente());
				ps.setInt(2, c.getDescontoDinheiro());
				ps.executeUpdate();
			}else {
				throw new ClienteNaoEncontradoException("Tipo Cliente não é Especial ou Normal");
			}
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

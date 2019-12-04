package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ConnectionFactory.ConnectionFactory;
import Exception.ClienteNaoEncontradoException;
import Exception.DAOException;
import model.bean.Caixa;
import model.bean.CaixaDisponivel;
import model.bean.Cliente;

public class ClienteDAO {

	public void create(Cliente c) {

		Connection conexao = ConnectionFactory.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement("INSERT INTO cliente(cpfCliente,nomeCLiente,rgCLiente,enderecoCliente,telefoneCLiente,tipoCLiente,statusCliente)VALUES(?,?,?,?,?,?,?)");

			ps.setString(1,c.getCpfCliente());
			ps.setString(2,c.getNomeCLiente());
			ps.setString(3,c.getRgCLiente());
			ps.setString(4,c.getEnderecoCliente());
			ps.setString(5,c.getTelefoneCLiente());
			ps.setString(6,c.getTipoCLiente());
			ps.setBoolean(7, c.isStatusCliente());
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
            throw new DAOException(ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
	            throw new DAOException(e.getMessage());
			}
		}
	}
	
	public List<Cliente> read() {

        Connection conexao = ConnectionFactory.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Cliente> clientes = new ArrayList<>();

        try {
            ps = conexao.prepareStatement("SELECT * FROM cliente WHERE statusCliente=true");
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCpfCliente(rs.getString("cpfCliente"));
                cliente.setNomeCLiente(rs.getString("nomeCLiente"));
                cliente.setRgCLiente(rs.getString("rgCLiente"));
                cliente.setEnderecoCliente(rs.getString("enderecoCliente"));
                cliente.setTelefoneCLiente(rs.getString("telefoneCLiente"));
                cliente.setTipoCLiente(rs.getString("tipoCLiente"));
                
                clientes.add(cliente);
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
        return clientes;
    }
	
    public void delete(Cliente c) {

        Connection conexao = ConnectionFactory.getConnection();
        
        PreparedStatement ps = null;
        String tipoCLiente ="";
        try {
        	if (c.getTipoCLiente().equals("E")) {
        		tipoCLiente = "clienteEspecial";
			}else if(c.getTipoCLiente().equals("N")) {
				tipoCLiente = "clienteNormal";
			}else {
				throw new ClienteNaoEncontradoException("Tipo Cliente não é Especial ou Normal");
			}
            ps = conexao.prepareStatement("DELETE FROM "+ tipoCLiente + " WHERE cpfCliente = ?");
            ps.setString(1, c.getCpfCliente());
            ps.executeUpdate();
            ps.close();
            ps = conexao.prepareStatement("UPDATE cliente SET statusCliente=false WHERE cpfCliente = ?");
            ps.setString(1, c.getCpfCliente());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        } finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
    }
    
    public void update(Cliente c) {
        Connection conexao = ConnectionFactory.getConnection();        
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement("UPDATE cliente SET nomeCLiente=?,rgCLiente=?,enderecoCliente=?,telefoneCLiente=?,tipoCLiente=? WHERE cpfCliente=?");
            ps.setString(1, c.getNomeCLiente());
            ps.setString(2, c.getRgCLiente());
            ps.setString(3, c.getEnderecoCliente());
            ps.setString(4, c.getTelefoneCLiente());
            ps.setString(5, c.getTipoCLiente());
            ps.setString(6, c.getCpfCliente());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        } finally {
			ConnectionFactory.closeConnection(conexao);
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
    }

	public Cliente read(String cpfCliente) {
		Connection conexao = ConnectionFactory.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente c = new Cliente();
		try {
			ps = conexao.prepareStatement("SELECT * from cliente WHERE cpfCliente=?");
			ps.setString(1, cpfCliente);
			rs = ps.executeQuery();
			if (rs.next()) {
				c.setTipoCLiente(rs.getString("tipoCliente"));
				c.setCpfCliente(rs.getString("cpfCliente"));
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
		return c;
	}
}
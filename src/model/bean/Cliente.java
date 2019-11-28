package model.bean;

public class Cliente {
	
	private String cpfCliente;
	private String nomeCLiente;
	private String rgCLiente;
	private String enderecoCliente;
	private String telefoneCLiente;
	private String tipoCLiente;
	private int qtdDEsconto; 
	private int descontoDinheiro;
	
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	public String getNomeCLiente() {
		return nomeCLiente;
	}
	public void setNomeCLiente(String nomeCLiente) {
		this.nomeCLiente = nomeCLiente;
	}
	public String getRgCLiente() {
		return rgCLiente;
	}
	public void setRgCLiente(String rgCLiente) {
		this.rgCLiente = rgCLiente;
	}
	public String getEnderecoCliente() {
		return enderecoCliente;
	}
	public void setEnderecoCliente(String enderecoCliente) {
		this.enderecoCliente = enderecoCliente;
	}
	public String getTelefoneCLiente() {
		return telefoneCLiente;
	}
	public void setTelefoneCLiente(String telefoneCLiente) {
		this.telefoneCLiente = telefoneCLiente;
	}
	public String getTipoCLiente() {
		return tipoCLiente;
	}
	public void setTipoCLiente(String tipoCLiente) {
		this.tipoCLiente = tipoCLiente;
	}
	public int getQtdDEsconto() {
		return qtdDEsconto;
	}
	public void setQtdDEsconto(int qtdDEsconto) {
		this.qtdDEsconto = qtdDEsconto;
	}
	public int getDescontoDinheiro() {
		return descontoDinheiro;
	}
	public void setDescontoDinheiro(int descontoDinheiro) {
		this.descontoDinheiro = descontoDinheiro;
	}
}

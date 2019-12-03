package model.bean;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Exception.DataDdMmYyyyInvalida;

public class Caixa {

	int idCaixa;
	CaixaDisponivel caixaDisponivel ;
	Date dataCaixa;
	Time horaCaixa;
	BigDecimal valorInicial;
	BigDecimal valorCartao;
	BigDecimal valorDinheiro;
	boolean status;
	private SimpleDateFormat formatoDDMMYYYY;
	
	public Caixa() {
		super();
		this.formatoDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy"); 
	}
	
	public int getIdCaixa() {
		return idCaixa;
	}
	public void setIdCaixa(int idCaixa) {
		this.idCaixa = idCaixa;
	}
	public CaixaDisponivel getCaixaDisponivel() {
		return caixaDisponivel;
	}
	public void setCaixaDisponivel(CaixaDisponivel caixaDisponivel) {
		this.caixaDisponivel = caixaDisponivel;
	}
	public Date getDataCaixa() {
		return dataCaixa;
	}
	public void setDataCaixa(Date dataCaixa) {
		this.dataCaixa = dataCaixa;
	}
	
	public String getDataCaixaToString() {
		return formatoDDMMYYYY.format(dataCaixa);
	}
	public void setDataCaixa(String data) {
		try {
			this.dataCaixa =  formatoDDMMYYYY.parse(data);
		} catch (ParseException e) {
			throw new DataDdMmYyyyInvalida("Data invalida");
		}
	}
	
	public Time getHoraCaixa() {
		return horaCaixa;
	}
	public void setHoraCaixa(Time horaCaixa) {
		this.horaCaixa = horaCaixa;
	}
	public BigDecimal getValorInicial() {
		return valorInicial;
	}
	public void setValorInicial(BigDecimal valorInicial) {
		this.valorInicial = valorInicial;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public BigDecimal getValorCartao() {
		return valorCartao;
	}

	public void setValorCartao(BigDecimal valorCartao) {
		this.valorCartao = valorCartao;
	}

	public BigDecimal getValorDinheiro() {
		return valorDinheiro;
	}

	public void setValorDinheiro(BigDecimal valorDinheiro) {
		this.valorDinheiro = valorDinheiro;
	}
}

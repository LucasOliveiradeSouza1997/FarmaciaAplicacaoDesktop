package model.bean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Exception.DataDdMmYyyyInvalida;

public class Medicamento {
	private int idMedicamento;
	private int lote;
	private String nomeMedicamento;
	private String descricaoMedicamento;
	private BigDecimal precoMedicamento;
	private Date validadeMedicamento;
	private boolean statusMedicamento;
	private SimpleDateFormat formatoDDMMYYYY;
	
	public Medicamento() {
		super();
		this.formatoDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy"); 
	}
	
	public int getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
	}
	
	public int getLote() {
		return lote;
	}

	public void setLote(int lote) {
		this.lote = lote;
	}

	public String getNomeMedicamento() {
		return nomeMedicamento;
	}
	public void setNomeMedicamento(String nomeMedicamento) {
		this.nomeMedicamento = nomeMedicamento;
	}
	public String getDescricaoMedicamento() {
		return descricaoMedicamento;
	}
	public void setDescricaoMedicamento(String descricaoMedicamento) {
		this.descricaoMedicamento = descricaoMedicamento;
	}
	public BigDecimal getPrecoMedicamento() {
		return precoMedicamento;
	}
	public void setPrecoMedicamento(BigDecimal precoMedicamento) {
		this.precoMedicamento = precoMedicamento;
	}
	public Date getValidadeMedicamento() {
		return validadeMedicamento;
	}
	
	public String getValidadeMedicamentoToString() {
		return formatoDDMMYYYY.format(validadeMedicamento);
	}
	
	public void setValidadeMedicamento(Date validadeMedicamento) {
		this.validadeMedicamento = validadeMedicamento;
	}
	public void setValidadeMedicamento(String validadeMedicamento) {
		try {
			this.validadeMedicamento =  formatoDDMMYYYY.parse(validadeMedicamento);
		} catch (ParseException e) {
			throw new DataDdMmYyyyInvalida("Data invalida");
		}
	}
	
	public boolean isStatusMedicamento() {
		return statusMedicamento;
	}
	public void setStatusMedicamento(boolean statusMedicamento) {
		this.statusMedicamento = statusMedicamento;
	}
}

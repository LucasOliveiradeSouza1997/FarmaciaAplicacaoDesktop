package model.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Medicamento {
	private int idMedicamento;
	private String nomeMedicamento;
	private String descricaoMedicamento;
	private BigDecimal precoMedicamento;
	private Date validadeMedicamento;
	private boolean statusMedicamento;
	
	public int getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(int idMedicamento) {
		this.idMedicamento = idMedicamento;
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
	public void setValidadeMedicamento(Date validadeMedicamento) {
		this.validadeMedicamento = validadeMedicamento;
	}
	public boolean isStatusMedicamento() {
		return statusMedicamento;
	}
	public void setStatusMedicamento(boolean statusMedicamento) {
		this.statusMedicamento = statusMedicamento;
	}
}

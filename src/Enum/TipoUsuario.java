package Enum;

public enum TipoUsuario {

	ATENDENTE("A"), GERENTE("G");
	private String descricao;

	TipoUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}

package Exception;

public class QuantidadeInvalidaEstoqueException extends RuntimeException {

	private static final long serialVersionUID = -3497765619352266104L;

	public QuantidadeInvalidaEstoqueException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public QuantidadeInvalidaEstoqueException(String mensagem) {
		super(mensagem);
	}

	public QuantidadeInvalidaEstoqueException(Throwable e) {
		super(e);
	}
}

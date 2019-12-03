package Exception;

public class FormaDePagamentoException extends RuntimeException {

	private static final long serialVersionUID = -8229054565114698147L;

	public FormaDePagamentoException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public FormaDePagamentoException(String mensagem) {
		super(mensagem);
	}

	public FormaDePagamentoException(Throwable e) {
		super(e);
	}
}

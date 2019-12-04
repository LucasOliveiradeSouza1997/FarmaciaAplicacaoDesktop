package Exception;

public class MedicamentoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = -3124807178163535697L;

	public MedicamentoInvalidoException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public MedicamentoInvalidoException(String mensagem) {
		super(mensagem);
	}

	public MedicamentoInvalidoException(Throwable e) {
		super(e);
	}
}

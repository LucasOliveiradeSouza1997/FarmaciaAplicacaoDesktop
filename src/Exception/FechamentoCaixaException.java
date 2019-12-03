package Exception;

public class FechamentoCaixaException extends RuntimeException {

	private static final long serialVersionUID = -8173361515043934512L;

	public FechamentoCaixaException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public FechamentoCaixaException(String mensagem) {
		super(mensagem);
	}

	public FechamentoCaixaException(Throwable e) {
		super(e);
	}
}

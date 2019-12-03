package Exception;

public class AberturaCaixaException extends RuntimeException {

	private static final long serialVersionUID = 3213250092200684523L;

	public AberturaCaixaException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public AberturaCaixaException(String mensagem) {
		super(mensagem);
	}

	public AberturaCaixaException(Throwable e) {
		super(e);
	}
}

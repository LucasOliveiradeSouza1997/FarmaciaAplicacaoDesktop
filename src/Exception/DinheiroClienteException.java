package Exception;

public class DinheiroClienteException extends RuntimeException {
	
	private static final long serialVersionUID = -585403133331388501L;

	public DinheiroClienteException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public DinheiroClienteException(String mensagem) {
		super(mensagem);
	}

	public DinheiroClienteException(Throwable e) {
		super(e);
	}
}

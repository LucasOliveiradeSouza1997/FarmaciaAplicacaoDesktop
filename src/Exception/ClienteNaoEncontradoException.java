package Exception;

public class ClienteNaoEncontradoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7262246939100319905L;

	public ClienteNaoEncontradoException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public ClienteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ClienteNaoEncontradoException(Throwable e) {
		super(e);
	}
}

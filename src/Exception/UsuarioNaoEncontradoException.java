package Exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -3099708112099740859L;

	public UsuarioNaoEncontradoException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Throwable e) {
		super(e);
	}
}

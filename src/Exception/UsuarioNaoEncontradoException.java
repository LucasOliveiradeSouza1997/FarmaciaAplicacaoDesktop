package Exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8788616258057096317L;

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

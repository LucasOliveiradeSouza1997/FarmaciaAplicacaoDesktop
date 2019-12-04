package Exception;

public class DinheiroCaixaException extends RuntimeException {

	private static final long serialVersionUID = 8329183086155347638L;

	public DinheiroCaixaException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public DinheiroCaixaException(String mensagem) {
		super(mensagem);
	}

	public DinheiroCaixaException(Throwable e) {
		super(e);
	}
}

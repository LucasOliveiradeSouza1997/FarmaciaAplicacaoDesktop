package Exception;

public class DataDigitadaInvalidaException extends RuntimeException {
	
	private static final long serialVersionUID = -4854564918178994350L;

	public DataDigitadaInvalidaException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public DataDigitadaInvalidaException(String mensagem) {
		super(mensagem);
	}

	public DataDigitadaInvalidaException(Throwable e) {
		super(e);
	}
}

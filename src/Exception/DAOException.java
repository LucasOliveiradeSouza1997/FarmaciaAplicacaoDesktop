package Exception;

public class DAOException extends RuntimeException {


	private static final long serialVersionUID = -4367409677555930422L;

	public DAOException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public DAOException(String mensagem) {
		super(mensagem);
	}

	public DAOException(Throwable e) {
		super(e);
	}
}

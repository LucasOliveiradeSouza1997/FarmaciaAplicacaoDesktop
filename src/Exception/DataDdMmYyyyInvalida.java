package Exception;

public class DataDdMmYyyyInvalida extends RuntimeException {
	
	private static final long serialVersionUID = 516813786409446885L;

	public DataDdMmYyyyInvalida(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public DataDdMmYyyyInvalida(String mensagem) {
		super(mensagem);
	}

	public DataDdMmYyyyInvalida(Throwable e) {
		super(e);
	}
}

package Exception;

public class QuantidadeInvalidaMedicamentoEstoqueException extends RuntimeException {

	private static final long serialVersionUID = 47445095584742761L;

	public QuantidadeInvalidaMedicamentoEstoqueException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public QuantidadeInvalidaMedicamentoEstoqueException(String mensagem) {
		super(mensagem);
	}

	public QuantidadeInvalidaMedicamentoEstoqueException(Throwable e) {
		super(e);
	}
}

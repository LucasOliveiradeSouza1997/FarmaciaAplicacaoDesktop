package Auxiliares;
import java.util.Random;

public class NumeroDoLoteMedicamento {
	
	private static Random numeroLote;
	
	public static int geraNumero() {
		if (numeroLote == null) {
			numeroLote = new Random();
		}
		return numeroLote.nextInt(999999999);
	}
}

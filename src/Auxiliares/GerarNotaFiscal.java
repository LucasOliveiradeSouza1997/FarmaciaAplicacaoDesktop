package Auxiliares;

import java.util.Random;

public class GerarNotaFiscal {
	
	private static Random numeroLote;
	
	public static String geraNFe() {
		StringBuilder notaFiscal = new StringBuilder();
		int digito;
		
		if (numeroLote == null) {
			numeroLote = new Random();
		}
		for (int i = 0; i < 44; i++) {
			digito = numeroLote.nextInt(10);
			notaFiscal.append(Integer.toString(digito));
		}		
		return notaFiscal.toString();
	}
}
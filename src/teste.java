import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Auxiliares.NumeroDoLoteMedicamento;

public class teste {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date data = formato.parse("23/11/2015");
		System.out.println(formato.format(data));
		System.out.println(new java.sql.Date(data.getTime()));
//		
//		BigDecimal preco = new BigDecimal("0.00");
//		
//		if (preco.compareTo(new BigDecimal("0")) == 1) {
//			System.out.println("valor valido");
//		}else {
//			System.out.println("b");
//		}
//		System.out.println(NumeroDoLoteMedicamento.geraNumero());
	}
}

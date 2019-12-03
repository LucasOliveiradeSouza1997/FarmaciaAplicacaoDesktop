package Auxiliares;

import Exception.DataDigitadaInvalidaException;

public class ValidaData {
	
	
	public static void validaDataComExcecao(String dia, String mes, String ano) {
		try {
			if (!validaData(dia,mes,ano)) {
				throw new DataDigitadaInvalidaException("Data invalida");
			}
		} catch (NumberFormatException e) {
			throw new DataDigitadaInvalidaException("Data invalida");
		}
	}
	
	public static boolean validaData(String diaString , String mesString, String anoString){
		int dia = Integer.parseInt(diaString);
		int mes = Integer.parseInt(mesString);
		int ano = Integer.parseInt(anoString);

		if (dia < 1 || mes < 1 || mes >12 || ano < 0){ 
			return false;
		}
		if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10  || mes == 12  ){
			if(dia > 31 ){ // meses com 31 dias
				return false;
			}
		}else if (mes == 4 || mes == 6 || mes == 9 || mes == 11  ){ // meses com 30 dias
			if(dia > 30 ){
				return false;
			}
		}else{//fevereiro
			int bissexto = ano % 4 == 0 ? 1 : 0;
			if (dia > 28 + bissexto){
				return false;
			}
		}
		return true;
	}
}

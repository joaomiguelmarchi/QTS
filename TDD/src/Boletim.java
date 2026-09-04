public class Boletim {
	public static double calcularMedia(double n1, double n2) {
		validar(n1);
		validar(n2);
		return (n1 + n2) / 2;
	}

// extraido no refactor: a regra fica em UM lugar so
	private static void validar(double nota) {
		if (nota < 0 || nota > 10) {
			throw new IllegalArgumentException("Nota invalida: " + nota);
		}
	}
	
	public static String situacao(double media) {
	    if (media >= 6.0) {
	        return "Aprovado";
	    }

	    if (media >= 4.0) {
	        return "Recuperacao";
	    }

	    return "Reprovado";
	}
}
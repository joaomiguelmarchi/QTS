import java.util.*;

public class Carrinho {
	private List<Double> itens = new ArrayList<>();

	public void adicionar(double preco) {	
		if (preco <= 0)
			throw new IllegalArgumentException("Preco deve ser positivo");

		itens.add(preco);
	}

	public double total() {
		double s = 0;
		for (double p : itens)
			s += p;
		return s;
	}

	public int quantidade() {
		return itens.size();
	}

	public boolean vazio() {
		return itens.isEmpty();
	}
	
	public void remover(double preco) {
	    itens.remove(preco);
	}
	
	public void aplicarDesconto(double percentual) {
	    if (percentual < 0 || percentual > 100) {
	        throw new IllegalArgumentException(
	            "Percentual deve estar entre 0 e 100"
	        );
	    }

	    double fator = 1 - percentual / 100.0;

	    for (int i = 0; i < itens.size(); i++) {
	        itens.set(i, itens.get(i) * fator);
	    }
	}
	
}
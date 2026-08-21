import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CarrinhoTest {
	private Carrinho carrinho;

	@BeforeEach
	public void prepararCarrinho() {
		carrinho = new Carrinho(); // um carrinho NOVO antes de cada teste
	}

	@Test
	@DisplayName("carrinho novo comeca vazio")
	public void comecaVazio() {
		assertTrue(carrinho.vazio());
		assertEquals(0, carrinho.quantidade());
	}

	@Test
	@DisplayName("adicionar dois itens soma o total")
	public void somaTotal() {
		carrinho.adicionar(10.50);
		carrinho.adicionar(20.00);
		assertEquals(30.50, carrinho.total(), 0.001);
		assertEquals(2, carrinho.quantidade());
	}

	@Test
	@DisplayName("preco negativo lanca excecao")
	public void precoInvalido() {
		assertThrows(IllegalArgumentException.class, () -> carrinho.adicionar(-5));
	}

	@Test
	@DisplayName("preco zero recusado")
	public void precoZero() {
		assertThrows(IllegalArgumentException.class, () -> carrinho.adicionar(0));
	}

	
	@Test
	@DisplayName("este teste NAO ve os itens do teste anterior")
	public void isolamento() {
		assertEquals(0, carrinho.quantidade()); // prova o @BeforeEach
	}
	
	//Testes de remover
	
	@Test
	@DisplayName("remove um item do carrinho")
	public void removerItem() {
	    carrinho.adicionar(10.00);
	    carrinho.adicionar(20.00);

	    carrinho.remover(10.00);

	    assertEquals(1, carrinho.quantidade());
	    assertEquals(20.00, carrinho.total(), 0.001);
	}

	@Test
	@DisplayName("remover item que nao existe nao altera o carrinho")
	public void removerItemInexistente() {
	    carrinho.adicionar(10.00);

	    carrinho.remover(20.00);

	    assertEquals(1, carrinho.quantidade());
	    assertEquals(10.00, carrinho.total(), 0.001);
	}
	
	@Test
	@DisplayName("remover item de carrinho vazio nao causa erro")
	public void removerDeCarrinhoVazio() {
	    carrinho.remover(10.00);

	    assertTrue(carrinho.vazio());
	    assertEquals(0, carrinho.quantidade());
	}
	
	
	
	//Testes de desconto
	
	@Test
	@DisplayName("aplica desconto de 10 porcento")
	public void descontoDezPorcento() {
	    carrinho.adicionar(100.00);

	    carrinho.aplicarDesconto(10);

	    assertEquals(90.00, carrinho.total(), 0.001);
	}

	@Test
	@DisplayName("desconto de zero porcento nao altera o total")
	public void descontoZero() {
	    carrinho.adicionar(100.00);

	    carrinho.aplicarDesconto(0);

	    assertEquals(100.00, carrinho.total(), 0.001);
	}

	@Test
	@DisplayName("desconto de 100 porcento zera o total")
	public void descontoCem() {
	    carrinho.adicionar(100.00);

	    carrinho.aplicarDesconto(100);

	    assertEquals(0.00, carrinho.total(), 0.001);
	}

	@Test
	@DisplayName("desconto negativo e invalido")
	public void descontoNegativo() {
	    assertThrows(IllegalArgumentException.class,
	        () -> carrinho.aplicarDesconto(-10));
	}

	@Test
	@DisplayName("desconto acima de 100 e invalido")
	public void descontoAcimaDeCem() {
	    assertThrows(IllegalArgumentException.class,
	        () -> carrinho.aplicarDesconto(101));
	}
	
}


//3:

//o teste passa, porque List.remove() simplesmente retorna false quando o elemento não existe, sem lançar exceção.

//Como analista, se a especificação não disser o que deve acontecer, eu levantaria essa dúvida antes de definir o comportamento. Uma opção razoável seria não lançar exceção, pois tentar remover algo que não existe pode ser tratado como uma operação sem efeito. Mas isso precisa ser confirmado com o requisito do sistema.



//4:Portanto, são 5 casos de teste, porque os dois tipos de percentual inválido representam comportamentos que precisam ser verificados separadamente.








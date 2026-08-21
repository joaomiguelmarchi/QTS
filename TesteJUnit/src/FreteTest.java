import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class FreteTest {
	@Test
	@DisplayName("CT01 - peso 3 kg cobra R$ 10,00")
	public void peso3Kg() {
	    assertEquals(10.0, Frete.calcular(3, 50), 0.001);
	}

	@Test
	@DisplayName("CT02 - peso 0 kg retorna -1")
	public void pesoZero() {
	    assertEquals(-1.0, Frete.calcular(0, 50), 0.001);
	}

	@Test
	@DisplayName("CT03 - peso negativo retorna -1")
	public void pesoNegativo() {
	    assertEquals(-1.0, Frete.calcular(-1, 50), 0.001);
	}

	@Test
	@DisplayName("CT04 - peso 5 kg cobra R$ 10,00")
	public void peso5Kg() {
	    assertEquals(10.0, Frete.calcular(5, 199), 0.001);
	}

	@Test
	@DisplayName("CT05 - peso 10 kg cobra R$ 18,00")
	public void peso10Kg() {
	    assertEquals(18.0, Frete.calcular(10, 50), 0.001);
	}

	@Test
	@DisplayName("CT06 - peso 10,01 kg cobra R$ 25,00")
	public void pesoAcima10Kg() {
	    assertEquals(25.0, Frete.calcular(10.01, 50), 0.001);
	}

	@Test
	@DisplayName("CT07 - peso 30 kg cobra R$ 25,00")
	public void peso30Kg() {
	    assertEquals(25.0, Frete.calcular(30, 50), 0.001);
	}

	@Test
	@DisplayName("CT08 - peso 30,01 kg retorna -1")
	public void pesoAcima30Kg() {
	    assertEquals(-1.0, Frete.calcular(30.01, 50), 0.001);
	}

	@Test
	@DisplayName("CT09 - peso 5 kg e compra de R$ 200,00 cobra R$ 10,00")
	public void compra200() {
	    assertEquals(10.0, Frete.calcular(5, 200), 0.001);
	}

	@Test
	@DisplayName("CT10 - compra acima de R$ 200,00 tem frete grátis")
	public void compraAcima200() {
	    assertEquals(0.0, Frete.calcular(5, 200.01), 0.001);
	}
}
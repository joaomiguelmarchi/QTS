import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoletimTest {

	@Test
	@DisplayName("CT01 - media de 8.0 e 6.0 resulta em 7.0")
	public void mediaSimples() {
		assertEquals(7.0, Boletim.calcularMedia(8.0, 6.0), 0.001);
	}

	@Test
	@DisplayName("CT02 - nota negativa e recusada")
	public void notaNegativa() {
		assertThrows(IllegalArgumentException.class, () -> Boletim.calcularMedia(-1.0, 5.0));
	}

	@Test
	@DisplayName("CT04 - nota 0 e valida (limite inferior)")
	public void limiteZero() {
		assertEquals(2.5, Boletim.calcularMedia(0, 5), 0.001);
	}

	@Test
	@DisplayName("CT05 - nota 10 e valida (limite superior)")
	public void limiteDez() {
		assertEquals(7.5, Boletim.calcularMedia(10, 5), 0.001);
	}

	@Test
	@DisplayName("CT06 - media 6.0 e Aprovado (limite)")
	public void aprovadoLimite() {
		assertEquals("Aprovado", Boletim.situacao(6.0));
	}

	@Test
	@DisplayName("CT07 - media 5.9 e Recuperacao")
	public void recuperacao() {
		assertEquals("Recuperacao", Boletim.situacao(5.9));
	}

	@Test
	@DisplayName("CT08 - media 4.0 e Recuperacao (limite)")
	public void recuperacaoLimite() {
		assertEquals("Recuperacao", Boletim.situacao(4.0));
	}

	@Test
	@DisplayName("CT09 - media 3.9 e Reprovado")
	public void reprovado() {
		assertEquals("Reprovado", Boletim.situacao(3.9));
	}

}

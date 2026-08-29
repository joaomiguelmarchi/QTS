import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NovoPedidoTest {

	  private static final double DELTA = 0.001;

	    // =========================================================
	    // CLASSE DE EQUIVALÊNCIA: VALORES INVÁLIDOS
	    // valor <= 0
	    // =========================================================

	    @Test
	    void deveRetornarMenosUmParaValorNegativo() {
	        assertEquals(
	                -1,
	                Pedido.calcularTotal(-10.00, false, null),
	                DELTA
	        );
	    }

	    @Test
	    void deveRetornarMenosUmParaValorZero() {
	        assertEquals(
	                -1,
	                Pedido.calcularTotal(0.00, false, null),
	                DELTA,
	                "Fronteira: valor igual a zero deve ser considerado inválido"
	        );
	    }

	    @Test
	    void deveRetornarMenosUmParaValorZeroMesmoSendoVipComCupom() {
	        assertEquals(
	                -1,
	                Pedido.calcularTotal(0.00, true, "PRIMEIRA"),
	                DELTA,
	                "Valor zero continua inválido independentemente de VIP ou cupom"
	        );
	    }


	    // =========================================================
	    // FRONTEIRA INFERIOR: imediatamente acima de zero
	    // =========================================================

	    @Test
	    void deveCalcularValorImediatamenteAcimaDeZero() {
	        assertEquals(
	                0.01,
	                Pedido.calcularTotal(0.01, false, null),
	                DELTA,
	                "Fronteira: valor imediatamente acima de zero deve ser válido"
	        );
	    }

	    @Test
	    void deveAplicarCupomParaValorImediatamenteAcimaDeZero() {
	        assertEquals(
	                0.009,
	                Pedido.calcularTotal(0.01, false, "PRIMEIRA"),
	                DELTA,
	                "Cupom PRIMEIRA deve aplicar 10% para pedido de até R$ 100,00"
	        );
	    }


	    // =========================================================
	    // CLASSE: ATÉ R$ 100,00
	    // Desconto base = 0%
	    // =========================================================

	    @Test
	    void deveManterValorParaPedidoAbaixoDeCem() {
	        assertEquals(
	                50.00,
	                Pedido.calcularTotal(50.00, false, null),
	                DELTA
	        );
	    }

	    @Test
	    void deveAplicarCincoPorCentoParaVipAbaixoDeCem() {
	        assertEquals(
	                47.50,
	                Pedido.calcularTotal(50.00, true, null),
	                DELTA
	        );
	    }

	    @Test
	    void deveAplicarDezPorCentoDoCupomAbaixoDeCem() {
	        assertEquals(
	                45.00,
	                Pedido.calcularTotal(50.00, false, "PRIMEIRA"),
	                DELTA
	        );
	    }

	    @Test
	    void deveAplicarQuinzePorCentoParaVipComCupomAbaixoDeCem() {
	        assertEquals(
	                42.50,
	                Pedido.calcularTotal(50.00, true, "PRIMEIRA"),
	                DELTA
	        );
	    }

	    @Test
	    void cupomInvalidoNaoDeveConcederDesconto() {
	        assertEquals(
	                50.00,
	                Pedido.calcularTotal(50.00, false, "OUTRO"),
	                DELTA
	        );
	    }


	    // =========================================================
	    // FRONTEIRA R$ 100,00
	    // Até R$ 100 inclusive = 0% de desconto base
	    // Cupom PRIMEIRA ainda é válido
	    // =========================================================

	    @Test
	    void deveManterValorExatamenteEmCem() {
	        assertEquals(
	                100.00,
	                Pedido.calcularTotal(100.00, false, null),
	                DELTA,
	                "Fronteira: R$ 100,00 pertence à faixa sem desconto"
	        );
	    }

	    @Test
	    void deveAplicarCincoPorCentoParaVipExatamenteEmCem() {
	        assertEquals(
	                95.00,
	                Pedido.calcularTotal(100.00, true, null),
	                DELTA,
	                "Fronteira: em R$ 100,00 não existe desconto por valor, apenas os 5% do VIP"
	        );
	    }

	    @Test
	    void deveAplicarCupomExatamenteEmCem() {
	        assertEquals(
	                90.00,
	                Pedido.calcularTotal(100.00, false, "PRIMEIRA"),
	                DELTA,
	                "Fronteira: cupom PRIMEIRA é válido para pedidos de até R$ 100,00 inclusive"
	        );
	    }

	    @Test
	    void deveAplicarVipECupomExatamenteEmCem() {
	        assertEquals(
	                85.00,
	                Pedido.calcularTotal(100.00, true, "PRIMEIRA"),
	                DELTA,
	                "Fronteira: R$ 100,00 com VIP e PRIMEIRA deve resultar em 15% de desconto"
	        );
	    }


	    // =========================================================
	    // IMEDIATAMENTE ACIMA DE R$ 100,00
	    // Desconto base = 5%
	    // Cupom PRIMEIRA deixa de ser válido
	    // =========================================================

	    @Test
	    void deveAplicarCincoPorCentoLogoAcimaDeCem() {
	        assertEquals(
	                95.0095,
	                Pedido.calcularTotal(100.01, false, null),
	                DELTA,
	                "Fronteira: acima de R$ 100,00 deve iniciar desconto de 5%"
	        );
	    }

	    @Test
	    void naoDeveAplicarCupomLogoAcimaDeCem() {
	        assertEquals(
	                95.0095,
	                Pedido.calcularTotal(100.01, false, "PRIMEIRA"),
	                DELTA,
	                "Fronteira: PRIMEIRA não é válido para valores acima de R$ 100,00"
	        );
	    }

	    @Test
	    void deveAplicarDezPorCentoParaVipLogoAcimaDeCem() {
	        assertEquals(
	                90.009,
	                Pedido.calcularTotal(100.01, true, null),
	                DELTA,
	                "Logo acima de R$ 100,00: 5% da faixa + 5% VIP"
	        );
	    }

	    @Test
	    void cupomNaoDeveAlterarDescontoDeVipLogoAcimaDeCem() {
	        assertEquals(
	                90.009,
	                Pedido.calcularTotal(100.01, true, "PRIMEIRA"),
	                DELTA,
	                "PRIMEIRA deve ser ignorado acima de R$ 100,00"
	        );
	    }


	    // =========================================================
	    // CLASSE: ACIMA DE R$ 100 ATÉ R$ 500
	    // Desconto base = 5%
	    // =========================================================

	    @Test
	    void deveAplicarCincoPorCentoNaFaixaAteQuinhentos() {
	        assertEquals(
	                285.00,
	                Pedido.calcularTotal(300.00, false, null),
	                DELTA
	        );
	    }

	    @Test
	    void deveAplicarDezPorCentoParaVipNaFaixaAteQuinhentos() {
	        assertEquals(
	                270.00,
	                Pedido.calcularTotal(300.00, true, null),
	                DELTA
	        );
	    }


	    // =========================================================
	    // FRONTEIRA R$ 500,00
	    // Ainda pertence à faixa de 5%
	    // =========================================================

	    @Test
	    void deveAplicarCincoPorCentoExatamenteEmQuinhentos() {
	        assertEquals(
	                475.00,
	                Pedido.calcularTotal(500.00, false, null),
	                DELTA,
	                "Fronteira: R$ 500,00 ainda pertence à faixa de desconto de 5%"
	        );
	    }

	    @Test
	    void deveAplicarDezPorCentoParaVipExatamenteEmQuinhentos() {
	        assertEquals(
	                450.00,
	                Pedido.calcularTotal(500.00, true, null),
	                DELTA,
	                "Fronteira: em R$ 500,00 são 5% da faixa + 5% VIP"
	        );
	    }


	    // =========================================================
	    // IMEDIATAMENTE ACIMA DE R$ 500,00
	    // Desconto base = 10%
	    // =========================================================

	    @Test
	    void deveAplicarDezPorCentoLogoAcimaDeQuinhentos() {
	        assertEquals(
	                450.009,
	                Pedido.calcularTotal(500.01, false, null),
	                DELTA,
	                "Fronteira: acima de R$ 500,00 o desconto deve passar para 10%"
	        );
	    }

	    @Test
	    void deveAplicarQuinzePorCentoParaVipLogoAcimaDeQuinhentos() {
	        assertEquals(
	                425.0085,
	                Pedido.calcularTotal(500.01, true, null),
	                DELTA,
	                "Fronteira: 10% da faixa + 5% VIP"
	        );
	    }


	    // =========================================================
	    // CLASSE: ACIMA DE R$ 500 ATÉ R$ 1.000
	    // Desconto base = 10%
	    // =========================================================

	    @Test
	    void deveAplicarDezPorCentoNaFaixaAteMil() {
	        assertEquals(
	                675.00,
	                Pedido.calcularTotal(750.00, false, null),
	                DELTA
	        );
	    }

	    @Test
	    void deveAplicarQuinzePorCentoParaVipNaFaixaAteMil() {
	        assertEquals(
	                637.50,
	                Pedido.calcularTotal(750.00, true, null),
	                DELTA
	        );
	    }


	    // =========================================================
	    // FRONTEIRA R$ 1.000,00
	    // Ainda pertence à faixa de 10%
	    // =========================================================

	    @Test
	    void deveAplicarDezPorCentoExatamenteEmMil() {
	        assertEquals(
	                900.00,
	                Pedido.calcularTotal(1000.00, false, null),
	                DELTA,
	                "Fronteira: R$ 1.000,00 ainda pertence à faixa de 10%"
	        );
	    }

	    @Test
	    void deveAplicarQuinzePorCentoParaVipExatamenteEmMil() {
	        assertEquals(
	                850.00,
	                Pedido.calcularTotal(1000.00, true, null),
	                DELTA,
	                "Fronteira: R$ 1.000,00 com VIP resulta em 15% de desconto"
	        );
	    }


	    // =========================================================
	    // IMEDIATAMENTE ACIMA DE R$ 1.000,00
	    // Desconto base = 15%
	    // =========================================================

	    @Test
	    void deveAplicarQuinzePorCentoLogoAcimaDeMil() {
	        assertEquals(
	                850.0085,
	                Pedido.calcularTotal(1000.01, false, null),
	                DELTA,
	                "Fronteira: acima de R$ 1.000,00 deve aplicar 15%"
	        );
	    }

	    @Test
	    void deveAplicarLimiteDeVintePorCentoParaVipLogoAcimaDeMil() {
	        assertEquals(
	                800.008,
	                Pedido.calcularTotal(1000.01, true, null),
	                DELTA,
	                "Fronteira: 15% da faixa + 5% VIP atinge o limite máximo de 20%"
	        );
	    }


	    // =========================================================
	    // CLASSE: ACIMA DE R$ 1.000
	    // Desconto base = 15%
	    // =========================================================

	    @Test
	    void deveAplicarQuinzePorCentoAcimaDeMil() {
	        assertEquals(
	                1275.00,
	                Pedido.calcularTotal(1500.00, false, null),
	                DELTA
	        );
	    }

	    @Test
	    void deveAplicarVintePorCentoParaVipAcimaDeMil() {
	        assertEquals(
	                1200.00,
	                Pedido.calcularTotal(1500.00, true, null),
	                DELTA
	        );
	    }

	    @Test
	    void cupomPrimeiraNaoDeveSerAplicadoAcimaDeMil() {
	        assertEquals(
	                1275.00,
	                Pedido.calcularTotal(1500.00, false, "PRIMEIRA"),
	                DELTA
	        );
	    }

	    @Test
	    void cupomPrimeiraNaoDeveUltrapassarLimiteParaVipAcimaDeMil() {
	        assertEquals(
	                1200.00,
	                Pedido.calcularTotal(1500.00, true, "PRIMEIRA"),
	                DELTA,
	                "Cupom PRIMEIRA é inválido acima de R$ 100,00 e desconto máximo permanece em 20%"
	        );
	    }
}

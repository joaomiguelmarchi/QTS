import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

public class PedidoTest {

    private static final double DELTA = 0.001;

    @Test
    void deveRetornarMenosUmQuandoValorForZero() {
        double resultado = Pedido.calcularTotal(0, false, null);

        assertEquals(-1.0, resultado, DELTA);
    }

    @Test
    void deveRetornarMenosUmQuandoValorForNegativo() {
        double resultado = Pedido.calcularTotal(-100, false, null);

        assertEquals(-1.0, resultado, DELTA);
    }

    @Test
    void deveRetornarValorIntegralQuandoMenorQue100() {
        double resultado = Pedido.calcularTotal(50, false, null);

        assertEquals(50.0, resultado, DELTA);
    }

    @Test
    void deveAplicarCincoPorCentoQuandoValorFor100() {
        double resultado = Pedido.calcularTotal(100, false, null);

        assertEquals(95.0, resultado, DELTA);
    }

    @Test
    void deveAplicarCincoPorCentoQuandoValorEstiverEntre100E500() {
        double resultado = Pedido.calcularTotal(300, false, null);

        assertEquals(285.0, resultado, DELTA);
    }

    @Test
    void deveAplicarCincoPorCentoQuandoValorFor500() {
        double resultado = Pedido.calcularTotal(500, false, null);

        assertEquals(475.0, resultado, DELTA);
    }

    @Test
    void deveAplicarDezPorCentoQuandoValorForMaiorQue500() {
        double resultado = Pedido.calcularTotal(600, false, null);

        assertEquals(540.0, resultado, DELTA);
    }

    @Test
    void deveAplicarDezPorCentoQuandoValorFor1000() {
        double resultado = Pedido.calcularTotal(1000, false, null);

        assertEquals(900.0, resultado, DELTA);
    }

    @Test
    void deveAplicarQuinzePorCentoQuandoValorForMaiorQue1000() {
        double resultado = Pedido.calcularTotal(1200, false, null);

        assertEquals(1020.0, resultado, DELTA);
    }

    @Test
    void deveAdicionarCincoPorCentoDeDescontoParaVip() {
        double resultado = Pedido.calcularTotal(600, true, null);

        // 10% pelo valor + 5% VIP = 15%
        assertEquals(510.0, resultado, DELTA);
    }

    @Test
    void deveAplicarDescontoVipEmValorMenorQue100() {
        double resultado = Pedido.calcularTotal(50, true, null);

        // 5% VIP
        assertEquals(47.5, resultado, DELTA);
    }

    @Test
    void deveAplicarCupomPrimeira() {
        double resultado = Pedido.calcularTotal(50, false, "PRIMEIRA");

        // 10% do cupom
        assertEquals(45.0, resultado, DELTA);
    }

    @Test
    void naoDeveAplicarDescontoParaCupomInvalido() {
        double resultado = Pedido.calcularTotal(50, false, "OUTRO");

        assertEquals(50.0, resultado, DELTA);
    }

    @Test
    void deveAceitarCupomNulo() {
        double resultado = Pedido.calcularTotal(50, false, null);

        assertEquals(50.0, resultado, DELTA);
    }

    @Test
    void deveCombinarDescontoVipECupom() {
        double resultado = Pedido.calcularTotal(600, true, "PRIMEIRA");

        // 10% pelo valor + 5% VIP + 10% cupom = 25%
        assertEquals(450.0, resultado, DELTA);
    }

    @Test
    void deveCombinarDescontoDeValorVipECupom() {
        double resultado = Pedido.calcularTotal(1200, true, "PRIMEIRA");

        // 15% pelo valor + 5% VIP = 20%
        // depois + 10% PRIMEIRA = 30%
        assertEquals(840.0, resultado, DELTA);
    }
    
    
    @Test
    @DisplayName("AUD-1 R$100 EXATOS não tem desconto (valor limite)")
    void limite100() {
        assertEquals(
            100.0,
            Pedido.calcularTotal(100, false, null),
            DELTA,
            "Até R$100 INCLUSIVE não há desconto por valor"
        );
    }

    @Test
    @DisplayName("AUD-2 PRIMEIRA não é válido acima de R$100")
    void cupomPrimeiraAcimaDe100() {
        assertEquals(
            285.0,
            Pedido.calcularTotal(300, false, "PRIMEIRA"),
            DELTA,
            "Acima de R$100 o cupom PRIMEIRA deve ser ignorado"
        );
    }

    @Test
    @DisplayName("AUD-3 VIP com PRIMEIRA em pedido até R$100")
    void vipComCupomPrimeiraValido() {
        assertEquals(
            42.5,
            Pedido.calcularTotal(50, true, "PRIMEIRA"),
            DELTA,
            "Em pedido de até R$100: 5% VIP + 10% PRIMEIRA = 15%"
        );
    }

    @Test
    @DisplayName("AUD-4 desconto total nunca ultrapassa 20%")
    void tetoDeDesconto20PorCento() {
        assertEquals(
            960.0,
            Pedido.calcularTotal(1200, true, null),
            DELTA,
            "15% pelo valor + 5% VIP = teto máximo de 20%"
        );
    }
}
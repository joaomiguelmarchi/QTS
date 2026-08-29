import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MelhorPedidoTest {


    private static final double DELTA = 0.001;

    // ============================================================
    // CLASSE DE EQUIVALÊNCIA 1
    // VALOR INVÁLIDO: valor <= 0
    // ============================================================

    @Test
    void deveRetornarMenosUmParaValorNegativo() {
        assertEquals(
                -1.0,
                Pedido.calcularTotal(-10.0, false, null),
                DELTA
        );
    }

    @Test
    void deveRetornarMenosUmParaValorNegativoMesmoComVipECupom() {
        assertEquals(
                -1.0,
                Pedido.calcularTotal(-10.0, true, "PRIMEIRA"),
                DELTA
        );
    }

    @Test
    void deveRetornarMenosUmParaValorZero() {
        assertEquals(
                -1.0,
                Pedido.calcularTotal(0.0, false, null),
                DELTA,
                "Fronteira inferior: valor exatamente zero deve ser considerado inválido"
        );
    }

    @Test
    void deveRetornarMenosUmParaValorZeroMesmoComVipECupom() {
        assertEquals(
                -1.0,
                Pedido.calcularTotal(0.0, true, "PRIMEIRA"),
                DELTA,
                "Valor zero deve continuar inválido independentemente de VIP e cupom"
        );
    }

    @Test
    void deveAceitarValorImediatamenteAcimaDeZero() {
        assertEquals(
                0.01,
                Pedido.calcularTotal(0.01, false, null),
                DELTA,
                "Valor imediatamente acima de zero deve ser válido"
        );
    }


    // ============================================================
    // CLASSE DE EQUIVALÊNCIA 2
    // 0 < valor <= 100
    //
    // Desconto base = 0%
    // VIP = +5%
    // PRIMEIRA = +10%
    // ============================================================

    @Test
    void deveManterValorAbaixoDeCemSemVipSemCupom() {
        assertEquals(
                50.0,
                Pedido.calcularTotal(50.0, false, null),
                DELTA
        );
    }

    @Test
    void deveAplicarCincoPorCentoAbaixoDeCemParaVip() {
        assertEquals(
                47.5,
                Pedido.calcularTotal(50.0, true, null),
                DELTA
        );
    }

    @Test
    void deveAplicarDezPorCentoAbaixoDeCemComCupomPrimeira() {
        assertEquals(
                45.0,
                Pedido.calcularTotal(50.0, false, "PRIMEIRA"),
                DELTA
        );
    }

    @Test
    void deveAplicarQuinzePorCentoAbaixoDeCemParaVipComCupomPrimeira() {
        assertEquals(
                42.5,
                Pedido.calcularTotal(50.0, true, "PRIMEIRA"),
                DELTA
        );
    }

    @Test
    void cupomDiferenteDePrimeiraNaoDeveAplicarDesconto() {
        assertEquals(
                50.0,
                Pedido.calcularTotal(50.0, false, "OUTRO"),
                DELTA
        );
    }

    @Test
    void cupomDiferenteDePrimeiraComVipDeveAplicarSomenteDescontoVip() {
        assertEquals(
                47.5,
                Pedido.calcularTotal(50.0, true, "OUTRO"),
                DELTA
        );
    }


    // ============================================================
    // ANÁLISE DE VALOR LIMITE: R$ 100,00
    //
    // 99,99  -> sem desconto base
    // 100,00 -> sem desconto base
    // 100,01 -> 5%
    //
    // PRIMEIRA é válido até 100 inclusive.
    // ============================================================

    @Test
    void deveManterValorImediatamenteAbaixoDeCem() {
        assertEquals(
                99.99,
                Pedido.calcularTotal(99.99, false, null),
                DELTA,
                "R$ 99,99 pertence à classe sem desconto por valor"
        );
    }

    @Test
    void deveAplicarCupomImediatamenteAbaixoDeCem() {
        assertEquals(
                89.991,
                Pedido.calcularTotal(99.99, false, "PRIMEIRA"),
                DELTA,
                "Cupom PRIMEIRA deve ser válido abaixo de R$ 100,00"
        );
    }

    @Test
    void deveAplicarVipECupomImediatamenteAbaixoDeCem() {
        assertEquals(
                84.9915,
                Pedido.calcularTotal(99.99, true, "PRIMEIRA"),
                DELTA,
                "Abaixo de R$ 100,00 VIP + PRIMEIRA devem totalizar 15% de desconto"
        );
    }

    @Test
    void deveManterValorExatamenteEmCem() {
        assertEquals(
                100.0,
                Pedido.calcularTotal(100.0, false, null),
                DELTA,
                "Fronteira R$ 100,00: ainda pertence à faixa sem desconto"
        );
    }

    @Test
    void deveAplicarSomenteVipExatamenteEmCem() {
        assertEquals(
                95.0,
                Pedido.calcularTotal(100.0, true, null),
                DELTA,
                "Fronteira R$ 100,00: deve aplicar apenas os 5% de VIP"
        );
    }

    @Test
    void deveAplicarCupomPrimeiraExatamenteEmCem() {
        assertEquals(
                90.0,
                Pedido.calcularTotal(100.0, false, "PRIMEIRA"),
                DELTA,
                "Fronteira R$ 100,00: o cupom PRIMEIRA ainda deve ser válido"
        );
    }

    @Test
    void deveAplicarVipECupomPrimeiraExatamenteEmCem() {
        assertEquals(
                85.0,
                Pedido.calcularTotal(100.0, true, "PRIMEIRA"),
                DELTA,
                "Fronteira R$ 100,00: VIP + PRIMEIRA devem totalizar 15% de desconto"
        );
    }

    @Test
    void deveAplicarCincoPorCentoImediatamenteAcimaDeCem() {
        assertEquals(
                95.0095,
                Pedido.calcularTotal(100.01, false, null),
                DELTA,
                "Acima de R$ 100,00 deve iniciar a faixa de 5% de desconto"
        );
    }

    @Test
    void naoDeveAplicarCupomPrimeiraImediatamenteAcimaDeCem() {
        assertEquals(
                95.0095,
                Pedido.calcularTotal(100.01, false, "PRIMEIRA"),
                DELTA,
                "Cupom PRIMEIRA não deve ser válido acima de R$ 100,00"
        );
    }

    @Test
    void deveAplicarDezPorCentoParaVipImediatamenteAcimaDeCem() {
        assertEquals(
                90.009,
                Pedido.calcularTotal(100.01, true, null),
                DELTA,
                "Acima de R$ 100,00: 5% por valor + 5% VIP"
        );
    }

    @Test
    void cupomPrimeiraNaoDeveAlterarVipImediatamenteAcimaDeCem() {
        assertEquals(
                90.009,
                Pedido.calcularTotal(100.01, true, "PRIMEIRA"),
                DELTA,
                "Cupom PRIMEIRA deve ser ignorado acima de R$ 100,00"
        );
    }


    // ============================================================
    // CLASSE DE EQUIVALÊNCIA 3
    // 100 < valor <= 500
    // Desconto base = 5%
    // ============================================================

    @Test
    void deveAplicarCincoPorCentoNaFaixaEntreCemEQuinhentos() {
        assertEquals(
                285.0,
                Pedido.calcularTotal(300.0, false, null),
                DELTA
        );
    }

    @Test
    void deveAplicarDezPorCentoParaVipNaFaixaEntreCemEQuinhentos() {
        assertEquals(
                270.0,
                Pedido.calcularTotal(300.0, true, null),
                DELTA
        );
    }

    @Test
    void deveIgnorarCupomPrimeiraNaFaixaEntreCemEQuinhentos() {
        assertEquals(
                285.0,
                Pedido.calcularTotal(300.0, false, "PRIMEIRA"),
                DELTA
        );
    }

    @Test
    void deveIgnorarCupomPrimeiraMantendoDescontoVipNaFaixaEntreCemEQuinhentos() {
        assertEquals(
                270.0,
                Pedido.calcularTotal(300.0, true, "PRIMEIRA"),
                DELTA
        );
    }


    // ============================================================
    // ANÁLISE DE VALOR LIMITE: R$ 500,00
    // ============================================================

    @Test
    void deveAplicarCincoPorCentoImediatamenteAbaixoDeQuinhentos() {
        assertEquals(
                474.9905,
                Pedido.calcularTotal(499.99, false, null),
                DELTA,
                "R$ 499,99 ainda pertence à faixa de 5%"
        );
    }

    @Test
    void deveAplicarCincoPorCentoExatamenteEmQuinhentos() {
        assertEquals(
                475.0,
                Pedido.calcularTotal(500.0, false, null),
                DELTA,
                "Fronteira R$ 500,00: deve permanecer na faixa de 5%"
        );
    }

    @Test
    void deveAplicarDezPorCentoParaVipExatamenteEmQuinhentos() {
        assertEquals(
                450.0,
                Pedido.calcularTotal(500.0, true, null),
                DELTA,
                "Fronteira R$ 500,00: 5% da faixa + 5% VIP"
        );
    }

    @Test
    void deveAplicarDezPorCentoImediatamenteAcimaDeQuinhentos() {
        assertEquals(
                450.009,
                Pedido.calcularTotal(500.01, false, null),
                DELTA,
                "Acima de R$ 500,00 deve iniciar a faixa de 10%"
        );
    }

    @Test
    void deveAplicarQuinzePorCentoParaVipImediatamenteAcimaDeQuinhentos() {
        assertEquals(
                425.0085,
                Pedido.calcularTotal(500.01, true, null),
                DELTA,
                "Acima de R$ 500,00: 10% da faixa + 5% VIP"
        );
    }


    // ============================================================
    // CLASSE DE EQUIVALÊNCIA 4
    // 500 < valor <= 1000
    // Desconto base = 10%
    // ============================================================

    @Test
    void deveAplicarDezPorCentoNaFaixaEntreQuinhentosEMil() {
        assertEquals(
                675.0,
                Pedido.calcularTotal(750.0, false, null),
                DELTA
        );
    }

    @Test
    void deveAplicarQuinzePorCentoParaVipNaFaixaEntreQuinhentosEMil() {
        assertEquals(
                637.5,
                Pedido.calcularTotal(750.0, true, null),
                DELTA
        );
    }

    @Test
    void deveIgnorarCupomPrimeiraNaFaixaEntreQuinhentosEMil() {
        assertEquals(
                675.0,
                Pedido.calcularTotal(750.0, false, "PRIMEIRA"),
                DELTA
        );
    }

    @Test
    void deveIgnorarCupomPrimeiraComVipNaFaixaEntreQuinhentosEMil() {
        assertEquals(
                637.5,
                Pedido.calcularTotal(750.0, true, "PRIMEIRA"),
                DELTA
        );
    }


    // ============================================================
    // ANÁLISE DE VALOR LIMITE: R$ 1.000,00
    // ============================================================

    @Test
    void deveAplicarDezPorCentoImediatamenteAbaixoDeMil() {
        assertEquals(
                899.991,
                Pedido.calcularTotal(999.99, false, null),
                DELTA,
                "R$ 999,99 ainda pertence à faixa de 10%"
        );
    }

    @Test
    void deveAplicarDezPorCentoExatamenteEmMil() {
        assertEquals(
                900.0,
                Pedido.calcularTotal(1000.0, false, null),
                DELTA,
                "Fronteira R$ 1.000,00: ainda pertence à faixa de 10%"
        );
    }

    @Test
    void deveAplicarQuinzePorCentoParaVipExatamenteEmMil() {
        assertEquals(
                850.0,
                Pedido.calcularTotal(1000.0, true, null),
                DELTA,
                "Fronteira R$ 1.000,00: 10% da faixa + 5% VIP"
        );
    }

    @Test
    void deveAplicarQuinzePorCentoImediatamenteAcimaDeMil() {
        assertEquals(
                850.0085,
                Pedido.calcularTotal(1000.01, false, null),
                DELTA,
                "Acima de R$ 1.000,00 deve iniciar a faixa de 15%"
        );
    }

    @Test
    void deveAplicarLimiteMaximoDeVintePorCentoImediatamenteAcimaDeMilParaVip() {
        assertEquals(
                800.008,
                Pedido.calcularTotal(1000.01, true, null),
                DELTA,
                "Acima de R$ 1.000,00 com VIP: 15% + 5% = limite máximo de 20%"
        );
    }


    // ============================================================
    // CLASSE DE EQUIVALÊNCIA 5
    // valor > 1000
    //
    // Desconto base = 15%
    // VIP = limite de 20%
    // PRIMEIRA deve ser ignorado
    // ============================================================

    @Test
    void deveAplicarQuinzePorCentoAcimaDeMil() {
        assertEquals(
                1275.0,
                Pedido.calcularTotal(1500.0, false, null),
                DELTA
        );
    }

    @Test
    void deveAplicarVintePorCentoParaVipAcimaDeMil() {
        assertEquals(
                1200.0,
                Pedido.calcularTotal(1500.0, true, null),
                DELTA
        );
    }

    @Test
    void deveIgnorarCupomPrimeiraAcimaDeMil() {
        assertEquals(
                1275.0,
                Pedido.calcularTotal(1500.0, false, "PRIMEIRA"),
                DELTA
        );
    }

    @Test
    void deveManterLimiteDeVintePorCentoParaVipComCupomInvalidoAcimaDeMil() {
        assertEquals(
                1200.0,
                Pedido.calcularTotal(1500.0, true, "PRIMEIRA"),
                DELTA,
                "Acima de R$ 100,00 PRIMEIRA deve ser ignorado e o desconto VIP não pode superar 20%"
        );
    }

    @Test
    void cupomQualquerNaoDeveAlterarValorAcimaDeMil() {
        assertEquals(
                1275.0,
                Pedido.calcularTotal(1500.0, false, "ABC"),
                DELTA
        );
    }


    // ============================================================
    // COMBINAÇÕES ADICIONAIS / ROBUSTEZ
    // ============================================================

    @Test
    void cupomNullDeveSerAceitoSemLancarExcecao() {
        assertEquals(
                100.0,
                Pedido.calcularTotal(100.0, false, null),
                DELTA
        );
    }

    @Test
    void cupomVazioNaoDeveConcederDesconto() {
        assertEquals(
                100.0,
                Pedido.calcularTotal(100.0, false, ""),
                DELTA
        );
    }

    @Test
    void cupomPrimeiraMinusculoNaoDeveSerConsideradoValido() {
        assertEquals(
                100.0,
                Pedido.calcularTotal(100.0, false, "primeira"),
                DELTA
        );
    }

    @Test
    void cupomPrimeiraComEspacosNaoDeveSerConsideradoValido() {
        assertEquals(
                100.0,
                Pedido.calcularTotal(100.0, false, " PRIMEIRA "),
                DELTA
        );
    }
}

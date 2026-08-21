public class Frete {

    public static double calcular(double pesoKg, double valorCompra) {

        if (valorCompra > 200)
            return 0.0;

        if (pesoKg <= 0 || pesoKg > 30)
            return -1;

        if (pesoKg <= 5)
            return 10.0;

        if (pesoKg <= 10)
            return 18.0;

        return 25.0;
    }
}
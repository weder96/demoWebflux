package com.webflux.demowebflux.Enumeradores;

public enum CodigoEnvioWebService {

    B4(33, "B4"), B6(2, "B6"), B8(3, "B8"), B10(4, "B10"), B11(5, "B11");

    private final int valor;
    private final String descricao;

    CodigoEnvioWebService(int valorOpcao, String descricao) {
            this.valor = valorOpcao;
            this.descricao = descricao;
    }

    public int getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }
}

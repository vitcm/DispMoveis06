package com.example.ex06;

import java.util.Date;

public class Entidade {
    private String nome;
    private String descricao;
    private Date avistamento;

    public Entidade(String nome, String descricao, Date avistamento) {
        this.nome = nome;
        this.descricao = descricao;
        this.avistamento = avistamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getAvistamento() {
        return avistamento;
    }

    public void setAvistamento(Date avistamento) {
        this.avistamento = avistamento;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

/**
 *
 * @author guilherme.oliveira
 */
public class Venda {
    
    private double valorFaturamentoDireto;
    private double valorFaturamentoDolphin;
    private double valorComissao;

    public Venda() {
        this.valorFaturamentoDireto = 0.00;
        this.valorFaturamentoDolphin = 0.00;
        this.valorComissao = 0.00;
    }

    public Venda(double valorFaturamentoDireto, double valorFaturamentoDolphin) {
        this.valorFaturamentoDireto = valorFaturamentoDireto;
        this.valorFaturamentoDolphin = valorFaturamentoDolphin;
    }

    public double getValorFaturamentoDireto() {
        return valorFaturamentoDireto;
    }

    public void setValorFaturamentoDireto(double valorFaturamentoDireto) {
        this.valorFaturamentoDireto = valorFaturamentoDireto;
    }

    public double getValorFaturamentoDolphin() {
        return valorFaturamentoDolphin;
    }

    public void setValorFaturamentoDolphin(double valorFaturamentoDolphin) {
        this.valorFaturamentoDolphin = valorFaturamentoDolphin;
    }

    public double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(double valorComissao) {
        this.valorComissao = valorComissao;
    }

    public double calcularValorTotal() {
        return valorFaturamentoDireto + valorFaturamentoDolphin;
    }
}

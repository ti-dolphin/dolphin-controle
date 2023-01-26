/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.apontamento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.apontamento.Apontamento;
import services.ApontamentoService;

/**
 *
 * @author ti
 */
public class ApontamentoTest {

    public static void main(String[] args) {
//        testaSeBuscaDadosDoBanco();
//        testaSeFiltraProblemasDeApontamento();
        testaPeriodoAtual();
//        testaPeriodoAnterior();
    }

    private static void testaSeBuscaDadosDoBanco() {
        try {
            ApontamentoService apontamentoService = new ApontamentoService();
            String query = "";
            ArrayList<Apontamento> apontamentos = (ArrayList<Apontamento>) apontamentoService.filtrarApontamentos(query);
        } catch (SQLException ex) {
            Logger.getLogger(ApontamentoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void testaSeFiltraProblemasDeApontamento() {
        try {
            ApontamentoService apontamentoService = new ApontamentoService();
            String query = "";
            ArrayList<Apontamento> apontamentos = (ArrayList<Apontamento>) apontamentoService.filtrarProblemasDeApontamento(query);

            for (Apontamento apontamento : apontamentos) {
                System.out.println(apontamento.getCodApont());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ApontamentoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void testaPeriodoAtual() {
        LocalDate hoje = LocalDate.of(2023, 12, 11);
        int mesAtual = hoje.getMonthValue();
        int proximoMes = mesAtual + 1;
        int anoAtual = hoje.getYear();
        int proximoAno = hoje.getYear() + 1;

        String de = LocalDate.of(anoAtual, mesAtual, 16).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String ate;
        
        if (proximoMes > 12) {
            ate = LocalDate.of(proximoAno, 1, 15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            ate = LocalDate.of(anoAtual, proximoMes, 15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        System.out.println("de: " + de);
        System.out.println("ate: " + ate);
    }

    private static void testaPeriodoAnterior() {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int mesAnterior = mesAtual - 1;
        int anoAtual = hoje.getYear();
        int anoAnterior = anoAtual - 1;

        String de;
        String ate;

        if (mesAnterior < 1) {
            de = LocalDate.of(anoAnterior, 12, 16).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            de = LocalDate.of(anoAtual, mesAnterior, 16).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        ate = LocalDate.of(anoAtual, mesAtual, 15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("de: " + de);
        System.out.println("ate: " + ate);
    }
}

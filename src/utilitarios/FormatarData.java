/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author guilherme.oliveira
 */
public class FormatarData {

    /**
     * Método usado para formatar data do formato 14/12/2018 para o 2018-12-14
     *
     * @param data
     * @return dataFormatada
     */
    public static String formatarData(String data) throws Exception {
        if (data.equals("")) {
            throw new Exception("Não foi possível converter data vazia");
        }
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        String dataFormatada = ano + "-" + mes + "-" + dia;
        return dataFormatada;
    }

    /**
     * Método usado para formatar data no padrão especificado. Ex.: 2018-01-07
     * -> 07/01/2018 (No padrao dd/MM/yyyy)
     *
     * @param data
     * @param padrao
     * @return dataEHoraFormatado
     */
    public static String formatarDataEmTexto(LocalDate data, String padrao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);

        String dataFormatada = data.format(formatter);
        return dataFormatada;
    }

    /**
     * Método usado para formatar data e hora no padrão especificado. Ex.:
     * 2018-01-07T16:11:26.485 -> 07/01/2018 16:11:26 (No padrao dd/MM/yyyy HH:mm:ss)
     *
     * @param dataEHora
     * @param padrao
     * @return dataEHoraFormatado
     */
    public static String formatarDataEHoraEmTexto(LocalDateTime dataEHora, String padrao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);

        String dataEHoraFormatado = dataEHora.format(formatter);

        return dataEHoraFormatado;
    }

    /**
     * Método usado para converter data e hora no tipo String em LocalDateTime
     *
     * @param dataEHora
     * @param padrao
     * @return dataEHoraFormatada
     */
    public static LocalDateTime converterTextoEmDataEHora(String dataEHora, String padrao) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);
        LocalDateTime dataEHoraFormatada = LocalDateTime.parse(dataEHora, formatter);

        return dataEHoraFormatada;
    }

    /**
     * Método usado para converter data no tipo String em LocalDate
     *
     * @param data
     * @param padrao
     * @return dataFormatada
     */
    public static LocalDate converterTextoEmData(String data, String padrao) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);
        LocalDate dataFormatada = LocalDate.parse(data, formatter);

        return dataFormatada;
    }

    /**
     * Método usado para converter data no tipo Calendar em LocalDate
     *
     * @param calendar
     * @return dataFormatada
     */
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }

}

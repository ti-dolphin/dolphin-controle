/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author guilherme.oliveira
 */
public class RetornosException extends Exception{
    int retorno;

    public RetornosException(int retorno) {
        this.retorno = retorno;
    }
    
    public String mensagens(int retorno) {
        
        String msg = "";

        switch (retorno) {
            case 0:
                msg = "COMANDO NÃO EXECUTADO!";
                break;
            case 1:
                msg = "COMANDO EXECUTADO COM SUCESSO";
                break;
            case -1:
                msg = "LEITOR INCOMPATIVEL COM SDK";
                break;
            case -2:
                msg = "DIGITAIS NÃO SÃO IGUAIS";
                break;
            case -10:
                msg = "ERRO DESCONHECIDO";
                break;
            case -11:
                msg = "FALTA DE MEMORIA";
                break;
            case -12:
                msg = "ARGUMENTO INVALIDO";
                break;
            case -13:
                msg = "LEITOR EM USO";
                break;
            case -14:
                msg = "TEMPLATE INVALIDO";
                break;
            case -15:
                msg = "ERRO INTERNO";
                break;
            case -16:
                msg = "NAO HABILITADO PARA CAPTURAR DIGITAL";
                break;
            case -17:
                msg = "CANCELADO PELO USUARIO";
                break;
            case -18:
                msg = "LEITURA NÃO POSSIVEL";
                break;
            case -21:
                msg = "ERRO DESCONHECIDO";
                break;
            case -22:
                msg = "SDK NÃO FOI INICIADO";
                break;
            case -23:
                msg = "LEITOR NÃO CONECTADO";
                break;
            case -24:
                msg = "ERRO NO LEITOR";
                break;
            case -25:
                msg = "FRAME DE DADOS VAZIO";
                break;
            case -26:
                msg = "ORIGEM FALSA (FAKE)";
                break;
            case -27:
                msg = "HARDWARE INCOMPATIVEL";
                break;
            case -28:
                msg = "FIRMWARE INCOMPATIVEL";
                break;
            case -29:
                msg = "FRAME ALTERADO";
                break;
            default:
                break;
        }

        return msg;
    }

    @Override
    public String toString() {
        return mensagens(retorno);
    }
}

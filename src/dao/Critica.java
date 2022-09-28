/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class Critica {

    /**
     * Método usado para testar se pode ser excluído algum registro de alguma tabela
     * @param tabela
     * @param colunaPK
     * @param codigo
     * @param msg
     * @return boolean
     * @throws SQLException 
     */
    public static boolean podeExcluir(String tabela, String colunaPK, int codigo, String msg) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        String sql = "SELECT MAX(" + colunaPK + ") FROM " + tabela + " where " + colunaPK + " = " + codigo;

        ResultSet rs;
        rs = stat.executeQuery(sql);

        if (rs != null && rs.next()) {
            if (rs.getInt("MAX(" + colunaPK + ")") > 0) {
                JOptionPane.showMessageDialog(null, 
                        "O registro não pode ser excluído pois está em uso no " + msg, 
                        "Aviso ao excluir registro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}

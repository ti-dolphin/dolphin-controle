/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class Carregar implements Runnable {

    @Override
    public void run() {
        Menu.jpbBarraProgresso.setVisible(true);
        Menu.jpbBarraProgresso.setIndeterminate(true);
    }
}

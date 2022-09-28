/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import exceptions.CampoEmBrancoException;

/**
 *
 * @author guilh
 */
public class Contato {
    
    private int id;
    private String nome;
    private String email;
    private String telefone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws CampoEmBrancoException {
        if (!nome.isEmpty()) {
            this.nome = nome;
        } else {
            throw new CampoEmBrancoException("Campo nome não pode ser vazio!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws CampoEmBrancoException {
        if (!email.isEmpty()) {
            this.email = email;
        } else {
            throw new CampoEmBrancoException("Campo e-mail não pode ser vazio!");
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws CampoEmBrancoException {
        if (!telefone.isEmpty()) {
            this.telefone = telefone;
        } else {
            throw new CampoEmBrancoException("Campo telefone não pode ser vazio!");
        }
    }
    
    
}

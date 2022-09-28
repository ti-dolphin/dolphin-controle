package services;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioServicos {

    public void cadastrarAutenticacao(Funcionario f) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        fDAO.cadastrarAutenticacao(f);
    }//fecha FuncionarioServicos

    public ArrayList<Funcionario> buscarFuncionario() throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        return fDAO.buscarFuncionario();
    }//fecha buscarFuncionario

    public ArrayList<Funcionario> buscarFuncionariosAtivos() throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        return fDAO.buscarFuncionariosAtivos();
    }//fecha buscarFuncionarioAtivos

    public ArrayList<Funcionario> filtarFuncionario(String query) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        return fDAO.filtarFuncionario(query);
    }//fecha filtarFuncionario

    public ArrayList<Funcionario> buscarFuncionariosPJ() throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        return fDAO.buscarFuncionariosPJ();
    }

}

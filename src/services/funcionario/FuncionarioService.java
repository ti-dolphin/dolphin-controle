package services.funcionario;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioService {

    public void cadastrarAutenticacao(Funcionario f) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        fDAO.cadastrarAutenticacao(f);
    }//fecha FuncionarioServicos

    public ArrayList<Funcionario> filtarFuncionarios(String query) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        return fDAO.filtrarFuncionarios(query);
    }//fecha filtarFuncionario

    public ArrayList<Funcionario> buscarFuncionariosPJ() throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionario();
        return fDAO.buscarFuncionariosPJ();
    }

}

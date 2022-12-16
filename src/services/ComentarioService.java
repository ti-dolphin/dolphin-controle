/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.apontamento.ComentarioDAO;
import java.sql.SQLException;
import java.util.List;
import model.apontamento.Comentario;

/**
 *
 * @author ti
 */
public class ComentarioService {

    private ComentarioDAO comentarioDAO;

    public ComentarioService() {
        this.comentarioDAO = new ComentarioDAO();
    }
    
    public Comentario inserir(Comentario comentario) throws SQLException {
        int idComentario = comentarioDAO.salvar(comentario);
        
        return comentarioDAO.buscarComentarioApontamentoPorId(idComentario);
    }
    
    public void excluir(int comentarioId) throws SQLException {
        comentarioDAO.deletar(comentarioId);
    }
    
    public List<Comentario> buscarComentariosDosApontamentos(int codApont) throws SQLException {
        return comentarioDAO.buscarComentApont(codApont);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import controllers.OrdemServicoContatoController;
import dao.apontamento.ComentarioDAO;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Contato;
import model.MovimentoItem;
import model.apontamento.Apontamento;
import model.apontamento.Comentario;
import model.os.OrdemServico;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author guilherme.oliveira
 */
public class ExportaExcel {

    String fileName;
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("FirstSheet");
    HSSFRow rowhead = sheet.createRow((short) 0);

    public ExportaExcel(String fileName) {
        this.fileName = fileName;

    }

    public String getFileName() {
        return fileName;
    }

    public void exportarItensMovimentados(ArrayList<MovimentoItem> itensMovimentados) throws Exception {
        rowhead.createCell(0).setCellValue("Data");
        rowhead.createCell(1).setCellValue("Produto");
        rowhead.createCell(2).setCellValue("Patrimônio");
        rowhead.createCell(3).setCellValue("Estoque de Origem");
        rowhead.createCell(4).setCellValue("Estoque de Destino");
        rowhead.createCell(5).setCellValue("Responsável");

        for (int i = 0; i < itensMovimentados.size(); i++) {
            MovimentoItem item = itensMovimentados.get(i);
            HSSFRow row = sheet.createRow((short) i + 1);
            row.createCell(0).setCellValue(item.getMovimento().getDataEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            row.createCell(1).setCellValue(item.getProduto().getNomeFantasia());
            row.createCell(2).setCellValue(item.getPatrimonio().getDescricao());
            row.createCell(3).setCellValue(item.getMovimento().getLocalDeEstoqueOrigem().getNome());
            row.createCell(4).setCellValue(item.getMovimento().getLocalDeEstoqueDestino().getNome());
            row.createCell(5).setCellValue(item.getMovimento().getResponsavel().getNome());
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Seu arquivo excel foi gerado!");

    }

    public void exportarApontamentos(ArrayList<Apontamento> apontamentos) throws Exception {

        rowhead.createCell(0).setCellValue("Assiduidade");
        rowhead.createCell(1).setCellValue("Comentado");
        rowhead.createCell(2).setCellValue("Chapa");
        rowhead.createCell(3).setCellValue("Funcionário");
        rowhead.createCell(4).setCellValue("Função");
        rowhead.createCell(5).setCellValue("Data");
        rowhead.createCell(6).setCellValue("Gerente");
        rowhead.createCell(7).setCellValue("Centro de Custo");
        rowhead.createCell(8).setCellValue("Status");
        rowhead.createCell(9).setCellValue("Líder");
        rowhead.createCell(10).setCellValue("Atividade");
        rowhead.createCell(11).setCellValue("Situação");
        rowhead.createCell(12).setCellValue("Nº OS/Tarefa");

        // definindo seus valores
        for (int i = 0; i < apontamentos.size(); i++) {
            Apontamento apontamento = apontamentos.get(i);
            HSSFRow row = sheet.createRow((short) i + 1);
            row.createCell(0).setCellValue(apontamento.isAssiduidade() ? "Sim" : "Não");
            row.createCell(1).setCellValue(apontamento.isComentado() ? "Sim" : "Não");
            row.createCell(2).setCellValue(apontamento.getFuncionario().getChapa());
            row.createCell(3).setCellValue(apontamento.getFuncionario().getNome());
            row.createCell(4).setCellValue(apontamento.getFuncionario().getFuncao().getNome());
            row.createCell(5).setCellValue(apontamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            row.createCell(6).setCellValue(apontamento.getGerente().getNome());
            row.createCell(7).setCellValue(apontamento.getCentroCusto().getNome());
            row.createCell(8).setCellValue(apontamento.getStatusApont().getCodStatusApont());
            row.createCell(9).setCellValue(apontamento.getLider().getNome());
            row.createCell(10).setCellValue(apontamento.getAtividade());
            row.createCell(11).setCellValue(apontamento.getSituacao().getCodSituacao());
            row.createCell(12).setCellValue(apontamento.getOrdemServico().getCodOs());
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Seu arquivo excel foi gerado!");
    }

    public void exportarTomadores(ArrayList<Apontamento> apontamentos) throws Exception {
        rowhead.createCell(0).setCellValue("Colaborador");
        rowhead.createCell(1).setCellValue("Chapa");
        rowhead.createCell(2).setCellValue("Centro de Custo");
        rowhead.createCell(3).setCellValue("CNO");
        rowhead.createCell(4).setCellValue("Quantidade de Dias");
        rowhead.createCell(5).setCellValue("%");

        for (int i = 0; i < apontamentos.size(); i++) {
            Apontamento apontamento = apontamentos.get(i);
            HSSFRow row = sheet.createRow((short) i + 1);
            row.createCell(0).setCellValue(apontamento.getFuncionario().getNome());
            row.createCell(1).setCellValue(apontamento.getFuncionario().getChapa());
            row.createCell(2).setCellValue(apontamento.getCentroCusto().getNome());
            row.createCell(3).setCellValue(apontamento.getCentroCusto().getCodReduzido());
            row.createCell(4).setCellValue(apontamento.getQuantidadeDias());
            row.createCell(5).setCellValue(apontamento.getPorcentagemDeDiasTrabalhados());
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Seu arquivo excel foi gerado!");

    }

    public void exportarVendas(ArrayList<OrdemServico> tarefas) throws Exception {
        
        final int COLUNA_PRINCIPAL = 0;
        final int COLUNA_SIGLA = 1;
        final int COLUNA_PROJETO = 2;
        final int COLUNA_ADICIONAL = 3;
        final int COLUNA_CLIENTE = 4;
        final int COLUNA_TAREFA = 5;
        final int COLUNA_FATURAMENTO_DIRETO = 6;
        final int COLUNA_FATURAMENTO_DOLPHIN = 7;
        final int COLUNA_VALOR_CONTRATO = 8;
        final int COLUNA_CLASSIFICACAO = 9;
        final int COLUNA_STATUS = 10;
        final int COLUNA_DATA_SOLICITACAO = 11;
        final int COLUNA_DATA_INICIO = 12;
        final int COLUNA_DATA_PREVISAO_FECHAMENTO = 13;
        final int COLUNA_DATA_FECHAMENTO = 14;
        final int COLUNA_RESPONSAVEL = 15;
        
        //cria cabeçalho
        rowhead.createCell(COLUNA_PRINCIPAL).setCellValue("Principal");
        rowhead.createCell(COLUNA_SIGLA).setCellValue("Inicio/Fechamento");
        rowhead.createCell(COLUNA_PROJETO).setCellValue("Projeto");
        rowhead.createCell(COLUNA_ADICIONAL).setCellValue("Adicional");
        rowhead.createCell(COLUNA_CLIENTE).setCellValue("Cliente");
        rowhead.createCell(COLUNA_TAREFA).setCellValue("OS/Tarefa");
        rowhead.createCell(COLUNA_FATURAMENTO_DIRETO).setCellValue("Faturamento direto");
        rowhead.createCell(COLUNA_FATURAMENTO_DOLPHIN).setCellValue("Faturamento Dolphin");
        rowhead.createCell(COLUNA_VALOR_CONTRATO).setCellValue("Valor do Contrato");
        rowhead.createCell(COLUNA_CLASSIFICACAO).setCellValue("Classificação ABC");
        rowhead.createCell(COLUNA_STATUS).setCellValue("Status");
        rowhead.createCell(COLUNA_DATA_SOLICITACAO).setCellValue("Solicitação");
        rowhead.createCell(COLUNA_DATA_INICIO).setCellValue("Data de Início");
        rowhead.createCell(COLUNA_DATA_PREVISAO_FECHAMENTO).setCellValue("Prev. de Fechamento");
        rowhead.createCell(COLUNA_DATA_FECHAMENTO).setCellValue("Data de Fechamento");
        rowhead.createCell(COLUNA_RESPONSAVEL).setCellValue("Responsável");
        for (int i = 0; i < tarefas.size(); i++) {
            OrdemServico tarefa = tarefas.get(i);
            HSSFRow row = sheet.createRow((short) i + 1);

            if (tarefa.isPrincipal()) {
                row.createCell(COLUNA_PRINCIPAL).setCellValue("Sim");
            } else {
                row.createCell(COLUNA_PRINCIPAL).setCellValue("Não");
            }

            if (tarefa.getProjeto() != null) {
                row.createCell(COLUNA_PROJETO).setCellValue(tarefa.getProjeto().getId());
            } else {
                row.createCell(COLUNA_PROJETO).setCellValue("");
            }
            
            if (tarefa.getAdicional() != null) {
                row.createCell(COLUNA_ADICIONAL).setCellValue(tarefa.getAdicional().getNumero());
            } else {
                row.createCell(COLUNA_ADICIONAL).setCellValue("");
            }

            if (tarefa.getCliente() != null) {
                row.createCell(COLUNA_CLIENTE).setCellValue(tarefa.getCliente().getNomeFantasia());
            } else {
                row.createCell(COLUNA_CLIENTE).setCellValue("");
            }
            row.createCell(COLUNA_TAREFA).setCellValue(tarefa.getNome());
            if (tarefa.getVenda() != null) {
                row.createCell(COLUNA_FATURAMENTO_DIRETO).setCellValue(tarefa.getVenda().getValorFaturamentoDireto());
                row.createCell(COLUNA_FATURAMENTO_DOLPHIN).setCellValue(tarefa.getVenda().getValorFaturamentoDolphin());
                row.createCell(COLUNA_VALOR_CONTRATO).setCellValue(tarefa.getVenda().calcularValorTotal());
            } else {
                row.createCell(COLUNA_FATURAMENTO_DIRETO).setCellValue(0.00);
                row.createCell(COLUNA_FATURAMENTO_DOLPHIN).setCellValue(0.00);
                row.createCell(COLUNA_VALOR_CONTRATO).setCellValue(0.00);
            }
            
            row.createCell(COLUNA_CLASSIFICACAO).setCellValue(tarefa.getPrioridade());
            row.createCell(COLUNA_STATUS).setCellValue(tarefa.getStatus().getNome());
            
            if (tarefa.getDataSolicitacao() != null) {
                row.createCell(COLUNA_DATA_SOLICITACAO).setCellValue(tarefa.getDataSolicitacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(COLUNA_DATA_SOLICITACAO).setCellValue("");
            }
            
            if (tarefa.getDataInicio() != null) {
                row.createCell(COLUNA_DATA_INICIO).setCellValue(tarefa.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(COLUNA_DATA_INICIO).setCellValue("");
            }
            
            if (tarefa.getDataPrevEntrega() != null) {
                row.createCell(COLUNA_DATA_PREVISAO_FECHAMENTO).setCellValue(tarefa.getDataPrevEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(COLUNA_DATA_PREVISAO_FECHAMENTO).setCellValue("");
            }
            if (tarefa.getDataEntrega() != null) {
                row.createCell(COLUNA_DATA_FECHAMENTO).setCellValue(tarefa.getDataEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(COLUNA_DATA_FECHAMENTO).setCellValue("");
            }
            
            if (tarefa.getResponsavel() != null) {
                row.createCell(COLUNA_RESPONSAVEL).setCellValue(tarefa.getResponsavel().getNome());
            } else {
                row.createCell(COLUNA_RESPONSAVEL).setCellValue("");
            }
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        System.out.println("Seu arquivo excel foi gerado!");
    }

    public void exportarRelatorioFollowUp(ArrayList<OrdemServico> tarefas) throws Exception {
        OrdemServicoContatoController ordemServicoContatoController = new OrdemServicoContatoController();
        //cria cabeçalho
        rowhead.createCell(0).setCellValue("Check da Interação");
        rowhead.createCell(1).setCellValue("Data da Interação");
        rowhead.createCell(2).setCellValue("Projeto");
        rowhead.createCell(3).setCellValue("Adicional");
        rowhead.createCell(4).setCellValue("Cliente");
        rowhead.createCell(5).setCellValue("OS/Tarefa");
        rowhead.createCell(6).setCellValue("Valor do Contrato");
        rowhead.createCell(7).setCellValue("Classificação ABC");
        rowhead.createCell(8).setCellValue("Status");
        rowhead.createCell(9).setCellValue("Data de Início");
        rowhead.createCell(10).setCellValue("Prev. de Fechamento");
        rowhead.createCell(11).setCellValue("Data de Fechamento");
        rowhead.createCell(12).setCellValue("Responsável");
        rowhead.createCell(13).setCellValue("Contato");

        LocalDate hoje = LocalDate.now();
        // definindo seus valores
        for (int i = 0; i < tarefas.size(); i++) {
            OrdemServico tarefa = tarefas.get(i);
            tarefa.setContatos(ordemServicoContatoController.buscarPorOrdemServico(tarefa.getCodOs()));
            ComentarioDAO cDAO = new ComentarioDAO();
            tarefa.setComentarios(cDAO.buscarComentOS(tarefa.getCodOs()));
            HSSFRow row = sheet.createRow((short) i + 1);

            if (tarefa.getDataInteracao() != null) {
                if (hoje.isAfter(tarefa.getDataInteracao())) {
                    row.createCell(0).setCellValue("X");
                } else {
                    row.createCell(0).setCellValue("OK");
                }

                row.createCell(1).setCellValue(tarefa.getDataInteracao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(0).setCellValue("");
                row.createCell(1).setCellValue("");
            }

            if (tarefa.getProjeto() != null) {
                row.createCell(2).setCellValue(tarefa.getProjeto().getId());
            } else {
                row.createCell(2).setCellValue("");
            }

            if (tarefa.getAdicional() != null) {
                row.createCell(3).setCellValue(tarefa.getAdicional().getNumero());
            } else {
                row.createCell(3).setCellValue("");
            }

            if (tarefa.getCliente() != null) {
                row.createCell(4).setCellValue(tarefa.getCliente().getNomeFantasia());
            } else {
                row.createCell(4).setCellValue("");
            }

            row.createCell(5).setCellValue(tarefa.getNome());

            if (tarefa.getVenda() != null) {
                row.createCell(6).setCellValue(tarefa.getVenda().calcularValorTotal());
            } else {
                row.createCell(6).setCellValue(0.00);
            }

            row.createCell(7).setCellValue(tarefa.getPrioridade());
            row.createCell(8).setCellValue(tarefa.getStatus().getNome());

            if (tarefa.getDataInicio() != null) {
                row.createCell(9).setCellValue(tarefa.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(9).setCellValue("");
            }

            if (tarefa.getDataPrevEntrega() != null) {
                row.createCell(10).setCellValue(tarefa.getDataPrevEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(10).setCellValue("");
            }

            if (tarefa.getDataEntrega() != null) {
                row.createCell(11).setCellValue(tarefa.getDataEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } else {
                row.createCell(11).setCellValue("");
            }

            row.createCell(12).setCellValue(tarefa.getResponsavel().getNome());

            if (tarefa.getContatos() != null && !tarefa.getContatos().isEmpty()) {

                String contatoCellValue = "";
                for (Contato contato : tarefa.getContatos()) {
                    contatoCellValue += contato.getNome()
                            + ", \n" + contato.getEmail()
                            + ", \n" + contato.getTelefone()
                            + " \n";
                }
                HSSFCell cell = row.createCell(13);
                cell.setCellValue(contatoCellValue);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
    }

}

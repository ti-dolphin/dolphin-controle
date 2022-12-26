package dao;

import dao.apontamento.ApontamentoDAO;
import dao.apontamento.ComentarioDAO;
import dao.apontamento.RelComentariosApontDAO;
import dao.apontamento.StatusApontDAO;
import dao.os.AlteracoesOsDAO;
import dao.os.CentroCustoDAO;
import dao.os.CidadeDAO;
import dao.os.ClienteDAO;
import dao.os.EstadoDAO;
import dao.os.HistoricoStatusDAO;
import dao.os.MotivoPerdaDAO;
import dao.os.OrdemServicoDAO;
import dao.os.PessoaDAO;
import dao.os.PessoaResponsavelDAO;
import dao.os.PessoaTipoDAO;
import dao.os.SegmentoDAO;
import dao.os.SeguidoresDAO;
import dao.os.StatusDAO;
import dao.os.TipoOsDAO;
import dao.ponto.PontoDAO;
import dao.ponto.RelogioPontoDAO;

public class DAOFactory {

    private static final SituacaoDAO SITUACAODAO = new SituacaoDAO();
    private static final FuncaoDAO FUNCAODAO = new FuncaoDAO();
    private static final FuncionarioDAO FUNCIONARIODAO = new FuncionarioDAO();
    private static final EpiDAO EPIDAO = new EpiDAO();
    private static final EpiFuncionarioDAO EPIFUNCIONARIODAO = new EpiFuncionarioDAO();
    private static final TicketDAO TICKETDAO = new TicketDAO();
    private static final LocalDAO LOCALDAO = new LocalDAO();
    private static final TipoOsDAO TIPOOSDAO = new TipoOsDAO();
    private static final PessoaDAO PESSOADAO = new PessoaDAO();
    private static final OrdemServicoDAO ORDEMSERVICODAO = new OrdemServicoDAO();
    private static final StatusDAO STATUSDAO = new StatusDAO();
    private static final StatusApontDAO STATUSAPONTDAO = new StatusApontDAO();
    private static final CentroCustoDAO CENTROCUSTODAO = new CentroCustoDAO();
    private static final ApontamentoDAO APONTAMENTODAO = new ApontamentoDAO();
    private static final ComentarioDAO COMENTARIODAO = new ComentarioDAO();
    private static final SistemaDAO SISTEMADAO = new SistemaDAO();
    private static final SeguidoresDAO SEGUIDORESDAO = new SeguidoresDAO();
    private static final AlteracoesOsDAO ALTERACOESOSDAO = new AlteracoesOsDAO();
    private static final HistoricoStatusDAO HISTORICOSTATUSDAO = new HistoricoStatusDAO();
    private static final RelComentariosApontDAO RELCOMENTARIOSAPONTDAO = new RelComentariosApontDAO();
    private static final RelogioPontoDAO RELOGIOPONTODAO = new RelogioPontoDAO();
    private static final PontoDAO PONTODAO = new PontoDAO();
    private static final ClienteDAO CLIENTEDAO = new ClienteDAO();
    private static final PessoaTipoDAO PESSOATIPODAO = new PessoaTipoDAO();
    private static final PessoaResponsavelDAO PESSOARESPONSAVELDAO = new PessoaResponsavelDAO();
    private static final SegmentoDAO SEGMENTODAO = new SegmentoDAO();
    private static final EstadoDAO ESTADODAO = new EstadoDAO();
    private static final CidadeDAO CIDADEDAO = new CidadeDAO();
    private static final ProdutoDAO PRODUTODAO = new ProdutoDAO();
    private static final MovimentoDAO MOVIMENTODAO = new MovimentoDAO();
    private static final MovimentoItemDAO MOVIMENTOITEMDAO = new MovimentoItemDAO();
    private static final ClassificacaoDAO CLASSIFICACAODAO = new ClassificacaoDAO();
    private static final ClienteComentarioDAO CLIENTECOMENTARIODAO = new ClienteComentarioDAO();
    private static final MotivoPerdaDAO MOTIVOPERDADAO = new MotivoPerdaDAO();

    public static SistemaDAO getSISTEMADAO() {
        return SISTEMADAO;
    }
    
    public static ComentarioDAO getCOMENTARIODAO() {
        return COMENTARIODAO;
    }
    
    public static StatusApontDAO getSTATUSAPONTDAO() {
        return STATUSAPONTDAO;
    }

    public static ApontamentoDAO getAPONTAMENTODAO() {
        return APONTAMENTODAO;
    }

    public static CentroCustoDAO getCENTROCUSTODAO() {
        return CENTROCUSTODAO;
    }

    public static StatusDAO getSTATUSDAO() {
        return STATUSDAO;
    }

    public static OrdemServicoDAO getORDEMSERVICODAO() {
        return ORDEMSERVICODAO;
    }

    public static PessoaDAO getPESSOADAO() {
        return PESSOADAO;
    }

    public static SituacaoDAO getPsituacao() {
        return SITUACAODAO;
    }

    public static FuncaoDAO getFuncao() {
        return FUNCAODAO;
    }

    public static FuncionarioDAO getFuncionario() {
        return FUNCIONARIODAO;
    }

    public static EpiDAO getEpidao() {
        return EPIDAO;
    }

    public static EpiFuncionarioDAO getEpifuncionariodao() {
        return EPIFUNCIONARIODAO;
    }

    public static TicketDAO getTICKETDAO() {
        return TICKETDAO;
    }

    public static LocalDAO getLOCALDAO() {
        return LOCALDAO;
    }

    public static TipoOsDAO getTIPOOSDAO() {
        return TIPOOSDAO;
    }

    public static SeguidoresDAO getSEGUIDORESDAO() {
        return SEGUIDORESDAO;
    }

    public static AlteracoesOsDAO getALTERACOESOSDAO() {
        return ALTERACOESOSDAO;
    }

    public static HistoricoStatusDAO getHISTORICOSTATUSDAO() {
        return HISTORICOSTATUSDAO;
    }

    public static RelComentariosApontDAO getRELCOMENTARIOSAPONTDAO() {
        return RELCOMENTARIOSAPONTDAO;
    }

    public static RelogioPontoDAO getRELOGIOPONTODAO() {
        return RELOGIOPONTODAO;
    }

    public static PontoDAO getPONTODAO() {
        return PONTODAO;
    }

    public static ClienteDAO getCLIENTEDAO() {
        return CLIENTEDAO;
    }

    public static PessoaTipoDAO getPESSOATIPODAO() {
        return PESSOATIPODAO;
    }

    public static PessoaResponsavelDAO getPESSOARESPONSAVELDAO() {
        return PESSOARESPONSAVELDAO;
    }

    public static SegmentoDAO getSEGMENTODAO() {
        return SEGMENTODAO;
    }

    public static EstadoDAO getESTADODAO() {
        return ESTADODAO;
    }

    public static CidadeDAO getCIDADEDAO() {
        return CIDADEDAO;
    }

    public static ProdutoDAO getPRODUTODAO() {
        return PRODUTODAO;
    }

    public static MovimentoDAO getMOVIMENTODAO() {
        return MOVIMENTODAO;
    }

    public static MovimentoItemDAO getMOVIMENTOITEMDAO() {
        return MOVIMENTOITEMDAO;
    }

    public static ClassificacaoDAO getCLASSIFICACAODAO() {
        return CLASSIFICACAODAO;
    }

    public static ClienteComentarioDAO getCLIENTECOMENTARIODAO() {
        return CLIENTECOMENTARIODAO;
    }
    
    public static MotivoPerdaDAO getMOTIVOPERDADAO() {
        return MOTIVOPERDADAO;
    }
    
}

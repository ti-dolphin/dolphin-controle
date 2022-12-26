package services;

import services.funcionario.FuncionarioService;
import services.epi.EpiService;
import services.epi.EpiFuncionarioService;

public class ServicosFactory {

    private static final SituacaoServicos psituacaoServicos = new SituacaoServicos();
    private static final FuncaoServicos FUNCAOSERVICOS = new FuncaoServicos();
    private static final FuncionarioService FUNCIONARIOSERVICOS = new FuncionarioService();
    private static final EpiService EPISERVICOS = new EpiService();
    private static final EpiFuncionarioService EPIFUNCIONARIOSERVICOS = new EpiFuncionarioService();
    private static final TicketServicos TICKETSERVICOS = new TicketServicos();
    private static final LocalServicos LOCALSERVICOS = new LocalServicos();
    private static final RelogioPontoServicos RELOGIOPONTOSERVICOS = new RelogioPontoServicos();

    public static SituacaoServicos getPsituacaoServicos() {
        return psituacaoServicos;
    }

    public static FuncaoServicos getFUNCAOSERVICOS() {
        return FUNCAOSERVICOS;
    }

    public static FuncionarioService getFUNCIONARIOSERVICOS() {
        return FUNCIONARIOSERVICOS;
    }

    public static EpiService getEPISERVICOS() {
        return EPISERVICOS;
    }

    public static EpiFuncionarioService getEPIFUNCIONARIOSERVICOS() {
        return EPIFUNCIONARIOSERVICOS;
    }

    public static TicketServicos getTICKETSERVICOS() {
        return TICKETSERVICOS;
    }

    public static LocalServicos getLOCALSERVICOS() {
        return LOCALSERVICOS;
    }

    public static RelogioPontoServicos getRELOGIOPONTOSERVICOS() {
        return RELOGIOPONTOSERVICOS;
    }

}

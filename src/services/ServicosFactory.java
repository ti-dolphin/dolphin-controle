package services;

public class ServicosFactory {

    private static final SituacaoServicos psituacaoServicos = new SituacaoServicos();
    private static final FuncaoServicos FUNCAOSERVICOS = new FuncaoServicos();
    private static final FuncionarioServicos FUNCIONARIOSERVICOS = new FuncionarioServicos();
    private static final EpiServicos EPISERVICOS = new EpiServicos();
    private static final EpiFuncionarioServicos EPIFUNCIONARIOSERVICOS = new EpiFuncionarioServicos();
    private static final TicketServicos TICKETSERVICOS = new TicketServicos();
    private static final LocalServicos LOCALSERVICOS = new LocalServicos();
    private static final RelogioPontoServicos RELOGIOPONTOSERVICOS = new RelogioPontoServicos();

    public static SituacaoServicos getPsituacaoServicos() {
        return psituacaoServicos;
    }

    public static FuncaoServicos getFUNCAOSERVICOS() {
        return FUNCAOSERVICOS;
    }

    public static FuncionarioServicos getFUNCIONARIOSERVICOS() {
        return FUNCIONARIOSERVICOS;
    }

    public static EpiServicos getEPISERVICOS() {
        return EPISERVICOS;
    }

    public static EpiFuncionarioServicos getEPIFUNCIONARIOSERVICOS() {
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

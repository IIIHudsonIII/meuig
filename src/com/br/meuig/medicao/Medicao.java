package com.br.meuig.medicao;

public class Medicao {
    private long id,usuario_id,momento_id;
    private int valorMedido;
    private String dataMedicao,observacao;
    
    public Medicao(){
	
    }
    
    public Medicao(int usuario_id, int valorMedido, int momento_id,
	String dataMedicao, String observacao) {
    super();
    this.usuario_id = usuario_id;
    this.valorMedido = valorMedido;
    this.momento_id = momento_id;
    this.dataMedicao = dataMedicao;
    this.observacao = observacao;
}


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id; 
    }
    public long getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(long usuario_id) {
        this.usuario_id = usuario_id;
    }
    public int getValorMedido() {
        return valorMedido;
    }
    public void setValorMedido(int valorMedido) {
        this.valorMedido = valorMedido;
    }
    public long getMomento_id() {
        return momento_id;
    }
    public void setMomento_id(long momento_id) {
        this.momento_id = momento_id;
    }
    public String getDataMedicao() {
        return dataMedicao;
    }
    public void setDataMedicao(String dataMedicao) {
        this.dataMedicao = dataMedicao;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    

}

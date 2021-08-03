package br.com.alura.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Rafaell Estevam
 *
 */
@Entity
public class Movimentacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private TipoMovimentacao tipoMovimentacao;
	private BigDecimal valor;
	private LocalDateTime dataHora;
	private String descricao;

	@ManyToOne
	private Conta conta;

	/**
	 * 
	 */
	public Movimentacao() {
		// TODO Auto-generated constructor stub
	}

	public Movimentacao(TipoMovimentacao tipoMovimentacao, BigDecimal valor, LocalDateTime dataHora, String descricao,
			Conta conta) {
		super();
		this.tipoMovimentacao = tipoMovimentacao;
		this.valor = valor;
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.conta = conta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "tipoMovimentacao=" + tipoMovimentacao + ", valor=" + valor + ", dataHora=" + dataHora + ", descricao="
				+ descricao + ", conta id=" + conta.getId();
	}
	
	

}

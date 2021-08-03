package br.com.alura.banco.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Rafaell Estevam
 *
 */
@Entity
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer agencia;
	private Integer numero;
	private BigDecimal saldo;
	@Enumerated(EnumType.STRING)
	private TipoConta tipoConta;

	@OneToMany(mappedBy = "conta", cascade = CascadeType.REMOVE)
	private List<Movimentacao> movimentacoes;

	/**
	 * 
	 */
	public Conta() {
		// TODO Auto-generated constructor stub
	}

	public Conta(Integer agencia, Integer numero, BigDecimal saldo, TipoConta tipoConta) {
		super();
		this.agencia = agencia;
		this.numero = numero;
		this.saldo = saldo;
		this.tipoConta = tipoConta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	@Override
	public String toString() {
		return "agencia=" + agencia + ", numero=" + numero + ", saldo=" + saldo;
	}

}

package br.com.alura.banco.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.alura.banco.dao.ContaDAO;

/**
 * @author Rafaell Estevam
 *
 */
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private LocalDate dataNascimento;
	private String profissao;
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(unique = true)
	private Conta conta;

	@OneToMany(mappedBy = "cliente", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private List<Telefone> telefones;

	/**
	 * 
	 */
	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	// atualizar
	public Cliente(String nome, String cpf, String email, LocalDate dataNascimento, String profissao,
			EstadoCivil estadoCivil, Conta conta, List<Telefone> telefones) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.profissao = profissao;
		this.estadoCivil = estadoCivil;
		this.conta = conta;
		this.telefones = telefones;
	}

	// inserir (conta é gerada automaticamente)
	public Cliente(EntityManager em, String nome, String cpf, String email, LocalDate dataNascimento, String profissao,
			EstadoCivil estadoCivil, List<Telefone> telefones) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.profissao = profissao;
		this.estadoCivil = estadoCivil;
		this.telefones = telefones;
		this.setConta(em);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void setConta(EntityManager em) {
		ContaDAO contaDAO = new ContaDAO(em);
		Random random = new Random();
		Integer numero;
		boolean numExiste;

		do {
			numero = random.nextInt(999999999);

			numExiste = contaDAO.checkIfAccountExists(numero);

		} while (numExiste);

		Conta conta = new Conta(3633, numero, BigDecimal.valueOf(0.00), TipoConta.CORRENTE);

		this.conta = conta;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

}

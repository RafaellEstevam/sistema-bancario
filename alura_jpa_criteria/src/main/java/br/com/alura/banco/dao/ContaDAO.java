package br.com.alura.banco.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.banco.model.Conta;
import br.com.alura.banco.model.Movimentacao;
import br.com.alura.banco.model.TipoConta;
import br.com.alura.banco.model.TipoMovimentacao;

/**
 * @author Rafaell Estevam
 *
 */
public class ContaDAO {

	private EntityManager em;

	public ContaDAO(EntityManager em) {
		super();
		this.em = em;
	}

	// buscar pelo ID 
	public Conta findById(Long id) {
		return em.find(Conta.class, id);
	}

	// buscar por agencia e nº da conta
	public Conta findByAgenciaAndNumero(Integer agencia, Integer numConta) {

		String jpql = "select c from Conta c where c.agencia = :agencia and c.numero= :numConta";

		TypedQuery<Conta> query = em.createQuery(jpql, Conta.class);
		query.setParameter(":agencia", agencia);
		query.setParameter(":numConta", numConta);

		return query.getSingleResult();

	}

	public boolean checkIfAccountExists(Integer numero) {

		try {
			String jpql = "select c from Conta c where c.numero = :numero";

			TypedQuery<Conta> query = em.createQuery(jpql, Conta.class);
			query.setParameter("numero", numero);
			query.getSingleResult();

		} catch (NoResultException e) {
			return false;

		}

		return true;

	}

	// query dinâmica -> select c from Conta c where c.agencia = :agencia and
	// c.tipoConta = :tipoConta and c.saldo between :valor1 and :valor2;
	public List<Conta> buscaDinamicaVariosParam(Integer agencia, TipoConta tipoConta, BigDecimal valor1,
			BigDecimal valor2) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Conta> query = criteriaBuilder.createQuery(Conta.class);
		Root<Conta> root = query.from(Conta.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		// c.agencia = :agencia
		if (agencia != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("agencia"), agencia);
			predicates.add(predicate);
		}

		// c.tipoConta = :tipoConta
		if (agencia != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("tipoConta"), tipoConta);
			predicates.add(predicate);
		}

		// c.saldo between :valor1 and :valor2
		if (agencia != null) {
			Predicate predicate = criteriaBuilder.between((Expression) root.get("saldo"), valor1, valor2);
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Conta> query2 = em.createQuery(query);
		return query2.getResultList();

	}
	
	
	
	public boolean sacar(Conta paramConta, BigDecimal valor) {
		
		Conta conta = em.find(Conta.class, paramConta.getId());
		
		if(conta.getSaldo().compareTo(valor) == 0 || conta.getSaldo().compareTo(valor) == 1){
			
			Movimentacao mov = new Movimentacao(TipoMovimentacao.SAIDA, valor, LocalDateTime.now(), "SAQUE", conta);
			
			em.getTransaction().begin();
			
			conta.setSaldo(conta.getSaldo().subtract(valor));
			
			em.persist(mov);
			
			em.getTransaction().commit();
			
			return true;
		}
		
		System.out.println("saldo insuficiente");
		return false;
		
	}
	
	
	
	public boolean transferir(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
		
		contaOrigem = em.find(Conta.class, contaOrigem.getId());
		contaDestino = em.find(Conta.class, contaDestino.getId());
		
		if(contaOrigem.getSaldo().compareTo(valor) == 1 || contaOrigem.getSaldo().compareTo(valor) == 0) {
			
			
			Movimentacao movOrigem = new Movimentacao(TipoMovimentacao.SAIDA, valor, LocalDateTime.now(), "TRANSF. SAIDA", contaOrigem);
			Movimentacao movDestino = new Movimentacao(TipoMovimentacao.ENTRADA, valor, LocalDateTime.now(), "TRANSF. ENTRADA", contaDestino);
			
			
			em.getTransaction().begin();
			
			contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
			
			em.persist(movOrigem);
			
			
			contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
			
			em.persist(movDestino);
			
			em.getTransaction().commit();
			
			return true;
		}
		
		System.out.println("saldo insuficiente");
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

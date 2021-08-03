package br.com.alura.banco.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.banco.model.Cliente;
import br.com.alura.banco.model.EstadoCivil;

/**
 * @author Rafaell Estevam
 *
 */
public class ClienteDAO {

	private EntityManager em;

	public ClienteDAO(EntityManager em) {

		this.em = em;
	}

	// salvar
	public void save(Cliente cliente) {

		em.getTransaction().begin();

		em.persist(cliente);

		em.getTransaction().commit();

	}

	// remover
	public void remove(Long id) {

		Cliente cliente = em.find(Cliente.class, id);

		em.getTransaction().begin();

		em.remove(cliente);

		em.getTransaction().commit();

	}

	// atualizar
	public void update(Cliente cliente) {

		em.getTransaction().begin();

		em.merge(cliente);

		em.getTransaction().commit();

	}

	// buscar por Id
	public Cliente findById(Long id) {

		return em.find(Cliente.class, id);

	}

	// buscar por cpf
	public Cliente findByCpf(String cpf) {

		String jpql = "select c from Cliente c where c.cpf = :cpf";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
		query.setParameter("cpf", cpf);

		return query.getSingleResult();
	}

	// listar todos os clientes
	public List<Cliente> findAll() {

		String jpql = "select distinct c from Cliente c join fetch c.conta left join fetch c.telefones";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
		return query.getResultList();

	}

	// buscar por semelhança de nome
	public List<Cliente> findByNome(String nome) {

		String jpql = "select c from Cliente c where c.nome like concat('%',:nome,'%')";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
		query.setParameter(jpql, nome);

		return query.getResultList();
	}

	// buscar cliente pelo nº da conta
	public Cliente findByContaNumero(Integer numConta) {

		String jpql = "select c from Cliente c join fetch c.conta ct where ct.numero = :numConta";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

		query.setParameter(jpql, numConta);

		return query.getSingleResult();

	}

	// buscar por estado civil
	public List<Cliente> findByEstadoCivil(EstadoCivil estadoCivil) {

		String jpql = "select c from Cliente c where c.estadoCivil = :estadoCivil";

		TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
		query.setParameter(jpql, estadoCivil);

		return query.getResultList();
	}

	// query dinâmica 1: data de nascimento
	public List<Cliente> buscaDinamicaDataNascimento(Integer dia, Integer mes, Integer ano) {
		// select c from Cliente c where day(c.dataNascimento) = :dia and
		// month(c.dataNascimento) = :mes and year(c.dataNascimento) = :ano;

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> query = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = query.from(Cliente.class); // select c from Cliente c

		List<Predicate> predicates = new ArrayList<Predicate>();

		// day(c.dataNascimento) = :dia
		if (dia != null) {
			Predicate predicate = criteriaBuilder
					.equal(criteriaBuilder.function("day", Integer.class, root.get("dataNascimento")), dia);
			predicates.add(predicate);
		}

		// month(c.dataNascimento) = :mes
		if (mes != null) {
			Predicate predicate = criteriaBuilder
					.equal(criteriaBuilder.function("month", Integer.class, root.get("dataNascimento")), mes);
			predicates.add(predicate);
		}

		// year(c.dataNascimento) = :ano
		if (ano != null) {
			Predicate predicate = criteriaBuilder
					.equal(criteriaBuilder.function("year", Integer.class, root.get("dataNascimento")), ano);
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Cliente> query2 = em.createQuery(query);

		return query2.getResultList();

	}


}

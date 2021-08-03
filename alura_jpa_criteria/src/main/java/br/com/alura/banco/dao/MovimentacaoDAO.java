package br.com.alura.banco.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.banco.model.Conta;
import br.com.alura.banco.model.MediaContaDTO;
import br.com.alura.banco.model.MediaDataDTO;
import br.com.alura.banco.model.Movimentacao;
import br.com.alura.banco.model.TipoMovimentacao;


/**
 * @author Rafaell Estevam
 *
 */
public class MovimentacaoDAO {

	private EntityManager em;

	public MovimentacaoDAO(EntityManager em) {
		super();
		this.em = em;
	}

	// salvar
	public void save(Movimentacao movimentacao) {
		em.getTransaction().begin();

		em.persist(movimentacao);

		em.getTransaction().commit();
	}

	// buscar por conta id, ordenada de forma ascendente pelo valor

	public List<Movimentacao> findByContaId(Long contaId) {

		String jpql = "select m from Movimentacao m join fetch m.conta where m.conta.id = :contaId order by m.valor";

		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		query.setParameter("contaId", contaId);

		return query.getResultList();

	}

	// buscar por agencia e conta
	public List<Movimentacao> findByConta_AgenciaAndNumero(Integer agencia, Integer numero) {

		String jpql = "select m from Movimentacao m join fetch m.conta c where c.agencia= :agencia and c.numero= :numero";

		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		query.setParameter("agencia", agencia);
		query.setParameter("numero", numero);

		return query.getResultList();
	}

	// listar todas as movimentações
	
	public List<Movimentacao> findAll() {

		String jpql = "select m from Movimentacao m join fetch m.conta";

		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		return query.getResultList();

	}

	//buscar movimentações por período
	public List<Movimentacao> findByPeriod(LocalDate data1, LocalDate data2) {

		LocalTime time = LocalTime.of(00, 00, 00);
		LocalDateTime paramData1 = LocalDateTime.of(data1, time);

		LocalTime time2 = LocalTime.of(23, 59, 59);
		LocalDateTime paramData2 = LocalDateTime.of(data2, time2);

		String jpql = "select m from Movimentacao m join fetch m.conta where m.dataHora between :data1 and :data2";

		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		query.setParameter("data1", paramData1);
		query.setParameter("data2", paramData2);

		return query.getResultList();

	}
	
	
	//buscar movimentações de uma conta x por periodo
	public List<Movimentacao>findByContaAndPeriodo(Conta conta, LocalDate data1, LocalDate data2){
		
		
		LocalDateTime paramData1 = LocalDateTime.of(data1, LocalTime.of(00, 00));
		LocalDateTime paramData2 = LocalDateTime.of(data2, LocalTime.of(23, 59));
		
		String jpql = "select m from Movimentacao m join fetch m.conta where m.conta = :conta and m.dataHora between :data1 and :data2";
		
		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		query.setParameter("conta", conta);
		query.setParameter("data1", paramData1);
		query.setParameter("data2", paramData2);
		
		return query.getResultList();
	}
	
	
	
	public Double getMedia(){
		//tirar media dos valores das movimentações
		
		String jpql="select avg(valor) from Movimentacao m";
		
		TypedQuery<Double> query = em.createQuery(jpql, Double.class);
		
		return query.getSingleResult();
		
	}
	
	
//	
//	public List<MediaContaDTO> getMediaPorConta(){
//		//tirar media dos valores das movimentacoes por conta
//		
//		String jpql="select new br.com.alura.banco.model.MediaContaDTO(avg(valor), m.conta.agencia, m.conta.numero) from Movimentacao m group by m.conta";
//		
//		TypedQuery<MediaContaDTO> query = em.createQuery(jpql, MediaContaDTO.class);
//		return query.getResultList();
//		
//	}
//	
	
	public List<MediaContaDTO> getMediaPorConta(){
		//tirar media dos valores das movimentacoes por conta
		
		String jpql="select new br.com.alura.banco.model.MediaContaDTO(avg(valor), m.conta) from Movimentacao m group by m.conta";
		
		TypedQuery<MediaContaDTO> query = em.createQuery(jpql, MediaContaDTO.class);
		return query.getResultList();
		
	}
	
	
	
	public Double getMediaPorPeriodo(Conta conta, LocalDate data1, LocalDate data2) {
		
		//tirar media dos valores das movimentações de uma conta dentro do periodo x
		
		LocalDateTime dataHora1 = LocalDateTime.of(data1, LocalTime.MIN);
		LocalDateTime dataHora2 = LocalDateTime.of(data2, LocalTime.MAX);
		
		
		String jpql="select avg(valor) from Movimentacao m where m.conta = :conta and m.dataHora between :dataHora1 and :dataHora2";
		
		TypedQuery<Double> query = em.createQuery(jpql, Double.class);
		query.setParameter("conta", conta);
		query.setParameter("dataHora1", dataHora1);
		query.setParameter("dataHora2", dataHora2);
		
		return query.getSingleResult();
	}
	
	
	
	
	public List<MediaDataDTO> getMediaDiaria() {
		//tirar media dos valores das movimentações agrupados por dia
		
		String jpql = "select new br.com.alura.banco.model.MediaDataDTO(avg(valor), day(m.dataHora), month(m.dataHora), year(m.dataHora)) from Movimentacao m group by day(m.dataHora), month(m.dataHora), year(m.dataHora)";
		
		TypedQuery<MediaDataDTO> query = em.createQuery(jpql, MediaDataDTO.class);
		
		return query.getResultList();
	}
	
	
	
	
	public BigDecimal getSoma() {
		//tirar a soma dos valores das movimentações
		
		String jpql="select sum(valor) from Movimentacao m";
		
		TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);
		
		return query.getSingleResult();
		
	}
	
	
	public BigDecimal getSomaConta(Conta conta) {
		//tirar a soma dos valores das movimentações de uma conta x
		
		
		String jpql="select sum(valor) from Movimentacao m where m.conta = :conta";
		
		TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);
		query.setParameter("conta", conta);
		
		return query.getSingleResult();
		
		
	}
	
	
	//Query dinâmica com os seguintes parâmetros: tipo, dia, mês e ano da movimentação
	public List<Movimentacao>dinamicQuery(TipoMovimentacao tipo, Integer dia, Integer mes, Integer ano){
		
		//select m from Movimentacao m where m.tipoMovimentacao = :tipo and day(m.dataHora)= :dia and month(m.dataHora)= :month and year(m.dataHora)= :ano
		
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Movimentacao>query = builder.createQuery(Movimentacao.class);
		Root<Movimentacao>root = query.from(Movimentacao.class);
		
		List<Predicate>predicates = new ArrayList<Predicate>();
		
		//m.tipoMovimentacao = :tipo		
		if(tipo != null) {
			Predicate predicate = builder.equal(root.get("tipoMovimentacao"), tipo);
			predicates.add(predicate);
		}
		
		// day(m.dataHora)= :dia
		if(dia != null) {
			Predicate predicate = builder.equal(builder.function("day", Integer.class, root.get("dataHora")), dia);
			predicates.add(predicate);
		}
		
		//month(m.dataHora)= :month
		if(mes != null) {
			Predicate predicate = builder.equal(builder.function("month", Integer.class, root.get("dataHora")), mes);
			predicates.add(predicate);
		}
		
		//year(m.dataHora)= :ano
		if(ano != null) {
			Predicate predicate = builder.equal(builder.function("year", Integer.class, root.get("dataHora")), ano);
			predicates.add(predicate);
		}
		
		
		query.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Movimentacao> query2 = em.createQuery(query);
		
		return query2.getResultList();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

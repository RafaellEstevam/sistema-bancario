package br.com.alura.banco.tests;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.banco.dao.ContaDAO;
import br.com.alura.banco.dao.MovimentacaoDAO;
import br.com.alura.banco.model.Conta;
import br.com.alura.banco.model.MediaContaDTO;
import br.com.alura.banco.model.MediaDataDTO;
import br.com.alura.banco.model.Movimentacao;
import br.com.alura.banco.model.TipoMovimentacao;

/**
 * @author Rafaell Estevam
 *
 */
public class Teste {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("banco");
		EntityManager em = emf.createEntityManager();

	}

}

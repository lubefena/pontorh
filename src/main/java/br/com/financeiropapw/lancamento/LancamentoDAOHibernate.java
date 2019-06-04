package br.com.financeiropapw.lancamento;

import java.math.BigDecimal;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.financeiropapw.conta.Conta;

public class LancamentoDAOHibernate implements LancamentoDAO {
	
	private Session session; //Declara��o do objeto da Classe Session do Hibernate.
	
	public void setSession(Session session) { // O m�todo "setSession" realiza a inje��o da Session do Hibernate na classe LancamentoDAOHibernate
	this.session = session;
	}
	
	public void salvar(Lancamento lancamento) {
		this.session.saveOrUpdate(lancamento);
	}
	
	public void excluir(Lancamento lancamento) {
		this.session.delete(lancamento);
	}
	
	public Lancamento carregar(Integer lancamento) {
		return (Lancamento) this.session.get(Lancamento.class, lancamento);
	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) { //O m�todo listar permite buscar os lan�amentos entre as datas in�cio e data fim de uma determinada conta. 
		Criteria criteria = this.session.createCriteria(Lancamento.class); //Instancia do objeto da Classe Criteria for�ando uma consulta a tabela de "lancamento" no Banco de Dados. 
		
		if (dataInicio != null && dataFim != null) { //Se a "dataInicio" e a "dataFim" forem diferentes de "null".
			criteria.add(Restrictions.between("data", dataInicio, dataFim)); //Ser� feita uma consulta com restri��o entre aos atributos "dataInicio" e "dataFim". 
		} else if (dataInicio != null) { //Caso apenas a "dataInicio" for diferente de "null"
			criteria.add(Restrictions.ge("data", dataInicio)); //Ser� feita uma consulta com restri��o maior que ou igual ao atributo "dataInicio".
		} else if (dataFim != null) { //Caso apenas a "dataFim" for diferente de "null"
			criteria.add(Restrictions.le("data", dataFim)); //Ser� feita uma consulta com restri��o menor que ou igual ao atributo "dataFim".
		}
		
		criteria.add(Restrictions.eq("conta", conta)); //Realiza-se uma consulta com restri��o igual ao atributo "conta".
		criteria.addOrder(Order.asc("data")); //Realiza-se uma ordena��o dos lan�amentos da data mais antiga para a mais recente.
		return criteria.list(); //Retorna-se o resultado da lista.
	}
	
	public float saldo(Conta conta, Date data) { //O m�todo saldo permite calcular o saldo total dos lan�amentos at� a data recebida por par�metro.
		StringBuffer sql = new StringBuffer(); //Instancia do objeto da Classe "StringBuffer" para concatenar a query do comando SQL.
		sql.append("select sum(l.valor * c.fator)");
		sql.append(" from lancamento l,");
		sql.append(" Categoria c");
		sql.append(" where l.categoria = c.codigo");
		sql.append(" and l.conta = :conta");
		sql.append(" and l.data <= :data");
		
		SQLQuery query = this.session.createSQLQuery(sql.toString()); //Realiza-se a intancia de um objeto da Classe "SQLQuery" recebendo query do objeto "sql" aplicado o "toString". 
		query.setParameter("conta", conta.getConta()); //Realiza-se a vincula��o do valor da conta atrav�s do "conta.getConta()" ao param�tro "conta". 
		query.setParameter("data", data); //Realiza-se a vincula��o do valor da data atrav�s ao param�tro "data".
		BigDecimal saldo = (BigDecimal) query.uniqueResult(); //Instancia-se o objeto da Classe "BigDecimal" atribuindo-se o resultado da consulta.
		if (saldo != null) { //Se o valor do saldo apurado for diferente de "null".
			return saldo.floatValue(); //Retorna-se o saldo convertido em float.
		}
		return 0f; //Caso contr�rio retorna ???
	}

}

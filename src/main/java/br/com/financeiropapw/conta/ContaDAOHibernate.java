package br.com.financeiropapw.conta;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.financeiropapw.usuario.Usuario;

public class ContaDAOHibernate implements ContaDAO { //Implementa interface ContaDAO

	private Session session; //Declara��o de propriedade da Classe Session do Hibernate
	
	public void setSession(Session session) { //M�todo padr�o para inje��o da Session do Hibernate na classe ContaDAOHibernate
		this.session = session;
	}
	
	public void salvar(Conta conta) { //M�todo que realiza a opera��o session.save para cadastrar ou atualizar uma conta
		this.session.saveOrUpdate(conta);
	}
	
	public void excluir(Conta conta) { //M�todo que realiza a opera��o session.delete para excluir uma conta 
		this.session.delete(conta);
	}
	
	public Conta carregar(Integer conta) { //M�todo que realiza a opera��o session.get recebendo a classe e o n�mero da conta, para carregar todos os dados da conta recebidos no par�metro
		return (Conta) this.session.get(Conta.class, conta);
	}
	
	public List<Conta> listar(Usuario usuario) { //M�todo que realiza a opera��o session.createCriteria seguida por uma restri��o (filtro) de Usu�rio e o retorno da lista
		Criteria criteria = this.session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		return criteria.list();
	}
	
	public Conta buscarFavorita(Usuario usuario) { //M�todo que realiza a opera��o session.createCriteria seguida por duas restri��es (filtro) de Usu�rio e da conta favorita e o retorno de um �nico resultado.
		Criteria criteria = this.session.createCriteria(Conta.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		criteria.add(Restrictions.eq("favorita", true));
		return (Conta) criteria.uniqueResult();
	}
}

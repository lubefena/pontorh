package br.com.financeiropapw.util;

import br.com.financeiropapw.categoria.CategoriaDAO;
import br.com.financeiropapw.categoria.CategoriaDAOHibernate;
import br.com.financeiropapw.conta.*;
import br.com.financeiropapw.lancamento.*;
import br.com.financeiropapw.usuario.*;

public class DAOFactory {

	public static UsuarioDAO criarUsuarioDAO() { //M�dodo que instanciar� um objeto da Classe UsuarioDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
	
	public static ContaDAO criarContaDAO() { //M�dodo que instanciar� um objeto da Classe ContaDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		ContaDAOHibernate contaDAO = new ContaDAOHibernate();
		contaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return contaDAO;
	}
	
	public static CategoriaDAO criarCategoriaDAO() { //M�dodo que instanciar� um objeto da Classe CategoriaDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		CategoriaDAOHibernate categoriaDAO = new CategoriaDAOHibernate();
		categoriaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return categoriaDAO;
	}
	
	public static LancamentoDAO criarLancamentoDAO() { //M�dodo que instanciar� um objeto da Classe LancamentoDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		LancamentoDAOHibernate lancamentoDAO = new LancamentoDAOHibernate();
		lancamentoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return lancamentoDAO;
	}
}
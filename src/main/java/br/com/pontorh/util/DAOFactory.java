package br.com.pontorh.util;

import br.com.pontorh.registro.*;
import br.com.pontorh.usuario.*;

public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO() { //M�dodo que instanciar� um objeto da Classe UsuarioDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
	
	public static RegistroDAO criarRegistroDAO() { //M�dodo que instanciar� um objeto da Classe RegistroDAOHibernate e realizar� a atribui��o da Session ao objeto instanciado.
		RegistroDAOHibernate registroDAO = new RegistroDAOHibernate();
		registroDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return registroDAO;
	}
}
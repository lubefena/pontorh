package br.com.pontorh.util;

import br.com.pontorh.registro.*;
import br.com.pontorh.usuario.*;

public class DAOFactory {
	
	public static UsuarioDAO criarUsuarioDAO() { //Médodo que instanciará um objeto da Classe UsuarioDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
	
	public static RegistroDAO criarRegistroDAO() { //Médodo que instanciará um objeto da Classe RegistroDAOHibernate e realizará a atribuição da Session ao objeto instanciado.
		RegistroDAOHibernate registroDAO = new RegistroDAOHibernate();
		registroDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return registroDAO;
	}
}
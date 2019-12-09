package br.com.pontorh.usuario;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UsuarioDAOHibernate implements UsuarioDAO { //Implementa a interface UsuarioDAO
	
	private Session session; //Declara��o de propriedade da Classe Session do Hibernate
	
	public void setSession(Session session) { //M�todo padr�o para inje��o da Session do Hibernate na classe UsuarioDAOHibernate
		this.session = session;
	}
	
	public void salvar(Usuario usuario) { //M�todo que realiza a opera��o session.save para cadastrar um novo usu�rio
		this.session.save(usuario);
	}
	
	public void atualizar(Usuario usuario) { //M�todo que realiza a opera��o session.update para modificar informa��es de um usu�rio j� cadastrado
		if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) { //Verifica-se se o usu�rio n�o possui permiss�o
			Usuario usuarioPermissao = this.carregar(usuario.getCodigo()); //Caso n�o tenha, � recarregado o conjunto de permiss�es do banco de dados
			usuario.setPermissao(usuarioPermissao.getPermissao()); //Ap�s, transfere-se as permiss�es originais para o objeto usuario a ser atualizado
			this.session.evict(usuarioPermissao); //Retira-se do contexto persistence o objeto "usuarioPermissao", que s� foi utilizado para copiar as permiss�es
		}
		this.session.update(usuario);
	}
	
	public void excluir(Usuario usuario) { //M�todo que realiza a opera��o session.delete para excluir um usu�rio 
		this.session.delete(usuario);
	}
	
	public Usuario carregar(Integer codigo) { //M�todo que realiza a opera��o session.get recebendo a classe e o c�digo do usu�rio, para carregar todos os dados de usu�rio recebidos no par�metro
		return (Usuario) this.session.get(Usuario.class, codigo);
	}
	
	public List<Usuario> listar() { //M�todo que realiza a opera��o session.createCriteria seguido por .list() for�ando uma consulta ao bando de dados sem declarar qualquer condi��o
		return this.session.createCriteria(Usuario.class).list();
	}
	
	public Usuario buscarPorLogin(String login) { //M�todo que realiza uma consulta no banco de dados utilizando uma query, atrav�s da opera��o session.createQuery(hql)
		String hql = "select u from Usuario u where u.login = :login";
		Query consulta = this.session.createQuery(hql); //� instanciado um objeto da Classe Query que realizar� uma consulta com a String hql
		consulta.setString("login", login); //O texto :login ser� substitu�do pelo par�metro do atribu�do no m�todo
		return (Usuario) consulta.uniqueResult(); //Como login � uma chave natural da tabela Usu�rio, � sabido que o resultado n�o vir� mais que uma linha. Portanto se chama o m�todo uniqueResult()
	}

}

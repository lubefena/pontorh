package br.com.pontorh.usuario;

import java.util.List;

import br.com.pontorh.util.DAOFactory;

public class UsuarioRN {
	
	private UsuarioDAO usuarioDAO; //Declara��o de propriedade da interface UsuarioDAO
	
	public UsuarioRN() { 
		this.usuarioDAO = DAOFactory.criarUsuarioDAO(); //Instancia��o da propriedade utilizando o DAOFactory no construtor
	}
	
	public Usuario carregar(Integer codigo) { //M�todo que carrega uma �nica inst�ncia de um usu�rio com base em seu c�digo
		return this.usuarioDAO.carregar(codigo);
	}
	
	public Usuario buscaPorLogin(String login) { //M�todo que carrega as informa��es do usu�rio logo ap�s o login
		return this.usuarioDAO.buscarPorLogin(login);
	}
	
	public void salvar(Usuario usuario) { //M�todo que realiza o cadastro ou atualiza��o de dados de um usu�rio
		Integer codigo = usuario.getCodigo();
		if (codigo == null || codigo == 0) { //Se o c�digo do usu�rio for nulo ou zero, � salvo um novo objeto no banco de dados
			usuario.getPermissao().add("ROLE_USUARIO"); //Quando o usu�rio cadastrado for novo, � que ser� adicionada a permiss�o "ROLE_USUARIO" ao set de permiss�es
			this.usuarioDAO.salvar(usuario);
		} else { //Se n�o, � feita a atualiza��o do objeto j� existente no banco de dados
			this.usuarioDAO.atualizar(usuario);
		}
	}
	
	public void excluir(Usuario usuario) { //M�todo que realiza a exclus�o de um usu�rio 
		this.usuarioDAO.excluir(usuario);
		
	}
	
	public List<Usuario> listar() { //M�todo que fornece uma lista com todos os usu�rios
		return this.usuarioDAO.listar();
	}
}

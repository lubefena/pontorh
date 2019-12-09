package br.com.pontorh.web;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.pontorh.usuario.Usuario;
import br.com.pontorh.usuario.UsuarioRN;

@ManagedBean(name = "usuarioBean")
@RequestScoped

public class UsuarioBean {
	
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;
	
	public String novo() { //M�todo respons�vel pela instancia de um objeto usuario
		this.destinoSalvar = "usuariosucesso";
		this.usuario = new Usuario();
		this.usuario.setAtivo(true); //Setando a propriedade Ativo como true
		return "/publico/usuario"; //Retorno da p�gina a ser exibida
	}
	
	public String editar() { //M�todo respons�vel por abrir a p�gina "usuario.xhtml com os dados do usu�rio a ser editado"
		this.confirmarSenha = this.usuario.getSenha(); //
		return "/publico/usuario";
	}
	
	public String salvar() { //M�todo respons�vel por salvar o usu�rio
		FacesContext context = FacesContext.getCurrentInstance(); //Instancia da FacesContext que ser� utilizada para adicionar as mensagens de erro que vierem a ser criadas
		
		String senha = this.usuario.getSenha();
		if (!senha.equals(this.confirmarSenha)) { //Compara��o do campo senha com o campo confirma senha
			FacesMessage facesMessage = new FacesMessage("A senha n�o foi confirmada corretamente"); //Cria��o da mensagem a ser exibida caso n�o haja a confirma��o da senha e confirma senha
			context.addMessage(null, facesMessage); //Adi��o da mensagem ao FacesContext para exibi��o
			return null; //A execu��o da a��o ser� nula, ou seja, n�o ca�ra em uma nova p�gina
		} else {
			UsuarioRN usuarioRN = new UsuarioRN(); //Caso haja a confirma��o do campo senha e confirma senha, ser� instanciado um usu�rio para persist�ncia no banco de dados
			usuarioRN.salvar(this.usuario); //Realiza��o da persist�ncia no banco de dados
		}
		
		return this.destinoSalvar; //Retornar a p�gina de destino
	}
	
	public String excluir() { //M�todo respons�vel pela exclus�o de um usu�rio
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(this.usuario);
		this.lista = null; //Atribui��o respons�vel por for�ar a recargar da lista de usu�rio pelo m�todo getLista()
		return null;
	}
	
	public String ativar() { //M�todo respons�vel por ativar/desativar um usu�rio
		if (this.usuario.isAtivo()) { //Se o usu�rio estiver ativo
			this.usuario.setAtivo(false);//Muda-se a situa��o ativa dele para false
		} else { //Se o usu�rio n�o estiver ativo
			this.usuario.setAtivo(true);//Muda-se a situa��o ativa dele para true
	}
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(this.usuario);
		return null;
	}
	
	public List<Usuario> getLista() { //M�todo respons�vel por fornecer os dados para alimentar a listagem de usu�rios na p�gina
		if (this.lista == null) { //Condicional utilizada para economizar recursos do sistema durante a montagem da p�gina.
			UsuarioRN usuarioRN = new UsuarioRN();
			this.lista = usuarioRN.listar();//Caso haja lista vazia, ser� instanciado um novo usu�rio e realizado a listagem
		} 
		return this.lista;//Retorno da listagem do usu�rio.
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}
	
	public String atribuiPermissao(Usuario usuario, String permissao) {
		this.usuario = usuario;
		java.util.Set<String> permissoes = this.usuario.getPermissao();
		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
}

package br.com.financeiropapw.usuario;

import java.util.List;

public interface UsuarioDAO { //Cont�m a assinatura de todos os m�todos ref. �s opera��es em BD para o Usu�rio 
	
	public void salvar(Usuario usuario);
	public void atualizar(Usuario usuario);
	public void excluir(Usuario usuario);
	public Usuario carregar(Integer codigo);
	public Usuario buscarPorLogin(String string);
	public List<Usuario> listar();

}

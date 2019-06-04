package br.com.financeiropapw.conta;

import java.util.List;

import br.com.financeiropapw.usuario.Usuario;

public interface ContaDAO {

	public void salvar(Conta conta);
	public void excluir(Conta conta);
	public Conta carregar(Integer conta);
	public List<Conta> listar(Usuario usuario); //Como toda Conta est� relacionada a um Usuario, o m�todo listar receber� um Usuario como par�metro 
	public Conta buscarFavorita(Usuario usuario); //Como toda Conta favorita est� relacionada a um Usuario, o m�todo buscarFavorita receber� um Usuairo como par�metro 
}

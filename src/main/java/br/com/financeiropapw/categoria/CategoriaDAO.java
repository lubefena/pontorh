package br.com.financeiropapw.categoria;

import java.util.List;

import br.com.financeiropapw.usuario.Usuario;

public interface CategoriaDAO {
	
	public Categoria salvar(Categoria categoria);
	public void excluir(Categoria categoria);
	public Categoria carregar(Integer categoria); 
	public List<Categoria> listar(Usuario usuario); //Como as Categorias est�o relacionadas a um Usuario, o m�todo listar receber� um Usuario como par�metro 

}

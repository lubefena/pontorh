package br.com.financeiropapw.categoria;

import java.util.List;

import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.util.DAOFactory;

public class CategoriaRN {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaRN() {
		this.categoriaDAO = DAOFactory.criarCategoriaDAO();
	}
	
	public List<Categoria> listar(Usuario usuario) {
		return this.categoriaDAO.listar(usuario);
	}
	
	public Categoria carregar(Integer categoria) {
		return this.categoriaDAO.carregar(categoria);
	}
	
	public void replicarFator(Categoria categoria, int fator) { //M�todo respons�vel por repassar a mudan�a de fator para todas as hierarquias abaixo da categoria alterada.
		if (categoria.getFilhos() != null) {
			for (Categoria filho : categoria.getFilhos()) {
				filho.setFator(fator);
				this.categoriaDAO.salvar(filho);
				this.replicarFator(filho, fator);
			}
		}
	}
	
	public Categoria salvar(Categoria categoria) {
		Categoria pai = categoria.getPai();
		if (pai == null) { //Teste para saber se a categoria que est� sendo salva tem uma categoria pai. 
			String msg = "A categoria " + categoria.getDescricao() + " deve ter um pai definido"; //Se n�o tiver ser� apresentada uma mensagem informando que a categoria deve ter um pai definido.
			throw new IllegalArgumentException(msg); //M�todo respons�vel pela apresenta��o da mensagem.
		}
		
		categoria.setFator(pai.getFator()); //Transfer�ncia do fator da categoria pai para a categoria filha, netos etc.
		categoria = this.categoriaDAO.salvar(categoria); //Persist�ncia da categoria no BD.
		
		boolean mudouFator = pai.getFator() != categoria.getFator(); //Se o fator da categoria pai for diferente da categoria do filho ser� instanciado o atributo "mudouFator" receber� o valor TRUE.
		
		if (mudouFator) { //Caso o atributo "mudouFator" seja true
			categoria = this.carregar(categoria.getCodigo()); //A categoria filho ser� carregada pelo seu c�digo
			this.replicarFator(categoria, categoria.getFator()); //Ser� executado o m�todo "replicarFator" para repassar a mudan�a de fator para todas as hierarquias abaixo da categoria alterada.
		}
		return categoria;
	}
	
	public void excluir(Categoria categoria) { //M�todo respons�vel pela exclus�o da categoria
		this.categoriaDAO.excluir(categoria); //Exclus�o realizada no BD.
	}
	
	public void excluir(Usuario usuario) { //M�doto respons�vel pela exclus�o por usu�rio. De modo que ao ser exlcu�do um usu�rio, automaticamente todas categorias ser�o exclu�das juntamente.
		List<Categoria> lista = this.listar(usuario); //Listagem das categorias por usu�rio
		for (Categoria categoria:lista) { //Realiza uma carga de todas as categorias
			this.categoriaDAO.excluir(categoria); //Exclus�o realizada no BD.
		}
	}
	
	public void salvaEstruturaPadrao(Usuario usuario) { //M�todo respons�vel por salvar uma estrutura padr�o no in�cio do acesso do usu�rio.

		Categoria despesas = new Categoria(null, usuario, "DESPESAS", -1);
		despesas = this.categoriaDAO.salvar(despesas);
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Moradia", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Alimenta��o", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Vestu�rio", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Deslocamento", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Cuidados Pessoais", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Educa��o", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Sa�de", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Lazer", -1));
		this.categoriaDAO.salvar(new Categoria(despesas, usuario, "Despesas Financeiras", -1));

		Categoria receitas = new Categoria(null, usuario, "RECEITAS", 1);
		receitas = this.categoriaDAO.salvar(receitas);
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Sal�rio", 1));
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Restitui��es", 1));
		this.categoriaDAO.salvar(new Categoria(receitas, usuario, "Rendimento", 1));
	}

}

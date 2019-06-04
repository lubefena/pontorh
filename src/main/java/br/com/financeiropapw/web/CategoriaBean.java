package br.com.financeiropapw.web;

import java.util.*;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.financeiropapw.categoria.*;

@ManagedBean(name = "categoriaBean")
@RequestScoped
public class CategoriaBean {

	private TreeNode categoriasTree; //�vores de categoria do primefaces.
	private Categoria editada = new Categoria(); //Objeto que alimentar� o formul�rio e receber� a categoria selecionada na �rvore.
	private List<SelectItem> categoriasSelect; //Lista que alimentar� a caixa de sele��o de categoria pai.
	private boolean mostraEdicao = false;

	@ManagedProperty(value = "#{contextoBean}")
	private ContextoBean contextoBean;

	public void novo() { //M�todo que ser� utilizado tanto pelo bot�o "Novo" quanto pelo "Nova subcategoria".
		Categoria pai = null; //Atribui��o da categoria pai como null.
		if (this.editada.getCodigo() != null) { //Verifica se o c�digo do objeto "editada" � diferente de null.
			CategoriaRN categoriaRN = new CategoriaRN(); //Caso seja, ser� instanciado um objeto "categoriaRN" da clase CategoriaRN.
			pai = categoriaRN.carregar(this.editada.getCodigo()); //O novo objeto instanciado ir� carregar o c�digo do objeto "editada" da classe Categoria e ser� atribu�do a vari�vel "Pai".
		}
		this.editada = new Categoria(); //O objeto "editada" ser� instanciado em uma nova categoria.
		this.editada.setPai(pai); //O objeto "editada" se torna uma categoria pai.
		this.mostraEdicao = true; //A nova categoria ter� atribui��o como "true"
	}

	public void selecionar(NodeSelectEvent event) { //M�todo repons�vel por selecionar uma categoria e inser�-la para edi��o.
		this.editada = (Categoria) event.getTreeNode().getData(); //Procedimento para atribuir a categoria selecionada da �rvore.
		Categoria pai = this.editada.getPai(); //Procedimento para atribuir a categoria pai da categoria selecionada.
		if (this.editada != null && pai != null && pai.getCodigo() != null) { //Verifica se a categoria selecionada, o pai da categoria selecionada e o c�digo do pai s�o diferentes de null.
			this.mostraEdicao = true; //Atribui-se como "true"
		} else { //Caso contr�rio
			this.mostraEdicao = false; //Atribui-se como "false"
		}
	}

	public String salvar() { //M�todo que serve para salvar uma categoria de determinado usu�rio
		CategoriaRN categoriaRN = new CategoriaRN(); //� instanciado um objeto categoriaRN da Classe ContaRN
		this.editada.setUsuario(this.contextoBean.getUsuarioLogado()); //Atribui-se o objeto Usuario do usuario logado na categoria em edi��o, pois toda categoria est� relacionada a um usu�rio.
		categoriaRN.salvar(this.editada); //O objeto realiza o metodo de salvar a categoria no Banco de Dados
		this.editada = null; //Procedimento para reiniciar a inst�ncia que acabou de ser salva.
		this.mostraEdicao = false; //Procedimento para n�o mostrar informa��o no formul�rio de edi��o.
		this.categoriasTree = null; //Procedimento para for�ar o carregamento dos dados da �rvore de categorias.
		this.categoriasSelect = null; //Procedimento para for�ar o carregamento dos dados da caixa de sele��o de categorias pai.
		return null;
	}

	public String excluir() { //M�todo que serve para exlcuir uma categoria de determinado usu�rio
		CategoriaRN categoriaRN = new CategoriaRN(); //� instanciado um objeto categoriaRN da Classe ContaRN
		categoriaRN.excluir(this.editada); //O objeto realiza o metodo de salvar a categoria no Banco de Dados
		this.editada = null; //Procedimento para reiniciar a inst�ncia que acabou de ser salva.
		this.mostraEdicao = false; //Procedimento para n�o mostrar informa��o no formul�rio de edi��o
		this.categoriasTree = null; //Procedimento para for�ar o carregamento dos dados da �rvore de categorias.
		this.categoriasSelect = null; //Procedimento para for�ar o carregamento dos dados da caixa de sele��o de categorias pai.
		return null;
	}

	public TreeNode getCategoriasTree() { //M�todo equivalente ao getter dos dados do componente Tree. Sua principal fun��o � o carregamento da estrutura hier�rquica das categorias do usu�rio.
		if (this.categoriasTree == null) { //Verifica se a �vore de categoria est� atribu�da como null.
			CategoriaRN categoriaRN = new CategoriaRN(); //Caso esteja, ser� instanciada um objeto "categoriaRN" da classe "CategoriaRN".
			List<Categoria> categorias = categoriaRN.listar(this.contextoBean.getUsuarioLogado()); //Ser� realizada a montagem da lista de categorias do usu�rio logado.
			this.categoriasTree = new DefaultTreeNode(null, null); //A �vore de categoria atual ir� instanciar um construtor vazio.
			this.montaDadosTree(this.categoriasTree, categorias); //A �rvore receber� a estrutura hier�rquica de Tree Node atrav�s do m�todo "montaDadosTree".
		}
		return this.categoriasTree; //� feito o retorno da �rvore.
	}

	private void montaDadosTree(TreeNode pai, List<Categoria> lista) { //M�todo que tem um funcionamento recursivo para percorrer todas as categorias do usu�rio e chamar a si mesmo.
		if (lista != null && lista.size() > 0) { //Se a lista da �rvore n�o for vazia e o seu tamanho for maior que 0.
			TreeNode filho = null; //Atribui-se a categoria filho como null.
			for (Categoria categoria : lista) { //Percorre-se todas as categorias da lista da �rvore. 
				filho = new DefaultTreeNode(categoria, pai); //Instancia o construtor do filho, atribuindo o nome da categoria e informando a categoria pai.
				this.montaDadosTree(filho, categoria.getFilhos()); //Novamente o m�todo "montaDadosTree" � chamado atribuindo-se a categoria filho como �vore pai e a listagem dos filhos abaixo de modo a gerar a recursividade. 
			}
		}
	}

	public List<SelectItem> getCategoriasSelect() { //M�todo equivalente ao "getCategoriasTree", por�m este gera uma lista plana de categorias baseada nas categorias do usu�rio.
		if (this.categoriasSelect == null) { //Verifica se a lista de categorias est� atribu�da como null.
			this.categoriasSelect = new ArrayList<SelectItem>(); //Caso esteja, ser� instanciado o objeto "categoriaSelect" como um ArrayList de sele��o de itens.
			CategoriaRN categoriaRN = new CategoriaRN(); //Caso esteja, ser� instanciado um objeto "categoriaRN" da classe "CategoriaRN".
			List<Categoria> categorias = categoriaRN.listar(this.contextoBean.getUsuarioLogado()); //Ser� realizada a montagem da lista de categorias do usu�rio logado.
			this.montaDadosSelect(this.categoriasSelect, categorias, ""); //A �rvore receber� uma listagem de categorias com estrutura hier�rquica atrav�s do m�todo "montaDadosSelect".
		}
		return categoriasSelect; //� feito o retorno da lista.
	}

	private void montaDadosSelect(List<SelectItem> select, List<Categoria> categorias, String prefixo) { //M�todo respons�vel por montar uma lista de SelectItem, com deslocamento de espa�o (&nbsp) para representar uma estrutura hier�rquica.
		SelectItem item = null; //Atribui-se o objeto "item" da classe SelectItem" como null.
		if (categorias != null) { //Verifica se a lista de categorias � diferente de null.
			for (Categoria categoria : categorias) { //Caso esteja, ser� realizada um for percorrendo as categorias de toda a lista.
				item = new SelectItem(categoria, prefixo + categoria.getDescricao()); //Cada Item receber� uma instancia do construtor de SelectItem contendo dois par�metros (Valor do item, Texto que deve ser exibido). 
				item.setEscape(false); //O m�todo setEscape(false) servir� para que o JSF deixe o navegador interpretar o comando &nbsp como um espa�o.
				select.add(item); //M�todo respons�vel pela adi��o do item a listagem de categorias.
				this.montaDadosSelect(select, categoria.getFilhos(), prefixo + "&nbsp;&nbsp;"); //M�todo respons�vel pela recursividade.
			}
		}
	}

	public Categoria getEditada() {
		return editada;
	}

	public void setEditada(Categoria editada) {
		this.editada = editada;
	}

	public boolean isMostraEdicao() {
		return mostraEdicao;
	}

	public void setMostraEdicao(boolean mostraEdicao) {
		this.mostraEdicao = mostraEdicao;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

}


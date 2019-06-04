package br.com.financeiropapw.categoria;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.financeiropapw.usuario.Usuario;

public class CategoriaDAOHibernate implements CategoriaDAO {

	private Session session; //Declara��o de propriedade da Classe Session do Hibernate
	
	public void setSession(Session session) { //M�todo padr�o para inje��o da Session do Hibernate na classe CategoriaDAOHibernate
		this.session = session;
	}
	
	public Categoria salvar(Categoria categoria) {
		Categoria merged = (Categoria) this.session.merge(categoria); //O m�todo "session.merged" � respons�vel pela fus�o de uma categoria que est� fora do contexto de persistencia com uma inst�ncia que est� na mem�ria. Possui a mesma funcionalidade final do m�todo saveOrUpdate.
		this.session.flush(); //Se o o objeto j� existir no banco de dados, o SQL UPDATE s� ser� executado quando o "session.flush" for chamado.
		this.session.clear(); //O m�todo "session.clear()" remove da mem�ria do Hibernate todos os objetos carregados.
		return merged;
	}
	
	public void excluir(Categoria categoria) {
		categoria = (Categoria) this.carregar(categoria.getCodigo()); //� feito o carga de todas as categorias.
		this.session.delete(categoria); //M�todo respons�vel pela exclus�o em cascata.
		this.session.flush();
		this.session.clear();
	}
	
	public Categoria carregar(Integer categoria) {
		return (Categoria) this.session.get(Categoria.class, categoria); 
	}
	
	public List<Categoria> listar(Usuario usuario) {
	String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario"; //A HQL demonstra que o filtro "c.pai is null" garantira que somente os primeiros n�veis de categoria (DESPESA e RECEITA) sejam carregados. E a carga dos demais n�veis ser� feita pelo relacionamento @OneToMany dos filhos.
		Query query = this.session.createQuery(hql);
		query.setInteger("usuario", usuario.getCodigo());
		List<Categoria> lista = query.list();
		return lista;
	}
	
}

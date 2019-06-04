package br.com.financeiropapw.categoria;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.financeiropapw.usuario.Usuario;

@Entity
public class Categoria implements Serializable { //Representa��o da tabela no BD
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@ManyToOne //Um relacionamento muitos para um sempre gera uma chave estrangeira. Para que eu o Hibernate n�o gera um nome alet�rio, � permitido usar um nome amig�vel atrav�s da annotation "@ForeignKey".
	@JoinColumn(name = "categoria_pai", nullable = true, foreignKey = @ForeignKey(name = "fk_categoria_categoria")) //A "categoria_pai" ser� utilizada para criar um campo que autorreferencia a tabela. Pela primeira vez usa-se o atribuo "nullable" como true pois o primeiro n�vel de categorias (RECEITA e DESPESA) de um usu�rio n�o ter� uma categoria pai.
	private Categoria pai;
	
	@ManyToOne
	@JoinColumn(name = "usuario", foreignKey = @ForeignKey(name = "fk_categoria_usuario"))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Usuario usuario;
	
	private String descricao;
	
	private int fator;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //A annotation "@OneToMany" � utilizada para fazer a carga da lista da categoria filhos. Al�m disso, o atributo CascadeType.REMOVE configura a exclus�o dos filhos e netos, caso a categoria seja exclu�da.
	@JoinColumn(name = "categoria_pai", updatable = false) //O uso da "annotation" @JoinColumn ir� carregar todas categorias cujo campo "categoria_pai" seja igual ao c�digo da categoria atual. O atributo "updatable = false" permite editar uma categoria sem afetar seus filhos.
	@org.hibernate.annotations.OrderBy(clause = "descricao asc") //Configura��o da ordena��o da carga de registros filhos.
	private List<Categoria> filhos;
	
	public Categoria() {} //O Hibernate exige a cria��o de um construtor vazio nas classes de entidade em que h� um construtor personalizado como o caso abaixo.
	
	public Categoria(Categoria pai, Usuario usuario, String descricao, int fator) { //M�todo construtor que ser� utilizado para facilitar a cria��o de categorias no momento de montar a estrutura padr�o do usu�rio no momento do cadastro.
		this.pai = pai;
		this.usuario = usuario;
		this.descricao = descricao;
		this.fator = fator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}
	
}

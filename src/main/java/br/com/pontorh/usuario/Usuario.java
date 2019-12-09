package br.com.pontorh.usuario;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Usuario implements Serializable { //Classe que ter� uma tabela representada no Banco de Dados e que ser� propriedade da classe UsuarioBean.
	
	@Id
	@GeneratedValue
	private Integer codigo;
	private String nome;
	private String email;
	
	@org.hibernate.annotations.NaturalId
	private String login;
	
	private String senha;
	private boolean ativo;
	
	@ElementCollection(targetClass = String.class) //O uso do "targetClass = String.Class" definir� o tipo da classe que ser� carregada no conjunto (set) de valores (ex.ROLE_ADMINISTRADOR, ROLE_USUARIO) atrav�s da annotation "ElementCollection".
	@JoinTable(name = "usuario_permissao", //O uso do "name = usuario_permissao" definir� o nome da tabela filha no Banco de Dados atrav�s da annotation "JoinTable".
			   uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario", "permissao"})}, // O uso do "uniqueConstraints" criar� um �ndice �nico nas colunas da tabela filha ("usu�rio_permissao") n�o permitindo que um usu�rio tenha permiss�es repetidas.
			   joinColumns = @JoinColumn(name = "usuario")) //O uso do "joinColumns" definir� qual coluna da tabela filha ("usuario_permissao") a tabela pai ir� se relacionar.
	@Column(name = "permissao", length = 50) //O uso do "name = permissao" definir� qual o nome da coluna de permiss�o da tabela filha ("usuario_permissao") e o "length = 50" ser� o tamanho da coluna.
	private Set<String> permissao = new HashSet<String>();
			
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public Set<String> getPermissao() {
		return permissao;
	}
	public void setPermissao(Set<String> permissao) {
		this.permissao = permissao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissao == null) ? 0 : permissao.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (permissao == null) {
			if (other.permissao != null)
				return false;
		} else if (!permissao.equals(other.permissao))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}
	
}

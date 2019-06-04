package br.com.financeiropapw.web;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import br.com.financeiropapw.conta.Conta;
import br.com.financeiropapw.conta.ContaRN;
import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.usuario.UsuarioRN;

@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = 4693275947797122717L;
	private int codigoContaAtiva = 0;
	
	public Usuario getUsuarioLogado() { //M�todo que serve para obter o login do usu�rio remoto e realizar a inst�ncia do usu�rio
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser(); //Recebendo os dados do login do usu�rio
		if (login != null) { //Se o login for diferente de null
			UsuarioRN usuarioRN = new UsuarioRN(); //� feita a inst�ncia da classe UsuarioRN
			return usuarioRN.buscaPorLogin(login); //� feito o retorno do objeto instanciado pelo login
		}
		return null;
	}
	
	private Conta getContaAtivaPadrao() { //M�todo que serve para determinar qual ser� a conta ativa padr�o (favorita).
		ContaRN contaRN = new ContaRN();
		Conta contaAtiva = null;
		Usuario usuario = this.getUsuarioLogado();
		contaAtiva = contaRN.buscarFavorita(usuario); //Conta ativa ser� a conta favorita do usu�rio
		if (contaAtiva == null) { //Se a contaAtiva estiver nula
			List<Conta> contas = contaRN.listar(usuario); //� listada o conjunto de contas do usu�rios
			if (contas != null && contas.size() > 0) { //Se a lista de contas for diferente de null e maior que 0
				contaAtiva = contas.get(0); //A conta ativa ser� a conta na posi��o 0 da lista
			}
		}
		return contaAtiva; //� feito o retorno da conta ativa
	}
	
	public Conta getContaAtiva() { //M�todo que serve para fornecer a conta ativa (favorita) do momento. Se ainda n�o houve, ele obt�m a conta favorita do usu�rio logado, ou a primeira cadastrada.
		Conta contaAtiva = null;
		if (this.codigoContaAtiva == 0) {
			contaAtiva = this.getContaAtivaPadrao();
		} else {
			ContaRN contaRN = new ContaRN();
			contaAtiva = contaRN.carregar(this.codigoContaAtiva);
		}
		if (contaAtiva != null) {
			this.codigoContaAtiva = contaAtiva.getConta();
			return contaAtiva;
		}
		return null;
	}
	
	public void changeContaAtiva(ValueChangeEvent event) { //M�todo que serve para alterar a conta ativa no sistema
		this.codigoContaAtiva = (Integer) event.getNewValue();
	}
	
}

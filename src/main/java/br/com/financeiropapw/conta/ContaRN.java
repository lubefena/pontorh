package br.com.financeiropapw.conta;

import java.util.Date;
import java.util.List;

import br.com.financeiropapw.usuario.Usuario;
import br.com.financeiropapw.util.DAOFactory;

public class ContaRN {

	private ContaDAO contaDAO; //Declara��o de propriedade da interface contaDAO
	
	public ContaRN() {
		this.contaDAO = DAOFactory.criarContaDAO(); //Instancia��o da propriedade utilizando o DAOFactory no construtor
	}
	
	public void salvar(Conta conta) { //M�todo que realiza o cadastro ou atualiza��o de dados de uma conta
		conta.setDataCadastro(new Date()); //Durante o cadastro de uma conta, a data a ser informada � a data do ato do cadastro
		this.contaDAO.salvar(conta);
	}
	
	public void excluir(Conta conta) { //M�todo que realiza a exclus�o de uma conta
		this.contaDAO.excluir(conta);
	}
	
	public Conta carregar(Integer conta) { //M�todo que carrega uma �nica inst�ncia de uma conta com base em seu c�digo
		return this.contaDAO.carregar(conta);
	}
	
	public List<Conta> listar(Usuario usuario) { //M�todo que fornece uma lista com todas as contas
		return this.contaDAO.listar(usuario);
	}
	
	public Conta buscarFavorita(Usuario usuario) { //M�todo que carrega as informa��es da conta na tela logo ap�s o login
		return this.contaDAO.buscarFavorita(usuario);
	}
	
	public void tornarFavorita(Conta contaFavorita) { //M�todo que registra uma determinada conta banc�ria como favorita do usu�rio
		Conta conta = this.buscarFavorita(contaFavorita.getUsuario()); //Se obt�m a conta favorita atual
		if (conta != null) { //Se a conta estiver preenchida
			conta.setFavorita(false); //Atribui-se como false 
			this.contaDAO.salvar(conta); //E salva a conta atual como false 
		}
		contaFavorita.setFavorita(true); //Depois marca a conta recebida no par�metro como true
		this.contaDAO.salvar(contaFavorita); //E salva conta recebida
	}
}

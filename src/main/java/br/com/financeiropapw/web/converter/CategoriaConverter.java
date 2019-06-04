package br.com.financeiropapw.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

import br.com.financeiropapw.categoria.*;

@FacesConverter(forClass = Categoria.class) //A regra de funcionamento do conversos � determinada pela annotation @FacesConverter, que pode ser efetuad de duas maneiras; forClass ou value.
public class CategoriaConverter implements Converter { 
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) { //M�todo executado quando uma informa��o vem no formato texto do navegador para ser atribu�da a uma propriedade na classe Bean.
		if (value != null && value.trim().length() > 0) { //Verifica se o valor da "String" s�o diferentes de null e o tamanho maior que 0. 
			Integer codigo = Integer.valueOf(value); //Caso seja, instancia um objeto c�digo do tipo Integer com o valor da String.
			try {
				CategoriaRN categoriaRN = new CategoriaRN(); //Instancia um objeto da classe "CategoriaRN" atrav�s do seu construtor.
				return categoriaRN.carregar(codigo); //Retorna o c�digo do objeto instanciado.
			} catch (Exception e) { //Caso n�o consiga realizar a convers�o � lan�ada a excess�o.
				throw new ConverterException(
						"N�o foi poss�vel encontrar a categoria de c�digo " + value + ". " + e.getMessage());
			}
		}
		return null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) { //M�todo executado quando uma informa��o vem da classe Bean para ser exibida na tela. 
		if (value != null) { //Verifica se o o valor do "Objeto" � diferente de null.
			Categoria categoria = (Categoria) value; //Caso seja, ser� feito o cast do valor.
			return categoria.getCodigo().toString(); //E ser� retornado o c�digo da categoria em formato de texto.
		}
		return "";
	}

}

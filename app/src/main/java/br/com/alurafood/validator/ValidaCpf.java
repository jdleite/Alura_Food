package br.com.alurafood.validator;

import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidaCpf implements Validador{

    private static final String CPF_INVALIDO = "Cpf inválido";
    private static final String DEVE_TER_ONZE_DIGITOS = "Cpf precisa ter 11 digitos";
    private static final String ERRO_FORMATO_CPF = "erro de formatação";
    private final TextInputLayout textInputCpf;
    private final EditText campoCpf;
    private final ValidacaoPadrao validadorPadrao;
    final CPFFormatter formatador = new CPFFormatter();

    public ValidaCpf(TextInputLayout textInputCpf) {
        this.textInputCpf = textInputCpf;
        this.campoCpf = textInputCpf.getEditText();
        this.validadorPadrao = new ValidacaoPadrao(textInputCpf);
    }


    private boolean validaCalculoCpf(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            textInputCpf.setError(CPF_INVALIDO);
            return false;
        }
        return true;
    }

    private String getCpf() {

        return campoCpf.getText().toString();
    }

    @Override
    public boolean estaValido(){
        if(!validadorPadrao.estaValido()) return false;
        String cpf = getCpf();
        String cpfSemFormato = cpf;
        try {
            cpfSemFormato = formatador.unformat(cpf);
        }catch (IllegalArgumentException e){
            Log.e(ERRO_FORMATO_CPF,e.getMessage());
        }
        if(!validaCampoComOnzeDigitos(cpfSemFormato)) return false;
        if(!validaCalculoCpf(cpfSemFormato)) return false;
        adicionaFormatacao(cpfSemFormato);
        return true;
    }

    private void adicionaFormatacao(String cpf) {
        String cpfFormatado = formatador.format(cpf);
        campoCpf.setText(cpfFormatado);
    }


    private boolean validaCampoComOnzeDigitos(String cpf) {

        if (cpf.length() != 11) {
            textInputCpf.setError(DEVE_TER_ONZE_DIGITOS);
            return false;
        }
        return true;


    }
}

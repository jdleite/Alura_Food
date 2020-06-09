package br.com.alurafood.validator;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import br.com.alurafood.formatter.FormataTelefoneComDdd;

public class ValidaTelefoneComDdd implements Validador{

    public static final String DEVE_TER_ENTRE_10_A_11_DIGITOS = "Telefone deve ter entre 10 a 11 digitos";
    private final TextInputLayout textInputTelefoneComDdd;
    private final EditText campoTelefoneComDdd;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormataTelefoneComDdd formatador = new FormataTelefoneComDdd();

    public ValidaTelefoneComDdd(TextInputLayout textInputTelefoneComDdd){
        this.textInputTelefoneComDdd = textInputTelefoneComDdd;
        this.validacaoPadrao = new ValidacaoPadrao(textInputTelefoneComDdd);
        this.campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
    }

    private boolean validaEntreDezouOnzeDigitos(String telefone){
        int digitos = telefone.length();

        if (digitos < 10 || digitos > 11) {
            textInputTelefoneComDdd.setError(DEVE_TER_ENTRE_10_A_11_DIGITOS);
            return false;

        }
        return true;

    }

    @Override
    public boolean estaValido(){
        if (!validacaoPadrao.estaValido()) return false;
        String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        String telefoneComDddSemFormatacao = formatador.remove(telefoneComDdd);
        if (!validaEntreDezouOnzeDigitos(telefoneComDddSemFormatacao)) return false;
        adicionaFormatacao(telefoneComDddSemFormatacao);
        return true;
    }


    private void adicionaFormatacao(String telefoneComDdd){
//        StringBuilder sb = new StringBuilder();
////        int digitos = telefoneComDdd.length();
////        for (int i = 0; i < digitos; i++) {
////            if (i == 0){
////                sb.append(" (");
////            }
////            char digito = telefoneComDdd.charAt(i);
////            sb.append(digito);
////            if (i == 1){
////                sb.append(") ");
////            }
////
////            if (digitos == 10 && i == 5 || digitos == 11 && i == 6){
////                sb.append("-");
////            }
////        }
////        String telefoneComDddFormatado = sb.toString();

        String telefoneComDddFormatado = formatador.formata(telefoneComDdd);
        campoTelefoneComDdd.setText(telefoneComDddFormatado);

    }


}

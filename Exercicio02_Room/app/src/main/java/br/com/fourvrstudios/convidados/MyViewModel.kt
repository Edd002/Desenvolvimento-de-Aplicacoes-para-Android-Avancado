package br.com.fourvrstudios.convidados

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.fourvrstudios.convidados.db.Convidado
import br.com.fourvrstudios.convidados.db.Repositorio
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyViewModel(private val repositorio: Repositorio) : ViewModel() {

    val convidados = repositorio.listaConvidados;
    private var convidadoUpdateDelete: Convidado?;
    private var isUpdateOrDelete = false;

    val inputNome = MutableLiveData<String?>();
    val inputEmail = MutableLiveData<String?>();
    val inputBusca = MutableLiveData<String?>();
    val userMessage = MutableLiveData<String?>();
    val hasFoundConvidado = MutableLiveData<Boolean?>();
    val onClear = MutableLiveData<Boolean>();
    val saveUpdateBtnTxt = MutableLiveData<String>();
    val clearDeleteBtnTxt = MutableLiveData<String>();

    init {
        convidadoUpdateDelete = null;
        saveUpdateBtnTxt.value = "Salvar";
        clearDeleteBtnTxt.value = "Limpar";
        onClear.value = false;
    }

    fun insert (convidado: Convidado): Job = viewModelScope.launch {
        repositorio.insert(convidado);
        userMessage.value = "Convidado registrado com sucesso.";
    }

    fun delete(convidado: Convidado): Job = viewModelScope.launch {
        repositorio.delete(convidado);
        userMessage.value = "Convidado excluído com sucesso.";
        reset(null, false, null, null, "Salvar", "Limpar");
    }

    fun update(convidado: Convidado): Job = viewModelScope.launch {
        repositorio.update(convidado);
        userMessage.value = "Convidado atualizado com sucesso.";
        reset(null, false, null, null, "Salvar", "Limpar");
    }

    fun clearAll(): Job = viewModelScope.launch {
        repositorio.clearAll();
    }

    fun search(): Job = viewModelScope.launch {
        var convidadoEncontrado = repositorio.selectConvidado(inputBusca.value.toString());
        if (convidadoEncontrado != null) {
            startUpdateDelete(convidadoEncontrado);
            hasFoundConvidado.value = true;
        } else {
            hasFoundConvidado.value = false;
        }
    }

    fun startUpdateDelete(convidado: Convidado) {
        reset(convidado, true, convidado.email, convidado.name, "Alterar", "Deletar");
    }

    fun saveUpdate() {
        val invalidNome = inputNome.value.isNullOrBlank();
        val invalidEmail = !isValidEmail(inputEmail.value.toString());
        if (invalidNome || invalidEmail) {
            if (invalidNome && !invalidEmail) {
                userMessage.value = "O nome informado não pode estar em branco."
            } else if (!invalidNome && invalidEmail) {
                userMessage.value = "O email informato não está em um formato válido."
            } else {
                userMessage.value = "O nome informado não pode estar em branco e o email informato não está em um formato válido."
            }
        } else {
            if (isUpdateOrDelete) {
                convidadoUpdateDelete?.name = inputNome.value!!;
                convidadoUpdateDelete?.email = inputEmail.value!!;
                update(convidadoUpdateDelete!!);
            } else {
                val nome = inputNome.value!!;
                val email = inputEmail.value!!;
                insert(Convidado(0, nome, email));
                inputNome.value = null;
                inputEmail.value = null;
            }
        }
    }

    fun clearOrDelete() {
        if (isUpdateOrDelete) {
            delete(convidadoUpdateDelete!!);
        } else {
            setOnClearState(true);
        }
    }

    fun setOnClearState(state: Boolean) {
        onClear.value = state;
    }

    private fun reset(convidadoUpdateDelete: Convidado?, isUpdateOrDelete: Boolean, inputEmail: String?, inputNome: String?, saveUpdateBtnTxt: String?, clearDeleteBtnTxt: String?) {
        this.convidadoUpdateDelete = convidadoUpdateDelete;
        this.isUpdateOrDelete = isUpdateOrDelete;
        this.inputEmail.value = inputEmail;
        this.inputNome.value = inputNome;
        this.saveUpdateBtnTxt.value = saveUpdateBtnTxt;
        this.clearDeleteBtnTxt.value = clearDeleteBtnTxt;
    }

    private fun isValidEmail(email: String): Boolean = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
}

class ViewModelFactory(private val repositorio: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(repositorio) as T;
        }
        throw IllegalArgumentException("ViewModel Desconhecida");
    }
}
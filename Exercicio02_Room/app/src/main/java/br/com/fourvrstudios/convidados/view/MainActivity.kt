package br.com.fourvrstudios.convidados.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.fourvrstudios.convidados.R

/*
Aprimoramentos do Aluno

- Observer criado para o MutableLiveData hasFoundConvidado a fim de apontar se um convidado foi encontrado para ser editado
- Observer criado para o MutableLiveData userMessage a fim de identificar quando exibir mensagens de respostas ao usuário
- Métodos onPause() e onResume() criados em CrudFragment.kt para adicionar ou remover os observers caso esteja ou não no fragment de CRUD
- Query de busca também verificando e-mail do convidado ao realizar a buscar
- Mensagens de convidado atualizado, convidado registrado, nome inválido e e-mail inválido implementadas juntamente com suas validações
- Lista de convidados ordenada por nome em ordem alfabética
- Método para resetar variáveis criado em MyViewModel.kt

 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
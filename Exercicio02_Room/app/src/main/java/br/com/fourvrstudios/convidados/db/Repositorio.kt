package br.com.fourvrstudios.convidados.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repositorio(private val dao: ConvidadoDAO) {
    val listaConvidados = dao.listarConvidados();

    suspend fun insert(convidado: Convidado) {
        dao.inserirConvidado(convidado);
    }

    suspend fun update(convidado: Convidado) {
        dao.updateConvidado(convidado);
    }

    suspend fun delete(convidado: Convidado) {
        dao.deleteConvidado(convidado);
    }

    suspend fun clearAll() {
        dao.deleteAll();
    }

    suspend fun selectConvidado(busca : String) : Convidado? {
        return withContext(Dispatchers.IO) {
            return@withContext dao.buscarConvidado(busca);
        }
    }
}
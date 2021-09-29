package br.com.fourvrstudios.convidados.db

import androidx.lifecycle.LiveData
import androidx.room.*

// DAO - Data Access Object
@Dao
interface ConvidadoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirConvidado(convidado: Convidado);

    @Update
    suspend fun updateConvidado(convidado: Convidado);

    @Delete
    suspend fun deleteConvidado(convidado: Convidado);

    @Query("SELECT * FROM convidado_data_table")
    fun listarConvidados(): LiveData<List<Convidado>>;

    @Query("DELETE FROM convidado_data_table")
    suspend fun deleteAll();

    @Query(value = "SELECT * FROM convidado_data_table WHERE convidado_name LIKE '%' || :search || '%'")
    suspend fun buscarConvidado(search: String): Convidado?;
}
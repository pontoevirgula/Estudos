package com.chslcompany.estudos.ativos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActiveViewModel( private val useCase : ActiveUseCase) : ViewModel() {

    fun saveRendaFixa(isEdit: Boolean, rendaFixaActive: RendaFixaActive){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productToSave = rendaFixaActive
                useCase.saveProductRendaFixa(productToSave)
            }
        }
    }

    fun saveFundo(isEdit: Boolean, fundoActive: FundoActive){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productToSave = fundoActive
                useCase.saveProductFundo(productToSave)
            }
        }
    }

    suspend fun getInvestmentCount() : Int? {
        return withContext(Dispatchers.IO) {
            useCase.getActiveProducts()?.sumOf {
                it.rendaFixaList.size + it.fundoList.size
            }
        }
    }

    suspend fun setEmptyList(){
        withContext(Dispatchers.IO) {
            useCase.removeAll()
        }

    }
}
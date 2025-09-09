package com.chslcompany.estudos.ativos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.estudos.ativos.usecase.ActiveUseCase
import com.chslcompany.estudos.ativos.util.InvestmentType
import com.chslcompany.estudos.model.ActiveProduct
import com.chslcompany.estudos.model.FundoActive
import com.chslcompany.estudos.model.RendaFixaActive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActiveViewModel( private val useCase : ActiveUseCase) : ViewModel() {

    fun saveRendaFixa(isEdit: Boolean, rendaFixaActive: RendaFixaActive){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productToSave = if (isEdit){
                    useCase.getRendaFixaSelected()?.copy(
                        name = rendaFixaActive.name,
                        qtd = rendaFixaActive.qtd
                    ) ?: rendaFixaActive
                } else {
                    rendaFixaActive
                }
                useCase.saveProductRendaFixa(productToSave)
            }
        }
    }

    fun saveFundo(isEdit: Boolean, fundoActive: FundoActive){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val productToSave = if (isEdit){
                    useCase.getFundsSelected()?.copy(
                        name = fundoActive.name,
                        code = fundoActive.code
                    ) ?: fundoActive
                } else {
                    fundoActive
                }
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

    suspend fun getAllInvestmentsSaved() : List<ActiveProduct>?{
        return withContext(Dispatchers.IO) {
            useCase.getActiveProducts()
        }
    }

    fun saveRendaFixaSelected(rendaFixaActive: RendaFixaActive, investmentType: InvestmentType) {
        useCase.saveProductRendaFixaSelected(rendaFixaActive)
    }

    fun saveFundsSelected(fundoActive: FundoActive, investmentType: InvestmentType) {
        useCase.saveProductFundsSelected(fundoActive)
    }

    fun getRendaFixaSelected(): RendaFixaActive? {
        return useCase.getRendaFixaSelected()
    }

    fun getFundsSelected(): FundoActive? {
        return useCase.getFundsSelected()
    }
}
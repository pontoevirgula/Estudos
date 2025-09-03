package com.chslcompany.estudos.ativos

import com.chslcompany.estudos.MyPreferences

class ActiveUseCase(private val myPreferences: MyPreferences) {

    suspend fun saveProductRendaFixa(rendaFixaActive: RendaFixaActive) {
        myPreferences.upsertRendaFixaItem(rendaFixaActive)
    }

    suspend fun saveProductFundo(fundoActive: FundoActive) {
        myPreferences.upsertFundoItem(fundoActive)
    }

    fun getActiveProducts() : List<ActiveProduct>? {
        return myPreferences.getActiveProductList()
    }

    suspend fun removeAll() {
        myPreferences.removeAll()
    }
}
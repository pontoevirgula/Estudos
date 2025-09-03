package com.chslcompany.estudos.ativos

import com.chslcompany.estudos.MyPreferences

class ActiveUseCase(private val myPreferences: MyPreferences) {

    fun saveProductRendaFixa(rendaFixaActive: RendaFixaActive) {
        myPreferences.upsertRendaFixaItem(rendaFixaActive)
    }

    fun saveProductFundo(fundoActive: FundoActive) {
        myPreferences.upsertFundoItem(fundoActive)
    }

    fun getActiveProducts() : List<ActiveProduct>? {
        return myPreferences.getActiveProductList()
    }

    fun removeAll() {
        myPreferences.removeAll()
    }
}
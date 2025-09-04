package com.chslcompany.estudos.ativos.usecase

import com.chslcompany.estudos.MyPreferences
import com.chslcompany.estudos.model.ActiveProduct
import com.chslcompany.estudos.model.FundoActive
import com.chslcompany.estudos.model.RendaFixaActive

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
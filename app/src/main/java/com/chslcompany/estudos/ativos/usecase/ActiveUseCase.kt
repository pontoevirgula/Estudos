package com.chslcompany.estudos.ativos.usecase

import com.chslcompany.estudos.ativos.util.MyPreferences
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

    fun removeRendaFixaItem(id : String) {
        myPreferences.removeRendaFixaItem(id)
    }

    fun removeFundoItem(id : String) {
        myPreferences.removeFundoItem(id)
    }

    fun saveProductRendaFixaSelected(rendaFixaActive: RendaFixaActive) {
        myPreferences.saveRendaFixaSelected(rendaFixaActive)
    }

    fun getRendaFixaSelected(): RendaFixaActive? {
        return myPreferences.getRendaFixaSelected()
    }

    fun getFundsSelected(): FundoActive? {
        return myPreferences.getFundsSelected()
    }

    fun saveProductFundsSelected(fundoActive: FundoActive) {
        myPreferences.saveFundsSelected(fundoActive)
    }

}
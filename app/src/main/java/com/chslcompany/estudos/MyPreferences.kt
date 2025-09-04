package com.chslcompany.estudos

import android.content.Context
import android.content.SharedPreferences
import com.chslcompany.estudos.model.RendaFixaActive
import com.google.gson.Gson
import androidx.core.content.edit
import com.chslcompany.estudos.model.ActiveProduct
import com.chslcompany.estudos.model.FundoActive
import com.google.gson.reflect.TypeToken

class MyPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val gson = Gson()

    private fun updateSingleton(
        transform: (ActiveProduct) -> ActiveProduct
    ) {
        val list = getActiveProductList() ?: emptyList()
        val before = list.firstOrNull()
        val after = transform(before ?: ActiveProduct())
        val out = listOf(after)

        saveActiveProduct(out)
    }

    fun saveActiveProduct(items: List<ActiveProduct>) {
        sharedPreferences.edit {
            val jsonString = gson.toJson(items)
            putString(ACTIVE_PRODUCT_LIST, jsonString)
        }
    }

    fun getActiveProductList(): List<ActiveProduct>? {
        val jsonString = sharedPreferences.getString(ACTIVE_PRODUCT_LIST, null)
        if (jsonString == null) {
            return null
        }
        val listType = object : TypeToken<List<ActiveProduct>>() {}.type
        return gson.fromJson(jsonString, listType)
    }


    fun upsertRendaFixaItem(
        item: RendaFixaActive
    ) = updateSingleton { inv ->
        val exists = inv.rendaFixaList.any { it.id == item.id }
        val newList = if (exists) {
            inv.rendaFixaList.map { if (it.id == item.id) item else it }
        } else {
            inv.rendaFixaList + item
        }
        inv.copy(rendaFixaList = newList)
    }

    fun upsertFundoItem(
        item: FundoActive
    ) = updateSingleton { inv ->
        val exists = inv.fundoList.any { it.id == item.id }
        val newList = if (exists) {
            inv.fundoList.map { if (it.id == item.id) item else it }
        } else {
            inv.fundoList + item
        }
        inv.copy(fundoList = newList)
    }

    fun removeAll() = updateSingleton { inv ->
        inv.copy(rendaFixaList = emptyList(), fundoList = emptyList())
    }


    companion object {
        const val ACTIVE_PRODUCT_LIST = "ACTIVE_PRODUCT_LIST"
        const val ACTIVE_RENDA_SELECT = "ACTIVE_RENDA_SELECT"
        const val PREF_NAME = "MyPrefs"
    }

}
package com.chslcompany.estudos.ativos.util

enum class InvestmentType (val value : String){
    FIXED_INCOME("Renda Fixa"),
    FUNDS("Fundo");

    companion object {
        fun fromValue(value: String): InvestmentType? =
            InvestmentType.entries.find { it.value == value }
    }
}
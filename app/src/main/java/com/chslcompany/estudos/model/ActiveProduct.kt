package com.chslcompany.estudos.model

import java.util.UUID

data class ActiveProduct(
    val rendaFixaList : List<RendaFixaActive> = emptyList(),
    val fundoList : List<FundoActive> = emptyList()
)

data class RendaFixaActive(
    val id : String = UUID.randomUUID().toString(),
    val name : String,
    val qtd : Int
)

data class FundoActive(
    val id : String = UUID.randomUUID().toString(),
    val name : String,
    val code : String
)

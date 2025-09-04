package com.chslcompany.estudos.ativos.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chslcompany.estudos.ativos.usecase.ActiveUseCase
import com.chslcompany.estudos.ativos.viewmodel.ActiveViewModel

class ViewModelFactory(private val useCase: ActiveUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ActiveViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
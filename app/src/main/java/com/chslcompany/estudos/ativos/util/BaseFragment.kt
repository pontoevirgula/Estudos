package com.chslcompany.estudos.ativos.util

import androidx.fragment.app.Fragment
import com.chslcompany.estudos.MyPreferences
import com.chslcompany.estudos.ativos.usecase.ActiveUseCase

open class BaseFragment : Fragment() {

    protected val myPreferences : MyPreferences by lazy {
        MyPreferences(requireContext().applicationContext)
    }

    protected val activeUseCase : ActiveUseCase by lazy {
        ActiveUseCase(myPreferences)
    }

    protected val viewModelFactory : ViewModelFactory by lazy {
        ViewModelFactory(activeUseCase)
    }
}
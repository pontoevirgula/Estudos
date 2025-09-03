package com.chslcompany.estudos

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chslcompany.estudos.ativos.SendInvestmentFragment
import com.chslcompany.estudos.status.OrderingFragment
import com.chslcompany.estudos.status.StatusFragment


class MainActivity : AppCompatActivity(), StatusFragment.OnFragmentInteractionListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnFragment1: Button = findViewById(R.id.btn_fragment_1)
        val btnFragment2: Button = findViewById(R.id.btn_fragment_2)
        val buttonContainer: LinearLayout = findViewById(R.id.button_container)

        btnFragment1.setOnClickListener {
            buttonContainer.visibility = View.GONE
            loadFragment(StatusFragment())
        }

        btnFragment2.setOnClickListener {
            buttonContainer.visibility = View.GONE
            loadFragment(SendInvestmentFragment())
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                buttonContainer.visibility = View.GONE
            } else {
                buttonContainer.visibility = View.VISIBLE
            }
        }


    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Adiciona à pilha de retorno
            .commit()
    }

    override fun onNavigateToFragmentB() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OrderingFragment())
            .addToBackStack(null) // Adiciona à pilha de retorno para permitir voltar
            .commit()
    }



}
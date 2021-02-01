package ru.trinitydigital.pagingcashe.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trinitydigital.pagingcashe.R

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private val adapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel() {
        vm.data.observe(this, {
            Toast.makeText(this, it.total_count.toString(), Toast.LENGTH_LONG).show()
        })
//        adapter.submitData()
    }
}
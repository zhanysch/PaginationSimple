package ru.trinitydigital.pagingcashe.ui.paginat_withoutcashe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.trinitydigital.pagingcashe.R
import ru.trinitydigital.pagingcashe.databinding.ActivityMainBinding
import ru.trinitydigital.pagingcashe.databinding.ActivityWithoutCasheBinding
import ru.trinitydigital.pagingcashe.ui.main.MainAdapter
import ru.trinitydigital.pagingcashe.utils.hideKeyboard

class WithoutCasheActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWithoutCasheBinding
    private val vm by viewModel<WithoutCasheViewModel>()
    private val adapter by lazy { MainAdapter() }
    private var searchJob : Job? = null

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutCasheBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        setupListeners()
    }

    private fun setupRecycler() {
        binding.recycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = GithubLoadStateAdapter(){adapter.retry()},  // добавляем еще адаптер чтоб, снизу когда загружютс итемы выходил ProggresBar и кнопка поыптаться снова
            footer = GithubLoadStateAdapter(){adapter.retry()}
        )
    }

    @ExperimentalPagingApi
    private fun setupListeners() {
        binding.etSearch.setOnEditorActionListener { v, actionId, Event ->
           if (actionId ==  EditorInfo.IME_ACTION_SEARCH){
               updateSearchRepository()
               hideKeyboard()           // клавиатура уходит после набора текста
                true
           }else
               false
        }

        adapter.addLoadStateListener {loadState ->          // анимаци
            binding.recycler.isVisible = loadState.source.refresh is LoadState.NotLoading
           binding.progesBar.isVisible  = loadState.source.refresh is LoadState.Loading      /// анимация прогрессбар
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error  //анимац кнопки если произошла ошибка
        }
        binding.btnRetry.setOnClickListener {
            adapter.retry()   // обработка при ошибке, если интернет отвалился чтоб заново загрузил данные
        }
    }

    @ExperimentalPagingApi
    fun updateSearchRepository(){
        binding.etSearch.text.trim().let {
            if (it.isNotEmpty())
                search(it.toString())
        }
    }

    @ExperimentalPagingApi
    private fun search(query: String){
        searchJob?.cancel()   // seacrhJob corutine при введени Edittext было немного торможение
       searchJob = lifecycleScope.launch {
            vm.getPagingData(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
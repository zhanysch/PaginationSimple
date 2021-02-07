package ru.trinitydigital.pagingcashe.ui.paginat_withoutcashe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.trinitydigital.pagingcashe.R
import ru.trinitydigital.pagingcashe.databinding.ItemLoadStateFooterBinding

class GithubLoadStateAdapter(private val retry: ()-> Unit): LoadStateAdapter<GithubLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: GithubLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GithubLoadStateViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_load_state_footer,parent,false)
        val binding = ItemLoadStateFooterBinding.bind(view)
        return GithubLoadStateViewHolder(binding,retry)
    }
}

class GithubLoadStateViewHolder(private val binding: ItemLoadStateFooterBinding,private val retry: ()-> Unit): RecyclerView.ViewHolder(binding.root){
    init {
        binding.btnRetry.setOnClickListener {
            retry.invoke()
        }
    }
    fun  bind(loadState: LoadState) {
        if (loadState is LoadState.Error){
           binding.tvError.text = loadState.error.localizedMessage
        }
        binding.progesBar.isVisible = loadState is LoadState.Loading   //если происходит ошибка внутри списка выходит прогрессбар
        binding.btnRetry.isVisible = loadState !is LoadState.Loading   //если происходит ошибка внутри списка выходит кнопка перзагрузить
        binding.tvError.isVisible = loadState !is LoadState.Loading   ///если происходит ошибка внутри списка выходит текст  ошибка!!!

    }

}
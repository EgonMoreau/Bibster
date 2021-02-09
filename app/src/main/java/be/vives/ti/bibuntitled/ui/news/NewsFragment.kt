package be.vives.ti.bibuntitled.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import be.vives.ti.bibuntitled.databinding.FragmentNewsBinding

/**
 * A simple [Fragment] subclass.
 * @author Joshua Tack
 */
class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = NewsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
        binding.viewModel = viewModel

        binding.newsList.adapter = NewsAdapter(NewsAdapter.NewsItemListener { newsItem ->
            val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(newsItem)
            view?.findNavController()?.navigate(action)
        })

        return binding.root
    }
}


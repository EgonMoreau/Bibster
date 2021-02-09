package be.vives.ti.bibuntitled.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import be.vives.ti.bibuntitled.databinding.ListNewsItemBinding

/** @author Joshua Tack **/
class NewsListItemFragment : Fragment() {

    private lateinit var binding: ListNewsItemBinding
    private lateinit var navController: NavController
    private val args: NewsDetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = NewsListItemFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListNewsItemBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.newsItem = args.newsItem
        //binding.description.loadDataWithBaseURL(null, args.newsItem.description, "text/html", "utf-8", null)
        return binding.root
    }
}
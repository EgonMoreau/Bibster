package be.vives.ti.bibuntitled.ui.news

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import be.vives.ti.bibuntitled.databinding.FragmentNewsDetailBinding

/** @author Joshua Tack */
class NewsDetailFragment : Fragment() {

    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var binding: FragmentNewsDetailBinding
    val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.newsItem = args.newsItem

        // Clickable <a> tags
        binding.description.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

}

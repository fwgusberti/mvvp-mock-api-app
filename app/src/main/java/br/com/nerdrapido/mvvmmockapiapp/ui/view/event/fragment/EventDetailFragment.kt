package br.com.nerdrapido.mvvmmockapiapp.ui.view.event.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import br.com.nerdrapido.mvvmmockapiapp.R
import br.com.nerdrapido.mvvmmockapiapp.presentation.model.Event
import br.com.nerdrapido.mvvmmockapiapp.presentation.viewModel.event.EventViewModel
import br.com.nerdrapido.mvvmmockapiapp.ui.helper.ViewHelper
import kotlinx.android.synthetic.main.fragment_event_detail.*
import org.koin.android.ext.android.inject


/**
 * Created By FELIPE GUSBERTI @ 11/08/2020
 */
class EventDetailFragment : Fragment() {

    private val viewModel: EventViewModel by activityViewModels()

    private val viewHelper: ViewHelper by inject()

    private lateinit var event: Event

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_event_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOptionsMenu()
        viewModel.getEventSelected().observe(viewLifecycleOwner, Observer {
            event = it
            checkInBt.text = getString(R.string.btn_fragment_event_detail_check_in_btn)
            checkInBt.isEnabled = true
            hydrateEvent(it)
        })
        viewModel.getEventCheckInSuccess().observe(viewLifecycleOwner, Observer {
            checkInBt.text =
                getString(R.string.btn_fragment_event_detail_check_in_btn_already_checked)
            checkInBt.isEnabled = false
        })
        checkInBt.setOnClickListener {
            viewModel.onCheckInWanted()
        }
        toolbar.setNavigationOnClickListener { viewModel.onBackPressed() }

    }

    private fun setOptionsMenu() {
        setHasOptionsMenu(true)
        toolbar.inflateMenu(R.menu.menu_share)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_item_share) {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, event.title)
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getShareBody())
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
            }
            true
        }
    }

    private fun getShareBody(): String {
        return getString(
            R.string.share_event_body,
            getString(R.string.app_name),
            event.title,
            event.description,
            event.latitude,
            event.longitude
        )
    }

    private fun hydrateEvent(event: Event) {
        collapsingToolbar.title = event.title
        overviewTv.text = getString(
            R.string.fragment_event_detail_overview,
            event.title,
            event.description
        )
        viewHelper.loadImageUrlIntoView(event.image, eventCoverIv)
        // aqui é feita a mágica para que o titulo da toolbar se adapte ao conteúdo.
        // A margem do título fica atrelada ao tamanho da overview do livro e é animada no scroll
        overviewTv.addOnLayoutChangeListener { _, _, top, _, _, _, _, _, _ ->
            collapsingToolbar.expandedTitleMarginBottom = titleOffsetPlaceholder.bottom - top
        }
        detailInfoContainer.removeAllViews()
        viewHelper.addInfoView(
            requireContext(),
            getString(R.string.fragment_event_detail_title),
            event.title,
            detailInfoContainer
        )
        viewHelper.addInfoView(
            requireContext(),
            getString(R.string.fragment_event_detail_valor),
            getString(R.string.item_event_list_money, event.price),
            detailInfoContainer

        )
        viewHelper.addInfoView(
            requireContext(),
            getString(R.string.fragment_event_detail_description),
            event.description,
            detailInfoContainer,
            true
        )
    }


}
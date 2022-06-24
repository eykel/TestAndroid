package com.android.test.testandroid.ui.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.test.testandroid.ui.helper.Constants.TEXT_TYPE
import com.android.test.testandroid.R
import com.android.test.testandroid.data.model.Event
import com.android.test.testandroid.databinding.CustomAlertDialogBinding
import com.android.test.testandroid.databinding.FragmentEventDetailBinding
import com.android.test.testandroid.ui.helper.*
import com.android.test.testandroid.ui.view_model.EventDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EventDetailFragment : Fragment() {

    private val args: EventDetailFragmentArgs by navArgs()
    private val viewModel: EventDetailViewModel by viewModel()

    private val viewBinding : FragmentEventDetailBinding by lazy {
        FragmentEventDetailBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView(args.item)
    }

    private fun setupObservers() {
        observe(viewModel.progressLiveData){
            showProgress(it)
        }
        observe(viewModel.error){
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        observe(viewModel.checkInDone){
            updateCheckInButton()
        }
    }

    private fun showProgress(showProgress: Boolean) {
        with(viewBinding){
            if(showProgress){
                progress.visible()
                svContent.gone()
            }else {
                svContent.visible()
                progress.gone()
            }
        }
    }

    private fun setupView(event: Event) {
        setupToolbar()
        with(viewBinding){
            ivEventImage.loadImageUrl(event.image)
            tvEventName.text = event.title
            tvEventDate.text = event.date
            tvEventDescription.text = event.description
            tvEventPrice.text = event.price
            tvEventLocation.text = getAddress(event.latitude, event.longitude)
            btCheckIn.setOnClickListener {
                showAlertDialog()
            }
        }
    }
    private fun showAlertDialog(){
        val customBinding : CustomAlertDialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(customBinding.root).create()
        customBinding.btCancel.setOnClickListener {
            dialog.dismiss()
        }
        customBinding.btSend.setOnClickListener {
            hideKeyboard()
            if (validateFields(customBinding)){
                viewModel.doCheckIn(
                    name = customBinding.etName.text.toString(),
                    email = customBinding.etEmail.text.toString(),
                    eventId = args.item.id
                )
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    private fun setupToolbar(){
        with(viewBinding){
            toolbar.tvTitle.text = getString(R.string.tv_event_text)
            toolbar.ivBack.visible()
            toolbar.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
            toolbar.btShare.visible()
            toolbar.btShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
                    type = TEXT_TYPE
                }
                startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)))
            }
        }
    }

    private fun validateFields(customBinding: CustomAlertDialogBinding) : Boolean{
        /**
         * In a real case, it's better to do a textWatcher, to do the validation in realTime.
         * Another option is use data binding to disable send button according of viewModel variables,
         * or if there's a lot of verifications to do, send it to viewModel and verify there.
         * In one of this cases, we could use binding.textView.error = "error message"
         * Here, a sample app and the meaning is a company test, i wont implement neither textWatch nor data binding
         * for this test =)
         */
        customBinding.tvErrorMessage.isVisible
        with(customBinding){
            val verify = etEmail.text.toString().isValidEmail() && !etName.text.isNullOrEmpty()
            tvErrorMessage.isVisible = !verify
            return verify
        }
    }

    private fun updateCheckInButton() {
        with(viewBinding){
            btCheckIn.isEnabled = false
            btCheckIn.text = getString(R.string.bt_check_in_done)
            btCheckIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_green))
            btCheckIn.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_check_circle_24)
            btCheckIn.iconTint = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.secondary_green))
        }
    }

}
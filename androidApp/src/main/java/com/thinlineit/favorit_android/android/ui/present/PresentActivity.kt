package com.thinlineit.favorit_android.android.ui.present

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.ActivityPresentBinding
import com.thinlineit.favorit_android.android.util.observeToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresentActivity : AppCompatActivity() {
    private val viewModel: PresentViewModel by lazy {
        ViewModelProvider(this)[PresentViewModel::class.java]
    }
    private val binding: ActivityPresentBinding by lazy {
        ActivityPresentBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@PresentActivity
            viewModel = this@PresentActivity.viewModel
        }
    }

    private val imm by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private var numberKeyPadBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (binding.numberKeyPad.visibility == View.VISIBLE) {
                binding.numberKeyPad.visibility = View.GONE
                isEnabled = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(numberKeyPadBackPressedCallback)
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.presentPhoto.observe(this) {
            binding.photoImageView.setImageURI(it)
        }
        viewModel.presentResult.observe(this) {
            when (it) {
                is Result.Fail -> {
                }
                is Result.Loading -> {
                }
                is Result.Success -> {
                    PresentFinishActivity.start(this, it.data)
                }
            }
        }
    }

    private fun initView() {
        viewModel.toastMessage.observeToastMessage(this, this)
        binding.root.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            binding.presentRootScrollView.scrollTo(0, binding.presentRootScrollView.bottom)
        }

        val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            viewModel.onImageSelected(uri)
        }
        binding.photoImageView.clipToOutline = true
        binding.photoImageView.setOnClickListener {
            toggleNumberKeyPadVisible(false)
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
        binding.makerNickNameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                toggleNumberKeyPadVisible(false)
            }
        }

        binding.supporterNameEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                toggleNumberKeyPadVisible(false)
            }
        }
        binding.presentMessageEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                toggleNumberKeyPadVisible(false)
            }
        }
        binding.presentAmountTextView.setOnClickListener {
            toggleNumberKeyPadVisible(true)
        }
        binding.numberKeyPad.init(viewModel.presentAmountOnNumberClickListener)
        binding.presentButton.setOnClickListener {
            viewModel.presentFunding(this)
        }
    }

    private fun toggleNumberKeyPadVisible(visible: Boolean) {
        if (visible) {
            binding.numberKeyPad.visibility = View.VISIBLE
            imm.hideSoftInputFromWindow(binding.makerNickNameEditText.windowToken, HIDE_NOT_ALWAYS)
            imm.hideSoftInputFromWindow(binding.supporterNameEditText.windowToken, HIDE_NOT_ALWAYS)
            imm.hideSoftInputFromWindow(binding.presentMessageEditText.windowToken, HIDE_NOT_ALWAYS)
            binding.makerNickNameEditText.clearFocus()
            binding.supporterNameEditText.clearFocus()
            binding.presentMessageEditText.clearFocus()
            numberKeyPadBackPressedCallback.isEnabled = true
        } else {
            binding.numberKeyPad.visibility = View.GONE
        }
    }

    companion object {
        fun start(context: Context, fundingId: Int) {
            val intent = Intent(context, PresentActivity::class.java).apply {
                putExtra(FUNDING_ID, fundingId)
            }
            context.startActivity(intent)
        }

        const val FUNDING_ID = "FUNDING_ID"
    }
}

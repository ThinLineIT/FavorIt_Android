package com.thinlineit.favorit_android.android.ui.createfunding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.MimeTypeFilter
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.ActivityCreateFundingBinding
import com.thinlineit.favorit_android.android.ui.MainActivity
import com.thinlineit.favorit_android.android.ui.customview.calendar.CalendarView
import com.thinlineit.favorit_android.android.ui.customview.numberkeypad.NumberKeyPadView
import com.thinlineit.favorit_android.android.ui.detail.FundingDetailActivity
import com.thinlineit.favorit_android.android.util.checkPermission
import com.thinlineit.favorit_android.android.util.fileFromContentUri
import com.thinlineit.favorit_android.android.util.setAllOnClickListener
import com.thinlineit.favorit_android.android.util.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFundingActivity : AppCompatActivity() {
    private val imm: InputMethodManager by lazy {
        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private val binding: ActivityCreateFundingBinding by lazy {
        ActivityCreateFundingBinding.inflate(layoutInflater)
    }
    val viewModel: CreateFundingViewModel by lazy {
        ViewModelProvider(this)[CreateFundingViewModel::class.java]
    }

    private val calendarOnBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (binding.calendarDatePicker.visibility == View.VISIBLE) {
                isEnabled = false
                binding.calendarDatePicker.visibility = View.GONE
            }
        }
    }


    private val numberKeyPadOnBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (binding.numberKeyPad.visibility == View.VISIBLE) {
                isEnabled = false
                binding.numberKeyPad.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkPermission(this)
        onBackPressedDispatcher.addCallback(calendarOnBackPressedCallback)
        onBackPressedDispatcher.addCallback(numberKeyPadOnBackPressedCallback)
        binding.apply {
            lifecycleOwner = this@CreateFundingActivity
            viewModel = this@CreateFundingActivity.viewModel
            initCalendarDatePicker(binding.calendarDatePicker)
            initNumberKeyPadView(binding.numberKeyPad)
            initObserver()
            initView()
        }.also {
            it.fundingDate.setOnClickListener {
                numberKeypadVisible(false)
                datePickerVisible(true)
            }
            it.fundingName.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    datePickerVisible(false)
                    numberKeypadVisible(false)
                }
            }

            it.content.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    datePickerVisible(false)
                    numberKeypadVisible(false)
                }
            }

            it.linkText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    datePickerVisible(false)
                    numberKeypadVisible(false)
                }
            }

            it.price.setOnClickListener {
                numberKeypadVisible(true)
                datePickerVisible(false)
            }

            it.imageGroup.setAllOnClickListener {
                attachImage()
            }
            it.reUploadButton.setOnClickListener {
                attachImage()
            }
            it.createButton.setOnClickListener {
                CreateFundingClickListener(
                    this,
                    this@CreateFundingActivity.viewModel
                ).onCreateTopic()
            }

            it.goToBackButton.setOnClickListener {
                finish()
            }
        }
    }

    private fun initView() {
        binding.root.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            binding.scrollView.scrollTo(0, binding.scrollView.bottom)
        }
    }

    private fun initNumberKeyPadView(numberKeyPad: NumberKeyPadView) {
        numberKeyPad.init(viewModel.fundingPriceOnNumberClickListener)
    }

    private fun initCalendarDatePicker(calendarDatePicker: CalendarView) {
        calendarDatePicker.setEndDate(viewModel.fundingExpiredDate.value)
        calendarDatePicker.onEndDateUpdated = {
            viewModel.onEndDateSelected(it)
        }
    }

    private fun datePickerVisible(value: Boolean) {
        if (value) {
            imm.hideSoftInputFromWindow(binding.fundingName.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.content.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.linkText.windowToken, 0)
            binding.fundingName.clearFocus()
            binding.content.clearFocus()
            binding.linkText.clearFocus()
            numberKeypadVisible(false)
            binding.calendarDatePicker.visibility = View.VISIBLE
            calendarOnBackPressedCallback.isEnabled = true
        } else {
            binding.calendarDatePicker.visibility = View.GONE
            calendarOnBackPressedCallback.isEnabled = false
        }
    }

    private fun numberKeypadVisible(value: Boolean) {
        if (value) {
            imm.hideSoftInputFromWindow(binding.fundingName.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.content.windowToken, 0)
            imm.hideSoftInputFromWindow(binding.linkText.windowToken, 0)
            binding.fundingName.clearFocus()
            binding.content.clearFocus()
            binding.linkText.clearFocus()
            binding.numberKeyPad.visibility = View.VISIBLE
            numberKeyPadOnBackPressedCallback.isEnabled = true
        } else {
            binding.numberKeyPad.visibility = View.GONE
            numberKeyPadOnBackPressedCallback.isEnabled = false
        }
    }

    private fun attachImage() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            putExtra(Intent.ACTION_GET_CONTENT, true)
        }
        getImage.launch(intent)
    }


    private val getImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            val imageUri = result.data?.data ?: run {
                Toast.makeText(this, getString(R.string.notice_no_img_selected), Toast.LENGTH_LONG)
                    .show()
                return@registerForActivityResult
            }
            imageUri.let {
                binding.addImageButton.visibility = View.GONE
                binding.addImageText.visibility = View.GONE
                binding.reUploadButton.visibility = View.VISIBLE
            }
            val mimeType = this.contentResolver.getType(imageUri)

            if (mimeType !== null && MimeTypeFilter.matches(mimeType, IMAGE_MIME_TYPE)) {
                val file = fileFromContentUri(this, imageUri)
                viewModel.loadImageUrl(
                    file.toUri(),
                    file.name
                )
            }
        }

    private fun initObserver() {
        viewModel.createFundingResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.createButton.isEnabled = !result.isLoading
                }
                is Result.Fail -> {
                    binding.createButton.isEnabled = true
                    this.shortToast("Something is wrong. Please try again.")
                }
                is Result.Success -> {
                    binding.createButton.isEnabled = true
                    FundingDetailActivity.start(this, result.data.fundingID)
                }
            }
        }
    }

    companion object {
        const val IMAGE_MIME_TYPE = "image/*"
        fun start(context: Context) {
            val intent = Intent(context, CreateFundingActivity::class.java)
            context.startActivity(intent)
        }
    }

}

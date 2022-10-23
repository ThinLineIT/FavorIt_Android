package com.thinlineit.favorit_android.android.ui.createfunding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thinlineit.favorit_android.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFundingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_funding)
        checkPermission(this)
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
            }
            val mimeType = this.contentResolver.getType(imageUri)

            if (mimeType !== null && MimeTypeFilter.matches(mimeType, IMAGE_MIME_TYPE)) {
                viewModel.loadImageUrl(
                    copyUri(
                        this,
                        imageUri,
                        mimeType
                    ),
                    getName(this, imageUri)
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
                    this.shortToast("성공")
                    // TODO: go to funding detail
                    // (result.data.fundingLink, result.data.fundingID)
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

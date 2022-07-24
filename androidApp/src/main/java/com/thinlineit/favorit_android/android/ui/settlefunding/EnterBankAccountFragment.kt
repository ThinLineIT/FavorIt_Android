package com.thinlineit.favorit_android.android.ui.settlefunding

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.Result
import com.thinlineit.favorit_android.android.databinding.FragmentEnterBankAccountBinding

class EnterBankAccountFragment :
    SettleFundingBaseFragment<FragmentEnterBankAccountBinding>(R.layout.fragment_enter_bank_account) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@EnterBankAccountFragment.viewModel
            initView()
            initNumberKeyPadView(binding.keyPadForAccount)
            initPriceCursorAnimation(binding.bankAccountCursor)
            initObserver()
        }
    }

    private fun initView() {
        binding.previousButton.setOnClickListener {
            navController.navigate(
                EnterBankAccountFragmentDirections
                    .actionEnterBankAccountFragmentToSelectBankFragment2()
            )
        }
        binding.nextButton.setOnClickListener {
            viewModel.checkBankAccount()
        }
    }

    private fun initNumberKeyPadView(keyPadForAccount: com.thinlineit.favorit_android.android.ui.customview.numberkeypad.NumberKeyPadView) {
        keyPadForAccount.init(viewModel.bankAccountOnNumberClickListener)
    }

    private fun initPriceCursorAnimation(bankAccountCursor: View) {
        val cursorAnim = AnimationUtils.loadAnimation(context, R.anim.blink_animation)
        this@EnterBankAccountFragment.viewModel.fundingAccountState.observe(viewLifecycleOwner) {
            if (it === SettleFundingViewModel.InputState.EMPTY) {
                bankAccountCursor.startAnimation(cursorAnim)
            } else {
                bankAccountCursor.clearAnimation()
            }
        }
    }

    private fun initObserver() {
        viewModel.accountOwnerNameResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Fail -> {
                    // TODO: add error message
                }
                is Result.Success -> {
                    navController.navigate(
                        EnterBankAccountFragmentDirections
                            .actionEnterBankAccountFragmentToConfirmBankAccountFragment()
                    )
                }
            }
        }
    }
}
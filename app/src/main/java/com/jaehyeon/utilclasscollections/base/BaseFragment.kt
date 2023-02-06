package com.jaehyeon.utilclasscollections.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaehyeon.utilclasscollections.extention.collectLatestLifecycleFlow
import com.jaehyeon.utilclasscollections.extention.shortToast
import com.jaehyeon.utilclasscollections.utils.ErrorEvent
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Jaehyeon on 2023/02/06.
 */
abstract class BaseFragment<T: ViewDataBinding, VM: BaseViewModel>(@LayoutRes val layoutRes: Int): Fragment() {

    protected lateinit var binding: T
    protected abstract val viewModel: VM
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    open fun initUI() {}
    open fun initData() {}
    open fun resumeData() {}
    protected fun isAttachActivity(): Boolean = activity != null && isAdded

    protected fun onBackPressed() {
        if (isAttachActivity()) requireActivity().onBackPressed()
    }

    protected fun errorHandle(error: ErrorEvent?) {
        when (error) {
            is ErrorEvent.ShowToastError -> {
                requireActivity().application.shortToast(error.message.asString(requireContext()))
            }

            is ErrorEvent.ShowDialogError -> {
            }

            else -> {

            }
        }
    }

    protected fun showBottomSheet(sheet: BottomSheetDialogFragment) {
        if (isAttachActivity()) {
            val alreadySheet = childFragmentManager.findFragmentByTag(sheet.javaClass.simpleName)
            if (alreadySheet == null) {
                sheet.show(childFragmentManager, sheet.javaClass.simpleName)
            }
        }
    }

    protected fun showDialog(dialog: DialogFragment) {
        if (isAttachActivity()) {
            val alreadyDialog = childFragmentManager.findFragmentByTag(dialog.javaClass.simpleName)
            if (alreadyDialog == null) {
                dialog.show(childFragmentManager, dialog.javaClass.simpleName)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        initUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        collectLatestLifecycleFlow(flow = viewModel.error) {
            errorHandle(it)
        }

        collectLatestLifecycleFlow(flow = viewModel.loading) {
            viewModel.loading.collectLatest {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        resumeData()
    }

}
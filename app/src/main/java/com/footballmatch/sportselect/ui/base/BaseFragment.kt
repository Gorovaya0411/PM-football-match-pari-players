package com.footballmatch.sportselect.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.footballmatch.sportselect.utill.autoCleared

abstract class BaseFragment<VB : ViewDataBinding>(
    @LayoutRes val layoutRes: Int
) : Fragment() {

    protected var binding by autoCleared<VB>()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initDataBinding(inflater, container).also { binding ->
            this.binding = binding
        }.root
    }

    private fun initDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB = DataBindingUtil.inflate(
        inflater,
        layoutRes,
        container,
        false
    )
}
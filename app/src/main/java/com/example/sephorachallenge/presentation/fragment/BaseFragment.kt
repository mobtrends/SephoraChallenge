package com.example.sephorachallenge.presentation.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    open val fragmentTag: String?
        get() = tag
}
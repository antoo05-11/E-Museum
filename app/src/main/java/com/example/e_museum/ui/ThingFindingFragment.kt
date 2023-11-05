package com.example.e_museum.ui

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.e_museum.EXTRA_MESSAGE
import com.example.e_museum.MuseumChoosingActivity
import com.example.e_museum.databinding.FragmentThingFindingBinding
import com.example.e_museum.databinding.MuseumChoosingViewBinding

class ThingFindingFragment : Fragment() {

    private var _binding: FragmentThingFindingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var codeScanner: CodeScanner

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThingFindingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val scannerView = binding.scannerView

        codeScanner = CodeScanner(requireContext(), scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                Toast.makeText(
                    activity?.applicationContext,
                    "Scan result: ${it.text}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            activity?.runOnUiThread {
                Toast.makeText(
                    activity?.applicationContext, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        binding.qrScanButton.setOnClickListener {
            binding.qrScannerView.visibility = View.VISIBLE
            binding.idInputView.visibility = View.INVISIBLE
        }
        binding.idButton.setOnClickListener {
            binding.qrScannerView.visibility = View.INVISIBLE
            binding.idInputView.visibility = View.VISIBLE
        }

        Thread {
            SystemClock.sleep(2000)
            //haizzzz
            val intent = Intent(context, MuseumChoosingActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "hello")
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }.start()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}
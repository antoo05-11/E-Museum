package com.example.e_museum.fragments_thing_finding

import android.os.Bundle
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
import com.example.e_museum.MainActivity
import com.example.e_museum.databinding.FragmentFindingThingByScanningBinding

class FindingThingByScanningFragment : Fragment() {

    private var _binding: FragmentFindingThingByScanningBinding? = null
    private val binding get() = _binding!!

    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindingThingByScanningBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //QR Camera view config.
        val scannerView = binding.scannerView
        codeScanner = CodeScanner(requireContext(), scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                Toast.makeText(
                    activity?.applicationContext,
                    "Scan result: ${it.text}",
                    Toast.LENGTH_LONG
                ).show()

                Thread {
                    MainActivity.sqlConnection.getDataQuery(
                        String.format(
                            "select * from things where thingID = %s",
                            it.text
                        )
                    )
                    requireActivity().runOnUiThread{
                        // TODO: Change to view thing fragment.
                    }
                }.start()
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
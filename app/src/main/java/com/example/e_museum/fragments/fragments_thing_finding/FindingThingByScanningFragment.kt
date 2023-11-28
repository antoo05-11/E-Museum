package com.example.e_museum.fragments.fragments_thing_finding

import android.content.Intent
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
import com.example.e_museum.activities.ViewThingActivity
import com.example.e_museum.databinding.FragmentFindingThingByScanningBinding
import com.example.e_museum.entities.Thing

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
                Thread {
                    val queryString = String.format(
                        "select * from things where thingID = %s", it.text
                    )
                    val resultSet = MainActivity.sqlConnection.getDataQuery(queryString)
                    if (resultSet == null) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Wrong ID", Toast.LENGTH_SHORT).show()
                        }
                        return@Thread
                    }
                    if (resultSet.next()) {
                        requireActivity().runOnUiThread {
                            val intent =
                                Intent(requireContext(), ViewThingActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    putExtra("thing", Thing(resultSet))
                                }
                            startActivity(intent)
                        }
                    }
                }.start()
            }
        }
        codeScanner.errorCallback = ErrorCallback {
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